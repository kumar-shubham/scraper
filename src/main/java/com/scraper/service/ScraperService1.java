package com.scraper.service;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scraper.scraper.FIXScraper;
import com.scraper.util.InstanceFactory;

@Service
public class ScraperService1 {
	
	@Autowired
	XLSXGenerator xlsxGenerator;
	
	public String getFixScrapedResults() throws Exception {
		String url = "https://www.onixs.biz/fix-dictionary/5.0.SP2/fields_by_tag.html";
		
		HashMap<String, Integer> headerMap = new HashMap<>();
		headerMap.put("Name", 0);
		headerMap.put("Type", 1);
		headerMap.put("Description", 2);
		headerMap.put("URL", 3);
		
		FIXScraper fixScraper = (FIXScraper) InstanceFactory.getInstance("FIXScraper");
		fixScraper.initialize();
		fixScraper.openURL(url);
		List<HashMap<String, String>> fields = fixScraper.getScrapedResults();
		String filepath = xlsxGenerator.generateXLSX(fields, "FIX_4.2", headerMap);
		System.out.println("file path => " + filepath);
		return filepath;
	}

}
