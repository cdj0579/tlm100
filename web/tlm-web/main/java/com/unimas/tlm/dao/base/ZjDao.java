package com.unimas.tlm.dao.base;

import java.sql.Connection;
import java.util.List;

import com.unimas.jdbc.DBFactory;
import com.unimas.jdbc.handler.SelectHandler;
import com.unimas.jdbc.handler.entry.SelectSqlModal;
import com.unimas.tlm.bean.JdbcBean;
import com.unimas.tlm.bean.base.ZjBean;
import com.unimas.tlm.bean.zs.XtBean;
import com.unimas.tlm.bean.zs.XtZsdRef;
import com.unimas.tlm.bean.zs.ZsdBean;
import com.unimas.tlm.bean.zs.ZsdContentBean;
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
	
	private void saveBean(Connection conn, ZjBean bean) throws Exception {
		ZjBean p = (ZjBean)super.save(conn, bean);
		List<ZsdBean> zsdes = bean.getZsdes();
		if(zsdes != null && zsdes.size() > 0){
			for(ZsdBean zsd : zsdes){
				zsd.setId(-1);
				zsd.setZjId(p.getId());
				zsd.setDqId(p.getDqId());
				super.save(conn, zsd);
				List<ZsdContentBean> contents = zsd.getContents();
				if(contents != null && contents.size() > 0){
					for(ZsdContentBean content : contents){
						content.setId(-1);
						content.setPid(zsd.getId());
						super.save(conn, content);
					}
				}
			}
		}
		List<ZjBean> children = bean.getChildren();
		if(children != null && !children.isEmpty()){
			for(ZjBean b : children){
				b.setPid(p.getId());
				this.saveBean(conn, b);
			}
		}
	}
	
	public void save(List<ZjBean> list, List<XtBean> xtes) throws Exception {
		if(list == null || list.isEmpty()) return;
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			conn.setAutoCommit(false);
			for(ZjBean bean : list){
				this.saveBean(conn, bean);
			}
			if(xtes != null && xtes.size() > 0){
				for(XtBean xt : xtes){
					super.save(conn, xt);
					List<XtZsdRef> refs = xt.getRefs();
					if(refs != null && refs.size() > 0){
						for(XtZsdRef ref : refs){
							if(ref.getZsd() != null){
								XtZsdRef tmp = new XtZsdRef();
								tmp.setXtId(xt.getId());
								tmp.setZsdId(ref.getZsd().getId());
								super.save(conn, tmp);
							}
						}
					}
				}
			}
			conn.commit();
		} catch(Exception e){
			if(conn != null){
				try {
					conn.rollback();
				} catch (Exception e2) {}
			}
			throw e;
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
