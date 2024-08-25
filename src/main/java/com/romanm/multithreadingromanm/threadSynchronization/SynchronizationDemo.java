package com.romanm.multithreadingromanm.threadSynchronization;

public class SynchronizationDemo {
    private static int counter = 0;

    /**
     * Race condition in terms of multithreading programming:
     * Increment consists of 3 stages:
     * 1. Load
     * 2. Increment
     * 3. Set back the value
     *
     */
    public static void main(String[] args) {
        Thread one = new Thread(() -> {
            for (int i = 0; i < 10_000; i++) {
                increment();
            }
        });

        Thread two = new Thread(() -> {
            for (int i = 0; i < 10_000; i++) {
                increment();
            }
        });

        one.start();
        two.start();


        try {
            one.join();
            two.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Counter value : " + counter);
    }

    private synchronized static void increment() {
        counter++;
    }
}
