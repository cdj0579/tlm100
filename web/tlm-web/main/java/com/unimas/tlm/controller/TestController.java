package com.unimas.tlm.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * <p>描述: 测试权限的控制器</p>
 * @author hxs
 * @date 2016年12月29日 上午10:25:17
 */
@Controller
public class TestController {
	
	@RequestMapping(value="/unauthorized")
    public String unauthorized(HttpServletRequest request) {
        return  "unauthorized";
    }
	
	@RequestMapping(value="/success")
	@RequiresRoles("admin,super")
    public String success(HttpServletRequest request) {
        return  "success";
    }
	
	@RequestMapping(value="/user")
	@RequiresPermissions(value="user:view")
    public String user(HttpServletRequest request) {
		Subject subject = SecurityUtils.getSubject();
		System.out.println(subject.getPrincipal());
		System.out.println(subject.getPrincipals().getPrimaryPrincipal());
        return  "user";
    }
	
	@RequestMapping(value="/query")
	@RequiresPermissions(value="query:view")
    public String query(HttpServletRequest request) {
        return  "query";
    }
	
	@RequestMapping(value="/home")
    public String home(HttpServletRequest request) {
        return  "home";
    }
	
	@RequestMapping(value="/superuser")
	@RequiresPermissions(value="super:view")
    public String superuser(HttpServletRequest request) {
		Subject subject = SecurityUtils.getSubject();
		subject.checkRole("super");
        return  "superuser";
    }
	
	@RequestMapping(value="/getData")
	@ResponseBody
    public Map<String, Object> getData(HttpServletRequest request) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("success", true);
		List<Map<String, String>> rows = Lists.newArrayList();
		Map<String, String> row = Maps.newHashMap();
		row.put("name", "XM");
		row.put("label", "姓名");
		rows.add(row);
		row.put("name", "SFZH");
		row.put("label", "身份证号");
		rows.add(row);
		map.put("rows", rows);
        return  map;
    }
	
	@RequestMapping(value="/putData", method=RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> putData(HttpServletRequest request) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("success", true);
		List<Map<String, String>> rows = Lists.newArrayList();
		Map<String, String> row = Maps.newHashMap();
		row.put("name", "XM");
		row.put("label", "姓名");
		rows.add(row);
		row.put("name", "SFZH");
		row.put("label", "身份证号");
		rows.add(row);
		map.put("rows", rows);
        return  map;
    }
	
	@RequestMapping(value="/getError")
	@ResponseBody
    public Map<String, Object> testError(HttpServletRequest request) throws Exception {
		throw new Exception("测试下错误处理");
    }

}
