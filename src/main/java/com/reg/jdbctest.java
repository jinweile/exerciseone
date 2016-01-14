package com.reg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

/**
 * 模拟数据库并发操作测试
 * @author jinweile
 *
 */
public class jdbctest {
	
	private static Connection dbConn;
	
	private static String sql = "INSERT INTO users (USERNAME, PASSWORD) SELECT 'AAA','BBB' FROM dual WHERE not exists (select * from users where USERNAME='AAA' and PASSWORD='BBB');";
	
	private static String sql1 = "INSERT INTO users (USERNAME, PASSWORD) values ('AAA','BBB')";
	
	private static String sql2 = "select * from users where USERNAME='AAA' and PASSWORD='BBB'";
	
	private static String url;
	
	public static void main(String[] args) throws Exception {
		String driverName = "com.mysql.jdbc.Driver";
		Class.forName(driverName);
		url = "jdbc:mysql://localhost:3306/test";
		dbConn = DriverManager.getConnection(url, "root", "123456");
		for(int i = 0; i < 999; i++){
			ThreadTest test = new ThreadTest(i);
			test.start();
		}
		
		System.in.read();
		dbConn.close();
	}
	
	public static class ThreadTest extends Thread {
		
		private int id;
		
		public ThreadTest(int id){
			this.id = id;
		}
		
		public void run() {
			Statement stat = null;
			try {
				stat = dbConn.createStatement();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				ResultSet result = stat.executeQuery(sql2);
				result.last();
				if(result.getRow() == 0){
					stat.executeUpdate(sql1);
				}
				stat.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(id);
		}
	}

}
