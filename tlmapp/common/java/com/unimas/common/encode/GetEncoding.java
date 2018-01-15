package com.unimas.common.encode;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetEncoding {
	public static  String getEncoding(InputStream in) throws IOException {
	       String encode="UTF-8";
	       String strTmp=null;
	       byte b[]=null;
	       String regExp="gb2312|GB2312|GBK|gbk|utf-8|UTF-8|utf8|UTF8";
	       int contentLength=in.available();
	       if(contentLength>1000){
	        contentLength=1000;
	        b=new byte[1000];
	       }
	       else
	        b=new byte[contentLength];
	       in.read(b,0,contentLength);
	       strTmp=new String(b);
	       Pattern p;
	       Matcher m;
	       p=Pattern.compile(regExp);
	       m=p.matcher(strTmp);
	       if(m.find())
	        return m.group();
	       return encode;
	    }
	
	public static  String getStringEncoding(String strTmp) throws IOException {
	       String encode="UTF-8";
	       String regExp="gb2312|GB2312|GBK|gbk|utf-8|UTF-8|utf8|UTF8";
	       Pattern p;
	       Matcher m;
	       p=Pattern.compile(regExp);
	       m=p.matcher(strTmp);
	       if(m.find())
	        return m.group();
	       return encode;
	}
	public static  String getByteEncoding(byte[] b) throws IOException {
		   String encode="UTF-8";
	       String strTmp=null;
	       String regExp="gb2312|GB2312|GBK|gbk|utf-8|UTF-8|utf8|UTF8";
	       strTmp=new String(b);
	       Pattern p;
	       Matcher m;
	       p=Pattern.compile(regExp);
	       m=p.matcher(strTmp);
	       if(m.find())
	        return m.group();
	       return encode;
	}
}
