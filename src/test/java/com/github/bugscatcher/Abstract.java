package com.github.bugscatcher;

import org.junit.BeforeClass;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class Abstract {
    static String nameToSearch;

    @BeforeClass
    public static void beforeTests() throws IOException {
        Properties properties = loadProperties();
        nameToSearch = properties.getProperty("user.name");
    }

    private static Properties loadProperties() throws IOException {
        Properties properties = new Properties();
        InputStream inputStream = Abstract.class.getResourceAsStream("/test.properties");
        properties.load(inputStream);
        return properties;
    }
}
