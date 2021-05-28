package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Comment;

/**
 * commentsテーブルを操作するリポジトリ.
 * 
 * @author masaki.taguchi
 *
 */
@Repository
public class CommentRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Comment> COMMENT_ROW_MAPPER = new BeanPropertyRowMapper<>(Comment.class);

	/**
	 * articleIdが一致するコメントを取得する.<br>
	 * articleIdは記事IDなので、複数件取得する可能性あり。
	 * 
	 * @param articleId 記事ID
	 * @return コメント(idの降順)
	 */
	public List<Comment> findByArticleId(int articleId) {
		String sql = "SELECT id, name, content, article_id FROM comments WHERE article_id = :articleId ORDER BY id DESC;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);
		List<Comment> commentList = template.query(sql, param, COMMENT_ROW_MAPPER);

		return commentList;
	}

	/**
	 * コメントを挿入する.
	 * 
	 * @param comment 挿入するコメント
	 */
	public void insert(Comment comment) {
		String sql = "INSERT INTO comments(name, content, article_id) VALUES (:name, :content, :articleId);";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", comment.getName())
				.addValue("content", comment.getContent()).addValue("articleId", comment.getArticleId());
		template.update(sql, param);
	}

}
