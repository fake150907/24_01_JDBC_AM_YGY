package com.KoreaIT.java.JDBCAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import com.KoreaIT.java.JDBCAM.container.Container;
import com.KoreaIT.java.JDBCAM.controller.ArticleController;
import com.KoreaIT.java.JDBCAM.controller.MemberController;

public class App {

	private Scanner sc;

	public App() {
		Container.init();
		this.sc = Container.sc;
	}

	public void run() {
		System.out.println("==프로그램 시작==");

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

				Container.conn = conn;

				int actionResult = action(cmd);

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

	private int action(String cmd) {

		MemberController mctr = Container.memberController;
		ArticleController actr = Container.articleController;

		String[] cmdBits = cmd.split(" ");

		if (cmdBits.length == 1) {
			System.out.println("명령어를 똑바로 입력해라 인간.");
			return 0;
		}

		if (cmdBits[0].equals("article")) {
			switch (cmdBits[1]) {
			case "write":
				if (isLogined() == false) {
					break;
				}
				actr.doWrite();
				break;
			case "list":
				actr.showList();
				break;
			case "detail":
				actr.showDetail(cmd);
				break;
			case "modify":
				if (isLogined() == false) {
					break;
				}
				actr.doModify(cmd);
				break;
			case "delete":
				if (isLogined() == false) {
					break;
				}
				actr.doRemove(cmd);
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
				mctr.login();
				break;
			case "logout":
				if (isLogined() == false) {
					break;
				}
				mctr.logout();
				break;
			case "profile":
				if (isLogined() == false) {
					break;
				}
				mctr.showProfile();
				break;
			default:
				System.out.println("처리할 수 없는 명령어 입니다.");
			}
		}

		return 0;
	}

	private boolean isLogined() {
		if (Container.session.isLogined() == false) {
			System.out.println("로그인 후 이용해주세요.");
			return false;
		}
		return true;
	}
}
