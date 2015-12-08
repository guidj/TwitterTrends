package com.gpjpe.twittertrends;

import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

public class SecretsConfig {

    private final static String SECRETS_FILE = "secrets.properties";
    private final static Logger LOGGER = Logger.getLogger(App.class.getName());

    Properties properties;

    public SecretsConfig() {
        InputStream inputStream;

        properties = new Properties();

        inputStream = getClass().getClassLoader().getResourceAsStream(SECRETS_FILE);

        try {
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new FileNotFoundException("Property file '" + SECRETS_FILE + "' not found in the classpath");
            }
        } catch (IOException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }

    public String getProperty(CONFIG key){
        return this.properties.getProperty(key.getName());
    }
}
