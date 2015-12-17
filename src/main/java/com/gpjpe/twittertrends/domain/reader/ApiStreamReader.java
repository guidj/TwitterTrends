package com.gpjpe.twittertrends.domain.reader;

import com.gpjpe.twittertrends.domain.twitter.APISecurity;
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


public class ApiStreamReader implements ITweetReader {

    APISecurity security;
    String streamEndpoint;

    OAuthService service;
    Token accessToken;
    BufferedReader reader;
    private final static Logger LOGGER = Logger.getLogger(ApiStreamReader.class.getName());


    public ApiStreamReader(APISecurity security, String streamEndpoint) {

        this.security = security;
        this.streamEndpoint = streamEndpoint;
        init();
    }

    private void init(){
        service = new ServiceBuilder()
                .provider(TwitterApi.class)
                .apiKey(this.security.getApiKey())
                .apiSecret(this.security.getApiSecret())
                .build();
        accessToken = new Token(security.getTokenValue(), this.security.getTokenSecret());
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
            return TweetSummary.parseCreatedTweet(reader.readLine());
        } catch(javax.net.ssl.SSLException e){

            try {
                Thread.sleep(5000);
                this.signIn();
                return TweetSummary.parseCreatedTweet(reader.readLine());
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
}
