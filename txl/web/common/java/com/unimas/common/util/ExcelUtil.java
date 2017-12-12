package com.unimas.common.util;

import jxl.Cell;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.*;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA. User: wgx Date: 15-5-4 Time: 上午10:08 To change
 * this template use File | Settings | File Templates.
 */
public class ExcelUtil {

	static String f = new String("D:\\工作记录.xlsx");

	private static final Logger logger = Logger.getLogger(ExcelUtil.class);

	private static int EXCEL_SHEET_MAXROW = 6000;

	public static void main(String[] args) {
		// 获取关键字
		System.out.println("begin....................");
		runTxt(f, 1);
	}

	String[] excelHeader = { "Sno", "Name", "Age" };
	
	public static void readXls(String filename) {
		try {
			InputStream is = new FileInputStream(filename);
			Workbook rwb = Workbook.getWorkbook(is);
			jxl.Sheet rs = rwb.getSheet(0); // 读取第一个工作表的数据

			int num = rs.getRows(); // 得到此excel有多少行..

			for (int i = 0; i < num; i++) {
				Cell[] cells = rs.getRow(i); // 得到第i行的数据..返回cell数组
				for(Cell cell : cells){
					String name = cell.getContents(); // 得到第i行.第一列的数据.
					System.out.print(name+" -- ");
				}
				System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}

	// 获取唯一关键字
	private static void runTxt(String filename, int postion) {
		try {
			InputStream is = new FileInputStream(filename);
			Workbook rwb = Workbook.getWorkbook(is);
			jxl.Sheet rs = rwb.getSheet(0); // 读取第一个工作表的数据

			int num = rs.getRows(); // 得到此excel有多少行..

			for (int i = 0; i < num; i++) {
				Cell[] cell = rs.getRow(i); // 得到第i行的数据..返回cell数组
				String name = cell[postion].getContents(); // 得到第i行.第一列的数据.
				if (!name.matches(".*\\d{1,}.*") && name.trim().length() <= 10) {
					try {
						int nums = 1;
						if (name.length() <= 1) {
							nums = (int) (Math.random() * 1000);
						} else {
							nums = 1;
						}
						String s = name + "\t" + String.valueOf(nums) + "\r\nx:" + String.valueOf(nums);
						BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));
						bw.write(s);
						bw.newLine();
						bw.close();
					} catch (Exception e) {
						System.out.println("error:" + e);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}

	// 获取唯一关键字
	protected void runcompay(String filename1, String filename2, String targetfile, int postion) {
		try {
			WritableWorkbook workbook;
			OutputStream os = new FileOutputStream(targetfile);
			workbook = Workbook.createWorkbook(os);
			WritableSheet sheet = workbook.createSheet("List", 0); // 添加第一个工作表
			// 获得总 Sheets
			// Sheet[] sheets = rwb.getSheets();
			// int sheetLen = sheets.length;
			// 获得单个Sheets 含有的行数
			InputStream is = new FileInputStream(filename1);
			Workbook rwb = Workbook.getWorkbook(is);
			jxl.Sheet rs = rwb.getSheet(0); // 读取第一个工作表的数据

			InputStream is2 = new FileInputStream(filename2);
			Workbook rwb2 = Workbook.getWorkbook(is2);
			jxl.Sheet rs2 = rwb2.getSheet(0); // 读取第一个工作表的数据
			// Cell[] cell_domain = rs.getColumn(0); // 读取第一列的值
			int num = rs.getRows(); // 得到此excel有多少行..
			int num2 = rs2.getRows(); // 得到此excel有多少行..
			int sum = 0;

			Map map = new HashMap();
			for (int i = 0; i < num; i++) {
				Cell[] cell = rs.getRow(i); // 得到第i行的数据..返回cell数组
				String name = cell[postion].getContents(); // 得到第i行.第一列的数据.
				for (int y = 0; y < num2; y++) {
					Cell[] cell2 = rs2.getRow(y); // 得到第i行的数据..返回cell数组
					String name2 = cell2[postion].getContents(); // 得到第i行.第一列的数据.
					if (!name.equals(name2) && !map.containsKey(String.valueOf(y))) {
						jxl.write.Label labelCF1 = new jxl.write.Label(0, sum, name2);
						sheet.addCell(labelCF1);
						sum++;
						map.put(String.valueOf(y), String.valueOf(y));
					}
				}
				System.out.println(sum);
				jxl.write.Label labelCF = new jxl.write.Label(0, sum, name);
				sheet.addCell(labelCF);
				sum++;
			}
			workbook.write();
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}

	// 获取关键字
	protected void run(String filename, String targetfile, int postion) {
		try {
			WritableWorkbook workbook;
			OutputStream os = new FileOutputStream(targetfile);
			workbook = Workbook.createWorkbook(os);
			WritableSheet sheet = workbook.createSheet("List", 0); // 添加第一个工作表
			// 获得总 Sheets
			// Sheet[] sheets = rwb.getSheets();
			// int sheetLen = sheets.length;
			// 获得单个Sheets 含有的行数
			InputStream is = new FileInputStream(filename);
			Workbook rwb = Workbook.getWorkbook(is);
			jxl.Sheet rs = rwb.getSheet(0); // 读取第一个工作表的数据
			// Cell[] cell_domain = rs.getColumn(0); // 读取第一列的值
			int num = rs.getRows(); // 得到此excel有多少行..
			for (int i = 1; i < num; i++) {
				Cell[] cell = rs.getRow(i); // 得到第i行的数据..返回cell数组
				String name = cell[postion].getContents(); // 得到第i行.第一列的数据.
				jxl.write.Label labelCF = new jxl.write.Label(0, i - 1, name);
				sheet.addCell(labelCF);
			}
			workbook.write();
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}

	/**
	 * @param path
	 *            文件路径
	 * @param suffix
	 *            后缀名, 为空则表示所有文件
	 * @param isdepth
	 *            是否遍历子目录
	 * @return list
	 */
	public static List<String> getListFiles(String path, String suffix, boolean isdepth) {
		List<String> lstFileNames = new ArrayList<String>();
		File file = new File(path);
		return listFile(lstFileNames, file, suffix, isdepth);
	}

	private static List<String> listFile(List<String> lstFileNames, File f, String suffix, boolean isdepth) {
		// 若是目录, 采用递归的方法遍历子目录
		if (f.isDirectory()) {
			File[] t = f.listFiles();
			for (int i = 0; i < t.length; i++) {
				if (isdepth || t[i].isFile()) {
					listFile(lstFileNames, t[i], suffix, isdepth);
				}
			}
		} else {
			String filePath = f.getAbsolutePath();
			if (!suffix.equals("")) {
				int begIndex = filePath.lastIndexOf("."); // 最后一个.(即后缀名前面的.)的索引
				String tempsuffix = "";

				if (begIndex != -1) {
					tempsuffix = filePath.substring(begIndex + 1, filePath.length());
					if (tempsuffix.equals(suffix)) {
						lstFileNames.add(filePath);
					}
				}
			} else {
				lstFileNames.add(filePath);
			}
		}
		return lstFileNames;
	}
	
	public static class WorkbookBuffer {
		
		//private File file;
		private WritableWorkbook workbook;
		private OutputStream os;
		
		public WorkbookBuffer(String filePath) throws Exception {
			this(new FileOutputStream(new File(filePath)));
		}
		
		public WorkbookBuffer(OutputStream out) throws Exception {
			try {
				os = out;
				workbook = Workbook.createWorkbook(os);
			} catch (FileNotFoundException e) {
				logger.error("找不到文件：" + e.getMessage());
				throw new Exception("找不到文件：" + e.getMessage(), e);
			} catch (IOException e) {
				logger.error("io 异常 ：" + e.getMessage());
				throw new Exception("io 异常 ：" + e.getMessage(), e);
			}
		}
		
		public WritableCellFormat createHeaderFormat() throws WriteException {
			WritableFont writableFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false,
					UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
			WritableCellFormat writableCellFormatTitle = new WritableCellFormat(writableFont);
			writableCellFormatTitle.setBackground(Colour.BRIGHT_GREEN);
			return writableCellFormatTitle;
		}
		
		public void wirteHeader(WritableSheet sheet, String[] heanderEnglish, String[] heanderChinese) throws Exception{
			// 1 header
			for (int i = 0; i < heanderChinese.length; i++) {
				Label label = new Label(i, 0, heanderChinese[i], createHeaderFormat());
				sheet.setColumnView(i, 20);
				sheet.addCell(label);
			}
		}
		
		//最后一个参数lastDotZero 标记为是否替换最后的.0
		public void createSheet(String sheetName, int sheetIndex, Map<String, String> header
				, List<Map<String, Object>> data, Object ... lastDotZero) throws Exception {
			
			// 几个sheet页
			int sheetSize = getExcelSheet(data.size());
			
			// header
			String[] heanderEnglish = new String[header.size()];
			String[] heanderChinese = new String[header.size()];
			Iterator<String> iterator = header.keySet().iterator();

			int headerIndex = 0;
			while (iterator.hasNext()) {
				String key = iterator.next();
				heanderEnglish[headerIndex] = key;
				heanderChinese[headerIndex] = header.get(key);
				headerIndex++;
			}
			
			
			if(sheetName == null){
				sheetName = "sheet";
			}
			if(sheetSize > 0){
				for (int n = 0; n < sheetSize; n++) {
					int t=0;
					// sheet页写
					WritableSheet sheet = workbook.createSheet(sheetSize>1?(sheetName + (n + 1)):sheetName, n + sheetIndex);
					wirteHeader(sheet, heanderEnglish, heanderChinese);

					// 内容
					int size = (n + 1) * EXCEL_SHEET_MAXROW > data.size() ? data.size(): (n + 1) * EXCEL_SHEET_MAXROW;
					for (int i = n * EXCEL_SHEET_MAXROW; i < size; i++) {
						Map<String, Object> oneMap = data.get(i);
						for (int j = 0; j < heanderEnglish.length; j++) {
							String value = String.valueOf(oneMap.get(heanderEnglish[j]) == null ? "" : oneMap.get(heanderEnglish[j]));
							if(lastDotZero != null && lastDotZero.length == 1 && lastDotZero[0].equals("1")) {
								value = value.replaceAll("\\.0", "");
							}
							Label label = new Label(j, t+1, value);
							sheet.addCell(label);
						}
						t++;
					}
					
				}
			} else {
				WritableSheet sheet = workbook.createSheet(sheetName, sheetIndex);
				wirteHeader(sheet, heanderEnglish, heanderChinese);
			}
		}
		
		public boolean write() throws Exception {
			boolean flag = false;
			try {
				workbook.write();
				flag = true;
			} catch (IOException e) {
				logger.error("io 异常 ：" + e.getMessage());
				throw new Exception("io 异常 ：" + e.getMessage(), e);
			} finally {
				if (workbook != null) {
					try {
						workbook.close();
					} catch (IOException e) {
						logger.error(e.getMessage());
					} catch (WriteException e) {
						logger.error(e.getMessage());
					}
				}
				if (os != null) {
					try {
						os.close();
					} catch (IOException e) {
						logger.error(e.getMessage());
					}
				}
			}
			return flag;
		}
	}
	
	//最后一个参数lastDotZero 标记为是否替换最后的.0
	public static boolean writeExcel(Map<String, String> header, List<Map<String, Object>> data, String filePath, Object ... lastDotZero)
			throws Exception {
		WorkbookBuffer wb = new WorkbookBuffer(filePath);
		wb.createSheet(null, 0, header, data, lastDotZero);
		return wb.write();
	}

	public static int getExcelSheet(int size) {

		int sheet = size / EXCEL_SHEET_MAXROW;
		if (size % EXCEL_SHEET_MAXROW != 0) {

			sheet++;

		}
		return sheet;
	}
}
