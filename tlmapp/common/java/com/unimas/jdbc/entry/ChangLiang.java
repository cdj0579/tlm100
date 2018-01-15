package com.unimas.jdbc.entry;

@Deprecated
public class ChangLiang {
	/**
	 * 操作类型定义
	 */
	public static final  int  YHDL=1;//用户登录
	
	public static final  int  YHDC=0;//用户登出
	
    public static final  int  SELECT=2;//查询
	
	public static final  int  UPDATE=3;//修改
	
    public static final  int  DELETE=4;//删除
	
	public static final  int  INSERT=5;//插入
	
	/**
	 * 应用系统标识
	 */
	public static final  String  XTBS1="6";//use
	
	/**
	 * 操作结果标识
	 * 
	 */
	public static final  String  SUCCESS="1";//use
	public static final  String  FALSE="0";  //use


	

}
