package com.unimas.tlm.bean.zs;

import com.unimas.jdbc.handler.annotation.Column;
import com.unimas.jdbc.handler.annotation.Table;
import com.unimas.tlm.bean.JdbcBean;

@Table("zsd_content")
public class ZsdContentBean extends JdbcBean {
	
	@Column(name="pid", nullNumberValue=-1)
	private int pid = -1;
	private String name;
	@Column(name="is_original", nullNumberValue=-1)
	private int isOriginal = -1;
	@Column(nullNumberValue=-1)
	private int yyfs = -1;
	@Column(name="is_share", nullNumberValue=-1)
	private int isShare = -1;
	private String content;
	@Column(name="user_no")
	private String userNo;
	@Column(nullNumberValue=-1)
	private int ljjf = -1;
	
	@Column(ignore=true)
	private ZsdBean zsd;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public ZsdBean getZsd() {
		return zsd;
	}
	public void setZsd(ZsdBean zsd) {
		this.zsd = zsd;
	}
	public int getLjjf() {
		return ljjf;
	}
	public void setLjjf(int ljjf) {
		this.ljjf = ljjf;
	}

}
