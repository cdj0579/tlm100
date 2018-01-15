package com.unimas.jdbc.entry;
/**
 * 
 * 系统操作枚举标识符
 * 
 * @author yy  2016-06-02
 */

public enum SysOperateBS {
	
	USER_LOGOUT(2), //用户登出
	USER_LOGIN(1),  //用户登出
	
	USER_SELECT(10), //查询用户信息
	USER_INSERT(11), //新增用户信息
	USER_UPDATE(12), //修改用户信息
	USER_DELETE(13), //删除用户信息
	
	ORGA_SELECT(20),//查询机构信息
	ORGA_INSERT(21),//新建机构信息
	ORGA_UPDATE(22),//修改机构信息
	ORGA_DELETE(23),//删除机构信息
	
	ROLE_SELECT(30),//查询角色信息
	ROLE_INSERT(31),//插入角色信息
	ROLE_UPDATE(32),//修改角色信息
	ROLE_DELETE(33),//删除角色信息
	
	RESOURCE_SELECT(40),//查询资源信息
	RESOURCE_INSERT(41),//注册资源信息
	RESOURCE_UPDATE(42),//修改资源详细
	RESOURCE_DELETE(43),//删除资源信息
	
	GRANT_ON_ROLE(44), //为角色授权
	GRANT_ON_ZY(45),   //为资源授权
	GRANT_ON_ZYML(46), //为资源目录授权
	GRANT_ON_TABLE(47),//为表、目录、文件授权
	
	APPLICATION_IDENTIFICATION(50),//应用系统标识    40/use
	
	OPERATOR_RESULT_SUCCESS(1), //操作成功标识
	OPERATOR_RESULT_FALSE(2);   //操作失败标识
	
	private int value;
	
	private SysOperateBS(int s){
		this.value = s;
	}
	
	@Override
	public String toString() {
		return String.valueOf(this.value);
	}
	
	public int getValue(){
		return this.value;
	}
	

}
