package com.unimas.web.utils;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.unimas.common.util.StringUtils;
import com.unimas.tlm.exception.UIException;
import com.unimas.tlm.service.MenuManage.PageView;
import com.unimas.web.auth.AuthRealm.ShiroUser;

public class PageUtils {
	
	/**
	 * 设置页面的基础信息，存储在request.attribute中：<br/>
	 * name: 页面名 <br/>
	 * title: 页面标题 <br/>
	 * desc: 页面描述 <br/>
	 * navs: 页面导航 <br/>
	 */
	public static void setPageView(HttpServletRequest request, PageView view) {
		request.setAttribute("title", view.title());
    	request.setAttribute("desc", view.desc());
    	request.setAttribute("navs", view.navs());
    	request.setAttribute("name", view.name());
	}
	
	/**
	 * 获取parameter中的参数并检查参数是否为空，若为空则抛出异常<br/>
	 *  当参数为null对象、""、" "、"null"、"NULL"，都表示为空
	 * @param request
	 * @param paramName
	 * @param warnMsg        参数为空时的提示信息
	 * @return
	 * @throws UIException
	 */
	public static String getParamAndCheckEmpty(HttpServletRequest request, String paramName, String warnMsg) throws UIException {
		String value = getParam(request, paramName, null);
		if(!StringUtils.isNotEmpty(value)){
			warnMsg = warnMsg==null?"参数["+paramName+"]不能为空！":warnMsg;
			throw new UIException(warnMsg);
		}
		return value;
	}
	
	/**
	 * 获取parameter中的参数并转换为Int，若参数不是数字则抛出异常<br/>
	 * @param request
	 * @param paramName
	 * @param warnMsg        参数不是数字时的提示信息
	 * @return
	 * @throws UIException
	 */
	public static int getIntParamAndCheckEmpty(HttpServletRequest request, String paramName, String warnMsg) throws UIException {
		int value = getIntParam(request, paramName);
		if(value == -1){
			warnMsg = warnMsg==null?"参数["+paramName+"]不是正确的数字！":warnMsg;
			throw new UIException(warnMsg);
		}
		return value;
	}
	
	/**
	 * 获取parameter中的参数
	 * @param request
	 * @param paramName
	 * @param paramName
	 * @return
	 */
	public static String getParam(HttpServletRequest request, String paramName, String defaultValue) {
		if(request == null || paramName == null) return null;
		String value = request.getParameter(paramName);
		if(defaultValue != null && !StringUtils.isNotEmpty(value)){
			value = defaultValue;
		}
		return value;
	}
	
	/**
	 * 获取parameter中的参数，并转换为Int，当param不存在或param不是Int类型时返回-1
	 * @param request
	 * @param paramName
	 * @return
	 */
	public static int getIntParam(HttpServletRequest request, String paramName) {
		int value = -1;
		if(request != null || paramName != null) {
			try {
				value = Integer.parseInt(getParam(request, paramName, null));
			} catch(Exception e){}
		}
		return value;
	}
	
	/**
	 * 获取parameter中的参数，并转换为boolean，当param不存在或param不是Int类型时返回false
	 * @param request
	 * @param paramName
	 * @return
	 */
	public static boolean getBooleanParam(HttpServletRequest request, String paramName) {
		boolean value = false;
		if(request != null || paramName != null) {
			try {
				value = Boolean.parseBoolean(getParam(request, paramName, null));
			} catch(Exception e){}
		}
		return value;
	}
	
	/**
	 * 获取系统访问URL
	 * @param request
	 * @return
	 */
	public static String getSystemUrl(HttpServletRequest req){
		return req.getScheme()+"://"+req.getLocalAddr()+":"+req.getLocalPort()+getContextPath(req);
	}
	
	/**
	 * 获取系统上下文根，如："/", "/las/"
	 * @param req
	 * @return
	 */
	public static String getContextPath(HttpServletRequest req){
		String context = req.getContextPath();
		if(!"/".equals(context)){
			context += "/";
		}
		return context;
	}
	
	public static String getBasePath(HttpServletRequest req){
		/*Object base = req.getAttribute("base");
		if(base != null){
			return (String)base;
		} else {*/
			return getContextPath(req);
		/*}*/
	}
	
	public static void initPageConfig(HttpServletRequest req, String title, String theme, String layout
			,Boolean showPageToolbar, String footerMsg){
		HashMap<String, Object> pageConfig = new HashMap<String, Object>();
		pageConfig.put("title", "跳龙门教学网");
		pageConfig.put("basePath", getBasePath(req));
		pageConfig.put("theme", theme!=null?theme:"default");
		pageConfig.put("layout", layout!=null?layout:"layout3");
		pageConfig.put("showPageToolbar", showPageToolbar!=null?showPageToolbar:true);
		pageConfig.put("footerMsg", footerMsg!=null?footerMsg:"杭州超骥信息科技有限公司.");
		req.setAttribute("page", pageConfig);
	}
	
	public static void initUserConfig(HttpServletRequest req, Subject subject){
		ShiroUser shiroUser = null;
		if(subject == null){
			shiroUser = new ShiroUser(-1, "-1", "guest", "游客", "guest");
		} else {
			shiroUser = (ShiroUser)subject.getPrincipals().getPrimaryPrincipal();
		}
		req.setAttribute("user", shiroUser);
	}
	
	public static void init(HttpServletRequest req){
		initUserConfig(req, SecurityUtils.getSubject());
		initPageConfig(req, null, null, null, null, null);
	}
	
	public static void initGuest(HttpServletRequest req){
		initUserConfig(req, null);
		initPageConfig(req, null, null, null, null, null);
	}

}
