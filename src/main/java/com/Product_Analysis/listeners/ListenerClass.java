package com.Product_Analysis.listeners;

import org.testng.ITestListener;
import org.testng.ITestResult;

import com.Product_Analysis.Browsers.AllBrowsers;

public class ListenerClass extends AllBrowsers implements ITestListener {

	public void onTestFailure(ITestResult result) {

		System.out.println("The name of the testcase failed is : " + ANSI_BLUE + result.getName() + ANSI_RESET + '\n');

	}

	public void onTestSkipped(ITestResult result) {

		System.out.println("The name of the testcase Skipped is : " + ANSI_BLUE + result.getName() + ANSI_RESET + '\n');

	}

	public void onTestStart(ITestResult result) {

		System.out.println('\n' + ANSI_BLUE + result.getName() + ANSI_RESET + " test case started." + '\n');

	}

	public void onTestSuccess(ITestResult result) {

		System.out.println("The name of the testcase passed is : " + ANSI_BLUE + result.getName() + ANSI_RESET + '\n');

	}

}
