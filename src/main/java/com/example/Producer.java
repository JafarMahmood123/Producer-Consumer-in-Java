package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {
    private final BlockingQueue<String> inputQueue;
    private final String inputFile;
    private final int numConsumers;

    public Producer(BlockingQueue<String> inputQueue, String inputFile, int numConsumers) {
        this.inputQueue = inputQueue;
        this.inputFile = inputFile;
        this.numConsumers = numConsumers;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                inputQueue.put(line);
                System.out.println("Producer Produced line: " + line);
            }
            // Signal end of input
            for (int i = 0; i < numConsumers; i++) {
                inputQueue.put("END");
            }
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
