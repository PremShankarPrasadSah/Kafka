package com.company;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;

import java.io.*;
import java.util.Properties;


public class Producer {

    public static void main(String[] args) {
        // For example 192.168.1.1:9092,192.168.1.2:9092

        System.out.println("Creating kafka Producer.");
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer kafkaProducer = new KafkaProducer(properties);



        try  {
            JSONParser jp = new JSONParser();
            FileReader reader = new FileReader("src/main/java/com/company/users.json");
            Object obj = jp.parse(reader);
            System.out.println(obj);
            JSONArray userList = (JSONArray) obj;
            System.out.println(userList);
            int i=0;
            userList.forEach(user ->{
                System.out.println(user);
                kafkaProducer.send(new ProducerRecord("user", 0,"test message - " + user));
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Finished- closed Kafka Producer.");
            kafkaProducer.close();
        }
    }
}