package com.seasugar.flink.datastream;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;



public class MapDemo {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<String> input = env.fromElements("apple", "banana", "cherry");

        // 使用 map 函数将每个字符串转换为其长度
        DataStream<Integer> mappedStream = input.map(new MapFunction<String, Integer>() {
            @Override
            public Integer map(String value) {
                return value.length();
            }
        });

        // 使用 flatMap 函数将每个字符串拆分成字符，并输出每个字符
        DataStream<Character> flatMappedStream = input.flatMap(new FlatMapFunction<String, Character>() {
            @Override
            public void flatMap(String value, Collector<Character> out) {
                for (char c : value.toCharArray()) {
                    out.collect(c);
                }
            }
        });

        mappedStream.print();
        flatMappedStream.print();

        env.execute();
    }
}
