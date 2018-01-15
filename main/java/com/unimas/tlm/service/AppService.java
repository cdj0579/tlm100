package com.unimas.tlm.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.unimas.jdbc.DBFactory;
import com.unimas.jdbc.handler.ResultSetHandler;
import com.unimas.tlm.bean.KscjBean;
import com.unimas.tlm.bean.user.StudentInfo;
import com.unimas.tlm.dao.JdbcDao;

@Service
public class AppService {

	/**
     * 保存裁剪过的头像图片
     * @param id
     * @param txImg
     * @throws Exception
     */
    public void saveStuTxImg(int id, String txImg) throws Exception{
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			conn.setAutoCommit(false);
			StudentInfo info = new StudentInfo();
			info.setId(id);
			info.setTx(txImg.getBytes());
			new JdbcDao<StudentInfo>().save(conn, info);
			conn.commit();
		} catch(Exception e){
			if(conn != null){
				try { conn.rollback(); } catch(Exception t){}
			}
			throw e;
		} finally {
			DBFactory.close(conn, null, null);
		}
	}
    
    
    public boolean isSetSrfx(String userNo) throws Exception{
    	boolean result = false;
    	Connection conn = null;
    	PreparedStatement stmt = null;
		ResultSet rs = null;
		long num = 0;
		try {
			conn = DBFactory.getConn();
			String sql = "select count(*) num from kaoshichengji where user_no='"+ userNo +"'";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			num = ResultSetHandler.toLong(rs);
			result = num > 0 ;
		} catch(Exception e){
			throw e;
		} finally {
			DBFactory.close(conn, stmt, rs);
		}
    	return result;
    }
	
    /**
     * 
     * @param id
     * @param txImg
     * @throws Exception
     */
    public void saveKSCJ(KscjBean info,int mbxxId,int userId) throws Exception{
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			conn.setAutoCommit(false);
			StudentInfo stu = new StudentInfo();
			stu.setId(userId);
			stu.setMbxxId(mbxxId);
			new JdbcDao<StudentInfo>().save(conn, stu);
			new JdbcDao<KscjBean>().save(conn, info);
			conn.commit();
		} catch(Exception e){
			e.printStackTrace();
			if(conn != null){
				try { conn.rollback(); } catch(Exception t){}
			}
			throw e;
		} finally {
			DBFactory.close(conn, null, null);
		}
	}
    
    /**
     * 获取学生最近6次的成绩（以总分100的基准换算）
     * @return
     */
    public Map<String, Object> getHistoryScort(String userNo){
    	Map<String, Object> result = new HashMap<String, Object>();
    	int dataLen =  6;
    	Integer[] sx = new Integer[dataLen];
    	Integer[] kx = new Integer[dataLen];
    	Integer[] yw = new Integer[dataLen];
    	Integer[] yy = new Integer[dataLen];
    	Integer[] sh = new Integer[dataLen];
    	Integer[] total = new Integer[dataLen];
    	Connection conn = null;
    	PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBFactory.getConn();
			String sql = "select * from kaoshichengji where user_no='"+ userNo +"' order by id desc limit "+ dataLen ;
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()){
				dataLen-- ;
				sx[dataLen] = rs.getInt("sx_cj") * 100 / rs.getInt("sx_mf"); 
				kx[dataLen] = rs.getInt("kx_cj") * 100 / rs.getInt("kx_mf"); 
				yw[dataLen] = rs.getInt("yw_cj") * 100 / rs.getInt("yw_mf"); 
				yy[dataLen] = rs.getInt("yy_cj") * 100 / rs.getInt("yy_mf"); 
				sh[dataLen] = rs.getInt("sh_cj") * 100 / rs.getInt("sh_mf"); 
				total[dataLen] = sx[dataLen] + kx[dataLen] +yw[dataLen] +yy[dataLen] +sh[dataLen] ; 
			}
		} catch(Exception e){
		} finally {
			DBFactory.close(conn, stmt, rs);
		}
		  System.out.println( Arrays.asList(sx));
		for(int i=dataLen-1 ;i >=0 ;i--){
			sx[i] =  0;  kx[i] =  0;  yw[i] =  0;
			yy[i] =  0;  sh[i] =  0;  total[i] = 0;
		}
		result.put("sx", Arrays.asList(sx));
		result.put("kx", Arrays.asList(kx));
		result.put("yw", Arrays.asList(yw));
		result.put("yy", Arrays.asList(yy));
		result.put("sh", Arrays.asList(sh));
		result.put("total", Arrays.asList(total));
    	return result ;
    }
    
    public static void main(String[] args) {
    	AppService appService = new AppService();
    	System.out.println(appService.getHistoryScort("S722326671"));
    	
    	 String[] array=new String[3];  
         array[0]="王利虎";  
         array[1]="张三";  
         array[2]="李四";  
         List<String> list=Arrays.asList(array);  
         System.out.println(list);
         for(int i=0;i<list.size();i++){  
             System.out.println(list.get(i));  
         }  
	}
}
