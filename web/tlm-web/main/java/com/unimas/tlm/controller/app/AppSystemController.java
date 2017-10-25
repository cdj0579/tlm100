package com.unimas.tlm.controller.app;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unimas.tlm.bean.datamodal.AjaxDataModal;
import com.unimas.tlm.exception.UIException;
import com.unimas.tlm.service.AppService;
import com.unimas.tlm.service.user.UserService;
import com.unimas.web.auth.AuthRealm.ShiroUser;
import com.unimas.web.utils.PageUtils;

/**
 * 
 * @author lusl
 *
 */
@Controller
@RequestMapping(value="/app")
public class AppSystemController {
	@Autowired
	UserService service;
	@Autowired
	AppService appService;
	
	@RequestMapping(value="/")
    public String index(Model model,HttpServletRequest request) {
    	return "redirect:/App/login.html";
    }
	
	@RequestMapping(value = "/login",method = RequestMethod.POST)
    public String handleLogin(HttpServletRequest request,@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName,@RequestParam(FormAuthenticationFilter.DEFAULT_PASSWORD_PARAM) String password) {
		return "redirect:/App/login.html";
    }
	
	@RequestMapping(value = "/register")
	@ResponseBody
    public Object studentRegister(HttpServletRequest request) {
		try {
			String username = PageUtils.getParamAndCheckEmpty(request, "username", "");
			String password = PageUtils.getParamAndCheckEmpty(request, "password", "");
			service.registerStudent(username, password);
			boolean rememberMe = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM);  
            String host = request.getRemoteHost();  
            //构造登陆令牌环  
            UsernamePasswordToken token = new UsernamePasswordToken(username, password.toCharArray(), rememberMe, host);
            //发出登陆请求  
            SecurityUtils.getSubject().login(token); 
            return new AjaxDataModal(true);
		} catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			}else{
				uiex = new UIException("服务器异常！");
			}
			return uiex.toJson();
		}
		
    }

	@RequestMapping(value="updatePwd",method = RequestMethod.POST)
    @ResponseBody
    public Object updatePwd(HttpServletRequest request) {
    	try {
    		UserService service = new UserService();
    		String password = PageUtils.getParamAndCheckEmpty(request, "old_Password", "请输入旧密码！");
    		String newPassword = PageUtils.getParamAndCheckEmpty(request, "password", "请输入新密码！");
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
	
	@RequestMapping(value = "/getStuHeadInfo",method = RequestMethod.POST)
	@ResponseBody
    public Object getStuHeadInfo(HttpServletRequest request) {
		try {
			AjaxDataModal dm = new AjaxDataModal(true);
			ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			dm.put("username",user.getLoginName());
			dm.put("realName",user.getRealName());
			dm.put("tx",user.getTxImg());
            return dm;
		} catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			}else{
				uiex = new UIException("服务器异常！");
			}
			return uiex.toJson();
		}
    }
	
	@RequestMapping(value = "/saveStuHeadImg",method = RequestMethod.POST)
	@ResponseBody
    public Object saveStuHeadImg(HttpServletRequest request) {
		String txImg = PageUtils.getParam(request, "headImg", null);
		try {
			AjaxDataModal dm = new AjaxDataModal(true);
			ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			appService.saveStuTxImg(user.getUserId(),txImg);
			user.setTxImg(txImg);
            return dm;
		} catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			}else{
				uiex = new UIException("服务器异常！");
			}
			return uiex.toJson();
		}
    }
}
