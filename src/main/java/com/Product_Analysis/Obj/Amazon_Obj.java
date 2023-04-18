package com.Product_Analysis.Obj;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.Product_Analysis.Browsers.AllBrowsers;

public class Amazon_Obj extends AllBrowsers {
	
	public static @FindBy(xpath = "//div[@class='a-row a-spacing-base a-size-base']") WebElement reviewsCount;
	public static @FindBy(xpath = "//div[@id='cm_cr-review_list']//div[@data-hook='review'][1]//span[@data-hook='review-body']/span") WebElement reviewText;
	public static @FindBy(xpath = "//ul[@class='a-pagination']//li[2]") WebElement nextPage;
	public static @FindBy(xpath = "//h1[@class='a-spacing-small']") WebElement signIn;
	public static @FindBy(xpath = "//h1[@class='a-size-large a-text-ellipsis']/a") WebElement productName;
	public static @FindBy(xpath = "//span[@data-hook='rating-out-of-text']") WebElement productRatings;
	
	

}
