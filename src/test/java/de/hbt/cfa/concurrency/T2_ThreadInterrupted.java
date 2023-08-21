package de.hbt.cfa.concurrency;

public class T2_ThreadInterrupted {

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            while (true) { // endless loop
                try {
                    Thread.sleep(1000); // busy waiting
                } catch (InterruptedException e) {
                    System.out.println("Thread interrupted.");
                    throw new RuntimeException(e); // don't swallow the interrupt, but exit the thread
                }
                System.out.printf("Hello from thread %s, thread group %s%n", Thread.currentThread().getName(), Thread.currentThread().getThreadGroup());
            }
        });
        t.start();
        Thread.sleep(2000);
        t.interrupt(); // interrupt the thread
        System.out.println("Main thread ending.");
    }
}
