package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestDataReader {
    private static Properties properties;

    static {
        properties = new Properties();
        try (InputStream input = TestDataReader.class.getClassLoader()
                .getResourceAsStream("testdata.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to find testdata.properties");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load testdata.properties", e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static int getIntProperty(String key) {
        return Integer.parseInt(properties.getProperty(key));
    }

    // Specific getters for commonly used properties
    public static String getImdbBaseUrl() {
        return getProperty("imdb.base.url");
    }

    public static String getSearchTermQA() {
        return getProperty("search.term.qa");
    }

    public static int getCastMinCount() {
        return getIntProperty("cast.min.count");
    }

    public static int getCastMemberIndex() {
        return getIntProperty("cast.member.index");
    }

    public static int getDropdownWait() {
        return getIntProperty("dropdown.wait");
    }

    public static int getPageLoadWait() {
        return getIntProperty("page.load.wait");
    }
}
