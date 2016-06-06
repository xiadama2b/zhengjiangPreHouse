package zhenjiangPreHouse;

import java.awt.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DBOperation {
	private final String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private final String url = "jdbc:sqlserver://localhost:1433;DataBaseName=SuperViseHouse";
	private final String user = "sa";
	private final String password = "sa";
	private final String SUCCESS = "Success";
	private final String FAIL_DRIVER = "Driver fail";
	private final String FAIL_CONNECT = "Connect database fail";
	private final String FAIL_QUERY = "Query sql fail";
	private final String FAIL_CONNECTION_CLOSE = "Close connection fail";
	private final String FAIL_STATEMENT = "Create statement fail";
	private final String FAIL_STATEMENT_CLOSE = "Close statement fail";
	
	public String getSUCCESS(){
		return this.SUCCESS;
	}
	
	public String getFAIL_DRIVER(){
		return this.FAIL_DRIVER;
	}
	
	public String getFAIL_CONNECT(){
		return this.FAIL_CONNECT;
	}
	
	public String getFAIL_QUERY(){
		return this.FAIL_QUERY;
	}
	
	public String getFAIL_CONNECTION_CLOSE(){
		return this.FAIL_CONNECTION_CLOSE;
	}
	
	public String getFAIL_STATEMENT(){
		return this.FAIL_STATEMENT;
	}
	
	public String getFAIL_STATEMENT_CLOSE(){
		return this.FAIL_STATEMENT_CLOSE;
	}
	
	public Connection dbConnect(StringBuffer ret_msg){
		Connection conn = null;
		try {
			Class.forName(this.driverName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ret_msg.append(this.FAIL_DRIVER);
			return null;
		}
		
		try {
			conn = DriverManager.getConnection(this.url, this.user, this.password);
			System.out.println("connection success");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ret_msg.append(this.FAIL_CONNECT);
			return null;
		}
		
		ret_msg.append(this.SUCCESS);
		System.out.println(ret_msg+"72");
		return conn;
	}
	
	public void dbClose(Connection conn, StringBuffer ret_msg){
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ret_msg.append(this.FAIL_CONNECTION_CLOSE);
			return;
		}
	}
	
	public ArrayList<HashMap<String, Object>> SQLQuery( String sql, ResultSet rs, StringBuffer ret_msg){
		Connection conn = null;
		conn = this.dbConnect(ret_msg);
		System.out.println(ret_msg+"90");
		if (!ret_msg.toString().equals(this.SUCCESS)) {
			rs = null;
			System.out.println(ret_msg+"93");
			return null;
		}
		
		ret_msg.delete(0, ret_msg.length());
		
		System.out.println(ret_msg+"12111");
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ret_msg.append(this.FAIL_STATEMENT);
			return null;
		}
		
		ArrayList<HashMap<String, Object>> list = this.convertList(rs);
		
		
		try {
			stmt.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			ret_msg.append(this.FAIL_STATEMENT_CLOSE);
			return null;
		}
		
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ret_msg.append(this.FAIL_CONNECTION_CLOSE);
			return null;
		}
		
		ret_msg.append(this.SUCCESS);
		
		return list;

	}
	
	private ArrayList<HashMap<String, Object>> convertList( ResultSet rs ){
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		try {
			ResultSetMetaData md = rs.getMetaData();
			int columnCount = md.getColumnCount();
			while (rs.next()) {
				HashMap<String, Object> rowData = new HashMap<String, Object>();
				String colName = "";
				for (int i = 1; i < columnCount; i++) {
					colName = md.getColumnName(i);
					rowData.put(colName, rs.getObject(i));
				}
				list.add(rowData);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
			} catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return list;
	}
	

}
