package com.unimas.tlm.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.unimas.common.util.json.JSONUtils;
import com.unimas.tlm.bean.datamodal.AjaxDataModal;
import com.unimas.tlm.bean.datamodal.DataTableDM;
import com.unimas.tlm.bean.zs.XtBean;
import com.unimas.tlm.bean.zs.XtZsdRef;
import com.unimas.tlm.bean.zs.ZsdBean;
import com.unimas.tlm.bean.zs.ZsdContentBean;
import com.unimas.tlm.bean.zs.ZtBean;
import com.unimas.tlm.bean.zs.ZtContentBean;
import com.unimas.tlm.exception.UIException;
import com.unimas.tlm.service.MenuManage.PageView;
import com.unimas.tlm.service.dic.DicService;
import com.unimas.tlm.service.jfrw.aspect.RwHandlerFactory;
import com.unimas.tlm.service.jfrw.aspect.annotations.RwPointcut;
import com.unimas.tlm.service.zs.ZsService;
import com.unimas.web.auth.AuthRealm.ShiroUser;
import com.unimas.web.utils.PageUtils;

/**
 * @author hxs
 * 知识管理请求控制器
 */
@Controller
@RequestMapping(value="/zs")
public class ZsglController {
	
	@Autowired
	ZsService service;
	
	/**
     * 知识体系
     * @return
     */
    @RequestMapping(value="zstx",method = RequestMethod.GET)
    public String zstx(HttpServletRequest request) {
    	PageUtils.setPageView(request, PageView.ZSTX);
    	ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
    	if("admin".equals(user.getRole())){
    		return  "zs/zstx/adminIndex";
    	} else {
    		try {
    			List<Map<String, Object>> list = new DicService().get("bb_dic", "id", "name", null, "type", "zj");
    			request.setAttribute("bbList", list);
    			request.setAttribute("user", user);
    		} catch (Exception e) {}
    		return  "zs/zstx/index";
    	}
    }
    
    /**
     * 知识体系(管理员)
     * @return
     */
    @RequestMapping(value="zstx/teacher",method = RequestMethod.GET)
    @RequiresRoles("admin")
    public String zstxOnAdmin(HttpServletRequest request, @RequestParam String user) {
    	PageUtils.setPageView(request, PageView.ZSTX);
    	try {
			List<Map<String, Object>> list = new DicService().get("bb_dic", "id", "name", null, "type", "zj");
			request.setAttribute("bbList", list);
			request.setAttribute("userNo", user);
		} catch (Exception e) {}
		return  "zs/zstx/admin";
    }
    
	/**
     * 知识点管理页面
     * @return
     */
    @RequiresRoles("admin")
    @RequestMapping(value="zsd",method = RequestMethod.GET)
    public String cstk(HttpServletRequest request) {
    	PageUtils.setPageView(request, PageView.ZSD);
        return  "zs/zsd/index";
    }
    
    /**
     * 知识点管理页面
     * @return
     */
    @RequiresRoles("teacher")
    @RequestMapping(value="zsd/content",method = RequestMethod.GET)
    public String zsdContent(HttpServletRequest request) {
    	PageUtils.setPageView(request, PageView.ZSD_CONTENT);
        return  "zs/zsd/content";
    }
    
    /**
     * 专题管理页面
     * @return
     */
    @RequiresRoles("admin")
    @RequestMapping(value="zt",method = RequestMethod.GET)
    public String zt(HttpServletRequest request) {
    	PageUtils.setPageView(request, PageView.ZT);
        return  "zs/zt/index";
    }
    
    /**
     * 专题管理页面
     * @return
     */
    @RequiresRoles("teacher")
    @RequestMapping(value="zt/content",method = RequestMethod.GET)
    public String ztContent(HttpServletRequest request) {
    	PageUtils.setPageView(request, PageView.ZT_CONTENT);
        return  "zs/zt/content";
    }
    
    /**
     * 习题管理页面
     * @return
     */
    @RequiresRoles("teacher")
    @RequestMapping(value="xt",method = RequestMethod.GET)
    public String xt(HttpServletRequest request) {
    	PageUtils.setPageView(request, PageView.XTGL);
        return  "zs/xt/index";
    }
    
