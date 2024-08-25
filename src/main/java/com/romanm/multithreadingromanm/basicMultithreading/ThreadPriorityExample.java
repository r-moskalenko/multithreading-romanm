package com.romanm.multithreadingromanm.basicMultithreading;

public class ThreadPriorityExample {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + " says Hi!");

        Thread one = new Thread(() -> {
            System.out.println("Thread one says Hi as well!");
        });

        one.setPriority(Thread.MAX_PRIORITY);
        one.start();
    }
}
