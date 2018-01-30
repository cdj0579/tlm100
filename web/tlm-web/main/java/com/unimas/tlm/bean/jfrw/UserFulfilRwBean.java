package com.unimas.tlm.bean.jfrw;

import com.unimas.jdbc.handler.annotation.Column;
import com.unimas.jdbc.handler.annotation.DefaultValue;
import com.unimas.jdbc.handler.annotation.Table;
import com.unimas.jdbc.handler.annotation.ToType;
import com.unimas.tlm.bean.JdbcBean;
import com.unimas.tlm.service.jfrw.aspect.annotations.RwPointcut;

@Table("rw_user_list")
public class UserFulfilRwBean extends JdbcBean {
	
	@Column(name="rw_id", nullNumberValue=-1)
	private int rwId = -1;
	@Column(name="user_no")
	private String userNo;
	@Column(name="content_id", nullNumberValue=-1)
	private int contentId = -1;
	@Column(ignore=true, name="content_name")
	private String contentName;
	
	/**
	 * 完成的任务状态，0：未完成，1：已完成，2：审核通过，3：审核不通过，4：超时未完成，5：已放弃
	 */
	@Column(name="status", nullNumberValue=-1)
	private int status = -1;
	private String shyj;
	@Column(name="insert_time", toType=ToType.DateToString,insertValue=DefaultValue.Now)
	private String insertTime;
	@Column(name="modify_time", toType=ToType.DateToString,insertValue=DefaultValue.Now,updateValue=DefaultValue.Now)
	private String modifyTime;
	
	@Column(ignore=true)
	private RwPointcut rpc;
	
	@Column(ignore=true, name="name")
	private String rwName;
	@Column(ignore=true, name="desc")
	private String rwDesc;
	@Column(ignore=true, name="type")
	private int type;
	@Column(ignore=true, name="source_id")
	private int sourceId;
	@Column(ignore=true, name="source_name")
	private String sourceName;
	@Column(ignore=true, name="user_name")
	private String userName;
	
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
	public String getContentName() {
		return contentName;
	}
	public void setContentName(String contentName) {
		this.contentName = contentName;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getShyj() {
		return shyj;
	}
	public void setShyj(String shyj) {
		this.shyj = shyj;
	}
	public String getRwName() {
		return rwName;
	}
	public void setRwName(String rwName) {
		this.rwName = rwName;
	}
	public String getRwDesc() {
		return rwDesc;
	}
	public void setRwDesc(String rwDesc) {
		this.rwDesc = rwDesc;
	}
	public int getSourceId() {
		return sourceId;
	}
	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public RwPointcut getRpc() {
		return rpc;
	}
	public void setRpc(RwPointcut rpc) {
		this.rpc = rpc;
	}

}
