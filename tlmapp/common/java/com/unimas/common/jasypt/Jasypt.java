package com.unimas.common.jasypt;

import org.jasypt.util.text.BasicTextEncryptor;
/**
 * 加密结果不唯一
 * 所以解密之后比较
 * @author unimas
 *
 */
public class Jasypt {
	
	/**
	 * 加密
	 * @param text
	 * @return
	 */
	public static String encrypt(String text){
		
		BasicTextEncryptor  textEncryptor=new BasicTextEncryptor();
		textEncryptor.setPassword("unimas");
		return textEncryptor.encrypt(text);
		
		
		
	}
	/**
	 * 解密
	 * @param text
	 * @return
	 */
    public static String decrypt(String text){
		
    	BasicTextEncryptor  textEncryptor=new BasicTextEncryptor();
		textEncryptor.setPassword("unimas");
		return textEncryptor.decrypt(text);
		
		
		
	}
    
    public static void main(String[] args) {
    	//Jasypt  js=new Jasypt();
    	
    	String str=Jasypt.encrypt("hzhz2003");
    	System.out.println(str);
    	//js=new Jasypt();
    	String str3=Jasypt.encrypt("hzhz2003");
    	
    	System.out.println(str3);
    	
    	//ZXUn3/rlXmmexjJw65N81CFjM0i2ffDH
    	//xtgpz7UtDBddyi+GtyamZL5JcLFXvsnN
    	//js=new Jasypt();
        String str2=Jasypt.decrypt(str);
        System.out.println(str2);
        System.out.println(Jasypt.decrypt(str3));
        
    	
    	
    	
	}
	
	
	

}
