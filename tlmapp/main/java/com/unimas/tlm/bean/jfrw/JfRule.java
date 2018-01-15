package com.unimas.tlm.bean.jfrw;

import com.unimas.jdbc.handler.annotation.Column;
import com.unimas.jdbc.handler.annotation.Table;
import com.unimas.tlm.bean.JdbcBean;

@Table("jf_rules")
public class JfRule extends JdbcBean {
	
	private String name;
	private String type;
	@Column(name="agg", nullNumberValue=-1)
	private int agg = -1;
	@Column(name="jf", nullNumberValue=-1)
	private int jf = -1;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getAgg() {
		return agg;
	}
	public void setAgg(int agg) {
		this.agg = agg;
	}
	public int getJf() {
		return jf;
	}
	public void setJf(int jf) {
		this.jf = jf;
	}

}
