package com.unimas.common.base;

import org.apache.log4j.Logger;

import com.unimas.common.cmd.CMDHandler;
import com.unimas.common.cmd.CMDOutputMode;
import com.unimas.common.cmd.CMDState;
import com.unimas.common.cmd.Command;


public class CommandTool {
	
	private static Logger logger = Logger.getLogger(CommandTool.class);
	
	/**
	 * 命令执行，成功返回成功信息，失败则抛出异常
	 * @param command
	 * @param args
	 * @return
	 * @throws UIException
	 */
	public static String exec(String command,String ...args) throws Exception{
		String param_cmd = command + " ";
		if(args != null){
			for(String tmp : args){
				param_cmd += tmp + " ";
			}
		}
		logger.debug("执行命令："+param_cmd);
		String charset = "gbk";//读取输出信息所用的字符集
		Command cmd = new Command();
		cmd.append("/bin/sh").append("-c");
		cmd.append(param_cmd);
		CMDHandler cmdhdl = new CMDHandler(cmd).setOutputMode(CMDOutputMode.HTML);
	    if(charset!= null){
	    	cmdhdl = cmdhdl.setReaderCharset(charset);
	    }
	    cmdhdl.exec();
		if(CMDState.SUCCESS.equals(cmdhdl.getStatus())){
			return cmdhdl.getOutput()+cmdhdl.getError();
		} else {
			throw new Exception(cmdhdl.getError());
		}
	}
	
	public static void main(String[] args) throws Exception {
		exec("/opt/unimas/nginx/xx/deal_sss", "r");
	}

}
