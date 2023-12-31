package org.github.prathamdubey2005.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BotUtils {
    // Get method to fetch properties from the specified '.property' file
    public static String getProperty(String file, String property) {
        // Try to read the specified property file
        try (InputStream input = BotUtils.class.getClassLoader().getResourceAsStream(file)) {
            Properties prop = new Properties();
            prop.load(input);

            // Return the specified property from the file
            return prop.getProperty(property);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return an empty string if nothing is found
        return "";
    }
}
