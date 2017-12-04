package com.unimas.txl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import com.unimas.common.util.StringUtils;
import com.unimas.jdbc.DBFactory;
import com.unimas.jdbc.handler.ResultSetHandler;
import com.unimas.txl.bean.LxrBean;

public class LxrDao extends JdbcDao<LxrBean> {
	
	public List<LxrBean> queryNofp(int jigouId, int xuexiaoId, String dqId, int nj, int bj) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBFactory.getConn();
			StringBuffer sql = new StringBuffer();
			sql.append( "select T0.*,T1.xuexiaoming,T2.name as dq_name from txl_lianxiren as T0 ");
			sql.append( "left join txl_xuexiao as T1 on (T1.id = T0.xuexiao_id) ");
			sql.append( "left join xzqh as T2 on (T2.code = T0.dq_id) ");
			sql.append( "left join txl_lianxiren_fenpei as T3 on (T3.lxr_id = T0.id)");
			sql.append( "left join txl_lianxiren_guanzhu as T4 on (T4.lxr_id = T0.id)");
			sql.append( "left join txl_lianxiren_qianyue as T5 on (T5.lxr_id = T0.id)");
			sql.append( " where T0.is_del = 0 and T3.id is null and T4.id is null and T5.id is null ");
			sql.append(" and T0.jigou_id = "+jigouId);
			if(xuexiaoId > 0){
				sql.append(" and T0.xuexiao_id = "+xuexiaoId);
			}
			if(nj > 0){
				sql.append(" and T0.nianji = "+nj);
			}
			if(bj > 0){
				sql.append(" and T0.banji = "+bj);
			}
			if(StringUtils.isNotEmpty(dqId)){
				sql.append(" and T0.dq_id = '"+dqId+"'");
			}
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql.toString());
			return ResultSetHandler.listBean(rs, LxrBean.class);
		} finally {
			DBFactory.close(conn, stmt, rs);
		}
	}
	
	public List<LxrBean> queryOnAdmin(int jigouId, int status, int xuexiaoId, String dqId, int nj, int bj) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBFactory.getConn();
			StringBuffer sql = new StringBuffer();
			sql.append( "SELECT t0.*,t1.name as shiyongzhe from (");
			sql.append( 	"select a.*, ");
			sql.append( 		"case when d.id is not null then 3 when c.id is not null then 2 when b.id is not null then 1 else 0 end as status,");
			sql.append( 		"case when d.id is not null then d.syz_id when c.id is not null then c.syz_id when b.id is not null then b.syz_id else null end as syz_id,");
			sql.append( 		"case when d.id is not null then d.shijian when c.id is not null then c.shijian when b.id is not null then b.shijian else null end as shijian,");
			sql.append( 		"e.xuexiaoming,f.name as dq_name ");
			sql.append( 	"from txl_lianxiren a ");
			sql.append( 	"left join txl_lianxiren_fenpei as b on (b.lxr_id = a.id) ");
			sql.append( 	"left join txl_lianxiren_guanzhu as c on (c.lxr_id = a.id) ");
			sql.append( 	"left join txl_lianxiren_qianyue as d on (d.lxr_id = a.id) ");
			sql.append( 	"left join txl_xuexiao as e on (e.id = a.xuexiao_id) ");
			sql.append( 	"left join xzqh as f on (f.code = a.dq_id) ");
			sql.append( 	"where	a.jigou_id="+jigouId+" and a.is_del=0 ");
			if(xuexiaoId > 0){
				sql.append(" and a.xuexiao_id = "+xuexiaoId);
			}
			if(nj > 0){
				sql.append(" and a.nianji = "+nj);
			}
			if(bj > 0){
				sql.append(" and a.banji = "+bj);
			}
			if(StringUtils.isNotEmpty(dqId)){
				sql.append(" and a.dq_id = '"+dqId+"'");
			}
			sql.append( ") t0 ");
			sql.append( "left join txl_shiyongzhe as t1 on (t0.syz_id = t1.id) ");
			if(status >= 0){
				sql.append(" where t0.status = "+status);
			}
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql.toString());
			return ResultSetHandler.listBean(rs, LxrBean.class);
		} finally {
			DBFactory.close(conn, stmt, rs);
		}
	}
	
	public Object save(LxrBean b) throws Exception{
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			checkPhone(conn, b.getJigouId(), b.getId(), b.getPhone());
			return save(conn, b);
		} finally {
			DBFactory.close(conn, null, null);
		}
	}
	
	public void checkPhone(Connection conn, int jigouId, int id, String phone) throws Exception {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select count(*) from txl_lianxiren where jigou_id=? and phone=?");
			if(id > 0){
				sql.append( " and id <> "+id);
			}
			stmt = conn.prepareStatement(sql.toString());
			stmt.setInt(1, jigouId);
			stmt.setString(2, phone);
			rs = stmt.executeQuery();
			if(ResultSetHandler.toLong(rs) > 0){
				throw new Exception("联系电话已存在！");
			}
		} finally {
			DBFactory.close(null, stmt, rs);
		}
	}
	
	public void checkStatus(Connection conn, int id) throws Exception {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append( "SELECT count(*) from ( ");
			sql.append( 	"select id from txl_lianxiren_fenpei where lxr_id="+id);
			sql.append( 	" UNION ");
			sql.append( 	"select id from txl_lianxiren_guanzhu where lxr_id="+id);
			sql.append( 	" UNION ");
			sql.append( 	"select id from txl_lianxiren_qianyue where lxr_id="+id);
			sql.append( ") t ");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql.toString());
			if(ResultSetHandler.toLong(rs) > 0){
				throw new Exception("联系人已分配，不能删除！");
			}
		} finally {
			DBFactory.close(null, stmt, rs);
		}
	}
	
	public static void main(String[] args) throws Exception {
		LxrBean b = new LxrBean();
		b.setIsDel(0);
		b.setJigouId(1);
		b.setXuexiaoId(2);
		b.setNianji(7);
		b.setBanji(1);
		List<?> list = new LxrDao().query(b);
		System.out.println(list);
	}

}
