package com.unimas.jdbc.entry;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;



/**
 * 服务调用日志的记录程序
 * @author cj
 *
 */
public class CallingLogger {
	static Logger CallingLogger = Logger.getLogger("CallingLogger");
	static Logger consoleLogger = Logger.getLogger("ConsoleLogger");
    /**
     * 将调用日志上下文根中的信息放入MDC，并调用log4j记录信息。
     * @param loggingContext
     */
    public static void log(CallingLogging _ctx){

		MDC.put("Reg_ID", _ctx.getReg_ID() == null? "":_ctx.getReg_ID());
		MDC.put("User_ID",_ctx.getUser_ID() == null? "":_ctx.getUser_ID());
		MDC.put("Organization",_ctx.getOrganization()== null? "":_ctx.getOrganization());
		MDC.put("Organization_ID",_ctx.getOrganization_ID() == null? "":_ctx.getUser_Name());
		MDC.put("User_Name", _ctx.getUser_Name()==null?"": _ctx.getUser_Name());
		MDC.put("Operate_Time",  _ctx.getOperate_Time()== null?getCurrentTime():_ctx.getOperate_Time());
		MDC.put("Terminal_ID", _ctx.getTerminal_ID() == null? "":_ctx.getTerminal_ID());

		MDC.put("Operate_Type",_ctx.getOperate_Type());
		MDC.put("Operate_Result",_ctx.getOperate_Result() == null? "":_ctx.getOperate_Result());
		MDC.put("Error_Code",_ctx.getError_Code() == null? "":_ctx.getError_Code());
		MDC.put("Operate_Name",_ctx.getOperate_Name() == null? "":_ctx.getOperate_Name());
		MDC.put("Operate_Condition", _ctx.getOperate_Condition() == null? "":_ctx.getOperate_Condition());
		MDC.put("BZ1",_ctx.getBZ1() == null? "":_ctx.getBZ1());
		MDC.put("BZ2", _ctx.getBZ2() == null? "":_ctx.getBZ2());
		MDC.put("BZ3", _ctx.getBZ3() == null?"":_ctx.getBZ3());
		MDC.put("BZ4", _ctx.getBZ4() == null?"":_ctx.getBZ4());
		MDC.put("BZ5", _ctx.getBZ5() == null?"":_ctx.getBZ5());
		MDC.put("BZ6", _ctx.getBZ6() == null?"":_ctx.getBZ6());
		MDC.put("BZ7", _ctx.getBZ7() == null?"":_ctx.getBZ7());
		MDC.put("BZ8", _ctx.getBZ8() == null?"":_ctx.getBZ8());
		MDC.put("BZ9", _ctx.getBZ9() == null?"":_ctx.getBZ9());
		MDC.put("BZ10", _ctx.getBZ10() == null?"":_ctx.getBZ10());
		CallingLogger.info("");
    }
    /**获取当前时间
     * @return
     */
    private static String getCurrentTime(){
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String nowtime = formatter.format(curDate);
		return nowtime;
    }
    
    private static String getQueryValue(){
    	return "";
    }
    
    private static String getResultValue(){
    	return "";
    }
    
 
	public static void main(String args[]){
	}


	
}
