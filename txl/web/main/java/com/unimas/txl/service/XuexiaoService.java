package com.unimas.txl.service;

import java.util.List;

import com.unimas.common.util.StringUtils;
import com.unimas.txl.bean.XuexiaoBean;
import com.unimas.txl.dao.JdbcDao;

public class XuexiaoService {
	
	@SuppressWarnings("unchecked")
	public List<XuexiaoBean> query(String dqId) throws Exception {
		XuexiaoBean bean = new XuexiaoBean();
		if(StringUtils.isNotEmpty(dqId)){
			bean.setDqId(dqId);
		}
		return (List<XuexiaoBean>) new JdbcDao<XuexiaoBean>().query(bean);
	}
	
	public void add(String dqId, String name, String beizhu) throws Exception {
		XuexiaoBean bean = new XuexiaoBean();
		bean.setDqId(dqId);
		bean.setBeizhu(beizhu);
		bean.setName(name);
		new JdbcDao<XuexiaoBean>().save(bean);
	}
	
	public void update(int id, String dqId, String name, String beizhu) throws Exception {
		XuexiaoBean bean = new XuexiaoBean();
		bean.setId(id);
		bean.setDqId(dqId);
		bean.setBeizhu(beizhu);
		bean.setName(name);
		new JdbcDao<XuexiaoBean>().save(bean);
	}
	
	public void delete(int id) throws Exception {
		XuexiaoBean bean = new XuexiaoBean();
		bean.setId(id);
		new JdbcDao<XuexiaoBean>().delete(bean);
	}

}
