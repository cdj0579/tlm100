package com.unimas.tlm.dao.zs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.unimas.jdbc.DBFactory;
import com.unimas.jdbc.handler.ResultSetHandler;
import com.unimas.jdbc.handler.UpdateInsertHandler;
import com.unimas.tlm.bean.zs.XtBean;
import com.unimas.tlm.bean.zs.XtZsdRef;
import com.unimas.tlm.bean.zs.ZsdBean;
import com.unimas.tlm.dao.JdbcDao;

public class XtDao extends JdbcDao<XtBean> {
	
	public XtBean save(XtBean bean) throws Exception{
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			conn.setAutoCommit(false);
			int id = bean.getId();
			if(id > 0){
				XtZsdRef ref = new XtZsdRef();
				ref.setXtId(id);
				UpdateInsertHandler.executeDelete(conn, ref);
			}
			XtBean b = (XtBean)super.save(conn, bean);
			id = bean.getId();
			List<ZsdBean> zsds = bean.getZsds();
			if(zsds != null){
				for(ZsdBean zsd : zsds){
					int zsdId = zsd.getId();
					if(zsdId > 0){
						XtZsdRef ref = new XtZsdRef();
						ref.setXtId(id);
						ref.setZsdId(zsdId);
						UpdateInsertHandler.executeInsert(conn, ref);
					}
				}
			}
			conn.commit();
			return b;
		} catch(Exception e){
			try {
				if(conn != null){
					conn.rollback();
				}
			} catch(Exception e1){}
			throw e;
		} finally {
			DBFactory.close(conn, null, null);
		}
	}
	
	public List<XtBean> query(String dqId, int kmId, int njId, int xq, String userNo) throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBFactory.getConn();
			StringBuffer sql = new StringBuffer();
			sql.append("select a.*,b.name ztName,c.name ndName from xt_main a ");
			sql.append("left join zt_main b on (a.zt_id = b.id) ");
			sql.append("left join nd_dic c on (a.nd_id = c.id) ");
			sql.append("where a.id in (");
			sql.append(	"select distinct xt_id from xt_zsd_ref where zsd_id in(");
			sql.append(		"select id from zsd_main where dq_id=? and km_id=? and nj_id=? and xq=?");
			sql.append(	")");
			sql.append(")");
			if(userNo != null){
				sql.append(" and a.user_no=?");
			}
			stmt = conn.prepareStatement(sql.toString());
			stmt.setString(1, dqId);
			stmt.setInt(2, kmId);
			stmt.setInt(3, njId);
			stmt.setInt(4, xq);
			if(userNo != null){
				stmt.setString(5, userNo);
			}
			rs = stmt.executeQuery();
			return ResultSetHandler.listBean(rs, XtBean.class);
		} finally {
			DBFactory.close(conn, stmt, rs);
		}
	}
	
	public List<XtBean> query(List<Integer> zsdIds) throws Exception {
		if(zsdIds == null || zsdIds.isEmpty()) return null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBFactory.getConn();
			stmt = conn.createStatement();
			String sql = "select * from xt_zsd_ref where zsd_id in ("+Joiner.on(",").join(zsdIds)+")";
			rs = stmt.executeQuery(sql);
			List<XtZsdRef> refs = ResultSetHandler.listBean(rs, XtZsdRef.class);
			List<Integer> xtIds = Lists.newArrayList();
			if(refs == null || refs.isEmpty()){
				return null;
			}
			for(XtZsdRef ref : refs){
				xtIds.add(ref.getXtId());
			}
			sql = "select * from xt_main where id in ("+Joiner.on(",").join(xtIds)+")";
			rs = stmt.executeQuery(sql);
			List<XtBean> xtes = ResultSetHandler.listBean(rs, XtBean.class);
			for(XtBean xt : xtes){
				List<XtZsdRef> tmpRef = Lists.newArrayList();
				for(int i=0;i<refs.size();i++){
					XtZsdRef ref = refs.get(i);
					if(xt.getId() == ref.getXtId()){
						tmpRef.add(ref);
						ref.setXt(xt);
						refs.remove(i--);
					}
				}
				xt.setRefs(tmpRef);
			}
			return xtes;
		} finally {
			DBFactory.close(conn, stmt, rs);
		}
	}
	
	public void delete(int id) throws Exception{
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			conn.setAutoCommit(false);
			super.delete(conn, id, XtBean.class);
			XtZsdRef ref = new XtZsdRef();
			ref.setXtId(id);
			UpdateInsertHandler.executeDelete(conn, ref);
			conn.commit();
		} catch(Exception e){
			try {
				if(conn != null){
					conn.rollback();
				}
			} catch(Exception e1){}
			throw e;
		} finally {
			DBFactory.close(conn, null, null);
		}
	}

}
