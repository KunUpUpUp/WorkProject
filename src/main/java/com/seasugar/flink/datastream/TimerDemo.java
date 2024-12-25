package com.seasugar.flink.datastream;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

import java.time.LocalDateTime;

public class TimerDemo extends ProcessFunction<String, String> {

    @Override
    public void processElement(String value, Context ctx, Collector<String> out) throws Exception {
        // 注册一个 5 秒后的定时器
        ctx.timerService().registerEventTimeTimer(ctx.timestamp() + Time.seconds(5).toMilliseconds());
    }

    @Override
    public void onTimer(long timestamp, OnTimerContext ctx, Collector<String> out) throws Exception {
        // 当定时器触发时执行的逻辑
        System.out.println("Timer triggered at: " + LocalDateTime.now());
    }
}

