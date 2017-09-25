package com.unimas.tlm.bean.zs;

import com.unimas.jdbc.handler.annotation.Column;
import com.unimas.jdbc.handler.annotation.DefaultValue;
import com.unimas.jdbc.handler.annotation.Table;
import com.unimas.jdbc.handler.annotation.ToType;
import com.unimas.jdbc.handler.entry.SelectSqlModal.LeftField;
import com.unimas.tlm.bean.JdbcBean;

@Table("zsd_modified")
public class ZsdModified extends JdbcBean {
	
	@Column(name="zsd_id", nullNumberValue=-1)
	private int zsdId = -1;
	private String name;
	@Column(name="nd_id", nullNumberValue=-1)
	private int ndId = -1;
	@Column(ignore=true)
	@LeftField(name="name", joinTable="nd_dic", joinField="id", refField="ndId")
	private String ndName;
	private int ks = 0;
	private String desc;
	@Column(name="insert_time", toType=ToType.DateToString,insertValue=DefaultValue.Now)
	private String insertTime;
	@Column(name="modify_time", toType=ToType.DateToString,insertValue=DefaultValue.Now,updateValue=DefaultValue.Now)
	private String modifyTime;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getZsdId() {
		return zsdId;
	}
	public void setZsdId(int zsdId) {
		this.zsdId = zsdId;
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
	public int getKs() {
		return ks;
	}
	public void setKs(int ks) {
		this.ks = ks;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}

}
