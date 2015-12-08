package com.gpjpe.twittertrends.domain.reader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TweetSummary {

    String language;
    Long timestamp;
    List<String> hashTags;

    public TweetSummary(String hashTag, String language, Long timestamp) {
        this.hashTags = new ArrayList<>(Arrays.asList(new String[]{hashTag}));
        this.language = language;
        this.timestamp = timestamp;
    }

    public TweetSummary(List<String> hashTags, String language, Long timestamp) {
        this.hashTags = hashTags;
        this.language = language;
        this.timestamp = timestamp;

    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public List<String> getHashTags() {
        return hashTags;
    }

    public void setHashTags(List<String> hashTags) {
        this.hashTags = hashTags;
    }

    public String toString(){
        return String.format("[lang=%s, hashtag=%s, timestamp=%d]",this.language,this.hashTags,this.timestamp);
    }
}
