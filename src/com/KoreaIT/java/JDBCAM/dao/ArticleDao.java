package com.KoreaIT.java.JDBCAM.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.KoreaIT.java.JDBCAM.Article;
import com.KoreaIT.java.JDBCAM.util.DBUtil;
import com.KoreaIT.java.JDBCAM.util.SecSql;

public class ArticleDao {

	public int doWrite(Connection conn, String title, String body) {
		SecSql sql = new SecSql();

		sql.append("INSERT INTO article");
		sql.append("SET regDate = NOW(),");
		sql.append("updateDate = NOW(),");
		sql.append("title = ?,", title);
		sql.append("`body`= ?;", body);

		int id = DBUtil.insert(conn, sql);

		return id;
	}

	public void doModify(Connection conn, int id, String newTitle, String newBody) {
		SecSql sql = new SecSql();

		sql.append("UPDATE article");
		sql.append("SET updateDate = NOW()");
		if (newTitle.length() > 0) {
			sql.append(",title = ?", newTitle);
		}
		if (newBody.length() > 0) {
			sql.append(",`body`= ?", newBody);
		}

		sql.append("WHERE id = ?;", id);

		DBUtil.update(conn, sql);
	}

	public void doRemove(Connection conn, int id) {
		SecSql sql = new SecSql();

		sql.append("DELETE FROM article");
		sql.append("WHERE id = ?;", id);

		DBUtil.delete(conn, sql);
	}

	public List<Article> showList(Connection conn) {
		List<Article> articles = new ArrayList<>();

		SecSql sql = new SecSql();

		sql.append("SELECT *");
		sql.append("FROM article");
		sql.append("ORDER BY id DESC;");

		List<Map<String, Object>> articleListMap = DBUtil.selectRows(conn, sql);

		for (Map<String, Object> articleMap : articleListMap) {
			articles.add(new Article(articleMap));
		}

		return articles;
	}

	public Map<String, Object> foundArticleMap(Connection conn, int id) {
		SecSql sql = new SecSql();

		sql.append("SELECT *");
		sql.append("FROM article");
		sql.append("WHERE id = ?;", id);

		Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);

		return articleMap;
	}

}
