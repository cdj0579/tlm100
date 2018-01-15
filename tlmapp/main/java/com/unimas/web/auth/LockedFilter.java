package com.unimas.web.auth;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.web.servlet.AdviceFilter;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.util.WebUtils;

public class LockedFilter extends AdviceFilter {  
	
    @Override  
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {  
       //返回false将中断后续拦截器链的执行
        ShiroHttpServletRequest req = (ShiroHttpServletRequest)request;
        HttpSession session = req.getSession();
        Object locked = session.getAttribute("locked");
        String requestURI = req.getRequestURI();
        if(locked != null && !requestURI.endsWith("system/lock")){
        	WebUtils.saveRequest(request);
        	WebUtils.issueRedirect(request, response, "system/lock");
        	return false;
        } else {
        	return true;  
        }
    }  
    @Override  
    protected void postHandle(ServletRequest request, ServletResponse response) throws Exception {  
    }  
    @Override  
    public void afterCompletion(ServletRequest request, ServletResponse response, Exception exception) throws Exception {  
    }  
}
