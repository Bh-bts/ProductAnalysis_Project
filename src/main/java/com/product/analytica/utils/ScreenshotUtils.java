package com.product.analytica.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/**
 * Utility class for capturing screenshots in Selenium WebDriver tests.
 *
 * @author Bhavin.Thumar
 */
public class ScreenshotUtils {

    /**
     * Captures a screenshot of the current browser window and saves it to the specified location.
     *
     * @param driver         The WebDriver instance used to capture the screenshot.
     * @param screenshotName The name to assign to the captured screenshot.
     * @throws IOException If an I/O error occurs while saving the screenshot.
     */
    public static void captureScreenshot(WebDriver driver, String screenshotName) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;

        // Capture the screenshot as a file
        File sourceFile = ts.getScreenshotAs(OutputType.FILE);

        // Define the destination file path
        File destFile = new File("src/test/resources/captureScreenshot/" + screenshotName + ".png");

        // Copy the screenshot file to the destination
        FileUtils.copyFile(sourceFile, destFile);
    }
}
