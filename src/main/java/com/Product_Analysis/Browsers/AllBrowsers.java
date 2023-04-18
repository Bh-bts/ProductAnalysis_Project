package com.Product_Analysis.Browsers;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

//import com.epam.healenium.SelfHealingDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AllBrowsers {

	/**
	 * @author Bhavin.Thumar
	 *
	 */

	public static WebDriver driver;
//	public static SelfHealingDriver driver;

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE = "\u001B[34m";

	public static WebDriver startBrowser(String browserName, String Weburl) {

		if (browserName.equals("chrome")) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions op = new ChromeOptions();
			op.addArguments("--remote-allow-origins=*");
			driver = new ChromeDriver(op);
			// WebDriver delegate = new ChromeDriver(op);
			// driver = SelfHealingDriver.create(delegate);

		} else if (browserName.equals("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			// WebDriver delegate = new FirefoxDriver();
			// driver = SelfHealingDriver.create(delegate);

		} else if (browserName.equals("IE")) {
			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();
			// WebDriver delegate = new InternetExplorerDriver();
			// driver = SelfHealingDriver.create(delegate);

		} else {
			System.out.println("Browser is not found");
		}

		driver.manage().window().maximize();
		driver.get(Weburl);
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

		return driver;
	}

	public static void quitBrowser() {
		driver.quit();
	}

}
