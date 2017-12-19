package com.unimas.txl.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unimas.txl.bean.SyzBean;
import com.unimas.txl.service.SyzService;
import com.unimas.web.MenuManage.PageView;
import com.unimas.web.auth.AuthRealm.ShiroUser;
import com.unimas.web.bean.datamodal.AjaxDataModal;
import com.unimas.web.bean.datamodal.DataTableDM;
import com.unimas.web.exception.UIException;
import com.unimas.web.utils.PageUtils;

@Controller
@RequestMapping(value="syzgl")
public class SyzController {
	
	/**
     * 使用者管理页面
     *
     * @return
     */
    @RequestMapping(value="")
    public String syzglIndex(Model model,HttpServletRequest request) {
    	PageUtils.setPageView(request, PageView.SHIYONGZHE);
    	return "syzgl/index";
    }
    
    @RequestMapping(value="list")
	@ResponseBody
	public AjaxDataModal getSyzs(HttpServletRequest request) {
		try {
			DataTableDM dm = new DataTableDM(0, true);
			String dqId = PageUtils.getParam(request, "dqId", null);
			String name = PageUtils.getParam(request, "name", null);
			ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			List<SyzBean> list = new SyzService().query(dqId, name, user);
			dm.putDatas(list);
			return dm;
		} catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("加载使用者失败！", e);
			}
			return uiex.toDM();
		}
	}
    
    @RequestMapping(value="add",method = RequestMethod.POST)
    @ResponseBody
    public AjaxDataModal addSyz(HttpServletRequest request) {
    	try {
    		String name = PageUtils.getParamAndCheckEmpty(request, "name", "姓名不能为空！");
    		String dqId = PageUtils.getParamAndCheckEmpty(request, "dqId", "地区不能为空！");
    		int lryId = PageUtils.getIntParam(request, "lryId");
    		String username = PageUtils.getParamAndCheckEmpty(request, "username", "账号不能为空！");
			String password = PageUtils.getParamAndCheckEmpty(request, "password", "密码不能为空！");
			ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			new SyzService().add(name, dqId, lryId, username, password, user);
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("添加使用者失败！", e);
			}
			return uiex.toDM();
		}
    }
    
    @RequestMapping(value="update",method = RequestMethod.POST)
    @ResponseBody
    public AjaxDataModal updateSyz(HttpServletRequest request) {
    	try {
    		int id = PageUtils.getIntParamAndCheckEmpty(request, "id", "错误的使用者ID！");
    		String name = PageUtils.getParamAndCheckEmpty(request, "name", "姓名不能为空！");
    		String dqId = PageUtils.getParamAndCheckEmpty(request, "dqId", "地区不能为空！");
    		int lryId = PageUtils.getIntParam(request, "lryId");
    		String userNo = PageUtils.getParamAndCheckEmpty(request, "userNo", "用户编号不能为空！");
    		boolean updatePwd = PageUtils.getBooleanParam(request, "isUpdatePwd");
			String password = null;
			if(updatePwd){
				password = PageUtils.getParamAndCheckEmpty(request, "password", "密码不能为空！");
			}
    		new SyzService().update(id, name, dqId, lryId, updatePwd, userNo, password);
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("修改使用者失败！", e);
			}
			return uiex.toDM();
		}
    }
    
    @RequestMapping(value="delete",method = RequestMethod.POST)
    @ResponseBody
    public AjaxDataModal deleteSyz(HttpServletRequest request) {
    	try {
    		int id = PageUtils.getIntParamAndCheckEmpty(request, "id", "错误的使用者ID！");
    		new SyzService().delete(id);
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("删除使用者失败！", e);
			}
			return uiex.toDM();
		}
    }

}
