package com.KoreaIT.java.JDBCAM.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ArticleService {
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private Scanner sc;

	public ArticleService() {
		sc = new Scanner(System.in);
	}

	public void write(Connection conn, String title, String body) {
		try {
			String sql = "INSERT INTO article ";
			sql += "SET regDate = NOW(),";
			sql += "updateDate = NOW(),";
			sql += "title = '" + title + "',";
			sql += "`body`= '" + body + "';";

			pstmt = conn.prepareStatement(sql);

			int affectedRow = pstmt.executeUpdate();

			System.out.println("affectedRow  : " + affectedRow);

			sql = "SELECT * FROM article ORDER BY id DESC LIMIT 1;";

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery(sql);

			while (rs.next()) {
				System.out.println(rs.getInt("id") + "번 게시글이 등록되었습니다.");
			}

		} catch (SQLException e) {
			System.out.println("에러 : " + e);
		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean modify(Connection conn, int articleId, String newTitle, String newBody) {
		try {
			String sql = "UPDATE article ";
			sql += "SET updateDate = NOW() ";
			if (newTitle.length() > 0) {
				sql += ",title = '" + newTitle + "'";
			}
			if (newTitle.length() > 0) {
				sql += ",`body` = '" + newBody + "'";
			}
			sql += " WHERE id = " + articleId;

			pstmt = conn.prepareStatement(sql);

			int r = pstmt.executeUpdate(sql);

			if (r > 0) {
				return true;
			}

		} catch (SQLException e) {
			System.out.println("에러 : " + e);
		} finally {
			try {
				if (rs != null && !rs.isClosed()) {
					rs.close();
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
		return false;
	}

	public boolean remove(Connection conn, int articleId) {
		try {

			String sql = "DELETE FROM article ";
			sql += " WHERE id = " + articleId + ";";

			pstmt = conn.prepareStatement(sql);

			int r = pstmt.executeUpdate(sql);

			if (r > 0) {
				return true;
			}

		} catch (SQLException e) {
			System.out.println("에러 : " + e);
		} finally {
			try {
				if (rs != null && !rs.isClosed()) {
					rs.close();
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
		return false;
	}

}
