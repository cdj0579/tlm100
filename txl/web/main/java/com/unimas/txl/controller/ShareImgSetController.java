package com.unimas.txl.controller;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import sun.misc.BASE64Encoder;

import com.unimas.txl.service.ShareImgSetService;
import com.unimas.web.MenuManage.PageView;
import com.unimas.web.bean.datamodal.AjaxDataModal;
import com.unimas.web.utils.PageUtils;

@Controller
@RequestMapping(value="simgset")
public class ShareImgSetController {
	
	@Autowired
	ShareImgSetService	service;
	
	@RequestMapping(value = "")
	public String getIndex(Model model, HttpServletRequest request) {
		PageUtils.setPageView(request, PageView.SHAREIMGSET);
		List<Map<String, Object>> list = null;
		list = service.getBaseImgInfo();
		request.setAttribute("imgList", list);
		return "simgset/index";
	}
	@RequestMapping(value="saveShareImg")
	@ResponseBody
	public AjaxDataModal registerTeacher(HttpServletRequest request,
			@RequestParam("imgInfo") CommonsMultipartFile[] file) {
		BASE64Encoder base64Encoder = new BASE64Encoder();
		String imgInfo = null;
		AjaxDataModal dm = null;
		try {
			int id = PageUtils.getIntParam(request, "id");
			
			if (file != null) {
				InputStream inputStream = file[0].getInputStream();
				byte[] imgByte = new byte[inputStream.available()];
				inputStream.read(imgByte);
				imgInfo = "data:image/jpeg;base64," + base64Encoder.encode(imgByte);
			}
			if(imgInfo != null){
				service.saveShareImgInfo(id, imgInfo);
				dm = new AjaxDataModal(true);
				dm.put("isReresh",id);
			}
		} catch (Exception e) {
			dm = new AjaxDataModal(false);
		}
		return dm;
	}
	 
	 //txl_base_tupian
}
