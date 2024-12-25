package com.seasugar.juc;

import java.util.Arrays;
import java.util.concurrent.Semaphore;

public class SFDemo {
    static Semaphore[] sema = new Semaphore[4];

    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4, 5};
        Arrays.fill(sema, new Semaphore(0));

        for (int i = 0; i < a.length; i++) {
            final int finalI = i;
            new Thread(() -> {
                if (finalI == 0) {
                    System.out.println(a[finalI]);
                    sema[finalI].release();
                } else {
                    try {
                        sema[finalI - 1].acquire();
                        System.out.println(a[finalI]);
                        if (finalI < a.length - 1) {
                            sema[finalI].release();
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        }
    }
}
