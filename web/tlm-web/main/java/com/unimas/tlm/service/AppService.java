package com.unimas.tlm.service;

import java.sql.Connection;

import org.springframework.stereotype.Service;

import com.unimas.jdbc.DBFactory;
import com.unimas.tlm.bean.user.StudentInfo;
import com.unimas.tlm.dao.JdbcDao;

@Service
public class AppService {

	/**
     * 保存裁剪过的头像图片
     * @param id
     * @param txImg
     * @throws Exception
     */
    public void saveStuTxImg(int id, String txImg) throws Exception{
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			conn.setAutoCommit(false);
			StudentInfo info = new StudentInfo();
			info.setId(id);
			info.setTx(txImg.getBytes());
			new JdbcDao<StudentInfo>().save(conn, info);
			conn.commit();
		} catch(Exception e){
			if(conn != null){
				try { conn.rollback(); } catch(Exception t){}
			}
			throw e;
		} finally {
			DBFactory.close(conn, null, null);
		}
	}
	
}
