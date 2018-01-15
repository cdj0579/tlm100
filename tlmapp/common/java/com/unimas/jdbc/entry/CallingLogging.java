package com.unimas.jdbc.entry;

import java.sql.Timestamp;
public class CallingLogging {

	private int ID ;// '主键'
	private int Num_ID ;//
	private String  Reg_ID;//应用系统/资源库标识,
	private String  User_ID;// 用户标识,
	private String  Organization ;//单位名称,
	private String  Organization_ID;//单位机构代码',
	private String User_Name ;//用户名,
	private Timestamp Operate_Time ;//操作时间,
	private String Terminal_ID;//终端标识,
	private int  Operate_Type;//操作类型,
	private String  Operate_Result;//操作结果',（1，成功,0失败）
	private String  Error_Code;//失败原因代码',
	private String  Operate_Name;//功能模块'（用户登录，用户退出，注册资源写操作的各个模块的名称）
	private String  Operate_Condition;//操作条件',
	private String  BZ1;//备用字段1资源id',
	private String  BZ2;//备用字段2资源名称',
	private String  BZ3;//备用字段3,
	private String  BZ4;//备用字段4用户系统角色 admin  、normal',
	private String  BZ5;//备用字段5' 操作详细,
	private String  BZ6;//备用字段6',
	private String  BZ7;//备用字段7',
	private String  BZ8;//备用字段8',
	private String  BZ9;//备用字段9',
	private String  BZ10;//备用字段10',

	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getNum_ID() {
		return Num_ID;
	}
	public void setNum_ID(int numID) {
		Num_ID = numID;
	}
	public String getReg_ID() {
		return Reg_ID;
	}
	public void setReg_ID(String regID) {
		Reg_ID = regID;
	}
	public String getUser_ID() {
		return User_ID;
	}
	public void setUser_ID(String userID) {
		User_ID = userID;
	}
	public String getOrganization() {
		return Organization;
	}
	public void setOrganization(String organization) {
		Organization = organization;
	}
	public String getOrganization_ID() {
		return Organization_ID;
	}
	public void setOrganization_ID(String organizationID) {
		Organization_ID = organizationID;
	}
	public String getUser_Name() {
		return User_Name;
	}
	public void setUser_Name(String userName) {
		User_Name = userName;
	}

	public Timestamp getOperate_Time() {
		return Operate_Time;
	}
	public void setOperate_Time(Timestamp operate_Time) {
		Operate_Time = operate_Time;
	}
	public String getTerminal_ID() {
		return Terminal_ID;
	}
	public void setTerminal_ID(String terminalID) {
		Terminal_ID = terminalID;
	}
	public int getOperate_Type() {
		return Operate_Type;
	}
	public void setOperate_Type(int operateType) {
		Operate_Type = operateType;
	}
	public String getOperate_Result() {
		return Operate_Result;
	}
	public void setOperate_Result(String operateResult) {
		Operate_Result = operateResult;
	}
	public String getError_Code() {
		return Error_Code;
	}
	public void setError_Code(String errorCode) {
		Error_Code = errorCode;
	}
	public String getOperate_Name() {
		return Operate_Name;
	}
	public void setOperate_Name(String operateName) {
		Operate_Name = operateName;
	}
	public String getOperate_Condition() {
		return Operate_Condition;
	}
	public void setOperate_Condition(String operateCondition) {
		Operate_Condition = operateCondition;
	}
	public String getBZ1() {
		return BZ1;
	}
	public void setBZ1(String bZ1) {
		BZ1 = bZ1;
	}
	public String getBZ2() {
		return BZ2;
	}
	public void setBZ2(String bZ2) {
		BZ2 = bZ2;
	}
	public String getBZ3() {
		return BZ3;
	}
	public void setBZ3(String bZ3) {
		BZ3 = bZ3;
	}
	public String getBZ4() {
		return BZ4;
	}
	public void setBZ4(String bZ4) {
		BZ4 = bZ4;
	}
	public String getBZ5() {
		return BZ5;
	}
	public void setBZ5(String bZ5) {
		BZ5 = bZ5;
	}
	public String getBZ6() {
		return BZ6;
	}
	public void setBZ6(String bZ6) {
		BZ6 = bZ6;
	}
	public String getBZ7() {
		return BZ7;
	}
	public void setBZ7(String bZ7) {
		BZ7 = bZ7;
	}
	public String getBZ8() {
		return BZ8;
	}
	public void setBZ8(String bZ8) {
		BZ8 = bZ8;
	}
	public String getBZ9() {
		return BZ9;
	}
	public void setBZ9(String bZ9) {
		BZ9 = bZ9;
	}
	public String getBZ10() {
		return BZ10;
	}
	public void setBZ10(String bZ10) {
		BZ10 = bZ10;
	}
	@Override
	public String toString() {
		return "CallingLogging [ID=" + ID
				+ ", Num_ID=" + Num_ID + ", Reg_ID=" + Reg_ID + ", User_ID="
				+ User_ID + ", Organization=" + Organization
				+ ", Organization_ID=" + Organization_ID + ", User_Name="
				+ User_Name + ", Operate_Time=" + Operate_Time
				+ ", Terminal_ID=" + Terminal_ID + ", Operate_Type="
				+ Operate_Type + ", Operate_Result=" + Operate_Result
				+ ", Error_Code=" + Error_Code + ", Operate_Name="
				+ Operate_Name + ", Operate_Condition=" + Operate_Condition
				+ ", BZ1=" + BZ1 + ", BZ2=" + BZ2 + ", BZ3=" + BZ3 + ", BZ4="
				+ BZ4 + ", BZ5=" + BZ5 + ", BZ6=" + BZ6 + ", BZ7=" + BZ7
				+ ", BZ8=" + BZ8 + ", BZ9=" + BZ9 + ", BZ10=" + BZ10 + "]";
	}
	
	
	
}
