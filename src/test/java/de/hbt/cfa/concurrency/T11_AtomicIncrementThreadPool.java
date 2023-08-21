package de.hbt.cfa.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class T11_AtomicIncrementThreadPool {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        AtomicInteger result = new AtomicInteger(0);
        long started = System.currentTimeMillis();

        ExecutorService executor = Executors.newCachedThreadPool();
        List<Future<?>> futures = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            futures.add(executor.submit(() -> incrementByOneAtomically(result)));
        }
        for (Future<?> future : futures) {
            // wait for all tasks to finish
            future.get();
        }

        // the tasks run sequentially because AtomicInteger is thread-safe, so the total time is 1 second (10 ms * 100)
        // set a breakpoint here and inspect how many threads are running
        System.out.printf("Time taken in total: %d ms%n", System.currentTimeMillis() - started);
    }

    // this method is not synchronized, but the AtomicInteger is thread-safe
    private static void incrementByOneAtomically(AtomicInteger result) {
        int newValue = result.updateAndGet(i -> {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return i + 1;
        });
        System.out.println("Set result to " + newValue);
    }

}
