package com.unimas.tlm.service.zs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.unimas.common.util.StringUtils;
import com.unimas.jdbc.DBFactory;
import com.unimas.jdbc.handler.IHandler;
import com.unimas.jdbc.handler.ResultSetHandler;
import com.unimas.tlm.bean.zs.UserCollections;
import com.unimas.tlm.bean.zs.XtBean;
import com.unimas.tlm.bean.zs.XtZsdRef;
import com.unimas.tlm.bean.zs.ZsdBean;
import com.unimas.tlm.bean.zs.ZsdContentBean;
import com.unimas.tlm.bean.zs.ZsdModified;
import com.unimas.tlm.bean.zs.ZtBean;
import com.unimas.tlm.bean.zs.ZtContentBean;
import com.unimas.tlm.dao.JdbcDao;
import com.unimas.tlm.dao.zs.XtDao;
import com.unimas.tlm.dao.zs.ZsdDao;
import com.unimas.tlm.dao.zs.ZtDao;
import com.unimas.web.auth.AuthRealm.ShiroUser;

@Service
public class ZsService {
	
	public void saveZsd(int id, String dqId, int kmId, int njId, int xq, int zjId, String name, int ks, int ndId, String desc, ShiroUser user) throws Exception{
		ZsdBean zsd = new ZsdBean();
		zsd.setId(id);
		zsd.setKmId(kmId);
		zsd.setDqId(dqId);
		zsd.setNjId(njId);
		zsd.setXq(xq);
		zsd.setZjId(zjId);
		zsd.setName(name);
		zsd.setKs(ks);
		zsd.setNdId(ndId);
		zsd.setDesc(desc);
		if("teacher".equals(user.getRole())){
			zsd.setLevel(1);
			zsd.setUserNo(user.getUserNo());
		}
		new ZsdDao().save(zsd);
	}
	
	public void diedaiZsd(int id, int modifiedId, String name, int ks, int ndId, String desc) throws Exception{
		ZsdDao dao = new ZsdDao();
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			conn.setAutoCommit(false);
			ZsdModified del = new ZsdModified();
			del.setId(modifiedId);
			dao.delete(conn, del);
			ZsdBean zsd = new ZsdBean();
			zsd.setId(id);
			zsd.setName(name);
			zsd.setKs(ks);
			zsd.setNdId(ndId);
			zsd.setDesc(desc);
			dao.save(conn, zsd);
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
	
	public void updateZsd(int id, String name, int ks, int ndId, String desc, ShiroUser user) throws Exception{
		ZsdDao dao = new ZsdDao();
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			ZsdBean old = (ZsdBean)dao.getById(conn, id, ZsdBean.class);
			if(old.getLevel() == 0 && user.getUserNo().equals(old.getUserNo())){
				ZsdModified zsd = new ZsdModified();
				ZsdModified modified = dao.getModified(id);
				if(modified != null){
					zsd.setId(modified.getId());
				}
				zsd.setZsdId(id);
				zsd.setName(name);
				zsd.setKs(ks);
				zsd.setNdId(ndId);
				zsd.setDesc(desc);
				dao.save(conn, zsd);
			} else {
				ZsdBean zsd = new ZsdBean();
				zsd.setId(id);
				zsd.setName(name);
				zsd.setKs(ks);
				zsd.setNdId(ndId);
				zsd.setDesc(desc);
				dao.save(conn, zsd);
			}
		} finally {
			DBFactory.close(conn, null, null);
		}
	}
	
	public void cainaZsd(int id) throws Exception{
		ZsdDao dao = new ZsdDao();
		ZsdBean zsd = new ZsdBean();
		zsd.setId(id);
		zsd.setLevel(0);
		dao.save(zsd);
	}
	
	public Map<String, Object> getmodifiedInfo(int id) throws Exception {
		return new ZsdDao().getmodifiedInfo(id);
	}
	
	public Map<String, Object> getViewContent(int id, String type) throws Exception {
		ZsdDao dao = new ZsdDao();
		Map<String, Object> map = Maps.newHashMap();
		if("zsd".equals(type)){
			ZsdContentBean b = new ZsdContentBean();
			b.setId(id);
			b = (ZsdContentBean)dao.getById(b);
			map.put("name", b.getName());
			map.put("content", b.getContent());
		} else if("xt".equals(type)){
			XtBean b = new XtBean();
			b.setId(id);
			b = (XtBean)dao.getById(b);
			map.put("name", b.getName());
			map.put("content", b.getContent());
			map.put("answer", b.getAnswer());
		} else if("zt".equals(type)){
			ZtContentBean b = new ZtContentBean();
			b.setId(id);
			b = (ZtContentBean)dao.getById(b);
			map.put("name", b.getName());
			map.put("content", b.getContent());
		}
		return map;
	}
	
