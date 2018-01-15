package com.unimas.jdbc.handler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.google.common.collect.Lists;
import com.unimas.common.util.StringUtils;
import com.unimas.jdbc.DBFactory;
import com.unimas.jdbc.handler.entry.DataValue;
import com.unimas.jdbc.handler.entry.SqlModal;
import com.unimas.jdbc.handler.entry.TableData;

/**
 * <p>描述: 执行Update、Insert、Delete等SQL的工具类</p>
 * @author hxs
 * @date 2017年1月12日 上午10:55:04
 */
public class UpdateInsertHandler extends BaseHandler {
	
	private static SqlModal getInsertSqlModal(TableData table, Connection conn) throws SQLException {
		SqlModal sm = new SqlModal();
		StringBuffer sql = new StringBuffer();
		sql.append("insert into ");
		sql.append(table.getName());
		sql.append(" (");
		List<DataValue> datas = table.getDatas();
		if(datas != null && datas.size() > 0){
			List<Object> values = Lists.newArrayList();
			for(DataValue dv : datas){
				if(dv.isAuto()){
					continue;
				} else {
					if(values.size() != 0){
						sql.append(",");
					}
					sql.append(reservedWordConflict(dv.getField(), conn));
					values.add(dv.getRealValue());
				}
			}
			sql.append(") values (");
			for(int i=0;i<values.size();i++){
				if(i!=0){
					sql.append(",");
				}
				sql.append("?");
				sm.addValue(values.get(i));
			}
			sql.append(")");
		} else {
			throw new RuntimeException("不能插入空的表！");
		}
		sm.setSql(sql.toString());
		return sm;
	}
	
	private static SqlModal getUpdateSqlModal(TableData table, Connection conn) throws SQLException {
		SqlModal sm = new SqlModal();
		StringBuffer sql = new StringBuffer();
		sql.append("update ");
		sql.append(table.getName());
		sql.append(" set ");
		List<DataValue> datas = table.getDatas();
		if(datas != null && datas.size() > 0){
			boolean isFirst = true;
			for(DataValue dv : datas){
				if(!dv.isPk()){
					if(isFirst){
						isFirst = false;
					} else {
						sql.append(",");
					}
					sql.append(reservedWordConflict(dv.getField(), conn));
					sql.append("=? ");
					sm.addValue(dv.getRealValue());
				}
			}
			sql.append(" where 1=1 ");
			for(DataValue dv : datas){
				if(dv.isPk()){
					sql.append(" and ");
					sql.append(dv.getField());
					sql.append("=?");
					sm.addValue(dv.getRealValue());
				}
			}
		} else {
			throw new RuntimeException("不能修改空的表！");
		}
		sm.setSql(sql.toString());
		return sm;
	}
	
	private static SqlModal getDeleteSqlModal(TableData table, Connection conn) throws SQLException {
		SqlModal sm = new SqlModal();
		StringBuffer sql = new StringBuffer();
		sql.append("delete from ");
		sql.append(table.getName());
		sql.append(" where 1=1 ");
		List<DataValue> datas = table.getDatas();
		boolean hasPk = false;
		if(datas != null && datas.size() > 0){
			for(DataValue dv : datas){
				if(dv.isPk()){
					hasPk = true;
					sql.append(" and ");
					sql.append(reservedWordConflict(dv.getField(), conn));
					sql.append("=?");
					sm.addValue(dv.getRealValue());
				}
			}
		} 
		if(!hasPk) {
			throw new RuntimeException("未指定要删除的记录！");
		}
		sm.setSql(sql.toString());
		return sm;
	}
	
	public static int executeInsert(Connection conn, Object obj) throws Exception {
		return executeInsert(conn, obj, null);
	}
	
	public static int executeInsert(Connection conn, Object obj, String tableName) throws Exception {
		if(obj == null) throw new NullPointerException();
		PreparedStatement stat = null;
		ResultSet rs = null;
		try{
			TableData table = toData(obj, TableData.Action.insert);
			if(StringUtils.isNotEmpty(tableName)){
				table.setName(tableName);
			}
			SqlModal sm = getInsertSqlModal(table, conn);
			String insertSql = sm.getSql();
			logger.debug("createInsertSql="+insertSql);
			stat = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
			setValues(stat, sm.getValues());
			stat.executeUpdate();
			rs = stat.getGeneratedKeys();
			return ResultSetHandler.toInt(rs);
		} finally {
			DBFactory.close(null, stat, rs);
		}
	}
	
	public static void executeUpdate(Connection conn, Object obj) throws Exception {
		executeUpdate(conn, obj, null);
	}
	
	public static void executeUpdate(Connection conn, Object obj, String tableName) throws Exception {
		if(obj == null) throw new NullPointerException();
		PreparedStatement stat = null;
		try{
			TableData table = toData(obj, TableData.Action.update);
			if(StringUtils.isNotEmpty(tableName)){
				table.setName(tableName);
			}
			SqlModal sm = getUpdateSqlModal(table, conn);
			String updateSql = sm.getSql();
			logger.debug("createUpdateSql="+updateSql);
			stat = conn.prepareStatement(updateSql);
			setValues(stat, sm.getValues());
			stat.executeUpdate();
		} finally {
			DBFactory.close(null, stat, null);
		}
	}
	
	public static void executeDelete(Connection conn, Object obj) throws Exception {
		executeDelete(conn, obj, null);
	}
	
	public static void executeDelete(Connection conn, Object obj, String tableName) throws Exception {
		if(obj == null) throw new NullPointerException();
		PreparedStatement stat = null;
		try{
			TableData table = toData(obj, TableData.Action.delete);
			if(StringUtils.isNotEmpty(tableName)){
				table.setName(tableName);
			}
			SqlModal sm = getDeleteSqlModal(table, conn);
			String deleteSql = sm.getSql();
			logger.debug("createDeleteSql="+deleteSql);
			stat = conn.prepareStatement(deleteSql);
			setValues(stat, sm.getValues());
		    stat.executeUpdate();
		} finally {
			DBFactory.close(null, stat, null);
		}
	}
	
	public static void executeClear(Connection conn, Object obj) throws Exception {
		if(obj == null) throw new NullPointerException();
		PreparedStatement stat = null;
		try{
			String clearSql = "delete from "+getTableName(obj.getClass());
			logger.debug("clearSql="+clearSql);
			stat = conn.prepareStatement(clearSql);
			stat.executeUpdate();
		} finally {
			DBFactory.close(null, stat, null);
		}
	}
	
	public static void execute(Connection conn, String sql) throws Exception {
		if(sql == null) throw new NullPointerException();
		Statement stat = null;
		try{
			stat = conn.createStatement();
			logger.debug("executeSql="+sql);
			stat.execute(sql);
		} finally {
			DBFactory.close(null, stat, null);
		}
	}

}
