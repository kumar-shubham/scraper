package com.scraper.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByClassName;
import org.openqa.selenium.By.ByCssSelector;
import org.openqa.selenium.By.ById;
import org.openqa.selenium.By.ByLinkText;
import org.openqa.selenium.By.ByName;
import org.openqa.selenium.By.ByPartialLinkText;
import org.openqa.selenium.By.ByTagName;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;


/**
 * @author kumar
 *
 */
@Component("Watson")
public class Watson {


	/**
	 * use this method stop the execution for particular seconds.
	 * @param seconds
	 */
	public static void sleep(int seconds){
		try {
			ScriptLogger.writeInfo("Sleeping for " + seconds + " second(s)");
			Thread.sleep(seconds*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			ScriptLogger.writeError("Error ", e);
		}
	}

	/**
	 * use this method to execute script in the page.
	 * This may throw runtime error by selenium.
	 * frameindex is the index of frame.
	 * -1 is for default frame.
	 * @param sherlock
	 * @param script
	 * @param frameIndex
	 */
	public static void executeScript(Sherlock sherlock, String script, int frameIndex){
		long start = System.currentTimeMillis();
		ScriptLogger.writeInfo("executing script :: " + script);
		JavascriptExecutor js = (JavascriptExecutor) sherlock.getDriverObject();
		js.executeScript(script, frameIndex);
		ScriptLogger.writeDebug("Total time taken in executing script -> " + (System.currentTimeMillis()-start));
	}
	
	public static void executeScript(Sherlock sherlock, String script){
		long start = System.currentTimeMillis();
		ScriptLogger.writeInfo("executing script :: " + script);
		JavascriptExecutor js = (JavascriptExecutor) sherlock.getDriverObject();
		js.executeScript(script);
		ScriptLogger.writeDebug("Total time taken in executing script -> " + (System.currentTimeMillis()-start));
	}


	public static void evaluateXpathAndClick(Sherlock sherlock, String xpath){

	}

	/**
	 * This method is used to take screenshot of the currently open window.
	 * idName is the name by which you want to store the screenshot.
	 * @param sherlock
	 * @param idName
	 */
	/*public static void takeScreenShot(Sherlock sherlock, String idName) {
		//get the driver
		long start = System.currentTimeMillis();
		File scrFile = ((TakesScreenshot)sherlock.getDriverObject()).getScreenshotAs(OutputType.FILE);
		//The below method will save the screen shot in d drive with test method name 
		try {
			FileUtils.copyFile(scrFile, new File(Constants.PATH_SCREENSHOT +idName+".png"));
			ScriptLogger.writeInfo("***Placed screen shot in " +  Constants.PATH_SCREENSHOT +"***");
		} catch (IOException e) {
			ScriptLogger.writeError("Error ", e);
		}
		ScriptLogger.writeDebug("Total time taken in taking screenshot -> " + (System.currentTimeMillis()-start));
	}*/

	/**
	 * This method wait for the given element for 20 seconds.
	 * It does not throw exception.
	 * @param sherlock
	 * @param ByType
	 */
	public static void waitForTheTarget(Sherlock sherlock, By ByType){
		long start = System.currentTimeMillis();
		WebDriverWait wait = new WebDriverWait(sherlock.getDriverObject(), 20);
		ScriptLogger.writeInfo("waiting for element to load");

		try{
			wait.until(ExpectedConditions.visibilityOfElementLocated(ByType));
			ScriptLogger.writeInfo("Element loaded");
		}
		catch(Exception e){
			ScriptLogger.writeError("Element not found");
		}
		ScriptLogger.writeDebug("Total time taken for waiting the target -> " + (System.currentTimeMillis()-start));

	}

	/**
	 * Use this method if you just want to switch to default frame.
	 * @param sherlock
	 */
	public static void switchToDefaultFrame(Sherlock sherlock){
		long start = System.currentTimeMillis();
		ScriptLogger.writeInfo("switching to defualt frame");
		sherlock.getDriverObject().switchTo().defaultContent();
		ScriptLogger.writeDebug("Total time taken to switch to default frame -> " + (System.currentTimeMillis()-start));
	}

	/**
	 * This method is used to switch to a frame with the given name.
	 * This first switches to default frame and then to the mentioned frame.
	 * So no need to switch to default frame if using this method.
	 * @param sherlock
	 * @param name
	 * @throws Exception 
	 */
	public static void switchToFrameByName(Sherlock sherlock, String name) throws Exception{
		long start = System.currentTimeMillis();
		ScriptLogger.writeInfo("switching to frame with name :: " + name);
		WebDriver driver = sherlock.getDriverObject();
		try{
			driver.switchTo().defaultContent();
			driver.switchTo().frame(name);
			ScriptLogger.writeInfo("frame switched");
		}
		catch(Exception e){
			ScriptLogger.writeError("frame switching failed");
			throw new Exception("Frame switch failed");
		}
		ScriptLogger.writeDebug("Total time taken to switch to frame [" + name + "] -> " + (System.currentTimeMillis()-start));
	}

	/**
	 * This method is used to switch to a frame with the given index.
	 * This first switches to default frame and then to the mentioned frame.
	 * So no need to switch to default frame if using this method.
	 * @param sherlock
	 * @param index
	 * @throws Exception 
	 */
	public static void switchToFrameByIndex(Sherlock sherlock, int index) throws Exception{
		long start = System.currentTimeMillis();
		ScriptLogger.writeInfo("switching to frame with index :: " + index);
		WebDriver driver = sherlock.getDriverObject();
		try{
			driver.switchTo().defaultContent();
			driver.switchTo().frame(index);
			ScriptLogger.writeInfo("frame switched");
		}
		catch(Exception e){
			ScriptLogger.writeError("frame switching failed");
			throw new Exception("Frame switch failed");
		}
		ScriptLogger.writeDebug("Total time taken to switch to frame with index [" + index + "] -> " + (System.currentTimeMillis()-start));
	}

	/**
	 * This method is used to switch to a frame element.
	 * This first switches to default frame and then to the mentioned frame.
	 * So no need to switch to default frame if using this method.
	 * @param sherlock
	 * @param webElement
	 * @throws Exception 
	 */
	public static void switchToFrame(Sherlock sherlock, WebElement webElement) throws Exception{
		long start = System.currentTimeMillis();
		ScriptLogger.writeInfo("switching to frame  " + webElement);
		WebDriver driver = sherlock.getDriverObject();
		try{
			driver.switchTo().defaultContent();
			driver.switchTo().frame(webElement);
			ScriptLogger.writeInfo("frame switched");
		}
		catch(Exception e){
			ScriptLogger.writeError("frame switching failed");
			throw new Exception("Frame switch failed");
		}
		ScriptLogger.writeDebug("Total time taken to switch to frame -> " + (System.currentTimeMillis()-start));
	}

	public static void switchToFrameContainingXPath(Sherlock sherlock, String xpath) throws Exception{
		long start = System.currentTimeMillis();
		List<WebElement> frames = sherlock.findElementsByTagName("iframe");

		for(WebElement frame: frames){

			switchToFrame(sherlock, frame);

			List<WebElement> elements = sherlock.findElementsByXPath(xpath);

			if(elements.size() > 0){
				ScriptLogger.writeDebug("Total time taken to switch to frame ->" + (System.currentTimeMillis()-start));
				return;
			}
		}

		throw new Exception("Error in switching frame");

	}


	/**
	 * This method is used to switch to a frame with the given name.
	 * This doesnot switches to default frame. 
	 * So use this for nested frame switching.
	 * @param sherlock
	 * @param name
	 * @throws Exception 
	 */
	public static void switchToChildFrameByName(Sherlock sherlock, String name) throws Exception{
		long start = System.currentTimeMillis();
		ScriptLogger.writeInfo("switching to frame with name/id :: " + name);
		WebDriver driver = sherlock.getDriverObject();
		try{
			driver.switchTo().frame(name);
		}
		catch(Exception e){
			ScriptLogger.writeError("frame switching failed");
			throw new Exception("Frame switch failed");
		}
		ScriptLogger.writeDebug("Total time taken to switch to child frame ->" + (System.currentTimeMillis()-start));
	}

	/**
	 * This method check if the text present on the page.
	 * 
	 * @param sherlock
	 * @param text
	 * @return
	 */
	public static boolean checkIfTextPresentOnPage(Sherlock sherlock, String text){
		long start = System.currentTimeMillis();
		sherlock.setImplicitwait(5);
		WebElement element = sherlock.findElementByXPath("//*[contains(.,.)]");
		sherlock.setImplicitwait(10);

		ScriptLogger.writeDebug("Total time taken for checking text -> " + (System.currentTimeMillis()-start));
		return element.getText().contains(text);
	}

	/**
	 * This method check if the text present on the page.
	 * 
	 * @param sherlock
	 * @param text
	 * @param wait TODO
	 * @return
	 */
	public static boolean checkIfTextPresentOnPage(Sherlock sherlock, String text, boolean wait){
		long start = System.currentTimeMillis();
		if(!wait){
			sherlock.setImplicitwait(0);
			WebElement element = sherlock.findElementByCSSSelector("html");
			sherlock.setImplicitwait(10);
			ScriptLogger.writeDebug("Total time taken for checking text -> " + (System.currentTimeMillis()-start));
			return element.getText().contains(text);
		}
		else{
			return checkIfTextPresentOnPage(sherlock, text);
		}



	}

	/**
	 * This method check if the text present on the page.
	 * 
	 * @param sherlock
	 * @param text
	 * @return
	 */
	public static boolean checkIfTextPresentOnPage1(Sherlock sherlock, String text){

		long start = System.currentTimeMillis();
		WebElement element = null;
		try{
			sherlock.setImplicitwait(5);
			element = sherlock.findElementByXPath("//*[contains(.,'"+ text +"')]");
		}
		catch(Exception e){
			ScriptLogger.writeInfo("text not found : " + text);
		}
		finally{
			sherlock.setImplicitwait(10);
		}

		if(element == null){
			ScriptLogger.writeDebug("Total time taken for checking text -> " + (System.currentTimeMillis()-start));
			return false;
		}
		ScriptLogger.writeDebug("Total time taken for checking text -> " + (System.currentTimeMillis()-start));
		return true;
	}


	/**
	 * This method check if the text present on the page.
	 * 
	 * @param sherlock
	 * @param text
	 * @param wait TODO
	 * @return
	 */
	public static boolean checkIfTextPresentOnPage1(Sherlock sherlock, String text, boolean wait){
		long start = System.currentTimeMillis();
		if(!wait){
			WebElement element = null;
			try{
				sherlock.setImplicitwait(0);
				element = sherlock.findElementByCSSSelector("html:contains('" + text + "')");
			}
			catch(Exception e){
				ScriptLogger.writeInfo("text not found : " + text);
			}
			finally{
				sherlock.setImplicitwait(10);
			}

			if(element == null){
				ScriptLogger.writeDebug("Total time taken for checking text -> " + (System.currentTimeMillis()-start));
				return false;
			}
			ScriptLogger.writeDebug("Total time taken for checking text -> " + (System.currentTimeMillis()-start));
			return true;
		}
		else{
			return checkIfTextPresentOnPage1(sherlock, text);
		}
	}

	/**
	 * This method check if all of the texts present in the textArray present on the page.
	 * returns false if any of the texts is missing.
	 * @param sherlock
	 * @param textArray
	 * @return
	 */
	public static boolean checkIfAllTextsPresentOnPage(Sherlock sherlock, String[] textArray){
		long start = System.currentTimeMillis();
		sherlock.setImplicitwait(5);
		WebElement element = sherlock.findElementByXPath("//*[contains(.,.)]");
		sherlock.setImplicitwait(10);
		String pageText = element.getText();

		if(textArray != null){

			for(int i = 0; i<textArray.length ;i++){
				if(!pageText.contains(textArray[i])){
					ScriptLogger.writeDebug("Total time taken for checking text -> " + (System.currentTimeMillis()-start));
					return false;
				}
			}
			ScriptLogger.writeDebug("Total time taken for checking text -> " + (System.currentTimeMillis()-start));
			return true;
		}
		ScriptLogger.writeDebug("Total time taken for checking text -> " + (System.currentTimeMillis()-start));
		return false;
	}

	/**
	 * This method check if all of the texts present in the textArray present on the page.
	 * returns false if any of the texts is missing.
	 * @param sherlock
	 * @param textArray
	 * @param wait TODO
	 * @return
	 */
	public static boolean checkIfAllTextsPresentOnPage(Sherlock sherlock, String[] textArray, boolean wait){
		long start = System.currentTimeMillis();
		if(!wait){
			sherlock.setImplicitwait(0);
			WebElement element = sherlock.findElementByCSSSelector("html");
			sherlock.setImplicitwait(10);
			String pageText = element.getText();

			if(textArray != null){

				for(int i = 0; i<textArray.length ;i++){
					if(!pageText.contains(textArray[i])){
						ScriptLogger.writeDebug("Total time taken for checking text -> " + (System.currentTimeMillis()-start));
						return false;
					}
				}
				ScriptLogger.writeDebug("Total time taken for checking text -> " + (System.currentTimeMillis()-start));
				return true;
			}
			ScriptLogger.writeDebug("Total time taken for checking text -> " + (System.currentTimeMillis()-start));
			return false;
		}
		else{
			return checkIfAllTextsPresentOnPage(sherlock, textArray);
		}
	}


	/**
	 * This method check if all of the texts present in the textArray present on the page.
	 * returns false if any of the texts is missing.
	 * @param sherlock
	 * @param textArray
	 * @return
	 */
	public static boolean checkIfAllTextsPresentOnPage1(Sherlock sherlock, String[] textArray){

		long start = System.currentTimeMillis();
		WebElement element = null;

		if(textArray != null){

			for(int i = 0; i<textArray.length ;i++){
				try{
					sherlock.setImplicitwait(5);
					element = sherlock.findElementByXPath("//*[contains(.,'"+ textArray[i] +"')]");
				}
				catch(Exception e){
					ScriptLogger.writeInfo("text not found : " + textArray[i] );
					ScriptLogger.writeDebug("Total time taken for checking text -> " + (System.currentTimeMillis()-start));
					return false;
				}
				finally{
					sherlock.setImplicitwait(10);
				}
				if(element == null){
					ScriptLogger.writeDebug("Total time taken for checking text -> " + (System.currentTimeMillis()-start));
					return false;
				}
			}
			ScriptLogger.writeDebug("Total time taken for checking text -> " + (System.currentTimeMillis()-start));
			return true;
		}
		ScriptLogger.writeDebug("Total time taken for checking text -> " + (System.currentTimeMillis()-start));
		return false;
	}


	/**
	 * This method check if all of the texts present in the textArray present on the page.
	 * returns false if any of the texts is missing.
	 * @param sherlock
	 * @param textArray
	 * @param wait TODO
	 * @return
	 */
	public static boolean checkIfAllTextsPresentOnPage1(Sherlock sherlock, String[] textArray, boolean wait){

		long start = System.currentTimeMillis();
		if(!wait){
			WebElement element = null;

			if(textArray != null){

				for(int i = 0; i<textArray.length ;i++){
					try{
						sherlock.setImplicitwait(0);
						element = sherlock.findElementByCSSSelector("html:contains('" + textArray[i]  + "')");
					}
					catch(Exception e){
						ScriptLogger.writeInfo("text not found : " + textArray[i] );
						ScriptLogger.writeDebug("Total time taken for checking text -> " + (System.currentTimeMillis()-start));
						return false;
					}
					finally{
						sherlock.setImplicitwait(10);
					}
					if(element == null){
						ScriptLogger.writeDebug("Total time taken for checking text -> " + (System.currentTimeMillis()-start));
						return false;
					}
				}
				ScriptLogger.writeDebug("Total time taken for checking text -> " + (System.currentTimeMillis()-start));
				return true;
			}
			ScriptLogger.writeDebug("Total time taken for checking text -> " + (System.currentTimeMillis()-start));
			return false;
		}
		else{
			return checkIfAllTextsPresentOnPage1(sherlock, textArray);
		}
	}


	/**
	 * This method check if any of the texts present in the textArray present on the page.
	 * returns true if any text is found.
	 * @param sherlock
	 * @param textArray
	 * @return
	 */
	public static boolean checkIfAnyTextsPresentOnPage(Sherlock sherlock, String[] textArray){
		long start = System.currentTimeMillis();
		sherlock.setImplicitwait(5);
		WebElement element = sherlock.findElementByXPath("//*[contains(.,.)]");
		sherlock.setImplicitwait(10);
		String pageText = element.getText();

		if(textArray != null){

			for(int i = 0; i<textArray.length ;i++){
				if(pageText.contains(textArray[i])){
					ScriptLogger.writeDebug("Total time taken for checking text -> " + (System.currentTimeMillis()-start));
					return true;
				}
			}
		}
		ScriptLogger.writeDebug("Total time taken for checking text -> " + (System.currentTimeMillis()-start));
		return false;
	}


	/**
	 * This method check if any of the texts present in the textArray present on the page.
	 * returns true if any text is found.
	 * @param sherlock
	 * @param textArray
	 * @param wait TODO
	 * @return
	 */
	public static boolean checkIfAnyTextsPresentOnPage(Sherlock sherlock, String[] textArray, boolean wait){
		long start = System.currentTimeMillis();
		if(!wait){
			sherlock.setImplicitwait(0);
			WebElement element = sherlock.findElementByCSSSelector("html");
			sherlock.setImplicitwait(10);
			String pageText = element.getText();

			if(textArray != null){

				for(int i = 0; i<textArray.length ;i++){
					if(pageText.contains(textArray[i])){
						ScriptLogger.writeDebug("Total time taken for checking text -> " + (System.currentTimeMillis()-start));
						return true;
					}
				}
			}
			ScriptLogger.writeDebug("Total time taken for checking text -> " + (System.currentTimeMillis()-start));
			return false;
		}
		else{
			return checkIfAnyTextsPresentOnPage(sherlock, textArray);
		}
	}

	/**
	 * This method check if any of the texts present in the textArray present on the page.
	 * returns true if any text is found.
	 * @param sherlock
	 * @param textArray
	 * @return
	 */
	public static boolean checkIfAnyTextsPresentOnPage1(Sherlock sherlock, String[] textArray){
		long start = System.currentTimeMillis();
		WebElement element = null;

		if(textArray != null){

			for(int i = 0; i<textArray.length ;i++){
				try{
					sherlock.setImplicitwait(5);
					element = sherlock.findElementByXPath("//*[contains(.,'"+ textArray[i] +"')]");
				}
				catch(Exception e){
					ScriptLogger.writeInfo("text not found : " + textArray[i] );
				}
				finally{
					sherlock.setImplicitwait(10);
				}
				if(element != null){
					ScriptLogger.writeDebug("Total time taken for checking text -> " + (System.currentTimeMillis()-start));
					return true;
				}
			}
		}
		ScriptLogger.writeDebug("Total time taken for checking text -> " + (System.currentTimeMillis()-start));
		return false;
	}


	/**
	 * This method check if any of the texts present in the textArray present on the page.
	 * returns true if any text is found.
	 * @param sherlock
	 * @param textArray
	 * @param wait TODO
	 * @return
	 */
	public static boolean checkIfAnyTextsPresentOnPage1(Sherlock sherlock, String[] textArray, boolean wait){
		long start = System.currentTimeMillis();
		if(!wait){
			WebElement element = null;

			if(textArray != null){

				for(int i = 0; i<textArray.length ;i++){
					try{
						sherlock.setImplicitwait(0);
						element = sherlock.findElementByCSSSelector("html:contains('" + textArray[i]  + "')");
					}
					catch(Exception e){
						ScriptLogger.writeInfo("text not found : " + textArray[i] );
					}
					finally{
						sherlock.setImplicitwait(10);
					}
					if(element != null){
						ScriptLogger.writeDebug("Total time taken for checking text -> " + (System.currentTimeMillis()-start));
						return true;
					}
				}
			}
			ScriptLogger.writeDebug("Total time taken for checking text -> " + (System.currentTimeMillis()-start));
			return false;
		}
		else{
			return checkIfAnyTextsPresentOnPage1(sherlock, textArray);
		}
	}

	/**
	 * This method checks if the given element is present on the page or not.
	 * @param sherlock
	 * @param byType
	 * @return true if element present on the page
	 */
	public static boolean checkIfElementPresentOnThePage(Sherlock sherlock, By byType){
		long start = System.currentTimeMillis();
		WebDriver driver = sherlock.getDriverObject();
		if(driver == null){
			ScriptLogger.writeError("driver object is null");
			ScriptLogger.writeDebug("Total time taken for checking element -> " + (System.currentTimeMillis()-start));
			return false;
		}
		ScriptLogger.writeInfo("inside checkIfElementPresentOnThePage");
		try{
			sherlock.setImplicitwait(5);
			ScriptLogger.writeInfo("checking with " + byType.getClass());
			driver.findElement(byType);
			ScriptLogger.writeInfo("Element present on the page");
		}
		catch(Exception e){
			ScriptLogger.writeWarning("Element not present on the page");
			ScriptLogger.writeDebug("Total time taken for checking element -> " + (System.currentTimeMillis()-start));
			return false;
		}
		finally{
			sherlock.setImplicitwait(10);
		}
		ScriptLogger.writeDebug("Total time taken for checking element -> " + (System.currentTimeMillis()-start));
		return true;
	}


	/**
	 * This method checks if the given element is present on the page or not without any default wait
	 * @param sherlock
	 * @param byType
	 * @param wait TODO
	 * @return true if element present on the page
	 */
	public static boolean checkIfElementPresentOnThePage(Sherlock sherlock, By byType, boolean wait){
		long start = System.currentTimeMillis();
		if(!wait){
			WebDriver driver = sherlock.getDriverObject();
			if(driver == null){
				ScriptLogger.writeError("driver object is null");
				ScriptLogger.writeDebug("Total time taken for checking element -> " + (System.currentTimeMillis()-start));
				return false;
			}
			ScriptLogger.writeInfo("inside checkIfElementPresentOnThePage1");
			try{
				sherlock.setImplicitwait(0);
				ScriptLogger.writeInfo("checking with " + byType.getClass());
				driver.findElement(byType);
				ScriptLogger.writeInfo("Element present on the page");
			}
			catch(Exception e){
				ScriptLogger.writeWarning("Element not present on the page");
				ScriptLogger.writeDebug("Total time taken for checking element -> " + (System.currentTimeMillis()-start));
				return false;
			}
			finally{
				sherlock.setImplicitwait(10);
			}
			ScriptLogger.writeDebug("Total time taken for checking element -> " + (System.currentTimeMillis()-start));
			return true;
		}
		else{
			return checkIfElementPresentOnThePage(sherlock, byType);
		}
	}


	/**
	 * Returns outer HTML of the HTML element passed. If the element is null then the method
	 * returns null;
	 * @param element
	 * @return
	 */
	public static String getOuterHTML(WebElement element){
		long start = System.currentTimeMillis();
		if(element == null){
			return null;
		}
		ScriptLogger.writeDebug("Total time taken in getting outer HTML -> " + (System.currentTimeMillis()-start));
		return element.getAttribute("outerHTML");


	}

	public static String formatAmount(String amount){
		if(amount == null){
			return null;
		}
		amount = amount.replace("$", "");

		return amount.replace(",", "");
	}


	public static String getYear(String rawDate, String dateFormat, String reference, String referenceFormat) throws Exception{

		reference = formatDate(reference);
		rawDate = formatDate(rawDate);

		Date date = getDate(referenceFormat, reference);
		Date date1 = getDate(dateFormat, rawDate);
		ScriptLogger.writeInfo("Raw date       :: " + rawDate);
		ScriptLogger.writeInfo("Raw format     :: " + dateFormat);
		ScriptLogger.writeInfo("Ref date       :: " + reference);
		ScriptLogger.writeInfo("Ref format     :: " + referenceFormat);
		if(date == null || date1 == null){
			throw new Exception("Invalid date Format");
		}

		Calendar c = Calendar.getInstance();
		c.setTime(date);

		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		c.setTime(date1);
		int month1 = c.get(Calendar.MONTH);
		int day1 = c.get(Calendar.DAY_OF_MONTH);

		if((month1 > month) || (month1 == month && day1 > day)){
			c.set(Calendar.YEAR, year-1);
		}
		else{
			c.set(Calendar.YEAR, year);
		}
		SimpleDateFormat sdf = new SimpleDateFormat(referenceFormat);
		String result = sdf.format(c.getTime());

		ScriptLogger.writeInfo("Raw date       :: " + rawDate);
		ScriptLogger.writeInfo("Raw format     :: " + dateFormat);
		ScriptLogger.writeInfo("Ref date       :: " + reference);
		ScriptLogger.writeInfo("Ref format     :: " + referenceFormat);
		ScriptLogger.writeInfo("New Date       :: " + result);

		return result;

	}


	//this method removes extra spaces from the date strings
	private static String formatDate(String reference) {
		// TODO Auto-generated method stub

		String temp[] = reference.split(" ");
		String result = temp[0];
		for(int i = 1; i<temp.length; i++){
			if(!temp[i].trim().equals("")){
				result += " " + temp[i];
			}
		}

		return result;
	}


	private static Date getDate(String format, String value){

		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			date = sdf.parse(value);
		} catch (ParseException ex) {
			ScriptLogger.writeError("Error ", ex);
		}
		if (date == null) {
			// Invalid date format
		} else {
			// Valid date format
		}

		return date;


	}

