package com.KoreaIT.java.JDBCAM.service;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.KoreaIT.java.JDBCAM.Article;
import com.KoreaIT.java.JDBCAM.dao.ArticleDao;

public class ArticleService {
	private ArticleDao acd;

	public ArticleService() {
		acd = new ArticleDao();
	}

	public int doWrite(Connection conn, String title, String body) {
		int id = acd.doWrite(conn, title, body);
		return id;
	}

	public void doModify(Connection conn, int id, String newTitle, String newBody) {
		acd.doModify(conn, id, newTitle, newBody);
	}

	public void doRemove(Connection conn, int id) {
		acd.doRemove(conn, id);
	}

	public List<Article> showList(Connection conn) {
		List<Article> articles = acd.showList(conn);
		return articles;
	}

	public Map<String, Object> foundArticleMap(Connection conn, int id) {
		Map<String, Object> aritlceMap = acd.foundArticleMap(conn, id);
		return aritlceMap;
	}
}
