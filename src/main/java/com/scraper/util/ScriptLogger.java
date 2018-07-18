package com.scraper.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;

public class ScriptLogger {

	private static Logger logger = LogManager.getLogger();
	
	private static Logger getLogger() {
		return logger;
	}


	public static void writeInfo(Object message) {

		getLogger().info(getCallerDetail() + "  <<i>>  " + message);
	}


	public static void writeInfo(Object message, Throwable t) {

		getLogger().info(" <<i>> " + message, t);
	}

	public static void writeWarning(Object message) {

		getLogger().warn(" >>e<< " + message);
	}

	public static void writeWarning(Object message, Throwable t) {

		getLogger().warn(" >>e<< " + message, t);
	}

	public static void writeError(Object message) {

		getLogger().error(" >>e<< " + message);
	}

	public static void writeError(Object message, Throwable t) {

		getLogger().error(" >>e<< " + message, t);
	}

	public static void writeDebug(Object message) {

		getLogger().debug(" <<d>> " + message);
	}

	public static void writeDebug(Object message, Throwable t) {

		getLogger().debug(" <<d>> " + message, t);
	}

	public static void printPage(Sherlock sherlock, ArrayList<String> regexList) {
		getLogger().info("-------->>>>>>> Inside printPage <<<<<<<--------");
		if (sherlock == null) {
			getLogger().error("Driver object is null");
		}

		String page = "^^^^^ PRINTING HTML PAGE ^^^^^^" + "\n" + "FOR THE URL ("
				+ sherlock.getDriverObject().getCurrentUrl() + ")\n" + sherlock.getDriverObject().getPageSource()
				+ "\n\n\n" + "^^^^^^ END OF PAGE PRINT ^^^^^^";

		page = maskString(page, regexList);

//		System.out.println(page);

		getLogger().info(page);

	}

	public static void printEle(WebElement element, ArrayList<String> regexList) {
		if (element == null) {
			getLogger().error("element is null");
		}

		String page = "^^^^^ PRINTING HTML ELEMENT ^^^^^^" + "\n" + element.getAttribute("outerHTML") + "\n\n\n"
				+ "^^^^^^ END OF HTML ELEMENT ^^^^^^";

		page = maskString(page, regexList);

		getLogger().info(page);

	}

	private static String getCallerDetail() {
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
		StackTraceElement e = stacktrace[3];// maybe this number needs to be corrected
		return "[" + e.getFileName() + "." + e.getMethodName() + "::" + e.getLineNumber() + "]";
	}

	private static String maskString(String string, ArrayList<String> regexList) {

		for (String regex : regexList) {
			Pattern p = Pattern.compile(regex);
			Matcher matcher = p.matcher(string);

			while (matcher.find()) {
				string = string.replaceAll(matcher.group(1), buildStringWithStars(matcher.group(1).length()));
			}
		}

		return string;
	}

	private static String buildStringWithStars(int i) {
		// TODO Auto-generated method stub
		CharSequence[] array = new CharSequence[i];
		Arrays.fill(array, "*");
		return String.join("", array);
	}
}
