/**
 * XML文件处理基类
 */
package com.unimas.common.utils.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.unimas.common.log.LoggerManger;



/**
 * @author wlh
 * 
 */
public class XmlLang {
	
	private static boolean fileLock = false; // 文件锁，同时只能对一个文件进行写操作。需要注意解锁

	private static XmlLang instance = null;

	private XmlLang() {
		super();
	}

	/**
	 * @return
	 */
	public static synchronized XmlLang getInstance() {
		if (instance == null) {
			instance = new XmlLang();
		}
		return instance;
	}

	
	
	/**
	 * 读取XML文件信息。 传递一个带文件路径的文件名，返回该XML文件信息。 如果文件不存在或文件路径错误，则报异常
	 * 
	 * @param file
	 * @return
	 * @throws MalformedURLException
	 *             DocumentException, Exception
	 *             szpShang 修改
	 */
	public Document readXMLFile(String fileName) throws MalformedURLException,
			DocumentException, Exception {
		File file = new File(fileName);
		// 检查文件路径是否正确
		if (!file.exists()) {
			LoggerManger.getLogger().error("文件路径:[" + fileName + "]不存在！");
			throw new Exception(fileName +"文件不存在！");
		}
		return readXMLFile(file);
	}
	
	/**
	 * 读取XML文件信息。 传递一个带文件路径的文件名，返回该XML文件信息。 如果文件不存在或文件路径错误，则报异常
	 * 
	 * @param file
	 * @return
	 * @throws MalformedURLException
	 *             DocumentException, Exception
	 *              szpShang 添加
	 */
	public Document readXMLFile(File file) throws MalformedURLException,DocumentException, Exception {
		Document doc = null;
		SAXReader saxReader = new SAXReader();
		// 读取该xml文件
		try {
			doc = saxReader.read(file);
		} catch (DocumentException e) {
			// 自动生成 catch 块
			throw e;
		}
		return doc;
	}
	
	/**
	 * 读取XML流信息。 传递xml流，返回该XML文件信息。
	 * 
	 * @param file
	 * @return
	 * @throws MalformedURLException
	 *             DocumentException, Exception
	 */
	public Document readXMLInputStream(InputStream is) throws MalformedURLException,
			DocumentException, Exception {
		Document doc = null;
		SAXReader saxReader = new SAXReader();

		// 读取该xml文件
		try {
			doc = saxReader.read(is);
		}catch (DocumentException e) {
			LoggerManger.getLogger().error(e);
			throw e;
		}

		return doc;
	}
	
	/**
	 * 将xml字符串，解析成Document对象
	 * @param xml
	 * @return
	 * @throws DocumentException
	 */
	public Document parseXml(String xml) throws DocumentException{
		return DocumentHelper.parseText(xml);
	}
	
	/**
	 * 保存XML信息 该方法属于共用方法，所有xml文件的保存均通过该方法调用 该方法受到文件锁的约束。 如果成功写入，则返回1
	 * 
	 * @param fileName
	 * @param document
	 * 
	 * @return
	 * @throws Exception 
	 * @throws IOException
	 *             Exception
	 */
	protected synchronized int saveXMLFile(String fileName, Document document, String encoding)
			throws Exception {
		int result = 0;
		XMLWriter writer = null;
		if ( !fileLock ) {
		
			// 文件未被锁定，可以执行修改操作
			fileLock = true; // 写操作，上锁
			try {
				// 格式化输出，类型IE浏览一样
				OutputFormat format = OutputFormat.createPrettyPrint();
				// 指定XML编码
				format.setEncoding(encoding);
				//writer = new XMLWriter(new FileWriter(new File(fileName)), format);
				writer = new XMLWriter(new FileOutputStream(fileName), format);
				writer.write(document);
				writer.flush();
				result = 1;
			} catch (Exception e) {
				LoggerManger.getLogger().error(e.getMessage(), e);
				throw new Exception("保存文件[" + fileName + "]失败！");
			} finally {
				fileLock = false; // 解锁
				if (writer != null) {
					try {
						writer.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
					//writer = null;
				}
			}
		} else {
			// 文件写操作已被锁定，抛出需要等待信息
			LoggerManger.getLogger().warn("写入文件[" + fileName + "]时，发生冲突。提示等待片刻。");
			throw new Exception("有进程在执行写XML文件操作，请等待片刻后重试");
		}
		if (writer != null) {
			writer.close();	
		}
		return result;
	}

	/**
	 * 保存XML信息 该方法属于共用方法，所有xml文件的保存均通过该方法调用 该方法受到文件锁的约束。 如果成功写入，则返回1
	 * 
	 * @param fileName
	 * @param document
	 * 
	 * @return
	 * @throws Exception 
	 * @throws IOException
	 *             Exception
	 */
	protected synchronized int saveUtf8XMLFile(String fileName, Document document)
			throws Exception {
		return saveXMLFile(fileName, document, "UTF-8");
	}
	
	/**
	 * 保存XML信息 该方法属于共用方法，所有xml文件的保存均通过该方法调用 该方法受到文件锁的约束。 如果成功写入，则返回1
	 * 
	 * @param fileName
	 * @param document
	 * 
	 * @return
	 * @throws Exception 
	 * @throws IOException
	 *             Exception
	 */
	protected synchronized int saveGbkXMLFile(String fileName, Document document)
			throws Exception {
		return saveXMLFile(fileName, document, "GBK");
	}
	
	/**
     * 保存XML信息 该方法属于共用方法，所有xml文件的保存均通过该方法调用 该方法受到文件锁的约束。 如果成功写入，则返回1
     * 
     * @param fileName
     * @param document
     * 
     * @return
     * @throws Exception 
     * @throws IOException
     *             Exception
     */
    public synchronized int saveXMLFile(String fileName, Document document)
            throws Exception {
        int result = 0;
        XMLWriter writer = null;
        if ( !fileLock ) {
        
            // 文件未被锁定，可以执行修改操作
            fileLock = true; // 写操作，上锁
            try {
                // 格式化输出，类型IE浏览一样
                OutputFormat format = OutputFormat.createPrettyPrint();
                // 指定XML编码
                format.setEncoding("GBK");
                writer = new XMLWriter(new FileWriter(new File(fileName)),
                        format);
                // writer = new XMLWriter(new FileOutputStream(fileName));
                writer.write(document);
                writer.flush();
                result = 1;
            } catch (Exception e) {
                throw new Exception("保存文件[" + fileName + "]失败！");
            } finally {
                fileLock = false; // 解锁
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //writer = null;
                }
            }
        } else {
            // 文件写操作已被锁定，抛出需要等待信息
            throw new Exception("有进程在执行写XML文件操作，请等待片刻后重试");
        }
        if (writer != null) {
            writer.close(); 
        }
        return result;
    }
	
}
