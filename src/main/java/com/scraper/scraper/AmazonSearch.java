package com.scraper.scraper;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.scraper.util.Sherlock;

@Component("AmazonSearch")
@Scope("prototype")
public class AmazonSearch {
	
	private String url = "";
	private String searchPhrase = "";
	
	Sherlock sherlock = null;
	
	public boolean initialize() throws MalformedURLException {
		
		try {
			sherlock = new Sherlock();
			sherlock.startDriver();
			return true;
		}catch(Exception e) {
			System.out.println("Error in initializing " + e.getMessage());
		}
		return false;
	}
	
	public boolean openURL(String url) {
		try {
			this.url = url;
			sherlock.openURL(url);
			return true;
		}catch(Exception e) {
			System.out.println("Error in openURL " + e.getMessage());
		}
		return false;
	}
	
	public boolean goToHomePage() {
		try {
			sherlock.openURL(url);
			return true;
		}
		catch(Exception e) {
			System.out.println("Error in goToHomePage " + e.getMessage());
		}
		return false;
	}
	
	public boolean search(String searchPhrase) {
		
		try {
			this.searchPhrase = searchPhrase;
			
			WebElement searchInput = sherlock.findElementById("twotabsearchtextbox");
			WebElement submitBtn = sherlock.findElementByXPath("//div[span[@id = 'nav-search-submit-text']]/input");
			sherlock.sendInput(searchInput, searchPhrase);
			submitBtn.click();
			
		}catch(Exception e) {
			System.out.println("Error in search " + e.getMessage());
		}
		return false;
	}
	
	public List<HashMap<String, String>> getSearchResults(){
		List<HashMap<String, String>> productList = new ArrayList<>();
		
		List<WebElement> productEles = sherlock.findElementsByXPath("//li[contains(@id, 'result_')]");
		System.out.println("total products found on the page => " + productEles.size());
		
		for(WebElement productEle : productEles) {
			String imageXpath = ".//div[@class = 'a-fixed-left-grid-col a-col-left']";
			WebElement imageMain = productEle.findElement(By.xpath(imageXpath));
			
			String detailXPath = ".//div[@class = 'a-fixed-left-grid-col a-col-right']";
			WebElement detailMain = productEle.findElement(By.xpath(detailXPath));
			WebElement nameEle = detailMain.findElement(By.cssSelector("div.a-row div a h2")); 
			String name = nameEle.getAttribute("data-attribute");
			
			HashMap<String, String> product = new HashMap<String, String>();
			product.put("name", name);
			productList.add(product);
			System.out.println(name);
		}
		return productList;
	}
	
	public boolean exit() {
		try {
			sherlock.closeDriver();
			return true;
		}catch(Exception e) {
			System.out.println("Error in exit " + e.getMessage());
		}
		return false;
	}

}
