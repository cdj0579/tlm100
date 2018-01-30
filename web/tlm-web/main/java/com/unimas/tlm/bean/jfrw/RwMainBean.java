package com.unimas.tlm.bean.jfrw;

import com.unimas.jdbc.handler.annotation.Column;
import com.unimas.jdbc.handler.annotation.DefaultValue;
import com.unimas.jdbc.handler.annotation.Table;
import com.unimas.jdbc.handler.annotation.ToType;
import com.unimas.tlm.bean.JdbcBean;

@Table("rw_main")
public class RwMainBean extends JdbcBean {
	
	private String name;
	private String desc;
	private int jf = 0;
	/**
	 * 任务状态， 0：进行中，1：已完成，2：关闭
	 */
	@Column(nullNumberValue=-1)
	private int status = -1;
	@Column(name="fulfil_num", nullNumberValue=-1)
	private int fulfilNum = -1;
	@Column(name="max_num", nullNumberValue=-1)
	private int maxNum = -1;
	@Column(name="insert_time", toType=ToType.DateToString,insertValue=DefaultValue.Now)
	private String insertTime;
	@Column(name="modify_time", toType=ToType.DateToString,insertValue=DefaultValue.Now,updateValue=DefaultValue.Now)
	private String modifyTime;
	
	@Column(ignore=true, name="rw_count")
	private long rwCount;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getJf() {
		return jf;
	}
	public void setJf(int jf) {
		this.jf = jf;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
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
	public long getRwCount() {
		return rwCount;
	}
	public void setRwCount(long rwCount) {
		this.rwCount = rwCount;
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
