package com.unimas.tlm.controller;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.google.common.collect.Lists;
import com.unimas.tlm.bean.datamodal.AjaxDataModal;
import com.unimas.tlm.bean.datamodal.DataTableDM;
import com.unimas.tlm.bean.user.StudentInfo;
import com.unimas.tlm.bean.user.TeacherInfo;
import com.unimas.tlm.exception.UIException;
import com.unimas.tlm.service.MenuManage.PageView;
import com.unimas.tlm.service.user.UserService;
import com.unimas.web.auth.AuthRealm.ShiroUser;
import com.unimas.web.utils.PageUtils;

import sun.misc.BASE64Encoder;

/**
 * @author hxs
 * 用户管理请求控制器
 */
@Controller
@RequestMapping(value="/user")
public class UserController {
	
	@Autowired
	UserService service;
	
	/**
     * 教师管理页面
     * @return
     */
    @RequestMapping(value="teacher",method = RequestMethod.GET)
    public String cstk(HttpServletRequest request) {
    	PageUtils.setPageView(request, PageView.JSGL);
        return  "user/teacher";
    }
    
    /**
     * 教师管理页面
     * @return
     */
    @RequestMapping(value="teacher/fileupload",method = RequestMethod.GET)
    public String fileupload(HttpServletRequest request) {
    	PageUtils.setPageView(request, PageView.JSGL);
        return  "user/fileupload";
    }
    
    /**
     * 家长学生管理页面
     * @return
     */
    @RequestMapping(value="student",method = RequestMethod.GET)
    public String jzxsgl(HttpServletRequest request) {
    	PageUtils.setPageView(request, PageView.JZXSGL);
        return  "user/student";
    }
    
