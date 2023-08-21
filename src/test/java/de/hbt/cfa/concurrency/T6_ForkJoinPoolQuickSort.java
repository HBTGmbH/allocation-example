package de.hbt.cfa.concurrency;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class T6_ForkJoinPoolQuickSort {

    public static void main(String[] args) {
        // generate 100.000.000 random numbers
        int[] arr = IntStream
                .range(0, 100_000_000)
                .map(i -> ThreadLocalRandom.current().nextInt())
                .toArray();

        // the fork join pool has as many threads as there are processors (by default)
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        // start the fork join task, it will recursively split the task into smaller tasks
        long started = System.currentTimeMillis();
        forkJoinPool.invoke(new QuickSortAction(arr));
        System.out.printf("Time taken using parallel quick sort to sort 100.000.000 numbers: %d ms%n", System.currentTimeMillis() - started);

        forkJoinPool.shutdown(); // do not forget to shut down the fork join pool
    }

    /**
     * Quick Sort based on Hoare partition scheme (<a href="https://en.wikipedia.org/wiki/Quicksort#Hoare_partition_scheme">...</a>)
     * copy-pasted from <a href="https://gist.github.com/EliaRohana/25b924048d990c5358313d18daf8f491">...</a>
     */
    public static class QuickSortAction extends RecursiveAction {
        private int[] data;
        private int left;
        private int right;

        public QuickSortAction(int[] data) {
            this.data = data;
            left = 0;
            right = data.length - 1;
        }

        public QuickSortAction(int[] data, int left, int right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }

        @Override
        protected void compute() {
            if (left < right) {
                int pivot = partition(data, left, right);
                // recursively sort the two partitions
                invokeAll(new QuickSortAction(data, left, pivot),
                        new QuickSortAction(data, pivot + 1, right));
            }
        }

        private int partition(int[] array, int low, int high) {
            int pivot = array[low];
            int i = low - 1;
            int j = high + 1;
            while (true) {
                do {
                    i++;
                }
                while (array[i] < pivot); // find element greater than pivot

                do {
                    j--;
                }
                while (array[j] > pivot); // find element smaller than pivot

                if (i >= j)
                    // the partition is sorted, return the index of the pivot
                    return j;

                // swap the two elements
                swap(array, i, j);
            }
        }

        private void swap(int[] array, int i, int j) {
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }
}
