package com.romanm.multithreadingromanm.threadSynchronization;

public class WaitAndNotifyDemo {

    private static Object lock = new Object();
    public static void main(String[] args) {
        Thread one = new Thread(() -> {
            try {
                one();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread two = new Thread(() -> {
            try {
                two();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        one.start();
        two.start();
    }

    private static void one() throws InterruptedException {
        synchronized (lock) {
            System.out.println("Hello from method one ...");
            lock.wait();
            System.out.println("Back again in the method one ...");
        }
    }

    private static void two() throws InterruptedException {
        synchronized (lock) {
            System.out.println("Hello from method two");
            lock.notify();
            System.out.println("Hello from method two even after notifying ...");
        }
    }
}
