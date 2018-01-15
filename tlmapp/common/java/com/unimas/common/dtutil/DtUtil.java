package com.unimas.common.dtutil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class DtUtil {
	/**
	 * 解析请求中详细字段条件信息
	 * @param req
	 * @return
	 */
	public static Map<String, Map<String, String>> getConditions(HttpServletRequest req) {
		int index = 0;
		String columnName = null;
		Map<String, Map<String, String>> conditions = new HashMap<String, Map<String, String>>();
		while((columnName = req.getParameter("columns["+index+"][data]")) != null){
			Map<String, String> columnMap = new HashMap<String, String>();
			columnMap.put("name", columnName);
			String alias = req.getParameter("columns["+index+"][name]");
			columnMap.put("alias", alias);
			columnMap.put("searchable", req.getParameter("columns["+index+"][searchable]"));
			columnMap.put("orderable", req.getParameter("columns["+index+"][orderable]"));
			columnMap.put("search_value", req.getParameter("columns["+index+"][search][value]"));
			columnMap.put("search_regex", req.getParameter("columns["+index+"][search][regex]"));
			conditions.put(String.valueOf(index), columnMap);
			index++;
		}
		return conditions;
	}
	/**
	 * 解析请求中排序条件信息
	 * @param req
	 * @return
	 */
	public static List<Map<String, String>> getOrderConditions(HttpServletRequest req) {
		int index = 0;
		String column = null;
		List<Map<String, String>> conditions = new ArrayList<Map<String, String>>();
		while((column = req.getParameter("order["+index+"][column]")) != null){
			Map<String, String> orderMap = new HashMap<String, String>();
			orderMap.put("column", column);
			orderMap.put("dir", req.getParameter("order["+index+"][dir]"));
			conditions.add(orderMap);
			index++;
		}
		return conditions;
	}
	
	

}
