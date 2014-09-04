package com.baoyuan;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OracleJdbcTest {
	String dbUrl = "jdbc:oracle:thin:@192.168.0.77:1521:wxhyk"; 
	//theUser为数据库用户名 
	String theUser = "wxhyk"; 
	//thePw为数据库密码 
	String thePw = "wxhyk"; 
	//几个数据库变量 
	Connection c = null; 
	Statement conn; 
	ResultSet rs = null; 
	//初始化连接 
	//OracleDriver
	public OracleJdbcTest() { 
		try { 
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance(); 
			//与url指定的数据源建立连接 
			c = DriverManager.getConnection(dbUrl, theUser, thePw); 
			//采用Statement进行查询 
			conn = c.createStatement(); 
		} catch (Exception e) { 
			e.printStackTrace(); 
		} 
	} 
	//执行查询 
	public ResultSet executeQuery(String sql) { 
		rs = null; 
		try { 
			rs = conn.executeQuery(sql); 
		} catch (SQLException e) { 
			e.printStackTrace(); 
		} 
		return rs; 
	} 
	public void close() { 
		try { 
			conn.close(); 
			c.close(); 
		} catch (Exception e) { 
			e.printStackTrace(); 
		} 
	} 
	public static void main(String[] args) { 
		ResultSet newrs; 
		OracleJdbcTest newjdbc = new OracleJdbcTest(); 
		newrs = newjdbc.executeQuery("select * from HYK_HYXX"); 
		try { 
				while (newrs.next()) { 
						System.out.print("会员Id："+newrs.getString("HYID")); 
						System.out.print("\t"+"类型："+newrs.getString("HYKTYPE"));
						System.out.print("\t"+"会员卡号："+newrs.getString("HYK_NO"));
						System.out.print("\t"+"会员名称："+newrs.getString("HY_NAME"));
						System.out.print("\t"+"门店："+newrs.getString("MDID"));
						System.out.print("\t"+"卡状态："+newrs.getString("STATUS"));
				} 
		} catch (Exception e) { 
			e.printStackTrace(); 
		} 
		newjdbc.close(); 
	} 
}
