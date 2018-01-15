package com.unimas.tlm.bean.ja;

import com.unimas.jdbc.handler.annotation.Column;
import com.unimas.jdbc.handler.annotation.Table;

@Table("ja_list")
public class JaBean extends JaTemplete {
	
	@Column(ignore=true)
	private boolean isCollected;
	@Column(ignore=true)
	private String userName;

	public boolean isCollected() {
		return isCollected;
	}
	public void setCollected(boolean isCollected) {
		this.isCollected = isCollected;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
