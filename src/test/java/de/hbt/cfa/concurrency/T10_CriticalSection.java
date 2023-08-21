package de.hbt.cfa.concurrency;

public class T10_CriticalSection {

    public static void main(String[] args) throws InterruptedException {
        final int[] result = {0};
        Thread t1 = new Thread(() -> {
            try {
                synchronized (result) {
                    result[0] = incrementByOneCriticalSection(result[0]);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                synchronized (result) {
                    result[0] = incrementByOneCriticalSection(result[0]); // the second thread will wait until the first thread exits the synchronized block
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
        // the tasks run sequentially because the method is synchronized, so the total time is 2 seconds
        System.out.printf("Time taken by the two threads: %d ms%n", System.currentTimeMillis() - started);
    }

    private static int incrementByOneCriticalSection(int result) throws InterruptedException {
        int newValue = result + 1;
        Thread.sleep(1000);
        System.out.println("Set result to " + newValue);
        return newValue;
    }

}
