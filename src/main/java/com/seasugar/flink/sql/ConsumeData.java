package com.seasugar.flink.sql;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.factories.TableFactoryUtil;
import org.apache.flink.types.Row;


public class ConsumeData {
    public static void main(String[] args) throws Exception {
        // 创建表执行环境
        EnvironmentSettings settings = EnvironmentSettings
                .newInstance()
                .inStreamingMode()
                .build();
        TableEnvironment tableEnv = TableEnvironment.create(settings);

//        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);
        // 定义表来读取 Kafka 数据

        String createTableDDL = "CREATE TABLE input_table (\n" +
                "    `time` VARCHAR,\n" +
                "    id INT\n" +
                ") WITH (\n" +
                "    'connector.type' = 'kafka',\n" +
                "    'connector.version' = 'universal',\n" +
                "    'connector.topic' = 'source',\n" +
                "    'connector.properties.group.id' = 'test1',\n" +
                "     'connector.startup-mode' = 'earliest-offset',\n" +
                "    'connector.properties.zookeeper.connect' = '10.86.40.215:2181',\n" +
                "    'connector.properties.bootstrap.servers' = '10.86.40.215:9092',\n" +
                "    'format.type' = 'json'\n" +
                ")";

        // 不会执行SPI
        tableEnv.executeSql(createTableDDL);

//        TableFactoryUtil.findAndCreateTableSource()

        // 在执行DQL时，会调用建表
        String sql = "select * from input_table";
        tableEnv.sqlQuery(sql);
        String sql2 = "select count(*) from input_table group by id";
        Table table = tableEnv.sqlQuery(sql2);
        table.printSchema();
//        tableEnv.toAppendStream(result, Row.class).print("result");

//        String createOutDDL = "CREATE TABLE out_table (\n" +
//                "    id INT,\n" +
//                "    `user` STRING\n" +
//                ") WITH (\n" +
//                "    'connector' = 'print' )";
//
//        tableEnv.sqlUpdate(createOutDDL);
//        result.insertInto(createOutDDL);

//        env.execute();

    }
}