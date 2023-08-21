package de.hbt.cfa.concurrency;

public class T9_SynchronizedObject {

    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();
        Thread t1 = new Thread(() -> {
            try {
                synchronized (lock) {
                    Thread.sleep(1000);
                    System.out.println("Hello from thread " + Thread.currentThread().getName());
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                // the second thread will wait until the first thread exits the synchronized method
                synchronized (lock) {
                    Thread.sleep(1000);
                    System.out.println("Hello from thread " + Thread.currentThread().getName());
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        t1.start();
        t2.start();
        long started = System.currentTimeMillis();
        t1.join();
        t2.join();
        // the tasks run sequentially because the synchronized block uses the same lock, so the total time is 2 seconds
        System.out.printf("Time taken by the two threads: %d ms%n", System.currentTimeMillis() - started);
    }

}
