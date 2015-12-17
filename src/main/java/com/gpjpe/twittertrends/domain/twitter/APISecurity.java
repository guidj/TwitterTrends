package com.gpjpe.twittertrends.domain.twitter;


public class APISecurity {

    String apiKey;
    String apiSecret;
    String tokenValue;
    String tokenSecret;

    public APISecurity(String apiKey, String apiSecret, String tokenValue, String tokenSecret) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.tokenValue = tokenValue;
        this.tokenSecret = tokenSecret;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public String getTokenSecret() {
        return tokenSecret;
    }

    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }
}
