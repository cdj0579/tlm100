package com.unimas.tlm.service.jfrw.aspect;

import org.apache.log4j.Logger;

import com.unimas.tlm.bean.jfrw.JfListBean;
import com.unimas.tlm.service.jfrw.JfService;
import com.unimas.tlm.service.jfrw.aspect.annotations.JfPointcut;
import com.unimas.web.auth.AuthRealm.ShiroUser;

public class JfHandlerFactory {
	
	protected static Logger logger = Logger.getLogger(JfHandlerFactory.class);
	
	private static final ThreadLocal<JfListBean> local = new ThreadLocal<JfListBean>();
	
	/**
	 * 创建审计对象
	 * @param audit
	 */
	public static void createContext(JfPointcut jpc) {
		JfListBean bean = new JfListBean();
		bean.setJpc(jpc);
		bean.setRule(jpc.type().value());
		local.set(bean);
	}
	
	/**
	 * 保存积分记录信息
	 */
	public static void save(){
		try {
			JfListBean bean = local.get();
			if(bean != null){
				new JfService().saveJfInfo(bean);
				local.remove();
			}
		} catch (Exception e) {
			logger.error("保存积分记录信息失败！", e);
		}
	}
	
	/**
	 * 设置积分的用户
	 * @param success
	 */
	public static void setJfInfo(ShiroUser user){
		JfListBean bean = local.get();
		if(bean != null){
			bean.setUserNo(user.getUserNo());
			bean.setUser(user);
		}
	}

}
