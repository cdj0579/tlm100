package com.unimas.tlm.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.unimas.common.util.json.JSONUtils;
import com.unimas.tlm.bean.datamodal.AjaxDataModal;
import com.unimas.tlm.bean.datamodal.DataTableDM;
import com.unimas.tlm.bean.ja.JaBean;
import com.unimas.tlm.bean.ja.JaDirs;
import com.unimas.tlm.bean.ja.JaTemplete;
import com.unimas.tlm.exception.UIException;
import com.unimas.tlm.service.MenuManage.PageView;
import com.unimas.tlm.service.hyd.aspect.annotations.HydPointcut;
import com.unimas.tlm.service.hyd.aspect.annotations.HydPointcut.HydRule;
import com.unimas.tlm.service.jagl.JaglService;
import com.unimas.web.auth.AuthRealm.ShiroUser;
import com.unimas.web.utils.PageUtils;

/**
 * @author hxs
 * 知识管理请求控制器
 */
@Controller
@RequestMapping(value="/jagl")
public class JaglController {
	
	@Autowired
	JaglService service;
	
	/**
     * 教案管理页面
     * @return
     */
    @RequestMapping(value="",method = RequestMethod.GET)
    public String list(HttpServletRequest request) {
    	PageUtils.setPageView(request, PageView.WDJA);
        return  "jagl/index";
    }
    
    /**
     * 教案模板页面
     * @return
     */
    @RequestMapping(value="templetes",method = RequestMethod.GET)
    public String templetes(HttpServletRequest request) {
    	PageUtils.setPageView(request, PageView.JAMB);
        return  "jagl/templetes/index";
    }
    
    /**
     * 教案预览页面
     * @return
     */
    @RequestMapping(value="view/{id}",method = RequestMethod.GET)
    public String view(@PathVariable int id, HttpServletRequest request) {
    	try {
			JaBean bean = service.getJaInfoById(id);
			request.setAttribute("info", bean);
		} catch (Exception e) {}
        return  "jagl/view";
    }
    
    /**
     * 教案预览页面
     * @return
     */
    @RequestMapping(value="templetes/view/{id}",method = RequestMethod.GET)
    public String templeteView(@PathVariable int id, HttpServletRequest request) {
    	try {
    		JaTemplete bean = service.getJaTempleteInfoById(id);
			request.setAttribute("info", bean);
		} catch (Exception e) {}
        return  "jagl/view";
    }
    
    /**
     * 教案编辑页面
     * @return
     */
    @RequestMapping(value="edit/{id}",method = RequestMethod.GET)
    public String edit(@PathVariable int id, HttpServletRequest request) {
    	PageUtils.setPageView(request, PageView.BJJA);
    	try {
			JaBean bean = service.getJaInfoById(id);
			request.setAttribute("info", bean);
		} catch (Exception e) {}
    	request.setAttribute("templete", false);
        return  "jagl/edit";
    }
    
    public static void main(String[] args) throws Exception {
    	JaBean bean = new JaglService().getJaInfoById(2);
    	byte[] bytes = bean.getContentBytes();
    	System.out.println(new String(bytes, "gbk"));
    	System.out.println(bean.getContent());
    	System.out.println(bean);
	}
    
    /**
     * 添加教案页面
     * @return
     */
    @RequestMapping(value="add",method = RequestMethod.GET)
    public String add(HttpServletRequest request) {
    	PageUtils.setPageView(request, PageView.BJJA);
    	request.setAttribute("templete", false);
    	Map<String, Object> info = Maps.newHashMap();
    	info.put("dirId", PageUtils.getIntParam(request, "dirId"));
    	request.setAttribute("info", info);
        return  "jagl/edit";
    }
    
    /**
     * 教案编辑页面
     * @return
     */
    @RequestMapping(value="templetes/edit/{id}",method = RequestMethod.GET)
    public String editTemplete(@PathVariable int id, HttpServletRequest request) {
    	PageUtils.setPageView(request, PageView.BJJAMB);
    	try {
    		JaTemplete bean = service.getJaTempleteInfoById(id);
			request.setAttribute("info", bean);
		} catch (Exception e) {}
    	request.setAttribute("templete", true);
        return  "jagl/edit";
    }
    
