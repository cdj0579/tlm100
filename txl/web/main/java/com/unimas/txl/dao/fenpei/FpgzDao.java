package com.unimas.txl.dao.fenpei;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import com.unimas.common.date.TimeUtils;
import com.unimas.jdbc.DBFactory;
import com.unimas.jdbc.handler.ResultSetHandler;
import com.unimas.jdbc.handler.SelectHandler;
import com.unimas.jdbc.handler.entry.SelectSqlModal;
import com.unimas.txl.bean.fenpei.FenpeiBean;
import com.unimas.txl.bean.fenpei.FenpeiSyzBean;
import com.unimas.txl.dao.JdbcDao;

public class FpgzDao extends JdbcDao<FenpeiBean> {
	
	public List<FenpeiBean> query(FenpeiBean b) throws Exception{
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			SelectSqlModal<FenpeiBean> sm = SelectHandler.createSelectModal(conn, b);
			sm.addOrder(FenpeiBean.class, "shijian", SelectSqlModal.Order.ASC);
			return SelectHandler.executeSelect(conn, sm);
		} finally {
			DBFactory.close(conn, null, null);
		}
	}
	
	public void save(FenpeiBean bean) throws Exception {
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			conn.setAutoCommit(false);
			bean = (FenpeiBean)save(conn, bean);
			FenpeiSyzBean clearBean = new FenpeiSyzBean();
			int id = bean.getId();
			clearBean.setGuizeId(id);
			delete(conn, clearBean);
			List<FenpeiSyzBean> refs = bean.getRefs();
			if(refs != null){
				for(FenpeiSyzBean fb : refs){
					fb.setGuizeId(id);
					save(conn, fb);
				}
			}
			conn.commit();
		} catch(Exception e) {
			if(conn != null){
				try { conn.rollback(); } catch(Exception e1){}
			}
			throw e;
		} finally {
			DBFactory.close(conn, null, null);
		}
	}
	
	public void delete(int id) throws Exception {
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			conn.setAutoCommit(false);
			FenpeiSyzBean clearBean = new FenpeiSyzBean();
			clearBean.setGuizeId(id);
			delete(conn, clearBean);
			delete(conn, id, FenpeiBean.class);
			conn.commit();
		} catch(Exception e) {
			if(conn != null){
				try { conn.rollback(); } catch(Exception e1){}
			}
			throw e;
		} finally {
			DBFactory.close(conn, null, null);
		}
	}
	
	public void clearLxrGuanzhu(int jigouId) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DBFactory.getConn();
			int gzsc = -1;
			ResultSet rs = null;
			try {
				String sql = "select guanzhu_shichang from txl_config where jigou_id="+jigouId;
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				gzsc = ResultSetHandler.toInt(rs);
				if(gzsc == -1){
					gzsc = 3;
				}
			} finally {
				DBFactory.close(null, stmt, rs);
			}
			String sql = "delete from txl_lianxiren_guanzhu where jigou_id="+jigouId+" and shijian<='"+TimeUtils.format(TimeUtils.add(new Date(), -1*gzsc))+"'";
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} finally {
			DBFactory.close(conn, stmt, null);
		}
	}

}
