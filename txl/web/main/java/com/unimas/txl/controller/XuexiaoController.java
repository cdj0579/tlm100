package com.unimas.txl.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unimas.txl.bean.XuexiaoBean;
import com.unimas.txl.service.XuexiaoService;
import com.unimas.web.MenuManage.PageView;
import com.unimas.web.bean.datamodal.AjaxDataModal;
import com.unimas.web.bean.datamodal.DataTableDM;
import com.unimas.web.exception.UIException;
import com.unimas.web.utils.PageUtils;

@Controller
@RequestMapping(value="xxgl")
public class XuexiaoController {

    /**
     * 学校管理页面
     *
     * @return
     */
    @RequestMapping(value="")
    public String index(Model model,HttpServletRequest request) {
    	PageUtils.setPageView(request, PageView.XUEXIAO);
    	return "xxgl/index";
    }
    
    @RequestMapping(value="list")
	@ResponseBody
	public AjaxDataModal getLists(HttpServletRequest request) {
		try {
			DataTableDM dm = new DataTableDM(0, true);
			String dqId = PageUtils.getParam(request, "dqId", null);
			List<XuexiaoBean> list = new XuexiaoService().query(dqId);
			dm.putDatas(list);
			return dm;
		} catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("加载学校信息失败！", e);
			}
			return uiex.toDM();
		}
	}
    
    @RequestMapping(value="add",method = RequestMethod.POST)
    @ResponseBody
    public AjaxDataModal addSyz(HttpServletRequest request) {
    	try {
    		String name = PageUtils.getParamAndCheckEmpty(request, "name", "学校名称不能为空！");
    		String dqId = PageUtils.getParamAndCheckEmpty(request, "dqId", "地区不能为空！");
			String beizhu = PageUtils.getParam(request, "beizhu", null);
			new XuexiaoService().add(dqId, name, beizhu);
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("添加学校信息失败！", e);
			}
			return uiex.toDM();
		}
    }
    
    @RequestMapping(value="update",method = RequestMethod.POST)
    @ResponseBody
    public AjaxDataModal updateSyz(HttpServletRequest request) {
    	try {
    		int id = PageUtils.getIntParamAndCheckEmpty(request, "id", "错误的录入员ID！");
    		String name = PageUtils.getParamAndCheckEmpty(request, "name", "学校名称不能为空！");
    		String dqId = PageUtils.getParamAndCheckEmpty(request, "dqId", "地区不能为空！");
			String beizhu = PageUtils.getParam(request, "beizhu", null);
			new XuexiaoService().update(id, dqId, name, beizhu);
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("修改学校信息失败！", e);
			}
			return uiex.toDM();
		}
    }
    
    @RequestMapping(value="delete",method = RequestMethod.POST)
    @ResponseBody
    public AjaxDataModal deleteSyz(HttpServletRequest request) {
    	try {
    		int id = PageUtils.getIntParamAndCheckEmpty(request, "id", "错误的学校ID！");
    		new XuexiaoService().delete(id);
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("删除学校信息失败！", e);
			}
			return uiex.toDM();
		}
    }
	
}
