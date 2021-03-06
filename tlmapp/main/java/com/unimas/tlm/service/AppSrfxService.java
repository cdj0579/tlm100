package com.unimas.tlm.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.unimas.common.date.DateUtils;
import com.unimas.jdbc.DBFactory;
import com.unimas.jdbc.handler.ResultSetHandler;
import com.unimas.tlm.bean.user.StudentInfo;
import com.unimas.tlm.dao.JdbcDao;

@Service
public class AppSrfxService {

	private int prevStartMoth = 7; //上学期开始月份
	private int prevQzMonth = 11; //上学期期末开始月份
	private int nextStartMoth = 2; //下学期开始月份
	private int nextQzMonth = 5; //下学期期末开始月份
	
	public Map<String,Object> dealFxReasult(int njId,int mbxxId,String userNo,int kmId,String dqId){
		Map<String,Object> result = new HashMap<String, Object>();
		Connection conn = null;
		//int ksfzs = 45; //每课时的分钟数
		Date date = new Date();
		int currentMonth = DateUtils.month(date);
		int xq = 1; //当前学期： 1：上学期；2：下学期
		if(currentMonth >= nextStartMoth && currentMonth < prevStartMoth){
			xq = 2;
		}
		
		try{
			conn =  DBFactory.getConn();
			//ksfzs = getKs(conn);
			result = this.getLastCj(conn, userNo);
			System.out.println(result);
			if(result == null ){
				return result;
			}
			this.getDiffScore(conn, result, mbxxId);
			
			njId = (Integer)result.get("nj_id");
			
			Map<String, Integer> fsMap = this.getXkdw(conn,mbxxId, kmId);
			result.putAll(fsMap);
			int[] data = getCjAndCjmf(result, kmId);
			int e  = data[0] * 100 / data[1];  // 成绩分数（e）
			
			int mbfx = fsMap.get("mbfs");
			int mf = fsMap.get("mf");
			mbfx = mbfx * 100 / mf ;  // 目标分数（m）
			
			int addYear = njId; //年级编号转换为相差年限；
			if(currentMonth >= prevStartMoth ){
				addYear = addYear - 1;
			}
			int c = addYear*12 + ( currentMonth - prevStartMoth ); //入学以来有多少月(不含当月)
			int d = 36 - c ; //离毕业还剩多少月(含当月)
			int k1 = fsMap.remove("k0");
			int k2 = fsMap.remove("k1");
			int k3 = fsMap.remove("k2");
			
			int A1_1  = 0;
			int A1_2  = 0;
			int A2_1 = 0;
			int A2_2 = 0;
			int A3  = 0;
			int A4  = 0;
			
			int mqj = 4; //目标分所在的区间
			int eqj = 1; //成绩分所在的区间
			if(mbfx >= k3 ){
				mqj = 4;
			} else if(mbfx >= k2 ){
				mqj = 3;
			} else if(mbfx >= k1 ){
				mqj = 2;
			} else {
				mqj = 1;
			}
			if( e < k1 ){
				eqj = 1;
			} else if( e < k2 ){
				eqj = 2;
			} else if( e < k3 ){
				eqj = 3;
			}else{
				eqj = 4;
			}
			
			if( eqj >= mqj ){
				eqj = mqj;
			}
			switch(eqj){
			case 1 :
				if(c >= 4){
					A1_1 = this.getClbqValue(conn, njId, xq, kmId, true, dqId);
				}
				if(d >= 6){
					A2_1 = this.getGgz(conn, njId, xq, kmId, dqId,true);
				}
				if(mqj == 1){
					break;
				}
			case 2 :
				if(c >= 4){
					A1_2 = this.getClbqValue(conn, njId, xq, kmId, false, dqId);
				}
				if(d >= 6){
					A2_2 = this.getGgz(conn, njId, xq, kmId, dqId,false);
				}
				if(mqj == 2){
					break;
				}
			case 3 :
				A3  = this.getA3A4(conn, currentMonth, njId, xq, kmId, true);
				if(mqj == 3){
					break;
				}
			case 4 :
				A4  = this.getA3A4(conn, currentMonth, njId, xq, kmId, false);
			}
			
			
			int A2 =  A2_1 +  A2_2 ;
			result.put("A2", A2  /*division(A2,ksfzs)*/ );
			result.put("A1_1", A1_1 /*division(A1_1, ksfzs)*/);
			result.put("A1_2", A1_2 /*division(A1_2, ksfzs)*/);
			result.put("A3", A3 /*division(A3, ksfzs)*/ );
			result.put("A4", A4 /*division(A4, ksfzs)*/ );
			result.put("total", A1_1+A1_2+A2+A3+A4 /*division( A1_1+A1_2+A2+A3+A4, ksfzs)*/ );
			
		}catch (Exception e) {
			// TODO: handle exception
		}finally{
			DBFactory.close(conn, null, null);
		}
		
		return result;
	}
	
