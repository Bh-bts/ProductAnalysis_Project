package com.Product_Analysis.testcase;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.Product_Analysis.Obj.Amazon_Obj;
import com.Product_Analysis.utils.PropertiesClass;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

public class Amazon_Page extends Amazon_Obj {

	public void prouductReviews() throws InterruptedException, IOException {

		driver.get(PropertiesClass.getAmazonProduct_URL());

		WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(30));
		w.until(ExpectedConditions.visibilityOf(reviewsCount));

		String product_name = productName.getText();

		String count = reviewsCount.getText();
		String[] parts = count.split(",");

		int numReviews = -1;
		for (String part : parts) {

			part = part.trim();

			if (part.endsWith("with reviews")) {
				String numStr = part.replace("with reviews", "");
				numReviews = Integer.parseInt(numStr.trim());
				break;
			}
		}

		StringBuilder sb = new StringBuilder();

		JavascriptExecutor js = (JavascriptExecutor) driver;

		if (driver.findElements(By.xpath("//ul[@class='a-pagination']//li[2]")).size() > 0) {

			while (true) {

				String attributeValue = nextPage.getAttribute("class");

				if (attributeValue.equalsIgnoreCase("a-last")) {

					for (int i = 1; i <= 10; i++) {

						if (driver.findElements(By.xpath("//div[@id='cm_cr-review_list']//div[@data-hook='review'][" + i
								+ "]//span[@data-hook='review-body']/span")).size() > 0) {

							List<WebElement> reviewsText = driver
									.findElements(By.xpath("//div[@id='cm_cr-review_list']//div[@data-hook='review']["
											+ i + "]//span[@data-hook='review-body']/span"));

							for (WebElement review : reviewsText) {
								sb.append(review.getText() + '\n');

							}

						}

					}

					js.executeScript("arguments[0].scrollIntoView(true);", nextPage);
					nextPage.click();

					Thread.sleep(2000);
					w.until(ExpectedConditions.visibilityOf(reviewsCount));

				} else {

					for (int i = 1; i <= 10; i++) {

						if (driver.findElements(By.xpath("//div[@id='cm_cr-review_list']//div[@data-hook='review'][" + i
								+ "]//span[@data-hook='review-body']/span")).size() > 0) {

							List<WebElement> reviewsText = driver
									.findElements(By.xpath("//div[@id='cm_cr-review_list']//div[@data-hook='review']["
											+ i + "]//span[@data-hook='review-body']/span"));

							for (WebElement review : reviewsText) {
								sb.append(review.getText() + '\n');

							}

						}

					}
					break;
				}

			}

		} else {

			for (int i = 1; i <= 10; i++) {

				if (driver.findElements(By.xpath("//div[@id='cm_cr-review_list']//div[@data-hook='review'][" + i
						+ "]//span[@data-hook='review-body']/span")).size() > 0) {

					List<WebElement> reviewsText = driver
							.findElements(By.xpath("//div[@id='cm_cr-review_list']//div[@data-hook='review'][" + i
									+ "]//span[@data-hook='review-body']/span"));

					for (WebElement review : reviewsText) {
						sb.append(review.getText() + '\n');

					}

				}

			}

		}

		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, depparse, parse, sentiment");
		props.setProperty("enforceRequirements", "false");
		props.setProperty("ssplit.eolonly", "true");
		props.setProperty("ner.applyNumericClassifiers", "false");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

		List<String> testcase = Arrays.asList(sb.toString().split("\n"));

		List<Annotation> annotations = new ArrayList(testcase);
		float totalSentimentvalue = 0;

		for (String ts : testcase) {
			Annotation annotation = new Annotation(ts);
			pipeline.annotate(annotation);
			annotations.add(annotation);

			for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
				Tree sentimentTree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
				float sentimateValue = RNNCoreAnnotations.getPredictedClass(sentimentTree);

				totalSentimentvalue += sentimateValue;

			}

		}

		float averageSentiment = totalSentimentvalue / numReviews;

		if (averageSentiment <= 2.99) {
			System.out.println(product_name + " : Product is poor.");

		} else if (averageSentiment == 3) {
			System.out.println(product_name + " : Average product");

		} else if (3.1 <= averageSentiment && averageSentiment <= 5) {
			System.out.println(product_name + " : Product is good.");

		} else {
			System.out.println(product_name + " Can't Indentify...");

		}

		System.out.println(averageSentiment + '\n');
		productRatings();
	}

	public void productRatings() {

		String count = productRatings.getText();
		String[] parts = count.split(" ");

		String numStr = parts[0];
		double numRatings = Double.parseDouble(numStr);

		if (numRatings <= 2.1) {
			System.out.println(" Product is poor based on Ratings. ");

		} else if (numRatings == 3) {
			System.out.println(" Product is average based on Ratings. ");

		} else if (3.1 <= numRatings && numRatings <= 5) {
			System.out.println(" Product is good based on Ratings. ");

		} else {
			System.out.println(" Can't Indentify... ");

		}

	}

}
