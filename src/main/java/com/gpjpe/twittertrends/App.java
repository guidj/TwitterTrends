package com.gpjpe.twittertrends;

import com.gpjpe.twittertrends.domain.reader.ApiStreamReader;
import org.apache.log4j.Logger;

public class App
{
    private final static Logger LOGGER = Logger.getLogger(ApiStreamReader.class.getName());
    public static void main( String[] args )
    {
        System.out.println("These is what you fed me:");
        for(String argument: args){
            LOGGER.info("\t" + argument);

        }
    }
}
