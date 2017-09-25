package com.unimas.tlm.dao.base;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.unimas.jdbc.DBFactory;
import com.unimas.tlm.bean.base.MbxxBean;
import com.unimas.tlm.bean.base.MbxxmbfBean;
import com.unimas.tlm.dao.JdbcDao;

public class MbxxDao extends JdbcDao<MbxxBean> {
	
	@SuppressWarnings("unchecked")
	public MbxxBean getMbxx(int id) throws Exception{
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			MbxxBean mbxx = (MbxxBean)getById(id, MbxxBean.class);
			MbxxmbfBean mbf = new MbxxmbfBean();
			mbf.setMbxxId(id);
			List<MbxxmbfBean> mbfList = (List<MbxxmbfBean>)query(conn, mbf);
			mbxx.setMbfList(mbfList);
			return mbxx;
		} finally {
			DBFactory.close(conn, null, null);
		}
	}
	
	public void deleteMbxx(int id) throws Exception{
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			conn.setAutoCommit(false);
			MbxxBean mbxx = new MbxxBean();
			mbxx.setId(id);
			delete(conn, mbxx);
			MbxxmbfBean mbf = new MbxxmbfBean();
			mbf.setMbxxId(id);
			delete(conn, mbf);
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
	
	public void saveMbxx(int id, String dqId, String name, String desc, List<Map<String, Object>> mbfList) throws Exception{
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			conn.setAutoCommit(false);
			MbxxBean mbxx = new MbxxBean();
			mbxx.setId(id);
			mbxx.setName(name);
			mbxx.setDqId(dqId);
			mbxx.setDesc(desc);
			save(conn, mbxx);
			for(Map<String, Object> map : mbfList) {
				MbxxmbfBean mbf = new MbxxmbfBean();
				mbf.setId((int)map.get("id"));
				mbf.setKmId((int)map.get("kmId"));
				mbf.setMbfs((int)map.get("mbfs"));
				mbf.setMf((int)map.get("mf"));
				mbf.setMbxxId(mbxx.getId());
				save(conn, mbf);
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
