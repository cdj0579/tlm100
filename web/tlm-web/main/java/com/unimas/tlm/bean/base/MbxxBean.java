package com.unimas.tlm.bean.base;

import java.util.List;

import com.unimas.jdbc.handler.annotation.Column;
import com.unimas.jdbc.handler.annotation.Table;
import com.unimas.jdbc.handler.entry.SelectSqlModal.LeftField;
import com.unimas.tlm.bean.JdbcBean;

@Table("mbxx")
public class MbxxBean extends JdbcBean {
	
	@Column(name="dq_id")
	private String dqId;
	@Column(ignore=true)
	@LeftField(name="name", joinTable="xzqh", joinField="code", refField="dqId")
	private String dqName;
	private String name;
	private String desc;
	
	@Column(ignore=true)
	private List<MbxxmbfBean> mbfList;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDqName() {
		return dqName;
	}
	public void setDqName(String dqName) {
		this.dqName = dqName;
	}
	public String getDqId() {
		return dqId;
	}
	public void setDqId(String dqId) {
		this.dqId = dqId;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public List<MbxxmbfBean> getMbfList() {
		return mbfList;
	}
	public void setMbfList(List<MbxxmbfBean> mbfList) {
		this.mbfList = mbfList;
	}

}
