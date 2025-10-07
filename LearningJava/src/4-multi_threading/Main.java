package multi_threading;

public class Main {
    public static void main(String[] args) {

        //Threads can be created by extending Thread class or by implementing Runnable interface

        // By Thread class
        Threads t1 = new Threads(123);
        t1.start();

        // By Runnable interface
        Runnables r1 = new Runnables();
        Thread t2 = new Thread(r1);
        t2.start();
        // we need threads to execute the runnables


        // Using anonymous class
        Runnable r2 = new Runnable() {
            @Override
            public void run() {
            System.out.println("Runnable using anonymous class");
            }
        };
        Thread t3 = new Thread(r2);
        t3.start();

        // Using lambda expression (Java 8+)
        Runnable r3 = () -> System.out.println("Runnable using lambda expression");
        Thread t4 = new Thread(r3);
        t4.start();

        // Difference between Thread class and Runnable interface
        // 1. Java supports only single inheritance, so if we extend Thread class, we can't extend any other class
        //    But if we implement Runnable interface, we can extend another class as well
        // 2. When we extend Thread class, we can't share the same object between multiple threads
        //    But when we implement Runnable interface, we can share the same object between multiple threads
        // 3. Runnable interface is more efficient than Thread class, as it doesn't require the overhead of creating a new thread object
        // 4. Runnable interface is preferred when we want to separate the task from the thread that executes it
        // 5. Runnable interface is preferred when we want to implement multiple inheritance

        // Threads in Java can be in one of the following states:
        // 1. New: when a thread is created but not yet started
        // 2. Runnable: when a thread is ready to run and waiting for CPU time
        // 3. Running: when a thread is executing
        // 4. Blocked: when a thread is waiting for a resource that is not available
        // 5. Waiting: when a thread is waiting indefinitely for another thread to perform a particular action
        // 6. Timed Waiting: when a thread is waiting for another thread to perform a particular action for a specified amount of time
        // 7. Terminated: when a thread has completed its execution

        // Multi-threading is a process of executing multiple threads simultaneously
        // Multi-threading is used to achieve parallelism and improve the performance of a program
        // Multi-threading is also used to achieve concurrency, where multiple threads can access shared resources
        // Synchronization is used to control the access of multiple threads to shared resources
        // Synchronization is achieved using synchronized keyword, which can be used to synchronize a method or a block of code

    
        // Example of synchronization using synchronized method
        class Counter {
            private int count = 0;

            // Synchronized method to ensure only one thread can access at a time
            // if we not use synchronized keyword, the final count value will be less than 2000
            // because multiple threads will access the increment method simultaneously
            public synchronized void increment() {
            count++;
            }

            public int getCount() {
            return count;
            }
        }

        Counter counter = new Counter();

        // Create multiple threads to increment the counter
        Thread t5 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
            counter.increment();
            }
        });

        Thread t6 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
            counter.increment();
            }
        });

        t5.start();
        t6.start();

        try {
            t5.join();
            t6.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final counter value (should be 2000): " + counter.getCount());

        // Notes:
        // Synchronization in Java is used to prevent thread interference and memory consistency errors.
        // The 'synchronized' keyword ensures that only one thread can execute a method or block at a time on the same object.
        // This is important when multiple threads modify shared resources.
        // You can synchronize an entire method or just a block of code.
        // Use synchronization carefully, as it can lead to reduced performance and deadlocks if not managed properly.

    }
}