	public ZsdContentBean saveZsdContentOnUser(int id, int pid, String name, int isOriginal, int yyfs, int isShare, 
			String content, ShiroUser user) throws Exception{
		ZsdContentBean bean = new ZsdContentBean();
		bean.setId(id);
		bean.setPid(pid);
		bean.setName(name);
		bean.setIsOriginal(isOriginal);
		if(isOriginal == 1){
			bean.setYyfs(yyfs);
		} else {
			bean.setYyfs(1);
		}
		bean.setIsShare(isShare);
		bean.setContent(content);
		bean.setUserNo(user.getUserNo());
		return (ZsdContentBean)new ZsdDao().save(bean);
	}
	
	@SuppressWarnings("unchecked")
	public List<ZsdBean> queryZsd(String dqId, final int kmId, final int njId, final int xq, String userNo) throws Exception{
		return (List<ZsdBean>)new ZsdDao().query(dqId, kmId, njId, xq, userNo);
	}
	
	public Object getInfo(int id, String type) throws Exception {
		if("zsd".equals(type)){
			ZsdContentBean b = new ZsdContentBean();
			b.setId(id);
			return new ZsdDao().getById(b);
		} else {
			XtBean b = new XtBean();
			b.setId(id);
			return new ZsdDao().getById(b);
		}
	}
	
	public List<Map<String, Object>> getZstxUsers() throws Exception {
		return new ZsdDao().getZstxUsers();
	}
	
	public List<Map<String, Object>> searchZsdContents(String type, int id, String userNo) throws Exception{
		return new ZsdDao().searchZsdContents(type, id, userNo);
	}
	
	public List<ZsdBean> getZsdByZj(int bbId, String dqId, int kmId, int njId, int xq, String onUserNo) throws Exception{
		return new ZsdDao().getZsdByZj(bbId, dqId, kmId, njId, xq, onUserNo);
	}
	
	public void saveZsdSort(int zjId, List<Map<String, Object>> zsdes, String userNo) throws Exception {
		new ZsdDao().saveZsdSort(zjId, zsdes, userNo);
	}
	
	public void collect(int id, String type, ShiroUser user) throws Exception {
		new ZsdDao().collect(id, type, user.getUserNo());
	}
	
	public void deleteZsdContent(int id) throws Exception{
		new JdbcDao<ZsdContentBean>().delete(id, ZsdContentBean.class);
	}
	
	private List<ZsdContentBean> toZsdContentBean(ResultSet rs) throws Exception{
		return ResultSetHandler.custom(rs, new IHandler<List<ZsdContentBean>>() {
			@Override
			public List<ZsdContentBean> handler(ResultSet rs) throws Exception {
				List<ZsdContentBean> list = null;
				ResultSetMetaData md = rs.getMetaData();
				while(rs.next()){
					if(list == null){
						list = Lists.newArrayList();
					}
					ZsdContentBean bean = ResultSetHandler.rsToBean(rs, ZsdContentBean.class, md);
					ZsdBean zsd = new ZsdBean();
					zsd.setId(bean.getPid());
					zsd.setName(rs.getString("pName"));
					zsd.setKmId(rs.getInt("kmId"));
					zsd.setDqId(rs.getString("dqId"));
					zsd.setNjId(rs.getInt("njId"));
					zsd.setXq(rs.getInt("xq"));
					bean.setZsd(zsd);
					list.add(bean);
				}
				return list;
			}
		});
	}
	
	private String getQueryZsdContentSql() {
		return "select a.*,b.name as pName,b.dq_id as dqId,b.km_id as kmId,b.nj_id as njId,b.xq as xq from zsd_content as a left join zsd_main as b on(a.pid = b.id) ";
	}
	
