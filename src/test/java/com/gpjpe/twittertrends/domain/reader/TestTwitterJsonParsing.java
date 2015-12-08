package com.gpjpe.twittertrends.domain.reader;

import junit.framework.TestCase;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestTwitterJsonParsing extends TestCase {

    List<String> tweets;

    public void setUp() {

        tweets = new ArrayList<>();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("stream-data.json");
        Scanner scanner = new Scanner(inputStream);

        while (scanner.hasNextLine()) {

            tweets.add(scanner.nextLine());
        }
    }

    public void tearDown() {

    }

    public void testParsingValidTweet() throws IOException {

        for(String tweet: tweets){
            ApiStreamReader.parse(tweet);
        }
    }
}
