package com.KoreaIT.java.JDBCAM.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ArticleController {
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private int articleId;
	private Scanner sc;
	private String cmd;
	private String[] cmdDiv;
	private ArticleService asv;

	public ArticleController(String cmd) {
		articleId = 0;
		sc = new Scanner(System.in);
		this.cmd = cmd;
		cmdDiv = cmd.split(" ");
		asv = new ArticleService();
	}

	public void write() {
		System.out.println("==글쓰기==");
		System.out.print("제목 : ");
		String title = sc.nextLine();
		System.out.print("내용 : ");
		String body = sc.nextLine();
		if (title.length() < 1 || body.length() < 1) {
			System.out.println("올바른 내용을 입력해주세요.");
			return;
		}
		asv.write(title, body);
	}

	public void list() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/JDBC_AM?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

			conn = DriverManager.getConnection(url, "root", "");
			System.out.println("연결 성공!");

			String sql = "SELECT * ";
			sql += " FROM article";
			sql += " ORDER BY id DESC;";

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery(sql);
			System.out.println("== article list ==");

			while (rs.next()) {
				int id = rs.getInt("id");
				String regDate = rs.getString("regDate");
				String title = rs.getString("title");

				System.out.printf("%d  /  %s  /  %s\n", id, title, regDate);
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
				if (pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public void detail() {
		if (cmdDiv.length < 3) {
			System.out.println("명령어를 똑바로 입력해라 인간.");
			return;
		}

		try {
			articleId = Integer.parseInt(cmdDiv[2]);
		} catch (NumberFormatException e) {
			System.out.println("정수를 똑바로 입력해라 인간.");
			return;
		}

		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/JDBC_AM?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

			conn = DriverManager.getConnection(url, "root", "");
			System.out.println("연결 성공!");

			String sql = "SELECT * ";
			sql += " FROM article";
			sql += " WHERE id = " + articleId + ";";

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery(sql);
			System.out.printf("== %s ==\n", cmd);

			while (rs.next()) {
				System.out.println("id : " + rs.getInt("id"));
				System.out.println("regDate : " + rs.getString("regDate"));
				System.out.println("updateDate : " + rs.getString("updateDate"));
				System.out.println("title : " + rs.getString("title"));
				System.out.println("body : " + rs.getString("body"));
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
				if (pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public void modify() {
		if (cmdDiv.length < 3) {
			System.out.println("명령어를 똑바로 입력해라 인간.");
			return;
		}
		try {
			articleId = Integer.parseInt(cmdDiv[2]);
		} catch (NumberFormatException e) {
			System.out.println("정수를 똑바로 입력해라 인간.");
			return;
		}
		String newTitle = "";
		String newBody = "";

		System.out.print("(수정)제목 : ");
		newTitle = sc.nextLine().trim();
		System.out.print("(수정)내용 : ");
		newBody = sc.nextLine().trim();

		boolean status = asv.modify(articleId, newTitle, newBody);

		if (status == false) {
			System.out.println(articleId + "번 게시글은 없습니다.");
			return;
		}

		System.out.println(articleId + "번 게시글이 수정되었습니다.");
	}

	public void remove() {
		if (cmdDiv.length < 3) {
			System.out.println("명령어를 똑바로 입력해라 인간.");
			return;
		}
		try {
			articleId = Integer.parseInt(cmdDiv[2]);
		} catch (NumberFormatException e) {
			System.out.println("정수를 똑바로 입력해라 인간.");
			return;
		}
		boolean status = asv.remove(articleId);

		if (status == false) {
			System.out.println(articleId + "번 게시글은 없습니다.");
			return;
		}

		System.out.printf("%d번 게시글이 삭제되었습니다.\n", articleId);
	}
}
