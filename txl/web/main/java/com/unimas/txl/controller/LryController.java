package com.unimas.txl.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unimas.txl.bean.LryBean;
import com.unimas.txl.service.LryService;
import com.unimas.web.MenuManage.PageView;
import com.unimas.web.auth.AuthRealm.ShiroUser;
import com.unimas.web.bean.datamodal.AjaxDataModal;
import com.unimas.web.bean.datamodal.DataTableDM;
import com.unimas.web.exception.UIException;
import com.unimas.web.utils.PageUtils;

@Controller
@RequestMapping(value="lrygl")
public class LryController {

    /**
     * 录入员管理页面
     *
     * @return
     */
    @RequestMapping(value="")
    public String lryglIndex(Model model,HttpServletRequest request) {
    	PageUtils.setPageView(request, PageView.LURUYUAN);
    	return "lrygl/index";
    }
    
    @RequestMapping(value="list")
	@ResponseBody
	public AjaxDataModal getLists(HttpServletRequest request) {
		try {
			DataTableDM dm = new DataTableDM(0, true);
			ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			List<LryBean> list = new LryService().query(user);
			dm.putDatas(list);
			return dm;
		} catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("加载录入员失败！", e);
			}
			return uiex.toDM();
		}
	}
    
    @RequestMapping(value="add",method = RequestMethod.POST)
    @ResponseBody
    public AjaxDataModal addSyz(HttpServletRequest request) {
    	try {
    		String name = PageUtils.getParamAndCheckEmpty(request, "name", "姓名不能为空！");
    		String username = PageUtils.getParamAndCheckEmpty(request, "username", "账号不能为空！");
			String password = PageUtils.getParamAndCheckEmpty(request, "password", "密码不能为空！");
			ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			new LryService().add(name, username, password, user);
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("添加录入员失败！", e);
			}
			return uiex.toDM();
		}
    }
    
    @RequestMapping(value="update",method = RequestMethod.POST)
    @ResponseBody
    public AjaxDataModal updateSyz(HttpServletRequest request) {
    	try {
    		int id = PageUtils.getIntParamAndCheckEmpty(request, "id", "错误的录入员ID！");
    		String name = PageUtils.getParamAndCheckEmpty(request, "name", "姓名不能为空！");
    		String userNo = PageUtils.getParamAndCheckEmpty(request, "userNo", "用户编号不能为空！");
    		boolean updatePwd = PageUtils.getBooleanParam(request, "isUpdatePwd");
			String password = null;
			if(updatePwd){
				password = PageUtils.getParamAndCheckEmpty(request, "password", "密码不能为空！");
			}
    		new LryService().update(id, name, updatePwd, userNo, password);
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("修改录入员失败！", e);
			}
			return uiex.toDM();
		}
    }
    
    @RequestMapping(value="delete",method = RequestMethod.POST)
    @ResponseBody
    public AjaxDataModal deleteSyz(HttpServletRequest request) {
    	try {
    		int id = PageUtils.getIntParamAndCheckEmpty(request, "id", "错误的录入员ID！");
    		new LryService().delete(id);
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("删除录入员失败！", e);
			}
			return uiex.toDM();
		}
    }
	
}
