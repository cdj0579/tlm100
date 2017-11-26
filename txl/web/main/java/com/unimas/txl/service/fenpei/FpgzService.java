package com.unimas.txl.service.fenpei;

import java.sql.Connection;
import java.util.List;

import com.google.common.collect.Lists;
import com.unimas.common.util.StringUtils;
import com.unimas.txl.bean.fenpei.FenpeiBean;
import com.unimas.txl.bean.fenpei.FenpeiSyzBean;
import com.unimas.txl.bean.fenpei.LxrFenpeiBean;
import com.unimas.txl.dao.fenpei.FpgzDao;
import com.unimas.web.auth.AuthRealm.ShiroUser;

public class FpgzService {
	
	public List<FenpeiBean> query(ShiroUser user) throws Exception {
		return query(user.getJigouId());
	}
	
	@SuppressWarnings("unchecked")
	public List<FenpeiBean> query(int jigouId) throws Exception {
		FenpeiBean bean = new FenpeiBean();
		bean.setJigouId(jigouId);
		return (List<FenpeiBean>) new FpgzDao().query(bean);
	}
	
	public void add(String dqId, int zhouqi, int xuexiaoId, int nj, int bj, int danliang, List<Integer> syzIds
			, ShiroUser user) throws Exception {
		FenpeiBean bean = new FenpeiBean();
		bean.setJigouId(user.getJigouId());
		if(StringUtils.isNotEmpty(dqId)){
			bean.setDqId(dqId);
		} else {
			List<FenpeiSyzBean> refs = Lists.newArrayList();
			if(syzIds != null){
				for(int syzId : syzIds) {
					FenpeiSyzBean b = new FenpeiSyzBean();
					b.setSyzId(syzId);
					refs.add(b);
				}
			}
			bean.setRefs(refs);
		}
		bean.setZhouqi(zhouqi);
		bean.setXuexiaoId(xuexiaoId);
		bean.setNj(nj);
		bean.setBj(bj);
		bean.setDanliang(danliang);
		new FpgzDao().save(bean);
	}
	
	public void update(int id, String dqId, int zhouqi, int xuexiaoId, int nj, int bj, int danliang
			, List<Integer> syzIds) throws Exception {
		FenpeiBean bean = new FenpeiBean();
		bean.setId(id);
		if(StringUtils.isNotEmpty(dqId)){
			bean.setDqId(dqId);
		} else {
			List<FenpeiSyzBean> refs = Lists.newArrayList();
			if(syzIds != null){
				for(int syzId : syzIds) {
					FenpeiSyzBean b = new FenpeiSyzBean();
					b.setSyzId(syzId);
					refs.add(b);
				}
			}
			bean.setRefs(refs);
			bean.setDqId("");
		}
		bean.setZhouqi(zhouqi);
		bean.setXuexiaoId(xuexiaoId);
		bean.setNj(nj);
		bean.setBj(bj);
		bean.setDanliang(danliang);
		new FpgzDao().save(bean);
	}
	
	public void delete(int id) throws Exception {
		new FpgzDao().delete(id);
	}
	
	@SuppressWarnings("unchecked")
	public List<FenpeiSyzBean> getSyzList(int id) throws Exception{
		FenpeiSyzBean bean = new FenpeiSyzBean();
		bean.setGuizeId(id);
		return (List<FenpeiSyzBean>)new FpgzDao().query(bean);
	}
	
	public void saveLxrFenpeiInfo(Connection conn, int jigouId, int lxrId, int syzId) throws Exception {
		LxrFenpeiBean bean = new LxrFenpeiBean();
		bean.setJigouId(jigouId);
		bean.setLxrId(lxrId);
		bean.setSyzId(syzId);
		new FpgzDao().save(conn, bean);
	}
	
	public void clearLxrFenpei(int jigouId) throws Exception {
		LxrFenpeiBean bean = new LxrFenpeiBean();
		bean.setJigouId(jigouId);
		new FpgzDao().delete(bean);
	}
	
	public void clearLxrGuanzhu(int jigouId) throws Exception {
		new FpgzDao().clearLxrGuanzhu(jigouId);
	}
	
	public static void main(String[] args) throws Exception {
		new FpgzService().clearLxrGuanzhu(1);
	}

}
