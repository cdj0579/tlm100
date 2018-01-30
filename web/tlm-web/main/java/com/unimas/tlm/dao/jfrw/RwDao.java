package com.unimas.tlm.dao.jfrw;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import com.unimas.jdbc.DBFactory;
import com.unimas.jdbc.handler.ResultSetHandler;
import com.unimas.tlm.bean.jfrw.RwListBean;
import com.unimas.tlm.bean.jfrw.RwMainBean;
import com.unimas.tlm.bean.jfrw.UserFulfilRwBean;
import com.unimas.tlm.dao.JdbcDao;
import com.unimas.tlm.service.jfrw.RwService;

public class RwDao extends JdbcDao<RwMainBean> {
	
	public List<RwMainBean> search(int status) throws Exception{
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String sql =    "SELECT a.*,ifnull(b.count, 0) rw_count FROM rw_main a "+
							"LEFT JOIN ("+
								"select count(*) count,pid from rw_list GROUP BY pid"+
							") b on (a.id = b.pid)"+
							"where 1=1";
			if(status >= 0){
				sql += " and a.status="+status;
			}
			sql += " ORDER BY a.modify_time desc,a.status asc";
			conn = DBFactory.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			return ResultSetHandler.listBean(rs, RwMainBean.class);
		} finally {
			DBFactory.close(conn, stmt, rs);
		}
	}

	public RwMainBean getFullBeanById(Connection conn, int id) throws Exception {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String sql =    "SELECT a.*,ifnull(b.count, 0) rw_count FROM rw_main a "+
							"LEFT JOIN ("+
								"select count(*) count,pid from rw_list where pid="+id+
							") b on (a.id = b.pid)"+
							"where id="+id;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			return ResultSetHandler.bean(rs, RwMainBean.class);
		} finally {
			DBFactory.close(null, stmt, rs);
		}
	}
	
	public List<RwListBean> dlqrw(int status, int kmId, String userNo) throws Exception{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select ");
			sql.append("	a.*,ifnull(d.count, 0) lqcs,b.max_num,b.name,b.desc,c.km_id,c.type stype,c.name sname ");
			sql.append("from rw_list a ");
			sql.append("LEFT JOIN rw_main b on(a.pid = b.id) ");
			sql.append("LEFT JOIN (");
			sql.append("	select id,'zsd' type,km_id,name from zsd_main ");
			sql.append("  UNION ");
			sql.append("  select id,'zt' type,km_id,name from zt_main ");
			sql.append(") c on (a.source_id = c.id and if(a.type=2, 'zt', 'zsd') = c.type) ");
			sql.append("LEFT JOIN (");
			sql.append("select count(*) count,rw_id from rw_user_list where status in (0, 1, 2) GROUP BY rw_id ");
			sql.append(") d on (a.id = d.rw_id)");
			sql.append("where 1=1 ");
			sql.append("and b.status=? ");
			sql.append("and b.max_num > ifnull(d.count, 0) ");
			sql.append("and c.km_id=? ");
			sql.append("and a.id not in(select rw_id from rw_user_list where user_no=? and status in (0, 1)) ");
			sql.append("order by a.modify_time desc ");
			conn = DBFactory.getConn();
			stmt = conn.prepareStatement(sql.toString());
			stmt.setInt(1, status);
			stmt.setInt(2, kmId);
			stmt.setString(3, userNo);
			rs = stmt.executeQuery();
			return ResultSetHandler.listBean(rs, RwListBean.class);
		} finally {
			DBFactory.close(conn, stmt, rs);
		}
	}
	
	public static void main(String[] args) throws Exception {
		//List<RwBean> list = new RwDao().dwcrw(1, 1, "T358246462");
		//List<RwBean> list = new RwDao().ywcrw("T358246463");
		List<UserFulfilRwBean> list = new RwService().dshrw(0);
		System.out.println(list);
	}
	
	public List<UserFulfilRwBean> dwcrw(String userNo) throws Exception{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "select a.*,c.name,c.desc,b.type,b.source_id,d.name source_name from rw_user_list a LEFT JOIN rw_list b on(b.id = a.rw_id) LEFT JOIN rw_main c on(c.id = b.pid) LEFT JOIN ( select id,'zsd' type,name from zsd_main UNION select id,'zt' type,name from zt_main ) d on (b.source_id = d.id and if(b.type=2, 'zt', 'zsd') = d.type) where a.user_no=? order by a.modify_time desc";
			conn = DBFactory.getConn();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, userNo);
			rs = stmt.executeQuery();
			return ResultSetHandler.listBean(rs, UserFulfilRwBean.class);
		} finally {
			DBFactory.close(conn, stmt, rs);
		}
	}
	
	/**
	 * 获取超时未完成任务
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public List<UserFulfilRwBean> cswwcRw(Connection conn, long timeout) throws Exception{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT * FROM rw_user_list where status=0 and TIMESTAMPDIFF(SECOND, insert_time, NOW()) > ?;";
			stmt = conn.prepareStatement(sql);
			stmt.setLong(1, timeout);
			rs = stmt.executeQuery();
			return ResultSetHandler.listBean(rs, UserFulfilRwBean.class);
		} finally {
			DBFactory.close(null, stmt, rs);
		}
	}
	
	public List<UserFulfilRwBean> dshrw(int status) throws Exception{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select ");
			sql.append("	a.*,c.name,c.desc,b.type,d.name source_name,e.name user_name,f.name content_name ");
			sql.append("from rw_user_list a ");
			sql.append("LEFT JOIN rw_list b on(b.id = a.rw_id) ");
			sql.append("LEFT JOIN rw_main c on(c.id = b.pid) ");
			sql.append("LEFT JOIN (");
			sql.append("	select id,'zsd' type,km_id,name from zsd_main ");
			sql.append("	UNION ");
			sql.append("	select id,'zt' type,km_id,name from zt_main ");
			sql.append(") d on (b.source_id = d.id and if(b.type=2, 'zt', 'zsd') = d.type) ");
			sql.append("LEFT JOIN teacher_info e on (a.user_no = e.user_no) ");
			sql.append("LEFT JOIN (");
			sql.append("	select id,0 type,name from xt_main ");
			sql.append("	UNION ");
			sql.append("  	select id,1 type,name from zsd_content ");
			sql.append("  	UNION ");
			sql.append("  	select id,2 type,name from zt_content ");
			sql.append(") f on (a.content_id = f.id and b.type = f.type)");
			sql.append("where a.status in (1, 2, 3)");
			if(status >= 0){
				sql.append(" and a.status=?");
			}
			sql.append(" order by a.modify_time desc");
			conn = DBFactory.getConn();
			stmt = conn.prepareStatement(sql.toString());
			if(status >= 0){
				stmt.setInt(1, status);
			}
			rs = stmt.executeQuery();
			return ResultSetHandler.listBean(rs, UserFulfilRwBean.class);
		} finally {
			DBFactory.close(conn, stmt, rs);
		}
	}

}
