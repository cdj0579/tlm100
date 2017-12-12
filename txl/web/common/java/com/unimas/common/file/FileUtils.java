package com.unimas.common.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.apache.log4j.Logger;

import com.unimas.common.util.StringUtils;

/**
 * @中文类名:FileUtils
 * @开发人员:rq
 * @开发日期:Sep 19, 2008
 * @功能说明:
 */
public class FileUtils {
	protected static final Logger log = Logger.getLogger(FileUtils.class);

	public static final String S_FileSign = "filesign";

	private static final int I_maxCount = 5;

	private static int I_count = 0;

	private static final byte[] b_Space = {32};// 空格符

	private static final byte[] b_Tab = {9};// tab符

	private static final byte[] b_WinEnter = {13, 10};// 回车键符 windwos版

	private static final byte[] b_LinuxEnter = {10, 13};// 回车键符 linux版

	private static final byte[] b_MacEnter = {13};// 回车键符 Mac os版

	private static final byte end = -1;// 结束符

	/**
	 * 功能说明:下载blob字段
	 * 
	 * @param name
	 * @param response
	 * @throws Exception
	 */
	public static void downloadBlob(String filepath, HttpServletResponse response) throws Exception {
		download(filepath, response);
	}

	/**
	 * 功能说明:下载clob字段
	 * 
	 * @param name
	 * @param response
	 * @throws Exception
	 */
	public static void downloadClob(String filepath, HttpServletResponse response) throws Exception {
		download(filepath, response);
	}

	/**
	 * 功能说明:文件下载
	 * 
	 * @param filepath
	 * @param response
	 * @throws Exception
	 */
	public synchronized static void download(String filepath, HttpServletResponse response) throws Exception {
		// 得到文件名filename
		File tfile = new File(filepath);
		if (!tfile.exists()) {
			throw new Exception(filepath + "文件不存在！");
		}
		if (!tfile.isFile()) {
			throw new Exception(filepath + "不是文件！");
		}
		String filename = tfile.getName();
		tfile = null;
		// header内不能用ISO-8859-1以外的编码的字符串，如GBK，需先将其转成ISO-8859-1编码
		// 页面弹出保存对话框时会按照页面编码（此为GBK）对字符串（此为文件名）进行自动转化
		filename = new String(filename.getBytes("GBK"), "ISO-8859-1");
		response.setContentType("application/octet-stream");
		response.setHeader("Content-disposition", "attachment; filename=" + filename);
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(filepath));
			bos = new BufferedOutputStream(response.getOutputStream());
			bos.flush();
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (final IOException e) {
			System.out.println("File Download IOException.\n" + e);
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
	}

