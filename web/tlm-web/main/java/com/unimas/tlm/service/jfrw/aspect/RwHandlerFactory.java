package com.unimas.tlm.service.jfrw.aspect;

import org.apache.log4j.Logger;

import com.unimas.tlm.bean.jfrw.UserFulfilRwBean;
import com.unimas.tlm.service.jfrw.RwService;
import com.unimas.tlm.service.jfrw.aspect.annotations.RwPointcut;

public class RwHandlerFactory {
	
	protected static Logger logger = Logger.getLogger(RwHandlerFactory.class);
	
	private static final ThreadLocal<UserFulfilRwBean> local = new ThreadLocal<UserFulfilRwBean>();
	
	/**
	 * 创建审计对象
	 * @param audit
	 */
	public static void createContext(RwPointcut rpc) {
		UserFulfilRwBean bean = new UserFulfilRwBean();
		bean.setRpc(rpc);
		local.set(bean);
	}
	
	/**
	 * 保存完成任务信息
	 */
	public static void save(){
		try {
			UserFulfilRwBean bean = local.get();
			if(bean != null){
				new RwService().wcrw(bean);
				local.remove();
			}
		} catch (Exception e) {
			logger.error("保存完成任务信息失败！", e);
		}
	}
	
	/**
	 * 设置操作结果
	 * @param success
	 */
	public static void setRwInfo(int id, int contentId){
		UserFulfilRwBean bean = local.get();
		if(bean != null){
			bean.setId(id);
			bean.setContentId(contentId);
		}
	}

}
