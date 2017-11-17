package com.unimas.txl.bean.user;

import com.unimas.jdbc.handler.annotation.Column;
import com.unimas.jdbc.handler.annotation.Table;
import com.unimas.jdbc.handler.entry.SelectSqlModal.LeftField;
import com.unimas.txl.bean.JdbcBean;

@Table("txl_shiyongzhe")
public class ShiYongZheInfo extends JdbcBean implements java.io.Serializable {

	@Column(ignore=true)
	private static final long serialVersionUID = 1L;
	
	@Column(name="jigou_id", nullNumberValue=-1)
	private int jigouId = -1;
	@Column(ignore=true)
	/*@LeftField(name="jigouming", joinTable="txl_jigou", joinField="id", refField="jigouId")*/
	private String jgName;
	
	@Column(name="user_no")
	private String userNo;
	
	@Column(name="name")
	private String name;
	
	@Column(name="cishu", nullNumberValue=-1)
	private int cishu = -1;

	public int getJigouId() {
		return jigouId;
	}

	public void setJigouId(int jigouId) {
		this.jigouId = jigouId;
	}

	public String getJgName() {
		return jgName;
	}

	public void setJgName(String jgName) {
		this.jgName = jgName;
	}

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

	public int getCishu() {
		return cishu;
	}

	public void setCishu(int cishu) {
		this.cishu = cishu;
	}
	
	
}
