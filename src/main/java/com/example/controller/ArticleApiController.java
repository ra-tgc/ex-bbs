package com.example.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class ArticleApiController {
	@Autowired
	private ServletContext application;

	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/count-up", method = RequestMethod.POST)
	public Map<String, Integer> countUp(Integer articleId) {
		Map<String, Integer> map = new HashMap<>();
		Map<Integer, Integer> countMap = (Map<Integer, Integer>) application.getAttribute("count");
		int num = 1;

		if (countMap == null) {
			countMap = new HashMap<>();
		}
		if (countMap.get(articleId) != null) {
			num = countMap.get(articleId) + 1;
		}
		countMap.put(articleId, num);
		application.setAttribute("count", countMap);
		map.put("goodCount", num);

		return map;
	}
}
