package com.gpjpe.twittertrends.domain.reader;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class FileStreamReader implements ITweetReader {

    private final static Logger LOGGER = Logger.getLogger(FileStreamReader.class.getName());

    String filePath;
    BufferedReader reader;
    boolean done;

    public FileStreamReader(String filePath) {
        this.filePath = filePath;
        reader = null;
        done = false;
        try {
            reader = new BufferedReader(new FileReader(filePath));
        } catch (IOException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public TweetSummary getTweetSummary() {
        try {

            String line = reader.readLine();
            if (line != null) {
                return TweetSummary.parseCreatedTweet(line);
            }

            done = true;
        } catch (IOException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        } finally {
            if (reader != null && done) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    LOGGER.error(ex);
                }
            }
        }

        return null;
    }

    @Override
    public boolean isClosed() {
        return done;
    }
}