	/**
	 * 功能说明:上传文件 利用common-fileupload.jar的方法 返回上传后存储的文件的文件名数组
	 * 
	 * @param request
	 * @param types
	 * @return
	 * @throws Exception
	 */
	public synchronized static Map uploadFile(HttpServletRequest request, String[] types, String uploadDir) throws Exception {
		Map map = new HashMap();
		try {
			RequestContext ctx = new ServletRequestContext(request);
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(1000 * 1024); // 设置保存到内存中的大小：1M
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(500 * 1024 * 1024);// 设置最大上传文件的大小500M
			List items = upload.parseRequest(ctx);// 解析请求里的上传文件单元
			if (items != null && items.size() > 0) {
				Iterator it = items.iterator();
				List list = new ArrayList();
				while (it.hasNext()) {
					FileItem item = (FileItem) it.next();
					boolean isForm = item.isFormField();// 是否是表单域
					if (isForm) {
						String key = StringUtils.toGbk(item.getFieldName());
						String value = StringUtils.toGbk(item.getString());
						map.put(key, value);// 添加表单参数值
					} else {
						// 如果不适表单域，则是文件上传
						String fileName = item.getName();// 获取上传的文件名
						// 扩展名
						String fileExtName = fileName.substring(fileName.lastIndexOf("."));
						map.put("filename", fileName);
						if (types != null && !types.equals("")) {
							boolean isExtName = false;
							for (int i = 0; i < types.length; i++) {
								if (types[i].equalsIgnoreCase(fileExtName)) {
									isExtName = true;
								}
							}
							if (!isExtName) {
								throw new Exception("未定义的上传文件类型[" + fileExtName + "]！");
							}
						}
						// 到达这个时候，上传文件所必须的条件都满足了，可以上传了，首先要给上传的文件起个名字
						String writeFile = "" + System.currentTimeMillis();// 以时间起个名字。
						writeFile = uploadDir + writeFile;// 文件的全路径
						File file = new File(writeFile + fileExtName);
						System.out.println("上传文件---------" + file.getPath());
						item.write(file);// 上传文件
						System.out.println("上传文件---------" + file.getPath());
						list.add(file.getPath());
					}
				}
				map.put(S_FileSign, list);// 记录最大文件数
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("上传文件失败：" + e.getMessage());
			throw new Exception(e);
		} finally {

		}
		return map;
	}
	public synchronized static String getFile(HttpServletRequest request, String[] types, String uploadDir) throws Exception {
		Map map = new HashMap();
		String contex="";
		try {
			RequestContext ctx = new ServletRequestContext(request);
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(1000 * 1024); // 设置保存到内存中的大小：1M
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(500 * 1024 * 1024);// 设置最大上传文件的大小500M
			List items = upload.parseRequest(ctx);// 解析请求里的上传文件单元
			if (items != null && items.size() > 0) {
				Iterator it = items.iterator();
				List list = new ArrayList();
				while (it.hasNext()) {
					FileItem item = (FileItem) it.next();
					boolean isForm = item.isFormField();// 是否是表单域
					if (isForm) {
						String key = StringUtils.toGbk(item.getFieldName());
						String value = StringUtils.toGbk(item.getString());
						map.put(key, value);// 添加表单参数值
					} else {
						// 如果不适表单域，则是文件上传
						String fileName = item.getName();// 获取上传的文件名
						// 扩展名
						String fileExtName = fileName.substring(fileName.lastIndexOf("."));
						map.put("filename", fileName);
						if (types != null && !types.equals("")) {
							boolean isExtName = false;
							for (int i = 0; i < types.length; i++) {
								if (types[i].equalsIgnoreCase(fileExtName)) {
									isExtName = true;
								}
							}
							if (!isExtName) {
								throw new Exception("未定义的上传文件类型[" + fileExtName + "]！");
							}
						}
						// 到达这个时候，上传文件所必须的条件都满足了，可以上传了，首先要给上传的文件起个名字
						String writeFile = "" + System.currentTimeMillis();// 以时间起个名字。
						writeFile = uploadDir + writeFile;// 文件的全路径
						File file = new File(writeFile + fileExtName);
						System.out.println("上传文件---------" + file.getPath());
//						item.write(file);// 上传文件
						map.put("file", file);
						System.out.println("上传文件---------" + file.getPath());
//						list.add(file.getPath());
					}
				}
				map.put(S_FileSign, list);// 记录最大文件数
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("上传文件失败：" + e.getMessage());
			throw new Exception(e);
		} finally {

		}
		return contex;
	}

	private static String[] getStr() {
		String result[] = new String[26];
		try {
			byte b = 65;
			for (byte i = 0; i <= 25; i++) {
				result[i] = new String(new byte[] {new Integer(b + i).byteValue()});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private static String[][] getStrs() {
		String result[][] = new String[26][26];
		try {
			byte b = 65;
			for (byte i = 0; i <= 25; i++) {
				for (byte j = 0; j <= 25; j++) {
					result[i][j] =
							new String(new byte[] {new Integer(b + i).byteValue()})
									+ new String(new byte[] {new Integer(b + j).byteValue()});
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private static void test(String str) {
		byte[] b = str.getBytes();
		for (int i = 0; i < b.length; i++) {
			System.out.println(str + " => " + new String(new byte[] {new Integer(b[i] - 17).byteValue()}));
		}

	}

	/**
	 * 功能说明:将Excel行数字转化为 行数 1-0 2-1 3-2
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	private static int intSwitch(int str) throws Exception {
		try {
			if (str >= 1 && str <= 65536) {
				str = str - 1;
			} else {
				throw new Exception(str + "数字超出范围1~65536！");
			}
		} catch (Exception e) {
			throw e;
		}
		return str;
	}

	/**
	 * 功能说明:将Excel列字母转化为列数字A~Z:0~26 AA~VI:27~255
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	private static int charSwitch(String str) throws Exception {
		int result = 0;
		try {
			if (str != null && (str.length() == 1 || str.length() == 2)) {
				byte[] batr = str.toUpperCase().getBytes();
				Integer[] temp = new Integer[batr.length];
				for (int i = 0; i < batr.length; i++) {
					if (batr[i] >= 65 && batr[i] <= 74) {
						Integer itr = batr[i] - 17;
						byte btr = itr.byteValue();
						temp[i] = Integer.parseInt(new String(new byte[] {btr}));
					} else if (batr[i] >= 75 && batr[i] <= 84) {
						Integer itr = batr[i] - 27;
						byte btr = itr.byteValue();
						temp[i] = Integer.parseInt(new String(new byte[] {new Integer(49).byteValue(), btr}));
					} else if (batr[i] >= 85 && batr[i] <= 90) {
						Integer itr = batr[i] - 37;
						byte btr = itr.byteValue();
						temp[i] = Integer.parseInt(new String(new byte[] {new Integer(50).byteValue(), btr}));
					} else {
						throw new Exception("字符" + str + "不是Excel文档相应的列！范围在a~z,aa-vi(不区分大小写) 26个英文字母之间！");
					}
					if (i == 1) {
						temp[i] = temp[i] + (26 * temp[i - 1]) + 26 - temp[i - 1];
					}
				}
				if (str.length() == 1) {
					result = temp[0];
				} else {
					result = temp[0] + temp[1];
				}
				if (result > 255) {
					throw new Exception("您输入的字符[" + str + "]超出Excel最大IV列！");
				}
			} else {
				throw new Exception("字符[" + str + "]不是Excel文档相应的列！正确的范围在a~z,aa-vi(不区分大小写) 26个英文字母之间！");
			}
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	/**
	 * 功能说明:解析excel文件
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public synchronized static Map parseExcelFile(Map map) throws Exception {
		Map result = new HashMap();
		/*InputStream fi = null;
		try {
			// 工作表
			String sheetName = (String) map.get("sheetName");
			// 起始行
			int startRow =
					intSwitch((String) map.get("startRow") == null || map.get("startRow").equals("") ? 1 : Integer
							.valueOf((String) map.get("startRow")));
			// 姓名列
			int nameCell =
					charSwitch(map.get("nameCell") == null || map.get("nameCell").equals("") ? "A" : ((String) map
							.get("nameCell")));
			// 密码列
			int pwdCell =
					charSwitch(map.get("pwdCell") == null || map.get("pwdCell").equals("") ? "B" : ((String) map
							.get("pwdCell")));
			List filelist = (List) map.get(FileUtils.S_FileSign);
			// 文件名
			String filepath = (String) filelist.get(0);
			System.out.println("工作表=>" + sheetName);
			System.out.println("起始行=>" + startRow);
			System.out.println("姓名列=>" + nameCell);
			System.out.println("密码列=>" + pwdCell);
			System.out.println("文件路径=>" + filepath);
			Workbook workbook = null;
			if (filepath != null && !filepath.equals("")) {
				fi = new FileInputStream(filepath);
				workbook = WorkbookFactory.create(fi);
				// if (filepath.endsWith("x")) {
				// workbook = new XSSFWorkbook(fi);// excel 2007 .xlsx 格式
				// } else {
				// workbook = new HSSFWorkbook(fi);// excel 2007格式
				// }
			}
			Sheet sheet = null;
			if (sheetName == null || sheetName.equals("")) {
				// 在Excel文档中，第一章工作表的缺省索引是0
				sheet = workbook.getSheetAt(0);
			} else {
				// 也可以用工作表名
				sheet = workbook.getSheet(sheetName);// 工作表
			}
			if (sheet != null) {
				int allRec = sheet.getLastRowNum();
				for (int i = startRow; i <= allRec; i++) {
					Row hrow = sheet.getRow(i);
					Cell namecell = hrow.getCell((short) nameCell);
					Cell pwdcell = hrow.getCell((short) pwdCell);
					if (namecell != null) {
						String name = getStringCellValue(namecell);
						String pwd = getStringCellValue(pwdcell);
						if (name != null && !name.equals("")) {
							result.put(name, pwd);
						}
					}
				}
			} else {
				throw new Exception(sheetName + "工作表不存在！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			if (fi != null) {
				fi.close();
			}
		}*/
		return result;
	}

	/**
	 * 功能说明:获取单元格数据内容为字符串类型的数据
	 * 
	 * @param cell
	 * @return
	 */
	/*private static String getStringCellValue(Cell cell) {
		if (cell == null) {
			return "";
		}
		String strCell = "";
		switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_STRING:
				strCell = cell.getRichStringCellValue().getString();
				break;
			case HSSFCell.CELL_TYPE_NUMERIC:
				strCell = String.valueOf(new Double(cell.getNumericCellValue()).longValue());
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN:
				strCell = String.valueOf(cell.getBooleanCellValue());
				break;
			case HSSFCell.CELL_TYPE_BLANK:
				strCell = "";
				break;
			default:
				strCell = "";
				break;
		}
		if (strCell.equals("") || strCell == null) {
			return "";
		}
		if (cell == null) {
			return "";
		}
		return strCell.trim();
	}*/

	public synchronized static String[] parseTxt(Map amp) throws Exception {
		String[] result = new String[0];
		try {

		} catch (Exception e) {
			throw new Exception(e);
		}
		return result;
	}

	/**
	 * 功能说明:解析txt文本文件
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public synchronized static Map parseTxtFile(Map map) throws Exception {
		Map result = new HashMap();
		// 用户名标识
		byte[] bnameSign = getByte(map, "nameSign");
		boolean isnameSign = boolByte(bnameSign);
		int inameSign = 0;
		// 用户名结束标识
		byte[] bnameSignEnd = getByte(map, "nameSignEnd");
		boolean isnameSignEnd = boolByte(bnameSignEnd);
		int inameSignEnd = 0;
		// 用户名内容标识
		byte[] bnameContext = getByte(map, "nameContext");
		boolean isnameContext = boolByte(bnameContext);
		int inameContext = 0;
		// 用户名内容结束标识
		byte[] bnameContextEnd = getByte(map, "nameContextEnd");
		boolean isnameContextEnd = boolByte(bnameContextEnd);
		int inameContextEnd = 0;
		// 密码标识
		byte[] bpwdSign = getByte(map, "pwdSign");
		boolean ispwdSign = boolByte(bpwdSign);
		int ipwdSign = 0;
		// 密码结束标识
		byte[] bpwdSignEnd = getByte(map, "pwdSignEnd");
		boolean ispwdSignEnd = boolByte(bpwdSignEnd);
		int ipwdSignEnd = 0;
		// 密码内容标识
		byte[] bpwdContext = getByte(map, "pwdContext");
		boolean ispwdContext = boolByte(bpwdContext);
		int ipwdContext = 0;
		// 密码内容结束标识
		byte[] bpwdContextEnd = getByte(map, "pwdContextEnd");
		boolean ispwdContextEnd = boolByte(bpwdContextEnd);
		int ipwdContextEnd = 0;

		int iresult = 0;
		boolean firstRow = true;// 标题行是否保留
		if (!isnameSign) {
			firstRow = false;
		}

		byte[] isend = new byte[0];
		if (isnameContextEnd && !ispwdContextEnd) {
			bpwdContextEnd = b_LinuxEnter;
			ispwdContextEnd = true;
			isend = bnameContextEnd;
			// bpwdContextEnd = bnameContextEnd;
		}

		if (!isnameSign && !isnameSignEnd && !isnameContext && !isnameContextEnd && !ispwdSign && !ispwdSignEnd
				&& !ispwdContext && !ispwdContextEnd) {
			bnameContextEnd = b_Space;
			isnameContextEnd = true;
			bpwdContextEnd = b_LinuxEnter;
			ispwdContextEnd = true;
			firstRow = false;
		}
		boolean init = false;
		FileInputStream fi = null;
		BufferedInputStream fis = null;

		try {
			List filelist = (List) map.get(FileUtils.S_FileSign);
			// 文件名
			String filepath = (String) filelist.get(0);
			File file = new File(filepath);
			fi = new FileInputStream(file);

			long fileLength = FileUtils.showAvailableBytes(fi);
			if (fileLength > 0L) {
				fis = new BufferedInputStream(fi);
				byte[] b = new byte[(int) fileLength + 2];
				while (fis.read(b) != -1) {
				}
				b[b.length - 2] = b_LinuxEnter[1];// 未尾处加上回车标识
				b[b.length - 1] = b_LinuxEnter[0];// 未尾处加上回车标识
				List key = new ArrayList();
				List value = new ArrayList();
				System.out.println("源长度=>" + b.length);
				for (int i = 0; i < b.length; i++) {
					// 检查用户名标识
					if (isnameSign && inameSign < bnameSign.length) {
						System.out.println("用户名标识次数=>" + inameSign);
						System.out.println("用户名标识内容=>" + bnameSign[inameSign]);
						System.out.println("源内容=>" + b[i]);
						if (inameSign == 0 && !isEnter(bnameSign[inameSign]) && isEnter(b[i])) {
							continue;
						}
						if (inameSign == 0 && !isSpace(bnameSign[inameSign]) && isSpace(b[i])) {
							continue;
						}
						if (inameSign == 0 && !isTab(bnameSign[inameSign]) && isTab(b[i])) {
							continue;
						}
						if (bnameSign[inameSign] == b[i]) {
							inameSign++;
							continue;
						} else {
							inameSign = 0;
							continue;
						}
					}
					// 检查用户名结束标识
					if (inameSign == bnameSign.length && isnameSignEnd && inameSignEnd < bnameSignEnd.length) {
						System.out.println("用户名结束标识次数=>" + inameSignEnd);
						System.out.println("用户名结束标识内容=>" + bnameSignEnd[inameSignEnd]);
						System.out.println("源内容=>" + b[i]);
						if (!isEnter(bnameSignEnd[inameSignEnd]) && isEnter(b[i])) {
							continue;
						}
						if (!isSpace(bnameSignEnd[inameSignEnd]) && isSpace(b[i])) {
							continue;
						}
						if (!isTab(bnameSignEnd[inameSignEnd]) && isTab(b[i])) {
							continue;
						}
						if (bnameSignEnd[inameSignEnd] == b[i]) {
							inameSignEnd++;
							continue;
						} else {
							inameSignEnd = 0;
							continue;
						}
					}
					// 检查用户名内容标识
					if (inameSignEnd == bnameSignEnd.length && isnameContext && inameContext < bnameContext.length) {
						System.out.println("用户名内容标识次数=>" + inameContext);
						System.out.println("用户名内容标识内容=>" + bnameContext[inameContext]);
						System.out.println("源内容=>" + b[i]);
						if (!isEnter(bnameContext[inameContext]) && isEnter(b[i])) {
							continue;
						}
						if (!isSpace(bnameContext[inameContext]) && isSpace(b[i])) {
							continue;
						}
						if (!isTab(bnameContext[inameContext]) && isTab(b[i])) {
							continue;
						}
						if (bnameContext[inameContext] == b[i]) {
							inameContext++;
							continue;
						} else {
							inameContext = 0;
							continue;
						}
					}
					System.out.println("inameContext == bnameContext.length => "
							+ (inameContext == bnameContext.length));
					System.out.println("inameContextEnd < bnameContextEnd.length => "
							+ (inameContextEnd < bnameContextEnd.length));
					System.out.println("inameContextEnd => " + inameContextEnd);
					System.out.println("bnameContextEnd.length => " + bnameContextEnd.length);
					if (inameContextEnd < bnameContextEnd.length)
						System.out.println("bnameContextEnd[inameContextEnd] != b[i] => "
								+ (bnameContextEnd[inameContextEnd] != b[i]));
					// 获取用户内容
					if (inameContext == bnameContext.length && inameContextEnd < bnameContextEnd.length
							&& bnameContextEnd[inameContextEnd] != b[i]) {
						if (isEnter(b[i]) && !isEnter(bnameContextEnd[inameContextEnd])) {
							continue;
						}
						if (isSpace(b[i]) && !isSpace(bnameContextEnd[inameContextEnd])) {
							continue;
						}
						if (isTab(b[i]) && !isTab(bnameContextEnd[inameContextEnd])) {
							continue;
						}
						System.out.println("---------------------------------------------------------获取的用户内容=>"
								+ new String(new byte[] {b[i]}) + "  byte=>" + b[i]);
						if (!isEnter(b[i]) && !isSpace(b[i]) && !isTab(b[i]))
							key.add(b[i]);
						else {
							for (int end = 0; end < isend.length; end++) {
								if (isend[end] == b[i]) {
									System.out.println("遇到指定的条件符[" + isend[end] + "]直接退出用户内容获取！");
									inameContextEnd = bnameContextEnd.length;
								}
							}
						}
						continue;
					} else {
						System.out.println("---------------------------------------------------------被抛弃的内容=>"
								+ new String(new byte[] {b[i]}) + "  byte=>" + b[i]);
						for (int end = 0; end < isend.length; end++) {
							if (isend[end] == b[i]) {
								System.out.println("遇到指定的条件符[" + isend[end] + "]直接退出用户内容获取！");
								inameContextEnd = bnameContextEnd.length;
							}
						}
					}
					// 检查用户名内容结束标识
					if (inameContext == bnameContext.length && isnameContextEnd
							&& inameContextEnd < bnameContextEnd.length) {
						System.out.println("用户名内容结束标识次数=>" + inameContextEnd);
						System.out.println("用户名内容结束标识内容=>" + bnameContextEnd[inameContextEnd]);
						System.out.println("源内容=>" + b[i]);
						if (!isEnter(bnameContextEnd[inameContextEnd]) && isEnter(b[i])) {
							continue;
						}
						if (!isSpace(bnameContextEnd[inameContextEnd]) && isSpace(b[i])) {
							continue;
						}
						if (!isTab(bnameContextEnd[inameContextEnd]) && isTab(b[i])) {
							continue;
						}
						if (bnameContextEnd[inameContextEnd] == b[i]) {
							inameContextEnd++;
							continue;
						} else {
							inameContextEnd = 0;
							continue;
						}
					}
					// 检查密码标识
					if (inameContextEnd == bnameContextEnd.length && ispwdSign && ipwdSign < bpwdSign.length) {
						System.out.println("密码标识次数=>" + ipwdSign);
						System.out.println("密码标识内容=>" + bpwdSign[ipwdSign]);
						System.out.println("源内容=>" + b[i]);
						if (!isEnter(bpwdSign[ipwdSign]) && isEnter(b[i])) {
							continue;
						}
						if (!isSpace(bpwdSign[ipwdSign]) && isSpace(b[i])) {
							continue;
						}
						if (!isTab(bpwdSign[ipwdSign]) && isTab(b[i])) {
							continue;
						}
						if (bpwdSign[ipwdSign] == b[i]) {
							ipwdSign++;
							continue;
						} else {
							ipwdSign = 0;
							continue;
						}
					}
					// 检查密码结束标识
					if (ipwdSign == bpwdSign.length && ispwdSignEnd && ipwdSignEnd < bpwdSignEnd.length) {
						System.out.println("密码结束标识次数=>" + ipwdSignEnd);
						System.out.println("密码结束标识内容=>" + bpwdSignEnd[ipwdSignEnd]);
						System.out.println("源内容=>" + b[i]);
						if (!isEnter(bpwdSignEnd[ipwdSignEnd]) && isEnter(b[i])) {
							continue;
						}
						if (!isSpace(bpwdSignEnd[ipwdSignEnd]) && isSpace(b[i])) {
							continue;
						}
						if (!isTab(bpwdSignEnd[ipwdSignEnd]) && isTab(b[i])) {
							continue;
						}
						if (bpwdSignEnd[ipwdSignEnd] == b[i]) {
							ipwdSignEnd++;
							continue;
						} else {
							ipwdSignEnd = 0;
							continue;
						}
					}
					// 检查密码内容标识
					if (ipwdSignEnd == bpwdSignEnd.length && ispwdContext && ipwdContext < bpwdContext.length) {
						System.out.println("密码内容标识次数=>" + ipwdContext);
						System.out.println("密码内容标识内容=>" + bpwdContext[ipwdContext]);
						System.out.println("源内容=>" + b[i]);
						if (!isEnter(bpwdContext[ipwdContext]) && isEnter(b[i])) {
							continue;
						}
						if (!isSpace(bpwdContext[ipwdContext]) && isSpace(b[i])) {
							continue;
						}
						if (!isTab(bpwdContext[ipwdContext]) && isTab(b[i])) {
							continue;
						}
						if (bpwdContext[ipwdContext] == b[i]) {
							ipwdContext++;
							continue;
						} else {
							ipwdContext = 0;
							continue;
						}

					}
					// System.out.println("bpwdContextEnd[ipwdContextEnd]" +
					// bpwdContextEnd[ipwdContextEnd]);
					// System.out.println("b[i]" + b[i]);
					// 获取密码
					if (ipwdContext == bpwdContext.length && ipwdContextEnd < bpwdContextEnd.length
							&& bpwdContextEnd[ipwdContextEnd] != b[i]) {
						if (isEnter(b[i])) {
							continue;
						}
						if (isSpace(b[i])) {
							continue;
						}
						if (isTab(b[i])) {
							continue;
						}
						System.out.println("----------------------------------------------获取的密码内容=>"
								+ new String(new byte[] {b[i]}));
						if (!isEnter(b[i]) && !isSpace(b[i]) && !isTab(b[i]))
							value.add(b[i]);
						continue;
					}

					System.out.println("ipwdContext == bpwdContext.length => " + (ipwdContext == bpwdContext.length));
					System.out.println("ispwdContextEnd => " + ispwdContextEnd);
					System.out.println("ipwdContextEnd < bpwdContextEnd.length => "
							+ (ipwdContextEnd < bpwdContextEnd.length));
					// 检查密码内容结束标识
					if (ipwdContext == bpwdContext.length && ispwdContextEnd && ipwdContextEnd < bpwdContextEnd.length) {
						System.out.println("密码内容结束标识次数=>" + ipwdContextEnd);
						System.out.println("密码内容结束标识内容=>" + bpwdContextEnd[ipwdContextEnd]);
						System.out.println("源内容=>" + b[i]);
						if (!isEnter(bpwdContextEnd[ipwdContextEnd]) && isEnter(b[i])) {
							continue;
						}
						if (!isSpace(bpwdContextEnd[ipwdContextEnd]) && isSpace(b[i])) {
							continue;
						}
						if (!isTab(bpwdContextEnd[ipwdContextEnd]) && isTab(b[i])) {
							continue;
						}
						if (bpwdContextEnd[ipwdContextEnd] == b[i]) {
							ipwdContextEnd++;
							System.out.println("ipwdContextEnd => " + ipwdContextEnd);
							System.out.println("(bpwdContextEnd.length - 1) => " + (bpwdContextEnd.length - 1));
							if (ipwdContextEnd == (bpwdContextEnd.length - 1)) {
								init = true;
							} else {
								continue;
							}
						} else {
							ipwdContextEnd = 0;
							continue;
						}
					}
					System.out.println("条件1=>" + init);
					System.out.println("ipwdContextEnd=>" + ipwdContextEnd);
					System.out.println("bpwdContextEnd.length=>" + bpwdContextEnd.length);
					if (init) {// 条件满足
						byte[] keys = new byte[key.size()];
						byte[] values = new byte[value.size()];
						Iterator keyit = key.iterator();
						for (int k = 0; keyit.hasNext(); k++) {
							keys[k] = (Byte) keyit.next();
						}
						Iterator valueit = value.iterator();
						for (int v = 0; valueit.hasNext(); v++) {
							values[v] = (Byte) valueit.next();
						}
						if (keys != null && keys.length > 0) {
							if (firstRow || iresult != 0) {
								result.put(new String(keys), new String(values));
							}
							iresult++;
						}
						key = new ArrayList();// 初始化key
						value = new ArrayList();// 初始化value
						inameSign = 0;
						inameSignEnd = 0;
						inameContext = 0;
						inameContextEnd = 0;
						ipwdSign = 0;
						ipwdSignEnd = 0;
						ipwdContext = 0;
						ipwdContextEnd = 0;
						init = false;
					}
				}
				fis.close();
				fis = null;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (fi != null) {
				try {
					fi.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				fi = null;
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				fis = null;
			}
		}
		return result;
	}

	private static boolean boolByte(byte[] value) throws Exception {
		boolean result = false;
		try {
			if (value != null && value.length != 0) {
				result = true;
			}
			System.out.println(value.getClass().getName() + " => " + result);
		} catch (Exception e) {
			throw new Exception(e);
		}
		return result;
	}

	/**
	 * 功能说明:是否是换行符
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	private static boolean isEnter(byte value) throws Exception {
		boolean result = false;
		try {
			if (value == b_LinuxEnter[0] || value == b_LinuxEnter[1]) {
				result = true;
			} else {
				result = false;
			}
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	/**
	 * 功能说明:是否是空格符
	 * 
	 * @return
	 * @throws Exception
	 */
	private static boolean isSpace(byte value) throws Exception {
		boolean result = false;
		try {
			if (value == b_Space[0]) {
				result = true;
			} else {
				result = false;
			}
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	/**
	 * 功能说明:是否是Tab符
	 * 
	 * @return
	 * @throws Exception
	 */
	private static boolean isTab(byte value) throws Exception {
		boolean result = false;
		try {
			if (value == b_Tab[0]) {
				result = true;
			} else {
				result = false;
			}
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	/**
	 * 功能说明:获取select byte值
	 * 
	 * @param selectValue
	 * @return
	 * @throws Exception
	 */
	private static byte[] getByte(String selectValue) throws Exception {
		byte[] result = null;
		try {
			if (selectValue != null && !selectValue.equals("")) {
				if (selectValue.equals("1")) {
					result = b_Space;
				} else if (selectValue.equals("2")) {
					result = b_LinuxEnter;
				} else if (selectValue.equals("3")) {
					result = b_Tab;
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	/**
	 * 功能说明:获取参数byte值
	 * 
	 * @param map
	 * @param key
	 * @return
	 * @throws Exception
	 */
	private static byte[] getByte(Map map, String key) throws Exception {
		byte[] result = new byte[0];
		try {
			String nameSign = (String) map.get(key);
			if (nameSign != null && !nameSign.equals("")) {
				System.out.println("接收到[" + key + "]参数");
				System.out.println(key + " [Radio]" + " => " + nameSign);
				if (nameSign.equals("2")) {
					String selectValue = (String) map.get(key + "Select");
					System.out.println(key + " [Select]" + " => " + selectValue);
					result = getByte(selectValue);
				} else if (nameSign.equals("3")) {
					result = ((String) map.get(key + "Text")).getBytes();
					System.out.println(key + " [Text]");
				} else {
					System.out.println("" + key + "=[" + nameSign + "] 参数值错误！");
				}
			} else {
				System.out.println("没有接收到[" + key + "]参数");
			}
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < result.length; i++) {
				System.out.println(key + "[byte] => " + result[i]);
				sb.append(result[i]);
			}

		} catch (Exception e) {
			throw new Exception(e);
		}
		return result;
	}

	/**
	 * 功能说明:删除上传的文件
	 * 
	 * @param path
	 * @throws Exception
	 */
	public static Boolean removeFile(String path) throws Exception {
		boolean result = false;
		try {
			File file = new File(path);
			System.out.println("文件" + file.getPath());
			if (file.isFile()) {
				result = file.delete();
				if (result)
					System.out.println("删除" + file.getName() + "文件成功！");
				else {
					if (I_count < I_maxCount) {
						I_count++;
						System.out.println("删除失败次数 =>" + I_count);
						Thread.sleep(500);
						result = removeFile(path);
					} else {
						System.out.println("删除" + file.getName() + "文件失败！");
						I_count = 0;
						throw new Exception("删除" + file.getName() + "文件失败！");
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// e.printStackTrace();
			// throw new Exception(e);
		}
		return result;
	}

	/**
	 * 功能说明:
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static byte[] readFileByChar(String fileName) throws Exception {
		byte[] result = null;
		File file = new File(fileName);
		try {
			long fileLength = FileUtils.showAvailableBytes(new FileInputStream(file));
			if (fileLength > 0L) {
				BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file));
				result = new byte[(int) fileLength];
				while (fis.read(result) != -1) {
				}
				fis.close();
				fis = null;
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
		return result;
	}

	/**
	 * 读取文件
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String readFile(String filePath) throws Exception {
		String fileContext = "";
		FileInputStream fi = null;
		byte[] b = null;
		int pos=0;
		int buffer=-1;
		File file = new File(filePath);
		try {
			fi = new FileInputStream(file);
			int length = FileUtils.showAvailableBytes(fi);
			if(0==length){
				throw new Exception("文件内容空,上传失败");
			}
			b = new byte[length];
			byte [] bom = new byte[3];
			int l = fi.read(bom);
			if(l == 3){
				if(bom[0] != ((byte)0xef) || bom[1] != ((byte)0xbb) || bom[2] != ((byte)0xbf)){
					for(int i=0;i<l;i++){
						b[pos++] = bom[i];
					}
				}
				while ((buffer=fi.read())!=-1) {
					b[pos++]=(byte) buffer;
				}
			} else if(l != -1){
				for(int i=0;i<l;i++){
					b[pos++] = bom[i];
				}
			}
			fileContext = new String(Arrays.copyOfRange(b,0,pos), "GBK");
		} catch (Exception e) {
			throw e;
		} finally {
			if (fi != null) {
				try {
					fi.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (b != null) {
				b = null;
			}
		}
		return fileContext;
	}
	public static String readFile(File file) throws Exception {
		String fileContext = "";
		FileInputStream fi = null;
		byte[] b = null;
		int pos=0;
		int buffer=-1;
//		File file = new File(filePath);
		try {
			fi = new FileInputStream(file);
			int length = FileUtils.showAvailableBytes(fi);
			if(0==length){
				throw new Exception("文件内容空,上传失败");
			}
			b = new byte[length];
			byte [] bom = new byte[3];
			int l = fi.read(bom);
			if(l == 3){
				if(bom[0] != ((byte)0xef) || bom[1] != ((byte)0xbb) || bom[2] != ((byte)0xbf)){
					for(int i=0;i<l;i++){
						b[pos++] = bom[i];
					}
				}
				while ((buffer=fi.read())!=-1) {
					b[pos++]=(byte) buffer;
				}
			} else if(l != -1){
				for(int i=0;i<l;i++){
					b[pos++] = bom[i];
				}
			}
			fileContext = new String(Arrays.copyOfRange(b,0,pos), "GBK");
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			if (fi != null) {
				try {
					fi.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (b != null) {
				b = null;
			}
		}
		return fileContext;
	}
	/**
	 * 显示输入流中还剩的字节数
	 */
	private static int showAvailableBytes(InputStream in) {
		try {
			return in.available();
		} catch (IOException e) {
			return -1;
		}
	}

	public void read() {

	}

	/**
	 * @des 写文件
	 * @param context
	 * @return
	 */
	public static String write(String context, String dir) {
		String result = "";
		File file = new File(dir);
		if (!file.exists()) {
			try {
				file.mkdirs();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		OutputStream out = null;
		try {
			File tempFile = File.createTempFile("unimas_", ".xml");
			File newfile = new File(dir + tempFile.getName());
			result = newfile.getAbsolutePath();
			out = new FileOutputStream(newfile);
			out.write(context.getBytes());
			out.flush();
			log.info("写文件" + newfile.getName() + "成功！");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.info("写文件失败！");
			log.info(e.getMessage(), e);
			e.printStackTrace();
		} finally {
			if (out != null)
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return result;
	}

	/**
	 * 删除文件,最多循环删除10次，每次休眠100毫秒
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean deleteFile(String filePath) {
		File file = new File(filePath);
		return deleteFile(file);
	}

	/**
	 * 删除文件,最多循环删除10次，每次休眠100毫秒
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean deleteFile(File file) {
		boolean flag = true;
		if (file != null && file.isFile() && file.exists()) {
			int num = 0;
			while (!(flag = file.delete()) && num++ < 10) {
				try {
					System.gc();
					Thread.sleep(100);
					log.info("deleteFile:第" + num + "次删除" + file.getAbsolutePath() + "文件");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		if (!flag) {
			log.info("删除" + file.getAbsolutePath() + "文件失败");
		}
		return flag;
	}

	/**
	 * 读取excel文件
	 * 
	 * @return
	 * @throws Exception
	 */
	/*public static List<String>  readExcelFile(String filePath) throws Exception {
		String fileContext = "";
		List<String> str = new ArrayList<String>();
		InputStream is = null;
		try {
			if (filePath != null || !"".equals(filePath)) {

				Workbook wb = null;
				Sheet sheet = null;
				Row row = null;
				String type = "2007";
				try {
					is = new FileInputStream(filePath);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					wb = new HSSFWorkbook(new POIFSFileSystem(is));
					type = "2003";
				} catch (Exception e) {
					wb = new XSSFWorkbook(filePath);
				}

				sheet = wb.getSheetAt(0);
				String cellValue = null;// 单元格，最终按字符串处理
				if(sheet.getRow(0)==null){
					throw new Exception("文件内容空,上传失败!");
				}
				int allRows = sheet.getLastRowNum();
				StringBuffer tmpStr = new StringBuffer();
				for (int i = 0; i <= allRows; i++) {
					row = sheet.getRow(i);
					int cellNum = row.getLastCellNum();
					int count = 1;
					String st1 = "";
					for (int j = 0; j < cellNum; j++) {
						Cell cell =  row.getCell(j);
						if (type.equals("2007")) {
							Cell xcell = cell;
							if (xcell == null) {// 单元格为空设置cellStr为空串
								cellValue = "";
							} else if (xcell.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN) {// 对布尔值的处理
								cellValue = String.valueOf(xcell.getBooleanCellValue());
							} else if (xcell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
								xcell.setCellType(XSSFCell.CELL_TYPE_STRING);
								cellValue = xcell.getStringCellValue();
							} else if(xcell.getCellType() == XSSFCell.CELL_TYPE_FORMULA){
								cellValue = xcell.getCellFormula();
							}else {// 其余按照字符串处理
								cellValue = xcell.getStringCellValue();
							}
						} else {
							Cell hcell = cell;
							if (hcell == null) {// 单元格为空设置cellStr为空串
								cellValue = "";
							} else if (hcell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {// 对布尔值的处理
								cellValue = String.valueOf(hcell.getBooleanCellValue());
							} else if (hcell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {// 对数字值的处理
								hcell.setCellType(HSSFCell.CELL_TYPE_STRING);
								cellValue = hcell.getRichStringCellValue().getString();
							} else if(hcell.getCellType() == XSSFCell.CELL_TYPE_FORMULA){
								cellValue = hcell.getCellFormula();
							}else {// 其余按照字符串处理
								cellValue = hcell.getStringCellValue();
							}
						}
						tmpStr.append("\"");
						st1 = st1+ cellValue;
						tmpStr.append(cellValue);
						tmpStr.append("\"");
						if (count < cellNum) {
							tmpStr.append(",");
							st1 = st1+ cellValue+",";
						}
						count++;
					}
					str.add(st1);
					if (i < allRows) {
						tmpStr.append("\n");
					}
				}
				fileContext = tmpStr.toString();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		} finally {
//			if (is != null) {
//				try {
//					is.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
		return str;
	}*/
	/*public static String readExcelFile(File filePath) throws Exception {
		String fileContext = "";
		InputStream is = null;
		try {
			if (filePath != null || !"".equals(filePath)) {

				Workbook wb = null;
				Sheet sheet = null;
				Row row = null;
				String type = "2007";
				try {
					is = new FileInputStream(filePath);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					wb = new HSSFWorkbook(new POIFSFileSystem(is));
					type = "2003";
				} catch (Exception e) {
					wb = new XSSFWorkbook();
				}

				sheet = wb.getSheetAt(0);
				String cellValue = null;// 单元格，最终按字符串处理
				if(sheet.getRow(0)==null){
					throw new Exception("文件内容空,上传失败!");
				}
				int allRows = sheet.getLastRowNum();
				StringBuffer tmpStr = new StringBuffer();
				for (int i = 0; i <= allRows; i++) {
					row = sheet.getRow(i);
					int cellNum = row.getLastCellNum();
					int count = 1;
					for (int j = 0; j < cellNum; j++) {
						Cell cell =  row.getCell(j);
						if (type.equals("2007")) {
							Cell xcell = cell;
							if (xcell == null) {// 单元格为空设置cellStr为空串
								cellValue = "";
							} else if (xcell.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN) {// 对布尔值的处理
								cellValue = String.valueOf(xcell.getBooleanCellValue());
							} else if (xcell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
								xcell.setCellType(XSSFCell.CELL_TYPE_STRING);
								cellValue = xcell.getStringCellValue();
							} else if(xcell.getCellType() == XSSFCell.CELL_TYPE_FORMULA){
								cellValue = xcell.getCellFormula();
							}else {// 其余按照字符串处理
								cellValue = xcell.getStringCellValue();
							}
						} else {
							Cell hcell = cell;
							if (hcell == null) {// 单元格为空设置cellStr为空串
								cellValue = "";
							} else if (hcell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {// 对布尔值的处理
								cellValue = String.valueOf(hcell.getBooleanCellValue());
							} else if (hcell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {// 对数字值的处理
								hcell.setCellType(HSSFCell.CELL_TYPE_STRING);
								cellValue = hcell.getRichStringCellValue().getString();
							} else if(hcell.getCellType() == XSSFCell.CELL_TYPE_FORMULA){
								cellValue = hcell.getCellFormula();
							}else {// 其余按照字符串处理
								cellValue = hcell.getStringCellValue();
							}
						}
						tmpStr.append("\"");
						tmpStr.append(cellValue);
						tmpStr.append("\"");
						if (count < cellNum) {
							tmpStr.append(",");
						}
						count++;
					}
					if (i < allRows) {
						tmpStr.append("\n");
					}
				}
				fileContext = tmpStr.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
//		} finally {
//			if (is != null) {
//				try {
//					is.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
		return fileContext;
	}*/
}