/**
 * 
 */
package com.genericdbclient.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Cezara C.
 * @author Marian F.
 * @version 1.0
 */

/*this is the interface??*/
/*should have an querybuilder class*/
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
	
	public static List<String> getObjectNames(String objectType) throws SQLException {
		
		List<String> objectNames = new ArrayList<String>();
		
		Connection conn = DbUtils.getConnection();
		
		/*this should be the XML file?*/
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
	
	public static List<List<String>> getAll(String tableName) throws SQLException {
		
		List<List<String>> data = new ArrayList<List<String>>();
		
		Connection conn = DbUtils.getConnection();
		
		/*this should be the XML file?*/
		String query = String.format("SELECT * FROM %s", tableName);
		PreparedStatement pstmt = conn.prepareStatement(query);
		
		ResultSet rs = pstmt.executeQuery();
		
		int columnCount = rs.getMetaData().getColumnCount();
		
		while(rs.next()) {
			List<String> row = new ArrayList<String>();
			
			for(int indx = 1; indx <= columnCount; ++indx) {
				
				String columnValue = rs.getString(indx);
				
				if(rs.wasNull()) {
					columnValue = "null";
				}
				else {
				
					ResultSetMetaData rsmd = rs.getMetaData();

					if(rsmd.getColumnClassName(indx).contentEquals("java.sql.Timestamp"))
						columnValue = getParsedDate(columnValue);
				}
				
				row.add(columnValue);	
			}
			
			data.add(row);
		}
		
		DbUtils.closeAll(rs, pstmt, conn);
		
		return data;
	}
	
	public static List<List<String>> getBy(String tableName, String filterClause) throws SQLException {
		
		List<List<String>> data = new ArrayList<List<String>>();
		
		Connection conn = DbUtils.getConnection();
		
		StringBuilder queryBuilder = new StringBuilder();
		
		queryBuilder.append("SELECT * FROM ").append(tableName).append(" WHERE ").append(filterClause.toUpperCase());
		
		PreparedStatement pstmt = conn.prepareStatement(queryBuilder.toString());
		
		ResultSet rs = pstmt.executeQuery();
		
		int columnCount = rs.getMetaData().getColumnCount();
		
		while(rs.next()) {
			List<String> row = new ArrayList<String>();
			
			for(int indx = 1; indx <= columnCount; ++indx) {
				
				String columnValue = rs.getString(indx);
				
				if(rs.wasNull()) {
					columnValue = "null";
				}
				else {
				
					ResultSetMetaData rsmd = rs.getMetaData();

					if(rsmd.getColumnClassName(indx).contentEquals("java.sql.Timestamp"))
						columnValue = getParsedDate(columnValue);
				}
				
				row.add(columnValue);	
			}
			
			data.add(row);
		}
		
		DbUtils.closeAll(rs, pstmt, conn);
		
		return data;
	}
	
	public static void insert(String tableName, List<String> columnsNames, List<String> columnsValues) throws SQLException {
		
		Connection conn = DbUtils.getConnection();
		
		StringBuilder queryBuilder = new StringBuilder();
		
		queryBuilder.append("INSERT INTO ").append(tableName).append(" (");
		
		for (String column : columnsNames)
			queryBuilder.append(column).append(",");
		
		queryBuilder.deleteCharAt(queryBuilder.length()-1);
		
		queryBuilder.append(") VALUES(");
		
		int columnCount = columnsNames.size();
		for (int indx = 0; indx < columnCount; ++indx)
			queryBuilder.append("?,");
		
		queryBuilder.deleteCharAt(queryBuilder.length()-1).append(")");
		
		PreparedStatement pstmt = conn.prepareStatement(queryBuilder.toString());
		
		for(int indx = 0; indx < columnCount; ++indx) {
			
			pstmt.setString(indx + 1, columnsValues.get(indx));
		}
		
		int rowsAffected = pstmt.executeUpdate();
		
		logger.log(Level.INFO, "Rows affected: " + rowsAffected);
		
		DbUtils.closeAll(null, pstmt, conn);
	}
	
	/*should test for null differently when with Oracle connection*/
	public static void updateValue
		(String tableName, String columnName, String value, List<String> columnsNames, List<String> columnsValues) 
				throws SQLException {
		
		Connection conn = DbUtils.getConnection();
		
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("UPDATE ").append(tableName).append(" SET ").append(columnName).append(" = ? WHERE ");
		
		for(int indx = 0; indx < columnsNames.size(); ++indx) {
			
			queryBuilder.append(columnsNames.get(indx));
			if(columnsValues.get(indx).contentEquals("null"))
				queryBuilder.append(" IS NULL AND ");
			else
				queryBuilder.append(" = ").append("? AND ");
		}
		
		String query = queryBuilder.substring(0, queryBuilder.length() - 5);
		
		logger.log(Level.INFO, "Updated query: " + query);
		
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setString(1, value);
		
		int currentPlaceholder = 2;
		for(int indx = 0; indx < columnsValues.size(); ++indx) {
			
			if(!columnsValues.get(indx).contentEquals("null"))
				pstmt.setString(currentPlaceholder++, columnsValues.get(indx));
		}
		
		int rowsAffected = pstmt.executeUpdate();
		
		logger.log(Level.INFO, "Updated Rows affected: " + rowsAffected);
		
		DbUtils.closeAll(null, pstmt, conn);
	}
	
	/*should test for null differently when working with Oracle connection*/
	public static void delete(String tableName, List<String> columnsNames, List<String> columnsValues) throws SQLException {
		
		Connection conn = DbUtils.getConnection();
		
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("DELETE FROM ").append(tableName).append(" WHERE ");
		
		for(int indx = 0; indx < columnsNames.size(); ++indx) {
			
			queryBuilder.append(columnsNames.get(indx));
			
			if(columnsValues.get(indx).contentEquals("null"))
				queryBuilder.append(" IS NULL AND ");
			else
				queryBuilder.append(" = ").append("? AND ");
		}
		
		String query = queryBuilder.substring(0, queryBuilder.length() - 5);
		
		logger.log(Level.INFO, "Delete query: " + query);
		
		PreparedStatement pstmt = conn.prepareStatement(query);

		int currentPlaceholder = 1;
		for(int indx = 0; indx < columnsValues.size(); ++indx) {

			if(!columnsValues.get(indx).contentEquals("null"))
				pstmt.setString(currentPlaceholder++, columnsValues.get(indx));
		}

		int rowsAffected = pstmt.executeUpdate();

		logger.log(Level.INFO, "Delete rows affected: " + rowsAffected);

		DbUtils.closeAll(null, pstmt, conn);
		
	}
	
	public static List<String> getReferencedTables(String tableName) throws SQLException {
		
		List<String> referencedTables = new ArrayList<String>();
		
		Connection conn = DbUtils.getConnection();
		
		DatabaseMetaData dbmd = conn.getMetaData();
		
		ResultSet rs = dbmd.getExportedKeys(conn.getCatalog(), null, tableName);
		
		while (rs.next())
			referencedTables.add(rs.getString("FKTABLE_NAME"));
		
		DbUtils.closeAll(rs, null, conn);
		
		return referencedTables;
	}
	
	/*these should go in another utility class*/
	private static String getParsedDate(String columnValue) {
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			Date date = df.parse(columnValue);
			columnValue = df.format(date);
			
			df = new SimpleDateFormat("dd-MMM-yyyy");
			columnValue = df.format(date).toUpperCase();
			
			return columnValue;
			
		} catch (ParseException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			return null;
		}
	}
}
