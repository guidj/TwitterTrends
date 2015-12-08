package com.gpjpe.twittertrends.domain.reader;

public class TweetSummary {

    String language;
    Long timestamp;
    String hashTag;

    public TweetSummary(String hashTag, String language, Long timestamp) {
        this.hashTag = hashTag;
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

    public String getHashTag() {
        return hashTag;
    }

    public void setHashTag(String hashTag) {
        this.hashTag = hashTag;
    }
}
