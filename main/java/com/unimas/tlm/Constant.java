package com.unimas.tlm;

import java.io.File;

/**
 * portal系统所用到的常量类
 * @author cj
 */
public enum Constant {
	
	//************变量*****************
	ENCODING("utf-8"),
	PORTAL_NEW("true"),
	CAUSEINFO_ENABLED("false"),
	
	//*********目录与文件路径*************
	ROOT((File.separatorChar=='\\'?"c:\\":"/")+"etc"+File.separator+"unimas"+File.separator+"use"+File.separator),//根目录
	CONF(Constant.ROOT.getValue()+"conf"+File.separator),
	LOG_OFF("true"),//是否记录日志
	//certs存放证书的目录
	CERT_PATH(Constant.CONF.getValue()+"certs"+File.separatorChar),
	/**
	 * 上传用户失败存储位置
	 */
	UPLOADUSER(Constant.CONF.getValue()),
	/**
	 * 文件上传缓存目录
	 */
	UPLOAD(Constant.ROOT.getValue()+"temp"+File.separator+"upload"+File.separator),
	/**
	 * 任务配置文件目录
	 */
	JOB_FILE_PATH(Constant.CONF.getValue()+"jobs"+File.separator),
	/**
	 * 图片缓存目录
	 */
	QUERYHBASETEMP(Constant.ROOT.getValue()+"temp"+File.separator+"hbasestempfile"+File.separator),
	/**
	 * 文件下载缓存目录
	 */
	DOWNLOAD(Constant.ROOT.getValue()+"temp"+File.separator+"download"+File.separator),
	/**
	 * 服务管理配置文件路径
	 */
	S_SERVICES_CONF_PATH(Constant.CONF.getValue() + "services.xml"),
	/**
	 * HBase查询服务配置文件路径
	 */
	S_HBASE_CONF_PATH(Constant.CONF.getValue() + "hbase"+File.separator),
	/**
	 * SQLCR查询服务配置文件路径
	 */
	S_SQLCR_CONF_PATH(Constant.CONF.getValue() + "sqlcr"+File.separator),
	/**
	 * 图片服务配置文件路径
	 */
	S_IMAGE_CONF_PATH(Constant.CONF.getValue() + "image"+File.separator),
	/**
	 * 第三方服务配置文件路径
	 */
	S_THIRDPARTY_CONF_PATH(Constant.CONF.getValue() + "thirdparty"+File.separator),
	/**
	 * 分布式服务配置文件路径
	 */
	S_DCS_CONF_PATH(Constant.CONF.getValue() + "dcs"+File.separator),
	/**
	 * ESQL服务配置文件路径
	 */
	S_ESQL_CONF_PATH(Constant.CONF.getValue() + "esql"+File.separator),
	/**
	 * CSB系统配置文件路径
	 */
	SYS_CONFIG_PATH(Constant.CONF.getValue() + "csb.properties"),
	/**
	 * hbase默认服务配置文件路径
	 */
	DEFAULT_HBASE_SERVICE_PATH(Constant.CONF.getValue() + "defaulthbase.json"),
	/**
	 * impala默认服务配置文件路径
	 */
	DEFAULT_IMPALA_SERVICE_PATH(Constant.CONF.getValue() + "defaultimpala.json"),
	/**
	 * postgre默认服务配置文件路径
	 */
	DEFAULT_POSTGRE_SERVICE_PATH(Constant.CONF.getValue() + "defaultpostgre.json"),
	/**
	 * 公安部接口配置文件
	 */
	DEFAULT_GAP_SERVICE(Constant.CONF.getValue() + "gab.json"),

	/**
	 * HBASE 数据源信息
	 */
	HBASE_DBSOURCE_PATH(Constant.CONF.getValue() + "hbaseDBSource.properties"),
	/**
	 *  session中用户key
	 */
	S_AuthUser("authuser"),
	/**
	 * 数据目录文件路径
	 */
	DATA_DIR_CONF_PATH(Constant.CONF.getValue()+"datadirconf.json"),
	/**
	 * 数据目录备份文件路径
	 */
	DATA_DIR_CONF_BAK_PATH(Constant.CONF.getValue()+"datadirconf.bak"),
	/**
	 * 根证书管理文件
	 */
	S_CaFile(Constant.CONF.getValue() + "ca.xml"),
	RESOURCE_MULU_PATH(Constant.CONF.getValue()+"resourceMulu.xml"), 
	Batch_FilePath(Constant.CONF.getValue() + "bidui"+File.separator),
	S_ClobName(Constant.CONF.getValue() + "clob"+File.separator), 
	S_BlobName(Constant.CONF.getValue() + "blob"+File.separator),
	S_UPLOAD_Path(Constant.CONF.getValue() + "uploadexcl"+File.separator),
	IMG_UPLOAD_Path(Constant.CONF.getValue() + "images"+File.separator+"temp"+File.separator),
	IMG_Path(Constant.CONF.getValue() + "images"+File.separator),
	/**
	 * DMM接口加密密钥
	 */
	KEY_CALL_API("U%56#o#u$jk0ffds");
	private String value;
	
	static {
		File file = new File(Constant.CONF.getValue());
		if(!file.exists()){
			file.mkdirs();
		}
		file = new File(Constant.UPLOAD.getValue());
		if(!file.exists()){
			file.mkdirs();
		}
		file = new File(Constant.DOWNLOAD.getValue());
		if(!file.exists()){
			file.mkdirs();
		}
		file = new File(Constant.JOB_FILE_PATH.getValue());
		if(!file.exists()){
			file.mkdirs();
		}
	}
	Constant(String s){
		this.value = s;
	}
	
	@Override
	public String toString() {
		return this.value;
	}



	public String getValue(){
		return this.value;
	}
}