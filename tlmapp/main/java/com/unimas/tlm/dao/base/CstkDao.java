package com.unimas.tlm.dao.base;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.unimas.jdbc.DBFactory;
import com.unimas.tlm.bean.base.CstBean;
import com.unimas.tlm.bean.base.CstOptionBean;
import com.unimas.tlm.dao.JdbcDao;

public class CstkDao extends JdbcDao<CstBean> {
	
	@SuppressWarnings("unchecked")
	public CstBean getCst(int id) throws Exception{
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			CstBean cst = (CstBean)getById(id, CstBean.class);
			CstOptionBean option = new CstOptionBean();
			option.setPid(id);
			List<CstOptionBean> options = (List<CstOptionBean>)query(conn, option);
			cst.setOptions(options);
			return cst;
		} finally {
			DBFactory.close(conn, null, null);
		}
	}
	
	public void deleteCst(int id) throws Exception{
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			conn.setAutoCommit(false);
			CstBean cst = new CstBean();
			cst.setId(id);
			delete(conn, cst);
			CstOptionBean option = new CstOptionBean();
			option.setPid(id);
			delete(conn, option);
			conn.commit();
		} catch(Exception e){
			if(conn != null){
				try{ conn.rollback();} catch(Exception e1){}
			}
			throw e;
		} finally {
			DBFactory.close(conn, null, null);
		}
	}
	
	public void saveCst(int id, int kmId, int njId, String name, String answer, List<Map<String, Object>> options) throws Exception{
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			conn.setAutoCommit(false);
			CstBean cst = new CstBean();
			cst.setId(id);
			cst.setKmId(kmId);
			cst.setNjId(njId);
			cst.setName(name);
			cst.setAnswer(answer);
			save(conn, cst);
			if(id > 0){
				CstOptionBean option = new CstOptionBean();
				option.setPid(id);
				delete(conn, option);
			}
			for(Map<String, Object> map : options) {
				CstOptionBean option = new CstOptionBean();
				option.setName((String)map.get("name"));
				option.setOption((String)map.get("option"));
				option.setPid(cst.getId());
				save(conn, option);
			}
			conn.commit();
		} catch(Exception e){
			if(conn != null){
				try{ conn.rollback();} catch(Exception e1){}
			}
			throw e;
		} finally {
			DBFactory.close(conn, null, null);
		}
	}

}