    @RequestMapping(value="zsd/add",method = RequestMethod.POST)
    @ResponseBody
    public AjaxDataModal addZsd(HttpServletRequest request) {
    	try {
    		String dqId = PageUtils.getParamAndCheckEmpty(request, "dqId", "地区不能为空！");
			int kmId = PageUtils.getIntParamAndCheckEmpty(request, "kmId", "错误的科目！");
			int njId = PageUtils.getIntParamAndCheckEmpty(request, "njId", "错误的年级！");
			int xq = PageUtils.getIntParamAndCheckEmpty(request, "xq", "未选择正确的上下学期！");
			int zjId = PageUtils.getIntParamAndCheckEmpty(request, "zjId", "错误的章节！");
			int ks = PageUtils.getIntParamAndCheckEmpty(request, "ks", "未填写课时！");
			int ndId = PageUtils.getIntParam(request, "ndId");
			String name = PageUtils.getParamAndCheckEmpty(request, "name", "知识点名称不能为空！");
			String desc = PageUtils.getParam(request, "desc", null);
			ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			service.saveZsd(-1, dqId, kmId, njId, xq, zjId, name, ks, ndId, desc, user);
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("添加知识点失败！", e);
			}
			return uiex.toDM();
		}
    }
    
    @RequestMapping(value="zsd/update",method = RequestMethod.POST)
    @ResponseBody
    public AjaxDataModal updateZsd(HttpServletRequest request) {
    	try {
    		int id = PageUtils.getIntParamAndCheckEmpty(request, "id", "错误的知识点ID！");
    		int ks = PageUtils.getIntParamAndCheckEmpty(request, "ks", "未填写课时！");
    		int ndId = PageUtils.getIntParam(request, "ndId");
    		String name = PageUtils.getParamAndCheckEmpty(request, "name", "知识点名称不能为空！");
    		ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
    		String desc = PageUtils.getParam(request, "desc", null);
    		if("admin".equals(user.getRole())){
    			String dqId = PageUtils.getParamAndCheckEmpty(request, "dqId", "地区不能为空！");
    			int kmId = PageUtils.getIntParamAndCheckEmpty(request, "kmId", "错误的科目！");
    			int njId = PageUtils.getIntParamAndCheckEmpty(request, "njId", "错误的年级！");
    			int xq = PageUtils.getIntParamAndCheckEmpty(request, "xq", "未选择正确的上下学期！");
    			int zjId = PageUtils.getIntParamAndCheckEmpty(request, "zjId", "错误的章节！");
    			service.saveZsd(id, dqId, kmId, njId, xq, zjId, name, ks, ndId, desc, user);
    		} else {
    			service.updateZsd(id, name, ks, ndId, desc, user);
    		}
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("修改知识点失败！", e);
			}
			return uiex.toDM();
		}
    }
    
    @RequestMapping(value="zsd/sort",method = RequestMethod.POST)
    @ResponseBody
    public AjaxDataModal updateZsdSort(HttpServletRequest request) {
    	try {
    		int zjId = PageUtils.getIntParamAndCheckEmpty(request, "zjId", "错误的章节！");
    		String _data = PageUtils.getParamAndCheckEmpty(request, "datas", "没有要保存的信息！");
    		List<Map<String, Object>> zsdes = JSONUtils.getObjFromString(_data, new TypeReference<List<Map<String, Object>>>() {});
    		ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
    		service.saveZsdSort(zjId, zsdes, user.getUserNo());
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("保存知识点顺序失败！", e);
			}
			return uiex.toDM();
		}
    }
    
    @RequiresRoles("admin")
    @RequestMapping(value="zsd/diedaiZsd.modal",method = RequestMethod.GET)
    public String diedaiZsdModal(HttpServletRequest request) {
    	try {
			int id = PageUtils.getIntParamAndCheckEmpty(request, "id", "错误的知识点ID！");
			Map<String, Object> info = service.getmodifiedInfo(id);
			request.setAttribute("info", info);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return  "zs/zstx/diedaiZsd.modal";
    }
    
    @RequestMapping(value="content/view",method = RequestMethod.GET)
    public String contentViewModal(HttpServletRequest request) {
    	try {
			int id = PageUtils.getIntParamAndCheckEmpty(request, "id", null);
			String type = PageUtils.getParamAndCheckEmpty(request, "type", null);
			request.setAttribute("info", service.getViewContent(id, type));
		} catch (Exception e) {
			e.printStackTrace();
		}
        return  "zs/reviewContent.modal";
    }
    
    @RequestMapping(value="zsd/diedai",method = RequestMethod.POST)
    @ResponseBody
    public AjaxDataModal diedaiZsd(HttpServletRequest request) {
    	try {
    		int id = PageUtils.getIntParamAndCheckEmpty(request, "id", "错误的知识点ID！");
    		int modifiedId = PageUtils.getIntParamAndCheckEmpty(request, "modifiedId", "错误的知识点ID！");
    		int ks = PageUtils.getIntParamAndCheckEmpty(request, "ks", "未填写课时！");
			int ndId = PageUtils.getIntParam(request, "ndId");
			String name = PageUtils.getParamAndCheckEmpty(request, "name", "知识点名称不能为空！");
			String desc = PageUtils.getParam(request, "desc", null);
			service.diedaiZsd(id, modifiedId, name, ks, ndId, desc);
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("迭代知识点失败！", e);
			}
			return uiex.toDM();
		}
    }
    
    
    @RequestMapping(value="zsd/caina",method = RequestMethod.POST)
    @ResponseBody
    public AjaxDataModal cainaZsd(HttpServletRequest request) {
    	try {
    		int id = PageUtils.getIntParamAndCheckEmpty(request, "id", "错误的知识点ID！");
			service.cainaZsd(id);
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("采纳知识点失败！", e);
			}
			return uiex.toDM();
		}
    }
    
    @RequestMapping(value="getInfo")
    @ResponseBody
    public AjaxDataModal getInfo(HttpServletRequest request) {
    	try {
    		int id = PageUtils.getIntParamAndCheckEmpty(request, "id", "错误的ID！");
    		String type = PageUtils.getParamAndCheckEmpty(request, "type", "错误的类型！");
    		Object obj = service.getInfo(id, type);
    		AjaxDataModal dm = new AjaxDataModal(true);
    		dm.put("data", obj);
			return dm;
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("获取知识内容失败！", e);
			}
			return uiex.toDM();
		}
    }
    
    @RequestMapping(value="zstx/users")
	@ResponseBody
	@RequiresRoles("admin")
	public AjaxDataModal getZstxUsers(HttpServletRequest request) {
		try {
			DataTableDM dm = new DataTableDM(0, true);
			List<Map<String, Object>> list = service.getZstxUsers();
			dm.putDatas(list);
			return dm;
		} catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("加载个人知识体系失败！", e);
			}
			return uiex.toDM();
		}
	}
    
    @RequestMapping(value="collect",method = RequestMethod.POST)
    @ResponseBody
    public AjaxDataModal collect(HttpServletRequest request) {
    	try {
    		int id = PageUtils.getIntParamAndCheckEmpty(request, "id", "错误的ID！");
    		String type = PageUtils.getParamAndCheckEmpty(request, "type", "错误的类型！");
    		ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
    		service.collect(id, type, user);
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("收藏知识失败！", e);
			}
			return uiex.toDM();
		}
    }
    
    @RequestMapping(value="zsd/content/add",method = RequestMethod.POST)
    @ResponseBody
    @RwPointcut
    @RequiresRoles("teacher")
    public AjaxDataModal addZsdContent(HttpServletRequest request) {
    	try {
			int pid = PageUtils.getIntParamAndCheckEmpty(request, "pid", "错误的知识点ID！");
			int rwId = PageUtils.getIntParam(request, "rwId");
			String name = PageUtils.getParamAndCheckEmpty(request, "name", "知识点内容简介不能为空！");
			int isOriginal = PageUtils.getIntParamAndCheckEmpty(request, "isOriginal", "请选择是否原创！");
			int yyfs = PageUtils.getIntParamAndCheckEmpty(request, "yyfs", "请填写消耗积分数！");
			int isShare = PageUtils.getIntParamAndCheckEmpty(request, "isShare", "请选择是否隐藏！");
			String content = PageUtils.getParamAndCheckEmpty(request, "content", "知识点内容不能为空！");
			ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			ZsdContentBean b = service.saveZsdContentOnUser(-1, pid, name, isOriginal, yyfs, isShare, content, user);
			if(rwId > 0){
				RwHandlerFactory.setRwInfo(rwId, b.getId(), user);
			}
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("添加知识点内容失败！", e);
			}
			return uiex.toDM();
		}
    }
    
    @RequestMapping(value="zsd/content/update",method = RequestMethod.POST)
    @ResponseBody
    @RequiresRoles("teacher")
    public AjaxDataModal updateZsdContent(HttpServletRequest request) {
    	try {
    		int id = PageUtils.getIntParamAndCheckEmpty(request, "id", "错误的知识点内容ID！");
    		int pid = PageUtils.getIntParamAndCheckEmpty(request, "pid", "错误的知识点ID！");
			String name = PageUtils.getParamAndCheckEmpty(request, "name", "知识点内容简介不能为空！");
			int isOriginal = PageUtils.getIntParamAndCheckEmpty(request, "isOriginal", "请选择是否原创！");
			int yyfs = PageUtils.getIntParamAndCheckEmpty(request, "yyfs", "请填写消耗积分数！");
			int isShare = PageUtils.getIntParamAndCheckEmpty(request, "isShare", "请选择是否隐藏！");
			String content = PageUtils.getParamAndCheckEmpty(request, "content", "知识点内容不能为空！");
			ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			service.saveZsdContentOnUser(id, pid, name, isOriginal, yyfs, isShare, content, user);
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("修改知识点内容失败！", e);
			}
			return uiex.toDM();
		}
    }
    
    @RequestMapping(value="zsd/list")
	@ResponseBody
	@RequiresRoles("admin")
	public AjaxDataModal getZsds(HttpServletRequest request) {
		try {
			DataTableDM dm = new DataTableDM(0, true);
			String dqId = PageUtils.getParamAndCheckEmpty(request, "dqId", "地区不能为空！");
			int kmId = PageUtils.getIntParamAndCheckEmpty(request, "kmId", "错误的科目！");
			int njId = PageUtils.getIntParamAndCheckEmpty(request, "njId", "错误的年级！");
			int xq = PageUtils.getIntParamAndCheckEmpty(request, "xq", "未选择正确的上下学期！");
			List<ZsdBean> list = service.queryZsd(dqId, kmId, njId, xq, null);
			dm.putDatas(list);
			return dm;
		} catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("加载知识点失败！", e);
			}
			return uiex.toDM();
		}
	}
    
    @RequestMapping(value="zsd/content/search")
	@ResponseBody
	public AjaxDataModal searchZsdContents(HttpServletRequest request) {
		try {
			DataTableDM dm = new DataTableDM(0, true);
			String type = PageUtils.getParam(request, "type", "zsd");
			int id = PageUtils.getIntParamAndCheckEmpty(request, "id", "错误的ID！");
			ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			List<Map<String, Object>> list = null;
			if("admin".equals(user.getRole())){
				String userNo = PageUtils.getParamAndCheckEmpty(request, "userNo", "userNo不能为空！");
				list = service.searchZsdContents(type, id, userNo);
			} else {
				list = service.searchZsdContents(type, id, user.getUserNo());
				if(list != null){
					for(Map<String, Object> map : list){
						String userNo = (String)map.get("userNo");
						boolean isSelf = user.getUserNo().equals(userNo);
						map.put("isSelf", isSelf);
					}
				}
			}
			dm.putDatas(list);
			return dm;
		} catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("加载知识点失败！", e);
			}
			return uiex.toDM();
		}
	}
    
    @RequestMapping(value="zsd/select")
	@ResponseBody
	@RequiresRoles("teacher")
	public AjaxDataModal selectZsd(HttpServletRequest request) {
		try {
			String dqId = PageUtils.getParamAndCheckEmpty(request, "dqId", "地区不能为空！");
			int kmId = PageUtils.getIntParamAndCheckEmpty(request, "kmId", "错误的科目！");
			int njId = PageUtils.getIntParamAndCheckEmpty(request, "njId", "错误的年级！");
			int xq = PageUtils.getIntParamAndCheckEmpty(request, "xq", "未选择正确的上下学期！");
			DataTableDM dm = new DataTableDM(0, true);
			ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			List<ZsdBean> list = service.queryZsd(dqId, kmId, njId, xq, user.getUserNo());
			dm.putDatas(list);
			return dm;
		} catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("加载知识点失败！", e);
			}
			return uiex.toDM();
		}
	}
    
    @RequestMapping(value="zsd/content/list")
	@ResponseBody
	@RequiresRoles("teacher")
	public AjaxDataModal getZsdContents(HttpServletRequest request) {
		try {
			DataTableDM dm = new DataTableDM(0, true);
			String dqId = PageUtils.getParamAndCheckEmpty(request, "dqId", "地区不能为空！");
			int kmId = PageUtils.getIntParamAndCheckEmpty(request, "kmId", "错误的科目！");
			int njId = PageUtils.getIntParamAndCheckEmpty(request, "njId", "错误的年级！");
			int xq = PageUtils.getIntParamAndCheckEmpty(request, "xq", "未选择正确的上下学期！");
			boolean global = PageUtils.getBooleanParam(request, "global");
			List<ZsdContentBean> list = null;
			if(global){
				list = service.queryZsdContent(dqId, kmId, njId, xq);
			} else {
				ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
				list = service.queryZsdContentOnUser(dqId, kmId, njId, xq, user);
			}
			dm.putDatas(list);
			return dm;
		} catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("加载知识点失败！", e);
			}
			return uiex.toDM();
		}
	}
    
    @RequestMapping(value="zsd/content/delete")
	@ResponseBody
	@RequiresRoles("teacher")
	public AjaxDataModal deleteZsdContent(HttpServletRequest request) {
		try {
			int id = PageUtils.getIntParamAndCheckEmpty(request, "id", "错误的知识点内容ID！");
			service.deleteZsdContent(id);
			return new AjaxDataModal(true);
		} catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("删除知识点内容失败！", e);
			}
			return uiex.toDM();
		}
	}
    
    @RequestMapping(value="zt/add",method = RequestMethod.POST)
    @ResponseBody
    @RequiresRoles("admin")
    public AjaxDataModal addZt(HttpServletRequest request) {
    	try {
			int kmId = PageUtils.getIntParamAndCheckEmpty(request, "kmId", "错误的科目！");
			int njId = PageUtils.getIntParamAndCheckEmpty(request, "njId", "错误的年级！");
			int xq = PageUtils.getIntParamAndCheckEmpty(request, "xq", "未选择正确的上下学期！");
			int qzqm = PageUtils.getIntParamAndCheckEmpty(request, "qzqm", "未选择的期中期末！");
			int ks = PageUtils.getIntParamAndCheckEmpty(request, "ks", "未填写课时！");
			int ndId = PageUtils.getIntParam(request, "ndId");
			String name = PageUtils.getParamAndCheckEmpty(request, "name", "专题名称不能为空！");
			String desc = PageUtils.getParam(request, "desc", null);
			service.saveZt(-1, kmId, njId, xq, qzqm, name, ks, ndId, desc);
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("添加专题失败！", e);
			}
			return uiex.toDM();
		}
    }
    
    @RequestMapping(value="zt/update",method = RequestMethod.POST)
    @ResponseBody
    @RequiresRoles("admin")
    public AjaxDataModal updateZt(HttpServletRequest request) {
    	try {
    		int id = PageUtils.getIntParamAndCheckEmpty(request, "id", "错误的专题ID！");
    		int kmId = PageUtils.getIntParamAndCheckEmpty(request, "kmId", "错误的科目！");
			int njId = PageUtils.getIntParamAndCheckEmpty(request, "njId", "错误的年级！");
			int xq = PageUtils.getIntParamAndCheckEmpty(request, "xq", "未选择正确的上下学期！");
			int qzqm = PageUtils.getIntParamAndCheckEmpty(request, "qzqm", "未选择的期中期末！");
			int ks = PageUtils.getIntParamAndCheckEmpty(request, "ks", "未填写课时！");
			int ndId = PageUtils.getIntParam(request, "ndId");
			String name = PageUtils.getParamAndCheckEmpty(request, "name", "专题名称不能为空！");
			String desc = PageUtils.getParam(request, "desc", null);
			service.saveZt(id, kmId, njId, xq, qzqm, name, ks, ndId, desc);
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("修改专题失败！", e);
			}
			return uiex.toDM();
		}
    }
    
    @RequestMapping(value="zt/content/add",method = RequestMethod.POST)
    @ResponseBody
    @RwPointcut
    @RequiresRoles("teacher")
    public AjaxDataModal addZtContent(HttpServletRequest request) {
    	try {
			int pid = PageUtils.getIntParamAndCheckEmpty(request, "pid", "错误的专题ID！");
			int rwId = PageUtils.getIntParam(request, "rwId");
			String name = PageUtils.getParamAndCheckEmpty(request, "name", "专题内容简介不能为空！");
			int isOriginal = PageUtils.getIntParamAndCheckEmpty(request, "isOriginal", "请选择是否原创！");
			int yyfs = PageUtils.getIntParamAndCheckEmpty(request, "yyfs", "请填写消耗积分数！");
			int isShare = PageUtils.getIntParamAndCheckEmpty(request, "isShare", "请选择是否隐藏！");
			String content = PageUtils.getParamAndCheckEmpty(request, "content", "专题内容不能为空！");
			ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			ZtContentBean b = service.saveZtContentOnUser(-1, pid, name,isOriginal, yyfs, isShare, content, user);
			if(rwId > 0){
				RwHandlerFactory.setRwInfo(rwId, b.getId(), user);
			}
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("添加专题内容失败！", e);
			}
			return uiex.toDM();
		}
    }
    
    @RequestMapping(value="zt/content/update",method = RequestMethod.POST)
    @ResponseBody
    @RequiresRoles("teacher")
    public AjaxDataModal updateZtContent(HttpServletRequest request) {
    	try {
    		int id = PageUtils.getIntParamAndCheckEmpty(request, "id", "错误的专题内容ID！");
    		int pid = PageUtils.getIntParamAndCheckEmpty(request, "pid", "错误的专题ID！");
			String name = PageUtils.getParamAndCheckEmpty(request, "name", "专题内容简介不能为空！");
			int isOriginal = PageUtils.getIntParamAndCheckEmpty(request, "isOriginal", "请选择是否原创！");
			int yyfs = PageUtils.getIntParamAndCheckEmpty(request, "yyfs", "请填写消耗积分数！");
			int isShare = PageUtils.getIntParamAndCheckEmpty(request, "isShare", "请选择是否隐藏！");
			String content = PageUtils.getParamAndCheckEmpty(request, "content", "专题内容不能为空！");
			ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			service.saveZtContentOnUser(id, pid, name, isOriginal, yyfs, isShare, content, user);
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("修改专题内容失败！", e);
			}
			return uiex.toDM();
		}
    }
    
    @RequestMapping(value="zt/list")
	@ResponseBody
	public AjaxDataModal getZtes(HttpServletRequest request) {
		try {
			DataTableDM dm = new DataTableDM(0, true);
			int kmId = PageUtils.getIntParamAndCheckEmpty(request, "kmId", "错误的科目！");
			int njId = PageUtils.getIntParamAndCheckEmpty(request, "njId", "错误的年级！");
			int xq = PageUtils.getIntParamAndCheckEmpty(request, "xq", "未选择正确的上下学期！");
			int qzqm = PageUtils.getIntParamAndCheckEmpty(request, "qzqm", "未选择期中期末！");
			List<ZtBean> list = service.queryZt(kmId, njId, xq, qzqm);
			dm.putDatas(list);
			return dm;
		} catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("加载专题失败！", e);
			}
			return uiex.toDM();
		}
	}
    
    @RequestMapping(value="zt/content/list")
	@ResponseBody
	@RequiresRoles("teacher")
	public AjaxDataModal getZtContents(HttpServletRequest request) {
		try {
			DataTableDM dm = new DataTableDM(0, true);
			int kmId = PageUtils.getIntParamAndCheckEmpty(request, "kmId", "错误的科目！");
			int njId = PageUtils.getIntParamAndCheckEmpty(request, "njId", "错误的年级！");
			int xq = PageUtils.getIntParamAndCheckEmpty(request, "xq", "未选择正确的上下学期！");
			int qzqm = PageUtils.getIntParamAndCheckEmpty(request, "qzqm", "未选择期中期末！");
			//List<ZtContentBean> list = service.queryZtContentOnUser(kmId, njId, qzqm, user);
			
			boolean global = PageUtils.getBooleanParam(request, "global");
			List<ZtContentBean> list = null;
			if(global){
				list = service.queryZtContent(kmId, njId, xq, qzqm);
			} else {
				ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
				list = service.queryZtContentOnUser(kmId, njId, xq, qzqm, user);
			}
			
			dm.putDatas(list);
			return dm;
		} catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("加载专题内容失败！", e);
			}
			return uiex.toDM();
		}
	}
    
    @RequestMapping(value="zt/content/delete")
	@ResponseBody
	@RequiresRoles("teacher")
	public AjaxDataModal deleteZtContent(HttpServletRequest request) {
		try {
			int id = PageUtils.getIntParamAndCheckEmpty(request, "id", "错误的专题内容ID！");
			service.deleteZtContent(id);
			return new AjaxDataModal(true);
		} catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("删除专题内容失败！", e);
			}
			return uiex.toDM();
		}
	}
    
    @RequestMapping(value="zt/select")
	@ResponseBody
	public AjaxDataModal selectZt(HttpServletRequest request) {
		try {
			int kmId = PageUtils.getIntParamAndCheckEmpty(request, "kmId", "错误的科目！");
			int njId = PageUtils.getIntParamAndCheckEmpty(request, "njId", "错误的年级！");
			int xq = PageUtils.getIntParamAndCheckEmpty(request, "xq", "未选择正确的上下学期！");
			int qzqm = PageUtils.getIntParamAndCheckEmpty(request, "qzqm", "未选择期中期末！");
			DataTableDM dm = new DataTableDM(0, true);
			List<ZtBean> list = service.queryZt(kmId, njId, xq, qzqm);
			dm.putDatas(list);
			return dm;
		} catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("加载专题失败！", e);
			}
			return uiex.toDM();
		}
	}
    
    @RequestMapping(value="xt/list")
	@ResponseBody
	@RequiresRoles("teacher")
	public AjaxDataModal getXtes(HttpServletRequest request) {
		try {
			DataTableDM dm = new DataTableDM(0, true);
			String dqId = PageUtils.getParamAndCheckEmpty(request, "dqId", "地区不能为空！");
			int kmId = PageUtils.getIntParamAndCheckEmpty(request, "kmId", "错误的科目！");
			int njId = PageUtils.getIntParamAndCheckEmpty(request, "njId", "错误的年级！");
			int xq = PageUtils.getIntParamAndCheckEmpty(request, "xq", "未选择正确的上下学期！");
			boolean global = PageUtils.getBooleanParam(request, "global");
			List<XtBean> list = null;
			if(global){
				list = service.queryXt(dqId, kmId, njId, xq);
			} else {
				ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
				list = service.queryXtOnUser(dqId, kmId, njId, xq, user);
			}
			dm.putDatas(list);
			return dm;
		} catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("加载习题失败！", e);
			}
			return uiex.toDM();
		}
	}
    
    @RequestMapping(value="xt/delete")
	@ResponseBody
	@RequiresRoles("teacher")
	public AjaxDataModal deleteXt(HttpServletRequest request) {
		try {
			int id = PageUtils.getIntParamAndCheckEmpty(request, "id", "错误的习题ID！");
			service.deleteXt(id);
			return new AjaxDataModal(true);
		} catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("删除习题失败！", e);
			}
			return uiex.toDM();
		}
	}
    
    @RequestMapping(value="xt/add",method = RequestMethod.POST)
    @ResponseBody
    @RwPointcut
    @RequiresRoles("teacher")
    public AjaxDataModal addXt(HttpServletRequest request) {
    	try {
			int ndId = PageUtils.getIntParamAndCheckEmpty(request, "ndId", "错误的难度ID！");
			int ztId = PageUtils.getIntParam(request, "ztId");
			int rwId = PageUtils.getIntParam(request, "rwId");
			String _zsdIds = PageUtils.getParamAndCheckEmpty(request, "zsdIds", "至少选择一个知识点！");
			List<Integer> zsdIds = Lists.newArrayList();
			String[] arr = _zsdIds.split(",");
			for(String _id : arr){
				try {
					zsdIds.add(Integer.parseInt(_id));
				} catch(Exception e){}
			}
			String name = PageUtils.getParamAndCheckEmpty(request, "name", "习题简介不能为空！");
			int typeId = PageUtils.getIntParamAndCheckEmpty(request, "typeId", "请选择习题类型！");
			int isOriginal = PageUtils.getIntParamAndCheckEmpty(request, "isOriginal", "请选择是否原创！");
			int yyfs = PageUtils.getIntParamAndCheckEmpty(request, "yyfs", "请填写消耗积分数！");
			int isShare = PageUtils.getIntParamAndCheckEmpty(request, "isShare", "请选择是否隐藏！");
			String content = PageUtils.getParamAndCheckEmpty(request, "content", "习题题目不能为空！");
			String answer = PageUtils.getParamAndCheckEmpty(request, "answer", "习题详解不能为空！");
			ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			XtBean b = service.saveXt(-1, ndId, ztId, zsdIds, name, typeId,isOriginal, yyfs, isShare, content, answer, user);
			if(rwId > 0){
				RwHandlerFactory.setRwInfo(rwId, b.getId(), user);
			}
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("添加习题失败！", e);
			}
			return uiex.toDM();
		}
    }
    
    @RequestMapping(value="xt/update",method = RequestMethod.POST)
    @ResponseBody
    @RequiresRoles("teacher")
    public AjaxDataModal updateXt(HttpServletRequest request) {
    	try {
    		int id = PageUtils.getIntParamAndCheckEmpty(request, "id", "错误的习题ID！");
    		int ndId = PageUtils.getIntParamAndCheckEmpty(request, "ndId", "错误的难度ID！");
			int ztId = PageUtils.getIntParam(request, "ztId");
			String _zsdIds = PageUtils.getParamAndCheckEmpty(request, "zsdIds", "至少选择一个知识点！");
			List<Integer> zsdIds = Lists.newArrayList();
			String[] arr = _zsdIds.split(",");
			for(String _id : arr){
				try {
					zsdIds.add(Integer.parseInt(_id));
				} catch(Exception e){}
			}
			String name = PageUtils.getParamAndCheckEmpty(request, "name", "习题简介不能为空！");
			int typeId = PageUtils.getIntParamAndCheckEmpty(request, "typeId", "请选择习题类型！");
			int isOriginal = PageUtils.getIntParamAndCheckEmpty(request, "isOriginal", "请选择是否原创！");
			int yyfs = PageUtils.getIntParamAndCheckEmpty(request, "yyfs", "请填写消耗积分数！");
			int isShare = PageUtils.getIntParamAndCheckEmpty(request, "isShare", "请选择是否隐藏！");
			String content = PageUtils.getParamAndCheckEmpty(request, "content", "习题题目不能为空！");
			String answer = PageUtils.getParamAndCheckEmpty(request, "answer", "习题详解不能为空！");
			ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			service.saveXt(id, ndId, ztId, zsdIds, name, typeId, isOriginal, yyfs, isShare, content, answer, user);
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("修改习题失败！", e);
			}
			return uiex.toDM();
		}
    }
    
    @RequestMapping(value="xt/zsdRefs")
    @ResponseBody
    public AjaxDataModal getXtZsdRefs(HttpServletRequest request) {
    	try {
    		int id = PageUtils.getIntParamAndCheckEmpty(request, "id", "错误的习题ID！");
			DataTableDM dm = new DataTableDM(0, true);
			List<XtZsdRef> list = service.getXtZsdRefs(id);
			dm.putDatas(list);
			return dm;
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("加载习题所属的知识点失败！", e);
			}
			return uiex.toDM();
		}
    }
	
}
