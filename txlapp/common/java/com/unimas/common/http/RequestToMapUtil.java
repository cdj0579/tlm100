package com.unimas.common.http;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class RequestToMapUtil {

	/**
	 * 把request里的parameter封装到map<parameter, getParameter(parameter)>
	 * 
	 * @param request
	 * @return Map
	 */
	public static Map getRequestMap(HttpServletRequest request) {
		Map returnMap = null;
		Map requestMap = request.getParameterMap();
		returnMap = new HashMap();
		returnMap.put("request", request);
		if (!requestMap.isEmpty()) {
			Iterator it = requestMap.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				String value = null;
				value = request.getParameter(key);
				returnMap.put(key, value == null ? "" : value);
			}
		}
		return returnMap;
	}
	
	/**
	 * 获取request对象中的parameters
	 * @param request
	 * @return
	 */
	public static Map<String, Object> parameterMap(HttpServletRequest request) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map<?, ?> parameterMap = request.getParameterMap();
		if (!parameterMap.isEmpty()) {
			Iterator<?> it = parameterMap.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				String value = null;
				value = request.getParameter(key);
				returnMap.put(key, value == null ? "" : value);
			}
		}
		return returnMap;
	}

}
