package com.unimas.tlm.bean.jfrw;

import com.unimas.jdbc.handler.annotation.Column;
import com.unimas.jdbc.handler.annotation.DefaultValue;
import com.unimas.jdbc.handler.annotation.Table;
import com.unimas.jdbc.handler.annotation.ToType;
import com.unimas.tlm.bean.JdbcBean;
import com.unimas.tlm.bean.user.TeacherInfo;
import com.unimas.tlm.service.jfrw.aspect.annotations.RwPointcut;

@Table("rw_list")
public class RwListBean extends JdbcBean {
	
	@Column(name="rw_id", nullNumberValue=-1)
	private int rwId = -1;
	@Column(name="user_no")
	private String userNo;
	@Column(name="content_id", nullNumberValue=-1)
	private int contentId = -1;
	@Column(name="status", nullNumberValue=-1)
	private int status = -1;
	private String shyj;
	@Column(name="insert_time", toType=ToType.DateToString,insertValue=DefaultValue.Now)
	private String insertTime;
	@Column(name="modify_time", toType=ToType.DateToString,insertValue=DefaultValue.Now,updateValue=DefaultValue.Now)
	private String modifyTime;
	
	@Column(ignore=true)
	private RwBean parent;
	@Column(ignore=true)
	private TeacherInfo user;
	@Column(ignore=true)
	private RwPointcut rpc;
	
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
	public int getContentId() {
		return contentId;
	}
	public void setContentId(int contentId) {
		this.contentId = contentId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getShyj() {
		return shyj;
	}
	public void setShyj(String shyj) {
		this.shyj = shyj;
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
	public RwBean getParent() {
		return parent;
	}
	public void setParent(RwBean parent) {
		this.parent = parent;
	}
	public TeacherInfo getUser() {
		return user;
	}
	public void setUser(TeacherInfo user) {
		this.user = user;
	}
	public RwPointcut getRpc() {
		return rpc;
	}
	public void setRpc(RwPointcut rpc) {
		this.rpc = rpc;
	}

}
