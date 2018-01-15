package com.unimas.common.cmd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import org.apache.log4j.Logger;

import com.unimas.common.encode.UnicodeUtils;



/**
 * 命令处理者
 * @author cj
 * 使用：
 * 	//得到输出信息
 *  //方法1：
 *  String param_cmd = request.getParameter("cmd");
	String charset = request.getParameter("cs");//读取输出信息所用的字符集
	consoleLogger.debug(param_cmd);
	Command cmd = new Command();
	cmd.append("/bin/sh").append("-c");
	cmd.append(param_cmd);
	CMDHandler cmdhdl = new CMDHandler(cmd).setOutputMode(CMDOutputMode.HTML);
    if(charset!= null){
    	cmdhdl = cmdhdl.setReaderCharset(charset);
    }
	try {
		cmdhdl.exec();
		consoleLogger.debug(cmdhdl.getOutput());//直接得到原生态的信息，为了页面展示的需要将会给每个换行加上"<br>"
	} catch (Exception e) {
		throw new UIException(e);
	}
	//方法2：将输出源得到
	Command cmd = new Command();
	cmd.append("/bin/sh").append("-c");
	cmd.append("top -b -n 1");
	CMDHandler cmdhdl = new CMDHandler(cmd);//.setOutputMode(CMDOutputMode.HTML);
	String json;
	try {
		cmdhdl.start();
		//读取输出信息
		BufferedReader rd = new BufferedReader(new InputStreamReader(cmdhdl.getInputStream()));
		json = getTopJson(rd);
	} catch (Exception e) {
		throw new UIException(e);
	}
 */
public class CMDHandler {
	static Logger consoleLogger = Logger.getLogger("ConsoleLogger");
	private List<String> commands;//待执行的命令
	private String verboseFile = null;//输出文件
	private CMDState status;//命令执行状态，有SUCCESS和FAIL两种状态
	private String workingDir = null;//工作目录
	private StringBuilder out = new StringBuilder();//输出信息
	private StringBuilder error = new StringBuilder();//输出信息
	private CMDOutputMode outputMode; 
	private String readerCharset = "UTF-8";//以怎样的字符集读取输出信息
	
	private Process process = null;
	public CMDHandler(Command cmd,String verboseFile,String workingDir){
		this.commands = cmd.getCommand();
		this.verboseFile = verboseFile;
		this.workingDir = workingDir;
	}
	
	public CMDHandler(Command cmd,String verboseFile){
		this.commands = cmd.getCommand();
		this.verboseFile = verboseFile;
	}
	
	public CMDHandler(Command cmd){
		this.commands = cmd.getCommand();
	}
	
	/**
	 * 设置输出模式
	 * @param outputMode
	 * @return
	 */
	public CMDHandler setOutputMode(CMDOutputMode outputMode){
		this.outputMode = outputMode;
		return this;
	}
	
	/**
	 * 设置读取输出信息所用的字符集
	 * @param outputMode
	 * @return
	 */
	public CMDHandler setReaderCharset(String charset){
		this.readerCharset = charset;
		return this;
	}
	
	private void setStatus(CMDState status){
		this.status = status;
	}
	
	public CMDState getStatus(){
		return this.status;
	}
	
	public String getOutput(){
		return out.toString();
	}
	
	public String getError(){
		return error.toString();
	}
	
	/**
	 * 执行命令并处理输出
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public void exec() throws Exception{
		this.start();
		this.dealWithOutput();
	}
	
	/**启动命令
	 * @throws IOException
	 */
	public void start() throws IOException{
		ProcessBuilder pb = new ProcessBuilder(this.commands);
        //设置工作目录，这个工作目录是做什么的，其实我也还不知道
        if(workingDir!= null){
        	pb.directory(new File(workingDir));
            pb.redirectErrorStream(true);
        }
		process = pb.start();
	}
	
	/**得到命令输出信息源头，可以交给其他程序任意处理输出信息
	 * @return
	 */
	public InputStream getInputStream(){
		return process.getInputStream();
	}
	
	/**得到命令标准错误输出信息源头，可以交给其他程序任意处理输出信息
	 * @return
	 * @throws Exception 
	 */
	public InputStream getErrorStream() throws Exception{
		return process.getErrorStream();
	}
	
	private void dealWithOutput() throws Exception{
		if(process == null){
			throw new Exception("命令未运行");
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(),readerCharset));
        FileWriter verboseWriter = null;
        if(this.verboseFile != null){
        	verboseWriter = new FileWriter(verboseFile, true);
        }
        String line = null, previous = null;
        String _endChar = outputMode== CMDOutputMode.HTML?"<br>":"\n";
        while ((line = br.readLine()) != null){
            if (!line.equals(previous)) {
                previous = line;
                if(outputMode== CMDOutputMode.HTML){
                	line = UnicodeUtils.toHTMLUnicode(line);
                }
                out.append(line).append(_endChar);
                consoleLogger.debug(line);
                if(verboseWriter != null){
                	verboseWriter.write(line+"\r\n");
                	verboseWriter.flush();
                }
            }
        }
        if(verboseWriter != null){
        	verboseWriter.close();
            verboseWriter = null;
        }
        this.trantError();
        //检查执行结果
        if (process.waitFor() == 0){
        	this.setStatus(CMDState.SUCCESS);
        }else {
        	this.setStatus(CMDState.FAIL);
        }
	}
	
	private void trantError() throws Exception{
		if(process == null){
			throw new Exception("命令未运行");
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream(),readerCharset));
        String line = null, previous = null;
        String _endChar = outputMode== CMDOutputMode.HTML?"<br>":"\n";
        while ((line = br.readLine()) != null){
            if (!line.equals(previous)) {
                previous = line;
                if(outputMode== CMDOutputMode.HTML){
                	line = UnicodeUtils.toHTMLUnicode(line);
                }
                error.append(line).append(_endChar);
            }
        }
	}
	
	public static void main(String[] args) {
		/*
		Command cmd = new Command().append("/bin/cat").append("/home/temp/cj.txt");
        CMDHandler cmdhdl = new CMDHandler(cmd).setOutputMode(CMDOutputMode.HTML);
        try {
			cmdhdl.exec();
			System.out.println(cmdhdl.getOutput());
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		/*
		ArrayList<String> cmds = new ArrayList<String>();
		cmds.add("/bin/sh");
		cmds.add("-c");
		cmds.add("ps -fe|grep sshd");
		ProcessBuilder pb = new ProcessBuilder(cmds);
        try {
			Process process = pb.start();
			String output = CMDHandler.getInfo(process.getInputStream());
			System.out.println(output);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		String param_cmd = "ps -fe |grep sshd|grep -v grep";
		String charset = "utf8";
		consoleLogger.debug(param_cmd);
		Command cmd = new Command();
		cmd.append("/bin/sh").append("-c");
		cmd.append(param_cmd);
		CMDHandler cmdhdl = new CMDHandler(cmd).setOutputMode(CMDOutputMode.HTML);
	    if(charset!= null){
	    	cmdhdl = cmdhdl.setReaderCharset(charset);
	    }
		try {
			cmdhdl.exec();
			consoleLogger.debug(cmdhdl.getOutput());
		} catch (Exception e) {
			//throw new UIException(e);
		}
	}
}

