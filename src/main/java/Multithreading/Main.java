package Multithreading;

import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

//================================ALL MULTITHREADING=========================================
// 1. Class Thread
// 1. volatile
// 1. synchronized
// 1. wait(), notify(), notifyAll()

public class Main {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello from " + Thread.currentThread().getName());

        //new Thread(() -> System.out.println("Hello from " + Thread.currentThread().getName())).start();
        var t = new Thread(() ->
        {
            System.out.println("Hello from " + Thread.currentThread().getName());
            System.out.println(Thread.currentThread().getName() + " - " + Thread.currentThread().getState());
        });
        System.out.println(t.getName() + " - " + t.getState());
        t.start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println(t.getName() + " - " + t.getState());
        //==========================================================================
        var stats = new Stats();
//        synchronized (stats) {
//            stats.increment();
//        }
        Runnable incrementLogic = () -> {
            for (int i = 0; i < 10_000; i++) {
                stats.increment();
            }
        };

        var t1 = new Thread(incrementLogic);
        var t2 = new Thread(incrementLogic);

        t1.start();
        t2.start();

        t1.join();
        t2.join();
        System.out.println(stats.counter());

        //======================DEAD LOCK==============================
        var s1 = new Stats();
        var s2 = new Stats();
        var thread1 = new Thread(() -> {
            synchronized (s1) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                s2.increment();
            }
        });

        var thread2 = new Thread(() -> {
            synchronized (s2) {
                s1.increment();
            }
        });

        var statePrinter = new Thread(() -> {
            while (true) {
                System.out.println(thread1.getState());
                System.out.println(thread2.getState());
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        statePrinter.start();

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        //==========================WORK WITH EXECUTORS================================
        var executor = Executors.newFixedThreadPool(2);
        executor.execute(() -> System.out.println("Hello"));
        executor.shutdownNow();

        //==========================FORK JOIN FRAMEWORK=================================
        // common pool = ядра - 1 Runtime.getRuntime().availableProcessor() - 1;
        int[] arr = {4,3,2,6,7,8,7,66,32};
        System.out.println(Arrays.toString(arr));
//      ForkJoinPool.commonPool().invoke(new MergeSortTask(arr));
        var myPool = new ForkJoinPool(4);
        myPool.invoke(new MergeSortTask(arr));
        System.out.println(Arrays.toString(arr));
    }

}
