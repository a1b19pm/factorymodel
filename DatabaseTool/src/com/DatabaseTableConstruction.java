package com;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class DatabaseTableConstruction {
	final static String url = "jdbc:mysql://127.0.0.1:3366/grwth_pay?characterEncoding=utf8";
	final static String user = "root";
	final static String pwd = "root";
	
	
	final static int showColumnNum = 5;
	static String[] titleArr = new String[showColumnNum];
	static{
		titleArr[0] = "字段名称";
		titleArr[1] = "数据类型名";
		titleArr[2] = "类型的长度";
		titleArr[3] = "小数点后的位数";
		titleArr[4] = "是否为空";
//		titleArr[5] = "对应的表名";
	}
	
	/**
	 * 从数据库中获取数据
	 * @return
	 */
	public static List<String[]> getDataForDatabase(String sqlTableName){
		List<String[]> dataList = new LinkedList<String[]>();
		Connection conn = getConn();
		String sql = "select * from "+sqlTableName+" limit 1";
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(sql);
			ResultSet rs=stmt.executeQuery(sql);
			ResultSetMetaData data=rs.getMetaData();
//			System.out.println("总列数："+data.getColumnCount());
			while(rs.next()){
				for(int i = 1 ; i<= data.getColumnCount() ; i++){
					//获得指定列的列名
					String columnName = data.getColumnName(i);
					//获得指定列的数据类型名
					String columnTypeName=data.getColumnTypeName(i);
					//某列类型的精确度(类型的长度)
					int precision= data.getPrecision(i);
					//小数点后的位数
					int scale=data.getScale(i);
					//是否为空
					int isNullable=data.isNullable(i);
//					//获取某列对应的表名
//					String tableName=data.getTableName(i);
//					System.out.println("获得列"+i+"的字段名称:"+columnName);
//					System.out.println("获得列"+i+"的数据类型名:"+columnTypeName);
//					System.out.println("获得列"+i+"类型的精确度(类型的长度):"+precision);
//					System.out.println("获得列"+i+"小数点后的位数:"+scale);
//					System.out.println("获得列"+i+"是否为空:"+isNullable);
//					System.out.println("获得列"+i+"对应的表名:" + tableName);
					
					String[] columnDataArr = new String[showColumnNum];
					columnDataArr[0] = columnName;
					columnDataArr[1] = columnTypeName;
					columnDataArr[2] = toString(precision);
					columnDataArr[3] = toString(scale);
					columnDataArr[4] = isNullable==0?"不为空":"可为空";//0为非空1为空
//					columnDataArr[5] = tableName;
					dataList.add(columnDataArr);
				}
			}
		} catch (SQLException e) {
			System.out.println("数据库连接失败");
		}
		return dataList;
	}
	
	/**
	 * 获取数据库连接
	 * @return
	 */
	private static Connection getConn(){
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url,user,pwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	/**
	 * 从连接字符串中获取数据库名
	 * @return
	 */
	public static String getDatabaseName(){
		String databaseName = "";
		String database = url;
		int startIndex = database.lastIndexOf("/");
		int endIndex = database.lastIndexOf("?");
		if(endIndex == -1){
			endIndex = database.length();
		}
		databaseName = database.substring(startIndex+1, endIndex);
		return databaseName;
	}
	
	/**
	 * 获取所有表
	 * @param conn
	 * @param user
	 * @param database
	 * @return
	 */
	public static List<String> getAllTableNames() {
		Connection conn = getConn();
		String database = getDatabaseName();
		List<String> tableNames = new ArrayList<String>();
		if (conn != null) {
			try {
				DatabaseMetaData dbmd = conn.getMetaData();
				// 表名列表 
				ResultSet rest = dbmd.getTables(database, null, null, new String[] { "TABLE" });
				// 输出 table_name
				while (rest.next()) {
//					String tableSchem = rest.getString("TABLE_SCHEM");
//					if (user.equalsIgnoreCase(tableSchem)) {
						tableNames.add(rest.getString("TABLE_NAME"));
//					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return tableNames;
	}
	

	private static String toString(Object obj){
		return obj==null?"":obj.toString();
	}
}
