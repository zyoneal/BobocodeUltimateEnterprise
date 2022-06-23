package Multithreading;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortTask extends RecursiveAction {

    private int[] arr;

    private int n;

    public MergeSortTask(int[] arr) {
        this.arr = arr;
        this.n = arr.length;
    }

    @Override
    protected void compute() {
        if (arr.length == 1) {
            return;
        }
        var left = Arrays.copyOfRange(arr, 0, n / 2);
        var right = Arrays.copyOfRange(arr, n / 2, n);
        var leftTask = new MergeSortTask(left);
        var rightTask = new MergeSortTask(right);
        leftTask.fork();
        rightTask.compute();
        leftTask.join();
        merge(arr, left, right);
    }

    private void merge(int[] arr, int[] left, int[] right) {
        var i = 0;
        var j = 0;
        while (i < left.length && j < right.length) {
            if (left[i] < right[j]) {
                arr[i + j] = left[i++];
            } else {
                arr[i + j] = right[j++];
            }
        }
        System.arraycopy(left, i, arr, i + j, left.length - i);
        System.arraycopy(right, j, arr, i + j, right.length - j);
    }

}
