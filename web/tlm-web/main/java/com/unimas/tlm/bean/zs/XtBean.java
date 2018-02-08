package com.unimas.tlm.bean.zs;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.unimas.jdbc.handler.annotation.Column;
import com.unimas.jdbc.handler.annotation.Table;
import com.unimas.jdbc.handler.entry.SelectSqlModal.LeftField;
import com.unimas.tlm.bean.JdbcBean;

@Table("xt_main")
public class XtBean extends JdbcBean {
	
	private String name;
	@Column(name="zt_id", nullNumberValue=-1)
	private int ztId = -1;
	@Column(ignore=true)
	@LeftField(name="name", joinTable="zt_main", joinField="id", refField="ztId")
	private String ztName;
	@Column(name="nd_id", nullNumberValue=-1)
	private int ndId = -1;
	@Column(ignore=true)
	@LeftField(name="name", joinTable="nd_dic", joinField="id", refField="ndId")
	private String ndName;
	@Column(name="type_id", nullNumberValue=-1)
	private int typeId = -1;
	@Column(ignore=true)
	@LeftField(name="name", joinTable="xt_type_dic", joinField="id", refField="typeId")
	private String typeName;
	@Column(name="is_original", nullNumberValue=-1)
	private int isOriginal = -1;
	@Column(nullNumberValue=-1)
	private int yyfs = -1;
	@Column(name="is_share", nullNumberValue=-1)
	private int isShare = -1;
	private String content;
	private String answer;
	@Column(name="user_no")
	private String userNo;
	@Column(nullNumberValue=-1)
	private int ljjf = -1;
	
	@Column(ignore=true)
	private ZtBean zt;
	@Column(ignore=true)
	private List<ZsdBean> zsds;
	@Column(ignore=true)
	@JsonIgnore
	private List<XtZsdRef> refs;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNdId() {
		return ndId;
	}
	public void setNdId(int ndId) {
		this.ndId = ndId;
	}
	public String getNdName() {
		return ndName;
	}
	public void setNdName(String ndName) {
		this.ndName = ndName;
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
	public int getZtId() {
		return ztId;
	}
	public void setZtId(int ztId) {
		this.ztId = ztId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public ZtBean getZt() {
		return zt;
	}
	public void setZt(ZtBean zt) {
		this.zt = zt;
	}
	public List<ZsdBean> getZsds() {
		return zsds;
	}
	public void setZsds(List<ZsdBean> zsds) {
		this.zsds = zsds;
	}
	public List<XtZsdRef> getRefs() {
		return refs;
	}
	public void setRefs(List<XtZsdRef> refs) {
		this.refs = refs;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getZtName() {
		return ztName;
	}
	public void setZtName(String ztName) {
		this.ztName = ztName;
	}
	public int getLjjf() {
		return ljjf;
	}
	public void setLjjf(int ljjf) {
		this.ljjf = ljjf;
	}

}
