package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.domain.Article;

/**
 * articlesテーブルを操作するリポジトリ.
 * 
 * @author masaki.taguchi
 *
 */
@Repository
public class ArticleRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Article> ARTICLE_ROW_MAPPER = (rs, i) -> {
		Article article = new Article();
		article.setId(rs.getInt("id"));
		article.setName(rs.getString("name"));
		article.setContent(rs.getString("content"));

		return article;
	};

	/**
	 * 記事をidの降順で全件取得する.
	 * 
	 * @return 記事全件(idの降順)
	 */
	public List<Article> findAll() {
		String sql = "SELECT id, name, content FROM articles ORDER BY id DESC;";
		List<Article> articleList = template.query(sql, ARTICLE_ROW_MAPPER);

		return articleList;
	}
}
