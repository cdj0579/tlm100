package com.unimas.common.image;

import java.io.File;
import java.io.FileInputStream;

public class ImageUtil {
	
	private String PATH;
	private String SWT=".jpg";
	
	public ImageUtil(String path){
		this.PATH = path;
	}
	
	public void setSWT(String sWT) {
		SWT = sWT;
	}
	
	public String getFileName(String id){
		return id+SWT;
	}
	
	protected String getDefaultFileName(){
		return "default"+SWT;
	}
	
	public byte[] getImage(String id) throws Exception {
		String filename = getFileName(id);
		byte[] b = this.getBytes(PATH,filename);
		if(b == null ){
			b = this.getBytes(PATH, getDefaultFileName());
		}
		return b;
	}
	
	public byte[] getIcon(String filePath,String id) throws Exception{
		String filename = getFileName(id);
		byte[] b = this.getBytes(filePath,filename);
		if(b == null ){
			b = this.getBytes(filePath,getDefaultFileName());
		}
		return b;
		
	}
	public byte[] getBytes(String filePath, String filename) throws Exception{
		File file = new File(filePath+filename);
		byte[] bs = new byte[(int)file.length()];
		FileInputStream fis =null;
		try {
			 fis = new FileInputStream(file);
			fis.read(bs);
		} catch (Exception e) {
			return null;
			
		}finally{
			if(fis!=null){
				fis.close();
			}
		}
		return bs;
	}
	
	public boolean moveIcon2Reject(String dataFilePath, String rejectFilePath,String filename,String newFilename) throws Exception {
		File file = new File(dataFilePath+filename+SWT);
		if(file.isFile())
		{
			return file.renameTo(new File(rejectFilePath + newFilename + SWT));
		} 
		return false;
	}

	
	
	public static void main(String[] args) {
		ImageUtil dao = new ImageUtil("D:\\ect\\unimas\\images"+File.separator);
		byte[] b;
		try {
			b = dao.getImage("");
			System.out.println(b.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
