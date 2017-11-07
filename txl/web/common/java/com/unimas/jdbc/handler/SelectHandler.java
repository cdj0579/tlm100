package com.unimas.jdbc.handler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.unimas.common.util.StringUtils;
import com.unimas.jdbc.DBFactory;
import com.unimas.jdbc.handler.entry.DataValue;
import com.unimas.jdbc.handler.entry.SelectSqlModal;
import com.unimas.jdbc.handler.entry.SqlModal;
import com.unimas.jdbc.handler.entry.TableData;

/**
 * <p>描述: 执行Select SQL的工具类</p>
 * @author hxs
 * @date 2017年1月12日 上午10:54:52
 */
public class SelectHandler extends BaseHandler {
	
	protected static SqlModal getSampleSelectSqlModal(TableData table, Connection conn) throws SQLException{
		SqlModal sm = new SqlModal();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ");
		sql.append(table.getName());
		List<DataValue> datas = table.getDatas();
		if(datas != null && datas.size() > 0){
			sql.append(" where 1=1 ");
			for(DataValue dv : datas){
				sql.append(" and ");
				sql.append(dv.getField());
				sql.append("=?");
				sm.addValue(dv.getRealValue());
			}
		} else {
			throw new RuntimeException("不能修改空的表！");
		}
		sm.setSql(sql.toString());
		return sm;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> SelectSqlModal<T> createSelectModal(Connection conn, T t) throws Exception {
		TableData table = toData(t, TableData.Action.select);
		SelectSqlModal<T> sm = new SelectSqlModal<T>(getDataBaseType(conn), table, (Class<T>)t.getClass());
		return sm;
	}
	
	public static <T> SelectSqlModal<T> createSelectModal(Connection conn, Class<T> c) throws Exception {
		TableData table = toData(c, TableData.Action.select);
		SelectSqlModal<T> sm = new SelectSqlModal<T>(getDataBaseType(conn), table, c);
		return sm;
	}
	
	public static <T> List<T> executeSelect(Connection conn, T t, String tableName) throws Exception {
		if(t == null) throw new NullPointerException();
		ResultSet rs = null;
		try{
			TableData table = toData(t, TableData.Action.select);
			if(StringUtils.isNotEmpty(tableName)){
				table.setName(tableName);
			}
			SelectSqlModal<T> sm = createSelectModal(conn, t);
			return executeSelect(conn, sm);
		} finally {
			DBFactory.close(null, null, rs);
		}
	}
	
	public static <T> List<T> executeSelect(Connection conn, SelectSqlModal<T> sm) throws Exception {
		if(sm == null) throw new NullPointerException();
		PreparedStatement stat = null;
		ResultSet rs = null;
		try {
			String selectSql = sm.getSql();
			logger.debug("createSelectSql="+selectSql);
			stat = conn.prepareStatement(selectSql);
			setValues(stat, sm.getValues());
			rs = stat.executeQuery();
			return sm.getResult(rs);
		} finally {
			DBFactory.close(null, stat, rs);
		}
	}
	
	public static <T> List<T> executeSelect(Connection conn, T t) throws Exception {
		return executeSelect(conn, t, null);
	}

}
