package com.unimas.web;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import com.unimas.txl.service.fenpei.FenpeiJob;

/**
 * <p>描述: 系统启动时需要执行的任务</p>
 * @author hxs
 * @date 2017年3月23日 下午4:57:57
 */
public class InitListener extends ContextLoaderListener implements ApplicationContextAware {
	
	private static final Logger logger = Logger.getLogger(InitListener.class);

	@Override
	public WebApplicationContext initWebApplicationContext(ServletContext arg0) {
		//System.setProperty("system.config", SystemConfDao.SYSTEM_CONF_PATH);
		return super.initWebApplicationContext(arg0);
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		try {
			FenpeiJob.startAll();
		} catch (Exception e) {
			logger.error("启动定时任务失败！", e);
		}
	}

}
