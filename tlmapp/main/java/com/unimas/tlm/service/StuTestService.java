package com.unimas.tlm.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.unimas.jdbc.DBFactory;
import com.unimas.jdbc.handler.ResultSetHandler;
import com.unimas.tlm.bean.datamodal.AjaxDataModal;


@Service
public class StuTestService {
	private int testNumLimit  = 20;
	
	public Map<String, Object> loadStuTestPage( int njId , int kmId ){
		Map<String, Object> result = new HashMap<String, Object>();
		Connection conn = null;
		try{
			conn =  DBFactory.getConn();
			List<Object> idsList = this.getCstIds(conn, njId, kmId);
			if(idsList.size() > 0){
				List<Object> testIds = this.getRandomId(idsList, testNumLimit);
				Map<String, Object> dataInfo = this.getOneCstInfo(conn, (Integer)testIds.get(0));
				result.put("ids", testIds);
				result.put("oneCstInfo", dataInfo);
			}else{
				result.put("errMsg", "null");
			} 
		}catch (Exception e) {
			result.put("errMsg", "error");
		}finally{
			DBFactory.close(conn, null, null);
		}
		return result ;
	}
	
	private List<Object> getCstIds(Connection conn,int njId,int kmId){
		List<Object> list = new ArrayList<Object>();
		String sql = "select id from cstk where nj_id = ? and km_id = ?";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, njId);
			stmt.setInt(2, kmId);
			rs = stmt.executeQuery();
			while( rs.next() ){
				list.add(rs.getInt("id"));
			}
		}catch (Exception e) {
		}finally{
			DBFactory.close(null, stmt, rs);
		}
		return list;
	}
	
	private List<Object> getRandomId(List<Object> source,int limit){
		List<Object> result = new ArrayList<Object>();  
		int len = source.size();
		if(len <= limit ){
			return source;
		}
		int index = 0;  
		Random rd = new Random();  
		for (int i = 0; i < limit; i++) {  
			//待选数组0到(len-2)随机一个下标  
			index = Math.abs(rd.nextInt() % len--);  
			//将随机到的数放入结果集  
			result.add(i,source.get(index));  
			//将待选数组中被随机到的数，用待选数组(len-1)下标对应的数替换  
			source.set(index,source.get(len)) ;  
       }
	   return result;
	}
	
	public Map<String, Object> getOneCstInfo(Connection conn,int cstId){
		Map<String, Object> result = new HashMap<String, Object>();
		String sql = "select name,answer from cstk where id = ?";
		String answerSql = "select t1.option,t1.name from cst_option t1 where pid = ? order by t1.option";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, cstId);
			rs = stmt.executeQuery();
			if(rs.next()){
				result = ResultSetHandler.rsToMap(rs, rs.getMetaData(), null);
				stmt = conn.prepareStatement(answerSql);
				stmt.setInt(1, cstId);
				rs = stmt.executeQuery();
				List<Map<String, Object>> list = ResultSetHandler.listMap(rs);
				result.put("ansList", list);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBFactory.close(null, stmt, rs);
		}
		return result;
	}
	
	public Map<String, Object> getOneCst( int cstId){
		Map<String, Object> result = new HashMap<String, Object>();
		Connection conn = null;
		try{
			conn =  DBFactory.getConn();
			result = this.getOneCstInfo(conn, cstId);
			if(result.size() == 0){
				result.put("errMsg", "null");
			}
		}catch (Exception e) {
			result.put("errMsg", "error");
		}finally{
			DBFactory.close(conn, null, null);
		}
		return result ;
	}
	
	public void saveTestResult(String user_no,int niId,int kmId,String ids,String rightAnswers,
			String testAnswer,String subjective,String time ) throws Exception{
		String in_sql1 = "insert into cs_result(user_no,nj_id,km_id,Unable_score,unsure_score,sure_error_score,all_score,use_time) value(?, ?, ?,?,?,?,?,?)";
		String in_sql2 = "insert into csdt_rescord(pid,test_ids,right_answer,test_answer,subjective) value(?, ?, ? ,?,?)";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			List<Integer> data = this.dealTestScore(rightAnswers, testAnswer, subjective); 
			conn =  DBFactory.getConn();
			stmt = conn.prepareStatement(in_sql1);
			stmt.setString(1, user_no);
			stmt.setInt(2, niId);
			stmt.setInt(3, kmId);
			stmt.setInt(4, data.get(1));
			stmt.setInt(5, data.get(2));
			stmt.setInt(6, data.get(3));
			stmt.setInt(7, data.get(0));
			stmt.setString(8,  time);
			stmt.executeUpdate();
	        rs = stmt.getGeneratedKeys();
	        int pid = -1;
	        if(rs.next()) {
	        	pid = rs.getInt(1);
            }
	        stmt = conn.prepareStatement(in_sql2);
			stmt.setInt(1, pid);
			stmt.setString(2, ids);
			stmt.setString(3, rightAnswers);
			stmt.setString(4, testAnswer);
			stmt.setString(5, subjective);
			stmt.executeUpdate();
		}catch (Exception e) {
			throw e;
		}finally{
			DBFactory.close(conn, stmt, rs);
		}
	}
	
	private List<Integer> dealTestScore( String rightAnswers,String testAnswer,String subjective ){
		int all = 0 ,unable = 0 ,subje = 0 ,sureError = 0  ;
		String[] right = rightAnswers.split(";");
		String[] test = testAnswer.split(";");
		String[] zhuguan = subjective.split(";");
		for(int i=0;i<right.length;i++){
			if(right[i].equals(test[i])){
				all++;
				if("1".equals(zhuguan[i])){
					unable++;
				}else if("2".equals(zhuguan[i])){
					subje++;
				}
			}else if("0".equals(zhuguan[i])){
				sureError++;
			}
		}
		int oneScore = 100 /test.length ;
		List<Integer> result = new ArrayList<Integer>();
		result.add(all * oneScore );
		result.add(unable * oneScore);
		result.add(subje * oneScore);
		result.add(sureError * oneScore);
		return result ;
	}
	
	public Map<String, Object> getTestResult(String user_no ) {
		Map<String, Object> result = null;
		String sql = "select unable_score as unable,unsure_score unsure ,sure_error_score sureError, all_score total,use_time from cs_result where user_no = ? order by id desc limit 1;";
		result = queryLastTestResult(user_no, sql);
		return result;
	}

	private Map<String, Object> queryLastTestResult(String user_no, String sql) {
		Map<String, Object> result = new HashMap<String, Object>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			conn =  DBFactory.getConn();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, user_no);
			rs = stmt.executeQuery();
			if(rs.next()){
				result = ResultSetHandler.rsToMap(rs, rs.getMetaData(), null);
			}
		}catch (Exception e) {
			
		}finally{
			DBFactory.close(conn, stmt, rs);
		}
		return result;
	}
	
	public int getOverStuNum(int njId ,int kmId, int allTotal) {
		int result = 0;
		String sql = "select count(*) from cs_result where nj_id=? and km_id=? and all_score < ?" ;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			conn =  DBFactory.getConn();
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, njId);
			stmt.setInt(2, kmId);
			stmt.setInt(3, allTotal);
			rs = stmt.executeQuery();
			result = ResultSetHandler.toInt(rs);
		}catch (Exception e) {
			
		}finally{
			DBFactory.close(conn, stmt, rs);
		}
		return result;
	}
	
	public Map<String, Object> getPkResultData(String userNo){
		Map<String, Object> result = new HashMap<String, Object>();
		String sql = "select t1.km_id,t1.nj_id,t2.name kmName , t1.all_score total,t1.use_time from cs_result t1 " +
				"LEFT JOIN km_dic t2 ON t2.id = t1.km_id  where user_no = ? order by t1.id desc limit 1;";
			result = queryLastTestResult(userNo, sql);
			int allseconds = 600; //10分钟换算成秒
			int speed = 0;
			String useTime = (String)result.get("use_time");
			if(useTime != null){
				String[] times = useTime.split("[:]");
				int usr = Integer.parseInt(times[0])*60 + Integer.parseInt(times[1]);
				speed = 100-(usr*100/allseconds); //剩余时间百分比数值
			}
			result.put("speed", speed);
		return result;
	}
	
	public Map<String, Object> checkTest(String user_no ) {
		Map<String, Object> result = new HashMap<String, Object>();
		String sql = "select DISTINCT CONCAT('km',km_id) kmId ,km_id as km from  cs_result where user_no=? ";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			conn =  DBFactory.getConn();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, user_no);
			rs = stmt.executeQuery();
			result = ResultSetHandler.map(rs);
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBFactory.close(conn, stmt, rs);
		}
		return result;
	}

	
	public static void main(String[] args) {
		StuTestService ser = new StuTestService();
//		System.out.println(ser.loadStuTestPage(2, 1));
		System.err.println(Integer.parseInt("08:00".split("[:]")[0]));
		Map<String,Object> data = ser.getTestResult("S722326671");
		Map<String,Object> data2 =  ser.checkTest("S722326671");
		System.out.println("test_result============="+ data2);
		AjaxDataModal dm = new AjaxDataModal(true);
		dm.putAll(ser.getOneCst(5));
		System.out.println(dm);
	}
}
