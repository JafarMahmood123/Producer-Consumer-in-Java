package com.example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    public static void main(String[] args) {
        BlockingQueue<String> inputQueue = new LinkedBlockingQueue<>();
        BlockingQueue<String> outputQueue = new LinkedBlockingQueue<>();

        String inputFile = "input.txt";
        String outputFile = "output.txt";

        int numConsumers = 3;

        // Start producer
        Thread producerThread = new Thread(new Producer(inputQueue, inputFile, numConsumers));
        producerThread.start();
        System.out.println("Producer Thred Started.");

        // Start consumers
        Thread[] consumerThreads = new Thread[numConsumers];
        for (int i = 0; i < numConsumers; i++) {
            consumerThreads[i] = new Thread(new Consumer(inputQueue, outputQueue));
            consumerThreads[i].start();
            System.out.println("Consumer Thred Started.");
        }

        // Start writer
        Thread writerThread = new Thread(new Writer(outputQueue, outputFile));
        writerThread.start();
        System.out.println("Writer Thred Started.");

        // Wait for all threads to finish
        try {
            producerThread.join();
            for (Thread t : consumerThreads) {
                t.join();
            }
            writerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Processing complete. Check output.txt");
    }
}
