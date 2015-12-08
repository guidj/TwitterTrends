package com.gpjpe.twittertrends.domain.reader;

public interface ITweetReader {
    TweetSummary getTweetSummary();
    boolean isClosed();
}
