package com.scraper.service;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scraper.scraper.AmazonSearch;
import com.scraper.scraper.One688Search;
import com.scraper.util.IQHttpClient;
import com.scraper.util.InstanceFactory;
import com.scraper.util.Watson;

@Service
public class ScraperService {
	
	public void getAmazonSearchResuts() throws Exception {
		String amazonUrl = "https://www.amazon.com/";
		String one688Url = "https://www.1688.com/";
		String searchPhrase = "IPhone 8";
		
		AmazonSearch amazonSearch = (AmazonSearch) InstanceFactory.getInstance("AmazonSearch");	
		
		amazonSearch.initialize();
		amazonSearch.openURL(amazonUrl);
		amazonSearch.search(searchPhrase);
		List<HashMap<String, String>> productList = amazonSearch.getSearchResults(); 
		List<String> productNames = getTranslatedProductNames(productList);
		System.out.println(productNames);
		amazonSearch.exit();
		
		One688Search one688Search = (One688Search) InstanceFactory.getInstance("One688Search");
		one688Search.initialize();
		one688Search.openURL(one688Url);
		one688Search.closePopups();
		one688Search.search(productNames.get(0));
		one688Search.closePopups();
		List<HashMap<String, String>> productList1688 = one688Search.getSearchResults();
		System.out.println(productList1688);
	}
	
	public List<String> getTranslatedProductNames(List<HashMap<String, String>> productList) throws Exception{
		List<String> productNames = new ArrayList<>();
		
		for(HashMap<String,String> product : productList) {
			String name = product.get("name");
			productNames.add(name);
		}
		
		return translateText(productNames, "en", "zh-TW");
	}
	
	public String translateText(String text, String fromLangCode, String toLangCode) throws Exception {
		return translateText(new ArrayList<>(Arrays.asList(text)), fromLangCode, toLangCode).get(0);
	}
	
	public List<String> translateText(List<String> texts, String fromLangCode, String toLangCode) throws Exception {
		 
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

}
