package com.example;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {
    private final BlockingQueue<String> inputQueue;
    private final BlockingQueue<String> outputQueue;

    public Consumer(BlockingQueue<String> inputQueue, BlockingQueue<String> outputQueue) {
        this.inputQueue = inputQueue;
        this.outputQueue = outputQueue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String line = inputQueue.take();
                if (line.equals("END")) {
                    // End signal
                    outputQueue.put("END");
                    break;
                }
                String processed = processLine(line);
                outputQueue.put(processed);
                System.out.println("Consumer consumed: " + processed);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private String processLine(String line) {
        String[] words = line.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            sb.append(new StringBuilder(word).reverse().toString()).append(" ");
        }
        return sb.toString().trim();
    }
}
