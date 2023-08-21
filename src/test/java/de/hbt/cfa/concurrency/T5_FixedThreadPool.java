package de.hbt.cfa.concurrency;

import java.util.concurrent.*;

public class T5_FixedThreadPool {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<String> callable = () -> {
            Thread.sleep(1000);
            return "Hello from thread " + Thread.currentThread().getName();
        };

        // the fixed thread pool has two threads
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<String> future1 = executor.submit(callable);
        Future<String> future2 = executor.submit(callable);

        long started = System.currentTimeMillis();
        System.out.println(future1.get());
        System.out.println(future2.get());
        // the tasks run in parallel, so the total time is 1 second
        System.out.printf("Time taken by the two threads: %d ms%n", System.currentTimeMillis() - started);

        executor.shutdown(); // do not forget to shut down the executor
    }
}
