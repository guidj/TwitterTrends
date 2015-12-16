package com.gpjpe.twittertrends.domain.reader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class ApiStreamReader implements ITweetReader {
    String apiKey;
    String apiSecret;
    String token;
    String tokenSecret;
    String streamEndpoint;

    OAuthService service;
    Token accessToken;
    BufferedReader reader;
    private final static Logger LOGGER = Logger.getLogger(ApiStreamReader.class.getName());


    public ApiStreamReader(String apiKey, String apiSecret,
                           String token, String tokenSecret, String streamEndpoint) {
//        service = new ServiceBuilder()
//                .provider(TwitterApi.class)
//                .apiKey(apiKey)
//                .apiSecret(apiSecret)
//                .build();
//        accessToken = new Token(token, tokenSecret);
//
//        OAuthRequest request = new OAuthRequest(Verb.GET, streamEndpoint);
//        service.signRequest(accessToken, request);
//        Response response = request.send();
//        this.reader = new BufferedReader(new InputStreamReader(response.getStream()));

        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.token = token;
        this.tokenSecret = tokenSecret;
        this.streamEndpoint = streamEndpoint;
        init();
    }

    private void init(){
        service = new ServiceBuilder()
                .provider(TwitterApi.class)
                .apiKey(apiKey)
                .apiSecret(apiSecret)
                .build();
        accessToken = new Token(token, tokenSecret);
        signIn();
    }

    private void signIn(){
        OAuthRequest request = new OAuthRequest(Verb.GET, streamEndpoint);
        service.signRequest(accessToken, request);
        Response response = request.send();
        this.reader = new BufferedReader(new InputStreamReader(response.getStream()));
    }


    @Override
    public TweetSummary getTweetSummary() {
        try {
            return parse(reader.readLine());
        } catch(javax.net.ssl.SSLException e){

            try {
                Thread.sleep(5000);
                this.signIn();
                return parse(reader.readLine());
            } catch (InterruptedException | IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        catch (IOException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isClosed() {
        return (reader == null);
    }

    public static TweetSummary parse(String jsonTweet) throws IOException {

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
}
