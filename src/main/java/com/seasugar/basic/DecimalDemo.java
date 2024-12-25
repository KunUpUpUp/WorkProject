package com.seasugar.basic;

import java.lang.reflect.Type;
import java.math.BigDecimal;

public class DecimalDemo {
    public static void main(String[] args) {
        double num = 1.23122222222222222222222222222354235325;
        System.out.println(num);
        BigDecimal decimal = new BigDecimal("1.23122222222222222222222222222354235325");
        System.out.println(decimal);
//        decimal.add()

        // 0.7000000000000001
        System.out.println(0.8 - 0.1);
    }
}
