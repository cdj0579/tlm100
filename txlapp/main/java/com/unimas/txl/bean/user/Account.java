package com.unimas.txl.bean.user;

import com.unimas.jdbc.handler.annotation.Column;
import com.unimas.jdbc.handler.annotation.Table;
import com.unimas.txl.bean.JdbcBean;

@Table("account")
public class Account extends JdbcBean {
	
	@Column(name="user_no")
	private String userNo;
	private String username;
	private String password;
	
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
