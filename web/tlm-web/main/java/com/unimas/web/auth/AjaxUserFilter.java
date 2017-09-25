package com.unimas.web.auth;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.util.WebUtils;

/**
 * <p>描述: AJAX请求超时发送401错误</p>
 * @author hxs
 * @date 2016年12月29日 下午7:32:22
 */
public class AjaxUserFilter extends UserFilter {
	
	protected static final Logger logger = Logger.getLogger(AjaxUserFilter.class);

	@Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        if(request != null){
            String xmlHttpRequest =  ((ShiroHttpServletRequest) request).getHeader("X-Requested-With");
            if(!StringUtils.isEmpty(xmlHttpRequest) && xmlHttpRequest.equalsIgnoreCase("XMLHttpRequest")) {
                accessDeniedResponse(response);
            } else {
                saveRequestAndRedirectToLogin(request, response);
            }
        }else {
            saveRequestAndRedirectToLogin(request, response);
        }
        return false;
    }

    @Override
	public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
    	/*HttpServletRequest req = (HttpServletRequest)request;
		setBasePath(req);*/
		return super.onPreHandle(request, response, mappedValue);
	}
    
    protected void setBasePath(HttpServletRequest req){
    	String uri = req.getRequestURI();
		String context = req.getContextPath();
		if("/".equals(context)){
			uri = uri.replaceFirst(context, "");
		} else {
			uri = uri.replaceFirst(context+"/", "");
		}
		int lasts = uri.lastIndexOf("/");
		if(lasts > 0){
			uri = uri.substring(0, lasts+1);
		} else if(lasts == -1){
			uri = "";
		}
		String base = "";
		if(!"".equals(uri)){
			String [] paths = uri.split("/");
			for(int i=0;i<paths.length;i++){
				base += "../";
			}
		}
		req.setAttribute("base", base);
    }

	/**
     * Provides a 401 Not Authorized HTTP status code to the client with a
     * custom challenge scheme that the client understands and can respond to.
     *
     * @param response
     * @throws Exception
     */
    private void accessDeniedResponse(ServletResponse response) throws IOException {
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        httpResponse.addHeader("WWW-Authentication", "ACME-AUTH");
        httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
	
}
