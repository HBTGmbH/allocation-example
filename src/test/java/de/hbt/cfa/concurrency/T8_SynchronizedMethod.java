package de.hbt.cfa.concurrency;

public class T8_SynchronizedMethod {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                waitAndLogSynchronized();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                waitAndLogSynchronized(); // the second thread will wait until the first thread exits the synchronized method
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        t1.start();
        t2.start();
        long started = System.currentTimeMillis();
        t1.join();
        t2.join();
        // the tasks run sequentially because the method is synchronized, so the total time is 2 seconds
        System.out.printf("Time taken by the two threads: %d ms%n", System.currentTimeMillis() - started);
    }

    private static synchronized void waitAndLogSynchronized() throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("Hello from thread " + Thread.currentThread().getName());
    }
}
