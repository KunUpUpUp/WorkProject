import org.junit.Test;
import scala.Int;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class JUCTest {

    Semaphore[] sema = new Semaphore[4];
    volatile boolean failed = false; // 标志位，指示任务是否失败

    @Test
    public void testSemaphore() {
        int[] a = {1, 2, 3, 4, 5};
        Arrays.fill(sema, new Semaphore(0));

        for (int i = 0; i < a.length; i++) {
            int index = i;  // 将循环变量复制到局部变量中
            new Thread(() -> {
                try {
                    if (index == 0) {
                        System.out.println(a[index]);
                        sema[index].release();
                    } else {
                        sema[index - 1].acquire();
                        System.out.println(a[index]);
                        if (index < a.length - 1) {
                            sema[index].release();
                        }
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }

    @Test
    public void testSemaphore2() {
        int[] a = {1, 2, 3, 4, 5};
        Arrays.fill(sema, new Semaphore(0));

        for (int i = 0; i < a.length; i++) {
            final int finalI = i;
            new Thread(() -> {
                try {
                    if (finalI == 0) {
                        System.out.println(a[finalI]);
                        sema[finalI].release();
                    } else {
                        sema[finalI - 1].acquire();
                        System.out.println(a[finalI]);
                        if (finalI < a.length - 1) {
                            sema[finalI].release();
                        }
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }

    @Test
    public void testSemaphore3() {
        int[] a = {1, 2, 3, 4, 5};

        // 初始化每个信号量
        for (int i = 0; i < sema.length; i++) {
            sema[i] = new Semaphore(0);  // 初始为 0，表示线程无法获取
        }

        // 启动线程
        for (int i = 0; i < a.length; i++) {
            int index = i;  // 捕获当前循环索引值，避免闭包问题
            new Thread(() -> {
                try {
                    // 第一个线程不需要等待
                    if (index > 0) {
                        sema[index - 1].acquire();  // 等待前一个线程释放信号量
                    }

                    // 打印对应的数字
                    System.out.println(a[index]);

                    // 如果不是最后一个线程，释放下一个信号量
                    if (index < a.length - 1) {
                        sema[index].release();  // 释放下一个线程的信号量
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        // 启动第一个线程
        sema[0].release();  // 释放第一个信号量，允许第一个线程执行
    }

    @Test
    public void testCountDownLatch() {
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
                    if (finalI == 0) {
                        // 第一个线程执行主任务
                        try {
                            // 模拟执行任务
                            Thread.sleep(1000);

//                            System.out.println("1号执行完了");
                            // 模拟失败
//                            int algo = 1 / 0;
                            System.out.println(nums[finalI]);
                            countDownLatches[finalI + 1].countDown();
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
                        // 模拟执行任务
                        Thread.sleep(1000);

                        countDownLatches[finalI].await(); // 等待前一个线程完成
                        // 顺序执行
                        System.out.println(nums[finalI]);
                        countDownLatches[finalI + 1].countDown();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
//                    countDownLatches[finalI + 1].countDown(); // 确保通知下一线程
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


    @Test
    public void testFuture() throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask<>(() -> 1);
        FutureTask<Integer> futureTask1 = new FutureTask<>(() -> {
            Random r = new Random();
            int s = r.nextInt(2);
            int i = 1 / s;
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

        futureTask.run();
        futureTask1.run();
        futureTask2.run();
        futureTask3.run();
        futureTask4.run();

        Integer i = futureTask.get();
        Integer i1 = null;
        try {
            i1 = futureTask1.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            for (int j = 0; j < 3; j++) {
                try {
                    Random r = new Random();
                    int s = r.nextInt(2);
                    i1 = 2 / s;
                    break;
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

    @Test
    public void testFuture2() throws ExecutionException, InterruptedException {
        Lock lock = new ReentrantLock();
    }

}
