package com.unimas.tlm.bean;

import com.unimas.jdbc.handler.annotation.Column;
import com.unimas.jdbc.handler.annotation.DefaultValue;
import com.unimas.jdbc.handler.annotation.Table;
import com.unimas.jdbc.handler.annotation.ToType;
import com.unimas.tlm.bean.JdbcBean;
import com.unimas.tlm.service.hyd.aspect.annotations.HydPointcut;
import com.unimas.web.auth.AuthRealm.ShiroUser;

@Table("hyd_list")
public class HydListBean extends JdbcBean {
	
	@Column(name="rule")
	private String rule;
	@Column(name="user_no")
	private String userNo;
	@Column(name="insert_time", toType=ToType.DateToString,insertValue=DefaultValue.Now)
	private String insertTime;
	
	@Column(ignore=true)
	private HydPointcut hpc;
	@Column(ignore=true)
	private ShiroUser user;
	
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	public HydPointcut getHpc() {
		return hpc;
	}
	public void setHpc(HydPointcut hpc) {
		this.hpc = hpc;
	}
	public ShiroUser getUser() {
		return user;
	}
	public void setUser(ShiroUser user) {
		this.user = user;
	}

}
