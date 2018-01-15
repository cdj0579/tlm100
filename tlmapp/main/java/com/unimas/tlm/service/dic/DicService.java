package com.unimas.tlm.service.dic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.unimas.common.util.StringUtils;
import com.unimas.jdbc.DBFactory;
import com.unimas.jdbc.handler.ResultSetHandler;
import com.unimas.jdbc.handler.UpdateInsertHandler;
import com.unimas.tlm.bean.base.ZjBean;

public class DicService {
	
	public static final Logger logger = Logger.getLogger(DicService.class);
	
	public List<Map<String, Object>> get(String tableName, String idField, String nameField, String groupField, 
			String typeField, String typeVelue) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select ");
			sql.append(idField);
			sql.append(" as id,");
			sql.append(nameField);
			sql.append(" as name");
			if(StringUtils.isNotEmpty(groupField)){
				sql.append(",");
				sql.append(groupField);
				sql.append(" as type");
			}
			sql.append(" from ");
			sql.append(tableName);
			if(StringUtils.isNotEmpty(typeField) && StringUtils.isNotEmpty(typeVelue)){
				sql.append(" where ");
				sql.append(typeField);
				sql.append("='");
				sql.append(typeVelue);
				sql.append("'");
			}
			conn = DBFactory.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql.toString());
			return ResultSetHandler.listMap(rs);
		}catch (Exception e) {
			logger.error("获取字典列表失败！", e);
			throw e;
		} finally {
			DBFactory.close(conn, stmt, rs);
		}
	}
	
	private void checkExsits(Connection conn, String tableName, String nameField, String value, String typeField, String typeVelue) throws Exception{
		Statement stmt = null;
		ResultSet rs = null;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select count(*) from ");
			sql.append(tableName);
			sql.append(" where ");
			sql.append(nameField);
			sql.append("='");
			sql.append(value);
			sql.append("'");
			if(StringUtils.isNotEmpty(typeField) && StringUtils.isNotEmpty(typeVelue)){
				sql.append(" and ");
				sql.append(typeField);
				sql.append("='");
				sql.append(typeVelue);
				sql.append("'");
			}
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql.toString());
			if(ResultSetHandler.toInt(rs) > 0){
				throw new Exception("信息已存在, 不能重复输入！");
			}
		}catch (Exception e) {
			logger.error("保存字典信息失败！", e);
			throw e;
		} finally {
			DBFactory.close(null, stmt, rs);
		}
	}
	
	public int add(String tableName, String nameField, String idField, String value, String typeField, String typeVelue) throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBFactory.getConn();
			checkExsits(conn, tableName, nameField, value, typeField, typeVelue);
			boolean hasType = StringUtils.isNotEmpty(typeField) && StringUtils.isNotEmpty(typeVelue);
			StringBuffer sql = new StringBuffer();
			sql.append("insert into ");
			sql.append(tableName);
			sql.append("(");
			sql.append(nameField);
			if(hasType){
				sql.append(",");
				sql.append(typeField);
			}
			sql.append(") values ('");
			sql.append(value);
			if(hasType){
				sql.append("','");
				sql.append(typeVelue);
			}
			sql.append("')");
			stmt = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			stmt.executeUpdate(sql.toString());
			rs = stmt.getGeneratedKeys();
			int id = (int)ResultSetHandler.toLong(rs);
			if(id == -1){
				throw new Exception("添加字典失败！");
			}
			return id;
		}catch (Exception e) {
			logger.error("添加字典失败！", e);
			throw e;
		} finally {
			DBFactory.close(conn, stmt, rs);
		}
	}
	
	public void update(String tableName, String nameField, String idField, String value, int id, String typeField, String typeVelue) throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			boolean hasType = StringUtils.isNotEmpty(typeField) && StringUtils.isNotEmpty(typeVelue);
			StringBuffer sql = new StringBuffer();
			sql.append("update ");
			sql.append(tableName);
			sql.append(" set ");
			sql.append(nameField);
			sql.append("='");
			sql.append(value);
			if(hasType){
				sql.append("',");
				sql.append(typeField);
				sql.append("='");
				sql.append(typeVelue);
			}
			sql.append("' where ");
			sql.append(idField);
			sql.append("=");
			sql.append(id);
			conn = DBFactory.getConn();
			stmt = conn.prepareStatement(sql.toString());
			stmt.executeUpdate(sql.toString());
		}catch (Exception e) {
			logger.error("修改字典失败！", e);
			throw e;
		} finally {
			DBFactory.close(conn, stmt, null);
		}
	}
	
	public void delete(String tableName, String idField, int id) throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("delete from ");
			sql.append(tableName);
			sql.append(" where ");
			sql.append(idField);
			sql.append("=");
			sql.append(id);
			conn = DBFactory.getConn();
			stmt = conn.prepareStatement(sql.toString());
			stmt.executeUpdate(sql.toString());
		}catch (Exception e) {
			logger.error("删除字典失败！", e);
			throw e;
		} finally {
			DBFactory.close(conn, stmt, null);
		}
	}
	
	public void deleteBb(int id) throws Exception{
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DBFactory.getConn();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			stmt.executeUpdate("delete from bb_dic where id="+id);
			ZjBean bean = new ZjBean();
			bean.setBbId(id);
			UpdateInsertHandler.executeDelete(conn, bean);
			conn.commit();
		} catch(Exception e){
			try{
				if(conn != null){
					conn.rollback();
				}
			} catch(Exception e1){}
			throw e;
		} finally {
			DBFactory.close(conn, stmt, null);
		}
	}

}
