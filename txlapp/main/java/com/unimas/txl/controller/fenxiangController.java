package com.unimas.txl.controller;


import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unimas.common.qrcode.QrcodeUtils;
import com.unimas.txl.bean.user.ShiYongZheInfo;
import com.unimas.txl.service.AppIndexService;
import com.unimas.web.auth.AuthRealm.ShiroUser;
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
			List<Map<String, Object>> xxList = indexService.get("txl_xuexiao", "CONCAT(id,'-',dq_id)", "xuexiaoming", null, null, null,null);
	    	request.setAttribute("xxList", xxList);
	    	List<Map<String, Object>> dqList = indexService.get("xzqh", "code", "name", null, "pid", "330500","=");
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
//    		int xuexiaoId = PageUtils.getIntParam(request, "xxId");
    		String xxId = PageUtils.getParam(request, "xxId", "-2").split("-")[0];
    		int xuexiaoId = Integer.parseInt(xxId);
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
    
    @RequestMapping(value="/{jgId}/{lryId}/showShare")
    public String showShare(HttpServletRequest request, @PathVariable int jgId,@PathVariable int lryId) throws Exception {
    	if(lryId > 0 && jgId > 0){
	    	Map<String, Object> data = indexService.queryShareInfo2(lryId+"", jgId+"");
	    	if(data == null){
	    		request.setAttribute("isNull", "1");
	    	}else{
	    		request.setAttribute("data", data);
	    	}
    	}else{
    		request.setAttribute("isNull", "1");
    	}
    	return "app/showShare";
    }
    
    /**
     * 无需登录获取联系人注册的二维码
     * @param request
     * @param response
     * @param jgId
     * @param lryId
     * @throws Exception
     */
    @RequestMapping(value="/{jgId}/{lryId}/qrcode")
    public void qrcode(HttpServletRequest request, final HttpServletResponse response,
    		@PathVariable int jgId,@PathVariable int lryId) throws Exception {
    	response.setContentType("image/png; charset=utf-8"); 
    	String url = PageUtils.getSystemUrl(request);
    	url += "fx/"+jgId+"/"+lryId ;
		OutputStream os = response.getOutputStream();
		if(lryId <= 0 ){
			os.write("".getBytes());
		}else{
			ByteArrayOutputStream output = new QrcodeUtils().genQrcodeImage(url, false);
			os.write(output.toByteArray());
		}
		os.flush();
		os.close();
    }
}
