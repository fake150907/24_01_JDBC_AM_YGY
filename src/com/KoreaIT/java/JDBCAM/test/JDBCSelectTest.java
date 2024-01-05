package com.KoreaIT.java.JDBCAM.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCSelectTest {
	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/JDBC_AM?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

			conn = DriverManager.getConnection(url, "root", "");
			System.out.println("연결 성공!");

			String sql = "SELECT * FROM article;";

			stmt = conn.prepareStatement(sql);

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String id = rs.getString("id");
				String regDate = rs.getString("regDate");
				String updateDate = rs.getString("updateDate");
				String title = rs.getString("title");
				String body = rs.getString("body");

//				System.out.println("id : " + rs.getString("id"));
//				System.out.println("regDate : " + rs.getString("regDate"));
//				System.out.println("updateDate : " + rs.getString("updateDate"));
//				System.out.println("title : " + rs.getString("title"));
//				System.out.println("body : " + rs.getString("body"));

				System.out.printf("%s  /  %s  \n", id, title);
			}

		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패");
		} catch (SQLException e) {
			System.out.println("에러 : " + e);
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (stmt != null && !stmt.isClosed()) {
					stmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}