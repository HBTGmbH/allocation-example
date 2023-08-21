package de.hbt.cfa.concurrency;

import java.util.concurrent.*;

public class T4_SingleThreadExecutor {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<String> callable = () -> {
            Thread.sleep(1000);
            return "Hello from thread " + Thread.currentThread().getName();
        };

        // the single thread executor has one thread
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future1 = executor.submit(callable);
        Future<String> future2 = executor.submit(callable);

        long started = System.currentTimeMillis();
        System.out.println(future1.get());
        System.out.println(future2.get());
        // the tasks run sequentially, so the total time is 2 seconds
        System.out.printf("Time taken by the two threads: %d ms%n", System.currentTimeMillis() - started);

        executor.shutdown(); // do not forget to shut down the executor
    }
}
