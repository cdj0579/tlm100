package com.unimas.txl.bean;

import com.unimas.jdbc.handler.annotation.Column;
import com.unimas.jdbc.handler.annotation.Table;

@Table("txl_config")
public class ConfigBean {
	
	@Column(isPk=true,name="jigou_id", nullNumberValue=-1)
	private int jigouId = -1;
	@Column(name="fenpei_zhouqi", nullNumberValue=-1)
	private int zhouqi = -1;
	@Column(name="guanzhu_shichang", nullNumberValue=-1)
	private int shichang = -1;
	@Column(name="luru_shangxian", nullNumberValue=-1)
	private int shangxian = -1;
	
	public int getJigouId() {
		return jigouId;
	}
	public void setJigouId(int jigouId) {
		this.jigouId = jigouId;
	}
	public int getZhouqi() {
		return zhouqi;
	}
	public void setZhouqi(int zhouqi) {
		this.zhouqi = zhouqi;
	}
	public int getShichang() {
		return shichang;
	}
	public void setShichang(int shichang) {
		this.shichang = shichang;
	}
	public int getShangxian() {
		return shangxian;
	}
	public void setShangxian(int shangxian) {
		this.shangxian = shangxian;
	}

}
