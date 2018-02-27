package com.unimas.txl.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.unimas.jdbc.DBFactory;
import com.unimas.jdbc.handler.ResultSetHandler;


@Service
public class ShareImgSetService {
	
	public List<Map<String, Object>> getBaseImgInfo(){
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		StringBuffer sql=new StringBuffer();
		sql.append("select id,tupian from txl_base_tupian");
		try {
			conn = DBFactory.getConn();
			stmt = conn.prepareStatement(sql.toString());
			rs = stmt.executeQuery();
			while (rs.next()){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id",rs.getInt("id"));
				map.put("tupian",rs.getString("tupian"));
				result.add(map);
			}
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			DBFactory.close(conn, stmt, rs);
		}		
		return result;
	}
	
	public void saveShareImgInfo( int id , String imgInfo ){
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			if(id > 0){
				update(conn, id, imgInfo);
			}else{
				insert(conn, imgInfo);
			}
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			DBFactory.close(conn, null, null);
		}		
	}

	public void insert(Connection conn, String imgInfo ){
		StringBuffer sql=new StringBuffer();
		sql.append("insert into txl_base_tupian( tupian ) value(?)"); 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sql.toString());
			stmt.setString(1, imgInfo);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBFactory.close(null, stmt, null);
		}
	}
	
	public void update(Connection conn, int id , String imgInfo ){
		StringBuffer sql=new StringBuffer();
		sql.append(" update txl_base_tupian set tupian=? where id = ? "); 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sql.toString());
			stmt.setString(1, imgInfo);
			stmt.setInt(2, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBFactory.close(null, stmt, null);
		}
	}
	
	public static void main(String[] args) {
		ShareImgSetService service = new ShareImgSetService();
		System.out.println(service.getBaseImgInfo());
		service.saveShareImgInfo(1,"share1.jpg");
	}
}
