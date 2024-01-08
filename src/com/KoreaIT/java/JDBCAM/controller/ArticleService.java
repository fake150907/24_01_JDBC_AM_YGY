package com.KoreaIT.java.JDBCAM.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ArticleService {
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	public void write(String title, String body) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/JDBC_AM?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

			conn = DriverManager.getConnection(url, "root", "");
			System.out.println("연결 성공!");

			String sql = "INSERT INTO article ";
			sql += "SET regDate = NOW(),";
			sql += "updateDate = NOW(),";
			sql += "title = '" + title + "',";
			sql += "`body`= '" + body + "';";

			pstmt = conn.prepareStatement(sql);

			int affectedRow = pstmt.executeUpdate();

			System.out.println("affectedRow  : " + affectedRow);

			sql = "SELECT * FROM article " + "ORDER BY id DESC " + "LIMIT 1;";

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery(sql);

			while (rs.next()) {
				System.out.println(rs.getInt("id") + "번 게시글이 등록되었습니다.");
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
				if (pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
