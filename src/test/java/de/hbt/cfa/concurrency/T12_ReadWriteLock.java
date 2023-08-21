package de.hbt.cfa.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class T12_ReadWriteLock {

    private static final ReadWriteLock LOCK = new ReentrantReadWriteLock();

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        final int[] result = {0};

        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<Future<?>> futures = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            // submit write tasks
            futures.add(executor.submit(() -> {
                LOCK.writeLock().lock();
                try {
                    result[0] = incrementByOneUsingWriteLock(result[0]);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    LOCK.writeLock().unlock();
                }
            }));

            // submit read tasks
            futures.add(executor.submit(() -> {
                LOCK.readLock().lock();
                try {
                    System.out.println("Read result: " + result[0]);
                } finally {
                    LOCK.readLock().unlock();
                }
            }));
        }
        for (Future<?> future : futures) {
            // wait for all tasks to finish
            future.get();
        }

        executor.shutdown(); // do not forget to shut down the executor
    }

    private static int incrementByOneUsingWriteLock(int result) throws InterruptedException {
        int newValue = result + 1;
        Thread.sleep(10);
        System.out.println("Set result to " + newValue);
        return newValue;
    }
}
