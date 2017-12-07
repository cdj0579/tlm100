package com.unimas.txl.controller;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unimas.txl.service.AppIndexService;
import com.unimas.web.bean.datamodal.AjaxDataModal;
import com.unimas.web.exception.UIException;
import com.unimas.web.utils.PageUtils;

@Controller
@RequestMapping(value="/fx")
public class fenxiangController {
	@Autowired
	AppIndexService indexService;
    
    @RequestMapping(value="/{jgId}/{lryId}",method=RequestMethod.GET)
    public String fengXiang(HttpServletRequest request,@PathVariable String jgId,@PathVariable String lryId) {
		try {
			List<Map<String, Object>> xxList = indexService.get("txl_xuexiao", "id", "xuexiaoming", null, null, null,null);
	    	request.setAttribute("xxList", xxList);
	    	List<Map<String, Object>> dqList = indexService.get("xzqh", "code", "name", null, "code", "3305%","like");
	    	request.setAttribute("dqList", dqList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("jgId", jgId);
		request.setAttribute("lryId", lryId);
    	return "app/fenxiang";
    }
    
    @RequestMapping(value="/getXuexiao",method = RequestMethod.POST)
    @ResponseBody
    public Object getXuexiao(HttpServletRequest request) {
    	AjaxDataModal dm = new AjaxDataModal(true);
    	try {
    		String dqId = PageUtils.getParam(request, "dqId", null);
			List<Map<String, Object>> xxList = indexService.get("txl_xuexiao", "id", "xuexiaoming", null, "dq_id", dqId,"=");
			dm.put("xxList", xxList);
    	} catch (Exception e) {
		}
    	return dm;
    }
    
    
    @RequestMapping(value="/saveLxrInfo",method = RequestMethod.POST)
    @ResponseBody
    public Object saveLxrInfo(HttpServletRequest request) {
    	try {
    		String name = PageUtils.getParam(request, "name", null);
    		int xb = PageUtils.getIntParam(request, "xb");
    		String dqId = PageUtils.getParam(request, "dqId", null);
    		int xuexiaoId = PageUtils.getIntParam(request, "xxId");
    		if(xuexiaoId <= 0) xuexiaoId = -2;
    		int nj = PageUtils.getIntParam(request, "nj");
    		if(nj <= 0) nj = -2;
    		int bj = PageUtils.getIntParam(request, "bj");
    		if(bj <= 0) bj = -2;
    		String lianxiren = PageUtils.getParamAndCheckEmpty(request, "lianxiren", "联系人姓名不能为空！");
			String phone = PageUtils.getParamAndCheckEmpty(request, "phone", "联系人电话不能为空！");
			int jgId = PageUtils.getIntParam(request, "jgId");
			int lryId = PageUtils.getIntParam(request, "lryId");
    		indexService.addLxrInfo(name, xb, xuexiaoId, dqId, nj, bj, lianxiren, phone, jgId, lryId);
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
