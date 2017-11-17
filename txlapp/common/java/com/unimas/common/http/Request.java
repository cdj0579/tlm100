package com.unimas.common.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import com.unimas.common.io.IOUtils;


/**
 * 描述:请求接口
 *
 * 项目:平台管理系统<br/>
 * 作者:攻心小虫 <br/>
 * 创建时间:2011-12-24<br/>
 * 版本号:1.0<br/>
 * 版权所有:杭州合众信息工程有限公司
 */
public abstract class Request {
	
	/**
	 * 普通的文件请求
	 * @param url	请求路径
	 * @param params	请求携带的参数
	 * @param headers	发送头部的信息
	 * @return ResponseBean 自定义结构数据
	 * @throws IOException
	 */
	public ResponseBean request(String url, Map<String, String> params, Map<String, String> headers) throws IOException{
		return request(url, headers, new RequestOutputStream(params),null);
	}
	
	public ResponseBean request(String url,String method) throws IOException{
		return request(url, null, (Map<String, String>)null, method);//(url, null, null,method);
	}
	
	/**
	 * 普通的文件请求
	 * @param url	请求路径
	 * @param params	请求携带的参数
	 * @param headers	发送头部的信息
	 * @param  method	请求方式
	 * @return ResponseBean 自定义结构数据
	 * @throws IOException
	 */
	public ResponseBean request(String url, Map<String, String> params, Map<String, String> headers,String method) throws IOException{
		return request(url, headers, params==null?null:new RequestOutputStream(params),method);
	}
	
	
	/**
	 * 
	 * 描述: 将参数转换为流形式
	 *	<i>非文件上传的数据格式</i>
	 * 项目:统一登录系统 <br/>
	 * 作者:攻心小虫 <br/>
	 * 创建时间:2011-12-6<br/>
	 * 版本号:1.0<br/>
	 * 版权所有:杭州合众信息工程有限公司
	 */
	public class RequestOutputStream extends SetRequestOutputStream{

		private Map<String, String> params;
		public RequestOutputStream(Map<String, String>  params){
			this.params = params;
		}
		public void setOutputStream(OutputStream out) throws IOException {
			String paramcontent = "";
			if(params != null){	//设置参数
				for (String key : params.keySet()) {
					paramcontent +="&"+key+"="+URLEncoder.encode(params.get(key), "utf-8");	//将数据转为utf-8   可能其他系统支持GBK的话 会乱码
				}
				out.write((paramcontent.trim().length()==0?paramcontent:paramcontent.substring(1)).getBytes()); 
				out.flush();
			}
			out.close(); 
		}
		
	}
	
	
	/**
	 *  适合文件上传
	 * @param url		请求路径
	 * @param params	请求携带参数
	 * @param header	请求头
	 * @param input		请求的数据  格式请参照相关说明
	 * @return	ResponseBean 自定义结构数据
	 * @return
	 * @throws IOException
	 */
	public ResponseBean request(String url, Map<String, String> params, Map<String, String> headers,InputStream inputStream,String method) throws IOException{
		String p = "?";	//将参数添加到url格式    文件上传时 参数不能添加到流中，防止破环流的格式
		if(params != null && params.size() != 0){
			for (String key : params.keySet()) {
				p += key+"="+params.get(key)+"&";
			}
		}
		return request(url+p.substring(0,p.length()-1), headers, new FileOutputStream(inputStream),method);
	}
	
	
	/**
	 *  适合文件上传
	 * @param url		请求路径
	 * @param header	请求头
	 * @param input		请求的数据  格式请参照相关说明
	 * @return	ResponseBean 自定义结构数据
	 * @return
	 * @throws IOException
	 */
	public ResponseBean request(String url, Map<String, String> headers,InputStream inputStream,String method) throws IOException{
		return request(url, headers, new FileOutputStream(inputStream),method);
	}
	
	
	
	/**
	 * 描述: 将文件上传的流数据转到代理流中
	 *
	 * 作者:攻心小虫 <br/>
	 * 创建时间:2011-12-6<br/>
	 * 版本号:1.0<br/>
	 * 版权所有:杭州合众信息工程有限公司
	 */
	public class FileOutputStream extends SetRequestOutputStream{

		private InputStream input;
		public FileOutputStream(InputStream input){
			this.input = input;
		}
		public void setOutputStream(OutputStream output) throws IOException {
			if(input == null) return;
			int chart;
			while((chart = input.read())!=-1){
				output.write(chart);
			}
			output.flush();
			output.close(); 
		}
		
	}
	
	
	protected abstract void setRequestParam(URLConnection conn,String method);
	protected abstract void closeRequest(URLConnection conn);
	/**
	 * 发送代理请求
	 * @param url
	 * @param headers
	 * @param setOutput
	 * @return
	 * @throws IOException
	 */
	protected ResponseBean request(String url,Map<String, String> headers,SetRequestOutputStream output,String method) throws IOException{
		url=url.replace(" ", "%20");
		URLConnection conn = new URL(url).openConnection();		//创建连接
		if(headers != null){//设置请求头信息
			for (String key : headers.keySet()) {
				conn.setRequestProperty(key, headers.get(key));
			}
		}
		
		setRequestParam(conn,method);		//设置发送时必要的设置
		try{
			conn.connect();			//创建连接
			if(output != null){
				output.setOutputStream(conn.getOutputStream());	//设置流数据  可能是参数形式的流 可能是文件上传的流
			}
			
			ResponseBean bean = new ResponseBean();
			bean.headers = conn.getHeaderFields();
			
			InputStream inputStream = conn.getInputStream();
			bean.webData =IOUtils.readToByte(inputStream);	//将返回的数据转为byte[]
			return bean;
		}finally{
			closeRequest(conn);	//关闭连接   (http不需要关闭连接)
		}
	}
	
	
	/**
	 * 描述: 处理发送代理时的流数据
	 *  <i>可能是参数流，可能是文件上传流</i>
	 *
	 * 作者:攻心小虫 <br/>
	 * 创建时间:2011-12-6<br/>
	 * 版本号:1.0<br/>
	 * 版权所有:杭州合众信息工程有限公司
	 */
	protected abstract class SetRequestOutputStream{
		public abstract void setOutputStream(OutputStream out) throws IOException;
	}
	
	
	/**
	 * 
	 * 描述: 返回结果的对象
	 *
	 * 作者:攻心小虫 <br/>
	 * 创建时间:2011-12-24<br/>
	 * 版本号:1.0<br/>
	 * 版权所有:杭州合众信息工程有限公司
	 */
	public class ResponseBean{
		private byte[] webData;				//返回内容
		private Map<String, List<String>> headers;	//返回的请求头 
		
		public byte[] getWebData() {
			return webData;
		}
		public Map<String, List<String>> getHeaders() {
			return headers;
		}
		public String toString(){
			return hashCode()+"@[请求都信息:"+headers+" \t返回字节数:"+webData.length+"]";
			
		}
	}

}