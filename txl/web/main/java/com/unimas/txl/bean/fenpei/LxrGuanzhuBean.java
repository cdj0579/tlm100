package com.unimas.txl.bean.fenpei;

import com.unimas.jdbc.handler.annotation.Column;
import com.unimas.jdbc.handler.annotation.DefaultValue;
import com.unimas.jdbc.handler.annotation.Table;
import com.unimas.jdbc.handler.annotation.ToType;
import com.unimas.txl.bean.JdbcBean;

@Table("txl_lianxiren_guanzhu")
public class LxrGuanzhuBean extends JdbcBean {
	
	@Column(isPk=true,name="jigou_id", nullNumberValue=-1)
	private int jigouId = -1;
	@Column(name="lxr_id", nullNumberValue=-1)
	private int lxrId = -1;
	@Column(name="syz_id", nullNumberValue=-1)
	private int syzId = -1;
	@Column(toType=ToType.DateToString,insertValue=DefaultValue.Now)
	private String shijian;
	
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
	public String getShijian() {
		return shijian;
	}
	public void setShijian(String shijian) {
		this.shijian = shijian;
	}
	public int getSyzId() {
		return syzId;
	}
	public void setSyzId(int syzId) {
		this.syzId = syzId;
	}

}
