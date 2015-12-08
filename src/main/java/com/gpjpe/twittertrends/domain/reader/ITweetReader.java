package com.gpjpe.twittertrends.domain.reader;

import java.util.List;

public interface ITweetReader {
    List<TweetSummary> readStream();
}
