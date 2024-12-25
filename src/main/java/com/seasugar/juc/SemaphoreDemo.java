package com.seasugar.juc;

import java.util.concurrent.Semaphore;

public class SemaphoreDemo {
    // Semaphore为0时不可运行，得等其他线程释放为1
    static Semaphore odd = new Semaphore(1);
    static Semaphore even = new Semaphore(0);

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                for (int i = 1; i <= 100; i += 2) {
                    System.out.println("-----------------");
                    System.out.println(odd.getQueueLength());
                    System.out.println(even.getQueueLength());
                    System.out.println("-----------------");
                    odd.acquire();
                    System.out.println(i);
                    even.release();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        new Thread(() -> {
            for (int i = 2; i <= 100; i += 2) {
                try {
                    even.acquire();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(i);
                odd.release();
            }
        }).start();
    }
}
