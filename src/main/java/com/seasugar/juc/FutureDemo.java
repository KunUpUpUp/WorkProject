package com.seasugar.juc;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask<>(() -> {
            return 1;
        });
        FutureTask<Integer> futureTask1 = new FutureTask<>(() -> {
            int i = 1 / 0;
            return 2;
        });
        FutureTask<Integer> futureTask2 = new FutureTask<>(() -> {
            return 3;
        });
        FutureTask<Integer> futureTask3 = new FutureTask<>(() -> {
            return 4;
        });
        FutureTask<Integer> futureTask4 = new FutureTask<>(() -> {
            return 5;
        });

        Integer i = futureTask.get();
        Integer i1 = null;
        try {
            i1 = futureTask1.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            for (int j = 0; j < 3; j++) {
                try {
                    i1 = futureTask1.get();
                    int k = 1 / 0;
                    return;
                } catch (Exception e1) {
                    System.out.println("重试");
                }
            }
        }
        Integer i2 = futureTask2.get();
        Integer i3 = futureTask3.get();
        Integer i4 = futureTask4.get();

        System.out.println(i);
        System.out.println(i1);
        System.out.println(i2);
        System.out.println(i3);
        System.out.println(i4);
    }
}
