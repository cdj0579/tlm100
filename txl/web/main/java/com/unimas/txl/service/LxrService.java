package com.unimas.txl.service;

import java.sql.Connection;
import java.util.List;

import com.unimas.common.util.StringUtils;
import com.unimas.jdbc.DBFactory;
import com.unimas.txl.bean.LxrBean;
import com.unimas.txl.bean.fenpei.LxrQianyueBean;
import com.unimas.txl.dao.LxrDao;
import com.unimas.web.auth.AuthRealm.ShiroUser;

public class LxrService {
	
	public List<LxrBean> queryOnAdmin(int status, int xuexiaoId, String dqId, int nj, int bj, ShiroUser user) throws Exception {
		return new LxrDao().queryOnAdmin(user.getJigouId(), status, xuexiaoId, dqId, nj, bj);
	}
	
	public List<LxrBean> query(int xuexiaoId, String dqId, int nj, int bj, ShiroUser user) throws Exception {
		return query(user.getJigouId(), user.getUserId(), xuexiaoId, dqId, nj, bj);
	}
	
	@SuppressWarnings("unchecked")
	public List<LxrBean> query(int jigouId, int lryId, int xuexiaoId, String dqId, int nj, int bj) throws Exception {
		LxrBean bean = new LxrBean();
		bean.setXuexiaoId(xuexiaoId);
		if(StringUtils.isNotEmpty(dqId)){
			bean.setDqId(dqId);
		}
		bean.setNianji(nj);
		bean.setBanji(bj);
		bean.setIsDel(0);
		bean.setJigouId(jigouId);
		bean.setLryId(lryId);
		return (List<LxrBean>) new LxrDao().query(bean);
	}
	
	public List<LxrBean> queryNofp(int jigouId, int xuexiaoId, String dqId, int nj, int bj) throws Exception {
		return new LxrDao().queryNofp(jigouId, xuexiaoId, dqId, nj, bj);
	}
	
	public void add(String name, int xb, int xuexiaoId, String dqId, int nj, int bj, String lianxiren
			, String phone, ShiroUser user) throws Exception {
		LxrBean bean = new LxrBean();
		bean.setJigouId(user.getJigouId());
		bean.setLryId(user.getUserId());
		bean.setXingming(name);
		bean.setXingbie(xb);
		bean.setXuexiaoId(xuexiaoId);
		bean.setDqId(dqId);
		bean.setNianji(nj);
		bean.setBanji(bj);
		bean.setLianxiren(lianxiren);
		bean.setPhone(phone);
		bean.setIsDel(0);
		bean.setCishu(0);
		new LxrDao().save(bean);
	}
	
	public void update(int id, String name, int xb, int xuexiaoId, String dqId, int nj, int bj, String lianxiren
			, String phone, ShiroUser user) throws Exception {
		LxrBean bean = new LxrBean();
		bean.setJigouId(user.getJigouId());
		bean.setId(id);
		bean.setXingming(name);
		bean.setXingbie(xb);
		bean.setXuexiaoId(xuexiaoId);
		bean.setDqId(dqId);
		bean.setNianji(nj);
		bean.setBanji(bj);
		bean.setLianxiren(lianxiren);
		bean.setPhone(phone);
		new LxrDao().save(bean);
	}
	
	public void delete(int id) throws Exception {
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			LxrDao dao = new LxrDao();
			dao.checkStatus(conn, id);
			LxrBean bean = new LxrBean();
			bean.setId(id);
			bean.setIsDel(1);
			dao.save(conn, bean);
		} finally {
			DBFactory.close(conn, null, null);
		}
	}
	
	public void cancerQianyue(int id) throws Exception {
		LxrDao dao = new LxrDao();
		LxrQianyueBean bean = new LxrQianyueBean();
		bean.setLxrId(id);
		dao.delete(bean);
	}

}
