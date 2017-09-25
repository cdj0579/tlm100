package com.unimas.tlm.bean.base;

import com.unimas.jdbc.handler.annotation.Column;
import com.unimas.jdbc.handler.annotation.Table;
import com.unimas.jdbc.handler.entry.SelectSqlModal.LeftField;
import com.unimas.tlm.bean.JdbcBean;

@Table("zj")
public class ZjBean extends JdbcBean {
	
	@Column(name="bb_id", isPk=true)
	private int bbId;
	@Column(name="dq_id")
	private String dqId;
	@Column(name="nj_id", nullNumberValue=-1)
	private int njId = -1;
	@Column(name="pid", isPk=true)
	private int pid;
	@Column(ignore=true)
	@LeftField(name="name", joinTable="nj_dic", joinField="id", refField="njId")
	private String njName;
	@Column(name="km_id", nullNumberValue=-1)
	private int kmId = -1;
	@Column(ignore=true)
	@LeftField(name="name", joinTable="km_dic", joinField="id", refField="kmId")
	private String kmName;
	private int xq = 0;
	private String name;
	private String bm;
	@Column(name="xh", nullNumberValue=-1)
	private int xh = -1;
	
	public int getNjId() {
		return njId;
	}
	public void setNjId(int njId) {
		this.njId = njId;
	}
	public String getDqId() {
		return dqId;
	}
	public void setDqId(String dqId) {
		this.dqId = dqId;
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
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public int getXq() {
		return xq;
	}
	public void setXq(int xq) {
		this.xq = xq;
	}
	public String getBm() {
		return bm;
	}
	public void setBm(String bm) {
		this.bm = bm;
	}
	public int getBbId() {
		return bbId;
	}
	public void setBbId(int bbId) {
		this.bbId = bbId;
	}
	public int getXh() {
		return xh;
	}
	public void setXh(int xh) {
		this.xh = xh;
	}

}
