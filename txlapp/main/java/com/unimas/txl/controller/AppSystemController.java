package com.unimas.txl.controller;


import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unimas.common.date.DateUtils;
import com.unimas.txl.service.AppIndexService;
import com.unimas.txl.service.user.UserService;
import com.unimas.web.auth.AuthRealm.ShiroUser;
import com.unimas.web.bean.datamodal.AjaxDataModal;
import com.unimas.web.exception.UIException;
import com.unimas.web.utils.PageUtils;

/**
 * <p>描述: 系统请求控制器</p>
 * @author hxs
 * @date 2016年12月29日 上午10:24:18
 */
@Controller
@RequestMapping(value="/app")
public class AppSystemController {
	@Autowired
	UserService service;
	@Autowired
	AppIndexService indexService;
	/**
     * 访问主页面的请求
     *
     * @return
     */
    @RequestMapping(value="/index")
    public String index(Model model,HttpServletRequest request) {
    	return "app/index";
    }
    
    @RequestMapping(value="/")
    public String indexRoot(Model model,HttpServletRequest request) {
    	return "app/index";
    }
    
    /**
     * 取得登录页面
     * 如果已经登陆则直接跳转至主页
     *
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(HttpServletRequest request) {
    	return "app/login";
    }
    
    /**
     * 使用shiro后登录失败才会跳转到这里
     *
     * @param userName 登录用户的用户名
     *
     *
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String handleLogin(HttpServletRequest request,@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName,@RequestParam(FormAuthenticationFilter.DEFAULT_PASSWORD_PARAM) String password) {
    	return "app/login";
    }
	
    @RequestMapping(value="setPwd",method = RequestMethod.GET)
    public String loadSetPwd(HttpServletRequest request) {
    	return "app/set_pwd";
    }
    
    @RequestMapping(value="updatePwd",method = RequestMethod.POST)
    @ResponseBody
    public Object updatePwd(HttpServletRequest request) {
    	try {
    		String password = PageUtils.getParam(request, "old_password", "");
    		String newPassword = PageUtils.getParam(request, "password", "");
    		ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
    		service.updatePwd(password, newPassword, user);
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException(e.getMessage(), e);
			}
			return uiex.toDM();
		}
    }
	
    @RequestMapping(value="getOneLianxiren",method = RequestMethod.POST)
    @ResponseBody
    public Object getOneFenpeiLianxiren(HttpServletRequest request) {
    	try {
    		String fpId = PageUtils.getParam(request, "fpId","0");
    		ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
    		Map<String, Object> data =indexService.queryOneFenPeiLianxiren(user.getJgId(),user.getUserId()+"", fpId);
    		AjaxDataModal dm = new AjaxDataModal(true);
    		if( data == null){
    			dm.put("over" , true );
    		}else{
    			dm.put("over" , false );
    			dm.putAll(data);
    		}
			return dm;
		}  catch (Exception e) {
			e.printStackTrace();
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException(e.getMessage(), e);
			}
			return uiex.toDM();
		}
    }
    
    @RequestMapping(value="getgGxORGzLianxiren",method = RequestMethod.POST)
    @ResponseBody
    public Object getgGxORGzLianxiren(HttpServletRequest request) {
    	try {
    		String isGx = PageUtils.getParam(request, "isGx","gx");
    		ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
    		List<Map<String, Object>> data =indexService.queryGzOrGxLianxiren(user.getJgId(),user.getUserId()+"", "gx".equals(isGx));
    		AjaxDataModal dm = new AjaxDataModal(true);
    		dm.put("rows",data);
			return dm;
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException(e.getMessage(), e);
			}
			return uiex.toDM();
		}
    }
    
    @RequestMapping(value="saveBeizhu",method = RequestMethod.POST)
    @ResponseBody
    public Object saveBeizhu(HttpServletRequest request) {
    	try {
    		int lxrId = PageUtils.getIntParam(request, "lxrId");
    		String beizhu = PageUtils.getParam(request, "beizhu","");
    		String type = PageUtils.getParam(request, "type","gz");
    		ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
    		int jgId = user.getJgId();
    		if("gz".equals(type)){
    			indexService.updateLianxirenBeizhu(lxrId, beizhu);
    			int glId = indexService.isExistsInfo(lxrId, user.getUserId(), false);
    			if(glId == -1){
    				indexService.saveGuanZhuInfo(-1,user.getUserId(), lxrId, DateUtils.formatDateToHMS(new Date()),jgId);
    			}
    		}else if("gx".equals(type)){
    			String time = null;
    			int glId = indexService.isExistsInfo(lxrId, user.getUserId(), true);
    			if(glId == -1){
    				time = DateUtils.formatDateToHMS(new Date());
    			}
        		indexService.saveQianYueInfo(glId,user.getUserId(), lxrId,beizhu,time,jgId);
    		}
    		AjaxDataModal dm = new AjaxDataModal(true);
			return dm;
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException(e.getMessage(), e);
			}
			return uiex.toDM();
		}
    }
    
    
    
}
