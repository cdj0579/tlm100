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
    public AjaxDataModal addSyz(HttpServletRequest request) {
    	try {
    		String dqId = PageUtils.getParam(request, "dqId", null);
    		int zhouqi = PageUtils.getIntParamAndCheckEmpty(request, "zhouqi", "分配更新周期不能为空！");
    		int xxId = PageUtils.getIntParam(request, "xxId");
    		int nj = PageUtils.getIntParam(request, "nj");
    		int bj = PageUtils.getIntParam(request, "bj");
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
			new FpgzService().add(dqId, zhouqi, xxId, nj, bj, danliang, syzIds, user);
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
    
    @RequestMapping(value="update",method = RequestMethod.POST)
    @ResponseBody
    public AjaxDataModal updateSyz(HttpServletRequest request) {
    	try {
    		int id = PageUtils.getIntParamAndCheckEmpty(request, "id", "错误的分配规则ID！");
    		String dqId = PageUtils.getParam(request, "dqId", null);
    		int zhouqi = PageUtils.getIntParamAndCheckEmpty(request, "zhouqi", "分配更新周期不能为空！");
    		int xxId = PageUtils.getIntParam(request, "xxId");
    		int nj = PageUtils.getIntParam(request, "nj");
    		int bj = PageUtils.getIntParam(request, "bj");
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
    		new FpgzService().update(id, dqId, zhouqi, xxId, nj, bj, danliang, syzIds);
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
    public AjaxDataModal deleteSyz(HttpServletRequest request) {
    	try {
    		int id = PageUtils.getIntParamAndCheckEmpty(request, "id", "错误的分配规则ID！");
    		new FpgzService().delete(id);
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
