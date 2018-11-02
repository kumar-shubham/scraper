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

@Component("FIXScraper")
@Scope("prototype")
public class FIXScraper {

	private String url = "";

	Sherlock sherlock = null;

	public boolean initialize() throws MalformedURLException {

		try {
			sherlock = new Sherlock();
			sherlock.startDriver();
			return true;
		} catch (Exception e) {
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
	
	public boolean exit() {
		try {
			sherlock.closeDriver();
			return true;
		}catch(Exception e) {
			System.out.println("Error in exit " + e.getMessage());
		}
		return false;
	}
	
	public List<HashMap<String, String>> getScrapedResults(){
		
		List<HashMap<String, String>> fieldList = new ArrayList<>();
		
		String xpath = "//tbody[tr/th[text() = 'Field']]/tr[contains(@class, 'tr') or contains(@class, 'y')]";
		List<WebElement> fields = sherlock.findElementsByXPath(xpath);
		System.out.println("size of fields => " + fields.size());
		
		
		
		for(WebElement field : fields) {
			
			WebElement fieldColumn = field.findElement(By.cssSelector("td:nth-child(2) a"));
			
			String name = fieldColumn.getText();
			String url = fieldColumn.getAttribute("href");
			
			HashMap<String, String> fieldMap = new HashMap<>();
			fieldMap.put("Name", name);
			fieldMap.put("URL", url);
			fieldList.add(fieldMap);
			
		}
		
		
//		String xpath1 = "//p[preceding-sibling::a/h3[contains(text(),'Description')] and following-sibling::a/h3[contains(text(),'Used In')]]";
		
		String xpath1 = "//td[contains(., 'Description')]";
		
		for(HashMap<String, String> fieldMap : fieldList) {
			
			String url = fieldMap.get("URL");
			
			sherlock.openURL(url);
			
			List<WebElement> descriptions = sherlock.findElementsByXPath(xpath1);
			
			String description = "";
			for(WebElement desc : descriptions) {
				description += "\n" + desc.getText();
			}
			
			if(description.contains("Description") && description.contains("Used In")) {
				try {
					description = description.substring(description.indexOf("Description")+12,
							description.lastIndexOf("Used In")-1);
				}catch(IndexOutOfBoundsException e) {
					description = "";
				}
			}
			fieldMap.put("Description", description);
			
			String typeXPath = "//p[contains(.,'Type:')]";
			WebElement typeEle = sherlock.findElementByXPath(typeXPath);
			String type = typeEle.getText();
			type = type.replace("Type:", "").trim();
			fieldMap.put("Type", type);
			
		}
		
//		System.out.println("fieldList => " + fieldList);
		
		
		return fieldList;
	}

}
