package com.gpjpe.twittertrends.domain.writer;

import com.gpjpe.twittertrends.domain.reader.TweetSummary;

public interface IStreamWriter {
    void write(TweetSummary tweetSummary);
}
