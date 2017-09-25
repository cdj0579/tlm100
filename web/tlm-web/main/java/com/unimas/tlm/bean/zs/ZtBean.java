package com.unimas.tlm.bean.zs;

import com.unimas.jdbc.handler.annotation.Column;
import com.unimas.jdbc.handler.annotation.Table;
import com.unimas.jdbc.handler.entry.SelectSqlModal.LeftField;
import com.unimas.tlm.bean.JdbcBean;

@Table("zt_main")
public class ZtBean extends JdbcBean {
	
	@Column(name="nj_id", nullNumberValue=-1)
	private int njId = -1;
	@Column(ignore=true)
	@LeftField(name="name", joinTable="nj_dic", joinField="id", refField="njId")
	private String njName;
	@Column(name="km_id", nullNumberValue=-1)
	private int kmId = -1;
	@Column(ignore=true)
	@LeftField(name="name", joinTable="km_dic", joinField="id", refField="kmId")
	private String kmName;
	private String name;
	private int qzqm = 0;
	private int xq = 0;
	@Column(name="nd_id", nullNumberValue=-1)
	private int ndId = -1;
	@Column(ignore=true)
	@LeftField(name="name", joinTable="nd_dic", joinField="id", refField="ndId")
	private String ndName;
	private int ks = 0;
	private String desc;
	
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
	public int getQzqm() {
		return qzqm;
	}
	public void setQzqm(int qzqm) {
		this.qzqm = qzqm;
	}
	public int getXq() {
		return xq;
	}
	public void setXq(int xq) {
		this.xq = xq;
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
