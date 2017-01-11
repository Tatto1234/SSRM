package com.sun.suma.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 向FTP服务器上传文件Apache Commons Net jar
 * 
 * @author Administrator
 *
 */
public class FTPUtil {
	private static Logger log = LoggerFactory.getLogger(FTPUtil.class);

	// FTP 用户名
	private static String userName; 
	// FTP 密码
	private static String password; 
	//FTP 地址IPַ
	private static String ip; 
	// FTP 端口
	private static int port; 
	// FTP 客户端
	private static FTPClient ftpClient = null; 
	// FTP 状态码
	public static int i = 1;

	/**
	 * FTP 初始化FTP基本参数，后期从配置文件读取
	 */
	private static void setArg() {

		userName = "uftp";
		password = "taoyiftp";
//		ip = "192.166.62.103";
		ip = "120.76.24.135";
		port = 21;

	}

	/**
	 * FTP 连接FTP服务器
	 * 
	 * @return
	 */
	public static boolean connectServer() {
		boolean flag = true;
		if (ftpClient == null) {
			int reply;
			try {
				setArg();
				ftpClient = new FTPClient();
				ftpClient.setControlEncoding("UTF-8");
				ftpClient.setDefaultPort(port);
				ftpClient.configure(getFtpConfig());
				ftpClient.connect(ip);
				ftpClient.login(userName, password);
				ftpClient.setDefaultPort(port);
				reply = ftpClient.getReplyCode();
				ftpClient.setDataTimeout(12000000);
				ftpClient.setConnectTimeout(18000000);
				if (!FTPReply.isPositiveCompletion(reply)) {
					ftpClient.disconnect();
					log.info("FTP服务器连接拒接！ ");
					flag = false;
				}
				i++;
			} catch (SocketException e) {
				flag = false;
				e.printStackTrace();
				log.error("登录FTP服务器 " + ip + " 失败，连接超时！");
			} catch (IOException e) {
				flag = false;
				e.printStackTrace();
				log.error("登录FTP服务器 " + ip + " 失败，FTP服务器无法打开！");
			}
		}
		return flag;
	}

