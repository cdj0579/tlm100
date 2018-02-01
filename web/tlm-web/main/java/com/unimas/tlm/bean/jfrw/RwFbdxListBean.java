package com.unimas.tlm.bean.jfrw;

import com.unimas.jdbc.handler.annotation.Column;
import com.unimas.jdbc.handler.annotation.Table;
import com.unimas.tlm.bean.JdbcBean;

@Table("rw_fbdx_list")
public class RwFbdxListBean extends JdbcBean{
	
	@Column(name="rw_id", nullNumberValue=-1)
	private int rwId = -1;
	
	@Column(name="user_no")
	private String userNo;
	
	public int getRwId() {
		return rwId;
	}
	
	public void setRwId(int rwId) {
		this.rwId = rwId;
	}
	
	public String getUserNo() {
		return userNo;
	}
	
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	@Override
	public String toString() {
		return "RwFbdxListBean [rwId=" + rwId + ", userNo=" + userNo + "]";
	}
	
	
	
}
