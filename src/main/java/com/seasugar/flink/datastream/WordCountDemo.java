package com.seasugar.flink.datastream;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;


public class WordCountDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> lineDS = env.readTextFile("E:\\JavaProject\\ProjectTest\\src\\main\\java\\com\\seasugar\\flink\\1.txt");
        SingleOutputStreamOperator<Tuple2<String, Integer>> wordAndOne = lineDS.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
            @Override
            // 重写变量名是可以修改的
            public void flatMap(String value, Collector<Tuple2<String, Integer>> out) throws Exception {
                // 按照空格切分单词
                String[] words = value.split(" ");
                for (String word : words) {
                    Tuple2<String, Integer> wordTuple2 = Tuple2.of(word, 1);
                    out.collect(wordTuple2);
                }
            }
        });
        KeyedStream<Tuple2<String, Integer>, String> wordAndOneKS = wordAndOne.keyBy(new KeySelector<Tuple2<String, Integer>, String>() {
            @Override
            public String getKey(Tuple2<String, Integer> value) throws Exception {
                return value.f0;
            }
        });
        // 以field1做sum
        SingleOutputStreamOperator<Tuple2<String, Integer>> sum = wordAndOneKS.sum(1);
        sum.print();

        env.execute();
    }
}
