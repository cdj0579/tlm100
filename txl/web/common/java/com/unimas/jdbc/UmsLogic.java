package com.unimas.jdbc;

import java.sql.Timestamp;

import org.apache.log4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unimas.jdbc.entry.CallingLogging;



/**
 * 服务调用日志的记录程序
 *
 */
public class UmsLogic {
	
	private  final  static Logger  LOGGER= LoggerFactory.getLogger("sysOperate");
	
    /**
     * 将调用日志上下文根中的信息放入MDC，并调用log4j记录信息。
     * @param _ctx
     */
    public static void log(CallingLogging _ctx){

		MDC.put("Reg_ID", _ctx.getReg_ID() == null? "":_ctx.getReg_ID());
		MDC.put("User_ID",_ctx.getUser_ID() == null? "":_ctx.getUser_ID());
		MDC.put("Organization",_ctx.getOrganization()== null? "":_ctx.getOrganization());
		MDC.put("Organization_ID",_ctx.getOrganization_ID() == null? "":_ctx.getOrganization_ID());
		MDC.put("User_Name", _ctx.getUser_Name()==null?"": _ctx.getUser_Name());
		MDC.put("Operate_Time", _ctx.getOperate_Time()== null?getCurrentTime():_ctx.getOperate_Time());
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
		
		LOGGER.info("");
    }
    /**获取当前时间
     * @return
     */
    private static Timestamp getCurrentTime(){
		return new Timestamp(System.currentTimeMillis());
    }
    

 
	public static void main(String args[]){
		
		//记录操作日志  初始化CallingLogging类  
		//CallingLogging类  中各个属性代表什么意思详见CallingLogging类  
		CallingLogging c = new CallingLogging();
		c.setUser_ID("userID");//用户ID
		c.setUser_Name("adminxxx");//用户名
		c.setOperate_Type(1);//操作类型
		c.setOperate_Result("失败");//操作结果
		c.setError_Code("用户不存在");//失败原因代码
		c.setOperate_Name("用户登录");//功能模块
		c.setOperate_Condition("adminxx");//操作条件
		c.setBZ1("1");//资源id
		c.setBZ2("常住人口信息");//资源名称
		
		//调用UmsLogic类中的log方法将日志入库
		UmsLogic.log(c);
	}


	
}
