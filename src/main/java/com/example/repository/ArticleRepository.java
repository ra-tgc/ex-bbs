package com.example.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Article;
import com.example.domain.Comment;

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
	private static int preId = -1;
	private static Article article = new Article();
	private static List<Comment> commentList = new ArrayList<>();

	private static final RowMapper<Article> ARTICLE_ROW_MAPPER = (rs, i) -> {
		Article article = new Article();
		article.setId(rs.getInt("id"));
		article.setName(rs.getString("name"));
		article.setContent(rs.getString("content"));

		return article;
	};

	private static final ResultSetExtractor<List<Article>> ARTICLE_ROW_MAPPER2 = (rs) -> {

		List<Article> articleList = new ArrayList();
		List<Comment> commentList = new ArrayList<>();
		Article article = new Article();
		int preId = -1;

		while (rs.next()) {
			if (rs.getInt("id") != preId) {
				if (preId != -1) {
					article.setCommentList(commentList);
					articleList.add(article);
				}
				article = new Article();
				commentList = new ArrayList<Comment>();
				article.setId(rs.getInt("id"));
				article.setName(rs.getString("name"));
				article.setContent(rs.getString("content"));
			}
			commentList.add(new Comment(rs.getInt("com_id"), rs.getString("com_name"), rs.getString("com_content"),
					rs.getInt("article_id")));
			preId = rs.getInt("id");
		}
		article.setCommentList(commentList);
		articleList.add(article);

		return articleList;
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

	/**
	 * 記事を挿入する.
	 * 
	 * @param name    投稿者名
	 * @param content 投稿内容
	 */
	public void insert(Article article) {
		String sql = "INSERT INTO articles(name, content) VALUES (:name, :content);";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", article.getName()).addValue("content",
				article.getContent());
		template.update(sql, param);
	}

	/**
	 * 記事を削除する.
	 * 
	 * @param id 主キー
	 */
	public void deleteById(int id) {
		String sql = "DELETE FROM articles WHERE id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);
	}

	public List<Article> findAllWithComment() {
		String sql = "SELECT a.id AS id, a.name AS name, a.content AS content, com.id AS com_id, com.name AS com_name, com.content AS com_content, com.article_id AS article_id FROM articles AS a LEFT OUTER JOIN comments AS com ON a.id = com.article_id ORDER BY a.id DESC, com.id DESC;";
		List<Article> articleList = template.query(sql, ARTICLE_ROW_MAPPER2);

		return articleList;
	}
}
