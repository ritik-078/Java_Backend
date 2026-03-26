# Java Multithreading

## Table of Contents
1. [Core Concepts](#core-concepts)
2. [Creating Threads](#creating-threads)
3. [Thread Lifecycle](#thread-lifecycle)
4. [Synchronization](#synchronization)
5. [Inter-thread Communication](#inter-thread-communication)
6. [Locks & Concurrency Utilities](#locks--concurrency-utilities)
7. [Executor Framework](#executor-framework)
8. [Volatile & Atomic](#volatile--atomic)
9. [Common Problems & Patterns](#common-problems--patterns)
10. [Interview Questions & Answers](#interview-questions--answers)

---

## Core Concepts

| Term | Meaning |
|------|---------|
| Process | Independent program with its own memory space |
| Thread | Lightweight unit of execution within a process, shares memory |
| Concurrency | Multiple tasks making progress (may not be simultaneous) |
| Parallelism | Multiple tasks literally running at the same time (multi-core) |
| Race Condition | Two threads read/write shared data concurrently → unpredictable result |
| Deadlock | Two threads each hold a lock the other needs → both wait forever |
| Starvation | A thread never gets CPU time because others keep taking priority |
| Livelock | Threads keep responding to each other but make no progress |

---

## Creating Threads

### 1. Extending Thread
```java
class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("Running in: " + Thread.currentThread().getName());
    }
}

MyThread t = new MyThread();
t.start(); // always call start(), not run()
```

### 2. Implementing Runnable (preferred)
```java
class MyTask implements Runnable {
    @Override
    public void run() {
        System.out.println("Task running in: " + Thread.currentThread().getName());
    }
}

Thread t = new Thread(new MyTask());
t.start();

// Lambda shorthand
Thread t2 = new Thread(() -> System.out.println("Lambda thread"));
t2.start();
```

### 3. Implementing Callable (returns a value)
```java
import java.util.concurrent.*;

Callable<Integer> task = () -> {
    Thread.sleep(1000);
    return 42;
};

ExecutorService executor = Executors.newSingleThreadExecutor();
Future<Integer> future = executor.submit(task);
System.out.println("Result: " + future.get()); // blocks until done
executor.shutdown();
```

> **Why Runnable over Thread?**
> Java is single-inheritance. Implementing Runnable lets your class still extend another class,
> and it separates the task from the thread mechanism.

---

## Thread Lifecycle

```
NEW ──start()──► RUNNABLE ──scheduler──► RUNNING
                    ▲                       │
                    │                  sleep/wait/IO
                    │                       │
                    └──────────────── BLOCKED/WAITING/TIMED_WAITING
                                            │
                                       run() ends
                                            │
                                       TERMINATED
```

| State | Cause |
|-------|-------|
| NEW | Thread created, not started |
| RUNNABLE | start() called, waiting for CPU |
| RUNNING | CPU assigned, executing run() |
| BLOCKED | Waiting to acquire a monitor lock |
| WAITING | wait(), join() with no timeout |
| TIMED_WAITING | sleep(n), wait(n), join(n) |
| TERMINATED | run() completed or exception thrown |

---

## Synchronization

### The Problem — Race Condition
```java
class Counter {
    int count = 0;
    void increment() { count++; } // NOT atomic: read → modify → write
}

Counter c = new Counter();
Thread t1 = new Thread(() -> { for (int i = 0; i < 1000; i++) c.increment(); });
Thread t2 = new Thread(() -> { for (int i = 0; i < 1000; i++) c.increment(); });
t1.start(); t2.start();
t1.join();  t2.join();
// count is likely < 2000 due to race condition
```

### synchronized Method
```java
class Counter {
    int count = 0;
    synchronized void increment() { count++; } // only one thread at a time
}
```

### synchronized Block (finer granularity)
```java
class Counter {
    int count = 0;
    private final Object lock = new Object();

    void increment() {
        synchronized (lock) {  // lock only the critical section
            count++;
        }
        // other non-critical code runs concurrently
    }
}
```

### Static Synchronization (class-level lock)
```java
class Counter {
    static int count = 0;
    static synchronized void increment() { count++; } // locks the Class object
}
```

> **Key rule:** `synchronized` on an instance method locks `this`. On a static method it locks
> `ClassName.class`. Two threads can simultaneously enter a synchronized instance method and a
> synchronized static method — they use different locks.

---

## Inter-thread Communication

`wait()`, `notify()`, `notifyAll()` must be called inside a `synchronized` block.

### Producer-Consumer (classic)
```java
class SharedBuffer {
    private int data;
    private boolean hasData = false;

    synchronized void produce(int value) throws InterruptedException {
        while (hasData) wait();       // wait if buffer is full
        data = value;
        hasData = true;
        System.out.println("Produced: " + value);
        notify();                     // wake up the consumer
    }

    synchronized int consume() throws InterruptedException {
        while (!hasData) wait();      // wait if buffer is empty
        hasData = false;
        System.out.println("Consumed: " + data);
        notify();                     // wake up the producer
        return data;
    }
}
```

> **Always use `while` not `if` before `wait()`** — spurious wakeups can occur where a thread
> wakes up without being notified.

---

## Locks & Concurrency Utilities

### ReentrantLock
```java
import java.util.concurrent.locks.*;

class Counter {
    int count = 0;
    private final ReentrantLock lock = new ReentrantLock();

    void increment() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock(); // always unlock in finally
        }
    }

    void tryIncrement() throws InterruptedException {
        if (lock.tryLock(100, TimeUnit.MILLISECONDS)) { // non-blocking attempt
            try { count++; }
            finally { lock.unlock(); }
        }
    }
}
```

### ReadWriteLock (multiple readers, one writer)
```java
ReadWriteLock rwLock = new ReentrantReadWriteLock();

void read() {
    rwLock.readLock().lock();
    try { /* multiple threads can read simultaneously */ }
    finally { rwLock.readLock().unlock(); }
}

void write(int val) {
    rwLock.writeLock().lock();
    try { /* exclusive access */ }
    finally { rwLock.writeLock().unlock(); }
}
```

### CountDownLatch (wait for N tasks to finish)
```java
CountDownLatch latch = new CountDownLatch(3); // count = 3

Runnable task = () -> {
    doWork();
    latch.countDown(); // decrement count
};

new Thread(task).start();
new Thread(task).start();
new Thread(task).start();

latch.await(); // main thread blocks until count reaches 0
System.out.println("All tasks done");
```

### CyclicBarrier (all threads wait at a checkpoint)
```java
CyclicBarrier barrier = new CyclicBarrier(3, () -> System.out.println("All reached barrier"));

Runnable task = () -> {
    doPhase1();
    barrier.await(); // wait for all 3 threads
    doPhase2();      // all start phase 2 together
};
```

### Semaphore (limit concurrent access)
```java
Semaphore semaphore = new Semaphore(3); // max 3 threads at a time

void accessResource() throws InterruptedException {
    semaphore.acquire();
    try { useResource(); }
    finally { semaphore.release(); }
}
```

---

## Executor Framework

### Thread Pools
```java
// Fixed pool — reuses N threads
ExecutorService fixed = Executors.newFixedThreadPool(4);

// Cached pool — creates threads as needed, reuses idle ones
ExecutorService cached = Executors.newCachedThreadPool();

// Single thread — tasks execute sequentially
ExecutorService single = Executors.newSingleThreadExecutor();

// Scheduled — run tasks after delay or periodically
ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(2);
scheduled.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);

// Submit tasks
fixed.submit(() -> System.out.println("Task 1"));
Future<Integer> f = fixed.submit(() -> 42);
System.out.println(f.get());

fixed.shutdown();    // waits for running tasks to finish
fixed.shutdownNow(); // interrupts running tasks
```

### CompletableFuture (async pipelines)
```java
CompletableFuture<Integer> cf = CompletableFuture
    .supplyAsync(() -> fetchData())       // runs async
    .thenApply(data -> process(data))     // transform result
    .thenApply(result -> result * 2);

cf.thenAccept(System.out::println);
cf.exceptionally(ex -> { log(ex); return 0; });

// Combine two futures
CompletableFuture<Integer> a = CompletableFuture.supplyAsync(() -> 10);
CompletableFuture<Integer> b = CompletableFuture.supplyAsync(() -> 20);
CompletableFuture<Integer> sum = a.thenCombine(b, Integer::sum);
System.out.println(sum.get()); // 30

// Wait for all
CompletableFuture.allOf(a, b).thenRun(() -> System.out.println("Both done"));
```

---

## Volatile & Atomic

### volatile
```java
class FlagExample {
    private volatile boolean running = true; // guaranteed visibility across threads

    void stop() { running = false; }

    void run() {
        while (running) { doWork(); } // always reads fresh value from main memory
    }
}
```

> `volatile` ensures **visibility** but NOT **atomicity**. `count++` is still not safe with volatile.

### Atomic Classes
```java
import java.util.concurrent.atomic.*;

AtomicInteger count = new AtomicInteger(0);
count.incrementAndGet();        // atomic i++
count.compareAndSet(5, 10);     // CAS: if value==5, set to 10

AtomicReference<String> ref = new AtomicReference<>("hello");
ref.compareAndSet("hello", "world");

// LongAdder — better than AtomicLong under high contention
LongAdder adder = new LongAdder();
adder.increment();
System.out.println(adder.sum());
```

---

## Common Problems & Patterns

### Deadlock Example & Fix
```java
Object lock1 = new Object(), lock2 = new Object();

// DEADLOCK: t1 holds lock1 waits for lock2; t2 holds lock2 waits for lock1
Thread t1 = new Thread(() -> {
    synchronized (lock1) { synchronized (lock2) { /* work */ } }
});
Thread t2 = new Thread(() -> {
    synchronized (lock2) { synchronized (lock1) { /* work */ } } // opposite order!
});

// FIX: always acquire locks in the same order
Thread t2Fixed = new Thread(() -> {
    synchronized (lock1) { synchronized (lock2) { /* work */ } }
});
```

### Thread-safe Singleton (Double-Checked Locking)
```java
class Singleton {
    private static volatile Singleton instance;

    private Singleton() {}

    public static Singleton getInstance() {
        if (instance == null) {               // first check (no lock)
            synchronized (Singleton.class) {
                if (instance == null) {       // second check (with lock)
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
```

### ThreadLocal (per-thread data)
```java
ThreadLocal<SimpleDateFormat> dateFormat =
    ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));

// Each thread gets its own instance — no sharing, no sync needed
String formatted = dateFormat.get().format(new Date());
dateFormat.remove(); // always remove when done (prevents memory leaks in thread pools)
```

### BlockingQueue (thread-safe producer-consumer)
```java
BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(10);

Thread producer = new Thread(() -> {
    for (int i = 0; i < 20; i++) queue.put(i); // blocks if full
});

Thread consumer = new Thread(() -> {
    while (true) process(queue.take()); // blocks if empty
});
```

### ForkJoinPool (divide and conquer)
```java
class SumTask extends RecursiveTask<Long> {
    private final int[] arr;
    private final int lo, hi;
    static final int THRESHOLD = 1000;

    SumTask(int[] arr, int lo, int hi) { this.arr = arr; this.lo = lo; this.hi = hi; }

    @Override
    protected Long compute() {
        if (hi - lo <= THRESHOLD) {
            long sum = 0;
            for (int i = lo; i < hi; i++) sum += arr[i];
            return sum;
        }
        int mid = (lo + hi) / 2;
        SumTask left = new SumTask(arr, lo, mid);
        SumTask right = new SumTask(arr, mid, hi);
        left.fork();                          // async execute left
        return right.compute() + left.join(); // compute right, wait for left
    }
}

ForkJoinPool pool = new ForkJoinPool();
long total = pool.invoke(new SumTask(arr, 0, arr.length));
```

---

## Interview Questions & Answers

### Q1. What is the difference between `start()` and `run()`?
`start()` creates a new OS thread and calls `run()` inside it.
`run()` called directly executes in the **current** thread — no new thread is created.

```java
Thread t = new Thread(() -> System.out.println(Thread.currentThread().getName()));
t.run();   // prints "main"
t.start(); // prints "Thread-0"
```

---

### Q2. What is the difference between `wait()` and `sleep()`?

| | `wait()` | `sleep()` |
|--|---------|-----------|
| Defined in | Object | Thread |
| Releases lock? | Yes | No |
| Must be in synchronized? | Yes | No |
| Woken by | `notify()` / `notifyAll()` | timeout expires |
| Use case | Inter-thread communication | Pause execution |

---

### Q3. What is a race condition? How do you fix it?
A race condition occurs when the result depends on the timing/order of thread execution on shared data.

```java
// Race condition — count++ is 3 steps: read, increment, write
int count = 0;
// Thread 1 and Thread 2 both do count++ simultaneously → lost update

// Fix 1: synchronized
synchronized void increment() { count++; }

// Fix 2: AtomicInteger
AtomicInteger count = new AtomicInteger(0);
count.incrementAndGet();
```

---

### Q4. What is deadlock? How do you prevent it?
Deadlock: Thread A holds Lock1 and waits for Lock2. Thread B holds Lock2 and waits for Lock1. Both wait forever.

Prevention strategies:
1. Always acquire locks in a consistent global order
2. Use `tryLock()` with a timeout
3. Avoid holding multiple locks simultaneously
4. Use higher-level utilities (BlockingQueue, etc.)

```java
if (lock1.tryLock(100, TimeUnit.MILLISECONDS)) {
    try {
        if (lock2.tryLock(100, TimeUnit.MILLISECONDS)) {
            try { /* do work */ }
            finally { lock2.unlock(); }
        }
    } finally { lock1.unlock(); }
}
```

---

### Q5. What is the difference between `synchronized` and `ReentrantLock`?

| Feature | synchronized | ReentrantLock |
|---------|-------------|---------------|
| Syntax | keyword | explicit lock/unlock |
| Try locking | No | `tryLock()` |
| Interruptible wait | No | `lockInterruptibly()` |
| Fairness policy | No | `new ReentrantLock(true)` |
| Multiple conditions | One (wait/notify) | Many (`newCondition()`) |
| Auto-release | Yes (end of block) | No — must call `unlock()` |

---

### Q6. What is `volatile`? When is it not enough?
`volatile` guarantees that reads/writes go directly to main memory (visibility), preventing threads from using stale cached values.

Not enough when:
- The operation is not atomic (`count++` is read-modify-write)
- You need check-then-act atomicity

```java
volatile boolean flag = true;  // safe: single write, multiple reads

volatile int count = 0;
count++;  // NOT safe — still a race condition
// Use AtomicInteger instead
```

---

### Q7. What is the happens-before relationship?
A guarantee that memory writes by one specific thread are visible to another specific thread.

Established by:
- `Thread.start()` — actions before start() are visible to the new thread
- `Thread.join()` — actions in a thread are visible after join() returns
- `synchronized` — unlock happens-before the next lock on the same monitor
- `volatile` write happens-before a subsequent volatile read of the same variable

---

### Q8. What is the difference between `notify()` and `notifyAll()`?
- `notify()` wakes up **one** arbitrary waiting thread
- `notifyAll()` wakes up **all** waiting threads (they then compete for the lock)

Use `notifyAll()` when multiple threads may be waiting for different conditions, to avoid a situation where the wrong thread is woken and all threads stay stuck.

---

### Q9. What is a ThreadPool? Why use it?
A thread pool maintains a set of reusable worker threads, avoiding the overhead of creating/destroying a thread per task.

Benefits:
- Reduced thread creation overhead
- Controlled resource usage (bounded number of threads)
- Task queuing when all threads are busy

```java
ExecutorService pool = Executors.newFixedThreadPool(4);
for (int i = 0; i < 100; i++) pool.submit(() -> processTask());
pool.shutdown();
```

---

### Q10. What is `Future` vs `CompletableFuture`?

| | Future | CompletableFuture |
|--|--------|------------------|
| Get result | `get()` — blocking | `thenApply/Accept` — non-blocking |
| Chaining | No | Yes |
| Exception handling | try-catch on `get()` | `exceptionally()` |
| Manual completion | No | `complete(value)` |
| Combining futures | No | `thenCombine()`, `allOf()` |

```java
// Future — blocking
Future<Integer> f = executor.submit(() -> compute());
int result = f.get(); // blocks here

// CompletableFuture — non-blocking pipeline
CompletableFuture.supplyAsync(() -> compute())
    .thenApply(r -> r * 2)
    .thenAccept(System.out::println)
    .exceptionally(ex -> { ex.printStackTrace(); return null; });
```

---

### Q11. What is `ConcurrentHashMap` vs `Collections.synchronizedMap()`?

| | ConcurrentHashMap | synchronizedMap |
|--|------------------|-----------------|
| Locking granularity | Bucket-level | Entire map |
| Read performance | High (no lock on reads) | Low (locks on reads) |
| Null keys/values | Not allowed | Allowed |
| Iterator | Weakly consistent | Throws ConcurrentModificationException |

```java
// ConcurrentHashMap — preferred
ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
map.put("a", 1);
map.computeIfAbsent("b", k -> k.length()); // atomic compound operation

// synchronizedMap — coarse-grained lock
Map<String, Integer> syncMap = Collections.synchronizedMap(new HashMap<>());
```

---

### Q12. What is `ThreadLocal`? When would you use it?
`ThreadLocal` provides a separate copy of a variable for each thread. No synchronization needed since no sharing occurs.

Use cases:
- Per-thread database connections
- Per-thread `SimpleDateFormat` (not thread-safe)
- Storing user session/request context in web apps

```java
ThreadLocal<Integer> threadId = ThreadLocal.withInitial(() -> (int)(Math.random() * 100));

Thread t1 = new Thread(() -> System.out.println("T1: " + threadId.get()));
Thread t2 = new Thread(() -> System.out.println("T2: " + threadId.get()));
// Each thread gets its own independent value

// Always remove in thread pools to prevent memory leaks
threadId.remove();
```

---

### Q13. What is the difference between `Callable` and `Runnable`?

| | Runnable | Callable\<V\> |
|--|---------|--------------|
| Return value | void | V |
| Throws checked exception | No | Yes |
| Used with | Thread, Executor | ExecutorService |

```java
Runnable r = () -> System.out.println("no return");

Callable<String> c = () -> {
    Thread.sleep(500);
    return "result"; // can return value and throw checked exceptions
};
```

---

### Q14. How does `synchronized` work internally?
Every Java object has a monitor (mutex). `synchronized` acquires the monitor on entry and releases it on exit — even if an exception is thrown.

- Instance method → locks `this`
- Static method → locks `ClassName.class`
- Block → locks the specified object reference

JVM uses `monitorenter` / `monitorexit` bytecode instructions. JDK 6+ optimizes with:
1. **Biased locking** — no CAS if only one thread uses the lock
2. **Lightweight locking** — CAS-based spin lock under low contention
3. **Heavyweight locking** — OS mutex, used only under high contention

---

### Q15. Explain Livelock vs Deadlock vs Starvation.

| | Deadlock | Livelock | Starvation |
|--|---------|---------|-----------|
| Threads making progress? | No | No (but active) | Some yes, one no |
| Cause | Circular lock wait | Threads react to each other endlessly | Low-priority thread never scheduled |
| Fix | Lock ordering, tryLock | Randomized retry, backoff | Fair locks, priority adjustment |

---

### Quick Reference: Which tool to use?

| Scenario | Use |
|----------|-----|
| Simple thread-safe counter | `AtomicInteger` |
| Protect a critical section | `synchronized` or `ReentrantLock` |
| Multiple readers, one writer | `ReadWriteLock` |
| Wait for N tasks to complete | `CountDownLatch` |
| All threads sync at a checkpoint | `CyclicBarrier` |
| Limit concurrent access | `Semaphore` |
| Thread-safe queue | `BlockingQueue` |
| Per-thread state | `ThreadLocal` |
| Async task with result & chaining | `CompletableFuture` |
| Divide-and-conquer parallelism | `ForkJoinPool` |
| Visibility only (single writer) | `volatile` |
