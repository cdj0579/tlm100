package com.unimas.common.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ProtocolException;
import java.net.URLConnection;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


/**
 * 描述: https请求带有证书的验证
 * 
 * 项目:平台管理系统<br/>
 * 作者:攻心小虫 <br/>
 * 创建时间:2011-12-24<br/>
 * 版本号:1.0<br/>
 * 版权所有:杭州合众信息工程有限公司
 */
public class HttpsRequest extends Request {

	private SSLContext context;

	/**
	 * 单项认证
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	public HttpsRequest(){
		try {
			setSSLPorxy(null);
		} catch (Exception e) {
			// 不验证证书 不会出现这样的错误
		}
	}

	/**
	 * 双向认证
	 * 
	 * @param certificatePath
	 *            证书路径
	 * @param certificatePwd
	 *            证书密码
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	public HttpsRequest(File certificatePath, String certificatePwd) throws IOException, GeneralSecurityException {
		setSSLPorxy(setCerti(certificatePath, certificatePwd));
	}

	/**
	 * 设置证书
	 * 
	 * @param certificatePath
	 * @param certificatePwd
	 * @return
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	private KeyManager[] setCerti(File certificatePath, String certificatePwd) throws GeneralSecurityException, IOException {
		char[] pwd_char = certificatePwd.toCharArray();
		FileInputStream instream = null;
		try {
			KeyStore trustStore = KeyStore.getInstance("pkcs12");
			KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			instream = new FileInputStream(certificatePath);
			trustStore.load(instream, pwd_char);
			kmf.init(trustStore, pwd_char);
			return kmf.getKeyManagers();
		} finally {
			if (instream != null) {
				instream.close();
			}
		}
	}

	/**
	 * 初始 请求通道
	 * 
	 * @param key
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws UIException
	 */
	private void setSSLPorxy(KeyManager[] key) throws GeneralSecurityException {
		context = SSLContext.getInstance("SSL"); // 暂不支持其他类型认证
		context.init(key, new TrustManager[] { new X509TrustManager() {
			public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
			}

			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		}}, new SecureRandom());

	}

	protected void setRequestParam(URLConnection conn,String method,int timeout) {
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setUseCaches(false);
		conn.setConnectTimeout(timeout);
		HttpsURLConnection c = (HttpsURLConnection) conn;
		c.setSSLSocketFactory(context.getSocketFactory());
		// new SSLSocketFactoryImplFixBug(context.getSocketFactory(), "SSLv3"));
		c.setHostnameVerifier(new HostnameVerifier() {
			public boolean verify(String arg0, SSLSession arg1) {
				return true;
			}
		});
		c.setInstanceFollowRedirects(true);
		try {
			if(method != null && method.trim().length() != 0){
				c.setRequestMethod(method);
			}else{
				c.setRequestMethod("POST");// 默认以post请求
			}
		} catch (ProtocolException e) {
			throw new RuntimeException("请求方式错误，无法请求  \t请求方式:"+method);
		}
	}
	
	protected void setRequestParam(URLConnection conn,String method) {
		setRequestParam(conn, method, 5*1000);
	}

	protected void closeRequest(URLConnection conn) {
		((HttpsURLConnection) conn).disconnect();
	}

}