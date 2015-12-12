package com.gpjpe.twittertrends;

import com.gpjpe.twittertrends.domain.reader.ApiStreamReader;
import com.gpjpe.twittertrends.domain.reader.FileStreamReader;
import com.gpjpe.twittertrends.domain.reader.ITweetReader;
import com.gpjpe.twittertrends.domain.reader.TweetSummary;
import com.gpjpe.twittertrends.domain.writer.IStreamWriter;
import com.gpjpe.twittertrends.domain.writer.KafkaWriter;
import org.apache.log4j.Logger;

public class App {

    private final static Logger LOGGER = Logger.getLogger(ApiStreamReader.class.getName());

    public static void main(String[] args) throws InterruptedException {

        StringBuilder usage = new StringBuilder();
        LOGGER.info(args.length);

        if (args.length != 7){
            usage.append("${APP_DIR}/appassembler/bin/startTwitterApp.sh mode apiKey apiSecret tokenValue tokenSecret kafkaBroker filepath");
            usage.append("Where:\n" +
                    "\n" +
                    "mode: 1 for File, 2 for Twitter Stream API\n" +
                    "apiKey: for twitter OAuth\n" +
                    "apiSecret: for twitter OAuth\n" +
                    "tokenValue: for twitter OAuth\n" +
                    "tokenSecret: for twitter OAuth\n" +
                    "kafkaBroker: IP:port for Kafka broker\n" +
                    "filename: path of file with tweet summaries");


            System.out.println(usage.toString());

            return;
        }

        //TODO: validate parameters
        AppConfig appConfig = new AppConfig();
        int mode = Integer.parseInt(args[0]);
        String apiKey = args[1];
        String apiSecret = args[2];
        String tokenValue = args[3];
        String tokenSecret = args[4];
        String kafkaBroker = args[5];
        String filename = args[6];
        String topic = appConfig.getProperty(CONFIG.KAFKA_TOPIC, "TweetStream");
        Long waitTime = Long.parseLong(appConfig.getProperty(CONFIG.WAIT_TIME, "2000"));

        LOGGER.info("Running with parameters:");
        LOGGER.info("Mode: " + mode);
        LOGGER.info("APIKey: " + apiKey);
        LOGGER.info("Token Value: " + tokenValue);
        LOGGER.info("Kafka Broker: " + kafkaBroker);
        LOGGER.info("File: " + filename);
        LOGGER.info("WaitTime: " + waitTime);
        LOGGER.info("KafkaTopic: " + kafkaBroker);

        ITweetReader tweeterStreamReader;
        IStreamWriter streamWriter = new KafkaWriter(kafkaBroker, topic);

        if (mode == 1){
            tweeterStreamReader = new FileStreamReader(filename);
        }else if (mode == 2){
            tweeterStreamReader = new ApiStreamReader(apiKey, apiSecret, tokenValue, tokenSecret,
                    appConfig.getProperty(CONFIG.STREAM_ENDPOINT, null));
        }else{
            throw new IllegalArgumentException(String.format("Invalid mode: %d. Should be 1 or 2", mode));
        }

        Long count = 0L;
        final Long limit = 1000L;
        TweetSummary tweetSummary;

        while(true){

            tweetSummary = tweeterStreamReader.getTweetSummary();

            if (tweetSummary == null && tweeterStreamReader.isClosed()){
                break;
            }

            if (tweetSummary != null){
                LOGGER.info(tweetSummary);
                streamWriter.write(tweetSummary);
            }

            count++;

            if (count > limit){
                LOGGER.info("Sleeping");
                Thread.sleep(waitTime);
                count = 0L;
            }
        }
    }
}
