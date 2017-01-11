package com.sun.suma.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.net.ftp.FTP;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sun.suma.entity.Range;
import com.sun.suma.exception.StreamException;
import com.sun.suma.util.Configurations;
import com.sun.suma.util.FTPUtil;
import com.sun.suma.util.FileUpLoadConstant;
import com.sun.suma.util.IoUtil;
import com.sun.suma.util.TokenUtil;





@Controller
@RequestMapping(value="/stream")
public class FileUpLoadByStreamController {
	
	private static Logger log=LoggerFactory.getLogger(FileUpLoadByStreamController.class);
	
	/**
	 * 获取文件的token
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(value="/gettoken")
	public void getFileToken(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		String name=request.getParameter(FileUpLoadConstant.FILE_NAME_FIELD);
		String size=request.getParameter(FileUpLoadConstant.FILE_SIZE_FIELD);
		String token=TokenUtil.generateToken(name, size);
		PrintWriter writer=response.getWriter();
		JSONObject json=new JSONObject();
		try{
			json.put(FileUpLoadConstant.TOKEN_FIELD, token);
			json.put(FileUpLoadConstant.SUCCESS, true);
			json.put(FileUpLoadConstant.MESSAGE, "");
		}catch(JSONException e)
		{
			log.info("文件"+name+"获取token失败！");
		}
		
		log.info("文件"+name+"获取token成功--token："+token);
		writer.write(json.toString());	
	}
	/**
	 * get 方式上传文件
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@RequestMapping(value="/streamUpload",method=RequestMethod.GET)
	public void uploadFileByGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		doOptions(request, response);

		final String token = request.getParameter(FileUpLoadConstant.TOKEN_FIELD);
		final String size = request.getParameter(FileUpLoadConstant.FILE_SIZE_FIELD);
		final String fileName = request.getParameter(FileUpLoadConstant.FILE_NAME_FIELD);
		final PrintWriter writer = response.getWriter();
		
		/** TODO: validate your token. */
		
