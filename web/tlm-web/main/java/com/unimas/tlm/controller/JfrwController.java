package com.unimas.tlm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unimas.common.util.json.JSONUtils;
import com.unimas.tlm.bean.datamodal.AjaxDataModal;
import com.unimas.tlm.bean.datamodal.DataTableDM;
import com.unimas.tlm.bean.jfrw.RwListBean;
import com.unimas.tlm.bean.jfrw.RwMainBean;
import com.unimas.tlm.bean.jfrw.UserFulfilRwBean;
import com.unimas.tlm.bean.user.TeacherInfo;
import com.unimas.tlm.exception.UIException;
import com.unimas.tlm.service.MenuManage.PageView;
import com.unimas.tlm.service.jfrw.RwService;
import com.unimas.tlm.service.zs.ZsService;
import com.unimas.web.auth.AuthRealm.ShiroUser;
import com.unimas.web.utils.PageUtils;

/**
 * @author hxs
 * 知识管理请求控制器
 */
@Controller
@RequestMapping(value="/jfrw")
public class JfrwController {
	
	@Autowired
	RwService rwService;
	@Autowired
	ZsService zsService;
	
	/**
     * 发布任务页面
     * @return
     */
    @RequestMapping(value="fbrw",method = RequestMethod.GET)
    public String fbrw(HttpServletRequest request) {
    	PageUtils.setPageView(request, PageView.JFRW_FBRW);
        return  "jfrw/fbrw/index";
    }
    
    /**
     * 审核任务页面
     * @return
     */
    @RequestMapping(value="shrw",method = RequestMethod.GET)
    public String shrw(HttpServletRequest request) {
    	PageUtils.setPageView(request, PageView.JFRW_SHRW);
        return  "jfrw/shrw/index";
    }
    
    /**
     * 任务管理页面
     * @return
     */
    @RequestMapping(value="view",method = RequestMethod.GET)
    public String view(HttpServletRequest request) {
    	PageUtils.setPageView(request, PageView.JFRW_VIEW);
        return  "jfrw/view/index";
    }
    
    /**
     * 积分规则页面
     * @return
     */
    @RequestMapping(value="set",method = RequestMethod.GET)
    public String set(HttpServletRequest request) {
    	PageUtils.setPageView(request, PageView.JFRW_SET);
        return  "jfrw/set/index";
    }
    
    /**
     * 发布任务
     * @return
     */
    @RequestMapping(value="fbrw",method = RequestMethod.POST)
    @ResponseBody
    public Object saveRw(HttpServletRequest request) {
    	try {
    		String name = PageUtils.getParamAndCheckEmpty(request, "name", "任务名称不能为空！");
    		int jf = PageUtils.getIntParamAndCheckEmpty(request, "jf", "积分不能为空！");
    		int maxNum = PageUtils.getIntParamAndCheckEmpty(request, "maxNum", "请设置任务最大完成次数！");
    		String desc = PageUtils.getParamAndCheckEmpty(request, "desc", "任务描述不能为空！");
    		String _listObj = PageUtils.getParamAndCheckEmpty(request, "list", "任务详细信息不能为空！");
    		List<Map<String, Object>> list = JSONUtils.getObjFromFile(_listObj, new TypeReference<ArrayList<Map<String, Object>>>() {});
			rwService.fbrw(name, jf, maxNum, desc, list);
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("发布任务失败！", e);
			}
			return uiex.toDM();
		}
    }
    
    /**
     * 关闭任务
     * @return
     */
    @RequestMapping(value="rw/close")
    @ResponseBody
    public Object closeRw(HttpServletRequest request) {
    	try {
    		int id = PageUtils.getIntParamAndCheckEmpty(request, "id", "错误的任务ID！");
			rwService.close(id);
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("关闭任务失败！", e);
			}
			return uiex.toDM();
		}
    }
    
    /**
     * 领取任务
     * @return
     */
    @RequestMapping(value="rw/lqrw")
    @ResponseBody
    public Object lqrw(HttpServletRequest request) {
    	try {
    		int id = PageUtils.getIntParamAndCheckEmpty(request, "id", "错误的任务ID！");
    		ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			rwService.lqrw(id, user);
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("领取任务失败！", e);
			}
			return uiex.toDM();
		}
    }
    
    /**
    * 放弃任务
    * @return
    */
   @RequestMapping(value="rw/fqrw")
   @ResponseBody
   public Object fqrw(HttpServletRequest request) {
   	try {
	   		int id = PageUtils.getIntParamAndCheckEmpty(request, "id", "错误的任务ID！");
			rwService.fqrw(id);
			return new AjaxDataModal(true);
		}  catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("放弃任务失败！", e);
			}
			return uiex.toDM();
		}
   }
    
    @RequestMapping(value="rw/list")
	@ResponseBody
	public AjaxDataModal getRwList(HttpServletRequest request) {
		try {
			DataTableDM dm = new DataTableDM(0, true);
			int status = PageUtils.getIntParam(request, "status");
			List<RwMainBean> list = rwService.search(status);
			dm.putDatas(list);
			return dm;
		} catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("查询任务列表失败！", e);
			}
			return uiex.toDM();
		}
	}
    
    @RequestMapping(value="rw/dlqrw")
	@ResponseBody
	public AjaxDataModal dlqrw(HttpServletRequest request) {
		try {
			DataTableDM dm = new DataTableDM(0, true);
			ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			int kmId = 9999;
			if("teacher".equals(user.getRole()) && user.getInfo() != null){
				TeacherInfo info = (TeacherInfo)user.getInfo();
				kmId = info.getKmId();
			}
			List<RwListBean> list = rwService.dlqrw(0, kmId, user);
			dm.putDatas(list);
			return dm;
		} catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("查询任务列表失败！", e);
			}
			return uiex.toDM();
		}
	}
    
    @RequestMapping(value="rw/dwcrw")
	@ResponseBody
	public AjaxDataModal dwcrw(HttpServletRequest request) {
		try {
			DataTableDM dm = new DataTableDM(0, true);
			ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			List<UserFulfilRwBean> list = rwService.dwcrw(user);
			dm.putDatas(list);
			return dm;
		} catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("查询任务列表失败！", e);
			}
			return uiex.toDM();
		}
	}
    
    @RequestMapping(value="rw/dshrw")
	@ResponseBody
	public AjaxDataModal dshrw(HttpServletRequest request) {
		try {
			DataTableDM dm = new DataTableDM(0, true);
			int status = PageUtils.getIntParam(request, "status");
			List<UserFulfilRwBean> list = rwService.dshrw(status);
			dm.putDatas(list);
			return dm;
		} catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("查询任务列表失败！", e);
			}
			return uiex.toDM();
		}
	}
    
    @RequestMapping(value="shrw",method = RequestMethod.POST)
	@ResponseBody
	public AjaxDataModal shrwPost(HttpServletRequest request) {
		try {
			AjaxDataModal dm = new AjaxDataModal(true);
			int id = PageUtils.getIntParamAndCheckEmpty(request, "id", null);
			int status = PageUtils.getIntParamAndCheckEmpty(request, "status", "审核状态不能为空！");
			String shyj = PageUtils.getParamAndCheckEmpty(request, "shyj", "审核意见不能为空！");
			rwService.shyj(id, status, shyj);
			return dm;
		} catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("查询任务列表失败！", e);
			}
			return uiex.toDM();
		}
	}
    
}
