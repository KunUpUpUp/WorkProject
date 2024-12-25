package com.seasugar.juc;

import java.util.concurrent.CountDownLatch;

public class ConcurrentDemo {
    static volatile boolean failed = false; // 标志位，指示任务是否失败

    public static void main(String[] args) {
        ThreadTest();
    }


    public static void ThreadTest() {
        int threadCount = 5;
        CountDownLatch[] countDownLatches = new CountDownLatch[threadCount + 1];
        int[] nums = {1, 2, 3, 4, 5};
        countDownLatches[0] = new CountDownLatch(0);  // 第一个线程不需要等待
        for (int i = 1; i < countDownLatches.length; i++) {
            countDownLatches[i] = new CountDownLatch(1);
        }


        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    if (finalI != 0) {
                        countDownLatches[finalI].await(); // 等待前一个线程完成
                    }
                    if (finalI == 0) {
                        // 第一个线程执行主任务
                        try {
                            Thread.sleep(1000);
                            System.out.println("1号执行完了");
                            // 模拟失败
                            int algo = 1 / 0;
                            System.out.println(nums[finalI]);
                        } catch (ArithmeticException | InterruptedException e) {
                            for (int retry = 0; retry < 4; retry++) {
                                try {
                                    System.out.println("重试中...");
                                    if (retry < 3) {
                                        int algo = 1 / 0; // 再次模拟失败
                                    }
                                    System.out.println("重试成功");
                                    System.out.println(nums[finalI]);
                                    return;
                                } catch (ArithmeticException retryException) {
                                    System.out.println("重试失败...");
                                }
                            }
                            failed = true; // 设置全局失败标志位
                        }
                    } else {
                        System.out.println(nums[finalI]);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatches[finalI + 1].countDown(); // 确保通知下一线程
                }
            }).start();
        }

        try {
            countDownLatches[5].await(); // 等待所有线程完成
            if (failed) {
                // 失败
                throw new RuntimeException("程序执行失败");
            } // 如果任务失败，不再执行后续逻辑
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
