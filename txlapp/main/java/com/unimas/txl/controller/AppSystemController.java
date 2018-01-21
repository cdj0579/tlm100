package com.unimas.txl.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unimas.common.date.DateUtils;
import com.unimas.txl.bean.user.ShiYongZheInfo;
import com.unimas.txl.service.AppIndexService;
import com.unimas.txl.service.user.UserService;
import com.unimas.web.auth.AuthRealm.ShiroUser;
import com.unimas.web.bean.datamodal.AjaxDataModal;
import com.unimas.web.exception.UIException;
import com.unimas.web.utils.PageUtils;

/**
 * <p>描述: 系统请求控制器</p>
 * @author hxs
 * @date 2016年12月29日 上午10:24:18
 */
@Controller
@RequestMapping(value="/app")
public class AppSystemController {
	@Autowired
	UserService service;
	@Autowired
	AppIndexService indexService;
	/**
     * 访问主页面的请求
     *
     * @return
     */
    @RequestMapping(value="/index")
    public String index(Model model,HttpServletRequest request) {
    	return "app/index";
    }
    
    @RequestMapping(value="/")
    public String indexRoot(Model model,HttpServletRequest request) {
    	return "app/index";
    }
    
    /**
     * 取得登录页面
     * 如果已经登陆则直接跳转至主页
     *
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(HttpServletRequest request) {
    	System.out.println("lusl===GET=login===" );
    	if(SecurityUtils.getSubject().isAuthenticated()){
    		System.out.println("lusl===GET=logout===" );
    		SecurityUtils.getSubject().logout();
     	}
    	return "app/login";
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
    	String result = "app/login";
    	/*ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipal().getPrimaryPrincipal();
     	if( null != user){
     		Connection conn = null;
	    	 try {
	         	conn = DBFactory.getConn();
		    	UserService service = new UserService();
		    	String userNo = service.verify(conn, userName, password);
		    	if(userNo.equals(user.getUserNo())){
		    		result = "app/index";
		    	}
	         } catch (Exception e) {
				e.printStackTrace();
			}finally {
	 			DBFactory.close(conn, null, null);
	 		}
    		
    	} */
    	return result ;
    }
	
    @RequestMapping(value="setPwd",method = RequestMethod.GET)
    public String loadSetPwd(HttpServletRequest request) {
    	return "app/set_pwd";
    }
    
    @RequestMapping(value="updatePwd",method = RequestMethod.POST)
    @ResponseBody
    public Object updatePwd(HttpServletRequest request) {
    	try {
    		String password = PageUtils.getParam(request, "old_password", "");
    		String newPassword = PageUtils.getParam(request, "password", "");
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
	
    @RequestMapping(value="getOneLianxiren",method = RequestMethod.POST)
    @ResponseBody
    public Object getOneFenpeiLianxiren(HttpServletRequest request) {
    	try {
    		String fpId = PageUtils.getParam(request, "fpId","0");
    		ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
    		Map<String, Object> data =indexService.queryOneFenPeiLianxiren(user.getJgId(),user.getUserId()+"", fpId);
    		AjaxDataModal dm = new AjaxDataModal(true);
    		if( data == null){
    			dm.put("over" , true );
    		}else{
    			dm.put("over" , false );
    			dm.putAll(data);
    		}
			return dm;
		}  catch (Exception e) {
			e.printStackTrace();
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException(e.getMessage(), e);
			}
			return uiex.toDM();
		}
    }
    
    @RequestMapping(value="getgGxORGzLianxiren",method = RequestMethod.POST)
    @ResponseBody
    public Object getgGxORGzLianxiren(HttpServletRequest request) {
    	try {
    		String isGx = PageUtils.getParam(request, "isGx","gx");
    		ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
    		List<Map<String, Object>> data =indexService.queryGzOrGxLianxiren(user.getJgId(),user.getUserId()+"", "gx".equals(isGx));
    		AjaxDataModal dm = new AjaxDataModal(true);
    		dm.put("rows",data);
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
    
    @RequestMapping(value="saveBeizhu",method = RequestMethod.POST)
    @ResponseBody
    public Object saveBeizhu(HttpServletRequest request) {
    	try {
    		int lxrId = PageUtils.getIntParam(request, "lxrId");
    		String beizhu = PageUtils.getParam(request, "beizhu","");
    		String type = PageUtils.getParam(request, "type","gz");
    		ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
    		int jgId = user.getJgId();
    		if("gz".equals(type)){
    			int isBz = PageUtils.getIntParam(request, "isBz");//1:备注；0：关注
    			indexService.updateLianxirenBeizhu(lxrId, beizhu);
    			if(isBz == 0){
    				int glId = indexService.isExistsInfo(lxrId, user.getUserId(), false);
    				if(glId == -1){
    					if( indexService.isUpperLimit(jgId, user.getUserId()) ){
        					throw new UIException("over");
        				}
    					indexService.saveGuanZhuInfo(-1,user.getUserId(), lxrId, DateUtils.formatDateToHMS(new Date()),jgId);
    				}
    			}
    		}else if("gx".equals(type)){
    			String time = null;
    			int glId = indexService.isExistsInfo(lxrId, user.getUserId(), true);
    			if(glId == -1){
    				time = DateUtils.formatDateToHMS(new Date());
    			}
        		indexService.saveQianYueInfo(glId,user.getUserId(), lxrId,beizhu,time,jgId);
    		}
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
    @RequestMapping(value="updateCall",method = RequestMethod.POST)
    @ResponseBody
    public Object updateCall(HttpServletRequest request) {
    	try {
    		int fpId = PageUtils.getIntParam(request, "fpId");
    		indexService.updateCall(fpId);
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
    
    
    @RequestMapping(value="setShare",method = RequestMethod.GET)
    public String loadSetShare(HttpServletRequest request) {
    	
    	try {
			List<Map<String, Object>> fileList = indexService.get("txl_base_tupian", "id", "tupian", null, null, null,null);
			request.setAttribute("imgList", fileList);
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
    	int lryId = ((ShiYongZheInfo)user.getInfo()).getLryId();
    	request.setAttribute( "lryId", lryId );
    	HttpSession session = request.getSession();
    	Map<String,	Object> dataMap= (Map<String,Object>)session.getAttribute("setShareInfo");
    	if(dataMap == null){
    		dataMap = indexService.queryShareInfo(user.getUserNo());
    		session.setAttribute("setShareInfo", dataMap);
    	}
    	dataMap = indexService.dealShareContent(dataMap);
    	request.setAttribute("tupId", dataMap.get("tupId"));
    	request.setAttribute("title_main", dataMap.get("mainTitle"));
		request.setAttribute("title_sub", dataMap.get("subTitle"));
		request.setAttribute("modelList", dataMap.get("modelList"));
    	return "app/set_share";
    }
   
    @RequestMapping(value="saveShareInfo",method = RequestMethod.POST)
    @ResponseBody
    public Object saveShareInfo(HttpServletRequest request) {
    	try {
    		ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
    		boolean flag = PageUtils.getBooleanParam(request, "flag");
    		String tupId = PageUtils.getParam(request, "tupId","0");
    		String mainTitle = PageUtils.getParam(request,"mainTitle",null);
    		String subTitle = PageUtils.getParam(request,"subTitle","");
    		//模块与模块间以\u0002分割；模块标题与模块段落或行间以\u0001分割；段落或行与段落或行间以\u0003分割
    		String modelValue = PageUtils.getParam(request,"content","");
    		HttpSession session = request.getSession();
    		Map<String,	Object> map = new HashMap<String, Object>();
    		map.put("tupId",tupId);
    		map.put("mainTitle",mainTitle);
    		map.put("subTitle",subTitle);
    		map.put("content",modelValue);
    		session.setAttribute("setShareInfo", map);
    		if(flag){
    			indexService.saveShareInfo(map, user.getUserNo());
    		}
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
    
    @RequestMapping(value="unSetShare",method = RequestMethod.POST)
    @ResponseBody
    public Object unSetShare(HttpServletRequest request) {
    	HttpSession session = request.getSession();
    	session.removeAttribute("setShareInfo");
    	AjaxDataModal dm = new AjaxDataModal(true);
		return dm;
    }
    
    @RequestMapping(value="showShare",method = RequestMethod.GET)
    public String loadShowShare(HttpServletRequest request) {
    	ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
    	String flag = PageUtils.getParam(request, "flag","");
    	Map<String, Object> data = null;
    	ShiYongZheInfo userInfo = (ShiYongZheInfo)user.getInfo();
    	int lryId = userInfo.getLryId();
		int jgId = user.getJgId();
		if(lryId <= 0){
			ShiYongZheInfo u = service.getShiYongZheByUserNo(user.getUserNo()); 
			if(u != null){
				lryId = u.getLryId();
				if(lryId > 0){
					userInfo.setLryId(lryId);
				}
			}
		}
    	if("looking".equals(flag)){ //浏览设置分享内容效果数据
    		HttpSession session = request.getSession();
    		data= (Map<String,Object>)session.getAttribute("setShareInfo");
    		data = indexService.dealShareContent(data);
    		data.put("jgId", jgId);
    		data.put("lryId", lryId);
    		String tupId = (String)data.get("tupId");
    		data.put("imgName", indexService.queryImgNameById(tupId));
    		System.out.println(data);
    	}else{   //首页上进入查看分享内容效果数据
			if(lryId <= 0){
				request.setAttribute("isNull", "2");
			}else{
		    	data = indexService.queryShareInfo2(lryId+"", jgId+"");
		    	if(data == null){
		    		request.setAttribute("isNull", "1");
		    	}
			}
    	}
    	request.setAttribute("data", data);
    	return "app/showShare";
    }
}
