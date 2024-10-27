package com.hahsm.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    
    private static final Properties properties = new Properties();

    // Static block to load default properties
    static {
        try {
            // Load default properties
            loadProperties("application.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load properties from a specific file
    public static void loadProperties(String fileName) throws IOException {
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                throw new IOException("Properties file " + fileName + " not found!");
            }
            properties.load(input);
        }
    }

    // Get a property by key
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    // Get a property by key with a default value if not found
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}
