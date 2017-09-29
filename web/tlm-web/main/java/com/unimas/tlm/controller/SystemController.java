package com.unimas.tlm.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unimas.tlm.bean.datamodal.AjaxDataModal;
import com.unimas.tlm.bean.datamodal.DataTableDM;
import com.unimas.tlm.bean.jfrw.RwBean;
import com.unimas.tlm.bean.user.TeacherInfo;
import com.unimas.tlm.exception.UIException;
import com.unimas.tlm.service.IndexService;
import com.unimas.tlm.service.MenuManage.PageView;
import com.unimas.tlm.service.dic.DicService;
import com.unimas.tlm.service.user.UserService;
import com.unimas.web.auth.AuthRealm.ShiroUser;
import com.unimas.web.utils.PageUtils;

/**
 * <p>描述: 系统请求控制器</p>
 * @author hxs
 * @date 2016年12月29日 上午10:24:18
 */
@Controller
public class SystemController {
	@Autowired
	IndexService indexService;
	/**
     * 访问主页面的请求
     *
     * @return
     */
    @RequestMapping(value="/")
    public String index(Model model,HttpServletRequest request) {
    	PageUtils.setPageView(request, PageView.HOME);
    	ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
    	Map<String, Object> map = null;
    	map = indexService.getIndexNumInfo(user.getUserNo());    	
    	request.setAttribute("map", map);
    	if("teacher".equals(user.getRole())){
    		return "teacher/index";
    	} else {
    		return "home/index";
    	}
    }
    
    /**
     * 取得登录页面
     * 如果已经登陆则直接跳转至主页
     *
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(HttpServletRequest request) {
    	try {
			List<Map<String, Object>> l = new DicService().get("km_dic", "id", "name", null, null, null);
			request.setAttribute("kmList", l);
		} catch (Exception e) {}
        return "login";
    }
    
    /**
     * 使用shiro后登录失败才会跳转到这里
     *
     * @param userName 登录用户的用户名
     *
     *
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String handleLogin(HttpServletRequest request,@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName,@RequestParam(FormAuthenticationFilter.DEFAULT_PASSWORD_PARAM) String password) {
    	return "login";
    }
	
	/**
     * 修改密码
     *
     * @return
     */
    @RequestMapping(value="system/updatePwd",method = RequestMethod.GET)
    public String updatePwd(Model model,HttpServletRequest request) {
        return  "system/updatePwd.modal";
    }
    
    /**
     * 系统设置
     *
     * @return
     */
    @RequestMapping(value="system/setting")
    public String setting(Model model,HttpServletRequest request) {
        return  "system/setting.modal";
    }
    
    /**
     * 修改密码
     *
     * @return
     */
    @RequestMapping(value="system/updatePwd",method = RequestMethod.POST)
    @ResponseBody
    public Object updatePwd(HttpServletRequest request) {
    	try {
    		UserService service = new UserService();
    		String password = PageUtils.getParamAndCheckEmpty(request, "oldPassword", "请输入旧密码！");
    		String newPassword = PageUtils.getParamAndCheckEmpty(request, "password", "请输入新密码！");
    		ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
    		service.updatePwd(password, newPassword, user);
			return new AjaxDataModal(true);
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
    
    /**
     * 保持在线状态
     *
     * @return
     */
    @RequestMapping(value="system/keepOnline")
    @ResponseBody
    public Object keepOnline(HttpServletRequest request) {
    	return new AjaxDataModal(true);
    }
    
    /**
     * 锁定页面
     *
     * @return
     */
    @RequestMapping(value="system/lock",method = RequestMethod.GET)
    public String lock(Model model,HttpServletRequest request) {
    	HttpSession session = request.getSession();
    	session.setAttribute("locked", true);
    	String referer = getHeader(request, "Referer");
    	if(referer == null) {
    		referer = "/";
    	}
    	session.setAttribute("lockReferer", referer);
        return  "lock";
    }
    
    public static String getHeader(HttpServletRequest request, String name){
    	Enumeration<?> names = request.getHeaderNames();
    	String header = null;
    	while(names.hasMoreElements()){
    		String param = (String)names.nextElement();
    		if(param.equalsIgnoreCase(name)){
    			header = request.getHeader(param);
    			break;
    		}
    	}
    	return header;
    }
    
    /**
     * 解除锁定
     *
     * @return
     * @throws IOException 
     */
    @RequestMapping(value="system/lock",method = RequestMethod.POST)
    public String unlock(Model model,HttpServletRequest request, HttpServletResponse response) throws IOException {
    	try {
    		UserService service = new UserService();
    		String password = PageUtils.getParamAndCheckEmpty(request, "password", "请输入用户密码！");
    		ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
    		service.verify(user.getLoginName(), password);
    		HttpSession session = request.getSession();
    		session.removeAttribute("locked");
    		String referer = (String)session.getAttribute("lockReferer");
    		WebUtils.redirectToSavedRequest(request, response, referer);
    		return null;
    	} catch(Exception e) {
    		request.setAttribute("errorMsg", e.getMessage());
    		return "lock";
    	}
    }
    
    /**
     * 我的信息页面
     *
     * @return
     * @throws IOException 
     */
    @RequestMapping(value="user/info",method = RequestMethod.GET)
    public String userInfo(HttpServletRequest request) throws IOException {
    	PageUtils.setPageView(request, PageView.USER);
    	return  "user/info";
    }
    
    /**
     * 贡献排行榜页面
     *
     * @return
     * @throws IOException 
     */
    @RequestMapping(value="user/gxphb",method = RequestMethod.GET)
    public String userGxphb(HttpServletRequest request) throws IOException {
    	PageUtils.setPageView(request, PageView.GXPHB);
    	return  "user/gxphb";
    }
    
    @RequestMapping(value="system/gxphb")
    @ResponseBody
    public AjaxDataModal teacherGxphb(HttpServletRequest request){
    	String type = PageUtils.getParam(request, "type", null);
    	try {
			DataTableDM dm = new DataTableDM(0, true);
			ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			String userNo =  user.getUserNo();
			List<Map<String ,Object>> list = indexService.getTeacherGxphb(userNo,type);
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
    /**
     * 管理员首页柱状图数据获取
     * @param request
     * @return
     */
    @RequestMapping(value="system/indexChart",method = RequestMethod.POST)
    @ResponseBody
    public AjaxDataModal indexChart(HttpServletRequest request){
    	String type = PageUtils.getParam(request, "type", null);
    	AjaxDataModal dm = new AjaxDataModal(true);
    	Map<String ,Object> data = indexService.getIndexChartInfo(type); 
    	dm.putAll(data);
    	return dm;
    }
    
    
}