    /**
     * 添加教案页面
     * @return
     */
    @RequestMapping(value="templetes/add",method = RequestMethod.GET)
    public String addTemplete(HttpServletRequest request) {
    	PageUtils.setPageView(request, PageView.BJJAMB);
    	request.setAttribute("templete", true);
    	Map<String, Object> info = Maps.newHashMap();
    	info.put("dirId", PageUtils.getIntParam(request, "dirId"));
    	request.setAttribute("info", info);
        return  "jagl/edit";
    }
    
    @RequestMapping(value="ckeditor",method = RequestMethod.GET)
    public String ckeditor(HttpServletRequest request) {
    	PageUtils.setPageView(request, PageView.BJJA);
        return  "jagl/tmp/ckeditor";
    }
    
    @RequestMapping(value="save",method = RequestMethod.POST)
    @ResponseBody
    @HydPointcut(type=HydRule. ADD_JA)
    public AjaxDataModal save(HttpServletRequest request) {
    	try {
			int id = PageUtils.getIntParam(request, "id");
			int dirId = PageUtils.getIntParam(request, "dirId");
			String name = PageUtils.getParamAndCheckEmpty(request, "name", "教案名称不能为空！");
			String content = PageUtils.getParamAndCheckEmpty(request, "content", "教案内容不能为空！");
			int isOriginal = PageUtils.getIntParamAndCheckEmpty(request, "isOriginal", "请选择是否原创！");
			//int yyfs = PageUtils.getIntParamAndCheckEmpty(request, "yyfs", "请填写消耗积分数！");
			int yyfs = 1;
			int isShare = PageUtils.getIntParamAndCheckEmpty(request, "isShare", "请选择是否隐藏！");
			ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			service.saveJa(id, dirId, name, content, isOriginal, yyfs, isShare, user);
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("保存教案失败！", e);
			}
			return uiex.toDM();
		}
    }
    
    @RequestMapping(value="delete")
    @ResponseBody
    public AjaxDataModal delete(HttpServletRequest request) {
    	try {
			int id = PageUtils.getIntParam(request, "id");
			service.deleteJa(id);
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("删除教案失败！", e);
			}
			return uiex.toDM();
		}
    }
    
    @RequestMapping(value="templetes/delete")
    @ResponseBody
    public AjaxDataModal deleteTemplete(HttpServletRequest request) {
    	try {
			int id = PageUtils.getIntParam(request, "id");
			service.deleteJaTemplete(id);
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("保存教案模板失败！", e);
			}
			return uiex.toDM();
		}
    }
    
    @RequestMapping(value="templetes/save",method = RequestMethod.POST)
    @ResponseBody
    @HydPointcut(type=HydRule. ADD_JAMB)
    public AjaxDataModal saveTemplete(HttpServletRequest request) {
    	try {
			int id = PageUtils.getIntParam(request, "id");
			int dirId = PageUtils.getIntParam(request, "dirId");
			String name = PageUtils.getParamAndCheckEmpty(request, "name", "教案名称不能为空！");
			String content = PageUtils.getParamAndCheckEmpty(request, "content", "教案内容不能为空！");
			int isOriginal = PageUtils.getIntParamAndCheckEmpty(request, "isOriginal", "请选择是否原创！");
			//int yyfs = PageUtils.getIntParamAndCheckEmpty(request, "yyfs", "请填写消耗积分数！");
			int yyfs = 1;
			int isShare = PageUtils.getIntParamAndCheckEmpty(request, "isShare", "请选择是否隐藏！");
			boolean isAdd = PageUtils.getBooleanParam(request, "isAdd");
			if(isAdd){
				id = -1;
			}
			ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			service.saveJaTemplete(id, dirId, name, content, isOriginal, yyfs, isShare, user);
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("保存教案模板失败！", e);
			}
			return uiex.toDM();
		}
    }
    
    @RequestMapping(value="list")
	@ResponseBody
	public AjaxDataModal getJaList(HttpServletRequest request) {
		try {
			DataTableDM dm = new DataTableDM(0, true);
			int dirId = PageUtils.getIntParam(request, "dirId");
			ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			List<JaBean> list = service.getJaList(dirId, user);
			dm.putDatas(list);
			return dm;
		} catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("加载教案失败！", e);
			}
			return uiex.toDM();
		}
	}
    
    @RequestMapping(value="otherUserJaList")
	@ResponseBody
	public AjaxDataModal getOtherUserJaList(HttpServletRequest request) {
		try {
			DataTableDM dm = new DataTableDM(0, true);
			String name = PageUtils.getParam(request, "name", null);
			String userName = PageUtils.getParam(request, "userName", null);
			ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			List<JaBean> list = service.getOtherUserJaList(name, userName, user);
			dm.putDatas(list);
			return dm;
		} catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("加载教案失败！", e);
			}
			return uiex.toDM();
		}
	}
    
    @RequestMapping(value="collect")
   	@ResponseBody
   	public AjaxDataModal collectJa(HttpServletRequest request) {
   		try {
   			String _datas = PageUtils.getParamAndCheckEmpty(request, "datas", "未选择要收藏的教案！");
   			List<Map<String, Object>> list = null;
   			try {
   				list = JSONUtils.getObjFromString(_datas, new TypeReference<List<Map<String, Object>>>() {});
   			} catch(Exception e){
   				throw new UIException("提交的参数格式不正确！", e);
   			}
   			int dirId = PageUtils.getIntParam(request, "dirId");
   			ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
   			service.collectJa(list, dirId, user);
   			return new AjaxDataModal(true);
   		} catch (Exception e) {
   			UIException uiex = null;
   			if(e instanceof UIException){
   				uiex = (UIException)e;
   			} else {
   				uiex = new UIException("收藏教案失败！", e);
   			}
   			return uiex.toDM();
   		}
   	}
    
    @RequestMapping(value="dir/tree")
	@ResponseBody
	public AjaxDataModal getDirsTree(HttpServletRequest request) {
		try {
			DataTableDM dm = new DataTableDM(0, true);
			ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			int type = PageUtils.getIntParamAndCheckEmpty(request, "type", null);
			List<JaDirs> list = service.getDirList(type, user);
			dm.putDatas(list);
			return dm;
		} catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("加载教案失败！", e);
			}
			return uiex.toDM();
		}
	}
    
    @RequestMapping(value="dir/add")
	@ResponseBody
	public AjaxDataModal addDir(HttpServletRequest request) {
		try {
			String name = PageUtils.getParamAndCheckEmpty(request, "name", "目录名不能为空！");
			int type = PageUtils.getIntParamAndCheckEmpty(request, "type", null);
			int pid = PageUtils.getIntParam(request, "pid");
			ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			JaDirs dir = service.saveDir(-1, pid, type, name, user);
			AjaxDataModal json = new AjaxDataModal(true);
			json.put("id", dir.getId());
			return json;
		} catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("添加目录失败！", e);
			}
			return uiex.toDM();
		}
	}
    
    @RequestMapping(value="dir/update")
   	@ResponseBody
   	public AjaxDataModal updateDir(HttpServletRequest request) {
   		try {
   			int id = PageUtils.getIntParamAndCheckEmpty(request, "id", "目录ID不能为空！");
   			String name = PageUtils.getParamAndCheckEmpty(request, "name", "目录名不能为空！");
   			int type = PageUtils.getIntParamAndCheckEmpty(request, "type", null);
   			int pid = PageUtils.getIntParam(request, "pid");
   			ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
   			service.saveDir(id, pid, type, name, user);
   			return new AjaxDataModal(true);
   		} catch (Exception e) {
   			UIException uiex = null;
   			if(e instanceof UIException){
   				uiex = (UIException)e;
   			} else {
   				uiex = new UIException("修改目录失败！", e);
   			}
   			return uiex.toDM();
   		}
   	}
    
    @RequestMapping(value="dir/delete")
   	@ResponseBody
   	public AjaxDataModal deleteDir(HttpServletRequest request) {
   		try {
   			int id = PageUtils.getIntParamAndCheckEmpty(request, "id", "目录ID不能为空！");
   			service.deleteDir(id);
   			return new AjaxDataModal(true);
   		} catch (Exception e) {
   			UIException uiex = null;
   			if(e instanceof UIException){
   				uiex = (UIException)e;
   			} else {
   				uiex = new UIException("删除目录失败！", e);
   			}
   			return uiex.toDM();
   		}
   	}
    
    @RequestMapping(value="templetes/list")
	@ResponseBody
	public AjaxDataModal getJaTempletes(HttpServletRequest request) {
		try {
			DataTableDM dm = new DataTableDM(0, true);
			int dirId = PageUtils.getIntParam(request, "dirId");
			ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			List<JaTemplete> list = service.getJaTempletes(dirId, user);
			dm.putDatas(list);
			return dm;
		} catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("加载教案模板失败！", e);
			}
			return uiex.toDM();
		}
	}
	
}
