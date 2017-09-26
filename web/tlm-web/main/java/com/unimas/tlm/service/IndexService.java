package com.unimas.tlm.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
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
	
	public static void main(String[] args) {
		IndexService i= new IndexService();
		System.out.println(i.getIndexNumInfo("admin"));
	}
	
}
