package com.unimas.tlm.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.unimas.jdbc.DBFactory;
import com.unimas.jdbc.handler.ResultSetHandler;

@Service
public class IndexService {

	public Map<String ,Object> getIndexNumInfo(String user_no){
		Map<String, Object> result  = new HashMap<String, Object>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		StringBuffer sql=new StringBuffer();
		if("admin".equals(user_no)){
			sql.append("select  'ztNum' as name , count(*) as  count from zt_content ")
			   .append("UNION ALL select  'zsdNum' as name , count(*) as count from zsd_main ")
			   .append("UNION ALL select  'jaNum' as name , count(*) as count from ja_list ")
			   .append("UNION ALL select  'userNum' as name , count(*) as count from  account where user_no!='admin'");
		}else{
			sql.append("select  'jfNum' as name , jf as count from teacher_info where  user_no= ? ")
			   .append("UNION ALL select  'wcrwNum' as name , count(*) as count from rw_list where user_no= ? ")
			   .append("UNION ALL select  'xtNum' as name , count(*) as count from xt_main where user_no= ? ")
			   .append("UNION ALL select  'jaNum' as name , count(*) as count from ja_list where user_no= ? ");
		}
	
		try {
			conn = DBFactory.getConn();
			stmt = conn.prepareStatement(sql.toString());
			if(!"admin".equals(user_no)){
				stmt.setString(1, user_no);
				stmt.setString(2, user_no);
				stmt.setString(3, user_no);
				stmt.setString(4, user_no);
			}	
			rs = stmt.executeQuery();
			result = ResultSetHandler.map(rs, "name", "count");
		} catch(Exception e){
			e.printStackTrace();
			result.put("ztNum", 0);
			result.put("zsdNum", 0);
			result.put("userNum", 0);
			result.put("jfNum", 0);
			result.put("wcrwNum", 0);
			result.put("xtNum", 0);
			result.put("jaNum", 0);
		} finally {
			DBFactory.close(conn, stmt, rs);
		}
		return result ;
	}
	
	public Map<String ,Object> getIndexChartInfo(String type){
		Map<String, Object> result  = new HashMap<String, Object>();
		List<String> listX = new ArrayList<String>();
    	List<Long> listY = new ArrayList<Long>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		StringBuffer sql=new StringBuffer();
		if("t".equals(type)){
			sql.append("select `name`, hyd as count from teacher_info order by hyd DESC limit 10; ");
		}else if("zsd".equals(type)){
			sql.append("select b.name , a.count from ( ")
			   .append("select count(*) as count ,cid from user_collections  where type='zsd' ")
			   .append("GROUP BY cid ORDER BY count DESC limit 10 ) a LEFT JOIN zsd_content b ON b.id = a.cid ");
		}
	
		try {
			conn = DBFactory.getConn();
			stmt = conn.prepareStatement(sql.toString());
			rs = stmt.executeQuery();
			while (rs.next()){
				listX.add(rs.getString(1));
				listY.add(rs.getLong(2));
			}
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			DBFactory.close(conn, stmt, rs);
		}
		int size = listX.size(); 
		if(size < 10){
			for(int i=size ;i<=10; i++){
				listX.add("");
				listY.add(0L);
			}
		}
		result.put("x", listX);
		result.put("y", listY);
		return result ;
	}
	
	
	public List< Map<String ,Object>> getTeacherGxphb(String user_no,String type){
		List< Map<String ,Object>> result  = new ArrayList<Map<String,Object>>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String tableName = null;
		if("zsd".equals(type)){
			tableName = "zsd_content";
		}else if("ja".equals(type)){
			tableName = "ja_list";
		}else if("xt".equals(type)){
			tableName = "xt_main";
		}else if("zt".equals(type)){
			tableName = "zt_content";
		}
		StringBuffer sql=new StringBuffer();
		sql.append("select num, jf ,b.`name` from")
		   .append(" (select count(*) as num ,cid, SUM(jf) as jf from user_collections  where type=")
		   .append("? GROUP BY cid) a  , ") .append(tableName)
		  .append(" b where a.cid = b.id and user_no = ? ORDER BY jf desc limit 100; ");
		try {
			conn = DBFactory.getConn();
			stmt = conn.prepareStatement(sql.toString());
			stmt.setString(1, type);
			stmt.setString(2, user_no);
			rs = stmt.executeQuery();
			while (rs.next()){
				Map<String ,Object> map = new HashMap<String, Object>();
				map.put("num", rs.getString("num"));
				map.put("jf", rs.getString("jf"));
				String str = rs.getString(3);
				map.put("name", str);
				result.add(map);
			}
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			DBFactory.close(conn, stmt, rs);
		}		
		return result ;
	}
	
	
	public static void main(String[] args) {
		IndexService i= new IndexService();
		System.out.println(i.getTeacherGxphb("zsd" , "T660375285"));
	}
	
}
