package com.unimas.txl.bean.fenpei;

import java.util.List;

import com.unimas.jdbc.handler.annotation.Column;
import com.unimas.jdbc.handler.annotation.DefaultValue;
import com.unimas.jdbc.handler.annotation.Table;
import com.unimas.jdbc.handler.annotation.ToType;
import com.unimas.jdbc.handler.entry.SelectSqlModal.LeftField;
import com.unimas.txl.bean.JdbcBean;

@Table("txl_fenpei_guize")
public class FenpeiBean extends JdbcBean {
	
	@Column(name="jigou_id", nullNumberValue=-1)
	private int jigouId = -1;
	@Column(name="dq_id")
	private String dqId;
	@Column(ignore=true)
	@LeftField(name="name", joinTable="xzqh", joinField="code", refField="dqId")
	private String dqName;
	@Column(nullNumberValue=-1)
	private int zhouqi = -1;
	@Column(name="xq_xuexiao", nullNumberValue=-1)
	private int xuexiaoId = -1;
	@Column(ignore=true)
	@LeftField(name="xuexiaoming", joinTable="txl_xuexiao", joinField="id", refField="xuexiaoId")
	private String xuexiao;
	@Column(name="xq_nianji", nullNumberValue=-1)
	private int nj = -1;
	@Column(name="xq_banji", nullNumberValue=-1)
	private int bj = -1;
	@Column(nullNumberValue=-1)
	private int danliang = -1;
	@Column(toType=ToType.DateToString,insertValue=DefaultValue.Now)
	private String shijian;
	
	@Column(ignore=true)
	private List<FenpeiSyzBean> refs;
	
	public String getDqId() {
		return dqId;
	}
	public void setDqId(String dqId) {
		this.dqId = dqId;
	}
	public int getZhouqi() {
		return zhouqi;
	}
	public void setZhouqi(int zhouqi) {
		this.zhouqi = zhouqi;
	}
	public int getNj() {
		return nj;
	}
	public void setNj(int nj) {
		this.nj = nj;
	}
	public int getBj() {
		return bj;
	}
	public void setBj(int bj) {
		this.bj = bj;
	}
	public int getDanliang() {
		return danliang;
	}
	public void setDanliang(int danliang) {
		this.danliang = danliang;
	}
	public String getShijian() {
		return shijian;
	}
	public void setShijian(String shijian) {
		this.shijian = shijian;
	}
	public int getXuexiaoId() {
		return xuexiaoId;
	}
	public void setXuexiaoId(int xuexiaoId) {
		this.xuexiaoId = xuexiaoId;
	}
	public String getXuexiao() {
		return xuexiao;
	}
	public void setXuexiao(String xuexiao) {
		this.xuexiao = xuexiao;
	}
	public int getJigouId() {
		return jigouId;
	}
	public void setJigouId(int jigouId) {
		this.jigouId = jigouId;
	}
	public List<FenpeiSyzBean> getRefs() {
		return refs;
	}
	public void setRefs(List<FenpeiSyzBean> refs) {
		this.refs = refs;
	}
	public String getDqName() {
		return dqName;
	}
	public void setDqName(String dqName) {
		this.dqName = dqName;
	}

}
