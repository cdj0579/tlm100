package com.unimas.tlm.exception.resolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class LasCascadeExceptionResolver implements HandlerExceptionResolver {
	
	public static final Logger logger = Logger.getLogger(LasCascadeExceptionResolver.class);
	
	private String filterUrl;
	
	/**
	 * 过滤不处理异常的URL
	 * @param request
	 * @return
	 */
	private boolean isFilterURI(HttpServletRequest request){
    	String uri = request.getRequestURI();
    	String context = request.getContextPath();
    	if(!"/".equals(context)){
    		uri = uri.replaceFirst(context, "");
    	}
    	return uri.startsWith(filterUrl);
	}
	
    @Override  
    public ModelAndView resolveException(HttpServletRequest request,  
            HttpServletResponse response, Object object, Exception exception) {
    	logger.error("请求异常！", exception);
    	ModelAndView mv = null;
    	if(!isFilterURI(request)){
    		if(exception instanceof org.apache.shiro.authz.UnauthorizedException){
    			mv = new ModelAndView("unauthorized");
    		} else {
    			mv = new ModelAndView("errors/500");
    		}
    	}
    	if(mv != null){
    		mv.addObject("exception", exception);
    	}
        return mv;  
    }

	public String getFilterUrl() {
		return filterUrl;
	}

	public void setFilterUrl(String filterUrl) {
		this.filterUrl = filterUrl;
	}  
  
}
