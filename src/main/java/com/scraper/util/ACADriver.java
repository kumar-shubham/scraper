package com.scraper.util;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author kumar
 *
 */

@Component("ACADriver")
@Scope("prototype")
public class ACADriver {


	private WebDriver driver = null;

	private String downloadFileLocation = null;

	private int downloadTimeOutInSec = 60;

	/**
	 * @return
	 * @throws MalformedURLException
	 */
	public WebDriver getDriver() throws MalformedURLException {
		if (driver == null) {
			Path p = Paths.get(System.getProperty("user.home"), "drivers", "chromedriver");

			ScriptLogger.writeInfo(":::::::::::::::: " + p);
			System.setProperty("webdriver.chrome.driver", p.toString());
			ScriptLogger
					.writeInfo("::::::::::::::::sssssssssss :::::::: " + System.getProperty("webdriver.chrome.driver"));


			String downloadFilepath = System.getProperty("user.home") + "/public/download/";
			String randomString = RandomStringUtils.randomAlphanumeric(8);
			downloadFilepath += randomString + "/";
			downloadFileLocation = downloadFilepath;

			File directory = new File(downloadFilepath);
			if (!directory.exists()) {
				directory.mkdir();
			}

			Map<String, Object> preferences = new Hashtable<String, Object>();
			preferences.put("profile.default_content_settings.popups", 0);
			preferences.put("download.prompt_for_download", "false");
			preferences.put("download.default_directory", downloadFilepath);
			preferences.put("plugins.always_open_pdf_externally", true);

			// disable flash and the PDF viewer
			preferences.put("plugins.plugins_disabled", new String[] { "Adobe Flash Player", "Chrome PDF Viewer" });

			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("prefs", preferences);
			options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			options.setCapability(ChromeOptions.CAPABILITY, options);
			options.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);

			driver = new ChromeDriver(options);

			 driver.manage().window().maximize();
			setDriverTimeout(10);
		}
		return driver;
	}

	/**
	 * @param seconds
	 */
	public void setDriverTimeout(int seconds) {
		driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
	}

	public String getDownloadFileLocation() {
		return downloadFileLocation;
	}

	/**
	 * @return the downloadTimeOutInSec
	 */
	public int getDownloadTimeOutInSec() {
		return downloadTimeOutInSec;
	}

	/**
	 * @param downloadTimeOutInSec the downloadTimeOutInSec to set
	 */
	public void setDownloadTimeOutInSec(int downloadTimeOutInSec) {
		this.downloadTimeOutInSec = downloadTimeOutInSec;
	}

}
