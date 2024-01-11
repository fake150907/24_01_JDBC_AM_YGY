package com.KoreaIT.java.JDBCAM.controller;

import com.KoreaIT.java.JDBCAM.container.Container;
import com.KoreaIT.java.JDBCAM.dto.Member;
import com.KoreaIT.java.JDBCAM.service.MemberService;

public class MemberController {
	private MemberService memberService;

	public MemberController() {
		this.memberService = Container.memberService;
	}

	public void doJoin() {
		System.out.println("==회원 가입==");
		String loginId = null;
		String loginPw = null;
		String name = null;

		while (true) {
			System.out.print("로그인 아이디 : ");
			loginId = Container.sc.nextLine().trim();

			if (loginId.length() < 2) {
				System.out.println("아이디를 두 글자 이상 입력해주세요.");
				continue;
			}

			if (loginId.contains(" ")) {
				System.out.println("공백을 포함하면 안됩니다.");
				continue;
			}

			boolean isLoginIdDup = memberService.isLoginIdDup(loginId);

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
			loginPw = Container.sc.nextLine().trim();

			if (loginPw.length() < 2) {
				System.out.println("비밀번호를 두 글자 이상 입력해주세요");
				continue;
			}

			if (loginPw.contains(" ")) {
				System.out.println("공백을 포함하면 안됩니다.");
				continue;
			}

			System.out.print("로그인 비밀번호 확인 : ");
			String loginPwConfirm = Container.sc.nextLine();
			if (!loginPw.equals(loginPwConfirm)) {
				System.out.println("비밀번호가 일치하지 않아. 다시 입력해주세요.");
				continue;
			}
			break;
		}
		while (true) {
			System.out.print("이름 : ");
			name = Container.sc.nextLine();

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

		int id = memberService.doJoin(loginId, loginPw, name);

		System.out.printf("%d번 회원이 가입 되었습니다. %s님 환영합니다.\n", id, name);
	}

	public void login() {
		if (Container.session.isLogined()) {
			System.out.println("로그아웃하고 이용해주세요");
			return;
		}
		String loginId = null;
		String loginPw = null;

		System.out.println("==로그인==");
		while (true) {
			System.out.print("로그인 아이디 : ");
			loginId = Container.sc.nextLine().trim();

			if (loginId.length() == 0 || loginId.contains(" ")) {
				System.out.println("아이디 똑바로 입력해");
				continue;
			}

			boolean isLoginIdDup = memberService.isLoginIdDup(loginId);

			if (isLoginIdDup == false) {
				System.out.println(loginId + "는(은) 없는놈이야");
				continue;
			}

			break;
		}

		Member member = memberService.getMemberByLoginId(loginId);

		int tryMaxCount = 3;
		int tryCount = 0;

		while (true) {
			if (tryCount >= tryMaxCount) {
				System.out.println("다시 확인하고 시도해라");
				break;
			}
			System.out.print("비밀번호 : ");
			loginPw = Container.sc.nextLine().trim();

			if (loginPw.length() == 0 || loginPw.contains(" ")) {
				tryCount++;
				System.out.println("비밀번호 똑바로 입력해");
				continue;
			}

			if (member.getLoginPw().equals(loginPw) == false) {
				tryCount++;
				System.out.println("일치하지 않아");
				continue;
			}

			Container.session.login(member);

			System.out.println(member.getName() + "님 환영");
			break;

		}

	}

	public void showProfile() {
		if (Container.session.isLogined() == false) {
			System.out.println("로그인 상태가 아님");
			return;
		} else {
			System.out.println(Container.session.loginedMember);
		}
	}

	public void logout() {
		if (Container.session.isLogined() == false) {
			System.out.println("로그인 상태가 아님");
			return;
		} else {
			Container.session.logout();
		}

	}

}
