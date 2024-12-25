package com.seasugar.juc;

public class ThreadDemo {
    public static void main(String[] args) {
        new Thread(() -> {
            try {
                Thread.sleep(100000000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}