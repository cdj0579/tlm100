package com.unimas.tlm.dao.zs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.unimas.common.util.StringUtils;
import com.unimas.jdbc.DBFactory;
import com.unimas.jdbc.handler.ResultSetHandler;
import com.unimas.tlm.bean.user.TeacherInfo;
import com.unimas.tlm.bean.zs.UserCollections;
import com.unimas.tlm.bean.zs.XtBean;
import com.unimas.tlm.bean.zs.ZsdBean;
import com.unimas.tlm.bean.zs.ZsdContentBean;
import com.unimas.tlm.bean.zs.ZsdModified;
import com.unimas.tlm.bean.zs.ZsdSort;
import com.unimas.tlm.bean.zs.ZtContentBean;
import com.unimas.tlm.dao.JdbcDao;
import com.unimas.tlm.service.user.UserService;

public class ZsdDao extends JdbcDao<ZsdBean> {
	
	@SuppressWarnings("unchecked")
	public ZsdModified getModified(int zsdId) throws Exception {
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			ZsdModified b = new ZsdModified();
			b.setZsdId(zsdId);
			List<ZsdModified> list = (List<ZsdModified>)query(conn, b);
			if(list != null && list.size() > 0){
				return list.get(0);
			} else {
				return null;
			}
		} finally {
			DBFactory.close(conn, null, null);
		}
	}
	
	public List<?> query(String dqId, int kmId, int njId, int xq, int zjId, String userNo) throws Exception{
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBFactory.getConn();
			stmt = conn.createStatement();
			String sql = "select a.*,b.name njName,c.name kmName,d.name zjName,e.name ndName from zsd_main a "
					+ "left join nj_dic b on (a.nj_id = b.id) "
					+ "left join km_dic c on (a.km_id = c.id) "
					+ "left join zj d on (a.zj_id = d.id) "
					+ "left join nd_dic e on (a.nd_id = e.id) "
					+ "where 1=1 ";
			if(StringUtils.isNotEmpty(dqId)){
				sql += " and a.dq_id='" + dqId + "'";
			}
			if(kmId > 0){
				sql += " and a.km_id="+kmId;
			}
			if(njId > 0){
				sql += " and a.nj_id="+njId;
			}
			if(xq > 0){
				sql += " and a.xq="+xq;
			}
			if(zjId > 0){
				rs = stmt.executeQuery("select queryChildrenZjInfo("+zjId+")");
				String ids = ResultSetHandler.toString(rs);
				sql += " and a.zj_id in ("+ids+")";
			}
			if(StringUtils.isNotEmpty(userNo)){
				sql += " and (a.level = 0 or a.user_no = '" + userNo + "')";
			} else {
				sql += " and (a.level = 0 and a.user_no is null)";
			}
			rs = stmt.executeQuery(sql);
			return ResultSetHandler.listBean(rs, ZsdBean.class);
		} finally {
			DBFactory.close(conn, stmt, rs);
		}
	}
	
	public List<?> query(String dqId, int kmId, int njId, int xq, String userNo) throws Exception{
		return query(dqId, kmId, njId, xq, -1, userNo);
	}
	
	public Map<String, Object> getmodifiedInfo(int id) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBFactory.getConn();
			String sql = "SELECT a.id as modifiedId,a.zsd_id as id,a.`name`,a.nd_id as ndId,"
					+ "a.ks,a.`desc`,b.name as ndName from zsd_modified a "
					+ "LEFT JOIN nd_dic b on (a.nd_id = b.id) where a.id="+id;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			List<Map<String,Object>> list = ResultSetHandler.listMap(rs);
			return list.get(0);
		} finally {
			DBFactory.close(conn, stmt, rs);
		}
	}
	
	public List<Map<String, Object>> getContentByIds(String ids, String type) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Map<String, Object>> results = Lists.newArrayList();
		try {
			conn = DBFactory.getConn();
			if("zsd".equals(type)){
				String sql = "select * from zsd_content where id in ("+ids+")";
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				List<ZsdContentBean> list = ResultSetHandler.listBean(rs, ZsdContentBean.class);
				if(list != null){
					for(ZsdContentBean b : list){
						Map<String, Object> map = Maps.newHashMap();
						map.put("name", b.getName());
						map.put("content", b.getContent());
						results.add(map);
					}
				}
			} else if("xt".equals(type)){
				String sql = "select * from xt_main where id in ("+ids+")";
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				List<XtBean> list = ResultSetHandler.listBean(rs, XtBean.class);
				if(list != null){
					for(XtBean b : list){
						Map<String, Object> map = Maps.newHashMap();
						map.put("name", b.getName());
						map.put("content", b.getContent());
						map.put("answer", b.getAnswer());
						results.add(map);
					}
				}
			} else if("zt".equals(type)){
				String sql = "select * from zt_content where id in ("+ids+")";
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				List<ZtContentBean> list = ResultSetHandler.listBean(rs, ZtContentBean.class);
				if(list != null){
					for(ZtContentBean b : list){
						Map<String, Object> map = Maps.newHashMap();
						map.put("name", b.getName());
						map.put("content", b.getContent());
						results.add(map);
					}
				}
			}
			return results;
		} finally {
			DBFactory.close(conn, stmt, rs);
		}
	}
	
	public void saveZsdSort(int zjId, List<Map<String, Object>> zsdes, String userNo) throws Exception {
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			conn.setAutoCommit(false);
			if(zsdes != null && zsdes.size() > 0){
				ZsdSort del = new ZsdSort();
				del.setZjId(zjId);
				del.setUserNo(userNo);
				delete(conn, del);
				for(Map<String, Object> map : zsdes){
					int zsdId = (Integer)map.get("zsdId");
					int xh = (Integer)map.get("xh");
					ZsdSort b = new ZsdSort();
					b.setUserNo(userNo);
					b.setZsdId(zsdId);
					b.setXh(xh);
					b.setZjId(zjId);
					save(conn, b);
				}
			}
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
	
	public void collect(int id, String type, String userNo) throws Exception {
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			conn.setAutoCommit(false);
			int yyfs = -1;
			String sUserNo = null;
			if("zsd".equals(type)){
				ZsdContentBean g = new ZsdContentBean();
				g.setId(id);
				ZsdContentBean b = (ZsdContentBean)getById(conn, g);
				yyfs = b.getYyfs();
				sUserNo = b.getUserNo();
				ZsdContentBean s = new ZsdContentBean();
				s.setId(b.getId());
				s.setLjjf(yyfs+b.getLjjf());
				save(conn, s);
			} else if("xt".equals(type)){
				XtBean g = new XtBean();
				g.setId(id);
				XtBean b = (XtBean)getById(conn, g);
				yyfs = b.getYyfs();
				sUserNo = b.getUserNo();
				XtBean s = new XtBean();
				s.setId(b.getId());
				s.setLjjf(yyfs+b.getLjjf());
				save(conn, s);
			}
			if(yyfs > 0){
				UserCollections b = new UserCollections();
				b.setCid(id);
				b.setType(type);
				b.setUserNo(userNo);
				b.setJf(yyfs);
				collect(conn, b, sUserNo);
			}
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
	
	public static void collect(Connection conn, UserCollections uc, String sUserNo) throws Exception{
		int yyfs = uc.getJf();
		String userNo = uc.getUserNo();
		UserCollections exsits = new UserCollections();
		exsits.setCid(uc.getCid());
		exsits.setType(uc.getType());
		exsits.setUserNo(userNo);
		JdbcDao<ZsdBean> dao = new JdbcDao<ZsdBean>();
		UserCollections tmp = (UserCollections)dao.getById(conn, exsits);
		if(tmp != null){
			throw new Exception("知识已收藏！");
		}
		UserService us = new UserService();
		TeacherInfo dTeacher = us.getTeacherByUserNo(conn, userNo);
		int syfs = dTeacher.getJf()-yyfs;
		if(syfs < 0){
			throw new Exception("积分不够！");
		}
		TeacherInfo jfBean = new TeacherInfo();
		jfBean.setId(dTeacher.getId());
		jfBean.setJf(syfs);
		dao.save(conn, jfBean);
		TeacherInfo sTeacher = us.getTeacherByUserNo(conn, sUserNo);
		jfBean = new TeacherInfo();
		jfBean.setId(sTeacher.getId());
		jfBean.setJf(sTeacher.getJf()+yyfs);
		dao.save(conn, jfBean);
		dao.save(conn, uc);
	}
	
	public List<ZsdBean> getZsdByZj(int bbId, String dqId, int kmId, int njId, int xq, String onUserNo) throws Exception{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBFactory.getConn();
			String sql = "select a.id,a.nj_id,a.xq,a.dq_id,a.km_id,a.zj_id,a.`level`,a.user_no,"
					+"ifnull(b.id, -1) as modified_id,"
					+"ifnull(b.name, a.name) as name,"
					+"ifnull(b.nd_id, a.nd_id) as nd_id,"
					+"ifnull(b.ks, a.ks) as ks,"
					+"ifnull(b.`desc`, a.`desc`) as `desc`"
					+ " from zsd_main a LEFT JOIN zsd_modified b on (b.zsd_id = a.id and a.user_no='"+onUserNo+"') "
					+"LEFT JOIN zsd_sort c on (c.zsd_id = a.id and c.user_no='"+onUserNo+"') "
					+ "where a.zj_id in (" + 
							"select id from zj where bb_id=? and dq_id=? and km_id=? and nj_id=? and xq=?" +
							") and (a.level = 0 or a.user_no='"+onUserNo+"')"
					+ " ORDER BY c.xh";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, bbId);
			stmt.setString(2, dqId);
			stmt.setInt(3, kmId);
			stmt.setInt(4, njId);
			stmt.setInt(5, xq);
			rs = stmt.executeQuery();
			return ResultSetHandler.listBean(rs, ZsdBean.class);
		} finally {
			DBFactory.close(conn, stmt, rs);
		}
	}
	
	public List<Map<String, Object>> getZstxUsers() throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBFactory.getConn();
			stmt = conn.createStatement();
			StringBuffer sql = new StringBuffer();
			sql.append( "select a.*,b.name,b.jf,b.skdz from ( ");
			sql.append( 	"select user_no, max(modify_time) as modify_time from ( ");
			sql.append( 		"select user_no,modify_time from zsd_main where user_no is not null ");
			sql.append( 		"UNION ");
			sql.append( 		"select b.user_no,a.modify_time from zsd_modified a LEFT JOIN zsd_main b on (a.zsd_id = b.id) ");
			sql.append( 		"UNION ");
			sql.append( 		"select user_no,modify_time from zsd_sort ");
			sql.append( 	") a GROUP BY user_no ");
			sql.append( ") a LEFT JOIN teacher_info b on (a.user_no = b.user_no) ");
			//sql.append( "where b.name like '%胡%' ");
			sql.append( "ORDER by modify_time desc ");
			rs = stmt.executeQuery(sql.toString());
			return ResultSetHandler.listMap(rs, "modify_time");
		} finally {
			DBFactory.close(conn, stmt, rs);
		}
	}
	
	public List<Map<String, Object>> searchZsdContents(String type, int id, String userNo, String stype, boolean loadAll) throws Exception{
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBFactory.getConn();
			stmt = conn.createStatement();
			StringBuffer sql = new StringBuffer();
			if("zj".equals(type)){
				rs = stmt.executeQuery("select queryChildrenZjInfo("+id+")");
				String ids = ResultSetHandler.toString(rs);
				DBFactory.close(null, null, rs);
				sql.append( "select a.id,a.name,a.is_original isOriginal,a.is_share isShare,a.user_no userNo,a.yyfs,a.type type,a.ljjf,if(b.user_no is null, 'no', 'yes') collect from ( ");
				List<String> sqls = Lists.newArrayList();
				if(stype == null || "zsdContent".equals(stype)){
					StringBuffer sql1 = new StringBuffer();
					sql1.append(		"select a.id,a.name,a.is_original,a.is_share,a.user_no,a.yyfs,a.ljjf,'zsd' as type from zsd_content a where a.pid in ( ");
					sql1.append(			"select id from zsd_main where zj_id in ("+ids+") ");
					sql1.append(		") and (a.is_share=1 or a.user_no='"+userNo+"') ");
					sqls.add(sql1.toString());
				}
				if(stype == null || "xt".equals(stype)){
					StringBuffer sql1 = new StringBuffer();
					sql1.append(		"select a.id,a.name,a.is_original,a.is_share,a.user_no,a.yyfs,a.ljjf,'xt' as type from xt_main a where a.id in ( ");
					sql1.append(			"select xt_id from xt_zsd_ref where zsd_id in ( ");
					sql1.append(				"select id from zsd_main where zj_id in ("+ids+") ");
					sql1.append(			")");
					sql1.append(		") and (a.is_share=1 or a.user_no='"+userNo+"') ");
					sqls.add(sql1.toString());
				}
				if(sqls.size() > 1){
					sql.append(Joiner.on(" union ").join(sqls));
				} else {
					sql.append(sqls.get(0));
				}
				sql.append(	") a ");
			} else {
				sql.append(	"select a.id,a.name,a.is_original isOriginal,a.is_share isShare,a.user_no userNo,a.yyfs,a.type type,a.ljjf,if(b.user_no is null, 'no', 'yes') collect from ( ");
				List<String> sqls = Lists.newArrayList();
				if(stype == null || "zsdContent".equals(stype)){
					StringBuffer sql1 = new StringBuffer();
					sql1.append(		"select a.id,a.name,a.is_original,a.is_share,a.user_no,a.yyfs,a.ljjf,'zsd' as type from zsd_content a where a.pid="+id+" and (a.is_share=1 or a.user_no='"+userNo+"') ");
					sqls.add(sql1.toString());
				}
				if(stype == null || "xt".equals(stype)){
					StringBuffer sql1 = new StringBuffer();
					sql1.append(		"select a.id,a.name,a.is_original,a.is_share,a.user_no,a.yyfs,a.ljjf,'xt' as type from xt_main a where a.id in ( ");
					sql1.append(			"select xt_id from xt_zsd_ref where zsd_id="+id+" ");
					sql1.append(		") and (a.is_share=1 or a.user_no='"+userNo+"') ");
					sqls.add(sql1.toString());
				}
				if(sqls.size() > 1){
					sql.append(Joiner.on(" union ").join(sqls));
				} else {
					sql.append(sqls.get(0));
				}
				sql.append(	") a ");
			}
			sql.append(	"LEFT JOIN user_collections b on (a.id = b.cid and a.type = b.type and b.user_no='"+userNo+"') ");
			sql.append(	"where 1=1 ");
			if(!loadAll){ //过滤掉未收藏的内容
				sql.append(	" and (b.user_no is not null or a.user_no='"+userNo+"') ");
			}
			sql.append(	"ORDER BY a.ljjf desc");
			rs = stmt.executeQuery(sql.toString());
			return ResultSetHandler.listMap(rs);
		} finally {
			DBFactory.close(conn, stmt, rs);
		}
	}

}
