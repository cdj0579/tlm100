package com.unimas.txl.bean;

import com.unimas.jdbc.handler.annotation.Column;
import com.unimas.jdbc.handler.annotation.Table;
import com.unimas.jdbc.handler.entry.SelectSqlModal.LeftField;

@Table("txl_luruyuan")
public class LryBean extends JdbcBean {
	
	@Column(name="jigou_id", nullNumberValue=-1)
	private int jigouId = -1;
	@Column(name="user_no")
	private String userNo;
	@Column(ignore=true)
	@LeftField(name="username", joinTable="account", joinField="user_no", refField="userNo")
	private String username;
	private String name;
	@Column(name="is_del", nullNumberValue=-1)
	private int isDel = -1;
	
	public int getJigouId() {
		return jigouId;
	}
	public void setJigouId(int jigouId) {
		this.jigouId = jigouId;
	}
	public int getIsDel() {
		return isDel;
	}
	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
