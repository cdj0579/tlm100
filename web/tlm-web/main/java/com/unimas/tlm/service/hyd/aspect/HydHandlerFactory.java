package com.unimas.tlm.service.hyd.aspect;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;

import com.unimas.tlm.bean.HydListBean;
import com.unimas.tlm.service.hyd.HydService;
import com.unimas.tlm.service.hyd.aspect.annotations.HydPointcut;
import com.unimas.web.auth.AuthRealm.ShiroUser;

public class HydHandlerFactory {
	
	protected static Logger logger = Logger.getLogger(HydHandlerFactory.class);
	
	private static final ThreadLocal<HydListBean> local = new ThreadLocal<HydListBean>();
	
	/**
	 * 创建审计对象
	 * @param audit
	 */
	public static void createContext(HydPointcut hpc) {
		HydListBean bean = new HydListBean();
		bean.setHpc(hpc);
		bean.setRule(hpc.type().value());
		ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		bean.setUser(user);
		local.set(bean);
	}
	
	/**
	 * 保存活跃度记录信息
	 */
	public static void save(){
		try {
			HydListBean bean = local.get();
			if(bean != null){
				new HydService().saveHydInfo(bean);
				local.remove();
			}
		} catch (Exception e) {
			logger.error("保存活跃度记录信息失败！", e);
		}
	}

}
