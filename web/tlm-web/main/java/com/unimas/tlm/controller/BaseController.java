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

import com.google.common.collect.Lists;
import com.unimas.common.util.json.JSONUtils;
import com.unimas.tlm.bean.base.CstBean;
import com.unimas.tlm.bean.base.MbxxBean;
import com.unimas.tlm.bean.base.XkdwBean;
import com.unimas.tlm.bean.base.ZjBean;
import com.unimas.tlm.bean.datamodal.AjaxDataModal;
import com.unimas.tlm.bean.datamodal.DataTableDM;
import com.unimas.tlm.bean.zs.ZsdBean;
import com.unimas.tlm.exception.UIException;
import com.unimas.tlm.service.BaseService;
import com.unimas.tlm.service.MenuManage.PageView;
import com.unimas.tlm.service.dic.DicService;
import com.unimas.tlm.service.zs.ZsService;
import com.unimas.web.auth.AuthRealm.ShiroUser;
import com.unimas.web.utils.PageUtils;

/**
 * @author hxs
 * 基础信息管理请求控制器
 */
@Controller
@RequestMapping(value="/base")
public class BaseController {
	
	@Autowired
	BaseService service;
	
	/**
     * 测试题库管理页面
     * @return
     */
    @RequestMapping(value="cstk",method = RequestMethod.GET)
    public String cstk(HttpServletRequest request) {
    	PageUtils.setPageView(request, PageView.CSTK);
        return  "base/cstk";
    }
    
    /**
     * 目标学校管理页面
     * @return
     */
    @RequestMapping(value="mbxx",method = RequestMethod.GET)
    public String mbxx(HttpServletRequest request) {
    	PageUtils.setPageView(request, PageView.MBXX);
        return  "base/mbxx";
    }
    
    /**
     * 学科档位管理页面
     * @return
     */
    @RequestMapping(value="xkdw",method = RequestMethod.GET)
    public String xkdw(HttpServletRequest request) {
    	PageUtils.setPageView(request, PageView.XKDW);
        return  "base/xkdw";
    }
    
    /**
     * 学科档位管理页面
     * @return
     */
    @RequestMapping(value="zj",method = RequestMethod.GET)
    public String zj(HttpServletRequest request) {
    	PageUtils.setPageView(request, PageView.ZJGL);
        return  "base/zj";
    }
    
