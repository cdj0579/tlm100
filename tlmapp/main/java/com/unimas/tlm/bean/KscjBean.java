package com.unimas.tlm.bean;

import com.unimas.jdbc.handler.annotation.Column;
import com.unimas.jdbc.handler.annotation.Table;

/**
 * 考试成绩bean类
 * @author lusl
 *
 */
@Table("kaoshichengji")
public class KscjBean extends JdbcBean {

	@Column(name="user_no")
	private String userNo; //用户编号
	
	@Column(name="nj_id", nullNumberValue=-1)
	private int njId = -1; //年级编号
	
	@Column(name="sx_cj", nullNumberValue=-1)
	private int sxcj = -1; //数学最近成绩
	
	@Column(name="sx_mf", nullNumberValue=-1)
	private int sxmf = -1; //数学满分
	
	@Column(name="yw_cj", nullNumberValue=-1)
	private int ywcj = -1; //语文最近成绩
	
	@Column(name="yw_mf", nullNumberValue=-1)
	private int ywmf = -1; //语文满分
	
	@Column(name="yy_cj", nullNumberValue=-1)
	private int yycj = -1;//英语最近成绩
	
	@Column(name="yy_mf", nullNumberValue=-1)
	private int yymf = -1; //英语满分
	
	@Column(name="kx_cj", nullNumberValue=-1)
	private int kxcj = -1; //科学最近成绩
	
	@Column(name="kx_mf", nullNumberValue=-1)
	private int kxmf = -1; //科学满分
	
	@Column(name="sh_cj", nullNumberValue=-1)
	private int shcj = -1; //社会最近成绩
	
	@Column(name="sh_mf", nullNumberValue=-1)
	private int shmf = -1; //社会满分

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public int getNjId() {
		return njId;
	}

	public void setNjId(int njId) {
		this.njId = njId;
	}

	public int getSxcj() {
		return sxcj;
	}

	public void setSxcj(int sxcj) {
		this.sxcj = sxcj;
	}

	public int getSxmf() {
		return sxmf;
	}

	public void setSxmf(int sxmf) {
		this.sxmf = sxmf;
	}

	public int getYwcj() {
		return ywcj;
	}

	public void setYwcj(int ywcj) {
		this.ywcj = ywcj;
	}

	public int getYwmf() {
		return ywmf;
	}

	public void setYwmf(int ywmf) {
		this.ywmf = ywmf;
	}

	public int getYycj() {
		return yycj;
	}

	public void setYycj(int yycj) {
		this.yycj = yycj;
	}

	public int getYymf() {
		return yymf;
	}

	public void setYymf(int yymf) {
		this.yymf = yymf;
	}

	public int getKxcj() {
		return kxcj;
	}

	public void setKxcj(int kxcj) {
		this.kxcj = kxcj;
	}

	public int getKxmf() {
		return kxmf;
	}

	public void setKxmf(int kxmf) {
		this.kxmf = kxmf;
	}

	public int getShcj() {
		return shcj;
	}

	public void setShcj(int shcj) {
		this.shcj = shcj;
	}

	public int getShmf() {
		return shmf;
	}

	public void setShmf(int shmf) {
		this.shmf = shmf;
	}
	
	
	
	
}
