package com.unimas.txl.bean;

import com.unimas.jdbc.handler.annotation.Column;
import com.unimas.jdbc.handler.annotation.Table;

@Table("txl_guanliyuan")
public class GuanliyuanBean extends JdbcBean implements java.io.Serializable {
	
	/**
	 * 
	 */
	@Column(ignore=true)
	private static final long serialVersionUID = 1L;
	
	@Column(name="jiaoshi_id", nullNumberValue=-1)
	private int jiaoshiId = -1;
	@Column(name="jigou_id", nullNumberValue=-1)
	private int jigouId = -1;
	
	@Column(ignore=true,name="user_no")
	private String userNo;
	@Column(ignore=true)
	private String name;
	
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public int getJiaoshiId() {
		return jiaoshiId;
	}
	public void setJiaoshiId(int jiaoshiId) {
		this.jiaoshiId = jiaoshiId;
	}
	public int getJigouId() {
		return jigouId;
	}
	public void setJigouId(int jigouId) {
		this.jigouId = jigouId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
