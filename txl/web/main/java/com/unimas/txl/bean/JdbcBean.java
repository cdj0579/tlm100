package com.unimas.txl.bean;

import com.unimas.common.util.json.JSONUtils;
import com.unimas.jdbc.handler.annotation.Column;

public class JdbcBean {
	
	@Column(isPk=true,auto=true, nullNumberValue=-1)
	protected int id = -1;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		try {
			return this.getClass().getName()+" "+JSONUtils.toJson(this, true);
		} catch (Exception e) {
			return super.toString();
		}
	}

}
