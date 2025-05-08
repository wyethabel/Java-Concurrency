// Simple program designed to review concurrency with two threads

import java.util.concurrent.*;

public class ConcurrencyTest {

    // Countdown latch utilized to efficiently block second thread activation.
    private static final CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) {
        Thread t1 = new Thread(ConcurrencyTest::to20, "First Thread");
        Thread t2 = new Thread(ConcurrencyTest::from20, "Second Thread");
        t1.start();
        t2.start();
    }

    // Method that counts to 20 run by the first thread t1.
    public static void to20() {
        System.out.println("First Thread: ");
        for (int i = 1; i <= 20; i++) {
            System.out.println(i);
        }
        // releases the countdown latch and activates second thread activity.
        latch.countDown();
    }

    // Method that counts down from 20 run by the second thread t2.
    public static void from20() {
        try {
            // Checks latch value and blocks thread until t1 completion.
            latch.await();
        } catch (InterruptedException e) {
            // Thread failsafe if await method is interrupted
            Thread.currentThread().interrupt();
            System.out.println("Thread operation interrupted");
            return;
        }
        System.out.println("-------------------");
        System.out.println("Second Thread: ");
        for (int i = 20; i >= 0; i--) {
            System.out.println(i);
        }
    }
}
