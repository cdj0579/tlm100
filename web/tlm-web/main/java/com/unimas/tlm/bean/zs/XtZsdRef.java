package com.unimas.tlm.bean.zs;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.unimas.jdbc.handler.annotation.Column;
import com.unimas.jdbc.handler.annotation.Table;
import com.unimas.tlm.bean.JdbcBean;

@Table("xt_zsd_ref")
public class XtZsdRef extends JdbcBean {
	
	@Column(name="zsd_id", nullNumberValue=-1)
	private int zsdId = -1;
	@Column(name="xt_id", nullNumberValue=-1, isPk=true)
	private int xtId = -1;
	
	@Column(ignore=true)
	@JsonIgnore
	private XtBean xt;
	@Column(ignore=true)
	@JsonIgnore
	private ZsdBean zsd;
	
	public int getZsdId() {
		return zsdId;
	}
	public void setZsdId(int zsdId) {
		this.zsdId = zsdId;
	}
	public XtBean getXt() {
		return xt;
	}
	public void setXt(XtBean xt) {
		this.xt = xt;
	}
	public ZsdBean getZsd() {
		return zsd;
	}
	public void setZsd(ZsdBean zsd) {
		this.zsd = zsd;
	}
	public int getXtId() {
		return xtId;
	}
	public void setXtId(int xtId) {
		this.xtId = xtId;
	}
	
}