		JSONObject json = new JSONObject();
		long start = 0;
		boolean success = true;
		String message = "";
		try {
			File f = IoUtil.getTokenedFile(token);
			start = f.length();
			/** file size is 0 bytes. */
			if (token.endsWith("_0") && "0".equals(size) && 0 == start)
				f.renameTo(IoUtil.getFile(fileName));
		} catch (FileNotFoundException fne) {
			message = "Error: " + fne.getMessage();
			success = false;
		} finally {
			try {
				if (success)
				json.put(FileUpLoadConstant.START_FIELD, start);
				json.put(FileUpLoadConstant.SUCCESS, success);
				json.put(FileUpLoadConstant.MESSAGE, message);
			} catch (JSONException e) {}
			writer.write(json.toString());
			IoUtil.close(writer);
		}
	}
 
	
	/**
	 * post方式上传文件
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value="/streamUpload",method=RequestMethod.POST)
	public void uploadFileByPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
        doOptions(request, response);
		
		final String token = request.getParameter(FileUpLoadConstant.TOKEN_FIELD);
		final String fileName =request.getParameter(FileUpLoadConstant.FILE_NAME_FIELD);
		Range range = IoUtil.parseRange(request);
		
		OutputStream out = null;
		InputStream content = null;
		final PrintWriter writer = response.getWriter();
		
		/** TODO: validate your token. */
		
		JSONObject json = new JSONObject();
		long start = 0;
		boolean success = true;
		String message = "";
		File f = IoUtil.getTokenedFile(token);
		try {
			if (f.length() != range.getFrom()) {
				/** drop this uploaded data */
				throw new StreamException(StreamException.ERROR_FILE_RANGE_START);
			}
			
			out = new FileOutputStream(f, true);
			content = request.getInputStream();
			int read = 0;
			final byte[] bytes = new byte[FileUpLoadConstant.BUFFER_LENGTH];
			while ((read = content.read(bytes)) != -1)
				out.write(bytes, 0, read);

			start = f.length();
		} catch (StreamException se) {
			success = StreamException.ERROR_FILE_RANGE_START == se.getCode();
			message = "Code: " + se.getCode();
		} catch (FileNotFoundException fne) {
			message = "Code: " + StreamException.ERROR_FILE_NOT_EXIST;
			success = false;
		} catch (IOException io) {
			message = "IO Error: " + io.getMessage();
			success = false;
		} finally {
			IoUtil.close(out);
			IoUtil.close(content);

			/** rename the file */
			if (range.getSize() == start) {
				/** fix the `renameTo` bug */
//				File dst = IoUtil.getFile(fileName);
//				dst.delete();
				// TODO: f.renameTo(dst); 重命名在Windows平台下可能会失败，stackoverflow建议使用下面这句
				try {
					// 先删除
					IoUtil.getFile(fileName).delete();
					
					Files.move(f.toPath(), f.toPath().resolveSibling(fileName));
					System.out.println("TK: `" + token + "`, NE: `" + fileName + "`");
				   
					/** if `STREAM_DELETE_FINISH`, then delete it. */
					if (Configurations.isDeleteFinished()) {
						IoUtil.getFile(fileName).delete();
					}
					String filePath=f.getParent()+File.separator+fileName;
					File file=new File(filePath);
					FTPUtil.connectServer();
					FTPUtil.setFileType(FTP.BINARY_FILE_TYPE);
					FTPUtil.uploadFile(file, file, "java",fileName);
					//FTPUtil.uploadManyFile(file, file, "java");
					
				} catch (IOException e) {
					success = false;
					message = "Rename file error: " + e.getMessage();
				}
				
			}
			try {
				if (success)
				json.put(FileUpLoadConstant.START_FIELD, start);
				json.put(FileUpLoadConstant.SUCCESS, success);
				json.put(FileUpLoadConstant.MESSAGE, message);
			} catch (JSONException e) {}
			
			writer.write(json.toString());
			IoUtil.close(writer);
		}
	}
	
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("application/json;charset=utf-8");
		resp.setHeader("Access-Control-Allow-Headers", "Content-Range,Content-Type");
		resp.setHeader("Access-Control-Allow-Origin", Configurations.getCrossOrigins());
		resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
	}
	
	
	@RequestMapping(value="/streamUploadByForm",method=RequestMethod.POST)
	public void uploadFileByForm(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{

        /** flash @ windows bug */
        request.setCharacterEncoding("utf8");

        final PrintWriter writer = response.getWriter();
        // Check that we have a file upload request
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (!isMultipart) {
            writer.println("ERROR: It's not Multipart form.");
            return;
        }
        JSONObject json = new JSONObject();
        long start = 0;
        boolean success = true;
        String message = "";

        ServletFileUpload upload = new ServletFileUpload();
        InputStream in = null;
        String token = null;
        try {
            String filename = null;
            FileItemIterator iter = upload.getItemIterator(request);
            while (iter.hasNext()) {
                FileItemStream item = iter.next();
                String name = item.getFieldName();
                in = item.openStream();
                if (item.isFormField()) {
                    String value = Streams.asString(in);
                    if (FileUpLoadConstant.TOKEN_FIELD.equals(name)) {
                        token = value;
                        /** TODO: validate your token. */
                    }
                    System.out.println(name + ":" + value);
                } else {
                    if (token == null || token.trim().length() < 1)
                        token =request.getParameter(FileUpLoadConstant.TOKEN_FIELD);
                    /** TODO: validate your token. */

                    // 这里不能保证token能有值
                    filename = item.getName();
                    if (token == null || token.trim().length() < 1)
                        token = filename;

                    start = IoUtil.streaming(in, token, filename);
                }
            }

            System.out.println("Form Saved : " + filename);
        } catch (FileUploadException fne) {
            success = false;
            message = "Error: " + fne.getLocalizedMessage();
        } finally {
            try {
                if (success)
                    json.put(FileUpLoadConstant.START_FIELD, start);
                json.put(FileUpLoadConstant.SUCCESS, success);
                json.put(FileUpLoadConstant.MESSAGE, message);
            } catch (JSONException e) {
            }

            writer.write(json.toString());
            IoUtil.close(in);
            IoUtil.close(writer);
        }
	}
	
	
}
