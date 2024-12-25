package com.seasugar.flink.datastream.process;

import com.seasugar.flink.model.AlertMessage;
import com.seasugar.flink.model.FilterMetricEvent;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;

public class TestProcess extends ProcessFunction<String, AlertMessage> {

    @Override
    public void processElement(String s, ProcessFunction<String, AlertMessage>.Context context, Collector<AlertMessage> collector) throws Exception {
        System.out.println(s);
        System.out.println();
    }
}
