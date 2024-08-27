package com.romanm.multithreadingromanm.threadSynchronization;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProducerConsumer {
    public static void main(String[] args) {
        Worker worker = new Worker(5, 0);

        Thread producer = new Thread(() -> {
            try {
                worker.produce();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                worker.consume();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        producer.start();
        consumer.start();
    }
}

class Worker {

    private int sequence = 0;
    private final Integer top;
    private final Integer bottom;
    private final List<Integer> container;
    private final Object lock = new Object();

    public Worker(Integer top, Integer bottom) {
        this.top = top;
        this.bottom = bottom;
        container = new ArrayList<>();
    }

    public void produce() throws InterruptedException {
        Random random = new Random();
        synchronized (lock) {
            while (true) {
                if (container.size() == top) {
                    System.out.println("Container full, waiting for items to be removed ...");
                    lock.wait();
                } else {
                    sequence = random.nextInt(100);
                    System.out.println(sequence + " Added to the container");
                    container.add(sequence);
                    lock.notify();
                }
                Thread.sleep(500);
            }
        }
    }

    public void consume() throws InterruptedException {
        synchronized (lock) {
            while (true) {
                if (container.size() == bottom) {
                    System.out.println("Container is empty, waiting for items to be added ...");
                    lock.wait();
                } else {
                    System.out.println(container.remove(0) + " removed from container");
                    lock.notify();
                }
                Thread.sleep(500);
            }
        }
    }
}
