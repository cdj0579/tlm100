package com.unimas.txl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.google.common.base.Joiner;
import com.unimas.common.util.StringUtils;
import com.unimas.jdbc.DBFactory;
import com.unimas.jdbc.handler.ResultSetHandler;
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
			sm.addOrder(SyzBean.class, "id", SelectSqlModal.Order.ASC);
			return SelectHandler.executeSelect(conn, sm);
		} finally {
			DBFactory.close(conn, null, null);
		}
	}
	
	public List<SyzBean> query(int jigouId, List<Integer> ids) throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBFactory.getConn();
			String sql = "select T0.*,T0.id,T1.username,T2.name as dq_name from txl_shiyongzhe as T0 left join account as T1 on (T1.user_no = T0.user_no) left join xzqh as T2 on (T2.code = T0.dq_id) where T0.is_del = ? and T0.jigou_id=?";
			if(ids != null){
				ids.add(-1);
				sql += " and T0.id in ("+Joiner.on(",").join(ids)+")";
			}
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, 0);
			stmt.setInt(2, jigouId);
			rs = stmt.executeQuery();
			return ResultSetHandler.listBean(rs, SyzBean.class);
		} finally {
			DBFactory.close(conn, stmt, rs);
		}
	}

}
