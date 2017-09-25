package com.unimas.tlm.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Sets;
import com.unimas.tlm.bean.datamodal.AjaxDataModal;
import com.unimas.tlm.bean.datamodal.DataTableDM;
import com.unimas.tlm.exception.UIException;
import com.unimas.tlm.service.dic.DicService;
import com.unimas.web.utils.PageUtils;

/**
 * <p>描述: 字典请求处理类</p>
 * @author hxs
 * @date 2017年1月17日 下午4:44:49
 */
@Controller
@RequestMapping(value="/dic")
public class DicController {
	
	DicService service;
	public DicController(){
		service = new DicService();
	}
	
	/**
	 * 添加字典
	 * @param request
	 * @return
	 * @throws UIException
	 */
    @RequestMapping(value="add",method = RequestMethod.POST)
    @ResponseBody
	public AjaxDataModal add(HttpServletRequest request) throws UIException {
		try {
			String tableName = PageUtils.getParamAndCheckEmpty(request, "tableName", null);
			String nameField = PageUtils.getParamAndCheckEmpty(request, "nameField", null);
			String value = PageUtils.getParamAndCheckEmpty(request, "value", null);
			String idField = PageUtils.getParamAndCheckEmpty(request, "idField", null);
			String typeField = PageUtils.getParam(request, "typeField", null);
			String typeVelue = PageUtils.getParam(request, "typeVelue", null);
			int id = service.add(tableName, nameField, idField, value, typeField, typeVelue);
			AjaxDataModal json = new AjaxDataModal(true);
			json.put("id", id);
			return json;
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("添加字典失败！", e);
			}
			return uiex.toDM();
		}
	}
    
    /**
	 * 删除字典
	 * @param request
	 * @return
	 * @throws UIException
	 */
    @RequestMapping(value="update",method = RequestMethod.POST)
    @ResponseBody
	public AjaxDataModal update(HttpServletRequest request) throws UIException {
		try {
			String tableName = PageUtils.getParamAndCheckEmpty(request, "tableName", null);
			int id = PageUtils.getIntParamAndCheckEmpty(request, "id", "字典ID不能为空！");
			String nameField = PageUtils.getParamAndCheckEmpty(request, "nameField", null);
			String value = PageUtils.getParamAndCheckEmpty(request, "value", null);
			String idField = PageUtils.getParamAndCheckEmpty(request, "idField", null);
			String typeField = PageUtils.getParam(request, "typeField", null);
			String typeVelue = PageUtils.getParam(request, "typeVelue", null);
			service.update(tableName, nameField, idField, value, id, typeField, typeVelue);
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("删除字典失败！", e);
			}
			return uiex.toDM();
		}
	}
    
    /**
	 * 删除字典
	 * @param request
	 * @return
	 * @throws UIException
	 */
    @RequestMapping(value="delete")
    @ResponseBody
	public AjaxDataModal delete(HttpServletRequest request) throws UIException {
		try {
			String tableName = PageUtils.getParamAndCheckEmpty(request, "tableName", null);
			int id = PageUtils.getIntParamAndCheckEmpty(request, "id", "字典ID不能为空！");
			String idField = PageUtils.getParamAndCheckEmpty(request, "idField", null);
			service.delete(tableName, idField, id);
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("删除字典失败！", e);
			}
			return uiex.toDM();
		}
	}
    
    /**
	 * 删除章节版本
	 * @param request
	 * @return
	 * @throws UIException
	 */
    @RequestMapping(value="bb/delete")
    @ResponseBody
	public AjaxDataModal deleteBb(HttpServletRequest request) throws UIException {
		try {
			int id = PageUtils.getIntParamAndCheckEmpty(request, "id", null);
			service.deleteBb(id);
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("删除章节版本失败！", e);
			}
			return uiex.toDM();
		}
	}
	
	@RequestMapping(value="get")
    @ResponseBody
	public AjaxDataModal get(HttpServletRequest request) throws UIException {
		try {
			String tableName = PageUtils.getParamAndCheckEmpty(request, "tableName", null);
			String nameField = PageUtils.getParamAndCheckEmpty(request, "nameField", null);
			String idField = PageUtils.getParamAndCheckEmpty(request, "idField", null);
			String groupField = PageUtils.getParam(request, "groupField", null);
			String typeField = PageUtils.getParam(request, "typeField", null);
			String typeVelue = PageUtils.getParam(request, "typeVelue", null);
			List<Map<String, Object>> l = service.get(tableName, idField, nameField, groupField, typeField, typeVelue);
			DataTableDM dm = new DataTableDM(0, true);
			dm.putDatas(l);
			return dm;
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("获取字典失败！", e);
			}
			return uiex.toDM();
		}
	}
	
	@RequestMapping(value="all")
    @ResponseBody
	public AjaxDataModal all(HttpServletRequest request) throws UIException {
		try {
			String[] _types = request.getParameterValues("types[]");
			Set<String> types = Sets.newHashSet(_types);
			AjaxDataModal dm = new AjaxDataModal(true);
			if(types.contains("km")){
				List<Map<String, Object>> list = service.get("km_dic", "id", "name", null, null, null);
				dm.put("km", list);
			}
			if(types.contains("nj")){
				List<Map<String, Object>> list = service.get("nj_dic", "id", "name", null, null, null);
				dm.put("nj", list);
			}
			return dm;
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("获取字典失败！", e);
			}
			return uiex.toDM();
		}
	}

}
