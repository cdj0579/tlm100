package com.unimas.common.log;

import org.apache.log4j.Logger;

public class LoggerManger {

	/**
	 * 获取日志文件输出
	 * @return
	 */
	public static Logger getLogger(){
		return Logger.getLogger("UMSLogger");
	}
	
	/**
	 * 获取控制台输出
	 * @return
	 */
	public static Logger getConsole(){
		return Logger.getLogger("ConsoleLogger");
	}
	
	/**
	 * 获取操作审计输出
	 * @return
	 */
	public static Logger getOperate(){
		return Logger.getLogger(LoggerManger.class);
	}

}
