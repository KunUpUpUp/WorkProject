package com.seasugar.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class ConcurrentService {
    static int counter = 0;
    private Lock lock = new ReentrantLock();

    public void add() {
        System.out.print(Thread.currentThread().getId() + ":");
        System.out.println(counter);
//        lock.lock();
        counter++;
        System.out.println(counter);
//        lock.unlock();
    }
}
