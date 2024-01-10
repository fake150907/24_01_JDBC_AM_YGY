package com.KoreaIT.java.JDBCAM.controller;

import java.sql.Connection;
import java.util.Scanner;

import com.KoreaIT.java.JDBCAM.util.DBUtil;
import com.KoreaIT.java.JDBCAM.util.SecSql;

public class MemberController {
	Connection conn;
	Scanner sc;

	public MemberController(Connection conn, Scanner sc) {
		this.conn = conn;
		this.sc = sc;
	}

	public void doJoin() {
		System.out.println("==회원 가입==");
		String loginId = null;
		String loginPw = null;
		String name = null;

		while (true) {
			System.out.print("로그인 아이디 : ");
			loginId = sc.nextLine().trim();

			if (loginId.length() < 2) {
				System.out.println("아이디를 두 글자 이상 입력해주세요.");
				continue;
			}

			if (loginId.contains(" ")) {
				System.out.println("공백을 포함하면 안됩니다.");
				continue;
			}

			SecSql sql = new SecSql();
			sql.append("SELECT COUNT(*) > 0");
			sql.append("FROM `member`");
			sql.append("WHERE loginId = ?;", loginId);

			boolean isLoginIdDup = DBUtil.selectRowBooleanValue(conn, sql);

			if (isLoginIdDup) {
				System.out.println("이미 있는 아이디입니다 다시입력해주세요.");
				continue;
			} else {
				System.out.println("사용할 수 있는 아이디입니다.");
				break;
			}
		}
		while (true) {
			System.out.print("로그인 비밀번호 : ");
			loginPw = sc.nextLine().trim();

			if (loginPw.length() < 2) {
				System.out.println("비밀번호를 두 글자 이상 입력해주세요");
				continue;
			}

			if (loginPw.contains(" ")) {
				System.out.println("공백을 포함하면 안됩니다.");
				continue;
			}

			System.out.print("로그인 비밀번호 확인 : ");
			String loginPwConfirm = sc.nextLine();
			if (!loginPw.equals(loginPwConfirm)) {
				System.out.println("비밀번호가 일치하지 않아. 다시 입력해주세요.");
				continue;
			}
			break;
		}
		while (true) {
			System.out.print("이름 : ");
			name = sc.nextLine();

			if (loginPw.length() < 2) {
				System.out.println("비밀번호를 두 글자 이상 입력해주세요");
				continue;
			}

			if (loginPw.contains(" ")) {
				System.out.println("공백을 포함하면 안됩니다.");
				continue;
			}

			break;
		}

		SecSql sql = new SecSql();

		sql.append("INSERT INTO `member`");
		sql.append("SET regDate = NOW(),");
		sql.append("updateDate = NOW(),");
		sql.append("loginId = ?,", loginId);
		sql.append("loginPw = ?,", loginPw);
		sql.append("`name` = ?;", name);

		int id = DBUtil.insert(conn, sql);

		System.out.printf("%d번 회원이 가입 되었습니다. %s님 환영합니다.\n", id, name);
	}

	public void doLogin() {
		// TODO Auto-generated method stub
		System.out.println("일단 git push해야해서 급하다.");
	}

}
