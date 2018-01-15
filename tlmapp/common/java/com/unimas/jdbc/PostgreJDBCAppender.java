package com.unimas.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.jdbc.JDBCAppender;
import org.apache.log4j.spi.LoggingEvent;

public class PostgreJDBCAppender extends JDBCAppender {

	@Override
	protected Connection getConnection() throws SQLException {
		try {
			return DBFactory.getConn();
		} catch (Exception e) {
			throw new SQLException(e);
		}
	}

	@Override
	public void append(LoggingEvent event) {
		super.setSql("INSERT INTO \"public\".\"operator_audit\" (\"Reg_ID\",\"User_ID\",\"Organization\" ,\"Organization_ID\",\"User_Name\",\"Operate_Time\",\"Terminal_ID\",\"Operate_Type\",\"Operate_Result\",\"Error_Code\",\"Operate_Name\",\"Operate_Condition\",\"BZ1\",\"BZ2\",\"BZ3\",\"BZ4\",\"BZ5\",\"BZ6\",\"BZ7\",\"BZ8\",\"BZ9\",\"BZ10\") VALUES ('%X{Reg_ID}', '%X{User_ID}','%X{Organization}', '%X{Organization_ID}','%X{User_Name}', '%X{Operate_Time}','%X{Terminal_ID}', '%X{Operate_Type}','%X{Operate_Result}', '%X{Error_Code}','%X{Operate_Name}', '%X{Operate_Condition}','%X{BZ1}', '%X{BZ2}', '%X{BZ3}', '%X{BZ4}', '%X{BZ5}', '%X{BZ6}', '%X{BZ7}', '%X{BZ8}', '%X{BZ9}', '%X{BZ10}')");
		super.append(event);
	}

}
