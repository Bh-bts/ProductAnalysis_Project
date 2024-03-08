package com.product.analytica.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.product.analytica.browsers.Browsers;
import com.product.analytica.helpers.ActionHelpers;
import com.product.analytica.utils.PropertiesUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

/**
 * Page object class for interacting with Amazon product pages and analyzing reviews and ratings.
 *
 * @author Bhavin.Thumar
 */
public class AmazonPage extends Browsers {

    @FindBy(xpath = "//div[@class='a-row a-spacing-base a-size-base']")
    WebElement reviewsCount;
    @FindBy(xpath = "//ul[@class='a-pagination']//li[2]")
    WebElement nextPage;
    @FindBy(xpath = "//h1[@class='a-size-large a-text-ellipsis']/a")
    WebElement productName;
    public static @FindBy(xpath = "//span[@data-hook='rating-out-of-text']")
    WebElement productRatings;

    public void productReviewsAnalysis() throws InterruptedException {
        driver.get(PropertiesUtils.getAmazonProduct_URL());
        ActionHelpers.dynamicTimeOut(reviewsCount);
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
                    ActionHelpers.scrollTillElement(nextPage);
                    nextPage.click();
                    Thread.sleep(2000);
                    ActionHelpers.dynamicTimeOut(reviewsCount);
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
                float sentimentValue = RNNCoreAnnotations.getPredictedClass(sentimentTree);

                totalSentimentvalue += sentimentValue;
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
        productRatingsAnalysis();
    }

    /**
     * Analyzes the product ratings and prints a message based on the ratings.
     */
    public void productRatingsAnalysis() {
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
            System.out.println(" Can't Identify... ");
        }
    }
}
