package com.Product_Analysis.utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesClass {

	public static Properties a;

	public static Properties fileConfig() throws IOException {

		String projectPath = System.getProperty("user.dir");
		FileReader reader = new FileReader(projectPath + "/src/main/resources/config/config.properties");

		Properties props = new Properties();
		props.load(reader);

		return a = props;

	}

	public static String getBrowser() throws IOException {

		fileConfig();
		String browser = a.getProperty("browser");
		return browser;

	}

	public static String getAmazonProduct_URL() throws IOException {

		fileConfig();
		String amazonProURL = a.getProperty("amazonProduct_url");
		return amazonProURL;
	}

	public static String getAmazon_URL() throws IOException {

		fileConfig();
		String amazonURL = a.getProperty("amazon_url");
		return amazonURL;

	}

}
