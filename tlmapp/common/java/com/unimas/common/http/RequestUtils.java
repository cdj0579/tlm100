package com.unimas.common.http;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * 描述:  网络请求工具
 *
 * 作者:szpShang<br/>
 * 创建时间:2011-12-24<br/>
 * 版本号:1.0<br/>
 * 版权所有:杭州合众信息工程有限公司
 */
public class RequestUtils {

	/**
	 * 创建http 代理模式 还是https 单项认证模式
	 * @param httpOrhttps
	 * @return
	 */
	public static Request WebPorxyFactory(String httpOrhttps){
		if(httpOrhttps.equalsIgnoreCase("http")){
			return new HttpRequest();
		}else{
			return new HttpsRequest();
		}
	}
	
	/**
	 * 创建双向认证对象
	 * @param certificatePath	//证书路径
	 * @param certificatePwd	//证书密码
	 * @return
	 * @throws IOException
	 * @throws GeneralSecurityException 
	 */
	public static Request WebPorxyFactory(File certificatePath,String certificatePwd) throws IOException, GeneralSecurityException{
		return new HttpsRequest(certificatePath,certificatePwd);
	}
	
}