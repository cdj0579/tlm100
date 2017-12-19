package com.unimas.txl.controller;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.unimas.common.qrcode.QrcodeUtils;
import com.unimas.txl.bean.user.ShiYongZheInfo;
import com.unimas.web.auth.AuthRealm.ShiroUser;
import com.unimas.web.utils.PageUtils;


@Controller
@RequestMapping(value="share")
public class ServicesController {

	/**
     * 联系人注册的二维码请求
     *
     * @return
	 * @throws Exception 
     */
    @RequestMapping(value="/qrcode")
    public void lxrzc(HttpServletRequest request, final HttpServletResponse response) throws Exception {
    	response.setContentType("image/png; charset=utf-8");  
        ByteArrayOutputStream output = new QrcodeUtils().genQrcodeImage(getContent(request), false);
		OutputStream os = response.getOutputStream();
		os.write(output.toByteArray());
		os.flush();
		os.close();
    }
    
    /**
     * 联系人注册的二维码下载
     *
     * @return
	 * @throws Exception 
     */
    @RequestMapping(value="/qrcode/download")
    public void download(HttpServletRequest request, final HttpServletResponse response) throws Exception {
    	response.reset();  
    	ByteArrayOutputStream output = new QrcodeUtils().genQrcodeImage(getContent(request), false);
    	byte[] bs = output.toByteArray();
        response.setHeader("Content-Disposition", "attachment; filename=\"qrcode.png\"");  
        response.addHeader("Content-Length", "" + bs.length);  
        response.setContentType("application/octet-stream;charset=UTF-8");  
        OutputStream os = response.getOutputStream();
		os.write(output.toByteArray());
		os.flush();
		os.close();
    }
	
    private String getContent(HttpServletRequest request){
    	ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
    	int jgId = user.getJgId();
    	int lryId = ((ShiYongZheInfo)user.getInfo()).getLryId();
    	String url = PageUtils.getSystemUrl(request);
    	url += "fx/"+jgId+"/"+lryId ;
    	return url;
    }
    
    @RequestMapping(value="/page",method = RequestMethod.GET)
    public String loadSetPwd(HttpServletRequest request) {
    	ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
    	int lryId = ((ShiYongZheInfo)user.getInfo()).getLryId();
    	if(lryId < 0){
    		request.setAttribute("isnull", "1");
    	}else{
    		request.setAttribute("isnull", "0");
    	}
    	return "app/share";
    }
}
