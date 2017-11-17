package com.unimas.txl.bean.user;

import com.unimas.jdbc.handler.annotation.Column;
import com.unimas.jdbc.handler.annotation.Table;
import com.unimas.txl.bean.JdbcBean;

@Table("txl_lianxiren_fenpei")
public class FenPeiInfo extends LianXiRenInfo {

	@Column(name="lxr_id", nullNumberValue=-1)
	private int lxrId = -1;
	@Column(name="syz_id", nullNumberValue=-1)
	private int syzId = -1;
	@Column(name="shijian")
	private String datetime;
	public int getLxrId() {
		return lxrId;
	}
	public void setLxrId(int lxrId) {
		this.lxrId = lxrId;
	}
	public int getSyzId() {
		return syzId;
	}
	public void setSyzId(int syzId) {
		this.syzId = syzId;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	
	
	
}
