package com.unimas.txl.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.unimas.common.date.DateUtils;
import com.unimas.common.md5.MD5;
import com.unimas.jdbc.DBFactory;
import com.unimas.jdbc.handler.ResultSetHandler;
import com.unimas.txl.bean.user.Account;
import com.unimas.txl.bean.user.FenPeiInfo;
import com.unimas.txl.bean.user.GuanZhuInfo;
import com.unimas.txl.bean.user.LianXiRenInfo;
import com.unimas.txl.bean.user.QianYueInfo;
import com.unimas.txl.dao.JdbcDao;

@Service
public class AppIndexService {
	
	public void saveGuanZhuInfo(Connection conn, int id, int syzId, int lxrId,String datetime) throws Exception{
		GuanZhuInfo info = new GuanZhuInfo();
		info.setLxrId(lxrId);
		info.setSyzId(syzId);
		info.setDatetime(datetime);
		new JdbcDao<GuanZhuInfo>().save(conn, info);
	}
	
	public void saveGuanZhuInfo(int id,int syzId, int lxrId, String datetime) throws Exception{
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			saveGuanZhuInfo(conn,id, syzId, lxrId, datetime);
		} finally {
			DBFactory.close(conn, null, null);
		}
	}
	
	public void saveQianYueInfo(Connection conn,int id, int syzId, int  lxrId,String beizhu,String datetime) throws Exception{
		QianYueInfo info = new QianYueInfo();
		info.setId(id);
		info.setLxrId(lxrId);
		info.setSyzId(syzId);
		info.setBeizhu(beizhu);
		info.setDatetime(datetime);
		new JdbcDao<QianYueInfo>().save(conn, info);
	}
	public void saveQianYueInfo(int id,int syzId, int lxrId, String beizhu, String datetime) throws Exception{
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			saveQianYueInfo(conn, id,syzId, lxrId,beizhu, datetime);
		} finally {
			DBFactory.close(conn, null, null);
		}
	}
	
	private void updateLianxirenBeizhu(Connection conn, int lxrId,String beizhu) throws Exception{
		LianXiRenInfo info = new LianXiRenInfo();
		info.setId(lxrId);
		info.setBeizhu(beizhu);
		new JdbcDao<LianXiRenInfo>().save(conn, info);
	}
	
	public void updateLianxirenBeizhu(int lxrId, String beizhu) throws Exception{
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			updateLianxirenBeizhu(conn, lxrId, beizhu);
		} finally {
			DBFactory.close(conn, null, null);
		}
	}
	
	public int isExistsInfo(Connection conn,String tableName,int lxrid,int syzid){
		PreparedStatement stmt = null;
		ResultSet rs = null;
		StringBuffer sqlSb = new StringBuffer();
		sqlSb.append("select id from ");
		sqlSb.append(tableName);
		sqlSb.append(" where lxr_id=? and  syz_id=?");
		int id = -1;
		try {
			stmt = conn.prepareStatement(sqlSb.toString());
			stmt.setInt(1, lxrid);
			stmt.setInt(2, syzid);
			rs = stmt.executeQuery();
			id = ResultSetHandler.toInt(rs);
		}catch (Exception e) {
			//throw new Exception("服务器异常！");
		} finally {
			DBFactory.close(null, stmt, rs);
		}
		return id;
	}
	
	public int isExistsInfo(int lxrId, int syzId,boolean isGx) throws Exception{
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			String tatbleName = " txl_lianxiren_guanzhu ";
			if(isGx){
				tatbleName = " txl_lianxiren_qianyue ";
			}
			return isExistsInfo(conn,tatbleName, lxrId, syzId);
		} finally {
			DBFactory.close(conn, null, null);
		}
	}
	/*@SuppressWarnings("unchecked")
	public List<QianYueInfo> queryAllQYInfoBySyzId(int syzId) throws Exception{
		QianYueInfo qy = new QianYueInfo();
		qy.setSyzId(syzId);
		return (List<QianYueInfo>)new JdbcDao<QianYueInfo>().query(qy);
	} 

	@SuppressWarnings("unchecked")
	public List<GuanZhuInfo> queryAllGZInfoBySyzId(int syzId) throws Exception{
		GuanZhuInfo qy = new GuanZhuInfo();
		qy.setSyzId(syzId);
		return (List<GuanZhuInfo>)new JdbcDao<GuanZhuInfo>().query(qy);
	} */
	
	/**
	 * 获取单个分配联系人信息
	 * @param syzId 使用者ID
	 * @param fpId  共享或关注与使用者关联的记录Id
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> queryOneFenPeiLianxiren(int jgId ,String syzId,String fpId) throws Exception{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Map<String, Object> result = null;
		StringBuffer sqlSb = new StringBuffer();
		sqlSb.append(" select  T0.id as fpId ,T0.lxr_id as lxrId,T1.lianxiren as name,T1.xingbie as sex,T1.dq_id quyu,")
			.append(" T2.xuexiaoming as xuexiao,CONCAT(T1.nianji,'年级（',T1.banji,'）班') banji,T1.phone ,T1.beizhu as gzbeizhu ")
			.append(" from txl_lianxiren_fenpei T0 right join txl_lianxiren T1 on T1.id = T0.lxr_id  and T1.is_del=0 and T1.jigou_id=? ")
			.append(" left join txl_xuexiao T2 on T2.id=T1.xuexiao_id where T0.syz_id=? and T0.id >? order by T0.id limit 1");
		try {
			conn = DBFactory.getConn();
			stmt = conn.prepareStatement(sqlSb.toString());
			stmt.setInt(1, jgId);
			stmt.setString(2, syzId);
			stmt.setString(3, fpId);
			rs = stmt.executeQuery();
			if(rs != null && rs.next()){
				ResultSetMetaData md = rs.getMetaData();
				result = ResultSetHandler.rsToMap(rs, md,null);
			}
		} finally {
			DBFactory.close(conn, stmt, rs);
		}
		return result;
	}
	
	/**
	 * 获取某个使用者关注或共享的联系人列表;
	 * @param syzId 使用者Id
	 * @param isGx true:共享;false:关注
	 * @return
	 * @throws Exception
	 */
	
	
	public List<Map<String, Object>> queryGzOrGxLianxiren(int jgId,String syzId,boolean isGx) throws Exception{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Map<String, Object>> result = null;
		StringBuffer sqlSb = new StringBuffer();
		sqlSb.append(" select T0.id as fpId ,T0.lxr_id as lxrId,T1.lianxiren as name,T1.xingbie as sex,T1.dq_id quyu, ");
		if(isGx){
			sqlSb.append(" T0.beizhu as gxbeizhu , ");
		}
		sqlSb.append(" T2.xuexiaoming as xuexiao,CONCAT(T1.nianji,'年级（',T1.banji,'）班') banji,T1.phone ,T1.beizhu as gzbeizhu from");
		if(isGx){
			sqlSb.append(" txl_lianxiren_qianyue "); //共享关联表
		}else {
			sqlSb.append(" txl_lianxiren_guanzhu "); //关注关联表
		}
		sqlSb.append(" T0 right join txl_lianxiren T1 on T1.id = T0.lxr_id  and T1.is_del=0 and T1.jigou_id=? ")
			.append(" left join txl_xuexiao T2 on T2.id=T1.xuexiao_id where T0.syz_id=? ");
		
		try {
			conn = DBFactory.getConn();
			stmt = conn.prepareStatement(sqlSb.toString());
			stmt.setInt(1, jgId);
			stmt.setString(2, syzId);
			rs = stmt.executeQuery();
			result = ResultSetHandler.listMap(rs);
		} finally {
			DBFactory.close(conn, stmt, rs);
		}
		return result;
	}
	
	public static void main(String[] args) throws Exception {
		AppIndexService service = new AppIndexService();
		service.saveQianYueInfo(-1 ,1,1,"签约备注",DateUtils.formatDateToHMS(new Date()));
//		service.updateLianxirenBeizhu(1, DateUtils.formatDateToHMS(new Date()));
	
		System.out.println(service.queryOneFenPeiLianxiren(1,"1", "0"));
//		System.out.println(service.queryGzOrGxLianxiren("1", true));
//		System.out.println(service.queryGzOrGxLianxiren("1", false));
	
	}
}
