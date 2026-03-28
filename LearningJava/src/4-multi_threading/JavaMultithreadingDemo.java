import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

/**
 * Java Multithreading Demo — Engineering Digest Playlist
 * Covers: Thread creation, lifecycle, synchronization, inter-thread communication,
 *         locks, executor framework, volatile, atomic, and common patterns.
 */
public class JavaMultithreadingDemo {

    // ─────────────────────────────────────────────────────────────────────────
    // 1. CREATING THREADS
    // ─────────────────────────────────────────────────────────────────────────

    // Way 1: Extend Thread
    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("[Thread] Running in: " + Thread.currentThread().getName());
        }
    }

    // Way 2: Implement Runnable (preferred — allows extending another class)
    static class MyTask implements Runnable {
        @Override
        public void run() {
            System.out.println("[Runnable] Running in: " + Thread.currentThread().getName());
        }
    }

    static void demonstrateThreadCreation() throws InterruptedException {
        System.out.println("\n=== 1. Thread Creation ===");

        // Extend Thread
        MyThread t1 = new MyThread();
        t1.start(); // always start(), not run()

        // Implement Runnable
        Thread t2 = new Thread(new MyTask());
        t2.start();

        // Lambda (Runnable shorthand)
        Thread t3 = new Thread(() -> System.out.println("[Lambda] Running in: " + Thread.currentThread().getName()));
        t3.start();

        // Callable — returns a value
        ExecutorService exec = Executors.newSingleThreadExecutor();
        Future<Integer> future = exec.submit(() -> {
            Thread.sleep(100);
            return 42;
        });
        System.out.println("[Callable] Result: " + future.get());
        exec.shutdown();

        t1.join(); t2.join(); t3.join();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 2. THREAD LIFECYCLE — start, sleep, join, interrupt
    // ─────────────────────────────────────────────────────────────────────────

    static void demonstrateLifecycle() throws InterruptedException {
        System.out.println("\n=== 2. Thread Lifecycle ===");

        Thread t = new Thread(() -> {
            try {
                System.out.println("[Lifecycle] State inside thread: RUNNING");
                Thread.sleep(200);
            } catch (InterruptedException e) {
                System.out.println("[Lifecycle] Thread interrupted!");
            }
        });

        System.out.println("Before start: " + t.getState()); // NEW
        t.start();
        System.out.println("After start:  " + t.getState()); // RUNNABLE or TIMED_WAITING
        t.join();
        System.out.println("After join:   " + t.getState()); // TERMINATED

        // start() vs run() — key interview question
        Thread demo = new Thread(() -> System.out.println("[start vs run] Thread name: " + Thread.currentThread().getName()));
        demo.run();   // prints "main"  — no new thread
        demo.start(); // prints "Thread-X" — new thread
        demo.join();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 3. SYNCHRONIZATION — race condition fix
    // ─────────────────────────────────────────────────────────────────────────

    static class UnsafeCounter {
        int count = 0;
        void increment() { count++; } // NOT atomic
    }

    static class SafeCounter {
        int count = 0;
        synchronized void increment() { count++; }
    }

    static class BlockSyncCounter {
        int count = 0;
        private final Object lock = new Object();
        void increment() {
            synchronized (lock) { count++; } // finer granularity
        }
    }

    static void demonstrateSynchronization() throws InterruptedException {
        System.out.println("\n=== 3. Synchronization ===");

        UnsafeCounter unsafe = new UnsafeCounter();
        SafeCounter safe = new SafeCounter();

        Runnable unsafeTask = () -> { for (int i = 0; i < 1000; i++) unsafe.increment(); };
        Runnable safeTask   = () -> { for (int i = 0; i < 1000; i++) safe.increment(); };

        Thread u1 = new Thread(unsafeTask), u2 = new Thread(unsafeTask);
        Thread s1 = new Thread(safeTask),   s2 = new Thread(safeTask);

        u1.start(); u2.start(); u1.join(); u2.join();
        s1.start(); s2.start(); s1.join(); s2.join();

        System.out.println("[Unsafe] Expected 2000, got: " + unsafe.count); // likely < 2000
        System.out.println("[Safe]   Expected 2000, got: " + safe.count);   // always 2000
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 4. INTER-THREAD COMMUNICATION — wait / notify (Producer-Consumer)
    // ─────────────────────────────────────────────────────────────────────────

    static class SharedBuffer {
        private int data;
        private boolean hasData = false;

        synchronized void produce(int value) throws InterruptedException {
            while (hasData) wait();       // always while, not if (spurious wakeups)
            data = value;
            hasData = true;
            System.out.println("[Producer] Produced: " + value);
            notify();
        }

        synchronized int consume() throws InterruptedException {
            while (!hasData) wait();
            hasData = false;
            System.out.println("[Consumer] Consumed: " + data);
            notify();
            return data;
        }
    }

    static void demonstrateProducerConsumer() throws InterruptedException {
        System.out.println("\n=== 4. Producer-Consumer (wait/notify) ===");
        SharedBuffer buffer = new SharedBuffer();

        Thread producer = new Thread(() -> {
            try { for (int i = 1; i <= 3; i++) buffer.produce(i); }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });

        Thread consumer = new Thread(() -> {
            try { for (int i = 1; i <= 3; i++) buffer.consume(); }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });

        producer.start(); consumer.start();
        producer.join();  consumer.join();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 5. REENTRANTLOCK & READWRITELOCK
    // ─────────────────────────────────────────────────────────────────────────

    static class LockCounter {
        int count = 0;
        private final ReentrantLock lock = new ReentrantLock();

        void increment() {
            lock.lock();
            try { count++; }
            finally { lock.unlock(); } // always unlock in finally
        }

        boolean tryIncrement() throws InterruptedException {
            if (lock.tryLock(100, TimeUnit.MILLISECONDS)) {
                try { count++; return true; }
                finally { lock.unlock(); }
            }
            return false;
        }
    }

    static void demonstrateLocks() throws InterruptedException {
        System.out.println("\n=== 5. ReentrantLock ===");
        LockCounter lc = new LockCounter();
        Thread t1 = new Thread(() -> { for (int i = 0; i < 500; i++) lc.increment(); });
        Thread t2 = new Thread(() -> { for (int i = 0; i < 500; i++) lc.increment(); });
        t1.start(); t2.start(); t1.join(); t2.join();
        System.out.println("[ReentrantLock] Count: " + lc.count); // always 1000

        // ReadWriteLock — multiple readers, one writer
        ReadWriteLock rwLock = new ReentrantReadWriteLock();
        int[] sharedData = {0};

        Runnable reader = () -> {
            rwLock.readLock().lock();
            try { System.out.println("[ReadLock] Read: " + sharedData[0] + " by " + Thread.currentThread().getName()); }
            finally { rwLock.readLock().unlock(); }
        };

        Runnable writer = () -> {
            rwLock.writeLock().lock();
            try { sharedData[0]++; System.out.println("[WriteLock] Wrote: " + sharedData[0]); }
            finally { rwLock.writeLock().unlock(); }
        };

        Thread w = new Thread(writer);
        Thread r1 = new Thread(reader), r2 = new Thread(reader);
        w.start(); w.join();
        r1.start(); r2.start(); r1.join(); r2.join();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 6. CONCURRENCY UTILITIES — CountDownLatch, CyclicBarrier, Semaphore
    // ─────────────────────────────────────────────────────────────────────────

    static void demonstrateConcurrencyUtils() throws InterruptedException, BrokenBarrierException {
        System.out.println("\n=== 6. Concurrency Utilities ===");

        // CountDownLatch — wait for N tasks to finish
        CountDownLatch latch = new CountDownLatch(3);
        for (int i = 1; i <= 3; i++) {
            final int id = i;
            new Thread(() -> {
                System.out.println("[Latch] Task " + id + " done");
                latch.countDown();
            }).start();
        }
        latch.await();
        System.out.println("[Latch] All tasks completed");

        // CyclicBarrier — all threads sync at a checkpoint
        CyclicBarrier barrier = new CyclicBarrier(3, () -> System.out.println("[Barrier] All threads reached checkpoint"));
        for (int i = 1; i <= 3; i++) {
            final int id = i;
            new Thread(() -> {
                try {
                    System.out.println("[Barrier] Thread " + id + " waiting");
                    barrier.await();
                    System.out.println("[Barrier] Thread " + id + " proceeding");
                } catch (Exception e) { Thread.currentThread().interrupt(); }
            }).start();
        }
        Thread.sleep(300);

        // Semaphore — limit concurrent access (e.g., max 2 threads at a time)
        Semaphore semaphore = new Semaphore(2);
        for (int i = 1; i <= 4; i++) {
            final int id = i;
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println("[Semaphore] Thread " + id + " acquired permit");
                    Thread.sleep(100);
                    System.out.println("[Semaphore] Thread " + id + " releasing permit");
                    semaphore.release();
                } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            }).start();
        }
        Thread.sleep(600);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 7. EXECUTOR FRAMEWORK & COMPLETABLEFUTURE
    // ─────────────────────────────────────────────────────────────────────────

    static void demonstrateExecutors() throws Exception {
        System.out.println("\n=== 7. Executor Framework ===");

        // Fixed thread pool
        ExecutorService pool = Executors.newFixedThreadPool(3);
        for (int i = 1; i <= 5; i++) {
            final int id = i;
            pool.submit(() -> System.out.println("[Pool] Task " + id + " on " + Thread.currentThread().getName()));
        }
        pool.shutdown();
        pool.awaitTermination(2, TimeUnit.SECONDS);

        // CompletableFuture — async pipeline
        System.out.println("\n--- CompletableFuture ---");
        CompletableFuture<Integer> cf = CompletableFuture
            .supplyAsync(() -> { System.out.println("[CF] Fetching data..."); return 10; })
            .thenApply(data -> { System.out.println("[CF] Processing: " + data); return data * 2; })
            .thenApply(result -> result + 5);

        cf.thenAccept(r -> System.out.println("[CF] Final result: " + r));

        // Combine two futures
        CompletableFuture<Integer> a = CompletableFuture.supplyAsync(() -> 10);
        CompletableFuture<Integer> b = CompletableFuture.supplyAsync(() -> 20);
        int sum = a.thenCombine(b, Integer::sum).get();
        System.out.println("[CF] Combined result: " + sum);

        // allOf — wait for all
        CompletableFuture.allOf(
            CompletableFuture.runAsync(() -> System.out.println("[CF] Task A")),
            CompletableFuture.runAsync(() -> System.out.println("[CF] Task B"))
        ).thenRun(() -> System.out.println("[CF] All done")).get();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 8. VOLATILE & ATOMIC
    // ─────────────────────────────────────────────────────────────────────────

    // volatile — visibility guarantee (not atomicity)
    static volatile boolean running = true;

    static void demonstrateVolatileAndAtomic() throws InterruptedException {
        System.out.println("\n=== 8. Volatile & Atomic ===");

        // volatile flag — safe for single-writer, multiple-reader
        Thread worker = new Thread(() -> {
            int count = 0;
            while (running) count++;
            System.out.println("[Volatile] Worker stopped after " + count + " iterations");
        });
        worker.start();
        Thread.sleep(10);
        running = false; // visible to worker thread immediately
        worker.join();

        // AtomicInteger — atomic operations without synchronized
        AtomicInteger atomicCount = new AtomicInteger(0);
        Thread at1 = new Thread(() -> { for (int i = 0; i < 1000; i++) atomicCount.incrementAndGet(); });
        Thread at2 = new Thread(() -> { for (int i = 0; i < 1000; i++) atomicCount.incrementAndGet(); });
        at1.start(); at2.start(); at1.join(); at2.join();
        System.out.println("[Atomic] Expected 2000, got: " + atomicCount.get());

        // CAS — Compare And Set
        atomicCount.set(5);
        boolean swapped = atomicCount.compareAndSet(5, 100); // if value==5, set to 100
        System.out.println("[CAS] Swapped: " + swapped + ", Value: " + atomicCount.get());

        // LongAdder — better under high contention
        LongAdder adder = new LongAdder();
        adder.increment();
        adder.add(9);
        System.out.println("[LongAdder] Sum: " + adder.sum());
    }

    // ─────────────────────────────────────────────────────────────────────────
    // 9. COMMON PATTERNS
    // ─────────────────────────────────────────────────────────────────────────

    // Deadlock demo & fix
    static void demonstrateDeadlockAndFix() throws InterruptedException {
        System.out.println("\n=== 9a. Deadlock Prevention ===");
        ReentrantLock lock1 = new ReentrantLock();
        ReentrantLock lock2 = new ReentrantLock();

        // FIX: use tryLock with timeout to avoid deadlock
        Thread t1 = new Thread(() -> {
            try {
                if (lock1.tryLock(100, TimeUnit.MILLISECONDS)) {
                    try {
                        Thread.sleep(50);
                        if (lock2.tryLock(100, TimeUnit.MILLISECONDS)) {
                            try { System.out.println("[Deadlock Fix] T1 acquired both locks"); }
                            finally { lock2.unlock(); }
                        } else { System.out.println("[Deadlock Fix] T1 couldn't get lock2, backing off"); }
                    } finally { lock1.unlock(); }
                }
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });

        Thread t2 = new Thread(() -> {
            try {
                if (lock1.tryLock(100, TimeUnit.MILLISECONDS)) { // same order as t1 — no deadlock
                    try {
                        if (lock2.tryLock(100, TimeUnit.MILLISECONDS)) {
                            try { System.out.println("[Deadlock Fix] T2 acquired both locks"); }
                            finally { lock2.unlock(); }
                        }
                    } finally { lock1.unlock(); }
                }
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });

        t1.start(); t2.start(); t1.join(); t2.join();
    }

    // Thread-safe Singleton — Double-Checked Locking
    static class Singleton {
        private static volatile Singleton instance;
        private Singleton() {}
        public static Singleton getInstance() {
            if (instance == null) {
                synchronized (Singleton.class) {
                    if (instance == null) instance = new Singleton();
                }
            }
            return instance;
        }
    }

    // ThreadLocal — per-thread data, no synchronization needed
    static void demonstrateThreadLocal() throws InterruptedException {
        System.out.println("\n=== 9b. ThreadLocal ===");
        ThreadLocal<Integer> threadId = ThreadLocal.withInitial(() -> (int) (Math.random() * 1000));

        Thread ta = new Thread(() -> {
            System.out.println("[ThreadLocal] Thread A id: " + threadId.get());
            threadId.remove(); // prevent memory leaks in thread pools
        });
        Thread tb = new Thread(() -> {
            System.out.println("[ThreadLocal] Thread B id: " + threadId.get());
            threadId.remove();
        });
        ta.start(); tb.start(); ta.join(); tb.join();
    }

    // BlockingQueue — thread-safe producer-consumer
    static void demonstrateBlockingQueue() throws InterruptedException {
        System.out.println("\n=== 9c. BlockingQueue ===");
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(5);

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    queue.put(i);
                    System.out.println("[BQ] Produced: " + i);
                }
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    int val = queue.take();
                    System.out.println("[BQ] Consumed: " + val);
                }
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });

        producer.start(); consumer.start();
        producer.join();  consumer.join();
    }

    // ForkJoinPool — divide and conquer parallelism
    static class SumTask extends RecursiveTask<Long> {
        private final int[] arr;
        private final int lo, hi;
        static final int THRESHOLD = 3;

        SumTask(int[] arr, int lo, int hi) { this.arr = arr; this.lo = lo; this.hi = hi; }

        @Override
        protected Long compute() {
            if (hi - lo <= THRESHOLD) {
                long sum = 0;
                for (int i = lo; i < hi; i++) sum += arr[i];
                return sum;
            }
            int mid = (lo + hi) / 2;
            SumTask left  = new SumTask(arr, lo, mid);
            SumTask right = new SumTask(arr, mid, hi);
            left.fork();                          // async execute left
            return right.compute() + left.join(); // compute right, wait for left
        }
    }

    static void demonstrateForkJoin() {
        System.out.println("\n=== 9d. ForkJoinPool ===");
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ForkJoinPool pool = new ForkJoinPool();
        long total = pool.invoke(new SumTask(arr, 0, arr.length));
        System.out.println("[ForkJoin] Sum of 1..10 = " + total); // 55
        pool.shutdown();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // MAIN
    // ─────────────────────────────────────────────────────────────────────────

    public static void main(String[] args) throws Exception {
        demonstrateThreadCreation();
        demonstrateLifecycle();
        demonstrateSynchronization();
        demonstrateProducerConsumer();
        demonstrateLocks();
        demonstrateConcurrencyUtils();
        demonstrateExecutors();
        demonstrateVolatileAndAtomic();
        demonstrateDeadlockAndFix();
        demonstrateThreadLocal();
        demonstrateBlockingQueue();
        demonstrateForkJoin();

        // Singleton — same instance across threads
        System.out.println("\n=== Singleton ===");
        Singleton s1 = Singleton.getInstance();
        Singleton s2 = Singleton.getInstance();
        System.out.println("[Singleton] Same instance: " + (s1 == s2));
    }
}
