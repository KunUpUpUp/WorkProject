package com.seasugar.flink.datastream;

import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class StateDemo extends RichMapFunction<Integer, Integer> {

    private ValueState<Integer> countState;

    @Override
    public void open(Configuration parameters) {
        ValueStateDescriptor<Integer> descriptor = new ValueStateDescriptor<>("count", Integer.class);
        countState = getRuntimeContext().getState(descriptor);
    }

    @Override
    public Integer map(Integer value) throws Exception {
//        int currentCount = countState.value() == null ? 0 : countState.value();
//        currentCount++;
//        countState.update(currentCount);
        countState.update(countState.value() == null ? 1 : countState.value() + 1);
        return value;
    }

    public static void main(String[] args) throws Exception {
        // 创建 Flink 执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 模拟数据源
        DataStreamSource<Integer> source = env.fromElements(1, 2, 3, 4, 5);

        // 应用状态计算函数
        DataStream<Integer> result = source.map(new StateDemo());

        // 打印结果
        result.print();

        // 执行任务
        env.execute("Stateful Count Example");
    }
}