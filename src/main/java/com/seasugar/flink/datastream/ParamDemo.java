package com.seasugar.flink.datastream;

import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;


public class ParamDemo {
    private final static Logger LOG = LoggerFactory.getLogger(ParamDemo.class);
    private static boolean loggerEnabled = false;

    public static void main(String[] args) {
        ParameterTool params = ParameterTool.fromArgs(args);
        if (LOG.isInfoEnabled()) {
            LOG.info("read args is finished. args={}", new Object[]{args});
        }

        loggerEnabled = params.getBoolean("log", false);
        String cluster = params.get("cluster", "xyidc");
        Integer countThresholdValue = params.getInt("countThresholdValue", 60000);
        // 生产环境地址： http://alert.p.xyidc/rest/alert.do
        String url = params.get("url", "http://10.220.21.151:8080/rest/alert.do");
        int alertFilterTimeSec = params.getInt("alertFilterTimeSec", 10);
        boolean filterEnabled = params.getBoolean("filter", false);
        int DEFUATCOUNTCHECK = params.getInt("DEFUATCOUNTCHECK", 2);

        String alertTitle = params.get("alertTitle", "超过半数节点服务不可用兜底报警");

        String checkMetricName = params.get("checkMetricName", "podstatus");

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime);
        env.setRestartStrategy(
                RestartStrategies.fixedDelayRestart(Integer.MAX_VALUE, org.apache.flink.api.common.time.Time.of(10, TimeUnit.SECONDS)));

        env.getConfig()
                .setAutoWatermarkInterval(1000)
                .setGlobalJobParameters(params);

        System.out.println(env);
    }
}
