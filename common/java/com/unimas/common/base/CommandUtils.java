package com.unimas.common.base;

//import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;

import org.apache.log4j.Logger;


/**
 *	功能说明：调用shell用的工具类
 *	 
 * @author cdj
 *
 */
public class CommandUtils {
	protected static final Logger log = Logger.getLogger(CommandUtils.class);
	/**
	 *	调用shell,返回一个打印信息 
	 * @param sh
	 * @return
	 */
	public static String exeShell(String sh) throws Exception{
		String result = "";
		Process process=null;
		InputStreamReader ir =null;
		LineNumberReader input =null;
		try {
			process = Runtime.getRuntime().exec(sh);
			ir = new InputStreamReader(process.getInputStream());
			input = new LineNumberReader(ir);
			String line;
			while ((line = input.readLine ())  != null) {
				result += line;
			}
		} catch (java.io.IOException e) {
			log.error(e + "\t\n freeMemory:" + Runtime.getRuntime().freeMemory()
					+ "\t\n totalMemory:" + Runtime.getRuntime().totalMemory()
					+ "\t\n maxMemory" + Runtime.getRuntime().maxMemory());
			throw new Exception("错误信息：shell调用时发生异常 - 系统过于繁忙或系统资源不足!");
		}finally{
			//关闭流
			closeReader(input);
			closeReader(ir);
			if (process != null) {
				process.destroy();
				process = null;
			}
		}
		return result;
	}
	// 关闭字符流
	public static void closeReader(Reader reader) throws Exception {
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				log.error(e.getMessage());
				throw new Exception (e);
			}
		}
	}
	public static void main(String[] args){
		try {
			String result = CommandUtils.exeShell("aaa");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
		}
	}
}
