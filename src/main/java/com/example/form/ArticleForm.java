package com.example.form;

import javax.validation.constraints.Size;

/**
 * 記事登録時に使用するフォーム.
 * 
 * @author masaki.taguchi
 *
 */
public class ArticleForm {
	/** 投稿者名 */
	@Size(min = 1, max = 50, message = "投稿者名は1文字以上50文字以内で入力してください")
	private String name;
	/** 投稿内容 */
	@Size(min = 1, max = 256, message = "投稿内容は1文字以上256文字以内で入力してください")
	private String content;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "ArticleForm [name=" + name + ", content=" + content + "]";
	}

}
