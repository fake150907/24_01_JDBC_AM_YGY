package com.KoreaIT.java.JDBCAM.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.KoreaIT.java.JDBCAM.container.Container;
import com.KoreaIT.java.JDBCAM.dto.Article;
import com.KoreaIT.java.JDBCAM.util.DBUtil;
import com.KoreaIT.java.JDBCAM.util.SecSql;

public class ArticleDao {

	public int doWrite(String title, String body, String name) {
		SecSql sql = new SecSql();

		sql.append("INSERT INTO article");
		sql.append("SET regDate = NOW(),");
		sql.append("updateDate = NOW(),");
		sql.append("title = ?,", title);
		sql.append("`body`= ?,", body);
		sql.append("`name`= ?;", name);

		int id = DBUtil.insert(Container.conn, sql);

		return id;
	}

	public void doModify(int id, String newTitle, String newBody) {
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

		DBUtil.update(Container.conn, sql);
	}

	public void doRemove(int id) {
		SecSql sql = new SecSql();

		sql.append("DELETE FROM article");
		sql.append("WHERE id = ?;", id);

		DBUtil.delete(Container.conn, sql);
	}

	public List<Article> showList() {
		List<Article> articles = new ArrayList<>();

		SecSql sql = new SecSql();

		sql.append("SELECT *");
		sql.append("FROM article");
		sql.append("ORDER BY id DESC;");

		List<Map<String, Object>> articleListMap = DBUtil.selectRows(Container.conn, sql);

		for (Map<String, Object> articleMap : articleListMap) {
			articles.add(new Article(articleMap));
		}

		return articles;
	}

	public Map<String, Object> foundArticleMap(int id) {
		SecSql sql = new SecSql();

		sql.append("SELECT *");
		sql.append("FROM article");
		sql.append("WHERE id = ?;", id);

		Map<String, Object> articleMap = DBUtil.selectRow(Container.conn, sql);

		return articleMap;
	}

}
