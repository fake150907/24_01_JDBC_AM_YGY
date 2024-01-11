package com.KoreaIT.java.JDBCAM.controller;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.KoreaIT.java.JDBCAM.Article;
import com.KoreaIT.java.JDBCAM.service.ArticleService;
import com.KoreaIT.java.JDBCAM.util.Util;

public class ArticleController {
	private Scanner sc;
	private String cmd;
	private ArticleService asv;

	public ArticleController(String cmd) {
		sc = new Scanner(System.in);
		this.cmd = cmd;
		asv = new ArticleService();
	}

	public void doWrite(Connection conn) {
		System.out.println("==글쓰기==");
		System.out.print("제목 : ");
		String title = sc.nextLine();
		System.out.print("내용 : ");
		String body = sc.nextLine();

		if (title.length() < 1 || body.length() < 1) {
			System.out.println("올바른 내용을 입력해주세요.");
			return;
		}

		int id = asv.doWrite(conn, title, body);

		System.out.println(id + "번 글이 생성되었습니다");
	}

	public void showList(Connection conn) {
		System.out.println("==목록==");

		List<Article> articles = asv.showList(conn);

		if (articles.size() == 0) {
			System.out.println("게시글이 없습니다");
			return;
		}

		System.out.println("  번호  /   제목  ");

		for (Article article : articles) {
			System.out.printf("  %d     /   %s   \n", article.getId(), article.getTitle());
		}
	}

	public void showDetail(Connection conn) {

		int id = 0;

		try {
			id = Integer.parseInt(cmd.split(" ")[2]);
		} catch (Exception e) {
			System.out.println("번호는 정수로 입력해");
			return;
		}

		System.out.println("==상세보기==");

		Map<String, Object> articleMap = asv.foundArticleMap(conn, id);

		if (articleMap.isEmpty()) {
			System.out.println(id + "번 글은 없습니다.");
			return;
		}

		Article article = new Article(articleMap);

		System.out.println("번호 : " + article.getId());
		System.out.println("작성날짜 : " + Util.getNowDate_TimeStr(article.getRegDate()));
		System.out.println("수정날짜 : " + Util.getNowDate_TimeStr(article.getUpdateDate()));
		System.out.println("제목 : " + article.getTitle());
		System.out.println("내용 : " + article.getBody());

	}

	public void doModify(Connection conn) {
		int id = 0;

		try {
			id = Integer.parseInt(cmd.split(" ")[2]);
		} catch (Exception e) {
			System.out.println("번호는 정수로 입력해");
			return;
		}

		Map<String, Object> articleMap = asv.foundArticleMap(conn, id);

		if (articleMap.isEmpty()) {
			System.out.println(id + "번 글은 없습니다.");
			return;
		}

		System.out.println("==수정==");
		System.out.print("새 제목 : ");
		String title = sc.nextLine().trim();
		System.out.println("새 내용 : ");
		String body = sc.nextLine().trim();

		asv.doModify(conn, id, title, body);

		System.out.println(id + "번 글이 수정되었습니다.");
	}

	public void doRemove(Connection conn) {
		int id = 0;

		try {
			id = Integer.parseInt(cmd.split(" ")[2]);
		} catch (Exception e) {
			System.out.println("번호는 정수로 입력해");
			return;
		}

		Map<String, Object> articleMap = asv.foundArticleMap(conn, id);

		if (articleMap.isEmpty()) {
			System.out.println(id + "번 글은 없습니다.");
			return;
		}

		asv.doRemove(conn, id);

		System.out.println(id + "번 글이 삭제되었습니다.");
	}

}
