package com.unimas.txl.service;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Workbook;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.unimas.common.file.PropertyUtils;
import com.unimas.common.qrcode.QrcodeUtils;
import com.unimas.common.util.StringUtils;
import com.unimas.common.util.ExcelUtil.WorkbookBuffer;
import com.unimas.jdbc.DBFactory;
import com.unimas.txl.bean.LryBean;
import com.unimas.txl.bean.LxrBean;
import com.unimas.txl.bean.fenpei.LxrQianyueBean;
import com.unimas.txl.dao.LryDao;
import com.unimas.txl.dao.LxrDao;
import com.unimas.web.auth.AuthRealm.ShiroUser;

public class LxrService {
	
	public List<LxrBean> queryOnAdmin(int status, int xuexiaoId, String dqId, int nj, int bj, ShiroUser user) throws Exception {
		return new LxrDao().queryOnAdmin(user.getJigouId(), status, xuexiaoId, dqId, nj, bj);
	}
	
	public List<LxrBean> query(int xuexiaoId, String dqId, int nj, int bj, ShiroUser user) throws Exception {
		return query(user.getJigouId(), user.getUserId(), xuexiaoId, dqId, nj, bj);
	}
	
	@SuppressWarnings("unchecked")
	public List<LxrBean> query(int jigouId, int lryId, int xuexiaoId, String dqId, int nj, int bj) throws Exception {
		LxrBean bean = new LxrBean();
		bean.setXuexiaoId(xuexiaoId);
		if(StringUtils.isNotEmpty(dqId)){
			bean.setDqId(dqId);
		}
		bean.setNianji(nj);
		bean.setBanji(bj);
		bean.setIsDel(0);
		bean.setJigouId(jigouId);
		bean.setLryId(lryId);
		return (List<LxrBean>) new LxrDao().query(bean);
	}
	
	public List<LxrBean> queryNofp(int jigouId, String xuexiaoId, String dqId, String nj, String bj, int lryId) throws Exception {
		return new LxrDao().queryNofp(jigouId, xuexiaoId, dqId, nj, bj, lryId);
	}
	
	public void add(String name, int xb, int xuexiaoId, String dqId, int nj, int bj, String lianxiren
			, String phone, ShiroUser user) throws Exception {
		LxrBean bean = new LxrBean();
		bean.setJigouId(user.getJigouId());
		bean.setLryId(user.getUserId());
		bean.setXingming(name);
		bean.setXingbie(xb);
		bean.setXuexiaoId(xuexiaoId);
		bean.setDqId(dqId);
		bean.setNianji(nj);
		bean.setBanji(bj);
		bean.setLianxiren(lianxiren);
		bean.setPhone(phone);
		bean.setIsDel(0);
		bean.setCishu(0);
		new LxrDao().save(bean);
	}
	
	public void update(int id, String name, int xb, int xuexiaoId, String dqId, int nj, int bj, String lianxiren
			, String phone, ShiroUser user) throws Exception {
		LxrBean bean = new LxrBean();
		bean.setJigouId(user.getJigouId());
		bean.setId(id);
		bean.setXingming(name);
		bean.setXingbie(xb);
		bean.setXuexiaoId(xuexiaoId);
		bean.setDqId(dqId);
		bean.setNianji(nj);
		bean.setBanji(bj);
		bean.setLianxiren(lianxiren);
		bean.setPhone(phone);
		new LxrDao().save(bean);
	}
	
	public void delete(int id) throws Exception {
		Connection conn = null;
		try {
			conn = DBFactory.getConn();
			LxrDao dao = new LxrDao();
			dao.checkStatus(conn, id);
			LxrBean bean = new LxrBean();
			bean.setId(id);
			bean.setIsDel(1);
			dao.save(conn, bean);
		} finally {
			DBFactory.close(conn, null, null);
		}
	}
	
	public void cancerQianyue(int id) throws Exception {
		LxrDao dao = new LxrDao();
		LxrQianyueBean bean = new LxrQianyueBean();
		bean.setLxrId(id);
		dao.delete(bean);
	}
	
	public ByteArrayOutputStream getLxrzcQrcode(int lryId) throws Exception {
		LryBean bean = (LryBean)new LryDao().getById(lryId, LryBean.class);
		String config_path = LxrService.class.getClassLoader().getResource("config.properties").getFile();
		Map<String, String> config = PropertyUtils.readProperties(config_path);
		String baseUrl = MessageFormat.format(config.get("lxrzc.url"), bean.getJigouId(), lryId);
		return new QrcodeUtils().genQrcodeImage(baseUrl, false);
	}
	
	public void createSampleExcel(String filename, String path) throws Exception {
		WorkbookBuffer wb = new WorkbookBuffer(path + filename);
		
		Map<String, String> header = Maps.newLinkedHashMap();
		String[] englishTitle = { "xingming", "xingbie", "xuexiaoId", "dqId", "nianji", "banji", "lianxiren", "phone"};
		String[] chineseTitle = { "学生姓名", "学生性别（1：女；2：男）", "学校ID", "地区Id", "年级", "班级", "联系人", "联系电话"};

		for (int i = 0; i < englishTitle.length; i++) {
			header.put(englishTitle[i], chineseTitle[i]);
		}
		List<Map<String, Object>> datas = Lists.newArrayList();
		Map<String, Object> data = Maps.newHashMap();
		data.put("xingming", "张三");
		data.put("xingbie", 2);
		data.put("xuexiaoId", 2);
		data.put("dqId", "330501");
		data.put("nianji", 7);
		data.put("banji", 1);
		data.put("lianxiren", "张三2");
		data.put("phone", "13543254324");
		datas.add(data);
		wb.createSheet("联系人列表", 0, header, datas);
		
		datas = new DicService().get("txl_xuexiao", "id", "xuexiaoming", null, null, null);
		header = Maps.newLinkedHashMap();
		englishTitle = new String[]{ "id", "name"};
		chineseTitle = new String[]{ "学校ID", "学校名"};

		for (int i = 0; i < englishTitle.length; i++) {
			header.put(englishTitle[i], chineseTitle[i]);
		}
		wb.createSheet("学校列表", 1, header, datas);
		
		datas = new DicService().get("xzqh", "code", "name", null, "pid", "330500");
		header = Maps.newLinkedHashMap();
		englishTitle = new String[]{ "id", "name"};
		chineseTitle = new String[]{ "地区ID", "地区名"};

		for (int i = 0; i < englishTitle.length; i++) {
			header.put(englishTitle[i], chineseTitle[i]);
		}
		wb.createSheet("地区列表", 2, header, datas);
		
		wb.write();
	}
	
