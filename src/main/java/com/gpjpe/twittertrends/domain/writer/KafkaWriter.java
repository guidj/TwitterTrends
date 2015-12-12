package com.gpjpe.twittertrends.domain.writer;

import com.gpjpe.twittertrends.domain.reader.TweetSummary;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class KafkaWriter implements IStreamWriter {

    private String kafkaBrokerUri;
    private String topic;
    KafkaProducer<String, String> producer;

    public KafkaWriter(String kafkaBrokerUri, String topic) {
        this.kafkaBrokerUri = kafkaBrokerUri;
        this.topic = topic;
        init();
    }

    private void init(){

        //for kafka 0.8.0
//        props.put("metadata.broker.list", kafkaBrokerUri);
//        props.put("serializer.class", "kafka.serializer.StringEncoder");
//        props.put("request.required.acks", "1");

        Properties props = new Properties();
        props.put("bootstrap.servers", kafkaBrokerUri);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("acks", "1");

        this.producer = new KafkaProducer<>(props);
    }

    public void close(){
        this.producer.close();
    }

    @Override
    public void write(TweetSummary tweetSummary) {

        List<String> messages = new ArrayList<String>();

        for(String hashTag: tweetSummary.getHashTags()){
            messages.add(String.format("%s,%s,%s", tweetSummary.getLanguage(), tweetSummary.getTimestamp(), hashTag));
        }

        for(String message: messages){
            try {
                this.producer.send(new ProducerRecord<String, String>(this.topic, message)).get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
