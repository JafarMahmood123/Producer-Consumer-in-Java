package com.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class Writer implements Runnable {
    private final BlockingQueue<String> outputQueue;
    private final String outputFile;

    public Writer(BlockingQueue<String> outputQueue, String outputFile) {
        this.outputQueue = outputQueue;
        this.outputFile = outputFile;
    }

    @Override
    public void run() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            int nullCount = 0;
            int numConsumers = 3;
            while (nullCount < numConsumers) {
                String line = outputQueue.take();
                if (line.equals("END")) {
                    nullCount++;
                } else {
                    writer.write(line);
                    writer.newLine();
                    System.out.println("Writer write: " + line);
                }
            }
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
