package com.scraper.service;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scraper.scraper.AmazonSearch;
import com.scraper.scraper.One688Search;
import com.scraper.util.IQHttpClient;
import com.scraper.util.InstanceFactory;

@Service
public class ScraperService {
	
	@Autowired
	XLSXGenerator xlsxGenerator;
	
	public String getAmazonSearchResuts(String searchPhrase) throws Exception {
		String amazonUrl = "https://www.amazon.com/";
		String one688Url = "https://www.1688.com/";
		
		AmazonSearch amazonSearch = (AmazonSearch) InstanceFactory.getInstance("AmazonSearch");	
		
		amazonSearch.initialize();
		amazonSearch.openURL(amazonUrl);
		amazonSearch.search(searchPhrase);
		List<HashMap<String, String>> productList = amazonSearch.getSearchResults(); 
		List<String> productNames = getTranslatedProductNames(productList);
		System.out.println(productNames);
		amazonSearch.exit();
		
		List<HashMap<String, String>> productList1688 = new ArrayList<>();
		
		One688Search one688Search = (One688Search) InstanceFactory.getInstance("One688Search");
		one688Search.initialize();
		
		try {
			for(String productName : productNames) {
				one688Search.openURL(one688Url);
				one688Search.closePopups();
				one688Search.search(productName);
				one688Search.closePopups();
				productList1688.addAll(one688Search.getSearchResults());
			}
		}catch(Exception e) {
				e.printStackTrace();
			}
		
		try {
//			one688Search.exit();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("total products scraped => " + productList1688.size());
		String filepath = xlsxGenerator.generateXLSX(productList1688);
		System.out.println("file path => " + filepath);
		
		return getResourceURL(filepath);
	}
	
	public List<String> getTranslatedProductNames(List<HashMap<String, String>> productList) throws Exception{
		List<String> productNames = new ArrayList<>();
		
		for(HashMap<String,String> product : productList) {
			String name = product.get("name");
			productNames.add(name);
		}
		
		return translateText(productNames, "en", "zh-TW");
	}
	
	public static String translateText(String text, String fromLangCode, String toLangCode) throws Exception {
		return translateText(new ArrayList<>(Arrays.asList(text)), fromLangCode, toLangCode).get(0);
	}
	
	public static List<String> translateText(List<String> texts, String fromLangCode, String toLangCode) throws Exception {
		 
		String url1 = "https://translation.googleapis.com/language/translate/v2?";
		String url2 = "&target=" + toLangCode + "&source=" + fromLangCode + "&key=AIzaSyBbEzhRYeRSrThhLMiuCQVeqyLUiHOZtho";
		
		String mainText = "";
		for(int i = 0; i<texts.size(); i++) {
			String text = texts.get(i);
			text = URLEncoder.encode(text, "UTF-8");
			if(i > 0) {
				mainText += "&q=" + text;
			}else {
				mainText = "q=" + text;
			}
		}
		String url = url1 + mainText + url2;
		
		System.out.println("new URL => " + url);
		
		IQHttpClient client = new IQHttpClient(url, IQHttpClient.REQUEST_TYPE_GET); 
		String result = client.getResponseForGetRequest();
		
		ObjectMapper mapper = new ObjectMapper();
		JSONObject json = mapper.readValue(result, JSONObject.class);
		 
		HashMap<String, List<HashMap<String, String>>> data =  (HashMap<String, List<HashMap<String, String>>>) json.get("data");
		
		List<HashMap<String, String>> translations = data.get("translations");
		
		List<String> response = new ArrayList<>();
		
		for(HashMap<String, String> translation : translations) {
			String newText = translation.get("translatedText");
			System.out.println(newText);
			response.add(newText);
		}
		return response;
	}
	
	private String getResourceURL(String path) {
		String serverURL = "http://18.222.208.156/";
		
		if(path.contains("/public/")) {
			path = path.substring(path.indexOf("/public/")+8);
		}
		return serverURL + path;
	}

}
