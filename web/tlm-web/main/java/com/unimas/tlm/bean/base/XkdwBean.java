package com.unimas.tlm.bean.base;

import com.unimas.jdbc.handler.annotation.Column;
import com.unimas.jdbc.handler.annotation.Table;
import com.unimas.jdbc.handler.entry.SelectSqlModal.LeftField;
import com.unimas.tlm.bean.JdbcBean;

@Table("xkdw")
public class XkdwBean extends JdbcBean {
	
	@Column(name="km_id", nullNumberValue=-1)
	private int kmId = -1;
	@Column(ignore=true)
	@LeftField(name="name", joinTable="km_dic", joinField="id", refField="kmId")
	private String kmName;
	@Column(name="level0", nullNumberValue=-1)
	private int level0 = -1;
	@Column(name="level1", nullNumberValue=-1)
	private int level1 = -1;
	@Column(name="level2", nullNumberValue=-1)
	private int level2 = -1;
	
	public int getKmId() {
		return kmId;
	}
	public void setKmId(int kmId) {
		this.kmId = kmId;
	}
	public String getKmName() {
		return kmName;
	}
	public void setKmName(String kmName) {
		this.kmName = kmName;
	}
	public int getLevel0() {
		return level0;
	}
	public void setLevel0(int level0) {
		this.level0 = level0;
	}
	public int getLevel1() {
		return level1;
	}
	public void setLevel1(int level1) {
		this.level1 = level1;
	}
	public int getLevel2() {
		return level2;
	}
	public void setLevel2(int level2) {
		this.level2 = level2;
	}
	
}
