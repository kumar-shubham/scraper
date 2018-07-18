package com.scraper.service;

import java.net.MalformedURLException;

import org.springframework.stereotype.Service;

import com.scraper.scraper.AmazonSearch;
import com.scraper.util.InstanceFactory;
import com.scraper.util.Watson;

@Service
public class ScraperService {
	
	public void getAmazonSearchResuts() throws MalformedURLException {
		String url = "https://www.amazon.com/";
		String searchPhrase = "IPhone 8";
		
		AmazonSearch amazonSearch = (AmazonSearch) InstanceFactory.getInstance("AmazonSearch");	
		
		amazonSearch.initialize();
		amazonSearch.openURL(url);
		amazonSearch.search(searchPhrase);
		amazonSearch.getSearchResults(); 
		Watson.sleep(10);
//		amazonSearch.exit();
	}

}
