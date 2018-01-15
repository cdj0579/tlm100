package com.unimas.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * zip压缩，unzip解压缩工具类
 * @author cj
 *
 */
public class ZipUtil {
	
	public static void unzip(String inputFilePath,String outputRoot) throws Exception {
		unzip(inputFilePath,outputRoot,false,null);
	}
	

	/**
	 * The buffer.
	 */
	protected static byte buf[] = new byte[1024];
	
	/**
	 * 根据文件流判断是否为zip文件
	 * 
	 * @param is
	 * @return
	 */
	public static boolean isZipFile(InputStream is) {
		boolean isZipFile = false;
		is.mark(0);//标记流的开头位置
		byte[] b = new byte[4];
		try {
			is.read(b, 0, b.length);
			is.reset();//读完前4个字节后，流的读取位置重置到开头
		} catch (IOException e) {
			e.printStackTrace();
		}
		String type = BytesUtils.bytesToHexString(b).toUpperCase();
		if (type.contains("504B0304")) {
			isZipFile = true;
		}
		return isZipFile;
	}
	
	/* 解压的文件名不能包含中文 */
	public static void unzip(String inputFilePath,String outputRoot,boolean verbose, String verboseOutFile) throws Exception {
		File file = new File(inputFilePath);
		/* 解压后的文件根目录 */
		String rootPath = outputRoot==null ?
				file.getAbsolutePath().substring(0,file.getAbsolutePath().lastIndexOf("."))
				:outputRoot;
		/* 创建根文件夹 */
		File rootFile = new File(rootPath);
		if(!rootFile.exists()){
			rootFile.mkdir();
		}
		/* 创建压缩文件输入流 */
		ZipInputStream in = new ZipInputStream(new BufferedInputStream(new FileInputStream(file), 2048));
		ZipEntry entry = null;// 读取的压缩条目
		/* 解压缩开始 */
		int number = 1;
		File f_verboseOutFile = null;
		FileWriter writer = null;
		if(verbose){
			f_verboseOutFile = new File(verboseOutFile);
			if(!f_verboseOutFile.exists()){
				f_verboseOutFile.createNewFile();
			}
		}
		while ((entry = in.getNextEntry()) != null) {
			if(verbose){
				writer = new FileWriter(f_verboseOutFile, true);
				String _output = "解压缩："+entry.getName()+"\r\n";
				writer.write(_output);
				writer.flush();
			}
			decompression(entry, number, rootPath, in);// 解压
			number++;
		}
		in.close();// 关闭输入流
		in = null;
		writer.close();//关闭输出信息的写入流
		writer = null;
	}

	/**
	 * 功能：解压
	 * @param entry 解压条目
	 * @param number 数量
	 * @param rootPath 根目录
	 * @throws Exception
	 */
	private static void decompression(ZipEntry entry, int number,String rootPath, ZipInputStream in) throws Exception {
		/* 如果是文件夹 */
		if ((entry.isDirectory() || -1 == entry.getName().lastIndexOf("."))&& !"\\".equals(entry.getName())) {
			File file = new File(rootPath+ File.separator
					+ entry.getName().substring(0, entry.getName().length() - 1));
			file.mkdir();// 创建文件夹
		} else if (!"\\".equals(entry.getName())) {
			File file = new File(rootPath + File.separator + entry.getName());
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file), 2048);
			int b;
			while ((b = in.read()) != -1) {
				bos.write(b);
			}
			bos.close();
			bos = null;
		}
	}
	
	/**
	 * 检查并设置有效的压缩级别.
	 * @param level - 压缩级别
	 * @return 有效的压缩级别或者默认压缩级别
	 */
	public static int checkZipLevel(int level) {
		if (level < 0 || level > 9)
			level = 7;
		return level;
	}
	
	/**
	 * 遍历目录并添加文件.
	 * @param jos - JAR 输出流
	 * @param file - 目录文件名
	 * @param pathName - ZIP中的目录名
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private static void recurseFiles(ZipOutputStream jos, File file,
			String pathName) throws IOException, FileNotFoundException {
		if (file.isDirectory()) {
			pathName = pathName + file.getName() + "/";
			jos.putNextEntry(new ZipEntry(pathName));
			String fileNames[] = file.list();
			if (fileNames != null) {
				for (int i = 0; i < fileNames.length; i++)
					recurseFiles(jos, new File(file, fileNames[i]), pathName);

			}
		} else {
			ZipEntry jarEntry = new ZipEntry(pathName + file.getName());
			//                System.out.println(pathName + "  " + file.getName());
			FileInputStream fin = new FileInputStream(file);
			BufferedInputStream in = new BufferedInputStream(fin);
			jos.putNextEntry(jarEntry);

			int len;
			while ((len = in.read(buf)) >= 0)
				jos.write(buf, 0, len);
			in.close();
			jos.closeEntry();
		}
	}
	
	public static void toZip(List<File> files, File zipFile,
			String zipFolderName, int level) throws IOException,
			FileNotFoundException {
		level = checkZipLevel(level);

		if (zipFolderName == null) {
			zipFolderName = "";
		}

		ZipOutputStream jos = new ZipOutputStream(new FileOutputStream(zipFile));
		jos.setLevel(level);

		for (int i = 0; i < files.size(); i++) {
			try{
				recurseFiles(jos, files.get(i), zipFolderName);
			}catch(ZipException e){
				e.printStackTrace();
			}
		}

		jos.close();

	}
	
	public static void main(String[] args) {
		try {
			unzip("C:\\cdj_upload\\cdjjj.zip",null,true,"C:\\cdj_upload\\tempfile.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}