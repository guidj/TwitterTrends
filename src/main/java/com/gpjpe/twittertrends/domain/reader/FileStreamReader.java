package com.gpjpe.twittertrends.domain.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

public class FileStreamReader implements ITweetReader {

    private final static Logger LOGGER = Logger.getLogger(FileStreamReader.class.getName());

    String filePath;

    public FileStreamReader(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<TweetSummary> readStream() {

        FileReader fileReader = null;

        try {
            fileReader = new FileReader(new File(filePath));
        } catch (FileNotFoundException ex) {
            LOGGER.error(ex);
            throw new RuntimeException(ex);
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException ex) {
                    LOGGER.error(ex);
                    throw new RuntimeException(ex);
                }
            }
        }

        return null;
    }
}
