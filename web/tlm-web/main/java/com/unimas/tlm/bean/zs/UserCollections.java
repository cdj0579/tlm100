package com.unimas.tlm.bean.zs;

import com.unimas.jdbc.handler.annotation.Column;
import com.unimas.jdbc.handler.annotation.DefaultValue;
import com.unimas.jdbc.handler.annotation.Table;
import com.unimas.jdbc.handler.annotation.ToType;
import com.unimas.tlm.bean.JdbcBean;

@Table("user_collections")
public class UserCollections extends JdbcBean {
	
	@Column(nullNumberValue=-1)
	private int cid = -1;
	private String type;
	@Column(name="user_no")
	private String userNo;
	@Column(nullNumberValue=-1)
	private int jf = -1;
	@Column(name="insert_time", toType=ToType.DateToString,insertValue=DefaultValue.Now)
	private String insertTime;
	@Column(name="modify_time", toType=ToType.DateToString,insertValue=DefaultValue.Now,updateValue=DefaultValue.Now)
	private String modifyTime;
	
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public int getJf() {
		return jf;
	}
	public void setJf(int jf) {
		this.jf = jf;
	}
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

}
