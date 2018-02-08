package com.unimas.tlm.controller.app;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unimas.tlm.bean.KscjBean;
import com.unimas.tlm.bean.datamodal.AjaxDataModal;
import com.unimas.tlm.bean.user.StudentInfo;
import com.unimas.tlm.exception.UIException;
import com.unimas.tlm.service.AppService;
import com.unimas.tlm.service.AppSrfxService;
import com.unimas.tlm.service.StuTestService;
import com.unimas.tlm.service.dic.DicService;
import com.unimas.tlm.service.user.UserService;
import com.unimas.web.auth.AuthRealm.ShiroUser;
import com.unimas.web.utils.PageUtils;

/**
 * 
 * @author lusl
 *
 */
@Controller
public class AppSystemController {
	@Autowired
	UserService service;
	@Autowired
	AppService appService;
	
	@Autowired
	AppSrfxService appSrfxService; 
	@Autowired
	StuTestService testService ;
	
	private boolean isReloadTxImg = false;
	
	@RequestMapping(value="/")
    public String indexRoot(Model model,HttpServletRequest request) {
		ShiroUser user = loadheader(request,false);
		loadCheckTest(request);
		if(isFirstLogin((StudentInfo)user.getInfo())){
			request.setAttribute("isFirst",true);
			initUserInfoForPage(request, user);
			return "user_profile";
		}else{
			return "index";
		}
    }

	private void loadCheckTest(HttpServletRequest request) {
		ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		Map<String,Object> data2 =  testService.checkTest(user.getUserNo());
		request.setAttribute("check",data2);
	}
	
	private ShiroUser loadheader(HttpServletRequest request,boolean isGrzy){
		ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		request.setAttribute("username",user.getLoginName());
		request.setAttribute("realName",user.getRealName());
		String tx = user.getTxImg();
		if( isGrzy ){
			String userNo = user.getUserNo();
			request.setAttribute("userNo",user.getUserId());
			if(isReloadTxImg){
				tx = new String(service.getStudentByUserNo(userNo).getTx());
				user.setTxImg(tx);
				isReloadTxImg = false;
			}
		}
		request.setAttribute("tx",tx!=null?tx:"assets/layouts/layout2/img/avatar3_small.jpg");
		return user;
	}
	
	private boolean isFirstLogin(StudentInfo info){
		boolean result = false;
		if(info.getNjId() <= 0 || info.getDqId()== null || "" .equals(info.getDqId())){
			result = true;
		}
		return result;
	}
	
	@RequestMapping(value="/index")
    public String index(Model model,HttpServletRequest request) {
		ShiroUser user = loadheader(request,false);
		loadCheckTest(request);
		if(isFirstLogin((StudentInfo)user.getInfo())){
			request.setAttribute("isFirst",true);
			initUserInfoForPage(request, user);
			return "user_profile";
		}else{
			return "index";
		}
    }
	
	@RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(HttpServletRequest request) {
    	if(SecurityUtils.getSubject().isAuthenticated()){
    		SecurityUtils.getSubject().logout();
     	}
    	return "login";
    }
	
	@RequestMapping(value = "/login",method = RequestMethod.POST)
    public String handleLogin(HttpServletRequest request,@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName,@RequestParam(FormAuthenticationFilter.DEFAULT_PASSWORD_PARAM) String password) {
		return "login";
    }
	
