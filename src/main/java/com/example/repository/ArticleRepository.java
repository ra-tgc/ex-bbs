package com.example.repository;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
	@Autowired
	private JdbcTemplate jdbcTemp;

	private static final RowMapper<Article> ARTICLE_ROW_MAPPER = (rs, i) -> {
		Article article = new Article();
		article.setId(rs.getInt("id"));
		article.setName(rs.getString("name"));
		article.setContent(rs.getString("content"));

		return article;
	};

	private static final ResultSetExtractor<List<Article>> ARTICLE_RESULT_SET_EXTRACTOR = (rs) -> {

		List<Article> articleList = new ArrayList<>();
		List<Comment> commentList = null;
		Article article = new Article();
		int preId = -1;

		while (rs.next()) {
			if (rs.getInt("id") != preId) {
				article = new Article();
				commentList = new ArrayList<Comment>();
				article.setId(rs.getInt("id"));
				article.setName(rs.getString("name"));
				article.setContent(rs.getString("content"));
				article.setCommentList(commentList);
				articleList.add(article);
			}

			if (rs.getString("com_content") != null) {
				commentList.add(new Comment(rs.getInt("com_id"), rs.getString("com_name"), rs.getString("com_content"),
						rs.getInt("article_id")));
			}
			preId = rs.getInt("id");
		}
		article.setCommentList(commentList);

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

	/**
	 * 記事とコメントを一緒に取得する.
	 * 
	 * @return 記事とコメント情報
	 */
	public List<Article> findAllWithComment() {
		String sql = "SELECT a.id AS id, a.name AS name, a.content AS content, com.id AS com_id, com.name AS com_name, com.content AS com_content, com.article_id AS article_id FROM articles AS a LEFT OUTER JOIN comments AS com ON a.id = com.article_id ORDER BY a.id DESC, com.id DESC;";
		List<Article> articleAndCommentList = template.query(sql, ARTICLE_RESULT_SET_EXTRACTOR);

		return articleAndCommentList;
	}

	/**
	 * commentsテーブルの外部キー制約をDELETE、UPDATE共にCASCADEに変更する. <br>
	 * 初期化時に一度だけ実行する。
	 */
	@PostConstruct
	public void changeCommentsTableForeignKeyConstraintToCascade() {
		String sql = "ALTER TABLE comments DROP CONSTRAINT comments_article_id_fkey; ALTER TABLE comments ADD FOREIGN KEY (article_id) REFERENCES articles (id) ON DELETE CASCADE ON UPDATE CASCADE;";
		jdbcTemp.execute(sql);
	}
}
