package com.seasugar.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LoggerDemo {
    final static Logger logger = LoggerFactory.getLogger(LoggerDemo.class);

    public static void main(String[] args) {
        Calendar c = Calendar.getInstance();
        Date tenMinutes = new Date();
        c.setTime(tenMinutes);
        //防止最近时间的数据还没计算出来，所以往前提10分钟
        c.add(Calendar.MINUTE, -10);
        long tenMinutesAgo = c.getTime().getTime();
        Date d = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String tenMinutesAgoFormat = sdf.format(d);
        logger.info("时间戳为:" + tenMinutesAgo + "\t时间为: " + tenMinutesAgoFormat);
//        logger.info("Hello World\nI will be my king\tI live for the the future");
    }
}
