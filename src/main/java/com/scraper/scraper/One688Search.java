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
import com.scraper.util.Watson;

@Component("One688Search")
@Scope("prototype")
public class One688Search {
	
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
	
	public boolean closePopups() {
		try {
			if(Watson.checkIfElementPresentOnThePage(sherlock, By.cssSelector("div.home-identity-dialog"))) {
				WebElement popup = sherlock.findElementByCSSSelector("div.home-identity-dialog");
				System.out.println("pop up is present");
				if(popup.isDisplayed()) {
					System.out.println("popup is displaying");
					WebElement closeBtn = sherlock.findElementByCSSSelector("span.identity-close");
					closeBtn.click();
					System.out.println("pop closed");
					Watson.sleep(2);
				}
			}else if(Watson.checkIfElementPresentOnThePage(sherlock, By.id("s-module-overlay"))) {
				WebElement popup = sherlock.findElementById("s-module-overlay");
				System.out.println("pop up is present");
				if(popup.isDisplayed()) {
					System.out.println("popup is displaying");
					WebElement closeBtn = sherlock.findElementByCSSSelector("div.s-overlay-close");
					closeBtn.click();
					System.out.println("pop closed");
					Watson.sleep(2);
				}
			}
			return true;
		}catch(Exception e) {
			System.out.println("Error in closePopups " + e.getMessage());
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
			
			WebElement searchInput = sherlock.findElementById("alisearch-keywords");
			WebElement submitBtn = sherlock.findElementById("alisearch-submit");
			sherlock.sendInput(searchInput, searchPhrase);
			submitBtn.click();
			
		}catch(Exception e) {
			System.out.println("Error in search " + e.getMessage());
		}
		return false;
	}
	
	public List<HashMap<String, String>> getSearchResults(){
		List<HashMap<String, String>> productList = new ArrayList<>();
		
		List<WebElement> productEles = sherlock.findElementsByCSSSelector("ul#sm-offer-list li");
		System.out.println("total products found on the page => " + productEles.size());
		
		for(WebElement productEle : productEles) {
			String imageCSSPath = "div.imgofferresult-mainBlock div.sm-offer-photo a img";
			WebElement imageEle = productEle.findElement(By.cssSelector(imageCSSPath));
			String imageURL = imageEle.getAttribute("src");
			
			System.out.println("image url => " + imageURL);
			
			WebElement productURLEle = productEle.findElement(By.cssSelector("div.imgofferresult-mainBlock div.sm-offer-photo a"));
			String productURL = productURLEle.getAttribute("href");
			
			WebElement priceEle = productEle.findElement(By.cssSelector("div.imgofferresult-mainBlock div.sm-offer-price span"));
			String price = priceEle.getText();
			
			WebElement nameEle = productEle.findElement(By.cssSelector("div.imgofferresult-mainBlock div.sm-offer-title > a"));
			String name = nameEle.getAttribute("title");
			
			WebElement sellerEle = productEle.findElement(By.cssSelector("div.imgofferresult-mainBlock div.sm-offer-company > a"));
			String seller = sellerEle.getAttribute("title");
			
			WebElement locationEle = productEle.findElement(By.cssSelector("div.imgofferresult-mainBlock div.sm-offer-sub div.sm-offer-location"));
			String location = locationEle.getAttribute("title");
			
			HashMap<String, String> product = new HashMap<String, String>();
			product.put("Search Phrase", searchPhrase);
			product.put("Product Name", name);
			product.put("Price", price);
			product.put("Image", imageURL);
			product.put("Seller", seller);
			product.put("Location", location);
			product.put("URL", productURL);
			productList.add(product);
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
