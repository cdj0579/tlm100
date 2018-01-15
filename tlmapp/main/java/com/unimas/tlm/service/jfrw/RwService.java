package com.unimas.tlm.service.jfrw;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.unimas.jdbc.DBFactory;
import com.unimas.tlm.bean.jfrw.RwBean;
import com.unimas.tlm.bean.jfrw.RwListBean;
import com.unimas.tlm.bean.user.TeacherInfo;
import com.unimas.tlm.bean.zs.XtBean;
import com.unimas.tlm.bean.zs.ZsdContentBean;
import com.unimas.tlm.bean.zs.ZtContentBean;
import com.unimas.tlm.dao.JdbcDao;
import com.unimas.tlm.dao.jfrw.RwDao;
import com.unimas.tlm.service.user.UserService;
import com.unimas.web.auth.AuthRealm.ShiroUser;

@Service
public class RwService {
	
	public void fbrw(String name, int type, int jf, int maxNum, String desc, int kmId, int njId, String dqId, int xq, int qzqm) throws Exception{
		RwBean bean = new RwBean();
		bean.setId(-1);
		bean.setName(name);
		bean.setType(type);
		bean.setJf(jf);
		bean.setMaxNum(maxNum);
		bean.setDesc(desc);
		bean.setKmId(kmId);
		bean.setNjId(njId);
		bean.setDqId(dqId);
		bean.setXq(xq);
		bean.setQzqm(qzqm);
		bean.setStatus(1);
		new RwDao().save(bean);
	}
	
	public void close(int id) throws Exception{
		RwBean bean = new RwBean();
		bean.setId(id);
		bean.setStatus(3);
		new RwDao().save(bean);
	}
	
	public void wcrw(RwListBean bean) throws Exception{
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			conn.setAutoCommit(false);
			int rwId = bean.getRwId();
			RwDao dao = new RwDao();
			RwBean rw = (RwBean)dao.getById(conn, rwId, RwBean.class);
			int maxNum = rw.getMaxNum();
			int fulfilNum = rw.getFulfilNum();
			if(rw.getStatus() == 1 && maxNum > fulfilNum){
				dao.save(conn, bean);
				RwBean updateRw = new RwBean();
				fulfilNum++;
				updateRw.setId(rwId);
				updateRw.setFulfilNum(fulfilNum);
				if(fulfilNum == maxNum){
					updateRw.setStatus(2);
				}
				dao.save(conn, updateRw);
			}
			conn.commit();
		} catch(Exception e){
			if(conn != null){
				try {
					conn.rollback();
				} catch(Exception t){}
			}
			throw e;
		} finally {
			DBFactory.close(conn, null, null);
		}
	}
	
	public List<RwBean> search(int status, int kmId, int njId) throws Exception{
		return new RwDao().search(status, kmId, njId);
	}
	
	public List<RwBean> dwcrw(int status, int kmId, ShiroUser user) throws Exception{
		return new RwDao().dwcrw(status, kmId, user.getUserNo());
	}
	
	public List<RwBean> ywcrw(ShiroUser user) throws Exception{
		return new RwDao().ywcrw(user.getUserNo());
	}
	
	public List<RwListBean> dshrw(int status) throws Exception{
		return new RwDao().dshrw(status);
	}
	
	public void shyj(int id, int status, String shyj, int rwJf, String userNo) throws Exception {
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			conn.setAutoCommit(false);
			RwDao dao = new RwDao();
			RwListBean bean = new RwListBean();
			bean.setId(id);
			bean.setStatus(status);
			bean.setShyj(shyj);
			dao.save(conn, bean);
			if(status == 1){
				TeacherInfo info = new UserService().getTeacherByUserNo(conn, userNo);
				TeacherInfo updateInfo = new TeacherInfo();
				updateInfo.setId(info.getId());
				updateInfo.setJf(rwJf+info.getJf());
				new JdbcDao<TeacherInfo>().save(conn, updateInfo);
			}
			conn.commit();
		} catch(Exception e){
			if(conn != null){
				try {
					conn.rollback();
				} catch(Exception t){}
			}
			throw e;
		} finally {
			DBFactory.close(conn, null, null);
		}
	}
	
	public Object getRwContent(int contentId, int type) throws Exception {
		if(type == 1){
			return new JdbcDao<XtBean>().getById(contentId, XtBean.class);
		} else if(type == 2){
			return new JdbcDao<ZsdContentBean>().getById(contentId, ZsdContentBean.class);
		} else if(type == 3){
			return new JdbcDao<ZtContentBean>().getById(contentId, ZtContentBean.class);
		}
		return null;
	}

}
