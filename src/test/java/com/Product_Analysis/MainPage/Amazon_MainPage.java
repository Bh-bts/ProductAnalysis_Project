package com.Product_Analysis.MainPage;

import java.io.IOException;

import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.Product_Analysis.Browsers.AllBrowsers;
import com.Product_Analysis.testcase.Amazon_Page;
import com.Product_Analysis.utils.PropertiesClass;
import com.Product_Analysis.utils.ScreenshotCapture;

@Listeners(com.Product_Analysis.listeners.ListenerClass.class)
public class Amazon_MainPage extends Amazon_Page {

	public Amazon_MainPage() throws IOException {

		driver = AllBrowsers.startBrowser(PropertiesClass.getBrowser(), PropertiesClass.getAmazon_URL());
	}

	@Test
	public void productAnalysis() throws InterruptedException, IOException {

		Amazon_Page obj = PageFactory.initElements(driver, Amazon_Page.class);
		obj.prouductReviews();

	}

	@AfterMethod
	public void teardown(ITestResult result) throws IOException {

		if (ITestResult.FAILURE == result.getStatus()) {

			ScreenshotCapture.captureScreenshot(driver, "Screenshot" + result.getName());
			System.out.println("Screenshot is generated " + '\n');

		}

	}

}
