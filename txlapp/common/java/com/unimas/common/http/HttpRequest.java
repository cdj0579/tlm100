package com.unimas.common.http;

import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URLConnection;


/**
 * 描述:  http请求
 *
 * 项目:平台管理系统<br/>
 * 作者:攻心小虫 <br/>
 * 创建时间:2011-12-24<br/>
 * 版本号:1.0<br/>
 * 版权所有:杭州合众信息工程有限公司
 */
public class HttpRequest extends Request {

	protected void setRequestParam(URLConnection conn,String method,int timeout) {
		conn.setDoOutput(true);    
		conn.setDoInput(true);    
		conn.setUseCaches(false);
		conn.setConnectTimeout(timeout);
		if(method != null && method.trim().length() != 0){
			try {
				((HttpURLConnection)conn).setRequestMethod(method);
			} catch (ProtocolException e) {
				throw new RuntimeException("请求方式错误，无法请求  \t请求方式:"+method);
			}
			
		}
	}
	
	protected void setRequestParam(URLConnection conn,String method) {
		setRequestParam(conn, method,5*1000);	//默认超时5秒
	}

	protected void closeRequest(URLConnection conn) {
		//http请求不需要关闭流  服务器会主动关闭请求
	}
	
	
	
	
}