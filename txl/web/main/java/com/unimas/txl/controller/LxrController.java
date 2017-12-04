package com.unimas.txl.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unimas.txl.bean.LxrBean;
import com.unimas.txl.service.LxrService;
import com.unimas.web.MenuManage.PageView;
import com.unimas.web.auth.AuthRealm.ShiroUser;
import com.unimas.web.bean.datamodal.AjaxDataModal;
import com.unimas.web.bean.datamodal.DataTableDM;
import com.unimas.web.exception.UIException;
import com.unimas.web.utils.PageUtils;

@Controller
@RequestMapping(value="lxrgl")
public class LxrController {
	
	/**
     * 联系人管理页面
     *
     * @return
     */
    @RequestMapping(value="")
    public String lxrglIndex(Model model,HttpServletRequest request) {
    	PageUtils.setPageView(request, PageView.LIANXIREN);
    	Subject subject = SecurityUtils.getSubject();
    	if(subject.hasRole("admin")){
    		return "lxrgl/admin";
    	} else {
    		return "lxrgl/index";
    	}
    }
    
    @RequestMapping(value="list")
	@ResponseBody
	public AjaxDataModal getLists(HttpServletRequest request) {
		try {
			Subject subject = SecurityUtils.getSubject();
			if(subject.hasRole("admin")){
				DataTableDM dm = new DataTableDM(0, true);
	    		String dqId = PageUtils.getParam(request, "dqId", null);
	    		int xuexiaoId = PageUtils.getIntParam(request, "xxId");
	    		int status = PageUtils.getIntParam(request, "status");
	    		int nj = PageUtils.getIntParam(request, "nj");
	    		int bj = PageUtils.getIntParam(request, "bj");
	    		ShiroUser user = (ShiroUser)subject.getPrincipals().getPrimaryPrincipal();
	    		List<LxrBean> list = new LxrService().queryOnAdmin(status, xuexiaoId, dqId, nj, bj, user);
	    		dm.putDatas(list);
	    		return dm;
	    	} else {
	    		DataTableDM dm = new DataTableDM(0, true);
	    		String dqId = PageUtils.getParam(request, "dqId", null);
	    		int xuexiaoId = PageUtils.getIntParam(request, "xxId");
	    		int nj = PageUtils.getIntParam(request, "nj");
	    		int bj = PageUtils.getIntParam(request, "bj");
	    		ShiroUser user = (ShiroUser)subject.getPrincipals().getPrimaryPrincipal();
	    		List<LxrBean> list = new LxrService().query(xuexiaoId, dqId, nj, bj, user);
	    		dm.putDatas(list);
	    		return dm;
	    	}
		} catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("加载联系人失败！", e);
			}
			return uiex.toDM();
		}
	}
    
    @RequestMapping(value="add",method = RequestMethod.POST)
    @ResponseBody
    public AjaxDataModal add(HttpServletRequest request) {
    	try {
    		String name = PageUtils.getParam(request, "name", null);
    		int xb = PageUtils.getIntParam(request, "xb");
    		int xuexiaoId = PageUtils.getIntParam(request, "xxId");
    		String dqId = PageUtils.getParam(request, "dqId", null);
    		int nj = PageUtils.getIntParam(request, "nj");
    		int bj = PageUtils.getIntParam(request, "bj");
    		String lianxiren = PageUtils.getParamAndCheckEmpty(request, "lianxiren", "联系人姓名不能为空！");
			String phone = PageUtils.getParamAndCheckEmpty(request, "phone", "联系人电话不能为空！");
			ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			new LxrService().add(name, xb, xuexiaoId, dqId, nj, bj, lianxiren, phone, user);
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("添加联系人失败！", e);
			}
			return uiex.toDM();
		}
    }
    
    @RequestMapping(value="update",method = RequestMethod.POST)
    @ResponseBody
    public AjaxDataModal update(HttpServletRequest request) {
    	try {
    		int id = PageUtils.getIntParamAndCheckEmpty(request, "id", "错误的联系人ID！");
    		String name = PageUtils.getParam(request, "name", null);
    		int xb = PageUtils.getIntParam(request, "xb");
    		int xuexiaoId = PageUtils.getIntParam(request, "xxId");
    		String dqId = PageUtils.getParam(request, "dqId", null);
    		int nj = PageUtils.getIntParam(request, "nj");
    		int bj = PageUtils.getIntParam(request, "bj");
    		String lianxiren = PageUtils.getParamAndCheckEmpty(request, "lianxiren", "联系人姓名不能为空！");
			String phone = PageUtils.getParamAndCheckEmpty(request, "phone", "联系人电话不能为空！");
			ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
    		new LxrService().update(id, name, xb, xuexiaoId, dqId, nj, bj, lianxiren, phone, user);
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("修改联系人失败！", e);
			}
			return uiex.toDM();
		}
    }
    
    @RequestMapping(value="delete",method = RequestMethod.POST)
    @ResponseBody
    public AjaxDataModal delete(HttpServletRequest request) {
    	try {
    		int id = PageUtils.getIntParamAndCheckEmpty(request, "id", "错误的联系人ID！");
    		new LxrService().delete(id);
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("删除联系人失败！", e);
			}
			return uiex.toDM();
		}
    }
    
    @RequestMapping(value="cancerQianyue",method = RequestMethod.POST)
    @ResponseBody
    public AjaxDataModal cancerQianyue(HttpServletRequest request) {
    	try {
    		int id = PageUtils.getIntParamAndCheckEmpty(request, "id", "错误的联系人ID！");
    		new LxrService().cancerQianyue(id);
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("取消共享失败！", e);
			}
			return uiex.toDM();
		}
    }

}
