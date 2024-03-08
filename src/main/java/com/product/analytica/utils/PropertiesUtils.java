package com.product.analytica.utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Utility class for loading and retrieving properties from a configuration file.
 *
 * @author Bhavin.Thumar
 */
public class PropertiesUtils {

    private static final String CONFIG_FILE_PATH = "/src/test/resources/config/config.properties";

    private static final String HEADLESS_KEY = "headless";
    private static final String URL_KEY = "amazon_url";
    private static final String PRODUCT_KEY = "amazon_product_url";

    private static Properties prop;

    /**
     * Loads properties from the configuration file.
     *
     * @return The loaded properties.
     * @throws RuntimeException If an error occurs while loading the properties file.
     */
    public static Properties loadProperties() {
        if (prop == null) {
            String projectPath = System.getProperty("user.dir");
            prop = new Properties();

            try (FileReader reader = new FileReader(projectPath + CONFIG_FILE_PATH)) {
                prop.load(reader);
            } catch (IOException e) {
                throw new RuntimeException("Error loading properties file", e);
            }
        }
        return prop;
    }

    /**
     * Retrieves the headless mode setting from the properties.
     *
     * @return The headless mode setting.
     */
    public static boolean getHeadlessMode() {
        String headlessValue = getProperty(HEADLESS_KEY);
        return Boolean.parseBoolean(headlessValue);
    }

    /**
     * Retrieves the Amazon URL from the properties.
     *
     * @return The Amazon URL.
     */
    public static String getAmazon_URL() {
        return getProperty(URL_KEY);
    }

    /**
     * Retrieves the Amazon product URL from the properties.
     *
     * @return The Amazon product URL.
     */
    public static String getAmazonProduct_URL() {
        return getProperty(PRODUCT_KEY);
    }

    /**
     * Retrieves a property value based on the provided key.
     *
     * @param key The key for the property.
     * @return The value associated with the key.
     */
    private static String getProperty(String key) {
        return loadProperties().getProperty(key);
    }
}
