package com.gpjpe.twittertrends;

public enum CONFIG {
    API_KEY("ApiKey"),
    API_SECRET("ApiSecret"),
    TOKEN("Token"),
    TOKEN_SECRET("TokenSecret"),
    STREAM_ENDPOINT("StreamEndpoint"),
    WAIT_TIME("WaitTime"),
    KAFKA_TOPIC("KafkaTopic");

    private String name;

    private CONFIG(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
