package com.unimas.txl.bean.fenpei;

import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.unimas.jdbc.handler.annotation.Column;
import com.unimas.jdbc.handler.annotation.DefaultValue;
import com.unimas.jdbc.handler.annotation.Table;
import com.unimas.jdbc.handler.annotation.ToType;
import com.unimas.jdbc.handler.entry.SelectSqlModal.LeftField;
import com.unimas.txl.bean.JdbcBean;

@Table("txl_fenpei_guize")
public class FenpeiBean extends JdbcBean {
	
	@Column(name="jigou_id", nullNumberValue=-1)
	private int jigouId = -1;
	@Column(name="dq_id")
	private String dqId;
	@Column(name="lxr_dq_id")
	private String lxrDqId;
	@Column(ignore=true)
	@LeftField(name="name", joinTable="xzqh", joinField="code", refField="lxrDqId")
	private String lxrDqName;
	@Column(name="xq_xuexiao")
	private String xuexiaoId;
	@Column(ignore=true)
	@LeftField(name="xuexiaoming", joinTable="txl_xuexiao", joinField="id", refField="xuexiaoId")
	private String xuexiao;
	@Column(name="xq_nianji")
	private String nj;
	@Column(name="xq_banji")
	private String bj;
	@Column(name="lry_id",nullNumberValue=-1)
	private int lryId = -1;
	@Column(nullNumberValue=-1)
	private int danliang = -1;
	@Column(toType=ToType.DateToString,insertValue=DefaultValue.Now)
	private String shijian;
	
	@Column(ignore=true)
	private List<FenpeiSyzBean> refs;
	
	public String getDqId() {
		return dqId;
	}
	public void setDqId(String dqId) {
		this.dqId = dqId;
	}
	public String getLxrDqId() {
		return lxrDqId;
	}
	public void setLxrDqId(String lxrDqId) {
		this.lxrDqId = lxrDqId;
	}
	public int getLryId() {
		return lryId;
	}
	public void setLryId(int lryId) {
		this.lryId = lryId;
	}
	public String getNj() {
		return nj;
	}
	public void setNj(int []  njIds) {
		this.nj = joinInts(njIds);
	}
	public String getBj() {
		return bj;
	}
	public void setBj(int [] bjIds) {
		this.bj = joinInts(bjIds);
	}
	public String getXuexiaoId() {
		return xuexiaoId;
	}
	public void setXuexiaoId(int [] xxIds) {
		this.xuexiaoId = joinInts(xxIds);
	}
	public int getDanliang() {
		return danliang;
	}
	public void setDanliang(int danliang) {
		this.danliang = danliang;
	}
	public String getShijian() {
		return shijian;
	}
	public void setShijian(String shijian) {
		this.shijian = shijian;
	}
	public String getXuexiao() {
		return xuexiao;
	}
	public void setXuexiao(String xuexiao) {
		this.xuexiao = xuexiao;
	}
	public int getJigouId() {
		return jigouId;
	}
	public void setJigouId(int jigouId) {
		this.jigouId = jigouId;
	}
	public List<FenpeiSyzBean> getRefs() {
		return refs;
	}
	public void setRefs(List<FenpeiSyzBean> refs) {
		this.refs = refs;
	}
	public String getLxrDqName() {
		return lxrDqName;
	}
	public void setLxrDqName(String lxrDqName) {
		this.lxrDqName = lxrDqName;
	}
	
	public static String joinInts(int [] ids){
		if(ids == null) return null;
		List<String> l = Lists.newArrayList();
		for(int id : ids){
			l.add(String.valueOf(id));
		}
		return Joiner.on(",").join(l);
	}

}
