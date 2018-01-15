package com.unimas.tlm.service.jfrw.aspect;

import org.apache.log4j.Logger;

import com.unimas.tlm.bean.jfrw.RwListBean;
import com.unimas.tlm.service.jfrw.RwService;
import com.unimas.tlm.service.jfrw.aspect.annotations.RwPointcut;
import com.unimas.web.auth.AuthRealm.ShiroUser;

public class RwHandlerFactory {
	
	protected static Logger logger = Logger.getLogger(RwHandlerFactory.class);
	
	private static final ThreadLocal<RwListBean> local = new ThreadLocal<RwListBean>();
	
	/**
	 * 创建审计对象
	 * @param audit
	 */
	public static void createContext(RwPointcut rpc) {
		RwListBean bean = new RwListBean();
		bean.setStatus(0);
		bean.setRpc(rpc);
		local.set(bean);
	}
	
	/**
	 * 保存完成任务信息
	 */
	public static void save(){
		try {
			RwListBean bean = local.get();
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
	public static void setRwInfo(int rwId, int contentId, ShiroUser user){
		RwListBean bean = local.get();
		if(bean != null){
			bean.setRwId(rwId);
			bean.setContentId(contentId);
			bean.setUserNo(user.getUserNo());
		}
	}

}
