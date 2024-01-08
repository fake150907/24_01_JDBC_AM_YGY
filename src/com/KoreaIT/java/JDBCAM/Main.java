package com.KoreaIT.java.JDBCAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		System.out.println("==프로그램 시작==");
		Scanner sc = new Scanner(System.in);

		List<Article> articles = new ArrayList<>();

		int lastArticleId = 0;

		while (true) {
			System.out.print("명령어 > ");
			String cmd = sc.nextLine().trim();

			if (cmd.equals("exit")) {
				break;
			}

			if (cmd.equals("article write")) {
				System.out.println("==글쓰기==");
				int id = lastArticleId + 1;
				System.out.print("제목 : ");
				String title = sc.nextLine();
				System.out.print("내용 : ");
				String body = sc.nextLine();

				Article article = new Article(id, title, body);
				articles.add(article);

				lastArticleId = id;

				System.out.println(id + "번 글이 등록되었습니다");

				//////////////////////

				Connection conn = null;
				PreparedStatement pstmt = null;

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

					System.out.println(sql);

					pstmt = conn.prepareStatement(sql);

					int affectedRow = pstmt.executeUpdate();

					System.out.println("affectedRow : " + affectedRow);

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

			} else if (cmd.startsWith("article modify")) {
				String[] cmdDiv = cmd.split(" ");
				if (cmdDiv.length < 3) {
					System.out.println("명령어를 똑바로 입력해라 인간.");
					continue;
				}
				int articleId = Integer.parseInt(cmdDiv[2]);

				Connection conn = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				try {
					Class.forName("com.mysql.jdbc.Driver");
					String url = "jdbc:mysql://127.0.0.1:3306/JDBC_AM?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

					conn = DriverManager.getConnection(url, "root", "");
					System.out.println("연결 성공!");

					System.out.print("(수정)제목 : ");
					String newTitle = sc.nextLine().trim();
					System.out.print("(수정)내용 : ");
					String newBody = sc.nextLine().trim();
					String sql = "UPDATE article ";
					sql += "SET title = " + newTitle + ",";
					sql += "`body` = " + newBody;
					sql += " WHERE id = " + articleId;

					pstmt = conn.prepareStatement(sql);

//					rs = pstmt.executeQuery(sql);

					int r = pstmt.executeUpdate(sql);
					
					if (r > 0) {
						System.out.println("입력 성공");
					} else {
						System.out.println("입력 실패");
					}

					System.out.printf("%d번 게시글이 수정되었습니다.\n", articleId);
					
				} catch (ClassNotFoundException e) {
					System.out.println("드라이버 로딩 실패");
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
			} else if (cmd.equals("article list")) {
				Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet rs = null;
				try {
					Class.forName("com.mysql.jdbc.Driver");
					String url = "jdbc:mysql://127.0.0.1:3306/JDBC_AM?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

					conn = DriverManager.getConnection(url, "root", "");
					System.out.println("연결 성공!");

					String sql = "SELECT * ";
					sql += " FROM article";
					sql += " ORDER BY id DESC;";

					stmt = conn.prepareStatement(sql);

					rs = stmt.executeQuery(sql);
					System.out.println("== article list ==");

					while (rs.next()) {
						int id = Integer.parseInt(rs.getString("id"));
						String regDate = rs.getString("regDate");
						String updateDate = rs.getString("updateDate");
						String title = rs.getString("title");
						String body = rs.getString("body");
						Article article = new Article(id, regDate, updateDate, title, body);
						System.out.printf("%d  /  %s  /  %s\n", id, title, regDate);
						articles.add(article);
					}

				} catch (ClassNotFoundException e) {
					System.out.println("드라이버 로딩 실패");
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

		System.out.println("==프로그램 종료==");

		sc.close();
	}
}