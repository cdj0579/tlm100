package com.unimas.tlm.exception;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.common.collect.Maps;
import com.unimas.common.util.StringUtils;
import com.unimas.tlm.Constant;
import com.unimas.tlm.bean.datamodal.AjaxDataModal;

/**
 * <p>描述: 用来包装从UI层面上抛出的异常,提供输出为json的方法toJson()
 * 默认转化的json格式为
 * <pre>
 * {
 *     success:false,
 *     errors:{
 *     		exception:"错误信息",
 *    	 	errcode:"12***"
 *     }
 * }
 * </pre>
 * </p>
 * @author hxs
 * @date 2016年12月29日 上午10:28:27
 */
public class UIException extends ServletException {
	
	public static final Logger logger = Logger.getLogger(UIException.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1782304898435402388L;
	
	/**
	 * 错误信息
	 */
	protected String errinfo;
	
	/**
	 * 起因信息
	 */
	protected String causeInfo;
	
	/**
	 * 错误码
	 */
	protected int errcode = -1;
	
	/**
	 * 错误类型
	 */
	protected String errtype = "common";
	
	private Throwable cause = this;
	
	public UIException(){}
	/**
	 * @param errinfo 错误信息
	 */
	public UIException(String errinfo){
		//起因是它自己
		//super((Throwable)null);
		this.errinfo= errinfo;
	}
	
	public UIException(String errinfo,String errtype){
		//起因是它自己
		//super((Throwable)null);
		this.errinfo = errinfo;
		this.errtype = errtype;
	}
	
	/**
	 * 
	 * @param errinfo 错误信息
	 * @param cause 起因异常
	 */
	public UIException(String errinfo,Throwable cause){
		this(errinfo, -1, cause);
	}
	
	/**
	 * @param errinfo 错误信息
	 * @param errcode 错误代号
	 * @param cause 起因异常
	 */
	public UIException(String errinfo,int errcode){
		//super((Throwable)null);
		this.errinfo= errinfo;
		this.errcode= errcode;
	}	
	
	/**
	 * @param errinfo 错误信息
	 * @param errcode 错误代号
	 * @param cause 起因异常
	 */
	public UIException(String errinfo,int errcode,Throwable cause){
		super(cause);
		this.cause = cause;
		this.errinfo = errinfo;
		if(cause != null && cause.getMessage() != null){
			String msg = cause.getMessage();
			if(StringUtils.isNotEmpty(msg)){
				this.errinfo = msg;
			}
		}
		this.errcode= errcode;
	}	
	
	/**
	 * 错误信息就是起因异常的信息
	 * @param cause 起因异常
	 */
	public UIException(Throwable cause){
		super(cause);
		this.cause = cause;
		this.errinfo= cause.getMessage();		
	}
	
	/**
	 * 错误信息就是起因异常的信息
	 * @param cause 起因异常
	 */
	public UIException(Exception cause){
		super(cause);
		this.cause = cause;
		this.errinfo= cause.getMessage();		
	}
	
	public Throwable getCause(){
		return (this.cause==this)?null:this.cause;
	}
	
	@Override
	public String getMessage() {
		return this.errinfo;
	}
	
	/**
	 * 将此异常转化为Json数据，用于返回给客户端
	 * @return
	 */
	public Map<String, Object> toJson(){
		return toDM();
	}
	
	/**
	 * 将此异常转化为Json数据，用于返回给客户端
	 * @return
	 */
	public AjaxDataModal toDM(){
		logger.error(errinfo, this);
		boolean causeInfoEnabled = "true".equalsIgnoreCase(Constant.CAUSEINFO_ENABLED.getValue());
		if(causeInfoEnabled){
			String causeInfo = getCauseInfo(this);
			errinfo += causeInfo;
		}
		AjaxDataModal result = new AjaxDataModal(false);
		result.put("success", false);
		if(errcode != -1){
			result.put("errcode", errcode);
		}
		if(this.errinfo!= null){
			this.errinfo = this.errinfo.replaceAll("\\\\", "$0$0"); //将错误信息里的单斜杆转化为双斜杆
			this.errinfo = this.errinfo.replaceAll("\"", "\'");
			this.errinfo = this.errinfo.replaceAll(":", "\\:");
			this.errinfo = this.errinfo.replaceAll("\n", "");
			Map<String, Object> errors = Maps.newHashMap();
			result.put("errors", errors);
			//errors.put("exception", UnicodeUtils.toHTMLUnicode(errinfo)); //将中文字符等转为逃脱码(即escaped为unicode)
			errors.put("exception", errinfo);
		}
		return result;
	}
	
	/**
	 * 找出根源！
	 * @param e
	 */
	private String unimasClassCause = "";
	protected String getCauseInfo(Throwable e) {
		StackTraceElement el = e.getStackTrace()[0];
		if(el.getClassName().indexOf("com.unimas.ue")!= -1){
			this.unimasClassCause = (el.getClassName()+"."+el.getMethodName()+"("+el.getFileName()+":"+el.getLineNumber()+")...");
		}
		Throwable cause = e.getCause();
		if(cause == null){
			causeInfo="";
			causeInfo+= (",起因："+this.unimasClassCause+ this.getCauseInfoBySTElement(el));
		}else {
			getCauseInfo(cause);
		}
		return causeInfo;
		
	}
	
	/**获取最终起因信息 java.io.FileOutputStream.open(原始方法)
	 * @param el  StackTraceElement
	 * @return
	 */
	private String getCauseInfoBySTElement(StackTraceElement el){
		String lineNum = el.getLineNumber()< 0? "原始方法":el.getFileName()+":"+el.getLineNumber();
		return (el.getClassName()+"."+el.getMethodName()+"("+lineNum+")");
	}
	
	public void shipToResponse(HttpServletResponse response) throws IOException{
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(this.toJson());
	}
	
}