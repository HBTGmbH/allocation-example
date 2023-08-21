package de.hbt.cfa.concurrency;

public class T1_DaemonThread {

    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            while (true) { // endless loop
                try {
                    Thread.sleep(1000); // busy waiting
                } catch (InterruptedException e) {
                    throw new RuntimeException(e); // does not matter if we swallow the interrupt, because the thread is a daemon thread
                }
                System.out.printf("Hello from thread %s, thread group %s%n", Thread.currentThread().getName(), Thread.currentThread().getThreadGroup());
            }
        });
        t.setDaemon(true); // a daemon thread exits when the main thread exits
        //t.setDaemon(false); // a non-daemon thread does not exit when the main thread exits
        t.start();
        System.out.println("Main thread ending.");
    }
}
