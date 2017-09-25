package com.unimas.tlm.bean.zs;

import com.unimas.jdbc.handler.annotation.Column;
import com.unimas.jdbc.handler.annotation.Table;
import com.unimas.tlm.bean.JdbcBean;

@Table("xt_zsd_ref")
public class XtZsdRef extends JdbcBean {
	
	@Column(name="zsd_id", nullNumberValue=-1)
	private int zsdId = -1;
	@Column(name="xt_id", nullNumberValue=-1, isPk=true)
	private int xtId = -1;
	
	public int getZsdId() {
		return zsdId;
	}
	public void setZsdId(int zsdId) {
		this.zsdId = zsdId;
	}
	public int getXtId() {
		return xtId;
	}
	public void setXtId(int xtId) {
		this.xtId = xtId;
	}
	
}
