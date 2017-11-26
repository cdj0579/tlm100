package com.unimas.txl.dao;

import java.sql.Connection;
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
