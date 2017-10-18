package com.unimas.tlm.service.jagl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.unimas.common.util.StringUtils;
import com.unimas.jdbc.DBFactory;
import com.unimas.jdbc.handler.IHandler;
import com.unimas.jdbc.handler.ResultSetHandler;
import com.unimas.tlm.bean.ja.JaBean;
import com.unimas.tlm.bean.ja.JaDirs;
import com.unimas.tlm.bean.ja.JaTemplete;
import com.unimas.tlm.bean.zs.UserCollections;
import com.unimas.tlm.dao.JdbcDao;
import com.unimas.tlm.dao.zs.ZsdDao;
import com.unimas.web.auth.AuthRealm.ShiroUser;

@Service
public class JaglService {
	
	private static final int MAX_CONUT = 100;
	
	public List<JaBean> getJaList(int dirId, ShiroUser user) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBFactory.getConn();
			stmt = conn.createStatement();
			String userNo = user.getUserNo();
			StringBuffer sql = new StringBuffer();
			sql.append("select a.id,a.name,a.modify_time,a.insert_time,if(a.user_no='"+userNo+"', 0, 1) as isCollected ");
			sql.append("from ja_list a left join user_collections b on (b.user_no='"+userNo+"' and b.type='ja' and b.cid = a.id) where (a.user_no='"+userNo+"' ");
			if(dirId > 0){
				sql.append("and a.dir_id="+dirId+"");
			}
			sql.append(") or (b.id is not null ");
			if(dirId > 0){
				sql.append("and b.bz_id1="+dirId+"");
			}
			sql.append(")");
			rs = stmt.executeQuery(sql.toString());
			return ResultSetHandler.custom(rs, new IHandler<List<JaBean>>() {
				@Override
				public List<JaBean> handler(ResultSet rs) throws Exception {
					List<JaBean> list = null;
					if(rs != null){
						list = Lists.newArrayList();
						ResultSetMetaData md = rs.getMetaData();
						while (rs.next()){
							JaBean bean = ResultSetHandler.rsToBean(rs, JaBean.class, md);
							int isCollected = rs.getInt("isCollected");
							bean.setCollected(isCollected==1);
							list.add(bean);
						}
					}
					return list;
				}
			});
		} finally {
			DBFactory.close(conn, stmt, rs);
		}
	}
	
	public List<JaBean> getOtherUserJaList(String name, String userName, ShiroUser user) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBFactory.getConn();
			stmt = conn.createStatement();
			String userNo = user.getUserNo();
			StringBuffer sql = new StringBuffer();
			sql.append("select a.id,a.name,a.yyfs,a.user_no,a.modify_time,a.insert_time,if(a.id in (");
			sql.append(" select cid from user_collections where user_no = '"+userNo+"' and type='ja'");
			sql.append("), 1, 0) as isCollected,b.name as userName ");
			sql.append("from ja_list a left join teacher_info b on (a.user_no = b.user_no)  where a.user_no <> '"+userNo+"' and a.is_share=1 ");
			if(StringUtils.isNotEmpty(name)){
				sql.append("and a.name like '%"+name+"%' ");
			}
			if(StringUtils.isNotEmpty(userName)){
				sql.append("and b.name like '%"+userName+"%'");
			}
			rs = stmt.executeQuery(sql.toString());
			return ResultSetHandler.custom(rs, new IHandler<List<JaBean>>() {
				@Override
				public List<JaBean> handler(ResultSet rs) throws Exception {
					List<JaBean> list = null;
					if(rs != null){
						list = Lists.newArrayList();
						ResultSetMetaData md = rs.getMetaData();
						while (rs.next()){
							JaBean bean = ResultSetHandler.rsToBean(rs, JaBean.class, md);
							int isCollected = rs.getInt("isCollected");
							bean.setCollected(isCollected==1);
							bean.setUserName(rs.getString("userName"));
							list.add(bean);
						}
					}
					return list;
				}
			});
		} finally {
			DBFactory.close(conn, stmt, rs);
		}
	}
	
	public void collectJa(List<Map<String, Object>> list, int dirId, ShiroUser user) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBFactory.getConn();
			conn.setAutoCommit(false);
			
			for(Map<String, Object> map : list){
				int id = (int)map.get("id");
				int jf = (int)map.get("yyfs");
				String userNo = (String)map.get("userNo");
				UserCollections uc = new UserCollections();
				uc.setBzId1(dirId);
				uc.setCid(id);
				uc.setUserNo(user.getUserNo());
				uc.setJf(jf);
				uc.setType("ja");
				ZsdDao.collect(conn, uc, userNo);
			}
			
			conn.commit();
		} catch(Exception e){
			try{
				if(conn != null){
					conn.rollback();
				}
			} catch(Exception e1){}
			throw e;
		} finally {
			DBFactory.close(conn, stmt, rs);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<JaDirs> getDirList(int type, ShiroUser user) throws Exception {
		JaDirs dir = new JaDirs();
		dir.setType(type);
		dir.setUserNo(user.getUserNo());
		List<JaDirs> list = (List<JaDirs>)new JdbcDao<JaDirs>().query(dir);
		return list;
	}
	
	public JaDirs saveDir(int id, int pid, int type, String name, ShiroUser user) throws Exception {
		JaDirs dir = new JaDirs();
		dir.setId(id);
		dir.setPid(pid);
		dir.setType(type);
		dir.setUserNo(user.getUserNo());
		dir.setName(name);
		return (JaDirs)new JdbcDao<JaDirs>().save(dir);
	}
	
	public void deleteDir(int id) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBFactory.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select queryChildrenJaDirsInfo("+id+")");
			String ids = ResultSetHandler.toString(rs);
			stmt.execute("delete from ja_dirs where id in ("+ids+")");
		} finally {
			DBFactory.close(conn, stmt, rs);
		}
	}
	
	public JaBean getJaInfoById(int id) throws Exception {
		return (JaBean)new JdbcDao<JaBean>().getById(id, JaBean.class);
	}
	
	public void saveJa(int id, int dirId, String name, String content, int isOriginal, int yyfs, int isShare, ShiroUser user) throws Exception{
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			JaBean bean = new JaBean();
			JdbcDao<JaBean> dao = new JdbcDao<JaBean>();
			if(id > 0){
				bean.setId(id);
			} else {
				bean.setUserNo(user.getUserNo());
				List<?> list = dao.query(conn, bean);
				if(list.size() >= MAX_CONUT){
					throw new Exception("最多只能保存"+MAX_CONUT+"个教案！");
				}
			}
			bean.setDirId(dirId);
			bean.setName(name);
			bean.setContent(content.getBytes("utf-8"));
			bean.setIsOriginal(isOriginal);
			if(isOriginal == 1){
				bean.setYyfs(yyfs);
			} else {
				bean.setYyfs(1);
			}
			bean.setIsShare(isShare);
			dao.save(conn, bean);
		} finally {
			DBFactory.close(conn, null, null);
		}
	}
	
	public void saveJaTemplete(int id, int dirId, String name, String content, int isOriginal, int yyfs, int isShare, ShiroUser user) throws Exception{
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			JaTemplete bean = new JaTemplete();
			JdbcDao<JaTemplete> dao = new JdbcDao<JaTemplete>();
			if(id > 0){
				bean.setId(id);
			} else {
				bean.setUserNo(user.getUserNo());
				List<?> list = dao.query(conn, bean);
				if(list.size() >= MAX_CONUT){
					throw new Exception("最多只能保存"+MAX_CONUT+"个教案模板！");
				}
			}
			bean.setDirId(dirId);
			bean.setName(name);
			bean.setContent(content.getBytes("utf-8"));
			bean.setIsOriginal(isOriginal);
			if(isOriginal == 1){
				bean.setYyfs(yyfs);
			} else {
				bean.setYyfs(1);
			}
			bean.setIsShare(isShare);
			dao.save(conn, bean);
		} finally {
			DBFactory.close(conn, null, null);
		}
	}
	
	public void deleteJa(int id) throws Exception {
		new JdbcDao<JaBean>().delete(id, JaBean.class);
	}
	
	public void deleteJaTemplete(int id) throws Exception {
		new JdbcDao<JaTemplete>().delete(id, JaTemplete.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<JaTemplete> getJaTempletes(int dirId, ShiroUser user) throws Exception {
		JaTemplete bean = new JaTemplete();
		bean.setUserNo(user.getUserNo());
		if(dirId > 0){
			bean.setDirId(dirId);
		}
		List<JaTemplete> list = (List<JaTemplete>)new JdbcDao<JaTemplete>().query(bean);
		return list;
	}
	
	public JaTemplete getJaTempleteInfoById(int id) throws Exception {
		return (JaTemplete)new JdbcDao<JaTemplete>().getById(id, JaTemplete.class);
	}

}
