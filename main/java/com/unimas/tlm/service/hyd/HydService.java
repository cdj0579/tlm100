package com.unimas.tlm.service.hyd;

import java.sql.Connection;

import com.unimas.jdbc.DBFactory;
import com.unimas.tlm.bean.HydListBean;
import com.unimas.tlm.bean.HydRule;
import com.unimas.tlm.bean.user.TeacherInfo;
import com.unimas.tlm.dao.JdbcDao;
import com.unimas.tlm.service.SystemService;
import com.unimas.web.auth.AuthRealm.ShiroUser;

public class HydService {
	
	public void saveHydInfo(HydListBean bean) throws Exception{
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			conn.setAutoCommit(false);
			JdbcDao<HydListBean> dao = new JdbcDao<HydListBean>();
			dao.save(conn, bean);
			String rule = bean.getRule();
			HydRule hydRule = new SystemService().getHydRuleByType(rule);
			ShiroUser user = bean.getUser();
			Object info = user.getInfo();
			if(info != null || hydRule != null){
				if(info instanceof TeacherInfo){
					TeacherInfo teacher = (TeacherInfo)info;
					TeacherInfo updateInfo = new TeacherInfo();
					updateInfo.setId(teacher.getId());
					updateInfo.setHyd(teacher.getHyd()+hydRule.getHyd());
					dao.save(conn, updateInfo);
				}
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

}