	public void uploadLxrExcel(InputStream is, ShiroUser user) throws Exception {
		List<LxrBean> list = readLxrExcel(is);
		new LxrDao().save(list, user.getJigouId(), user.getUserId());
	}
	
	private List<LxrBean> readLxrExcel(InputStream is) throws Exception {
		try {
			Workbook rwb = Workbook.getWorkbook(is);
			jxl.Sheet rs = rwb.getSheet(0); // 读取第一个工作表的数据

			int num = rs.getRows(); // 得到此excel有多少行..
			
			List<LxrBean> list = null;
			if(num > 0){
				list = Lists.newArrayList();
				if(num > 1){
					for (int i = 1; i < num; i++) {
						Cell[] cells = rs.getRow(i); // 得到第i行的数据..返回cell数组
						LxrBean bean = new LxrBean();
						for(int j=0;j<cells.length;j++){
							Cell cell = cells[j];
							String name = cell.getContents(); // 得到第i行.第一列的数据.
							name = name.trim();
							switch (j) {
								case 0:{
									if(StringUtils.isNotEmpty(name)){
										bean.setXingming(name);
									}
									break;
								}
								case 1:{
									try {
										bean.setXingbie(Integer.parseInt(name));
									} catch(Exception e){}
									break;
								}
								case 2:{
									try {
										bean.setXuexiaoId(Integer.parseInt(name));
									} catch(Exception e){}
									break;
								}
								case 3:{
									if(StringUtils.isNotEmpty(name)){
										bean.setDqId(name);
									}
									break;
								}
								case 4:{
									try {
										bean.setNianji(Integer.parseInt(name));
									} catch(Exception e){}
									break;
								}
								case 5:{
									try {
										bean.setBanji(Integer.parseInt(name));
									} catch(Exception e){}
									break;
								}
								case 6:{
									if(StringUtils.isNotEmpty(name)){
										bean.setLianxiren(name);
									}
									break;
								}
								case 7:{
									if(StringUtils.isNotEmpty(name)){
										bean.setPhone(name);
									}
									break;
								}
								default: break;
							}
						}
						if(bean.getLianxiren() == null || bean.getPhone() == null){
							continue;
						} else {
							list.add(bean);
						}
					}
				}
			}
			return list;
		} catch(Exception e){
			throw e;
		}
	}
	
	public void writeSampleExcel(OutputStream out) throws Exception {
		WorkbookBuffer wb = new WorkbookBuffer(out);
		
		Map<String, String> header = Maps.newLinkedHashMap();
		String[] englishTitle = { "xingming", "xingbie", "xuexiaoId", "dqId", "nianji", "banji", "lianxiren", "phone"};
		String[] chineseTitle = { "学生姓名", "学生性别（1：女；2：男）", "学校ID", "地区Id", "年级", "班级", "联系人", "联系电话"};

		for (int i = 0; i < englishTitle.length; i++) {
			header.put(englishTitle[i], chineseTitle[i]);
		}
		List<Map<String, Object>> datas = Lists.newArrayList();
		Map<String, Object> data = Maps.newHashMap();
		data.put("xingming", "张三");
		data.put("xingbie", 2);
		data.put("xuexiaoId", 2);
		data.put("dqId", "330501");
		data.put("nianji", 7);
		data.put("banji", 1);
		data.put("lianxiren", "张三2");
		data.put("phone", "13543254324");
		datas.add(data);
		wb.createSheet("联系人列表", 0, header, datas);
		
		datas = new DicService().get("txl_xuexiao", "id", "xuexiaoming", null, null, null);
		header = Maps.newLinkedHashMap();
		englishTitle = new String[]{ "id", "name"};
		chineseTitle = new String[]{ "学校ID", "学校名"};

		for (int i = 0; i < englishTitle.length; i++) {
			header.put(englishTitle[i], chineseTitle[i]);
		}
		wb.createSheet("学校列表", 1, header, datas);
		
		datas = new DicService().get("xzqh", "code", "name", null, "pid", "330500");
		header = Maps.newLinkedHashMap();
		englishTitle = new String[]{ "id", "name"};
		chineseTitle = new String[]{ "地区ID", "地区名"};

		for (int i = 0; i < englishTitle.length; i++) {
			header.put(englishTitle[i], chineseTitle[i]);
		}
		wb.createSheet("地区列表", 2, header, datas);
		
		wb.write();
	}
	
	public static void main(String[] args) throws Exception {
		String filename = "联系人模板.xls";
		String path = "D://";
		//new LxrService().createSampleExcel(filename, path);
		//ExcelUtil.readXls(path+filename);
		List<LxrBean> list = new LxrService().readLxrExcel(new FileInputStream(path+filename));
		System.out.println(list);
	}

}
