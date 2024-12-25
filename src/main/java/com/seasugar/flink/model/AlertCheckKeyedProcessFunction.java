/*
 * CopyRight (c) 2012-2020 Ezviz Co, Ltd. All rights reserved. Filename:
 * AlertValidateFlatMapFunction.java Create-User:
 * joe.zhao(zhaohaolin@hikvision.com.cn) Create-Date: 2021年3月4日 下午4:30:45
 */
package com.seasugar.flink.model;

import com.google.common.collect.Maps;
import org.apache.flink.api.common.state.MapState;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.api.common.state.StateTtlConfig;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;


public class AlertCheckKeyedProcessFunction extends KeyedProcessFunction<Tuple, FilterMetricEvent, AlertMessage> {

    private final static Logger LOG = LoggerFactory.getLogger(AlertCheckKeyedProcessFunction.class);

    private static final long serialVersionUID = 1L;
    private static final Map<String, Tuple2<Set, Long>> WARN_MAP = Maps.newConcurrentMap();

    private final static int ttlMinute = 1000 * 5;
    private final String cluster;
    private final Integer countThresholdValue;

    private ConcurrentHashMap<String, Set<String>> RuleMapState = new ConcurrentHashMap<>();

    public final AtomicBoolean TIMER_UPDATE_RATE = new AtomicBoolean(true);


    private Integer CHCHETIMEOUT = 180;

    private StateTtlConfig ttlConfig = null;

    private ConcurrentHashMap<String, Integer> alertRecoverMapState = new ConcurrentHashMap<>();

    private MapStateDescriptor<String, String> stateDescriptor = new MapStateDescriptor<String, String>(
            "rulestate", String.class, String.class);

    private MapStateDescriptor<String, String> warnStateDescriptor = new MapStateDescriptor<String, String>(
            "rulestate", String.class, String.class);

    private MapState<String, String> RuleMapStateCache = null;

    private MapState<String, String> warnRuleMapStateCache = null;

    private final String alertTitle;

    @Override
    public void onTimer(long timestamp, OnTimerContext context, Collector<AlertMessage> out) throws Exception {

        try {
            LOG.info("开始审计");
//			LOG.info(RuleMapStateCache.toString());
            if (RuleMapState != null && RuleMapState.size() > 0) {
                // ReluMapState Key是rulekey， value是endpoint的set
                Iterator<String> rulekeyset = RuleMapState.keySet().iterator();
                while (rulekeyset.hasNext()) {
                    String key = rulekeyset.next();
                    Set<String> allValue = RuleMapState.get(key);
                    Set<String> valueTmp = new HashSet<>();
                    Boolean check = false;

                    Iterator<String> iter = allValue.iterator();
                    while (iter.hasNext()) {
                        String value = iter.next();
                        // RuleMapStateCache作用 contains判断key是否存在
                        if (!RuleMapStateCache.contains(value)) {
                            check = true;
                            LOG.info("总节点去除节点" + value);
                        } else {
                            valueTmp.add(value);
                        }
                    }

                    //更新缓存
                    if (check) {
                        RuleMapState.put(key, valueTmp);
                    }

                }
            }


            //问题节点是否过期
            if (WARN_MAP != null && WARN_MAP.size() > 0) {
                Iterator<String> rulekeyset = WARN_MAP.keySet().iterator();
                while (rulekeyset.hasNext()) {
                    String key = rulekeyset.next();
                    Tuple2<Set, Long> tupleValue = WARN_MAP.get(key);
                    Set<String> allValue = tupleValue.f0;
                    Long countValue = tupleValue.f1;
                    Set<String> valueTmp = new HashSet<>();
                    Boolean check = false;

                    Iterator<String> iter = allValue.iterator();
                    while (iter.hasNext()) {
                        String value = iter.next();
                        // warnRuleMapStateCache作用
                        if (!warnRuleMapStateCache.contains(value)) {
                            check = true;
                            LOG.info("报警列表去除节点" + value);
                        } else {
                            valueTmp.add(value);
                        }
                    }

                    //更新缓存
                    if (check) {
                        WARN_MAP.put(key, new Tuple2<>(valueTmp, countValue));
                    }
                }
            }

            TIMER_UPDATE_RATE.compareAndSet(false, true);

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            LOG.error("定时审计节点错误");
        }
    }

    // 只执行一次
    @Override
    public void open(Configuration parameters) throws Exception {
        ParameterTool params = (ParameterTool) getRuntimeContext().getExecutionConfig()
                .getGlobalJobParameters();

//        CHCHETIMEOUT = params.getInt("CHCHETIMEOUT", 180);
        CHCHETIMEOUT = params.getInt("CHCHETIMEOUT", 360);
//		RuleMapStateCache = new CacheMapTimeOut<>(CHCHETIMEOUT);
//		warnRuleMapStateCache = new CacheMapTimeOut<>(CHCHETIMEOUT);

        ttlConfig = StateTtlConfig
                .newBuilder(Time.seconds(CHCHETIMEOUT))
                .setTtlTimeCharacteristic(StateTtlConfig.TtlTimeCharacteristic.ProcessingTime)
                .setUpdateType(StateTtlConfig.UpdateType.OnCreateAndWrite)
                .setStateVisibility(StateTtlConfig.StateVisibility.NeverReturnExpired)
                .cleanupFullSnapshot()
                .build();

        stateDescriptor.enableTimeToLive(ttlConfig);
        RuleMapStateCache = getRuntimeContext().getMapState(stateDescriptor);

        warnStateDescriptor.enableTimeToLive(ttlConfig);
        warnRuleMapStateCache = getRuntimeContext().getMapState(warnStateDescriptor);


    }

    public AlertCheckKeyedProcessFunction(String cluster, Integer countThresholdValue, Integer DEFUATCOUNTCHECK, String alertTitle) {
        this.cluster = cluster;
        this.countThresholdValue = countThresholdValue;
        this.alertTitle = alertTitle;
    }

    /**
     * @throws Exception
     * @see KeyedProcessFunction#processElement(Object,
     * Context,
     * Collector)
     */
    @Override
    public void processElement(FilterMetricEvent event, KeyedProcessFunction<Tuple, FilterMetricEvent, AlertMessage>.Context ctx,
                               Collector<AlertMessage> out) throws Exception {
        System.out.println("111");
    }

    public Integer getCount(Integer count) {
        if (count % 2 == 0) {
            return count / 2;
        } else {
            return (count + 1) / 2;
        }

    }

}
