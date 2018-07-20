package com.scraper.constant;

import java.util.HashMap;

public class Constants {
	
	private static HashMap<String, Integer> headerMap = new HashMap<>();
	
	static {
		headerMap.put("Search Phrase", 0);
		headerMap.put("Product Name", 1);
		headerMap.put("Price", 2);
		headerMap.put("Seller", 3);
		headerMap.put("Location", 4);
		headerMap.put("URL", 5);
		headerMap.put("Image", 6);
	}
	
	public static HashMap<String, Integer> getHeaderMap(){
		return headerMap;
	}

}
