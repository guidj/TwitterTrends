package com.gpjpe.twittertrends;

import com.gpjpe.twittertrends.domain.reader.ApiStreamReader;
import org.apache.log4j.Logger;

/**
 * Hello world!
 *
 */
public class App
{
    private final static Logger LOGGER = Logger.getLogger(ApiStreamReader.class.getName());
    public static void main( String[] args )
    {
        System.out.println("These is what you fed me:");
        for(String argument: args){
            LOGGER.info("\t" + argument);

        }


        SecretsConfig secretsConfig= new SecretsConfig();
        ApiStreamReader streamReader = new ApiStreamReader(
                secretsConfig.getProperty(CONFIG.API_KEY),
                secretsConfig.getProperty(CONFIG.API_SECRET),
                secretsConfig.getProperty(CONFIG.TOKEN),
                secretsConfig.getProperty(CONFIG.TOKEN_SECRET),
                secretsConfig.getProperty(CONFIG.STREAM_ENDPOINT));

        int counter = 0;
        while (true){
            LOGGER.info(streamReader.getTweet().toString());
            counter++;
            if (counter == 100){
                try {
                    Thread.sleep(1000);
                    counter = 0;
                } catch (InterruptedException e) {
                    LOGGER.error(e);
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
