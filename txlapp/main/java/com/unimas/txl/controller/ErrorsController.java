package com.unimas.txl.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unimas.web.MenuManage.PageView;
import com.unimas.web.utils.PageUtils;

/**
 * <p>描述: 错误页面显示</p>
 * @author hxs
 * @date 2016年12月29日 上午10:23:24
 */
@Controller
public class ErrorsController {
	
	@RequestMapping(value="/errors/{code}")
	public String errors(@PathVariable int code, HttpServletRequest request) {
		if(code == 404){
			PageUtils.setPageView(request, PageView.ERROR_404);
		} else {
			PageUtils.setPageView(request, PageView.ERROR_500);
		}
		return "errors/"+code;
	}

}