	public static String removeUnicodeSpace(String string){

		string  = string.replaceAll("\u00A0", "");
		return string;
	}


	/**
	 * @param ByType
	 * @param value
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private static By getByInstance(By ByType, String value) throws Exception{

		By byInstance = null;

		if(ByType instanceof ById){
			ScriptLogger.writeInfo("By instance of ById");
			byInstance = By.id(value);
		}
		else if(ByType instanceof ByClassName){
			ScriptLogger.writeInfo("By instance of ByClassName");
			byInstance = By.className(value);
		}
		else if(ByType instanceof ByLinkText){
			ScriptLogger.writeInfo("By instance of ByLinkText");
			byInstance = By.linkText(value);
		}
		else if(ByType instanceof ByName){
			ScriptLogger.writeInfo("By instance of ByName");
			byInstance = By.name(value);
		}
		else if(ByType instanceof ByTagName){
			ScriptLogger.writeInfo("By instance of ByTagName");
			byInstance = By.tagName(value);
		}
		else if(ByType instanceof ByPartialLinkText){
			ScriptLogger.writeInfo("By instance of ByPartialLinkText");
			byInstance = By.partialLinkText(value);
		}
		else if(ByType instanceof ByCssSelector){
			ScriptLogger.writeInfo("By instance of ByCssSelector");
			byInstance = By.cssSelector(value);
		}
		else if(ByType instanceof ByXPath){
			ScriptLogger.writeInfo("By instance of ByXPath");
			byInstance = By.xpath(value);
		}
		else{
			throw new Exception("By Type not supported");
		}

		return byInstance;

	}

	public static void switchToNewWindow(Sherlock sherlock){

		WebDriver driver = sherlock.getDriverObject();
		String parent=driver.getWindowHandle();
		ScriptLogger.writeInfo("Switching to new window...");
		ScriptLogger.writeInfo("parent window name is " + parent);
		// This will return the number of windows opened by Webdriver and will return Set of Strings
		Set<String>s1=driver.getWindowHandles();

		// Now we will iterate using Iterator
		Iterator<String> I1= s1.iterator();

		while(I1.hasNext())
		{
			String child_window=I1.next();

			// Here we will compare if parent window is not equal to child window then we            will close
			
			if(!parent.equals(child_window))
			{	
				ScriptLogger.writeInfo("child window name is " + child_window);
				driver.switchTo().window(child_window);
			}
		}
	}
	
	public static String maskString(String string) {
		
		if(StringUtils.isNotEmpty(string)) {
			String maskedString = null;
			if(string.length() > 4) {
				maskedString = buildStringWithStars(string.length()-4) + string.substring(string.length()-4);
			}
			else {
				maskedString = string;
			}
			return maskedString;
			
		}else {
			return "";
		}
		
	}
	
	private static String buildStringWithStars(int i) {
		// TODO Auto-generated method stub
		CharSequence[] array = new CharSequence[i];
		Arrays.fill(array, "*");
		return String.join("", array);
	}
	
	public static void waitForAsync(List<Future<Boolean>> results) {
		
		if(results != null) {
			results.forEach(result -> {
				try {
					result.get();
				} catch (InterruptedException | ExecutionException e) {
					//handle thread error
				}
			});
		}
	}
	
}
