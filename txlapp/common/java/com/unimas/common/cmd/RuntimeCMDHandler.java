package com.unimas.common.cmd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

/**
 * 命令执行工具
 * @author cj
 *
 */
public class RuntimeCMDHandler {	    
	static Logger consoleLogger = Logger.getLogger("ConsoleLogger");//终端调试输出
	private Process proc;

	/**
	 * 执行命令
	 * @param cmd
	 * @return
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws UIException
	 */
	public boolean exec(String cmd, boolean verbose, String verboseOutFile) throws InterruptedException, IOException{
		proc = Runtime.getRuntime().exec(cmd);
        if(proc.waitFor() == 0){
        	if(verbose){//罗嗦模式下会输出日志文件
        		File _vf = new File(verboseOutFile);
        		if(!_vf.exists()){
        			_vf.createNewFile();
        		}
	        	FileWriter writer = new FileWriter(_vf, true);
	        	InputStreamReader isr = new InputStreamReader(proc.getInputStream());
	        	BufferedReader br = new BufferedReader(isr);
	        	String line = null;
	    		while((line = br.readLine()) != null){
	    			writer.write(line+"\r\n");
	    			writer.flush();
	    		}
	    		writer.close();
				writer = null;
        	}
        	return true;
        }
        return false;
	}
	
	public Process getProc(){
		return proc;
	}
	
	public void close(){
		try{
			proc.getInputStream().close();
		}catch(Exception e){}
		try{
			proc.getErrorStream().close();
		}catch(Exception e){}
		proc.destroy();
	}
	
	public String getOutputInfo() throws Exception{
		return this.getInfo(proc.getInputStream());
	}
	
	public String getErrorInfo() throws Exception{
		return this.getInfo(proc.getErrorStream());
	}
	
	private String getInfo(InputStream in)throws Exception{
    	InputStreamReader isr = new InputStreamReader(in);
    	BufferedReader br = new BufferedReader(isr);
    	String line = null;
    	StringBuffer result = new StringBuffer();
		while((line = br.readLine()) != null){
			result.append(line);
		}
		return result.toString();
	}
	public static void main(String[] args) {
		//CMDHandler _ch = new CMDHandler();
		try {
			//尝试删除临时zip输出目录
			String[] c = {"/bin/sh","-c","ps -fe |grep sshd"};
			//_ch.exec("ps -fe |grep sshd",false,null);
			//consoleLogger.debug(_ch.getOutputInfo());
			Process ps = Runtime.getRuntime().exec(c);
			RuntimeCMDHandler _ch = new RuntimeCMDHandler();
			String output = _ch.getInfo(ps.getInputStream());
			System.out.println(output);
		} catch (Exception e1) {
			//do nothing
		}
	}
}
