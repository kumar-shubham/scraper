package com.scraper.controller;

import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scraper.service.ScraperService;
import com.scraper.util.ScriptLogger;


@CrossOrigin(origins = "*", maxAge = 3600000)
@RestController
@RequestMapping("/web")
public class Controller {
	
	@Autowired
	ScraperService scraperService;
	
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String addRolePrivilege() throws Exception {

		System.out.println("hello Api called...");
		return "Hello";
	}
	

	@RequestMapping(value = "/searchAmazon", method = RequestMethod.GET)
	public String searchAmazon() throws Exception {

		System.out.println("searchAmazon Api called...");
		scraperService.getAmazonSearchResuts();
		return "Search completed";
	}
	
	@RequestMapping(value = "/translateText", method = RequestMethod.GET)
	public String translateText(String text) throws Exception {
		System.out.println("translateText Api called...");
		System.out.println("text to translate => " + text);
		return scraperService.translateText(text, "en", "hi");
	}
	
	

}
