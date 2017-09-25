package com.unimas.tlm.bean.base;

import com.unimas.jdbc.handler.annotation.Column;
import com.unimas.jdbc.handler.annotation.Table;
import com.unimas.jdbc.handler.entry.SelectSqlModal.LeftField;
import com.unimas.tlm.bean.JdbcBean;

@Table("mbxx_mbf")
public class MbxxmbfBean extends JdbcBean {
	
	@Column(isPk=true, name="mbxx_id", nullNumberValue=-1)
	private int mbxxId = -1;
	@Column(name="km_id", nullNumberValue=-1)
	private int kmId = -1;
	@Column(ignore=true)
	@LeftField(name="name", joinTable="km_dic", joinField="id", refField="kmId")
	private String kmName;
	private int mbfs;
	private int mf;
	
	public int getMbxxId() {
		return mbxxId;
	}
	public void setMbxxId(int mbxxId) {
		this.mbxxId = mbxxId;
	}
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
	public int getMbfs() {
		return mbfs;
	}
	public void setMbfs(int mbfs) {
		this.mbfs = mbfs;
	}
	public int getMf() {
		return mf;
	}
	public void setMf(int mf) {
		this.mf = mf;
	}
}
