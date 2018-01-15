package com.unimas.tlm.bean.zs;

import com.unimas.jdbc.handler.annotation.Column;
import com.unimas.jdbc.handler.annotation.DefaultValue;
import com.unimas.jdbc.handler.annotation.Table;
import com.unimas.jdbc.handler.annotation.ToType;
import com.unimas.jdbc.handler.entry.SelectSqlModal.LeftField;
import com.unimas.tlm.bean.JdbcBean;

@Table("zsd_main")
public class ZsdBean extends JdbcBean {
	
	@Column(name="nj_id", nullNumberValue=-1)
	private int njId = -1;
	@Column(ignore=true)
	@LeftField(name="name", joinTable="nj_dic", joinField="id", refField="njId")
	private String njName;
	@Column(name="dq_id")
	private String dqId;
	@Column(name="km_id", nullNumberValue=-1)
	private int kmId = -1;
	@Column(ignore=true)
	@LeftField(name="name", joinTable="km_dic", joinField="id", refField="kmId")
	private String kmName;
	private String name;
	private int xq = 0;
	@Column(name="zj_id", nullNumberValue=-1)
	private int zjId = -1;
	@Column(ignore=true)
	@LeftField(name="name", joinTable="zj", joinField="id", refField="zjId")
	private String zjName;
	@Column(name="nd_id", nullNumberValue=-1, toType=ToType.toInt)
	private int ndId = -1;
	@Column(ignore=true)
	@LeftField(name="name", joinTable="nd_dic", joinField="id", refField="ndId")
	private String ndName;
	@Column(nullNumberValue=-1)
	private int level = -1;
	@Column(name="user_no")
	private String userNo;
	@Column(toType=ToType.toInt)
	private int ks = 0;
	private String desc;
	@Column(name="insert_time", toType=ToType.DateToString,insertValue=DefaultValue.Now)
	private String insertTime;
	@Column(name="modify_time", toType=ToType.DateToString,insertValue=DefaultValue.Now,updateValue=DefaultValue.Now)
	private String modifyTime;
	
	@Column(ignore=true)
	private boolean isSelf;
	@Column(ignore=true, name="modified_id", toType=ToType.toInt)
	private int modifiedId;
	
	public int getNjId() {
		return njId;
	}
	public void setNjId(int njId) {
		this.njId = njId;
	}
	public String getNjName() {
		return njName;
	}
	public void setNjName(String njName) {
		this.njName = njName;
	}
	public String getDqId() {
		return dqId;
	}
	public void setDqId(String dqId) {
		this.dqId = dqId;
	}
	public int getKmId() {
		return kmId;
	}
	public void setKmId(int kmId) {
		this.kmId = kmId;
	}
	public String getKmName() {
		return kmName;
	}
	public void setKmName(String kmName) {
		this.kmName = kmName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getXq() {
		return xq;
	}
	public void setXq(int xq) {
		this.xq = xq;
	}
	public int getZjId() {
		return zjId;
	}
	public void setZjId(int zjId) {
		this.zjId = zjId;
	}
	public String getZjName() {
		return zjName;
	}
	public void setZjName(String zjName) {
		this.zjName = zjName;
	}
	public int getNdId() {
		return ndId;
	}
	public void setNdId(int ndId) {
		this.ndId = ndId;
	}
	public String getNdName() {
		return ndName;
	}
	public void setNdName(String ndName) {
		this.ndName = ndName;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public int getKs() {
		return ks;
	}
	public void setKs(int ks) {
		this.ks = ks;
	}
	public boolean isSelf() {
		return isSelf;
	}
	public void setSelf(boolean isSelf) {
		this.isSelf = isSelf;
	}
	public int getModifiedId() {
		return modifiedId;
	}
	public void setModifiedId(int modifiedId) {
		this.modifiedId = modifiedId;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}

}
