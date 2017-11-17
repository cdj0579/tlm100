package com.unimas.common.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class DButils {
	
	protected static final Logger log = Logger.getLogger("UELogger");

	public static Map<String, String> getDBInfos(DatabaseMetaData dbmd) {
		Map<String, String> infos = new LinkedHashMap<String, String>();
		try {
			// conn Infos
			String value = dbmd.getDatabaseProductName();
			value += "[" + dbmd.getDatabaseMajorVersion();
			value += "." + dbmd.getDatabaseMinorVersion() + "]";
			infos.put("数据库名称和版本", value);

			value = dbmd.getDriverName();
			value += "[" + dbmd.getDriverMajorVersion();
			value += "." + dbmd.getDriverMinorVersion() + "]";
			infos.put("JDBC驱动名称和版本", value);

			value = dbmd.getUserName();
			value += " connect to " + dbmd.getURL();
			infos.put("当前链接信息", value);

			int iValue = dbmd.getJDBCMajorVersion();
			value = String.valueOf(iValue);
			iValue = dbmd.getJDBCMinorVersion();
			value += "." + iValue;
			infos.put("JDBC规范版本", value);

			// 事务
			boolean support = dbmd.supportsTransactions();
			infos.put("是否支持事务", String.valueOf(support));
			// TODO：检查事务支持级别
			support = dbmd.supportsStoredProcedures();
			infos.put("是否支持使用存储过程", String.valueOf(support));
			// others
			support = dbmd.supportsStatementPooling();
			infos.put("是否支持Statement Pooling", String.valueOf(support));
			support = dbmd.supportsBatchUpdates();
			infos.put("是否支持批量更新", String.valueOf(support));
			support = dbmd.supportsGetGeneratedKeys();
			infos.put("是否支持执行语句后检索自动生成的键", String.valueOf(support));
			support = dbmd.supportsConvert();
			infos.put("是否支持SQL类型之间的转换(CONVERT)", String.valueOf(support));
			// SQL
			value = "高级";
			support = dbmd.supportsANSI92FullSQL();
			if (!support) {
				value = "中级";
				support = dbmd.supportsANSI92IntermediateSQL();
				if (!support) {
					value = "初级";
				}
			}
			infos.put("支持ANSI92 QL语法的级别", value);

			support = dbmd.supportsSubqueriesInComparisons();
			infos.put("是否支持比较表达式中的子查询", String.valueOf(support));
			support = dbmd.supportsSubqueriesInExists();
			infos.put("是否支持 EXISTS 表达式中的子查询", String.valueOf(support));
			support = dbmd.supportsSubqueriesInIns();
			infos.put("是否支持 IN 语句中的子查询", String.valueOf(support));
			support = dbmd.supportsCorrelatedSubqueries();
			infos.put("是否支持相关子查询", String.valueOf(support));
			support = dbmd.supportsSubqueriesInQuantifieds();
			infos.put("是否支持量化表达式 (quantified expression) 中的子查询", String
					.valueOf(support));

			support = dbmd.supportsSelectForUpdate();
			infos.put("是否支持 SELECT FOR UPDATE", String.valueOf(support));
			support = dbmd.supportsUnion();
			infos.put("否支持 UNION", String.valueOf(support));
			support = dbmd.supportsUnionAll();
			infos.put("是否支持 UNION ALL", String.valueOf(support));
			support = dbmd.supportsGroupBy();
			infos.put("是否支持 GROUP BY", String.valueOf(support));
			support = dbmd.supportsOuterJoins();
			infos.put("是否支持的外连接", String.valueOf(support));
			support = dbmd.supportsLimitedOuterJoins();
			infos.put("是否为外连接提供受限制的支持", String.valueOf(support));
			support = dbmd.supportsFullOuterJoins();
			infos.put("是否支持完全嵌套的外连接", String.valueOf(support));
			iValue = dbmd.getMaxStatementLength();
			infos.put("允许在 SQL 语句中使用的最大字符数", String.valueOf(iValue));

			value = dbmd.getSQLKeywords();
			infos.put("非SQL92标准的关键字", value);
			value = dbmd.getSystemFunctions();
			infos.put("可用的系统函数", value);
			value = dbmd.getNumericFunctions();
			infos.put("可用于数值类型的数学函数", value);
			value = dbmd.getStringFunctions();
			infos.put("可用于字符串类型的函数", value);
			value = dbmd.getTimeDateFunctions();
			infos.put("可用于时间和日期类型的函数", value);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return infos;
	}

	public static void closeConnection(Connection conn) {
		try {
			if(conn != null){
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
	}

	public static void closeStatement(PreparedStatement stmt) {
		try {
			if(stmt != null){
				stmt.close();
				stmt = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
	}
	
}
