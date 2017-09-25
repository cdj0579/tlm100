package com.unimas.tlm.bean.zs;

import com.unimas.jdbc.handler.annotation.Column;
import com.unimas.jdbc.handler.annotation.DefaultValue;
import com.unimas.jdbc.handler.annotation.Table;
import com.unimas.jdbc.handler.annotation.ToType;
import com.unimas.tlm.bean.JdbcBean;

@Table("zsd_sort")
public class ZsdSort extends JdbcBean {
	
	@Column(name="zj_id", nullNumberValue=-1,isPk=true)
	private int zjId = -1;
	@Column(name="zsd_id", nullNumberValue=-1)
	private int zsdId = -1;
	@Column(nullNumberValue=-1)
	private int xh = -1;
	@Column(name="user_no",isPk=true)
	private String userNo;
	@Column(name="insert_time", toType=ToType.DateToString,insertValue=DefaultValue.Now)
	private String insertTime;
	@Column(name="modify_time", toType=ToType.DateToString,insertValue=DefaultValue.Now,updateValue=DefaultValue.Now)
	private String modifyTime;
	
	public int getZjId() {
		return zjId;
	}
	public void setZjId(int zjId) {
		this.zjId = zjId;
	}
	public int getZsdId() {
		return zsdId;
	}
	public void setZsdId(int zsdId) {
		this.zsdId = zsdId;
	}
	public int getXh() {
		return xh;
	}
	public void setXh(int xh) {
		this.xh = xh;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

}
