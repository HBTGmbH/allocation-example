package de.hbt.cfa.concurrency;

import java.util.concurrent.locks.ReentrantLock;

public class T7_ReentrantLock {

    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Thread t1 = new Thread(() -> {
            lock.lock();
            try {
                Thread.sleep(1000);
                System.out.println("Hello from thread " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        });
        Thread t2 = new Thread(() -> {
            lock.lock(); // the second thread will wait until the first thread releases the lock
            try {
                Thread.sleep(1000);
                System.out.println("Hello from thread " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        });

        t1.start();
        t2.start();
        long started = System.currentTimeMillis();
        t1.join();
        t2.join();
        // the tasks run sequentially because of the lock, so the total time is 2 seconds
        System.out.printf("Time taken by the two threads: %d ms%n", System.currentTimeMillis() - started);
    }
}
