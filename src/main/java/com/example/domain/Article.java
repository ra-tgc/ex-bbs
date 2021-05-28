package com.example.domain;

import java.util.List;

/**
 * 記事を表すドメイン.
 * 
 * @author masaki.taguchi
 *
 */
public class Article {
	/** 記事ID */
	private Integer id;
	/** 記事投稿者名 */
	private String name;
	/** 記事内容 */
	private String conent;
	/** 記事へのコメント一覧 */
	private List<Comment> commentList;

	public Article() {
	}

	public Article(Integer id, String name, String content, List<Comment> commentList) {
		super();
		this.id = id;
		this.name = name;
		this.conent = content;
		this.commentList = commentList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return conent;
	}

	public void setContent(String content) {
		this.conent = content;
	}

	public List<Comment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}

	@Override
	public String toString() {
		return "Article [id=" + id + ", name=" + name + ", content=" + conent + ", commentList=" + commentList + "]";
	}

}
