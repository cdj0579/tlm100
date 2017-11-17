package com.unimas.txl.bean.user;

import com.unimas.jdbc.handler.annotation.Column;
import com.unimas.jdbc.handler.annotation.Table;
import com.unimas.jdbc.handler.entry.SelectSqlModal.LeftField;
import com.unimas.txl.bean.JdbcBean;

@Table("txl_lianxiren")
public class LianXiRenInfo extends JdbcBean {
	
	@Column(name="jigou_id",nullNumberValue=-1)
	private int jigouId = -1;
	
	private String xingming;
	
	@Column(name="xingbie", nullNumberValue=-1)
	private int xingbie = -1;
	
	@Column(name="xuexiao_id",nullNumberValue=-1)
	private int xuexiaoId = -1;
	
	@Column(ignore=true)
	@LeftField(name="xuexiaoming",joinTable="txl_xuexiao", joinField="id", refField="xuexiaoId")
	private String xuexiao;
	
	@Column(name="dq_id")
	private String dqId;

	private String nianji;

	private String banji;

	private String lianxiren;

	private String phone;
	
	private String beizhu;
	
	@Column(name="cishu", nullNumberValue=-1)
	private int cishu = -1;

	public int getJigouId() {
		return jigouId;
	}

	public void setJigouId(int jigouId) {
		this.jigouId = jigouId;
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

	public String getDqId() {
		return dqId;
	}

	public void setDqId(String dqId) {
		this.dqId = dqId;
	}

	public String getNianji() {
		return nianji;
	}

	public void setNianji(String nianji) {
		this.nianji = nianji;
	}

	public String getBanji() {
		return banji;
	}

	public void setBanji(String banji) {
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

	public int getCishu() {
		return cishu;
	}

	public void setCishu(int cishu) {
		this.cishu = cishu;
	}

}
