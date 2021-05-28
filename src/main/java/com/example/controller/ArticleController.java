package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.form.ArticleForm;
import com.example.repository.ArticleRepository;

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
	private ArticleRepository repository;

	@ModelAttribute
	public ArticleForm setUpForm() {
		return new ArticleForm();
	}

	/**
	 * 投稿一覧画面を表示する.<br>
	 * 
	 * @param model リクエストスコープ
	 * @return 投稿一覧画面のビュー
	 */
	@RequestMapping("/")
	public String index(Model model) {
		List<Article> articleList = repository.findAll();

		model.addAttribute("articleList", articleList);

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
		repository.insert(article);

		return "redirect:/";
	}
}
