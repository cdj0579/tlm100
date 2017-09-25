package com.unimas.tlm.bean.base;

import java.util.List;

import com.unimas.jdbc.handler.annotation.Column;
import com.unimas.jdbc.handler.annotation.Table;
import com.unimas.jdbc.handler.entry.SelectSqlModal.LeftField;
import com.unimas.tlm.bean.JdbcBean;

@Table("cstk")
public class CstBean extends JdbcBean {
	
	@Column(name="nj_id", nullNumberValue=-1)
	private int njId = -1;
	@Column(ignore=true)
	@LeftField(name="name", joinTable="nj_dic", joinField="id", refField="njId")
	private String njName;
	@Column(name="km_id", nullNumberValue=-1)
	private int kmId = -1;
	@Column(ignore=true)
	@LeftField(name="name", joinTable="km_dic", joinField="id", refField="kmId")
	private String kmName;
	private String name;
	private String answer;
	
	@Column(ignore=true)
	private List<CstOptionBean> options;
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public List<CstOptionBean> getOptions() {
		return options;
	}
	public void setOptions(List<CstOptionBean> options) {
		this.options = options;
	}

}
