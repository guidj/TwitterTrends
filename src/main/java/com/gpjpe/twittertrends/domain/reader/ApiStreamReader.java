package com.gpjpe.twittertrends.domain.reader;

import com.gpjpe.twittertrends.CONFIG;
import com.gpjpe.twittertrends.SecretsConfig;
import org.apache.log4j.Logger;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;
import org.scribe.model.Token;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ApiStreamReader implements ITweetReader{
    OAuthService service;
    Token accessToken;
    InputStream tweetStream;
    BufferedReader reader;
    private final static Logger LOGGER = Logger.getLogger(ApiStreamReader.class.getName());


    public ApiStreamReader(String apiKey, String apiSecret,
                           String token, String tokenSecret, String streamEndpoint){
                service = new ServiceBuilder()
                .provider(TwitterApi.class)
                .apiKey(apiKey)
                .apiSecret(apiSecret)
                .build();
        accessToken = new Token(token,tokenSecret);

        OAuthRequest request = new OAuthRequest(Verb.GET, streamEndpoint);
        service.signRequest(accessToken, request);
        Response response = request.send();
        reader = new BufferedReader(
                new InputStreamReader(response.getStream()));

    }


    @Override
    public TweetSummary getTweet(){
        try {
            String line = reader.readLine();



            return new TweetSummary(line, line, 1L);
        } catch (IOException e){
            LOGGER.error(e);
            throw new RuntimeException(e);
        }


        //TODO complete

       /* OAuthRequest request = new OAuthRequest(Verb.GET, "https://stream.twitter.com/1.1/statuses/sample.json");
        service.signRequest(accessToken, request);
        Response response = request.send();
        System.out.println(response.getBody());*/




    }

   /* public void test(){
        try {


            OAuthRequest request = new OAuthRequest(Verb.GET, "https://stream.twitter.com/1.1/statuses/sample.json");
            service.signRequest(accessToken, request);
            Response response = request.send();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(response.getStream()))



            int counter = 0;

            while (true) {
//                String line = reader.readLine();
//                System.out.println(line);
                  counter++;
                //System.out.println(a);#
                if(counter == 100){
                    Thread.sleep(1000);
                    counter = 0;
                }

            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }*/

}
