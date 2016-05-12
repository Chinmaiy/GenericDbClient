/**
 * 
 */
package com.genericdbclient.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * 
 * @author Cezara C.
 * @author Marian F.
 * @version 1.0
 */
public class DbUtils {
	
	private static final Logger logger = Logger.getLogger(DbUtils.class.getName());
	
	/*this will go in a configuration file that the user must upload to connect to the desired database*/
	private static String JDBC_URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static String USER = "STUDENT";
	private static String PASSWORD = "STUDENT";
	private static String DRIVERCLASS = "oracle.jdbc.driver.OracleDriver";
	
	static {
		try {
			Class.forName(DRIVERCLASS);
			/*here read the configuration variables*/
		}
		catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	
	public static Connection getConnection() throws SQLException {
		
		return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
	}
	
	public static void closeAll(ResultSet rs, Statement stmt, Connection conn) {
		
		try {
			if(rs != null) 
				rs.close();
		}
		catch (SQLException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		
		try {
			if(stmt != null)
				stmt.close();
		}
		catch (SQLException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		
		try {
			if(conn != null)
				conn.close();
		}
		catch (SQLException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	
	/*could get a map (key - name of object, value - type of object to just have this method called just once*/
	public static List<String> getObjectNames(String objectType) throws SQLException {
		
		List<String> objectNames = new ArrayList<String>();
		
		Connection conn = DbUtils.getConnection();
		
		String query = "SELECT OBJECT_NAME FROM USER_OBJECTS WHERE UPPER(OBJECT_TYPE) = UPPER(?)";
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setString(1, objectType);
		
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next())
			objectNames.add(rs.getString(1));
		
		DbUtils.closeAll(rs, pstmt, conn);
		
		return objectNames;
	}
	
	public static List<String> getTableColumns(String tableName) throws SQLException {
		
		List<String> columnNames = new ArrayList<String>();
		
		Connection conn = DbUtils.getConnection();
		
		ResultSet rs = conn.getMetaData().getColumns(null, null, tableName, null);
		
		while(rs.next())
			columnNames.add(rs.getString("COLUMN_NAME"));
		
		DbUtils.closeAll(rs, null, conn);
		
		return columnNames;
	}
}
