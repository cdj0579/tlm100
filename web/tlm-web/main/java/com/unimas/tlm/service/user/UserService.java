package com.unimas.tlm.service.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authc.AuthenticationException;
import org.springframework.stereotype.Service;

import com.unimas.common.md5.MD5;
import com.unimas.common.random.RandomNumber;
import com.unimas.jdbc.DBFactory;
import com.unimas.jdbc.handler.ResultSetHandler;
import com.unimas.tlm.bean.user.Account;
import com.unimas.tlm.bean.user.StudentInfo;
import com.unimas.tlm.bean.user.TeacherInfo;
import com.unimas.tlm.dao.JdbcDao;
import com.unimas.web.auth.AuthRealm.ShiroUser;

@Service
public class UserService {
	
	public String saveAccount(Connection conn, String username, String password, String type) throws Exception{
		String userNo = createUserNo(conn, type);
		Account acc = new Account();
		acc.setUserNo(userNo);
		acc.setUsername(username);
		acc.setPassword(MD5.getMD5(password));
		new JdbcDao<Account>().save(conn, acc);
		return userNo;
	}
	
	public String saveAccount(String username, String password, String type) throws Exception{
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			return saveAccount(conn, username, password, type);
		} finally {
			DBFactory.close(conn, null, null);
		}
	}
	
	public String verify(Connection conn, String username, String password) throws Exception{
		Map<String, Object> map = getAccountByUsernamePassword(conn, username, password);
		return (String)map.get("userNo");
	}
	
	public String verify(String username, String password) throws Exception{
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			return verify(conn, username, password);
		} catch(Exception e){
			throw e;
		} finally {
			DBFactory.close(conn, null, null);
		}
	}
	
	private Map<String, Object> getAccountByUsernamePassword(Connection conn, String username, String password) throws Exception{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "select password,user_no as userNo,id from account where username=?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			rs = stmt.executeQuery();
			List<Map<String, Object>> list = ResultSetHandler.listMap(rs);
			if(list == null || list.size() == 0){
				throw new AuthenticationException("用户不存在！");
			}
			Map<String, Object> map = list.get(0);
			String pwd = (String)map.get("password");
			if(!pwd.equals(MD5.getMD5(password))){
				throw new AuthenticationException("输入的密码不正确！");
			}
			return map;
		} finally {
			DBFactory.close(null, stmt, rs);
		}
	}
	
	public void updatePwd(String password, String newPassword, ShiroUser user) throws Exception{
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			Map<String, Object> map = null;
			try {
				map = getAccountByUsernamePassword(conn, user.getLoginName(), password);
			} catch(Exception e){
				throw new Exception("输入的旧密码不正确！");
			}
			Account acc = new Account();
			acc.setId((Integer)map.get("id"));
			acc.setPassword(MD5.getMD5(newPassword));
			new JdbcDao<Account>().save(conn, acc);
		} catch(Exception e){
			throw e;
		} finally {
			DBFactory.close(conn, null, null);
		}
	}
	
	protected String createUserNo(Connection conn, String type) throws Exception{
		String userNo = null;
		if("admin".equals(type)){
			userNo = "admin";
			return userNo;
		} else if("student".equals(type)){
			userNo = "S"+RandomNumber.getRandomNumber(9);
		} else if("teacher".equals(type)){
			userNo = "T"+RandomNumber.getRandomNumber(9);
		} else {
			throw new Exception("错误的用户类型["+type+"]！");
		}
		while(!checkUserNo(conn, userNo)){
			userNo = createUserNo(conn, type);
		}
		return userNo;
	}
	
	private boolean checkUserNo(Connection conn, String userNo) throws Exception{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "select count(*) from account where user_no=?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, userNo);
			rs = stmt.executeQuery();
			int count = ResultSetHandler.toInt(rs);
			if(count > 0){
				return false;
			}
		} finally {
			DBFactory.close(null, stmt, rs);
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public List<StudentInfo> queryStudents(String key) throws Exception{
		return (List<StudentInfo>)new JdbcDao<StudentInfo>().query(new StudentInfo());
	}
	
	@SuppressWarnings("unchecked")
	public List<TeacherInfo> queryTeachers(String key) throws Exception{
		return (List<TeacherInfo>)new JdbcDao<TeacherInfo>().query(new TeacherInfo());
	}
	
	public void registerTeacher(String username, String password, String name, String skdz, int kmId, 
			byte[] jszgz, byte[] djzs, byte[] ryzs) throws Exception{
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			conn.setAutoCommit(false);
			String userNo = saveAccount(conn, username, password, "teacher");
			TeacherInfo info = new TeacherInfo();
			info.setKmId(kmId);
			info.setName(name);
			info.setUserNo(userNo);
			info.setSkdz(skdz);
			info.setJszgz(jszgz);
			info.setDjzs(djzs);
			info.setRyzs(ryzs);
			new JdbcDao<TeacherInfo>().save(conn, info);
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
	
	public void registerStudent(String username, String password) throws Exception{
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			conn.setAutoCommit(false);
			String userNo = saveAccount(conn, username, password, "student");
			StudentInfo info = new StudentInfo();
			info.setUserNo(userNo);
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
	
	public void saveStudentInfo(String studentName, String parentName, String contact, int mbxxId, 
			int njId, ShiroUser user) throws Exception {
		StudentInfo info = new StudentInfo();
		info.setId(user.getUserId());
		info.setStudentName(studentName);
		info.setParentName(parentName);
		info.setContact(contact);
		info.setMbxxId(mbxxId);
		info.setNjId(njId);
		new JdbcDao<StudentInfo>().save(info);
	}
	
	public static void main(String[] args) throws Exception {
		/*String userNo = new UserService().saveAccount("brees", "tlm2017", "student");
		System.out.println(userNo);*/
		/*byte[] jszgz = FileUtils.readFileToByteArray(new File("D:\\var\\330100000001.png"));
		byte[] djzs = FileUtils.readFileToByteArray(new File("D:\\var\\330100000002.png"));
		byte[] ryzs = FileUtils.readFileToByteArray(new File("D:\\var\\330100000003.png"));
		System.out.println(jszgz.length);
		System.out.println(djzs.length);
		System.out.println(ryzs.length);*/
		/*Connection conn = null;
		try {
			conn = DBFactory.getConn();
			TeacherInfo info = new UserService().getTeacherByUserNo(conn, "T973643720");
			FileUtils.writeByteArrayToFile(new File("D:\\var\\test.png"), info.getJszgz());
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBFactory.close(conn, null, null);
		}*/
		//new UserService().registerStudent("test2", "123456");
		//System.out.println(MD5.getMD5("7516328"));
		/*int l = 100;
		for(int i=2;i<=l;i++){
			int kmId = 1;
			if(i>=1 && i<=20){
				 kmId = 1;
			} else if(i>=21 && i<=40){
				 kmId = 2;
			} else if(i>=41 && i<=60){
				 kmId = 3;
			} else if(i>=61 && i<=80){
				 kmId = 4;
			} else if(i>=81 && i<=100){
				 kmId = 5;
			}
			String num = String.format("%03d", i);
			new UserService().registerTeacher("vip"+num, "88888888", "VIP"+num, null, kmId, null, null, null);
		}*/
		System.out.println(MD5.getMD5("88888888"));
	}
	
	@SuppressWarnings("unchecked")
	public TeacherInfo getTeacherByUserNo(Connection conn, String userNo) throws Exception {
		TeacherInfo info = new TeacherInfo();
		info.setUserNo(userNo);
		List<TeacherInfo> list = (List<TeacherInfo>)new JdbcDao<TeacherInfo>().query(conn, info);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public StudentInfo getStudentByUserNo(Connection conn, String userNo) throws Exception {
		StudentInfo info = new StudentInfo();
		info.setUserNo(userNo);
		List<StudentInfo> list = (List<StudentInfo>)new JdbcDao<StudentInfo>().query(conn, info);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

}
