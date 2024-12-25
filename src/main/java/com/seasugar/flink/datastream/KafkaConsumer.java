package com.seasugar.flink.datastream;

import com.seasugar.flink.datastream.process.TestProcess;
import com.seasugar.flink.model.AlertCheckKeyedProcessFunction;
import com.seasugar.flink.model.AlertMessage;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.util.Collector;

import java.util.Properties;

public class KafkaConsumer {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");
        properties.setProperty("zookeeper.connect", "localhost:2181");
        properties.setProperty("group.id", "test");
        FlinkKafkaConsumer<String> kafkaConsumer = new FlinkKafkaConsumer<>("pod_event", new org.apache.flink.api.common.serialization.SimpleStringSchema(), properties);
        DataStream<String> inputStream = env.addSource(kafkaConsumer);
        DataStream<AlertMessage> alertStream = inputStream.process(new TestProcess());


        env.execute("My flink");
    }
}
