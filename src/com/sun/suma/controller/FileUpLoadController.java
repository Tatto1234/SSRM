package com.sun.suma.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.net.ftp.FTP;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.sun.suma.util.FTPUtil;

/**
 * 用户上传文件到FTP服务器上
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value="/file")
public class FileUpLoadController {		
	@RequestMapping(value="/upload")  
    public void addUser(@RequestParam("file")CommonsMultipartFile[] files,HttpServletRequest request) throws IllegalStateException, IOException{  
		
		
		int size=files.length;
		FTPUtil.connectServer();
		FTPUtil.setFileType(FTP.BINARY_FILE_TYPE);
		for(int i=0;i<size;i++)
		{
			String filename=files[i].getOriginalFilename();
			DiskFileItem fi = (DiskFileItem)files[i].getFileItem();
			File file=new File("");
			file = fi.getStoreLocation();		
			FTPUtil.uploadFile(file, file, "java",filename);
		}
		
	}     
	

}
