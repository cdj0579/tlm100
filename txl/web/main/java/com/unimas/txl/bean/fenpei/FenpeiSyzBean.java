package com.unimas.txl.bean.fenpei;

import com.unimas.jdbc.handler.annotation.Column;
import com.unimas.jdbc.handler.annotation.Table;
import com.unimas.txl.bean.JdbcBean;

@Table("txl_shiyongzhe_fenpei")
public class FenpeiSyzBean extends JdbcBean {
	
	@Column(isPk=true,name="guize_id", nullNumberValue=-1)
	private int guizeId = -1;
	@Column(name="syz_id", nullNumberValue=-1)
	private int syzId = -1;
	
	public int getGuizeId() {
		return guizeId;
	}
	public void setGuizeId(int guizeId) {
		this.guizeId = guizeId;
	}
	public int getSyzId() {
		return syzId;
	}
	public void setSyzId(int syzId) {
		this.syzId = syzId;
	}

}
