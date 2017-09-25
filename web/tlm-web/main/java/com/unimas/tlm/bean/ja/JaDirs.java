package com.unimas.tlm.bean.ja;

import com.unimas.jdbc.handler.annotation.Column;
import com.unimas.jdbc.handler.annotation.Table;
import com.unimas.tlm.bean.JdbcBean;

@Table("ja_dirs")
public class JaDirs extends JdbcBean {
	
	@Column(name="pid")
	private int pid;
	private int type = 0;
	private String name;
	@Column(name="user_no")
	private String userNo;
	
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

}
