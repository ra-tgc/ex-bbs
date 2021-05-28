package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.domain.Comment;
import com.example.form.ArticleForm;
import com.example.form.CommentForm;
import com.example.repository.ArticleRepository;
import com.example.repository.CommentRepository;

/**
 * 記事関連機能の制御を行うコントローラ.
 * 
 * @author masaki.taguchi
 *
 */
@Controller
@Transactional
@RequestMapping("")
public class ArticleController {
	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private CommentRepository commentRepository;

	@ModelAttribute
	public ArticleForm setUpArticleForm() {
		return new ArticleForm();
	}

	@ModelAttribute
	public CommentForm setUpCommentForm() {
		return new CommentForm();
	}

	/**
	 * 投稿一覧画面を表示する.<br>
	 * 
	 * @param model リクエストスコープ
	 * @return 投稿一覧画面のビュー
	 */
	@RequestMapping("/")
	public String index(Model model) {
		List<Article> articleList = articleRepository.findAll();
		// Map<Integer, List<Comment>> commentMap = new LinkedHashMap<>();

		for (Article article : articleList) {
			article.setCommentList(commentRepository.findByArticleId(article.getId()));
			// commentMap.put(article.getId(),
			// commentRepository.findByArticleId(article.getId()));
		}

		model.addAttribute("articleList", articleList);
		// model.addAttribute("commentMap", commentMap);

		return "index";
	}

	/**
	 * 記事を投稿する.<br>
	 * 投稿後は投稿一覧画面にリダイレクトする。
	 * 
	 * @param name    投稿者名
	 * @param content 投稿内容
	 * @return 投稿一覧画面へのリダイレクト
	 */
	@RequestMapping("/insert-article")
	public String insertArticle(ArticleForm form) {
		Article article = new Article();
		article.setName(form.getName());
		article.setContent(form.getContent());
		articleRepository.insert(article);

		return "redirect:/";
	}

	/**
	 * コメントを投稿する.<br>
	 * 投稿後は投稿一覧画面にリダイレクトする。
	 * 
	 * @param name    コメント投稿者名
	 * @param content コメント内容
	 * @return 投稿一覧画面へのリダイレクト
	 */
	@RequestMapping("/insert-comment")
	public String insertComment(CommentForm form) {
		Comment comment = new Comment();
		comment.setName(form.getName());
		comment.setContent(form.getContent());
		comment.setArticleId(Integer.parseInt(form.getArticleId()));
		commentRepository.insert(comment);

		return "redirect:/";
	}
}
