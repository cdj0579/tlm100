package com.unimas.tlm.bean.jfrw;

import com.unimas.jdbc.handler.annotation.Column;
import com.unimas.jdbc.handler.annotation.DefaultValue;
import com.unimas.jdbc.handler.annotation.Table;
import com.unimas.jdbc.handler.annotation.ToType;
import com.unimas.tlm.bean.JdbcBean;

@Table("rw_list")
public class RwListBean extends JdbcBean {
	
	@Column(name="pid", nullNumberValue=-1)
	private int pid = -1;
	/**
	 * 任务类型，0：习题任务，1：知识点任务，2：专题任务
	 */
	@Column(name="type", nullNumberValue=-1)
	private int type = -1;
	@Column(name="source_id", nullNumberValue=-1)
	private int sid = -1;
	@Column(name="fulfil_num", nullNumberValue=-1)
	private int fulfilNum = -1;
	@Column(name="insert_time", toType=ToType.DateToString,insertValue=DefaultValue.Now)
	private String insertTime;
	@Column(name="modify_time", toType=ToType.DateToString,insertValue=DefaultValue.Now,updateValue=DefaultValue.Now)
	private String modifyTime;
	
	@Column(ignore=true, name="name")
	private String rwName;
	@Column(ignore=true, name="desc")
	private String rwDesc;
	@Column(ignore=true, name="max_num")
	private int maxNum;
	@Column(ignore=true, name="lqcs")
	private long lqcs;
	@Column(ignore=true, name="stype")
	private String sourceType;
	@Column(ignore=true, name="sname")
	private String sourceName;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public int getFulfilNum() {
		return fulfilNum;
	}
	public void setFulfilNum(int fulfilNum) {
		this.fulfilNum = fulfilNum;
	}
	public int getMaxNum() {
		return maxNum;
	}
	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}
	public long getLqcs() {
		return lqcs;
	}
	public void setLqcs(long lqcs) {
		this.lqcs = lqcs;
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
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
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
