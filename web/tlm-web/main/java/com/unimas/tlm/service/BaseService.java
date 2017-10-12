package com.unimas.tlm.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.unimas.jdbc.DBFactory;
import com.unimas.jdbc.handler.ResultSetHandler;
import com.unimas.tlm.bean.base.CstBean;
import com.unimas.tlm.bean.base.MbxxBean;
import com.unimas.tlm.bean.base.XkdwBean;
import com.unimas.tlm.bean.base.ZjBean;
import com.unimas.tlm.dao.JdbcDao;
import com.unimas.tlm.dao.base.CstkDao;
import com.unimas.tlm.dao.base.MbxxDao;
import com.unimas.tlm.dao.base.ZjDao;

@Service
public class BaseService {
	
	@SuppressWarnings("unchecked")
	public List<CstBean> queryCtsList() throws Exception {
		CstBean cst = new CstBean();
		return (List<CstBean>) new CstkDao().query(cst);
	}
	
	public void saveCst(int id, int kmId, int njId, String name, String answer, List<Map<String, Object>> options) throws Exception{
		new CstkDao().saveCst(id, kmId, njId, name, answer, options);
	}
	
	public void deleteCst(int id) throws Exception{
		new CstkDao().deleteCst(id);
	}
	
	public CstBean getCst(int id) throws Exception{
		return new CstkDao().getCst(id);
	}
	
	@SuppressWarnings("unchecked")
	public List<MbxxBean> queryMbxxList() throws Exception {
		MbxxBean mbxx = new MbxxBean();
		return (List<MbxxBean>) new MbxxDao().query(mbxx);
	}
	
	public void saveMbxx(int id, String dqId, String name, String desc, List<Map<String, Object>> mbfList) throws Exception{
		new MbxxDao().saveMbxx(id, dqId, name, desc, mbfList);
	}
	
	public void deleteMbxx(int id) throws Exception{
		new MbxxDao().deleteMbxx(id);
	}
	
	public MbxxBean getMbxx(int id) throws Exception{
		return new MbxxDao().getMbxx(id);
	}
	
	@SuppressWarnings({ "unchecked" })
	public List<XkdwBean> getXkdwList() throws Exception{
		return (List<XkdwBean>)new JdbcDao<XkdwBean>().query(new XkdwBean());
	}
	
	public void setXkdw(int id, int kmId, int level1, int level2) throws Exception{
		XkdwBean bean = new XkdwBean();
		bean.setId(id);
		bean.setKmId(kmId);
		bean.setLevel1(level1);
		bean.setLevel2(level2);
		new JdbcDao<XkdwBean>().save(bean);
	}
	
	@SuppressWarnings("unchecked")
	public List<ZjBean> queryZjList(int pid, String dqId, int bbId, int kmId, int njId, int xq) throws Exception {
		ZjBean zj = new ZjBean();
		zj.setPid(pid);
		zj.setDqId(dqId);
		zj.setKmId(kmId);
		zj.setNjId(njId);
		zj.setXq(xq);
		zj.setBbId(bbId);
		return (List<ZjBean>) new ZjDao().query(zj);
	}
	
	@SuppressWarnings("unchecked")
	public List<ZjBean> queryZjList(String dqId, int kmId, int njId, int xq) throws Exception {
		ZjBean zj = new ZjBean();
		zj.setKmId(kmId);
		zj.setNjId(njId);
		zj.setDqId(dqId);
		zj.setXq(xq);
		return (List<ZjBean>) new ZjDao().query(zj);
	}
	
	@SuppressWarnings("unchecked")
	public List<ZjBean> queryZjList(int bbId, String dqId, int kmId, int njId, int xq) throws Exception {
		ZjBean zj = new ZjBean();
		zj.setBbId(bbId);
		zj.setKmId(kmId);
		zj.setNjId(njId);
		zj.setDqId(dqId);
		zj.setXq(xq);
		return (List<ZjBean>) new ZjDao().query(zj);
	}
	
	
	public void copyZj(String newDqId, int newBbId, String dqId, int bbId, int kmId, int njId, int xq) throws Exception{
		List<ZjBean> zjList = queryZjList(bbId, dqId, kmId, njId, xq);
		if(zjList == null) return;
		List<ZjBean> treeList = Lists.newArrayList();
		Map<Integer, ZjBean> idMaps = Maps.newHashMap();
		for(int i=0;i<zjList.size();i++){
			ZjBean bean = zjList.get(i);
			int id = bean.getId();
			idMaps.put(id, bean);
			bean.setId(-1);
			bean.setDqId(newDqId);
			bean.setBbId(newBbId);
		}
		for(ZjBean bean : zjList){
			int pid = bean.getPid();
			ZjBean p = idMaps.get(pid);
			if(p == null){
				treeList.add(bean);
			} else {
				p.putChild(bean);
			}
		}
		new ZjDao().save(treeList);
	}
	
	public ZjBean saveZj(int id, String dqId, int bbId, int kmId, int njId, int xq, String name, String bm, int xh, int pid) throws Exception{
		ZjBean zj = new ZjBean();
		zj.setId(id);
		zj.setBbId(bbId);
		zj.setDqId(dqId);
		zj.setKmId(kmId);
		zj.setNjId(njId);
		zj.setXq(xq);
		zj.setName(name);
		zj.setBm(bm);
		zj.setPid(pid);
		zj.setXh(xh);
		return (ZjBean)new ZjDao().save(zj);
	}
	
	public void deleteZj(int id) throws Exception{
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBFactory.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select queryChildrenZjInfo("+id+")");
			String ids = ResultSetHandler.toString(rs);
			stmt.execute("delete from zj where id in ("+ids+")");
		} finally {
			DBFactory.close(conn, stmt, rs);
		}
	}
	
	public ZjBean getZj(int id) throws Exception{
		return (ZjBean)new JdbcDao<ZjBean>().getById(id, ZjBean.class);
	}
	
	public static void main(String[] args) throws Exception {
		//System.out.println(new BaseService().queryCtsList());
		new BaseService().deleteZj(69);
	}

}
