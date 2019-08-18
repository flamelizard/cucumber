package eu.guy.cucumber.atm.common;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private static String DEFAULT_CONFIG = "project.properties";
    private static Properties config;

    public static void loadConfig() throws IOException {
        String configPath = System.getProperty("config.path");
        if (configPath == null)
            configPath = DEFAULT_CONFIG;
        config = new Properties();
        config.load(
                new FileReader(Common.getFileFromResources(configPath)));
    }

    public static String getValue(String key) {
        if (config == null)
            throw new RuntimeException("Config file has not been loaded");
        return config.getProperty(key);
    }
}