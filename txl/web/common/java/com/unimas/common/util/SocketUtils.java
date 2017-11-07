package com.unimas.common.util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.apache.log4j.Logger;

public class SocketUtils {
	
	public static final Logger logger = Logger.getLogger(SocketUtils.class);
	
	/**
	 * 默认的超时时间
	 */
	private static final int TEST_TIMEOUT = 2000;
	
	public static boolean conect(String host, int port, int timeout) {
		boolean b= false;
		Socket socket = null;
		try {
			if (host == null || host.equals("")) {
				throw new Exception("主机不能为空！");
			}
			String [] hosts = null;
			if(host != null && host.contains(",")){
				hosts = host.split(",");
			} else {
				hosts = new String[]{host};
			}
			for(String h : hosts){
				if (port <= 0) {
					throw new Exception("端口不能为空！");
				}
				socket = new Socket();
				socket.connect(new InetSocketAddress(h, port), timeout);
				socket.close();
				return true;
			}
		} catch (Exception e) {
			logger.debug("测试不通过!", e);
		} finally {
			if(socket != null){
				try { socket.close(); } catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return b;
	}
	
	public static boolean conect(String host, int port) {
		return conect(host, port, TEST_TIMEOUT);
	}

}