    /**
     * 教师注册页面
     * @return
     */
    @RequestMapping(value="teacher/register",method = RequestMethod.POST)
    public String registerTeacher(HttpServletRequest request, @RequestParam("jszgz") CommonsMultipartFile[] jszgzFiles
    		, @RequestParam("djzs") CommonsMultipartFile[] djzsFiles, @RequestParam("ryzs") CommonsMultipartFile[] ryzsFiles) {
    	try {
    		String username = PageUtils.getParamAndCheckEmpty(request, "username", "");
    		String password = PageUtils.getParamAndCheckEmpty(request, "password", "");
    		String name = PageUtils.getParamAndCheckEmpty(request, "name", "");
    		String skdz = PageUtils.getParamAndCheckEmpty(request, "skdz", "");
    		int kmId = PageUtils.getIntParamAndCheckEmpty(request, "kmId", "");
    		
    		byte[] jszgz = null;
    		if(jszgzFiles != null && jszgzFiles.length > 0){
    			InputStream inputStream = jszgzFiles[0].getInputStream();
    			jszgz = new byte[inputStream.available()];
    			inputStream.read(jszgz);
    		}
    		
    		byte[] djzs = null;
    		if(djzsFiles != null && djzsFiles.length > 0){
    			InputStream inputStream = djzsFiles[0].getInputStream();
    			djzs = new byte[inputStream.available()];
    			inputStream.read(djzs);
    		}
    		
    		byte[] ryzs = null;
    		if(ryzsFiles != null && ryzsFiles.length > 0){
    			InputStream inputStream = ryzsFiles[0].getInputStream();
    			ryzs = new byte[inputStream.available()];
    			inputStream.read(ryzs);
    		}
    		
    		service.registerTeacher(username, password, name, skdz, kmId, jszgz, djzs, ryzs);
    		
    		boolean rememberMe = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM);  
            String host = request.getRemoteHost();  
            //构造登陆令牌环  
            UsernamePasswordToken token = new UsernamePasswordToken(username, password.toCharArray(), rememberMe, host);
            //发出登陆请求  
            SecurityUtils.getSubject().login(token);  
    		return "redirect:/";
    	} catch (Exception e) {
    		e.printStackTrace();
    		return  "redirect:/login";
		}
    }
    
    @RequestMapping(value="student/list")
	@ResponseBody
	public AjaxDataModal getStudents(HttpServletRequest request) {
		try {
			String key = PageUtils.getParam(request, "key", "");
			DataTableDM dm = new DataTableDM(0, true);
			List<StudentInfo> list = service.queryStudents(key);
			dm.putDatas(list);
			return dm;
		} catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("加载学长家长信息失败！", e);
			}
			return uiex.toDM();
		}
	}
    
    @SuppressWarnings("unchecked")
	@RequestMapping(value="teacher/list")
	@ResponseBody
	public AjaxDataModal getTeachers(HttpServletRequest request) {
		try {
			String key = PageUtils.getParam(request, "key", "");
			DataTableDM dm = new DataTableDM(0, true);
			List<TeacherInfo> list = service.queryTeachers(key);
			List<Map<String, Object>> l = Lists.newArrayList();
			if(list != null){
				BASE64Encoder base64Encoder = new BASE64Encoder();
				for(TeacherInfo info : list){
					Map<String, Object> map = PropertyUtils.describe(info);
					byte[] jszgz = info.getJszgz();
					if(jszgz != null){
						map.put("jszgz", "data:image/jpeg;base64,"+base64Encoder.encode(jszgz));
					}
					byte[] djzs = info.getDjzs();
					if(djzs != null){
						map.put("djzs", "data:image/jpeg;base64,"+base64Encoder.encode(djzs));
					}
					byte[] ryzs = info.getRyzs();
					if(ryzs != null){
						map.put("ryzs", "data:image/jpeg;base64,"+base64Encoder.encode(ryzs));
					}
					l.add(map);
				}
			}
			dm.putDatas(l);
			return dm;
		} catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("加载教师信息失败！", e);
			}
			return uiex.toDM();
		}
	}
	
    @RequestMapping(value="teacher/saveTxImg",method = RequestMethod.POST)
    @ResponseBody
    public AjaxDataModal saveTxImg(HttpServletRequest request) {
    	String img = PageUtils.getParam(request, "img", "");
    	String x1 = PageUtils.getParam(request, "x1", "0");
    	String y1 = PageUtils.getParam(request, "y1", "0");
    	String x2 = PageUtils.getParam(request, "x2", "100");
    	String y2 = PageUtils.getParam(request, "y2", "100");
		try {
			AjaxDataModal dm = new AjaxDataModal(true);
			ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			String dataImg =  service.dealTxImage(img, Integer.parseInt(x1), Integer.parseInt(y1), Integer.parseInt(x2), Integer.parseInt(y2));
			service.saveTxImg(user.getUserId(),dataImg);
			user.setTxImg(dataImg);
			dm.put("dataImg",dataImg);
			return dm;
		} catch (Exception e) {
			UIException uiex = new UIException("保存图像信息成功！", e);
			return uiex.toDM();
		}
		
    }
    @RequestMapping(value="teacher/getTeacherInfo",method = RequestMethod.POST)
    @ResponseBody
    public AjaxDataModal getTeacherInfo(HttpServletRequest request) {
    	AjaxDataModal dm = new AjaxDataModal(true);
    	ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
    	try {
    		TeacherInfo info = service.getTeacherByUserNo(user.getUserNo());
    		
    		BASE64Encoder base64Encoder = new BASE64Encoder();
    		Map<String, Object> map = new HashMap<String, Object>();;
			byte[] tx = info.getTx();
    		if(tx != null){
				map.put("txImg", new String(tx));
	    		info.setTx(null);
			}
    		byte[] jszgz = info.getJszgz();
			if(jszgz != null){
				map.put("jszgz", "data:image/jpeg;base64,"+base64Encoder.encode(jszgz));
				info.setJszgz(null);
			}
			byte[] djzs = info.getDjzs();
			if(djzs != null){
				map.put("djzs", "data:image/jpeg;base64,"+base64Encoder.encode(djzs));
				info.setDjzs(null);
			}
			byte[] ryzs = info.getRyzs();
			if(ryzs != null){
				map.put("ryzs", "data:image/jpeg;base64,"+base64Encoder.encode(ryzs));
				info.setRyzs(null);
			}
			dm.put("file",map);
			dm.put("info",info);
		} catch (Exception e) {
		}
    	
    	return dm ;
    }
    
    @RequestMapping(value="teacher/saveInfo",method = RequestMethod.POST)
    @ResponseBody
    public AjaxDataModal saveTeacherInfo(HttpServletRequest request, @RequestParam(value ="jszgz" , required = false) MultipartFile jszgzFiles
    		, @RequestParam(value ="djzs", required = false) MultipartFile djzsFiles, @RequestParam(value ="ryzs", required = false) MultipartFile ryzsFiles) {
    	AjaxDataModal dm = new AjaxDataModal(true);
    	try {
    		int id = PageUtils.getIntParamAndCheckEmpty(request, "id", "");
    		String name = PageUtils.getParam(request, "name", null);
			String skdz = PageUtils.getParam(request, "skdz", null);
			int sex = PageUtils.getIntParamAndCheckEmpty(request, "sex", "0");
			int kmId = PageUtils.getIntParamAndCheckEmpty(request, "kmId", null);
		
			byte[] jszgz = null;
			if(jszgzFiles != null ){
				InputStream inputStream = jszgzFiles.getInputStream();
				jszgz = new byte[inputStream.available()];
				inputStream.read(jszgz);
			}
			
			byte[] djzs = null;
			if(djzsFiles != null ){
				InputStream inputStream = djzsFiles.getInputStream();
				djzs = new byte[inputStream.available()];
				inputStream.read(djzs);
			}
			
			byte[] ryzs = null;
			if(ryzsFiles != null ){
				InputStream inputStream = ryzsFiles.getInputStream();
				ryzs = new byte[inputStream.available()];
				inputStream.read(ryzs);
			}
			service.saveTeacherInfo(id,name,sex,skdz,kmId,jszgz,djzs,ryzs);
			//用户名修改后，更新缓存内的信息
			ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			user.setRealName(name);
			
			dm.put("msg", "保存个人信息成功！");
    	} catch (Exception e) {
    		UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("保存个人信息失败！", e);
			}
			return uiex.toDM();
		}
    	return dm;
    }
    
    
    @RequestMapping(value="teacher/getGxd_top10",method = RequestMethod.POST)
    @ResponseBody
    public AjaxDataModal getGxd_top10(HttpServletRequest request) {
    	try {
			DataTableDM dm = new DataTableDM(0, true);
			ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			String userNo =  user.getUserNo();
			List<Map<String ,Object>> list = service.getGxd_top10(userNo);
			dm.putDatas(list);
			return dm;
		} catch (Exception e) {
			UIException uiex = null;
			if(e instanceof UIException){
				uiex = (UIException)e;
			} else {
				uiex = new UIException("获取前十贡献名称列表失败！", e);
			}
			return uiex.toDM();
		}
    }
    
}
