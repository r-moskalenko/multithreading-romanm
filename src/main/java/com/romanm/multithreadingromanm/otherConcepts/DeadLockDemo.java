package com.romanm.multithreadingromanm.otherConcepts;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLockDemo {
    private final Lock lockA = new ReentrantLock(true);
    private final Lock lockB = new ReentrantLock(true);

    private void workerOne() {
        lockA.lock();
        System.out.println("Worker One acquired lock A");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        lockB.lock();
        System.out.println("Worker One acquired lock B");
        lockA.unlock();
        lockB.unlock();
    }

    private void workerTwo() {
        lockB.lock();
        System.out.println("Worker Two acquired lock B");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        lockA.lock();
        System.out.println("Worker Two acquired lock A");
        lockB.unlock();
        lockA.unlock();
    }

    public static void main(String[] args){
        DeadLockDemo demo = new DeadLockDemo();
        new Thread(demo::workerOne, "Worker One").start();
        new Thread(demo::workerTwo, "Worker Two").start();

        new Thread(() -> {
            ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();
            while (true) {
                long[] deadlockedThreads = mxBean.findDeadlockedThreads();
                if (deadlockedThreads != null) {
                    System.out.println("Deadlock detected");
                    for (long threadId : deadlockedThreads) {
                        System.out.println("Thread with id " + threadId + " is in deadlock");
                    }
                    break;
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}
