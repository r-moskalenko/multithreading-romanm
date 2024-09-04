package com.romanm.multithreadingromanm.locks;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {
    private final ReentrantLock lock = new ReentrantLock();
    private int sharedData = 0;

    public void methodA() {
        lock.lock();
        try {
            // critical section
            sharedData++;
            System.out.println("MethodA sharedData: " + sharedData);
            // Call methodB() which also requires a lock
            methodB();
        } finally {
            lock.unlock();
        }
    }

    private void methodB() {
        lock.lock();
        try {
            // critical section
            sharedData--;
            System.out.println("MethodB sharedData: " + sharedData);
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ReentrantLockDemo demo = new ReentrantLockDemo();
        for (int i = 0; i < 5; i++) {
            new Thread(demo::methodA).start();
        }
    }
}
