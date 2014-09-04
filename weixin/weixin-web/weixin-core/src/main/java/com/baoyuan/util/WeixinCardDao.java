package com.baoyuan.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class WeixinCardDao {
	
	public static final String dbUrl = "jdbc:oracle:thin:@192.168.0.77:1521:wxhyk"; 
	public static final String theUser = "wxhyk"; 
	public static final String thePw = "wxhyk"; 

	/** 连接对象 */
	protected Connection con;
	/** 预编译 */
	protected PreparedStatement ps;
	/** 结果集 */
	protected ResultSet rs;
	
    /** 
     * 获得Oracle连接 
     */  
    public Connection getConn()throws Exception{  
        Class.forName("oracle.jdbc.driver.OracleDriver");  
        return DriverManager.getConnection(dbUrl,theUser,thePw);  
    } 
    
    /** 
     * 关闭连接 
     */  
    public void closeAll(ResultSet rs,PreparedStatement psm,Connection conn)throws Exception{  
        if(rs!=null) rs.close();  
        if(psm!=null) psm.close();  
        if(conn!=null) conn.close();  
    }  
   
}
