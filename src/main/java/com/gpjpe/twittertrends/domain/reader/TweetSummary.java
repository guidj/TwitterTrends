package com.gpjpe.twittertrends.domain.reader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
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

    public static TweetSummary parseCreatedTweet(String jsonTweet) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readValue(jsonTweet, JsonNode.class);

        JsonNode createdAtNode = jsonNode.get("created_at");

        if (createdAtNode == null) {
            return null;
        }

        JsonNode hashTagNodes = jsonNode.get("entities").get("hashtags");

        List<String> hashTags = new ArrayList<>();

        for (JsonNode hashTagNode : hashTagNodes) {
            String hashTag = hashTagNode.get("text").asText();
            hashTags.add(hashTag);
        }

        if (hashTags.isEmpty()){
            return null;
        }

        JsonNode langNode = jsonNode.get("lang");
        JsonNode timestampNode = jsonNode.get("timestamp_ms");

        if (langNode == null || timestampNode == null){
            return null;
        }

        Long timestamp = Long.parseLong(timestampNode.asText())/1000L;

        return new TweetSummary(hashTags, langNode.asText(), timestamp);
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
