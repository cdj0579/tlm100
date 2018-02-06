package com.unimas.tlm.bean.user;

import com.unimas.jdbc.handler.annotation.Column;
import com.unimas.jdbc.handler.annotation.Table;
import com.unimas.jdbc.handler.entry.SelectSqlModal.LeftField;
import com.unimas.tlm.bean.JdbcBean;

@Table("student_info")
public class StudentInfo extends JdbcBean implements java.io.Serializable {
	
	/**
	 * 
	 */
	@Column(ignore=true)
	private static final long serialVersionUID = 1L;
	
	@Column(name="user_no")
	private String userNo;
	@Column(name="student_name")
	private String studentName;
	@Column(name="parent_name")
	private String parentName;
	private String contact;
	@Column(name="nj_id", nullNumberValue=-1)
	private int njId = -1;
	@Column(ignore=true)
	@LeftField(name="name", joinTable="nj_dic", joinField="id", refField="njId")
	private String njName;
	@Column(name="mbxx_id", nullNumberValue=-1)
	private int mbxxId = -1;
	@Column(ignore=true)
	@LeftField(name="name", joinTable="mbxx", joinField="id", refField="mbxxId")
	private String mbxxName;
	@Column(name="jf", nullNumberValue=-1)
	private int jf = -1;
	@Column(name="hyd", nullNumberValue=-1)
	private int hyd = -1;
	@Column(name="tx") //头像图片的base64字符串
	private byte[] tx;
	/*@Column(name="zhcj", nullNumberValue=-1)
	private int zhcj = -1;*/
	@Column(name="school")
	private String school;
	@Column(name="dq_id")
	private String dqId;
	
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public int getNjId() {
		return njId;
	}
	public void setNjId(int njId) {
		this.njId = njId;
	}
	public String getNjName() {
		return njName;
	}
	public void setNjName(String njName) {
		this.njName = njName;
	}
	public int getMbxxId() {
		return mbxxId;
	}
	public void setMbxxId(int mbxxId) {
		this.mbxxId = mbxxId;
	}
	public String getMbxxName() {
		return mbxxName;
	}
	public void setMbxxName(String mbxxName) {
		this.mbxxName = mbxxName;
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
	public byte[] getTx() {
		return tx;
	}
	public void setTx(byte[] tx) {
		this.tx = tx;
	}
/*	public int getZhcj() {
		return zhcj;
	}
	public void setZhcj(int zhcj) {
		this.zhcj = zhcj;
	}*/
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getDqId() {
		return dqId;
	}
	public void setDqId(String dqId) {
		this.dqId = dqId;
	}

}
