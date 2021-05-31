package com.example.form;

import javax.validation.constraints.Size;

/**
 * コメント登録時に使用するフォーム.
 * 
 * @author masaki.taguchi
 *
 */
public class CommentForm {
	/** コメント投稿者名 */
	@Size(min = 1, max = 50, message = "名前は1文字以上50文字以内で入力してください")
	private String name;
	/** コメント内容 */
	@Size(min = 1, max = 128, message = "コメントは1文字以上128文字以内で入力してください")
	private String content;
	/** コメント先の記事ID */
	private String articleId;

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

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	@Override
	public String toString() {
		return "CommentForm [name=" + name + ", content=" + content + ", articleId=" + articleId + "]";
	}

}
