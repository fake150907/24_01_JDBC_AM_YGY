package com.KoreaIT.java.JDBCAM;

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
				actr.write();
				break;
			case "list":
				actr.list();
				break;
			case "detail":
				actr.detail();
				break;
			case "modify":
				actr.modify();
				break;
			case "delete":
				actr.remove();
				break;
			default:
				System.out.println("처리할 수 없는 명령어입니다.");
			}
		}

		System.out.println("==프로그램 종료==");

		sc.close();
	}

}
