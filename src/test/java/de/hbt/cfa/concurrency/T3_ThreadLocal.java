package de.hbt.cfa.concurrency;

public class T3_ThreadLocal {

    private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            // this is a thread local variable, i.e. each thread has its own copy of the variable
            THREAD_LOCAL.set("Hello from thread " + Thread.currentThread().getName());
            System.out.println(THREAD_LOCAL.get());
        });
        t.start();
        t.join(); // make sure that the thread is finished before we access the thread local variable
        System.out.println(THREAD_LOCAL.get());
    }
}