	public List<ZsdContentBean> queryZsdContentOnUser(String dqId, final int kmId, final int njId, final int xq, final ShiroUser user) throws Exception{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBFactory.getConn();
			String sql = getQueryZsdContentSql() + "where a.user_no=? and b.km_id=? and b.nj_id=? and b.xq=? and b.dq_id=?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, user.getUserNo());
			stmt.setInt(2, kmId);
			stmt.setInt(3, njId);
			stmt.setInt(4, xq);
			stmt.setString(5, dqId);
			rs = stmt.executeQuery();
			return toZsdContentBean(rs);
		} finally {
			DBFactory.close(conn, stmt, rs);
		}
	}
	
	public List<ZsdContentBean> queryZsdContent(String dqId, final int kmId, final int njId, final int xq) throws Exception{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBFactory.getConn();
			String sql = getQueryZsdContentSql() + "where b.km_id=? and b.nj_id=? and b.xq=? and b.dq_id=?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, kmId);
			stmt.setInt(2, njId);
			stmt.setInt(3, xq);
			stmt.setString(4, dqId);
			rs = stmt.executeQuery();
			return toZsdContentBean(rs);
		} finally {
			DBFactory.close(conn, stmt, rs);
		}
	}
	
	public void saveZt(int id, int kmId, int njId, int xq, int qzqm, String name, int ks, int ndId, String desc) throws Exception{
		ZtBean zt = new ZtBean();
		zt.setId(id);
		zt.setKmId(kmId);
		zt.setNjId(njId);
		zt.setXq(xq);
		zt.setQzqm(qzqm);
		zt.setName(name);
		zt.setKs(ks);
		zt.setNdId(ndId);
		zt.setDesc(desc);
		new ZtDao().save(zt);
	}
	
	public ZtContentBean saveZtContentOnUser(int id, int pid, String name, int isOriginal, int yyfs, int isShare, String content, ShiroUser user) throws Exception{
		ZtContentBean bean = new ZtContentBean();
		bean.setId(id);
		bean.setPid(pid);
		bean.setName(name);
		bean.setIsOriginal(isOriginal);
		if(isOriginal == 1){
			bean.setYyfs(yyfs);
		} else {
			bean.setYyfs(1);
		}
		bean.setIsShare(isShare);
		bean.setContent(content);
		bean.setUserNo(user.getUserNo());
		return (ZtContentBean)new ZtDao().save(bean);
	}
	
	@SuppressWarnings("unchecked")
	public List<ZtBean> searchZt(String key, boolean loadContent) throws Exception{
		if(StringUtils.isNotEmpty(key)){ //搜索
			return null;
		} else {
			return (List<ZtBean>)new ZtDao().query(new ZtBean());
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<ZtBean> queryZt(final int kmId, final int njId, int xq, final int qzqm) throws Exception{
		ZtBean bean = new ZtBean();
		bean.setKmId(kmId);
		bean.setNjId(njId);
		bean.setXq(xq);
		bean.setQzqm(qzqm);
		return (List<ZtBean>)new ZtDao().query(bean);
	}
	
	public void deleteZtContent(int id) throws Exception{
		new JdbcDao<ZtContentBean>().delete(id, ZtContentBean.class);
	}
	
	
	public void collectZtContent(List<Map<String, Object>> list, final ShiroUser user) throws Exception{
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
				uc.setCid(id);
				uc.setUserNo(user.getUserNo());
				uc.setJf(jf);
				uc.setType("zt");
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
	
	public List<ZtContentBean> queryZtContentOnUser(final int kmId, final int njId, int xq, final int qzqm, final ShiroUser user) throws Exception{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBFactory.getConn();
			String userNo = user.getUserNo();
			String sql = "select a.id,a.name,a.yyfs,a.user_no,a.is_share,a.is_original,"
					+ "if(a.user_no='"+userNo+"', 0, 1) as isCollected,c.name as pName "
							+ "from zt_content as a left join user_collections b on (b.user_no='"+userNo+"' and b.type='zt' and b.cid = a.id) left join zt_main as c on(a.pid = c.id) "
					+ "where (a.user_no=? or b.id is not null) and c.km_id=? and c.nj_id=? and c.xq=? and c.qzqm=?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, user.getUserNo());
			stmt.setInt(2, kmId);
			stmt.setInt(3, njId);
			stmt.setInt(4, xq);
			stmt.setInt(5, qzqm);
			rs = stmt.executeQuery();
			return toZtContent(rs, kmId, njId, xq, qzqm);
		} finally {
			DBFactory.close(conn, stmt, rs);
		}
	}
	
	public List<ZtContentBean> getOtherUserContents(final int kmId, final int njId, final int xq, final int qzqm, 
			String contentName, String userName, String name, final ShiroUser user) throws Exception{
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBFactory.getConn();
			stmt = conn.createStatement();
			String userNo = user.getUserNo();
			StringBuffer sql = new StringBuffer();
			sql.append("select a.id,a.name,a.yyfs,a.user_no,a.is_share,a.is_original,if(a.id in (");
			sql.append(" select cid from user_collections where user_no = '"+userNo+"' and type='zt'");
			sql.append("), 1, 0) as isCollected,b.name as userName,c.name as pName ");
			sql.append("from zt_content a left join teacher_info b on (a.user_no = b.user_no)");
			sql.append("left join zt_main c on (a.pid = c.id)");
			sql.append(" where a.user_no <> '"+userNo+"' and a.is_share=1 and c.km_id="+kmId+" and c.nj_id="+njId+" and c.xq="+xq+" and c.qzqm="+qzqm+" ");
			if(StringUtils.isNotEmpty(contentName)){
				sql.append("and a.name like '%"+contentName+"%' ");
			}
			if(StringUtils.isNotEmpty(userName)){
				sql.append("and b.name like '%"+userName+"%'");
			}
			if(StringUtils.isNotEmpty(name)){
				sql.append("and c.name like '%"+name+"%'");
			}
			rs = stmt.executeQuery(sql.toString());
			return toZtContent(rs, kmId, njId, xq, qzqm);
		} finally {
			DBFactory.close(conn, stmt, rs);
		}
	}
	
	public List<ZtContentBean> queryZtContent(final int kmId, final int njId, int xq, final int qzqm) throws Exception{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBFactory.getConn();
			String sql = "select a.*,b.name as pName from zt_content as a left join zt_main as b on(a.pid = b.id) "
					+ "where b.km_id=? and b.nj_id=? and b.xq=? and b.qzqm=?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, kmId);
			stmt.setInt(2, njId);
			stmt.setInt(3, xq);
			stmt.setInt(4, qzqm);
			rs = stmt.executeQuery();
			return toZtContent(rs, kmId, njId, xq, qzqm);
		} finally {
			DBFactory.close(conn, stmt, rs);
		}
	}
	
	private List<ZtContentBean> toZtContent(ResultSet rs, final int kmId, final int njId, final int xq, final int qzqm) throws Exception{
		return ResultSetHandler.custom(rs, new IHandler<List<ZtContentBean>>() {
			@Override
			public List<ZtContentBean> handler(ResultSet rs) throws Exception {
				List<ZtContentBean> list = null;
				ResultSetMetaData md = rs.getMetaData();
				while(rs.next()){
					if(list == null){
						list = Lists.newArrayList();
					}
					ZtContentBean bean = ResultSetHandler.rsToBean(rs, ZtContentBean.class, md);
					if(ResultSetHandler.hasColumn("isCollected", md)){
						int isCollected = rs.getInt("isCollected");
						bean.setCollected(isCollected==1);
					}
					if(ResultSetHandler.hasColumn("userName", md)){
						bean.setUserName(rs.getString("userName"));
					}
					ZtBean zt = new ZtBean();
					zt.setId(bean.getPid());
					zt.setName(rs.getString("pName"));
					zt.setKmId(kmId);
					zt.setNjId(njId);
					zt.setQzqm(qzqm);
					zt.setXq(xq);
					bean.setZt(zt);
					list.add(bean);
				}
				return list;
			}
		});
	}
	
	public List<XtBean> queryXtOnUser(String dqId, int kmId, int njId, int xq, ShiroUser user) throws Exception{
		return new XtDao().query(dqId, kmId, njId, xq, user.getUserNo());
	}
	
	public List<XtBean> queryXt(String dqId, int kmId, int njId, int xq) throws Exception{
		return new XtDao().query(dqId, kmId, njId, xq, null);
	}
	
	public void deleteXt(int id) throws Exception{
		new XtDao().delete(id);
	}
	
	public XtBean saveXt(int id, int ndId, int ztId, List<Integer> zsdIds, String name, int typeId,
			int isOriginal, int yyfs, int isShare, String content, String answer, ShiroUser user) throws Exception{
		XtBean xt = new XtBean();
		xt.setUserNo(user.getUserNo());
		xt.setId(id);
		xt.setNdId(ndId);
		xt.setZtId(ztId);
		xt.setName(name);
		xt.setTypeId(typeId);
		xt.setIsOriginal(isOriginal);
		if(isOriginal == 1){
			xt.setYyfs(yyfs);
		} else {
			xt.setYyfs(1);
		}
		xt.setIsShare(isShare);
		xt.setContent(content);
		xt.setAnswer(answer);
		if(zsdIds != null){
			List<ZsdBean> zsds = Lists.newArrayList();
			for(int zsdId : zsdIds){
				ZsdBean zsd = new ZsdBean();
				zsd.setId(zsdId);
				zsds.add(zsd);
			}
			xt.setZsds(zsds);
		}
		return (XtBean)new XtDao().save(xt);
	}
	
	@SuppressWarnings("unchecked")
	public List<XtZsdRef> getXtZsdRefs(int id) throws Exception{
		XtZsdRef ref = new XtZsdRef();
		ref.setXtId(id);
		return (List<XtZsdRef>)new JdbcDao<XtZsdRef>().query(ref);
	}

}
