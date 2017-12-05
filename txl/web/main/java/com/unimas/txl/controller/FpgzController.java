package com.unimas.txl.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.unimas.txl.bean.fenpei.FenpeiBean;
import com.unimas.txl.bean.fenpei.FenpeiSyzBean;
import com.unimas.txl.service.fenpei.FpgzService;
import com.unimas.web.MenuManage.PageView;
import com.unimas.web.auth.AuthRealm.ShiroUser;
import com.unimas.web.bean.datamodal.AjaxDataModal;
import com.unimas.web.bean.datamodal.DataTableDM;
import com.unimas.web.exception.UIException;
import com.unimas.web.utils.PageUtils;

@Controller
@RequestMapping(value="fpgzgl")
public class FpgzController {

	/**
     * 分配规则管理页面
     *
     * @return
     */
    @RequestMapping(value="")
    public String fpgzglIndex(Model model,HttpServletRequest request) {
    	PageUtils.setPageView(request, PageView.FENPEIGUIZE);
    	return "fpgzgl/index";
    }
    
    @RequestMapping(value="list")
	@ResponseBody
	public AjaxDataModal getLists(HttpServletRequest request) {
		try {
			DataTableDM dm = new DataTableDM(0, true);
			ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			List<FenpeiBean> list = new FpgzService().query(user);
			dm.putDatas(list);
			return dm;
		} catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("加载分配规则失败！", e);
			}
			return uiex.toDM();
		}
	}
    
    @RequestMapping(value="add",method = RequestMethod.POST)
    @ResponseBody
    public AjaxDataModal add(HttpServletRequest request) {
    	try {
    		String dqId = PageUtils.getParam(request, "dqId", null);
    		int[] xxId = PageUtils.getIntParams(request, "xxId", null);
    		int[] nj = PageUtils.getIntParams(request, "nj", null);
    		int[] bj = PageUtils.getIntParams(request, "bj", null);
    		String lxrDqId = PageUtils.getParam(request, "lxrDqId", null);
    		int lryId = PageUtils.getIntParam(request, "lryId");
    		int danliang = PageUtils.getIntParamAndCheckEmpty(request, "danliang", "分配单数不能为空！");
    		String _syzIds = PageUtils.getParam(request, "syzIds", null);
    		List<Integer> syzIds = null;
    		try {
    			String[] arr = _syzIds.split(",");
    			syzIds = Lists.newArrayList();
    			for(String id : arr){
    				syzIds.add(Integer.parseInt(id));
    			}
    		} catch(Exception e){}
			ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			new FpgzService().add(dqId, lxrDqId, xxId, nj, bj, lryId, danliang, syzIds, user);
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("添加分配规则失败！", e);
			}
			return uiex.toDM();
		}
    }
    
    @RequestMapping(value="syzlist")
    @ResponseBody
    public AjaxDataModal syzlist(HttpServletRequest request) {
    	try {
    		int id = PageUtils.getIntParamAndCheckEmpty(request, "id", "错误的分配规则ID！");
    		List<FenpeiSyzBean> list = new FpgzService().getSyzList(id);
    		AjaxDataModal dm = new AjaxDataModal(true);
    		List<Integer> syzIds = Lists.newArrayList();
    		for(FenpeiSyzBean b : list){
    			syzIds.add(b.getSyzId());
    		}
    		dm.put("syzIds", syzIds);
			return dm;
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("修改分配规则失败！", e);
			}
			return uiex.toDM();
		}
    }
    
    @RequestMapping(value="getZhouqi")
    @ResponseBody
    public AjaxDataModal getZhouqi(HttpServletRequest request) {
    	try {
    		ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
    		int zhouqi = new FpgzService().getZhouqi(user);
    		AjaxDataModal dm = new AjaxDataModal(true);
    		dm.put("zhouqi", zhouqi);
			return dm;
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("获取分配周期失败！", e);
			}
			return uiex.toDM();
		}
    }
    
    /*@RequestMapping(value="updateZhouqi")
    @ResponseBody
    public AjaxDataModal updateZhouqi(HttpServletRequest request) {
    	try {
    		int zhouqi = PageUtils.getIntParamAndCheckEmpty(request, "zhouqi", "错误的分配周期！");
    		ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
    		new FpgzService().updateZhouqi(zhouqi, user);
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("修改分配周期失败！", e);
			}
			return uiex.toDM();
		}
    }*/
    
    @RequestMapping(value="update",method = RequestMethod.POST)
    @ResponseBody
    public AjaxDataModal update(HttpServletRequest request) {
    	try {
    		int id = PageUtils.getIntParamAndCheckEmpty(request, "id", "错误的分配规则ID！");
    		String dqId = PageUtils.getParam(request, "dqId", null);
    		int[] xxId = PageUtils.getIntParams(request, "xxId", new int[0]);
    		int[] nj = PageUtils.getIntParams(request, "nj", new int[0]);
    		int[] bj = PageUtils.getIntParams(request, "bj", new int[0]);
    		String lxrDqId = PageUtils.getParam(request, "lxrDqId", "");
    		int lryId = PageUtils.getIntParam(request, "lryId");
    		if(lryId == -1) lryId = -2;
    		int danliang = PageUtils.getIntParamAndCheckEmpty(request, "danliang", "分配单数不能为空！");
    		String _syzIds = PageUtils.getParam(request, "syzIds", null);
    		List<Integer> syzIds = null;
    		try {
    			String[] arr = _syzIds.split(",");
    			syzIds = Lists.newArrayList();
    			for(String sid : arr){
    				syzIds.add(Integer.parseInt(sid));
    			}
    		} catch(Exception e){}
    		ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
    		new FpgzService().update(id, dqId, lxrDqId, xxId, nj, bj, lryId, danliang, syzIds, user);
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("修改分配规则失败！", e);
			}
			return uiex.toDM();
		}
    }
    
    @RequestMapping(value="delete",method = RequestMethod.POST)
    @ResponseBody
    public AjaxDataModal delete(HttpServletRequest request) {
    	try {
    		int id = PageUtils.getIntParamAndCheckEmpty(request, "id", "错误的分配规则ID！");
    		ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
    		new FpgzService().delete(id, user);
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("删除分配规则失败！", e);
			}
			return uiex.toDM();
		}
    }
	
}