	/**
	 * 设置FTP客服端的配置--一般可以不设置
	 * 
	 * @return
	 */
	private static FTPClientConfig getFtpConfig() {
		FTPClientConfig ftpConfig = new FTPClientConfig(
				FTPClientConfig.SYST_UNIX);
		ftpConfig.setServerLanguageCode(FTP.DEFAULT_CONTROL_ENCODING);
		return ftpConfig;
	}

/**
 * 上传多个文件，并重命名
 * @param localFile
 * @param localRootFile
 * @param distFolder
 * @param filename
 * @return
 */
	public static boolean uploadFile(File localFile, final File localRootFile,
			final String distFolder,String filename) {
		log.info("----------------文件"+filename+"上传FTP服务器开始------------------");
		boolean flag = true;
		try {
			//connectServer();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
			InputStream input = new FileInputStream(localFile);
//			String furi1 = localFile.getParentFile().getAbsoluteFile().toURI()
//					.toString();
//			String furi2 = localRootFile.getParentFile().getAbsoluteFile()
//					.toURI().toString();
//			 String objFolder = distFolder + File.separator + furi1.substring(furi2.length());
//			ftpClient.changeWorkingDirectory("/");
//			ftpClient.makeDirectory(objFolder);
//			ftpClient.changeWorkingDirectory(objFolder);log.info(localFile.exists()+"");
			log.info( "原始路径："+localFile.getPath() + " ,  FTP服务器路径:" + ftpClient.printWorkingDirectory());
			flag = ftpClient.storeFile(filename, input);
			if (flag) {
				log.info("--------"+filename+"文件上传成功------------------");
			} else {
				log.info("--------"+filename+"文件上传失败------------------");
			}
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
			log.error("--------------"+filename+"文件上传异常------", e);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 
	 * @param localFile
	 * @return
	 */
	public static String uploadManyFile(String localFile) {
		boolean flag = true;
		StringBuffer strBuf = new StringBuffer();
		try {
			//connectServer();
			File file = new File(localFile); // // 在此目录中找文件
			File fileList[] = file.listFiles();
			for (File f : fileList) {
				if (f.isDirectory()) { // 文件夹中还有文件夹
					uploadManyFile(f.getAbsolutePath());
				} else {
				}
				if (!flag) {
					strBuf.append(f.getName() + "\r\n");
				}
			}
			System.out.println(strBuf.toString());
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("本地文件上传失败！", e);
		}
		return strBuf.toString();
	}


     /**
      * 上传多个文件
      * @param localFile
      * @param localRootFile
      * @param distFolder
      * @return
      */
	public static String uploadManyFile(File localFile,
			final File localRootFile, final String distFolder) {
		log.info("----------------文件上传开始ʼ------------------");
		boolean flag = true;
		StringBuffer strBuf = new StringBuffer();
		try {
			//connectServer();
			//判断下文件夹创建是否成功
			boolean dirFlag=ftpClient.makeDirectory(distFolder + File.separator
					+ localFile.getParent());
			if(dirFlag)
			{
				log.info("文件夹创建成功"+distFolder + File.separator
					+ localFile.getParent());
			}
			else {
				log.info("文件夹创建失败"+distFolder + File.separator
						+ localFile.getParent());
			}
			File fileList[] = localFile.listFiles();
			for (File upfile : fileList) {
				if (upfile.isDirectory()) {
					uploadManyFile(upfile, localRootFile, distFolder);
				} else {
					flag = uploadFile(upfile, localRootFile, distFolder,upfile.getName());
				}
				if (!flag) {
					strBuf.append(upfile.getName() + "\r\n");
				}
			}
			log.info("-------------" + strBuf.toString() + "-----------");
		} catch (NullPointerException e) {
			e.printStackTrace();
			log.error("------本地文件上传失败！找不到上传文件！-----", e);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("------本地文件上传失败！---------", e);
		}
		return strBuf.toString();
	}

     /**
      * 下载文件
      * @param remoteFileName
      * @param localFileName
      * @return
      */
	public static boolean loadFile(String remoteFileName, String localFileName) {
		boolean flag = true;
		//connectServer();
		BufferedOutputStream buffOut = null;
		try {
			buffOut = new BufferedOutputStream(new FileOutputStream(
					localFileName));
			flag = ftpClient.retrieveFile(remoteFileName, buffOut);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("本地文件下载失败！", e);
		} finally {
			try {
				if (buffOut != null)
					buffOut.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

	/**
	 * 删除一个文件
	 * @param filename
	 * @return
	 */
	public static boolean deleteFile(String filename) {
		boolean flag = true;
		try {
			connectServer();
			flag = ftpClient.deleteFile(filename);
			if (flag) {
				log.info("文件" + "filename" + "删除成功");
			} else {
				log.info("文件" + "filename" + "删除失败");
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return flag;
	}

	/**
	 * 删除目录
	 * @param pathname
	 */
	public static void deleteDirectory(String pathname) {
		try {
			connectServer();
			File file = new File(pathname);
			if (file.isDirectory()) {
				ftpClient.removeDirectory(pathname);
			} else {
				deleteFile(pathname);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	/**
	 * 删除空目录
	 */
	public static void deleteEmptyDirectory(String pathname) {
		try {
			connectServer();
			ftpClient.removeDirectory(pathname);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	/**
	 * 列出服务器上文件和目录
	 *
	 * @param regStr
	 */
	public static void listRemoteFiles(String regStr) {
		connectServer();
		try {
			String files[] = ftpClient.listNames(regStr);
			if (files == null || files.length == 0)
				log.info("---------FTP 服务器上无任何文件!-----------");
			else {
				for (int i = 0; i < files.length; i++) {
					System.out.println(files[i]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 列出Ftp服务器上的所有文件和目录
	 */
	public static void listRemoteAllFiles() {
		connectServer();
		try {
			String[] names = ftpClient.listNames();
			for (int i = 0; i < names.length; i++) {
				System.out.println(names[i]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭FTP连接
	 */
	public static void closeConnect() {
		try {
			if (ftpClient != null) {
				ftpClient.logout();
				ftpClient.disconnect();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置传输文件的类型[文本文件或者二进制文件]
	 * 
	 * @param fileType
	 *            --BINARY_FILE_TYPE��ASCII_FILE_TYPE
	 *
	 */
	public static void setFileType(int fileType) {
		try {
			connectServer();
			ftpClient.setFileType(fileType);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *  扩展使用
	 * 
	 * @return ftpClient
	 */
	protected static FTPClient getFtpClient() {
		connectServer();
		return ftpClient;
	}

	/**
	 * 进入到服务器的某个目录下
	 *
	 * @param directory
	 */
	public static void changeWorkingDirectory(String directory) {
		try {
			connectServer();
			ftpClient.changeWorkingDirectory(directory);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	/**
	 * 返回到上一层目录
	 */
	public static void changeToParentDirectory() {
		try {
			connectServer();
			ftpClient.changeToParentDirectory();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	/**
	 * 文件重命名
	 * @param oldFileName
	 * @param newFileName
	 */
	public static void renameFile(String oldFileName, String newFileName) {
		try {
			connectServer();
			ftpClient.rename(oldFileName, newFileName);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	/**
	 * 转码[ISO-8859-1 -> GBK] 不同的平台需要不同的转码
	 * 
	 * @param obj
	 * @return ""
	 */
	@SuppressWarnings("unused")
	private static String iso8859togbk(Object obj) {
		try {
			if (obj == null)
				return "";
			else
				return new String(obj.toString().getBytes("iso-8859-1"), "GBK");
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 在服务器上创建一个文件夹
	 * @param dir
	 * @return
	 */
	public static boolean makeDirectory(String dir) {
		connectServer();
		boolean flag = true;
		try {
			flag = ftpClient.makeDirectory(dir);
			if (flag) {
				log.info("创建文件夹" + dir + "成功");

			} else {
				log.info("创建文件夹 " + dir + "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
}