    @RequestMapping(value="cstk/add",method = RequestMethod.POST)
    @ResponseBody
    public AjaxDataModal addCst(HttpServletRequest request) {
    	try {
			int kmId = PageUtils.getIntParamAndCheckEmpty(request, "kmId", "错误的科目！");
			int njId = PageUtils.getIntParamAndCheckEmpty(request, "njId", "错误的年级！");
			String name = PageUtils.getParamAndCheckEmpty(request, "name", "题目不能为空！");
			String answer = PageUtils.getParamAndCheckEmpty(request, "answer", "答案不能为空！");
			String _options = PageUtils.getParam(request, "options", null);
			List<Map<String, Object>> options = JSONUtils.getObjFromFile(_options, new TypeReference<List<Map<String, Object>>>() {});
			service.saveCst(-1, kmId, njId, name, answer, options);
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("添加测试题失败！", e);
			}
			return uiex.toDM();
		}
    }
    
    @RequestMapping(value="cstk/update",method = RequestMethod.POST)
    @ResponseBody
    public AjaxDataModal updateCst(HttpServletRequest request) {
    	try {
    		int id = PageUtils.getIntParamAndCheckEmpty(request, "id", "错误的测试题ID！");
			int kmId = PageUtils.getIntParamAndCheckEmpty(request, "kmId", "错误的科目！");
			int njId = PageUtils.getIntParamAndCheckEmpty(request, "njId", "错误的年级！");
			String name = PageUtils.getParamAndCheckEmpty(request, "name", "题目不能为空！");
			String answer = PageUtils.getParamAndCheckEmpty(request, "answer", "答案不能为空！");
			String _options = PageUtils.getParam(request, "options", null);
			List<Map<String, Object>> options = JSONUtils.getObjFromFile(_options, new TypeReference<List<Map<String, Object>>>() {});
			service.saveCst(id, kmId, njId, name, answer, options);
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("修改测试题失败！", e);
			}
			return uiex.toDM();
		}
    }
    
    @RequestMapping(value="cstk/delete")
    @ResponseBody
    public AjaxDataModal deleteCst(HttpServletRequest request) {
    	try {
    		int id = PageUtils.getIntParamAndCheckEmpty(request, "id", "错误的测试题ID！");
			service.deleteCst(id);
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("删除测试题失败！", e);
			}
			return uiex.toDM();
		}
    }
    
    @RequestMapping(value="cstk/{id}")
	@ResponseBody
	public AjaxDataModal getCst(@PathVariable int id, HttpServletRequest request) {
		try {
			AjaxDataModal ajax = new AjaxDataModal(true);
			CstBean cst = service.getCst(id);
			ajax.put("data", cst);
			return ajax;
		} catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("加载测试题失败！", e);
			}
			return uiex.toDM();
		}
	}
    
    @RequestMapping(value="cstk/list")
	@ResponseBody
	public AjaxDataModal getCstks(HttpServletRequest request) {
		try {
			DataTableDM dm = new DataTableDM(0, true);
			List<CstBean> list = service.queryCtsList();
			dm.putDatas(list);
			return dm;
		} catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("加载测试题库失败！", e);
			}
			return uiex.toDM();
		}
	}
    
    @RequestMapping(value="mbxx/add",method = RequestMethod.POST)
    @ResponseBody
    public AjaxDataModal addMbxx(HttpServletRequest request) {
    	try {
    		String dqId = PageUtils.getParamAndCheckEmpty(request, "dqId", "地区不能为空！");
			String name = PageUtils.getParamAndCheckEmpty(request, "name", "学校名称不能为空！");
			String desc = PageUtils.getParam(request, "desc", null);
			String _mbf = PageUtils.getParamAndCheckEmpty(request, "mbf", "目标分不能为空！");
			List<Map<String, Object>> mbfList = JSONUtils.getObjFromFile(_mbf, new TypeReference<List<Map<String, Object>>>() {});
			service.saveMbxx(-1, dqId, name, desc, mbfList);
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("添加目标学校失败！", e);
			}
			return uiex.toDM();
		}
    }
    
    @RequestMapping(value="mbxx/update",method = RequestMethod.POST)
    @ResponseBody
    public AjaxDataModal updateMbxx(HttpServletRequest request) {
    	try {
    		int id = PageUtils.getIntParamAndCheckEmpty(request, "id", "错误的目标学校ID！");
    		String dqId = PageUtils.getParamAndCheckEmpty(request, "dqId", "地区不能为空！");
    		String name = PageUtils.getParamAndCheckEmpty(request, "name", "学校名称不能为空！");
			String desc = PageUtils.getParam(request, "desc", null);
			String _mbf = PageUtils.getParam(request, "mbf", null);
			List<Map<String, Object>> mbfList = JSONUtils.getObjFromFile(_mbf, new TypeReference<List<Map<String, Object>>>() {});
			service.saveMbxx(id, dqId, name, desc, mbfList);
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("修改目标学校失败！", e);
			}
			return uiex.toDM();
		}
    }
    
    @RequestMapping(value="mbxx/delete")
    @ResponseBody
    public AjaxDataModal deleteMbxx(HttpServletRequest request) {
    	try {
    		int id = PageUtils.getIntParamAndCheckEmpty(request, "id", "错误的目标学校ID！");
			service.deleteMbxx(id);
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("删除目标学校失败！", e);
			}
			return uiex.toDM();
		}
    }
    
    @RequestMapping(value="mbxx/{id}")
	@ResponseBody
	public AjaxDataModal getMbxx(@PathVariable int id, HttpServletRequest request) {
		try {
			AjaxDataModal ajax = new AjaxDataModal(true);
			MbxxBean mbxx = service.getMbxx(id);
			ajax.put("data", mbxx);
			return ajax;
		} catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("加载目标学校信息失败！", e);
			}
			return uiex.toDM();
		}
	}
    
    @RequestMapping(value="mbxx/list")
	@ResponseBody
	public AjaxDataModal getMbxxes(HttpServletRequest request) {
		try {
			DataTableDM dm = new DataTableDM(0, true);
			List<MbxxBean> list = service.queryMbxxList();
			dm.putDatas(list);
			return dm;
		} catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("加载目标学校信息失败！", e);
			}
			return uiex.toDM();
		}
	}
    
    @RequestMapping(value="xkdw/list")
	@ResponseBody
	public AjaxDataModal getXkdwList(HttpServletRequest request) {
		try {
			DataTableDM dm = new DataTableDM(0, true);
			List<XkdwBean> list = service.getXkdwList();
			dm.putDatas(list);
			return dm;
		} catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("加载学科档位失败！", e);
			}
			return uiex.toDM();
		}
	}
    
    @RequestMapping(value="xkdw/update",method = RequestMethod.POST)
    @ResponseBody
    public AjaxDataModal updateXkdw(HttpServletRequest request) {
    	try {
    		int id = PageUtils.getIntParamAndCheckEmpty(request, "id", "错误的学科档位ID！");
    		int kmId = PageUtils.getIntParamAndCheckEmpty(request, "kmId", "错误的学科ID！");
    		int level1 = PageUtils.getIntParamAndCheckEmpty(request, "level1", "错误的档位分数！");
    		int level2 = PageUtils.getIntParamAndCheckEmpty(request, "level2", "错误的档位分数！");
			service.setXkdw(id, kmId, level1, level2);
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("修改学科档位失败！", e);
			}
			return uiex.toDM();
		}
    }
    
    @RequestMapping(value="zj/list")
	@ResponseBody
	public AjaxDataModal getZjList(HttpServletRequest request) {
		try {
			int pid = PageUtils.getIntParam(request, "pid");
			int bbId = PageUtils.getIntParam(request, "bbId");
			String dqId = PageUtils.getParamAndCheckEmpty(request, "dqId", "地区不能为空！");
    		int kmId = PageUtils.getIntParamAndCheckEmpty(request, "kmId", "错误的学科ID！");
			int njId = PageUtils.getIntParamAndCheckEmpty(request, "njId", "错误的年级ID！");
			int xq = PageUtils.getIntParamAndCheckEmpty(request, "xq", "未选择正确的上下学期！");
			DataTableDM dm = new DataTableDM(0, true);
			List<ZjBean> list = service.queryZjList(pid>0?pid:-1, dqId, bbId, kmId, njId, xq);
			dm.putDatas(list);
			return dm;
		} catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("加载章节信息失败！", e);
			}
			return uiex.toDM();
		}
	}
    
    @RequestMapping(value="zj/tree")
   	@ResponseBody
   	public AjaxDataModal getZjTreeList(HttpServletRequest request) {
   		try {
   			String dqId = PageUtils.getParamAndCheckEmpty(request, "dqId", "地区不能为空！");
       		int kmId = PageUtils.getIntParamAndCheckEmpty(request, "kmId", "错误的学科ID！");
   			int njId = PageUtils.getIntParamAndCheckEmpty(request, "njId", "错误的年级ID！");
   			int xq = PageUtils.getIntParamAndCheckEmpty(request, "xq", "未选择正确的上下学期！");
   			DataTableDM dm = new DataTableDM(0, true);
   			List<ZjBean> list = service.queryZjList(dqId, kmId, njId, xq);
   			if(list == null){
   				list = Lists.newArrayList();
   			}
   			DicService ds = new DicService();
   			List<Map<String, Object>> list2 = ds.get("bb_dic", "id", "name", null, "type", "zj");
   			dm.put("bbList", list2);
   			dm.putDatas(list);
   			return dm;
   		} catch (Exception e) {
   			UIException uiex = null;
   			if(e instanceof UIException){
   				uiex = (UIException)e;
   			} else {
   				uiex = new UIException("加载章节信息失败！", e);
   			}
   			return uiex.toDM();
   		}
   	}
    
    @RequestMapping(value="zj/bb/tree")
   	@ResponseBody
   	public AjaxDataModal getZjTreeListByBbId(HttpServletRequest request) {
   		try {
   			int bbId = PageUtils.getIntParam(request, "bbId");
   			String dqId = PageUtils.getParamAndCheckEmpty(request, "dqId", "地区不能为空！");
       		int kmId = PageUtils.getIntParamAndCheckEmpty(request, "kmId", "错误的学科ID！");
   			int njId = PageUtils.getIntParamAndCheckEmpty(request, "njId", "错误的年级ID！");
   			int xq = PageUtils.getIntParamAndCheckEmpty(request, "xq", "未选择正确的上下学期！");
   			DataTableDM dm = new DataTableDM(0, true);
   			List<ZjBean> list = service.queryZjList(bbId, dqId, kmId, njId, xq);
   			ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
   			if("admin".equals(user.getRole())){
   				String userNo = PageUtils.getParamAndCheckEmpty(request, "userNo", "userNo不能为空！");
   				List<ZsdBean> zsdList = new ZsService().getZsdByZj(bbId, dqId, kmId, njId, xq, userNo);
   				if(list != null){
   					for(ZsdBean bean : zsdList){
   						boolean isSelf = userNo.equals(bean.getUserNo());
   						bean.setSelf(isSelf);
   					}
   				}
   				dm.put("zsdList", zsdList);
   			} else {
   				List<ZsdBean> zsdList = new ZsService().getZsdByZj(bbId, dqId, kmId, njId, xq, user.getUserNo());
   				if(list != null){
   					for(ZsdBean bean : zsdList){
   						String userNo = bean.getUserNo();
   						boolean isSelf = user.getUserNo().equals(userNo);
   						bean.setSelf(isSelf);
   					}
   				}
   				dm.put("zsdList", zsdList);
   			}
   			dm.putDatas(list);
   			return dm;
   		} catch (Exception e) {
   			UIException uiex = null;
   			if(e instanceof UIException){
   				uiex = (UIException)e;
   			} else {
   				uiex = new UIException("加载章节信息失败！", e);
   			}
   			return uiex.toDM();
   		}
   	}
   	
    @RequestMapping(value="zj/add",method = RequestMethod.POST)
    @ResponseBody
    public AjaxDataModal addZj(HttpServletRequest request) {
    	try {
    		int pid = PageUtils.getIntParam(request, "pid");
    		int bbId = PageUtils.getIntParam(request, "bbId");
    		String dqId = PageUtils.getParamAndCheckEmpty(request, "dqId", "地区不能为空！");
    		int kmId = PageUtils.getIntParamAndCheckEmpty(request, "kmId", "错误的学科ID！");
			int njId = PageUtils.getIntParamAndCheckEmpty(request, "njId", "错误的年级ID！");
			int xq = PageUtils.getIntParamAndCheckEmpty(request, "xq", "未选择正确的上下学期！");
			String name = PageUtils.getParamAndCheckEmpty(request, "name", "章节名称不能为空！");
			String bm = PageUtils.getParamAndCheckEmpty(request, "bm", "章节编目不能为空！");
			int xh = PageUtils.getIntParamAndCheckEmpty(request, "xh", "未填写正确的章节序号！");
			ZjBean bean = service.saveZj(-1, dqId, bbId, kmId, njId, xq, name, bm, xh, pid);
			AjaxDataModal json = new AjaxDataModal(true);
			json.put("id", bean.getId());
			return json;
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("添加章节失败！", e);
			}
			return uiex.toDM();
		}
    }
    
    @RequestMapping(value="zj/update",method = RequestMethod.POST)
    @ResponseBody
    public AjaxDataModal updateZj(HttpServletRequest request) {
    	try {
    		int id = PageUtils.getIntParamAndCheckEmpty(request, "id", "错误的章节ID！");
    		int pid = PageUtils.getIntParam(request, "pid");
    		int bbId = PageUtils.getIntParam(request, "bbId");
    		String dqId = PageUtils.getParamAndCheckEmpty(request, "dqId", "地区不能为空！");
    		int kmId = PageUtils.getIntParamAndCheckEmpty(request, "kmId", "错误的学科ID！");
			int njId = PageUtils.getIntParamAndCheckEmpty(request, "njId", "错误的年级ID！");
			int xq = PageUtils.getIntParamAndCheckEmpty(request, "xq", "未选择正确的上下学期！");
			String name = PageUtils.getParamAndCheckEmpty(request, "name", "章节名称不能为空！");
			String bm = PageUtils.getParamAndCheckEmpty(request, "bm", "章节编目不能为空！");
			int xh = PageUtils.getIntParamAndCheckEmpty(request, "xh", "未填写正确的章节序号！");
			service.saveZj(id, dqId, bbId, kmId, njId, xq, name, bm, xh, pid);
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("修改章节失败！", e);
			}
			return uiex.toDM();
		}
    }
    
    @RequestMapping(value="zj/copy",method = RequestMethod.POST)
    @ResponseBody
    public AjaxDataModal copyZj(HttpServletRequest request) {
    	try {
    		int newBbId = PageUtils.getIntParam(request, "newBbId");
    		String newDqId = PageUtils.getParamAndCheckEmpty(request, "newDqId", "地区不能为空！");
    		int bbId = PageUtils.getIntParam(request, "bbId");
    		String dqId = PageUtils.getParamAndCheckEmpty(request, "dqId", "地区不能为空！");
    		int kmId = PageUtils.getIntParamAndCheckEmpty(request, "kmId", "错误的学科ID！");
			int njId = PageUtils.getIntParamAndCheckEmpty(request, "njId", "错误的年级ID！");
			int xq = PageUtils.getIntParamAndCheckEmpty(request, "xq", "未选择正确的上下学期！");
			service.copyZj(newDqId, newBbId, dqId, bbId, kmId, njId, xq);
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("复制章节信息失败！", e);
			}
			return uiex.toDM();
		}
    }
    
    @RequestMapping(value="zj/delete")
    @ResponseBody
    public AjaxDataModal deleteZj(HttpServletRequest request) {
    	try {
    		int id = PageUtils.getIntParamAndCheckEmpty(request, "id", "错误的章节ID！");
			service.deleteZj(id);
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("删除章节失败！", e);
			}
			return uiex.toDM();
		}
    }
	
}
