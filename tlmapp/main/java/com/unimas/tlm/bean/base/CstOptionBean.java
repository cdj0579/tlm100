package com.unimas.tlm.bean.base;

import com.unimas.jdbc.handler.annotation.Column;
import com.unimas.jdbc.handler.annotation.Table;
import com.unimas.tlm.bean.JdbcBean;

@Table("cst_option")
public class CstOptionBean extends JdbcBean {
	
	@Column(isPk=true,name="pid", nullNumberValue=-1)
	private int pid = -1;
	private String name;
	private String option;
	
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOption() {
		return option;
	}
	public void setOption(String option) {
		this.option = option;
	}

}
