package com.unimas.txl.bean.user;

import com.unimas.jdbc.handler.annotation.Column;
import com.unimas.jdbc.handler.annotation.Table;
import com.unimas.txl.bean.JdbcBean;

@Table("txl_lianxiren_qianyue")
public class QianYueInfo extends JdbcBean {

	@Column(name="lxr_id", nullNumberValue=-1)
	private int lxrId = -1;
	@Column(name="syz_id", nullNumberValue=-1)
	private int syzId = -1;
	
	private String  beizhu;
	
	@Column(name="shijian",datePattern="yyyy-MM-dd HH:mm:ss")
	private String datetime;
	
	@Column(name="jigou_id",nullNumberValue=-1)
	private int jigouId = -1;
	public int getJigouId() {
		return jigouId;
	}
	public void setJigouId(int jigouId) {
		this.jigouId = jigouId;
	}
	
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
	
	public String getBeizhu() {
		return beizhu;
	}
	public void setBeizhu(String beizhu) {
		this.beizhu = beizhu;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	
	
	
}
