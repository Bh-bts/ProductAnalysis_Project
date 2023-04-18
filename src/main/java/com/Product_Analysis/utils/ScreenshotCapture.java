package com.Product_Analysis.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotCapture {

	public static void captureScreenshot(WebDriver driver, String screenshotName) throws IOException {

		TakesScreenshot ts = (TakesScreenshot) driver;

		File sourceFile = ts.getScreenshotAs(OutputType.FILE);
		File destFile = new File("src/main/resources/captureScreenshot/" + screenshotName + ".png");
		FileUtils.copyFile(sourceFile, destFile);

	}

}