	/**
	 * 两整数相除保留一位小数
	 * @param a 被除数
	 * @param b 除数
	 * @return
	 */
	public String division(int a ,int b){
        String result = "";
        float num =(float)a/b;
        DecimalFormat df = new DecimalFormat("0.0");
        result = df.format(num);
        return result;
    }
	//
	/**
	 * 获取课时时间（每课时多少分钟数）
	 * @param conn
	 * @return
	 */
	public int getKs(Connection conn){
		int ks_pt = 45;
		String sql = "select value from config where name='ks_pt'";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			ks_pt =ResultSetHandler.toInt(rs);
			if( ks_pt == -1 ){
				 ks_pt = 45;
			}
		}catch (Exception e) {
		}finally{
			DBFactory.close(null, stmt, rs);
		}
		return ks_pt;
	}
	
	public Map<String,Object> getLastCj(Connection conn,String userNo){
		Map<String, Object> result = new HashMap<String, Object>();
		String sql = "select * from kaoshichengji where id = (select max(id) from kaoshichengji where user_no = ? );";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, userNo);
			rs = stmt.executeQuery();
			if(rs.next()){
				result = ResultSetHandler.rsToMap(rs, rs.getMetaData(), null);
			}
		}catch (Exception e) {
		}finally{
			DBFactory.close(null, stmt, rs);
		}
		return result;
	}
	
	/**
	 * 获取目标学校学科分数及学科档位
	 * @param conn  	数据库连接
	 * @param mbxxId 	当前目标学校编号
	 * @param kmId 		当前科目编号
	 * @return
	 */
	public Map<String, Integer> getXkdw(Connection conn,int mbxxId,int kmId){
		Map<String, Integer> result = new HashMap<String, Integer>();
		String sql = "select level0,level1,level2,mbfs,mf from xkdw T1 LEFT JOIN mbxx_mbf T2 ON T2.mbxx_id=? and T1.km_id = T2.km_id  where T1.km_id = ? ;";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, mbxxId);
			stmt.setInt(2, kmId);
			rs = stmt.executeQuery();
			if(rs.next()){
				result.put("k0", rs.getInt("level0"));
				result.put("k1", rs.getInt("level1"));
				result.put("k2", rs.getInt("level2"));
				result.put("mf", rs.getInt("mf"));
				result.put("mbfs", rs.getInt("mbfs"));
			}
		}catch (Exception e) {
		}finally{
			DBFactory.close(null, stmt, rs);
		}
		return result;
	}
	
	/**
	 * 当离毕业还剩6个月以上(含当月)时，获取同步巩固的提分值；
	 * @param conn  数据库连接
	 * @param njId  年级编号
	 * @param xq 	当前学期： 1：上学期；2：下学期
	 * @param kmId 	当前科目编号
	 * @param dqId 所属区域id
	 * @param flag  true: 基础知识点；false：重难知识点
	 * @return
	 */
	public int getGgz(Connection conn,int njId,int xq, int kmId,String dqId,boolean flag){
		int result = 0;
		String sql = "select IFNULL(sum(ks),0) count from zsd_main where nj_id = ? and xq = ? and km_id = ? and dq_id=? and ( nd_id=? or nd_id=? )";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, njId);
			stmt.setInt(2, xq);
			stmt.setInt(3, kmId);
			stmt.setString(4, dqId);
			if( flag ){
				stmt.setInt(5, 7);
				stmt.setInt(6, 8);
			}else{
				stmt.setInt(5, 9);
				stmt.setInt(6, 10);
			}
			rs = stmt.executeQuery();
			result = ResultSetHandler.toInt(rs);
		}catch (Exception e) {
			// TODO: handle exception
		}finally{
			DBFactory.close(null, stmt, rs);
		}
		return result;
	}
	
	/**
	 * 获取入学4个月以上的 查漏补缺提分值（基础知识点 或 重难知识点）
	 * @param conn  数据库连接
	 * @param njId  年级编号
	 * @param xq 	当前学期： 1：上学期；2：下学期
	 * @param kmId  科目编号
	 * @param flag  true: 基础知识点；false：重难知识点
	 * @param dqId 所属区域id
	 * @return
	 */
	public int getClbqValue(Connection conn,int njId,int xq, int kmId,boolean flag,String dqId){
		int result = 0;
		if(xq == 1){ 
			njId = njId -1 ;
			xq = 2;
		}else{
			xq = 1;
		}
		String sql = "select IFNULL(sum(ks),0) count from zsd_main where nj_id = ? and xq = ? and km_id = ? and ( nd_id=? or nd_id=? ) and dq_id=?";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, njId);
			stmt.setInt(2, xq);
			stmt.setInt(3, kmId);
			if( flag ){
				stmt.setInt(4, 7);
				stmt.setInt(5, 8);
			}else{
				stmt.setInt(4, 9);
				stmt.setInt(5, 10);
			}
			stmt.setString(6, dqId);
			rs = stmt.executeQuery();
			result = ResultSetHandler.toInt(rs);
		}catch (Exception e) {
			// TODO: handle exception
		}finally{
			DBFactory.close(null, stmt, rs);
		}
		return result;
	}
	
	/**
	 * 获取综合题型或压轴专题的提分值
	 * @param conn			数据库连接
	 * @param currentMonth  当前月份
	 * @param njId			年级编号
	 * @param xq			当前学期： 1：上学期；2：下学期
	 * @param kmId			当前科目编号
	 * @param flag 			true：综合题型；false：压轴专题
	 * @return
	 */
	public int getA3A4(Connection conn,int currentMonth,int njId,int xq, int kmId,boolean flag){
		int result = 0;
		int qzqm = 2; //其中期末：2:期末；1：其中；
		if(xq == 1 && currentMonth < prevQzMonth){
			qzqm = 1;
		}else if(xq == 2 && currentMonth < nextQzMonth){
			qzqm = 1;
		}
				
		String sql = "select IFNULL(sum(ks),0) count from zt_main where nj_id = ? and xq = ? and km_id=? and qzqm=? and nd_id=?";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, njId);
			stmt.setInt(2, xq);
			stmt.setInt(3, kmId);
			stmt.setInt(4, qzqm);
			if( flag ){
				stmt.setInt(5, 1);
			}else {
				stmt.setInt(5, 2);
			}
			rs = stmt.executeQuery();
			result = ResultSetHandler.toInt(rs);
		}catch (Exception e) {
			// TODO: handle exception
		}finally{
			DBFactory.close(null, stmt, rs);
		}
		return result;
	}
	private int[] getCjAndCjmf(Map<String, Object> dataMap ,int km){
		int cj = 0;
		int cjmf = 0;
		if(km == 1) {
			cj = (Integer)dataMap.get("sx_cj");
			cjmf = (Integer)dataMap.get("sx_mf");
		}else if(km == 2) {
			cj = (Integer)dataMap.get("kx_cj");
			cjmf = (Integer)dataMap.get("kx_mf");
		}else if(km == 3) {
			cj = (Integer)dataMap.get("yw_cj");
			cjmf = (Integer)dataMap.get("yw_mf");
		}else if(km == 4) {
			cj = (Integer)dataMap.get("yy_cj");
			cjmf = (Integer)dataMap.get("yy_mf");
		}else if(km == 5) {
			cj = (Integer)dataMap.get("sh_cj");
			cjmf = (Integer)dataMap.get("sh_mf");
		}
		int[] result = new int[2];
		result[0] = cj;
		result[1] = cjmf;
		return result;
	}
	public void getDiffScore(Connection conn,Map<String, Object> dataMap ,int mbxxId){
		String sql = "select km_id, mbfs,mf from mbxx_mbf where mbxx_id=?";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, mbxxId);
			rs = stmt.executeQuery();
			while(rs.next()){
				int km = rs.getInt("km_id");
				int cj = 0;
				int cjmf = 0;
				if(km >= 1 && km <= 5){
					int[] data = getCjAndCjmf(dataMap, km);
					cj = data[0];
					cjmf = data[1];
				}else{
					continue;
				}
				int mf = rs.getInt("mf");
				int mbfs = rs.getInt("mbfs");
				if(mf == cjmf ){  //分数总分差距以目标科目总分为基准
					cj = mbfs - cj ;
				}else{
					cj = mbfs - cj * mf/cjmf ; 
				}
				dataMap.put( "diff_"+km, cj );
			}
		}catch (Exception e) {
		}finally{
			DBFactory.close(null, stmt, rs);
		}
	}
	
	public static void main(String[] args) {
		AppSrfxService app = new  AppSrfxService();
		Map<String, Object> dataMap = app.dealFxReasult(1, 12, "S722326671", 1,"330501");
		System.out.println(dataMap);
	}
}