	@RequestMapping(value = "/register")
	@ResponseBody
    public Object studentRegister(HttpServletRequest request) {
		try {
			String username = PageUtils.getParamAndCheckEmpty(request, "username", "");
			String password = PageUtils.getParamAndCheckEmpty(request, "password", "");
			service.registerStudent(username, password);
			boolean rememberMe = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM);  
            String host = request.getRemoteHost();  
            //构造登陆令牌环  
            UsernamePasswordToken token = new UsernamePasswordToken(username, password.toCharArray(), rememberMe, host);
            //发出登陆请求  
            SecurityUtils.getSubject().login(token); 
            return new AjaxDataModal(true);
		} catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			}else{
				uiex = new UIException("服务器异常！");
			}
			return uiex.toJson();
		}
		
    }

	/**
	 * 获取个人信息页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/grzy")
    public String grzy(HttpServletRequest request) {
		loadheader(request,true);
    	return "grzy";
    }
	
	@RequestMapping(value = "/set_pwd" ,method = RequestMethod.GET)
    public String updatePwdPage(HttpServletRequest request) {
    	return "set_pwd";
    }
	
	@RequestMapping(value="updatePwd",method = RequestMethod.POST)
    @ResponseBody
    public Object updatePwd(HttpServletRequest request) {
    	try {
    		UserService service = new UserService();
    		String password = PageUtils.getParamAndCheckEmpty(request, "old_Password", "请输入旧密码！");
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
	
	@RequestMapping(value = "/getStuHeadInfo",method = RequestMethod.POST)
	@ResponseBody
    public Object getStuHeadInfo(HttpServletRequest request) {
		try {
			AjaxDataModal dm = new AjaxDataModal(true);
			ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			dm.put("username",user.getLoginName());
			dm.put("realName",user.getRealName());
			dm.put("tx",user.getTxImg());
            return dm;
		} catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			}else{
				uiex = new UIException("服务器异常！");
			}
			return uiex.toJson();
		}
    }
	
	@RequestMapping(value = "/set_tx" ,method = RequestMethod.GET)
    public String setTxPage(HttpServletRequest request) {
    	return "set_tx";
    }
	
	@RequestMapping(value = "/saveStuHeadImg",method = RequestMethod.POST)
	@ResponseBody
    public Object saveStuHeadImg(HttpServletRequest request) {
		String txImg = PageUtils.getParam(request, "headImg", null);
		try {
			AjaxDataModal dm = new AjaxDataModal(true);
			ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			appService.saveStuTxImg(user.getUserId(),txImg);
			user.setTxImg(txImg);
            return dm;
		} catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			}else{
				uiex = new UIException("服务器异常！");
			}
			return uiex.toJson();
		}
    }
	
	@RequestMapping(value = "/saveStuHeadImg2/${userId}",method = RequestMethod.POST)
	@ResponseBody
    public Object saveStuHeadImg2(HttpServletRequest request,@PathVariable int userId) {
		String txImg = PageUtils.getParam(request, "headImg", null);
		try {
			AjaxDataModal dm = new AjaxDataModal(true);
			appService.saveStuTxImg(userId,txImg);
			isReloadTxImg = true;
            return dm;
		} catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			}else{
				uiex = new UIException("服务器异常！");
			}
			return uiex.toJson();
		}
    }
	
	@RequestMapping(value = "/user_profile" ,method = RequestMethod.GET)
    public String userProfilePage(HttpServletRequest request) {
		ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		initUserInfoForPage(request, user);
    	return "user_profile";
    }

	private void initUserInfoForPage(HttpServletRequest request, ShiroUser user) {
		request.setAttribute("info", user.getInfo());
		DicService ds = new DicService();
		List<Map<String, Object>> nj_list = null;
		List<Map<String, Object>> dq_list = null;
		try {
			nj_list = ds.get("nj_dic", "id", "name", null, null, null);
			dq_list = ds.get("xzqh", "code", "name", null, "pid", "330500");
		} catch (Exception e) {
		}
		request.setAttribute("njList", nj_list);
		request.setAttribute("dqList", dq_list);
	}
	
	@RequestMapping(value = "/saveStuInfo",method = RequestMethod.POST)
	@ResponseBody
    public Object saveStuInfo(HttpServletRequest request) {
		try {
			String xs_name = PageUtils.getParam(request, "xs_name", null);
			String jz_name = PageUtils.getParam(request, "jz_name", null);
			String phone = PageUtils.getParam(request, "phone", null);
			String school = PageUtils.getParam(request, "school",null);
			int nj = PageUtils.getIntParam(request, "nj");
			String dqId = PageUtils.getParam(request, "dqId",null);

			AjaxDataModal dm = new AjaxDataModal(true);
			ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			service.saveStudentInfo(xs_name, jz_name, phone, -1, nj, user,school,dqId);
			user.setRealName(xs_name);
            return dm;
		} catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			}else{
				uiex = new UIException("服务器异常！");
			}
			return uiex.toJson();
		}
    }
	
	@RequestMapping(value = "/getStuInfo",method = RequestMethod.POST)
	@ResponseBody
    public Object getStuInfo(HttpServletRequest request) {
		try {
			AjaxDataModal dm = new AjaxDataModal(true);
			ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
            dm.put("info", user.getInfo());
            DicService ds = new DicService();
            List<Map<String, Object>> nj_list = ds.get("nj_dic", "id", "name", null, null, null);
   			dm.put("nj_list", nj_list);
   			List<Map<String, Object>> school_list = ds.get("mbxx", "id", "name", null, null, null);
   			dm.put("school_list", school_list);
			return dm;
		} catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			}else{
				uiex = new UIException("服务器异常！");
			}
			return uiex.toJson();
		}
    }
	
	@RequestMapping(value = "/reSrfx")
    public String reSrfxSetPage(HttpServletRequest request) {
		return"forward:/srfx?isReset=1";
	}
	
	@RequestMapping(value = "/srfx")
    public String srfxSetPage(HttpServletRequest request) {
		ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		String isReset = PageUtils.getParam(request, "isReset", null);
		boolean flag = false;
		try {
			if(!"1".equals(isReset)){
				flag = appService.isSetSrfx(user.getUserNo());
			}
			StudentInfo info= (StudentInfo)user.getInfo();
			if(flag){
				Map<String,Object> data = appSrfxService.dealFxReasult(info.getNjId(), info.getMbxxId(), user.getUserNo(), 1, info.getDqId());
				request.setAttribute("data", data);
			}else{
				request.setAttribute("info", info);
				DicService ds = new DicService();
				List<Map<String, Object>> nj_list = ds.get("nj_dic", "id", "name", null, null, null);
				request.setAttribute("njList", nj_list);
				List<Map<String, Object>> dqList = ds.get("xzqh", "code", "name", null, "pid", "330500");
		    	request.setAttribute("dqList", dqList);
				List<Map<String, Object>> school_list = ds.get("mbxx", "CONCAT(id,'|',dq_id)", "name", null, null, null);
				request.setAttribute("mbxxList", school_list);
			}
		} catch (Exception e) {
		}
    	if(flag){
    		return "srfx_result";
    	}else{
			return "srfx_set";

    	}
    }
	
	@RequestMapping(value = "getSrfxResult",method = RequestMethod.POST)
	@ResponseBody
    public Object getSrfxResult(HttpServletRequest request) {
		ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		try {
			int xkId = PageUtils.getIntParam(request, "xkId");
			StudentInfo info= (StudentInfo)user.getInfo();
			Map<String,Object> data = appSrfxService.dealFxReasult(info.getNjId(), info.getMbxxId(), user.getUserNo(), xkId, info.getDqId());
			AjaxDataModal dm = new AjaxDataModal(true);
			dm.put("data", data);
            return dm;
		} catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			}else{
				uiex = new UIException("服务器异常！");
			}
			return uiex.toJson();
		}
    }
	
	@RequestMapping(value = "saveSrfxSet",method = RequestMethod.POST)
	@ResponseBody
    public Object saveSrfxSet(HttpServletRequest request) {
		try {
			int njId = PageUtils.getIntParam(request, "nj");
			String dateTime = PageUtils.getParam(request, "dateTime", null);
			int yw_cj = PageUtils.getIntParam(request, "yw_cj");
			int yw_mf = PageUtils.getIntParam(request, "yw_mf");
			int kx_cj = PageUtils.getIntParam(request, "kx_cj");
			int kx_mf = PageUtils.getIntParam(request, "kx_mf");
			int yy_cj = PageUtils.getIntParam(request, "yy_cj");
			int yy_mf = PageUtils.getIntParam(request, "yy_mf");
			int sx_cj = PageUtils.getIntParam(request, "sx_cj");
			int sx_mf = PageUtils.getIntParam(request, "sx_mf");
			int sh_cj = PageUtils.getIntParam(request, "sh_cj");
			int sh_mf = PageUtils.getIntParam(request, "sh_mf");
			String xxId = PageUtils.getParam(request, "mbxx", "-2");
			int mbxxId =Integer.parseInt(xxId.split("[|]")[0]);
			
			AjaxDataModal dm = new AjaxDataModal(true);
			ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			KscjBean bean = new KscjBean();
			bean.setNjId(njId);
			bean.setKxcj(kx_cj);
			bean.setKxmf(kx_mf);
			bean.setShcj(sh_cj);
			bean.setShmf(sh_mf);
			bean.setSxcj(sx_cj);
			bean.setSxmf(sx_mf);
			bean.setUserNo(user.getUserNo());
			bean.setYwcj(yw_cj);
			bean.setYwmf(yw_mf);
			bean.setYycj(yy_cj);
			bean.setYymf(yy_mf);
			bean.setDateTime(dateTime);
			appService.saveKSCJ(bean, mbxxId, user.getUserId());
			((StudentInfo)user.getInfo()).setMbxxId(mbxxId);
            return dm;
		} catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			}else{
				uiex = new UIException("服务器异常！");
			}
			return uiex.toJson();
		}
    }
	
	@RequestMapping(value = "czgj")
    public String czgj(HttpServletRequest request) {
		ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		String userNo = user.getUserNo();
		Map<String,Object> data = appService.getHistoryScort(userNo);
		request.setAttribute("data", data);
		return "czgj";
	}
	
	@RequestMapping(value = "{kmId}/stuTest")
	@SuppressWarnings("unchecked")
    public String stuTestPage(HttpServletRequest request,@PathVariable int kmId ) {
		ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		int njId = ((StudentInfo)user.getInfo()).getNjId();
		request.getSession().setAttribute("kmId", kmId);
		Map<String,Object> info = testService.loadStuTestPage(njId, kmId);
		if(info.get("errMsg") != null ){
			request.setAttribute("msg", info.get("errMsg"));
		}else{
			List<Object> ids =  (List<Object>)info.get("ids");
			request.getSession().setAttribute("testIds", ids);
			Map<String,Object> data = (Map<String,Object>) info.get("oneCstInfo");
			request.getSession().setAttribute("rightAnswer", data.get("answer"));
			request.setAttribute("data", data);
			request.setAttribute("num",  ids.size());
		}
		
		return "stu_test";
	}
	
	@RequestMapping(value = "getOneCst",method = RequestMethod.POST)
	@ResponseBody
    public Object getOneCstBySeq(HttpServletRequest request) {
		int seq = PageUtils.getIntParam(request, "seq");
		List<Object> ids = (List<Object>)request.getSession().getAttribute("testIds");
		Map<String, Object> data = testService.getOneCst((Integer)ids.get(seq));
		HttpSession session = request.getSession();
		String str = session.getAttribute("rightAnswer") +";" + data.get("answer");
		session.setAttribute("rightAnswer",str );
		AjaxDataModal dm = new AjaxDataModal(true);
		dm.putAll(data);
		return dm;
	}
	
	@RequestMapping(value = "testOverSave" ,method = RequestMethod.POST)
	@ResponseBody
	public Object testOverSave( HttpServletRequest request ){
		try{
			ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			String answerArray = PageUtils.getParam(request, "answerArray", null);
			String zhuguanArray = PageUtils.getParam(request, "zhuguanArray", null);
			String time = PageUtils.getParam(request, "time", null);
			List<Object> idsList = (List<Object>)request.getSession().getAttribute("testIds");
			String rightAnswers = (String)request.getSession().getAttribute("rightAnswer");
			String ids = StringUtils.join(idsList.toArray(), ","); 
			int njId = ((StudentInfo)user.getInfo()).getNjId();
			int kmId = (Integer)request.getSession().getAttribute("kmId");
			testService.saveTestResult(user.getUserNo(), njId, kmId, ids, rightAnswers, answerArray,
					zhuguanArray, time);
			AjaxDataModal dm = new AjaxDataModal(true);
			return dm;
		} catch (Exception e) {
			e.printStackTrace();
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			}else{
				uiex = new UIException("服务器异常！");
			}
			return uiex.toJson();
		}
	}
	
	@RequestMapping(value = "test_result")
    public String loadTestResultPage(HttpServletRequest request) {
		ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		String userNo = user.getUserNo();
		Map<String,Object> data = testService.getTestResult(userNo);
		Map<String,Object> csdtRecord = testService.getcstRecordByPid(String.valueOf(data.get("id")));
		if(data != null && data.size() > 0){
			request.setAttribute("data", data);
			request.setAttribute("csdtRecord", csdtRecord);
			
		}else{
			request.setAttribute("msg", "error");
		}
		return "test_result";
	}
	
	@RequestMapping(value = "pk_result")
    public String loadPkResultPage(HttpServletRequest request) {
		ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		String userNo = user.getUserNo();
		Map<String,Object> data = testService.getPkResultData(userNo);
		int overNum = testService.getOverStuNum((Integer)data.get("nj_id"), (Integer)data.get("km_id"), (Integer)data.get("total"));
		data.put("overNum", overNum);
		request.setAttribute("data", data);
		return "pk_result";
	}
	
}
