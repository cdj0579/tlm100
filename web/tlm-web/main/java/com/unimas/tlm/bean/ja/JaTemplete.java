package com.unimas.tlm.bean.ja;

import java.io.UnsupportedEncodingException;

import com.unimas.jdbc.handler.annotation.Column;
import com.unimas.jdbc.handler.annotation.DefaultValue;
import com.unimas.jdbc.handler.annotation.Table;
import com.unimas.jdbc.handler.annotation.ToType;
import com.unimas.tlm.bean.JdbcBean;

@Table("ja_templetes")
public class JaTemplete extends JdbcBean {
	
	@Column(name="user_no")
	private String userNo;
	private String name;
	@Column(name="dir_id")
	private int dirId;
	private byte[] content;
	@Column(name="is_original", nullNumberValue=-1)
	private int isOriginal = -1;
	@Column(nullNumberValue=-1)
	private int yyfs = -1;
	@Column(name="is_share", nullNumberValue=-1)
	private int isShare = -1;
	@Column(name="insert_time", toType=ToType.DateToString,insertValue=DefaultValue.Now)
	private String insertTime;
	@Column(name="modify_time", toType=ToType.DateToString,insertValue=DefaultValue.Now,updateValue=DefaultValue.Now)
	private String modifyTime;
	
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public int getDirId() {
		return dirId;
	}
	public void setDirId(int dirId) {
		this.dirId = dirId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		try {
			return new String(content, "utf-8");
		} catch (UnsupportedEncodingException e) {
			return new String(content);
		}
	}
	public byte[] getContentBytes() {
		return this.content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	public int getIsOriginal() {
		return isOriginal;
	}
	public void setIsOriginal(int isOriginal) {
		this.isOriginal = isOriginal;
	}
	public int getYyfs() {
		return yyfs;
	}
	public void setYyfs(int yyfs) {
		this.yyfs = yyfs;
	}
	public int getIsShare() {
		return isShare;
	}
	public void setIsShare(int isShare) {
		this.isShare = isShare;
	}
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

}
