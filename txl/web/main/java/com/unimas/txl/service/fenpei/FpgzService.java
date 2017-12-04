package com.unimas.txl.service.fenpei;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.unimas.common.util.StringUtils;
import com.unimas.txl.bean.ConfigBean;
import com.unimas.txl.bean.fenpei.FenpeiBean;
import com.unimas.txl.bean.fenpei.FenpeiSyzBean;
import com.unimas.txl.bean.fenpei.LxrFenpeiBean;
import com.unimas.txl.dao.fenpei.FpgzDao;
import com.unimas.txl.service.ConfigService;
import com.unimas.web.auth.AuthRealm.ShiroUser;

public class FpgzService {
	
	//private static final String FENPEI_ZHOUQI_FILE_PATH = FpgzService.class.getClassLoader().getResource("fenpei-zhouqi.properties").getFile();
	//private static final String FENPEI_ZHOUQI_FILE_PATH = "D:\\work\\learn\\ttf\\tlm\\txl-web\\main\\resources\\fenpei-zhouqi.properties";
	
	public List<FenpeiBean> query(ShiroUser user) throws Exception {
		return query(user.getJigouId());
	}
	
	@SuppressWarnings("unchecked")
	public List<FenpeiBean> query(int jigouId) throws Exception {
		FenpeiBean bean = new FenpeiBean();
		bean.setJigouId(jigouId);
		return (List<FenpeiBean>) new FpgzDao().query(bean);
	}
	
	public void add(String dqId, int xuexiaoId, int nj, int bj, int danliang, List<Integer> syzIds
			, ShiroUser user) throws Exception {
		FenpeiBean bean = new FenpeiBean();
		int jigouId = user.getJigouId();
		bean.setJigouId(jigouId);
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
		bean.setXuexiaoId(xuexiaoId);
		bean.setNj(nj);
		bean.setBj(bj);
		bean.setDanliang(danliang);
		new FpgzDao().save(bean);
		restartJob(user);
	}
	
	public void update(int id, String dqId, int xuexiaoId, int nj, int bj, int danliang
			, List<Integer> syzIds, ShiroUser user) throws Exception {
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
		bean.setXuexiaoId(xuexiaoId);
		bean.setNj(nj);
		bean.setBj(bj);
		bean.setDanliang(danliang);
		new FpgzDao().save(bean);
		restartJob(user);
	}
	
	public void delete(int id, ShiroUser user) throws Exception {
		new FpgzDao().delete(id);
		restartJob(user);
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
	
	public static Map<Integer, Integer> getAllZhouqi() throws Exception{
		List<ConfigBean> list = new ConfigService().getAll();
		Map<Integer, Integer> map = Maps.newHashMap();
		if(list != null){
			for(ConfigBean b : list){
				map.put(b.getJigouId(), b.getZhouqi());
			}
		}
		return map;
	}
	
	public int getZhouqi(ShiroUser user) throws Exception{
		return getZhouqi(user.getJigouId());
	}
	
	public int getZhouqi(int jigouId) throws Exception{
		Map<Integer, Integer> map = getAllZhouqi();
		if(map.containsKey(jigouId)){
			return map.get(jigouId);
		} else {
			return -1;
		}
	}
	
	/*public void updateZhouqi(int zhouqi, ShiroUser user) throws Exception{
		int jigouId = user.getJigouId();
		PropertyUtils.writeProperties(FENPEI_ZHOUQI_FILE_PATH, String.valueOf(jigouId), String.valueOf(zhouqi));
		restartJob(user);
	}*/
	
	public void restartJob(ShiroUser user) throws Exception {
		int jigouId = user.getJigouId();
		FenpeiJob.startJob(jigouId, getZhouqi(user));
	}
	
	public static void main(String[] args) throws Exception {
		new FpgzService().clearLxrGuanzhu(1);
	}
	

}
