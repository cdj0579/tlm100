package com.unimas.txl.bean;

import com.unimas.jdbc.handler.annotation.Column;
import com.unimas.jdbc.handler.annotation.Table;
import com.unimas.jdbc.handler.entry.SelectSqlModal.LeftField;

@Table("txl_shiyongzhe")
public class SyzBean extends JdbcBean {
	
	@Column(name="jigou_id", nullNumberValue=-1)
	private int jigouId = -1;
	@Column(name="user_no")
	private String userNo;
	@Column(ignore=true)
	@LeftField(name="username", joinTable="account", joinField="user_no", refField="userNo")
	private String username;
	private String name;
	@Column(name="dq_id")
	private String dqId;
	@Column(ignore=true)
	@LeftField(name="name", joinTable="xzqh", joinField="code", refField="dqId")
	private String dqName;
	@Column(name="is_del", nullNumberValue=-1)
	private int isDel = -1;
	private int cishu = 0;
	
	public String getDqId() {
		return dqId;
	}
	public void setDqId(String dqId) {
		this.dqId = dqId;
	}
	public int getJigouId() {
		return jigouId;
	}
	public void setJigouId(int jigouId) {
		this.jigouId = jigouId;
	}
	public String getDqName() {
		return dqName;
	}
	public void setDqName(String dqName) {
		this.dqName = dqName;
	}
	public int getIsDel() {
		return isDel;
	}
	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}
	public int getCishu() {
		return cishu;
	}
	public void setCishu(int cishu) {
		this.cishu = cishu;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

}
