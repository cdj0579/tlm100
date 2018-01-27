package com.unimas.txl.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unimas.txl.bean.ConfigBean;
import com.unimas.txl.service.ConfigService;
import com.unimas.web.MenuManage.PageView;
import com.unimas.web.auth.AuthRealm.ShiroUser;
import com.unimas.web.bean.datamodal.AjaxDataModal;
import com.unimas.web.exception.UIException;
import com.unimas.web.utils.PageUtils;

@Controller
@RequestMapping(value="config")
public class ConfigController {

	/**
     * 系统配置页面的请求
     *
     * @return
     */
    @RequestMapping(value="")
    public String config(Model model,HttpServletRequest request) {
    	PageUtils.setPageView(request, PageView.CONFIG);
		return "config/index";
    }
    
    @RequestMapping(value="load")
	@ResponseBody
	public AjaxDataModal load(HttpServletRequest request) {
		try {
			AjaxDataModal dm = new AjaxDataModal(true);
			ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			ConfigBean bean = new ConfigService().load(user);
			dm.put("data", bean);
			return dm;
		} catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("加载配置信息失败！", e);
			}
			return uiex.toDM();
		}
	}
    
    @RequestMapping(value="save",method = RequestMethod.POST)
    @ResponseBody
    public AjaxDataModal save(HttpServletRequest request) {
    	try {
    		int zhouqi = PageUtils.getIntParamAndCheckEmpty(request, "zhouqi", "错误的分配周期！");
    		int shichang = PageUtils.getIntParamAndCheckEmpty(request, "shichang", "错误的关注时限！");
    		int gzShangxian = PageUtils.getIntParamAndCheckEmpty(request, "gzShangxian", "错误的关注上限！");
    		int shangxian = PageUtils.getIntParamAndCheckEmpty(request, "shangxian", "错误的录入上限！");
			ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			new ConfigService().save(zhouqi, shichang, gzShangxian, shangxian, user);
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("保存配置信息失败！", e);
			}
			return uiex.toDM();
		}
    }
	
}
