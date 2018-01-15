package com.unimas.common.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;

import org.apache.log4j.Logger;

/**
 * 由相对路径获得 绝对路径、File、文件列表，此工具根据 ClassPath寻找路径 注意：本类不要加到jar包中，不然会失效
 * 在Servlet中获得文件真实路径 string
 * file_real_path=getServletContext().getRealPath("/1.swf");
 * 
 */
public class FilePathUtils {
	private static Logger logger = Logger.getLogger("UELogger");
	private FilePathUtils() {}
	
	
	public static String readFile2(String path)throws Exception {
		String ret = "",jsonStr="";
		BufferedReader reader = null;
		InputStream input = new FileInputStream(new File(path));
		reader = new BufferedReader(new InputStreamReader(input,"UTF-8"));
		while ((jsonStr = reader.readLine()) != null) {
			ret = ret + jsonStr;
		}
		if(null !=reader){
			input.close();
			reader.close();
		}
		return ret;
    }
	public static String readFile(String path) throws Exception{
		String ret = "",jsonStr="";
		BufferedReader reader = null;
		InputStream input = new FileInputStream(new File(FilePathUtils.getClassPath("")+File.separator+path));
		reader = new BufferedReader(new InputStreamReader(input,"UTF-8"));
		while ((jsonStr = reader.readLine()) != null) {
			ret = ret + jsonStr;
		}
		if(null !=reader){
			input.close();
			reader.close();
		}
		return ret;
	}

	/**
	 * 根据相对路径获得 File
	 * 
	 * @param relativePath
	 *            工程下的以"./"开头，web下的以"/"开头，ClassPath下的直接相对路径
	 * @return File
	 */
	public static File getFileByRelativePath(String relativePath) {
		return new File(getAbsolutePath(relativePath));
	}

	/**
	 * 根据相对路径获得绝对路径
	 * 
	 * @param relativePath
	 *            工程下的以"./"开头，web下的以"/"开头，ClassPath下的直接相对路径
	 * @return 绝对路径字符串
	 */
	private static String getAbsolutePath(String relativePath) {
		String result = null;
		if (null != relativePath) {
			if (relativePath.indexOf("./") == 0) {
				String workspacePath = new File("").getAbsolutePath();
				relativePath = relativePath.substring(2);
				if (relativePath.length() > 0) {
					relativePath = relativePath
							.replace('/', File.separatorChar);
					result = workspacePath + String.valueOf(File.separatorChar)
							+ relativePath;
				} else {
					result = workspacePath;
				}
			} else if (relativePath.indexOf("/") == 0) {
				String webRootPath = getAbsolutePathOfWebRoot();
				if (relativePath.length() > 0) {
					relativePath = relativePath
							.replace('/', File.separatorChar);
					result = webRootPath + relativePath;
				} else {
					result = webRootPath;
				}
			} else {
				String classPath = getAbsolutePathOfClassPath();
				if (relativePath.length() > 0) {
					relativePath = relativePath
							.replace('/', File.separatorChar);
					result = classPath + File.separatorChar + relativePath;
				} else {
					result = classPath;
				}
			}
		}
		return result;
	}

	// 得到WebRoot目录的绝对地址
	public static String getAbsolutePathOfWebRoot() {
		String result = null;
		result = getAbsolutePathOfClassPath();
		result = result.replace(File.separatorChar + "WEB-INF"
				+ File.separatorChar + "classes", "");
		return result;
	}

	// 得到ClassPath的绝对路径
	public static String getAbsolutePathOfClassPath() {
		String result = null;
		try {
			File file = new File(getURLOfClassPath().toURI());
			result = file.getAbsolutePath();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 得到ClassPath的URL
	private static URL getURLOfClassPath() {
		URL url = getClassLoader().getResource("/");
		if(null == url){
			url = getClassLoader().getResource("");
		}
		return url;
	}

	// 得到类加载器
	private static ClassLoader getClassLoader() {
		return FilePathUtils.class.getClassLoader();
	}

	public static String getWebInfPath() {
		URL url = FilePathUtils.class.getProtectionDomain().getCodeSource().getLocation();
		String path = url.toString();
		int index = path.indexOf("WEB-INF");

		if (index == -1) {
			index = path.indexOf("classes");
		}

		if (index == -1) {
			index = path.indexOf("bin");
		}

		path = path.substring(0, index + 8);

		if (path.startsWith("zip")) {// 当class文件在war中时，此时返回zip:D:/...这样的路径
			path = path.substring(4);
		} else if (path.startsWith("file")) {// 当class文件在class文件中时，此时返回file:/D:/...这样的路径
			path = path.substring(6);
		} else if (path.startsWith("jar")) {// 当class文件在jar文件里面时，此时返回jar:file:/D:/...这样的路径
			path = path.substring(10);
		}
		try {
			path = URLDecoder.decode(path, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		if (File.separator.equals("\\")) {

		} else if (File.separator.equals("/")) {
			path = File.separator + path;
		}
		return path;
	}

	public static String getWebInfPath(String relativePath) {
		return getWebInfPath() + relativePath;
	}

	public static String getClassPath(String relativePath) {
		return getAbsolutePathOfClassPath() + File.separator + relativePath;
	}
	
	
	public static boolean writeFileBy(String path,String fileName,String text){
		File dir = new File(path);
		if(!dir.exists()){
			logger.debug("目录["+path+"]不存在,将自动创建这个目录.");
			dir.mkdirs();
		}
		File file = new File(path + File.separator + fileName);
		if(file.exists()){
			file.delete();
		}
		Writer writer = null;
		OutputStream out = null;
		OutputStreamWriter outWriter = null;  
		try{
			out = new FileOutputStream(file);
			outWriter = new OutputStreamWriter(out,"UTF-8");
			writer = new BufferedWriter(outWriter);
			writer.write(text);
			writer.flush();
			logger.debug("写PATH["+(path + File.separator + fileName)+"]文件success!");
			System.out.println("写PATH["+(path + File.separator + fileName)+"]文件success!");
		} catch (IOException e) {
			logger.warn("write file["+path+"] exception,case: ",e);
			throw new RuntimeException(e);
		}finally{
			try {
				if(null != out){
					out.close();
				}
				if(null != writer){
					writer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			writer = null;
		}
		return true;
	}
	
	public static void main(String[] args) {
		//System.out.println(FilePathUtils.getWebInfPath());
		//writeFileBy("d:\\testzxy11.txt","{\"x\" : \"y\"}");
		//String s = getWebInfPath();
		//System.out.println(s);
		
		//System.out.println(writeFileBy("d://t1/t.json","hello"));
		
		
	}

}
