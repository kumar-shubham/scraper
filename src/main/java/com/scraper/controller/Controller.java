package com.scraper.controller;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scraper.service.ScraperService;
import com.scraper.service.ScraperService1;


@CrossOrigin(origins = "*", maxAge = 3600000)
@RestController
@RequestMapping("/web")
public class Controller {
	
	@Autowired
	ScraperService scraperService;
	
	@Autowired
	ScraperService1 scraperService1;
	
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String addRolePrivilege() throws Exception {

		System.out.println("hello Api called...");
		return "Hello";
	}
	

	@RequestMapping(value = "/searchAmazon", method = RequestMethod.GET)
	public String searchAmazon(String text, String key) throws Exception {

		System.out.println("searchAmazon Api called...");
		if(!"93nfii29sjwd0".equals(key)) {
			return "invalid request";
		}
		if(StringUtils.isEmpty(text)) {
			return "search text is null";
		}
		return scraperService.getAmazonSearchResuts(text);
	}
	
	@RequestMapping(value = "/search1688", method = RequestMethod.GET)
	public String search1688(String text, String key) throws Exception {

		System.out.println("search1688 Api called...");
		if(!"93nfii29sjwd0".equals(key)) {
			return "invalid request";
		}
		if(StringUtils.isEmpty(text)) {
			return "search text is null";
		}
		return scraperService.get1688SearchResuts(text);
	}
	
	@RequestMapping(value = "/scrapeFIX", method = RequestMethod.GET)
	public String scrapeFIX(String text, String key) throws Exception {

		System.out.println("scrapeFIX Api called...");
		if(!"93nfii29sjwd0".equals(key)) {
			return "invalid request";
		}
		
		return scraperService1.getFixScrapedResults();
	}
	
	@RequestMapping(value = "/translateText", method = RequestMethod.GET)
	public String translateText(String text) throws Exception {
		System.out.println("translateText Api called...");
		System.out.println("text to translate => " + text);
		return scraperService.translateText(text, "en", "hi");
	}
	
	@RequestMapping(value = "/translateTexts", method = RequestMethod.GET)
	public List<String> translateText() throws Exception {
		System.out.println("translateText Api called...");
		List<String> texts = Arrays.asList("my name is khan", "your name is khan");
		System.out.println("text to translate => " + texts);
		return scraperService.translateText(texts, "en", "hi");
	}
	
	

}
