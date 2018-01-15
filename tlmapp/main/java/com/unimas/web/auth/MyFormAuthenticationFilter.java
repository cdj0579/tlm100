package com.unimas.web.auth;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

public class MyFormAuthenticationFilter extends FormAuthenticationFilter {

	@Override
	protected boolean executeLogin(ServletRequest arg0, ServletResponse arg1) throws Exception {
		arg0.setCharacterEncoding("utf-8");
		return super.executeLogin(arg0, arg1);
	}

}
