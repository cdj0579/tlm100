package com.unimas.tlm.service.jfrw;

import java.sql.Connection;

import com.unimas.jdbc.DBFactory;
import com.unimas.tlm.bean.jfrw.JfListBean;
import com.unimas.tlm.bean.user.TeacherInfo;
import com.unimas.tlm.dao.JdbcDao;
import com.unimas.web.auth.AuthRealm.ShiroUser;

public class JfService {
	
	public void saveJfInfo(JfListBean bean) throws Exception{
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			conn.setAutoCommit(false);
			JdbcDao<JfListBean> dao = new JdbcDao<JfListBean>();
			dao.save(bean);
			ShiroUser user = bean.getUser();
			Object info = user.getInfo();
			if(info != null){
				if(info instanceof TeacherInfo){
					TeacherInfo teacher = (TeacherInfo)info;
					TeacherInfo updateInfo = new TeacherInfo();
				}
			}
			/*int rwId = bean.getRwId();
			RwDao dao = new RwDao();
			JfListBean jf = (JfListBean)dao.getById(conn, rwId, JfListBean.class);
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
			}*/
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

}
