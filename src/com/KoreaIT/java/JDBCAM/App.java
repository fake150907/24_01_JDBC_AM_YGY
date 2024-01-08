package com.KoreaIT.java.JDBCAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import com.KoreaIT.java.JDBCAM.controller.ArticleController;

public class App {

	public void run() {
		System.out.println("==프로그램 시작==");
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.print("명령어 > ");
			String cmd = sc.nextLine().trim();
			ArticleController actr = new ArticleController(cmd);
			Connection conn = null;

			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			String url = "jdbc:mysql://127.0.0.1:3306/JDBC_AM?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

			try {
				conn = DriverManager.getConnection(url, "root", "");

			} catch (SQLException e) {
				System.out.println("에러 : " + e);
			}

			if (cmd.equals("exit")) {
				break;
			}

			String[] cmdBits = cmd.split(" ");

			if (cmdBits.length == 1) {
				System.out.println("명령어를 똑바로 입력해라 인간.");
				continue;
			}
			switch (cmdBits[1]) {
			case "write":
				actr.write(conn);
				break;
			case "list":
				actr.list(conn);
				break;
			case "detail":
				actr.detail(conn);
				break;
			case "modify":
				actr.modify(conn);
				break;
			case "delete":
				actr.remove(conn);
				break;
			default:
				System.out.println("처리할 수 없는 명령어입니다.");
			}
			closeConn(conn);
		}

		System.out.println("==프로그램 종료==");

		sc.close();
	}

	private void closeConn(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
