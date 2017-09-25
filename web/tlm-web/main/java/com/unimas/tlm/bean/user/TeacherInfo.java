package com.unimas.tlm.bean.user;

import com.unimas.jdbc.handler.annotation.Column;
import com.unimas.jdbc.handler.annotation.Table;
import com.unimas.jdbc.handler.entry.SelectSqlModal.LeftField;
import com.unimas.tlm.bean.JdbcBean;

@Table("teacher_info")
public class TeacherInfo extends JdbcBean implements java.io.Serializable {
	
	/**
	 * 
	 */
	@Column(ignore=true)
	private static final long serialVersionUID = 1L;
	
	@Column(name="user_no")
	private String userNo;
	private String name;
	private String skdz;
	@Column(name="km_id", nullNumberValue=-1)
	private int kmId = -1;
	@Column(ignore=true)
	@LeftField(name="name", joinTable="km_dic", joinField="id", refField="kmId")
	private String kmName;
	@Column(name="jszgz")
	private byte[] jszgz;
	@Column(name="djzs")
	private byte[] djzs;
	@Column(name="ryzs")
	private byte[] ryzs;
	@Column(nullNumberValue=-1)
	private int jf = -1;
	@Column(name="hyd", nullNumberValue=-1)
	private int hyd = -1;
	
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSkdz() {
		return skdz;
	}
	public void setSkdz(String skdz) {
		this.skdz = skdz;
	}
	public int getKmId() {
		return kmId;
	}
	public void setKmId(int kmId) {
		this.kmId = kmId;
	}
	public int getJf() {
		return jf;
	}
	public void setJf(int jf) {
		this.jf = jf;
	}
	public int getHyd() {
		return hyd;
	}
	public void setHyd(int hyd) {
		this.hyd = hyd;
	}
	public String getKmName() {
		return kmName;
	}
	public void setKmName(String kmName) {
		this.kmName = kmName;
	}
	public byte[] getJszgz() {
		return jszgz;
	}
	public void setJszgz(byte[] jszgz) {
		this.jszgz = jszgz;
	}
	public byte[] getDjzs() {
		return djzs;
	}
	public void setDjzs(byte[] djzs) {
		this.djzs = djzs;
	}
	public byte[] getRyzs() {
		return ryzs;
	}
	public void setRyzs(byte[] ryzs) {
		this.ryzs = ryzs;
	}
	
}
