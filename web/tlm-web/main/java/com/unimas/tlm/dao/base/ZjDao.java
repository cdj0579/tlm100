package com.unimas.tlm.dao.base;

import java.sql.Connection;
import java.util.List;

import com.unimas.jdbc.DBFactory;
import com.unimas.jdbc.handler.SelectHandler;
import com.unimas.jdbc.handler.entry.SelectSqlModal;
import com.unimas.tlm.bean.JdbcBean;
import com.unimas.tlm.bean.base.ZjBean;
import com.unimas.tlm.dao.JdbcDao;

public class ZjDao extends JdbcDao<ZjBean> {

	@Override
	public Object save(JdbcBean b) throws Exception {
		ZjBean bean = (ZjBean)b;
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			checkExsits(conn, bean);
			return super.save(conn, b);
		} finally {
			DBFactory.close(conn, null, null);
		}
	}
	
	@Override
	public List<?> query(JdbcBean b) throws Exception {
		ZjBean bean = (ZjBean)b;
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			SelectSqlModal<ZjBean> sm = SelectHandler.createSelectModal(conn, bean);
			sm.addOrder(ZjBean.class, "xh", SelectSqlModal.Order.ASC);
			return SelectHandler.executeSelect(conn, sm);
		} finally {
			DBFactory.close(conn, null, null);
		}
	}

	public void checkExsits(Connection conn, ZjBean bean) throws Exception {
		ZjBean cond = new ZjBean();
		cond.setBbId(bean.getBbId());
		cond.setDqId(bean.getDqId());
		cond.setKmId(bean.getKmId());
		cond.setNjId(bean.getNjId());
		cond.setXq(bean.getXq());
		cond.setPid(bean.getPid());
		String bm = bean.getBm();
		SelectSqlModal<ZjBean> sm = SelectHandler.createSelectModal(conn, cond);
		sm.addCondition(ZjBean.class, "bm", "=", bm);
		sm.addCondition(ZjBean.class, "id", "<>", bean.getId());
		List<ZjBean> list = SelectHandler.executeSelect(conn, sm);
		if(list != null && list.size() > 0){
			throw new Exception("章节编目["+bm+"]已存在！");
		}
		String name = bean.getName();
		sm = SelectHandler.createSelectModal(conn, cond);
		sm.addCondition(ZjBean.class, "name", "=", name);
		sm.addCondition(ZjBean.class, "id", "<>", bean.getId());
		list = SelectHandler.executeSelect(conn, sm);
		if(list != null && list.size() > 0){
			throw new Exception("章节名称["+name+"]已存在！");
		}
	}

}
