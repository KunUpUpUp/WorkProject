package com.seasugar.basic;

import java.util.Arrays;
import java.util.List;

public class CheckBase {
    public static void main(String[] args) {
        String[] check = {"a","b","c"};
        List<String> checkList = Arrays.asList(check);
        for (String s : checkList) {
            System.out.println(s);
        }
    }
}

