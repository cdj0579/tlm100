package com.unimas.txl.dao.fenpei;

import java.sql.Connection;
import java.util.List;

import com.unimas.jdbc.DBFactory;
import com.unimas.jdbc.handler.UpdateInsertHandler;
import com.unimas.txl.bean.fenpei.FenpeiBean;
import com.unimas.txl.bean.fenpei.FenpeiSyzBean;
import com.unimas.txl.dao.JdbcDao;

public class FpgzDao extends JdbcDao<FenpeiBean> {
	
	public void save(FenpeiBean bean) throws Exception {
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			conn.setAutoCommit(false);
			bean = (FenpeiBean)save(conn, bean);
			FenpeiSyzBean clearBean = new FenpeiSyzBean();
			int id = bean.getId();
			clearBean.setGuizeId(id);
			UpdateInsertHandler.executeClear(conn, clearBean);
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
			UpdateInsertHandler.executeClear(conn, clearBean);
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

}
