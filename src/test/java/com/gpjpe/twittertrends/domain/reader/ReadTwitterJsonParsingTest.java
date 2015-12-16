package com.gpjpe.twittertrends.domain.reader;

import junit.framework.TestCase;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadTwitterJsonParsingTest extends TestCase {

    List<String> tweets;

    public void setUp() {

        tweets = new ArrayList<>();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("stream-data.txt");
        Scanner scanner = new Scanner(inputStream);

        while (scanner.hasNextLine()) {

            tweets.add(scanner.nextLine());
        }
    }

    public void tearDown() {

    }

    public void testShouldParseValidTweet() throws IOException{

        String tweetJson = "{\n" +
                "  \"created_at\": \"Tue Dec 08 13:06:01 +0000 2015\",\n" +
                "  \"entities\": {\n" +
                "    \"hashtags\": [\n" +
                "      {\n" +
                "        \"text\": \"hashTagUno\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"text\": \"hashTag2\"\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  \"lang\": \"en\",\n" +
                "  \"timestamp_ms\": \"1449579961657\"\n" +
                "}";

        TweetSummary tweetSummary = TweetSummary.parseCreatedTweet(tweetJson);

        assertNotNull(tweetSummary);
        assertEquals(tweetSummary.getLanguage(), "en");
        assertEquals(tweetSummary.getTimestamp().compareTo(1449579961657L/1000L), 0);
        assertEquals(tweetSummary.getHashTags().size(), 2);
        for(String hashTag: new String[]{"hashTagUno", "hashTag2"}) {
            assertTrue(tweetSummary.getHashTags().contains(hashTag));
        }
    }

    public void testShouldNotParseDeletedTweet() throws IOException {

        String tweetJson = "{\n" +
                "  \"deleted\": \"Tue Dec 08 13:06:01 +0000 2015\",\n" +
                "  \"lang\": \"en\",\n" +
                "  \"timestamp_ms\": \"1449579961657\"\n" +
                "}";

        TweetSummary tweetSummary =  TweetSummary.parseCreatedTweet(tweetJson);
        assertNull(tweetSummary);
    }

    public void testCheckTotalValidTweetsInStreamSample() throws IOException {

        TweetSummary tweetSummary;
        List<TweetSummary> summaryTweets = new ArrayList<>();
        for(String tweetJson: tweets){
            tweetSummary = TweetSummary.parseCreatedTweet(tweetJson);

            if (tweetSummary != null){
                summaryTweets.add(tweetSummary);
            }
        }

        assertEquals(summaryTweets.size(), 10);
    }
}
