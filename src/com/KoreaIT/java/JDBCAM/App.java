package com.KoreaIT.java.JDBCAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import com.KoreaIT.java.JDBCAM.controller.ArticleController;
import com.KoreaIT.java.JDBCAM.controller.MemberController;

public class App {

	public void run() {
		System.out.println("==프로그램 시작==");
		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.print("명령어 > ");
			String cmd = sc.nextLine().trim();

			Connection conn = null;

			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			String url = "jdbc:mysql://127.0.0.1:3306/JDBC_AM?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

			try {
				conn = DriverManager.getConnection(url, "root", "");

				int actionResult = action(conn, sc, cmd);

				if (actionResult == -1) {
					System.out.println("==프로그램 종료==");
					sc.close();
					break;
				}

			} catch (SQLException e) {
				System.out.println("에러 1 : " + e);
			} finally {
				try {
					if (conn != null && !conn.isClosed()) {
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private int action(Connection conn, Scanner sc, String cmd) {

		ArticleController actr = new ArticleController(cmd);
		MemberController mctr = new MemberController(conn, sc);

		String[] cmdBits = cmd.split(" ");

		if (cmdBits.length == 1) {
			System.out.println("명령어를 똑바로 입력해라 인간.");
			return 0;
		}

		if (cmdBits[0].equals("article")) {
			switch (cmdBits[1]) {
			case "write":
				actr.doWrite(conn);
				break;
			case "list":
				actr.showList(conn);
				break;
			case "detail":
				actr.showDetail(conn);
				break;
			case "modify":
				actr.doModify(conn);
				break;
			case "delete":
				actr.doRemove(conn);
				break;
			default:
				System.out.println("처리할 수 없는 명령어입니다.");
			}
		}

		if (cmdBits[0].equals("member")) {
			switch (cmdBits[1]) {
			case "join":
				mctr.doJoin();
				break;
			case "login":
				mctr.doLogin();
			default:
				System.out.println("처리할 수 없는 명령어 입니다.");
			}
		}

		return 0;

	}
}
