package com.unimas.txl.controller;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unimas.txl.service.LxrService;

@Controller
@RequestMapping(value="services")
public class ServicesController {

	/**
     * 联系人注册的二维码请求
     *
     * @return
	 * @throws Exception 
     */
    @RequestMapping(value="qrcode/lxrzc")
    public void lxrzc(int lryId, final HttpServletResponse response) throws Exception {
    	response.setContentType("image/png; charset=utf-8");  
        ByteArrayOutputStream output = new LxrService().getLxrzcQrcode(lryId);
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
    @RequestMapping(value="qrcode/lxrzc/download")
    public void download(int lryId, final HttpServletResponse response) throws Exception {
    	response.reset();  
    	ByteArrayOutputStream output = new LxrService().getLxrzcQrcode(lryId);
    	byte[] bs = output.toByteArray();
        response.setHeader("Content-Disposition", "attachment; filename=\"qrcode.png\"");  
        response.addHeader("Content-Length", "" + bs.length);  
        response.setContentType("application/octet-stream;charset=UTF-8");  
        OutputStream os = response.getOutputStream();
		os.write(output.toByteArray());
		os.flush();
		os.close();
    }
	
}
