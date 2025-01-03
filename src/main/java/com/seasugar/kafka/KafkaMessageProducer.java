package com.seasugar.kafka;

import com.alibaba.fastjson2.JSONObject;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaMessageProducer {

    public static void sendMessage(String topic, String message) {
        KafkaProducer<String, String> producer = null;

        try {
            // 配置 Kafka 生产者属性
            Properties props = new Properties();
            props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.0.103:9092");
            props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
            props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

            // 创建生产者实例
            producer = new KafkaProducer<>(props);

            // 发送消息
            String key = "pod_event";
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, message);
            producer.send(record);
        } finally {
            // 关闭生产者
            if (producer != null) {
                producer.close();
            }
        }

    }
}
