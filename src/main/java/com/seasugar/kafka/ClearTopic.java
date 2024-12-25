package com.seasugar.kafka;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DeleteTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class ClearTopic {

    public static void main(String[] args) {
        Properties properties = new Properties();
//        properties.setProperty("bootstrap.servers", "10.86.40.215:9092");
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        try (AdminClient adminClient = AdminClient.create(properties)) {
            // 删除主题
            DeleteTopicsResult deleteTopicsResult = adminClient.deleteTopics(Collections.singletonList("topic-name"));
            deleteTopicsResult.all().get(); // 等待删除完成

            System.out.println("Topic 'test' has been deleted.");

            // 等待2秒
            Thread.sleep(2000);

            // 重新创建主题
            NewTopic newTopic = new NewTopic("pod_event", 1, (short) 1); // 1个分区，副本因子为1
            adminClient.createTopics(Collections.singletonList(newTopic)).all().get();

            System.out.println("Topic 'test' has been recreated.");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
