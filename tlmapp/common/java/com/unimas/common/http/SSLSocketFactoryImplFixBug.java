package com.unimas.common.http;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 * 描述: ssl请求不能设置请求的protocol
 *  <i>jdk的异常</i>
 *
 * 作者:攻心小虫 <br/>
 * 创建时间:2011-12-24<br/>
 * 版本号:1.0<br/>
 * 版权所有:杭州合众信息工程有限公司
 */
public class SSLSocketFactoryImplFixBug extends SSLSocketFactory {

	private SSLSocketFactory ssf;
	private String protocol;

	public SSLSocketFactoryImplFixBug(SSLSocketFactory ssf,String protocol){
		this.ssf = ssf;
		this.protocol = protocol;
	}

	public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
		if(s instanceof SSLSocket){
			((SSLSocket)s).setEnabledProtocols(new String[]{protocol});
		}
		return ssf.createSocket(s, host, port, autoClose);
	}

	public String[] getDefaultCipherSuites() {
		return ssf.getDefaultCipherSuites();
	}

	public String[] getSupportedCipherSuites() {
		return ssf.getSupportedCipherSuites();
	}

	public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
		Socket s = ssf.createSocket(host, port);
		if(s instanceof SSLSocket){
			((SSLSocket)s).setEnabledProtocols(new String[]{protocol});
		}
		return s;
	}

	public Socket createSocket(InetAddress host, int port) throws IOException {
		return ssf.createSocket(host, port);
	}

	public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException, UnknownHostException {
		return ssf.createSocket(host, port,localHost,localPort);
	}

	public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
		return ssf.createSocket(address, port,localAddress,localPort);
	}
}