package com.unimas.txl.service;

import java.sql.Connection;
import java.util.List;

import com.unimas.common.util.StringUtils;
import com.unimas.jdbc.DBFactory;
import com.unimas.txl.bean.LxrBean;
import com.unimas.txl.dao.LxrDao;
import com.unimas.web.auth.AuthRealm.ShiroUser;

public class LxrService {
	
	@SuppressWarnings("unchecked")
	public List<LxrBean> query(int xuexiaoId, String dqId, int nj, int bj, ShiroUser user) throws Exception {
		LxrBean bean = new LxrBean();
		bean.setXuexiaoId(xuexiaoId);
		if(StringUtils.isNotEmpty(dqId)){
			bean.setDqId(dqId);
		}
		bean.setNianji(nj);
		bean.setBanji(bj);
		bean.setIsDel(0);
		bean.setJigouId(user.getJigouId());
		return (List<LxrBean>) new LxrDao().query(bean);
	}
	
	public void add(String name, int xb, int xuexiaoId, String dqId, int nj, int bj, String lianxiren
			, String phone, String beizhu, ShiroUser user) throws Exception {
		LxrBean bean = new LxrBean();
		bean.setJigouId(user.getJigouId());
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
		bean.setBeizhu(beizhu);
		new LxrDao().save(bean);
	}
	
	public void update(int id, String name, int xb, int xuexiaoId, String dqId, int nj, int bj, String lianxiren
			, String phone, String beizhu) throws Exception {
		LxrBean bean = new LxrBean();
		bean.setId(id);
		bean.setXingming(name);
		bean.setXingbie(xb);
		bean.setXuexiaoId(xuexiaoId);
		bean.setDqId(dqId);
		bean.setNianji(nj);
		bean.setBanji(bj);
		bean.setLianxiren(lianxiren);
		bean.setPhone(phone);
		bean.setBeizhu(beizhu);
		new LxrDao().save(bean);
	}
	
	public void delete(int id) throws Exception {
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			LxrDao dao = new LxrDao();
			LxrBean bean = new LxrBean();
			bean.setId(id);
			bean.setIsDel(1);
			dao.save(conn, bean);
		} finally {
			DBFactory.close(conn, null, null);
		}
	}

}
