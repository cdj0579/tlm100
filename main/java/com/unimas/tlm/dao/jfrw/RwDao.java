package com.unimas.tlm.dao.jfrw;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.unimas.jdbc.DBFactory;
import com.unimas.jdbc.handler.ResultSetHandler;
import com.unimas.jdbc.handler.SelectHandler;
import com.unimas.jdbc.handler.entry.SelectSqlModal;
import com.unimas.tlm.bean.jfrw.RwBean;
import com.unimas.tlm.bean.jfrw.RwListBean;
import com.unimas.tlm.bean.user.TeacherInfo;
import com.unimas.tlm.dao.JdbcDao;
import com.unimas.tlm.service.jfrw.RwService;

public class RwDao extends JdbcDao<RwBean> {
	
	public List<RwBean> search(int status, int kmId, int njId) throws Exception{
		Connection conn = null;
		try {
			RwBean bean = new RwBean();
			bean.setKmId(kmId);
			bean.setNjId(njId);
			bean.setStatus(status);
			conn = DBFactory.getConn();
			SelectSqlModal<RwBean> sm = SelectHandler.createSelectModal(conn, bean);
			sm.addOrder(RwBean.class, "modifyTime", SelectSqlModal.Order.DESC);
			sm.addOrder(RwBean.class, "status", SelectSqlModal.Order.ASC);
			return SelectHandler.executeSelect(conn, sm);
		} finally {
			DBFactory.close(conn, null, null);
		}
	}
	
	public List<RwBean> dwcrw(int status, int kmId, String userNo) throws Exception{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			
			String sql = "select distinct a.*,c.name as njName from rw_main a LEFT JOIN nj_dic c on(a.nj_id = c.id) where a.status=? and a.km_id=? and a.id not in(select rw_id from rw_list where user_no = ?) order by a.modify_time desc";
			conn = DBFactory.getConn();
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, status);
			stmt.setInt(2, kmId);
			stmt.setString(3, userNo);
			rs = stmt.executeQuery();
			return ResultSetHandler.listBean(rs, RwBean.class);
		} finally {
			DBFactory.close(conn, stmt, rs);
		}
	}
	
	public static void main(String[] args) throws Exception {
		//List<RwBean> list = new RwDao().dwcrw(1, 1, "T358246462");
		//List<RwBean> list = new RwDao().ywcrw("T358246463");
		List<RwListBean> list = new RwService().dshrw(0);
		System.out.println(list);
	}
	
	public List<RwBean> ywcrw(String userNo) throws Exception{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "select distinct a.*,c.name as njName,b.status as status2 from rw_main a LEFT JOIN rw_list b on(a.id = b.rw_id) LEFT JOIN nj_dic c on(a.nj_id = c.id) where b.user_no = ? order by a.modify_time desc";
			conn = DBFactory.getConn();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, userNo);
			rs = stmt.executeQuery();
			return ResultSetHandler.listBean(rs, RwBean.class);
		} finally {
			DBFactory.close(conn, stmt, rs);
		}
	}
	
	public List<RwListBean> dshrw(int status) throws Exception{
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			RwListBean bean = new RwListBean();
			bean.setStatus(status);
			SelectSqlModal<RwListBean> sm = SelectHandler.createSelectModal(conn, bean);
			sm.addLeftJoin(RwBean.class, "id", "rwId", "parent");
			sm.addLeftJoin(TeacherInfo.class, "userNo", "userNo", "user");
			sm.addOrder(RwListBean.class, "modifyTime", SelectSqlModal.Order.DESC);
			return SelectHandler.executeSelect(conn, sm);
		} finally {
			DBFactory.close(conn, null, null);
		}
	}

}
