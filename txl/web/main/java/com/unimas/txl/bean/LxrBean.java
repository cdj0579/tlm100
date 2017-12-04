package com.unimas.txl.bean;

import com.unimas.jdbc.handler.annotation.Column;
import com.unimas.jdbc.handler.annotation.Table;
import com.unimas.jdbc.handler.annotation.ToType;
import com.unimas.jdbc.handler.entry.SelectSqlModal.LeftField;

@Table("txl_lianxiren")
public class LxrBean extends JdbcBean {
	
	@Column(name="jigou_id", nullNumberValue=-1)
	private int jigouId = -1;
	@Column(name="lry_id", nullNumberValue=-1)
	private int lryId = -1;
	private String xingming;
	@Column(nullNumberValue=-1)
	private int xingbie = -1;
	@Column(name="xuexiao_id", nullNumberValue=-1)
	private int xuexiaoId = -1;
	@Column(name="xuexiaoming", ignore=true)
	@LeftField(name="xuexiaoming", joinTable="txl_xuexiao", joinField="id", refField="xuexiaoId")
	private String xuexiao;
	@Column(name="dq_id")
	private String dqId;
	@Column(name="dq_name", ignore=true)
	@LeftField(name="name", joinTable="xzqh", joinField="code", refField="dqId")
	private String dqName;
	@Column(nullNumberValue=-1)
	private int nianji = -1;
	@Column(nullNumberValue=-1)
	private int banji = -1;
	private String lianxiren;
	private String phone;
	@Column(name="is_del", nullNumberValue=-1)
	private int isDel = -1;
	private int cishu = 0;
	private String beizhu;
	
	@Column(name="status", ignore=true)
	private int status;
	@Column(name="shiyongzhe", ignore=true)
	private String shiyongzhe;
	@Column(name="shijian", ignore=true, toType=ToType.DateToString)
	private String shijian;
	
	public String getDqId() {
		return dqId;
	}
	public void setDqId(String dqId) {
		this.dqId = dqId;
	}
	public int getLryId() {
		return lryId;
	}
	public void setLryId(int lryId) {
		this.lryId = lryId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getShijian() {
		return shijian;
	}
	public void setShijian(String shijian) {
		this.shijian = shijian;
	}
	public String getShiyongzhe() {
		return shiyongzhe;
	}
	public void setShiyongzhe(String shiyongzhe) {
		this.shiyongzhe = shiyongzhe;
	}
	public String getXingming() {
		return xingming;
	}
	public void setXingming(String xingming) {
		this.xingming = xingming;
	}
	public int getXingbie() {
		return xingbie;
	}
	public void setXingbie(int xingbie) {
		this.xingbie = xingbie;
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
	public int getNianji() {
		return nianji;
	}
	public void setNianji(int nianji) {
		this.nianji = nianji;
	}
	public int getBanji() {
		return banji;
	}
	public void setBanji(int banji) {
		this.banji = banji;
	}
	public String getLianxiren() {
		return lianxiren;
	}
	public void setLianxiren(String lianxiren) {
		this.lianxiren = lianxiren;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getBeizhu() {
		return beizhu;
	}
	public void setBeizhu(String beizhu) {
		this.beizhu = beizhu;
	}
	public int getJigouId() {
		return jigouId;
	}
	public void setJigouId(int jigouId) {
		this.jigouId = jigouId;
	}
	public String getDqName() {
		return dqName;
	}
	public void setDqName(String dqName) {
		this.dqName = dqName;
	}
	public int getIsDel() {
		return isDel;
	}
	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}
	public int getCishu() {
		return cishu;
	}
	public void setCishu(int cishu) {
		this.cishu = cishu;
	}

}
