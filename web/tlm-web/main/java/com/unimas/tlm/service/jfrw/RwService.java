package com.unimas.tlm.service.jfrw;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.unimas.jdbc.DBFactory;
import com.unimas.tlm.bean.jfrw.RwListBean;
import com.unimas.tlm.bean.jfrw.RwMainBean;
import com.unimas.tlm.bean.jfrw.UserFulfilRwBean;
import com.unimas.tlm.bean.user.TeacherInfo;
import com.unimas.tlm.dao.JdbcDao;
import com.unimas.tlm.dao.jfrw.RwDao;
import com.unimas.tlm.service.user.UserService;
import com.unimas.web.auth.AuthRealm.ShiroUser;

@Service
public class RwService {
	
	private static final Logger logger = Logger.getLogger(RwService.class);
	
	/**
	 * 领取任务后未完成的超时时间
	 */
	public static final long TIMEOUT_CSWWC = 24*60*60;
	/**
	 * 超时未完成时，扣取的积分数
	 */
	public static final int KF_CSWWC = 1;
	/**
	 * 审核不通过时，扣取的积分数
	 */
	public static final int KF_SHBTG = KF_CSWWC;
	
	
	public void fbrw(String name, int jf, int maxNum, String desc, List<Map<String, Object>> list) throws Exception{
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			conn.setAutoCommit(false);
			RwMainBean bean = new RwMainBean();
			bean.setName(name);
			bean.setJf(jf);
			bean.setMaxNum(maxNum);
			bean.setStatus(0);
			bean.setDesc(desc);
			RwDao dao = new RwDao();
			dao.save(conn, bean);
			if(list != null && list.size() > 0){
				int rwId = bean.getId();
				for(Map<String, Object> map : list){
					int sid = Integer.parseInt(String.valueOf(map.get("sid")));
					int type = Integer.parseInt(String.valueOf(map.get("type")));
					RwListBean sBean = new RwListBean();
					sBean.setPid(rwId);
					sBean.setSid(sid);
					sBean.setType(type);
					dao.save(conn, sBean);
				}
			} else {
				throw new Exception("保存任务失败，没有任务详细信息！");
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
	
	public void close(int id) throws Exception{
		RwMainBean bean = new RwMainBean();
		bean.setId(id);
		bean.setStatus(2);
		new RwDao().save(bean);
	}
	
	public void lqrw(int rwId, ShiroUser user) throws Exception{
		UserFulfilRwBean bean = new UserFulfilRwBean();
		bean.setRwId(rwId);
		bean.setUserNo(user.getUserNo());
		bean.setStatus(0);
		new RwDao().save(bean);
	}
	
	public void fqrw(int id) throws Exception{
		UserFulfilRwBean bean = new UserFulfilRwBean();
		bean.setId(id);
		bean.setStatus(5);
		new RwDao().save(bean);
	}
	
	public void wcrw(UserFulfilRwBean bean) throws Exception{
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			RwDao dao = new RwDao();
			bean.setStatus(1);
			dao.save(conn, bean);
		} finally {
			DBFactory.close(conn, null, null);
		}
	}
	
	public List<RwMainBean> search(int status) throws Exception{
		return new RwDao().search(status);
	}
	
	public List<RwListBean> dlqrw(int status, int kmId, ShiroUser user) throws Exception{
		return new RwDao().dlqrw(status, kmId, user.getUserNo());
	}
	
	public List<UserFulfilRwBean> dwcrw(ShiroUser user) throws Exception{
		return new RwDao().dwcrw(user.getUserNo());
	}
	
	public List<UserFulfilRwBean> dshrw(int status) throws Exception{
		return new RwDao().dshrw(status);
	}
	
	public void shyj(int id, int status, String shyj) throws Exception {
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			conn.setAutoCommit(false);
			RwDao dao = new RwDao();
			UserFulfilRwBean bean = new UserFulfilRwBean();
			bean.setId(id);
			bean.setStatus(status);
			bean.setShyj(shyj);
			dao.save(conn, bean);
			bean = (UserFulfilRwBean) new JdbcDao<UserFulfilRwBean>().getById(conn, id, UserFulfilRwBean.class);
			String userNo = bean.getUserNo();
			TeacherInfo info = new UserService().getTeacherByUserNo(conn, userNo);
			if(status == 2){
				RwListBean rwListBean = (RwListBean) new JdbcDao<RwListBean>().getById(conn, bean.getRwId(), RwListBean.class);
				RwMainBean rwBean = dao.getFullBeanById(conn, rwListBean.getPid());
				
				//审核通过用户获取积分
				int rwJf = rwBean.getJf();
				TeacherInfo updateUser = new TeacherInfo();
				updateUser.setId(info.getId());
				updateUser.setJf(rwJf+info.getJf());
				new JdbcDao<TeacherInfo>().save(conn, updateUser);
				
				//任务子表中已经完成次数累加
				RwListBean updateRwListBean = new RwListBean();
				updateRwListBean.setId(rwListBean.getId());
				updateRwListBean.setFulfilNum(1+rwListBean.getFulfilNum());
				new JdbcDao<RwListBean>().save(conn, updateRwListBean);
				
				//任务主表中已经完成次数累加，若完成次数已达上限则修改状态为已完成
				int fulfilNum = rwListBean.getFulfilNum();
				fulfilNum++;
				RwMainBean updateRwBean = new RwMainBean();
				updateRwBean.setId(rwBean.getId());
				updateRwBean.setFulfilNum(fulfilNum);
				if(fulfilNum == (rwBean.getMaxNum()*rwBean.getRwCount())){
					updateRwBean.setStatus(1);
				}
				new JdbcDao<RwMainBean>().save(conn, updateRwBean);
			} else { //审核不通过时扣分
				TeacherInfo updateUser = new TeacherInfo();
				int jf = info.getJf()-KF_SHBTG;
				if(jf < 0) jf = 0;
				updateUser.setId(info.getId());
				updateUser.setJf(jf);
				new JdbcDao<TeacherInfo>().save(conn, updateUser);
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
	
	/**
	 * 清理超时未完成的任务
	 * @throws Exception
	 */
	public void clearCswwcRw() throws Exception {
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			RwDao dao = new RwDao();
			List<UserFulfilRwBean> list = dao.cswwcRw(conn, TIMEOUT_CSWWC);
			if(list != null && list.size() > 0){
				logger.info("------检测到【"+list.size()+"】个用户领取的任务已经超时未完成------");
				conn.setAutoCommit(false);
				for(UserFulfilRwBean bean : list){
					UserFulfilRwBean updateBean = new UserFulfilRwBean();
					updateBean.setId(bean.getId());
					updateBean.setStatus(4);
					dao.save(conn, updateBean);
					
					String userNo = bean.getUserNo();
					TeacherInfo info = new UserService().getTeacherByUserNo(conn, userNo);
					TeacherInfo updateUser = new TeacherInfo();
					int jf = info.getJf()-KF_CSWWC;
					if(jf < 0) jf = 0;
					updateUser.setId(info.getId());
					updateUser.setJf(jf);
					new JdbcDao<TeacherInfo>().save(conn, updateUser);
				}
				conn.commit();
				logger.info("------已将【"+list.size()+"】个用户领取的任务设置为超时未完成，且扣取积分------");
			} else {
				logger.info("------没有检测到超时未完成的任务------");
			}
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
