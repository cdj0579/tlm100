package com.unimas.web.bean.datamodal;

import java.util.HashMap;

/**
 * <p>描述: AJAX请求数据模型</p>
 * @author hxs
 * @date 2016年12月29日 上午10:52:14
 */
public class AjaxDataModal extends HashMap<String, Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * @param success  true表示请求成功， false表示请求失败
	 * @param msg      说明信息
	 */
	public AjaxDataModal(boolean success, String msg){
		super();
		this.put("success", success);
		if(msg != null){
			this.put("message", msg);
		}
	}
	
	/**
	 * @param success   true表示请求成功， false表示请求失败
	 */
	public AjaxDataModal(boolean success){
		this(success, null);
	}

}
