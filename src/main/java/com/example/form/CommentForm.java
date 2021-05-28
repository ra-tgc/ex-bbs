package com.example.form;

/**
 * コメント登録時に使用するフォーム.
 * 
 * @author masaki.taguchi
 *
 */
public class CommentForm {
	/** コメント投稿者名 */
	private String name;
	/** コメント内容 */
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
