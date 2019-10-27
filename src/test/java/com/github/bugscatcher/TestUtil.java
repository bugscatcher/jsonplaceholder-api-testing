package com.github.bugscatcher;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class TestUtil {
    static boolean isValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    static String getMessageForEmailInvalidFormat(String email) {
        return String.format("The Email `%s` is in an invalid format.", email);
    }

    static String getMessageForNonExistentUser(String username) {
        return String.format("There is no user with username `%s`.", username);
    }

    static String getMessageForNonExistentProperty(String property) {
        return String.format("There is no property `%s`.", property);
    }

    static Properties loadProperties() throws IOException {
        Properties properties = new Properties();
        InputStream inputStream = TestUtil.class.getResourceAsStream("/test.properties");
        properties.load(inputStream);
        return properties;
    }
}
