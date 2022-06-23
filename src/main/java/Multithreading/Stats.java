package Multithreading;

public class Stats {
    // volatile - shared memory
    volatile private Long counter = 0L;
    //synchronized - only 1 thread can use this method
    synchronized public void increment() {
        counter++;
    }

    public long counter() {
        return counter;
    }

}
