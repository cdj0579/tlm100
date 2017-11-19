package com.unimas.txl.dao;

import java.sql.Connection;
import java.util.List;

import com.unimas.common.util.StringUtils;
import com.unimas.jdbc.DBFactory;
import com.unimas.jdbc.handler.SelectHandler;
import com.unimas.jdbc.handler.entry.SelectSqlModal;
import com.unimas.txl.bean.SyzBean;

public class SyzDao extends JdbcDao<SyzBean> {
	
	public List<SyzBean> query(int jigouId, String dqId, String name) throws Exception {
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			SyzBean bean = new SyzBean();
			bean.setIsDel(0);
			bean.setJigouId(jigouId);
			if(StringUtils.isNotEmpty(dqId)){
				bean.setDqId(dqId);
			}
			SelectSqlModal<SyzBean> sm = SelectHandler.createSelectModal(conn, bean);
			if(StringUtils.isNotEmpty(name)){
				sm.addCondition(SyzBean.class, "name", "like", "%"+name+"%");
			}
			return SelectHandler.executeSelect(conn, sm);
		} finally {
			DBFactory.close(conn, null, null);
		}
	}

}
