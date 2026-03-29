Core Java – SDE Interview Prep
100 Most Asked Core Java Interview Questions
OOP & Basics (1–15)
1. What are the four pillars of OOP?

Encapsulation: Hide state behind access methods; e.g., private int balance with getBalance() in BankAccount.
Inheritance: Dog extends Animal — reuses eat() from Animal.
Polymorphism: Animal a = new Dog(); a.speak(); — Dog's speak() called at runtime.
Abstraction: abstract class Shape { abstract double area(); } — hides implementation.
2. What is the difference between abstract class and interface?

Abstract Class	Interface
Constructor	Yes	No
Fields	Any	public static final only
Methods	Abstract + concrete	Abstract + default/static (Java 8+)
Inheritance	Single	Multiple
Use abstract class for shared state; interface for capability contracts.

3. Can an abstract class have a constructor? Can it be instantiated? Yes to constructor (invoked via super() from subclass). No to instantiation — new AbstractClass() is a compile error.

4. What is method overloading vs method overriding?

Overloading: Same name, different parameters; resolved at compile time.
Overriding: Subclass redefines parent method with same signature; resolved at runtime via dynamic dispatch.
5. What is the difference between == and equals()?

== compares references for objects, values for primitives.
equals() compares logical content (overridden in String, Integer, etc.).
new String("abc") == new String("abc") → false; .equals() → true.
6. What is hashCode() and why must it be consistent with equals()? Returns an int used by hash-based collections to find the bucket. Contract: if a.equals(b) then a.hashCode() == b.hashCode(). Violation causes objects to be lost in HashMaps.

7. What is the final keyword?

Variable: Cannot be reassigned after initialization.
Method: Cannot be overridden.
Class: Cannot be subclassed (e.g., String).
8. What is static? What are static blocks and when do they run? static members belong to the class, not instances. Static blocks run once when the class is first loaded by the ClassLoader, before any constructor.

9. What is the difference between this and super?

this — current instance; calls same-class constructor via this(...).
super — parent class; calls parent constructor via super(...) or parent method via super.method().
10. What is covariant return type? An overriding method can return a subtype of the parent's declared return type. E.g., parent returns Animal, child can return Dog. Introduced in Java 5.

11. Can we override a static method? What is method hiding? No. Static methods are resolved at compile time by reference type. A subclass defining the same static signature hides the parent's method — not overrides it.

12. What is the difference between Comparable and Comparator?

Comparable.compareTo() — natural ordering defined inside the class; one ordering only.
Comparator.compare() — external ordering; multiple orderings possible; used with sort().
13. What is an immutable class? How do you create one?

Declare class final
All fields private final
No setters
Deep-copy mutable fields in constructor/getters
Examples: String, Integer, LocalDate.

14. What is the difference between shallow copy and deep copy?

Shallow: Copies the object; nested references point to the same objects.
Deep: Recursively copies all nested objects; must be implemented manually.
15. What is object cloning? What is the Cloneable interface? Object.clone() creates a field-by-field copy. Class must implement marker interface Cloneable; otherwise CloneNotSupportedException is thrown. Default clone is shallow.

String (16–22)
16. Why is String immutable in Java? Security (class names, passwords can't be altered), thread safety (no synchronization needed), and String pool efficiency (safe to share references). Achieved by private final char[] backing array and no mutating methods.

17. What is the String Pool? How does intern() work? A cache of string literals in the heap. Literals like "abc" are automatically pooled. intern() checks the pool: if the string exists, returns the pooled reference; otherwise adds it. Allows == comparison for interned strings.

18. What is the difference between String, StringBuilder, and StringBuffer?

String: Immutable; each concat creates a new object.
StringBuilder: Mutable, not thread-safe, fast.
StringBuffer: Mutable, thread-safe (synchronized), slower.
19. What does String.valueOf() vs toString() do?

String.valueOf(obj) — null-safe; returns "null" if argument is null.
obj.toString() — throws NullPointerException if obj is null.
20. How does + operator work with Strings internally? The compiler converts "a" + "b" + c into new StringBuilder().append("a").append("b").append(c).toString(). In loops, this creates a new StringBuilder each iteration — use explicit StringBuilder instead.

21. What is the output of "abc" == new String("abc")? false. "abc" is a pool reference; new String("abc") creates a new heap object. Use .equals() for content comparison.

22. How do you reverse a String without using built-in reverse?

String reverse(String s) {
    char[] c = s.toCharArray();
    int l = 0, r = c.length - 1;
    while (l < r) { char t = c[l]; c[l++] = c[r]; c[r--] = t; }
    return new String(c);
}
Collections (23–40)
23. What is the difference between ArrayList and LinkedList?

ArrayList: Dynamic array; O(1) random access, O(n) insert/delete in middle.
LinkedList: Doubly-linked list; O(1) insert/delete at ends, O(n) random access. Higher memory overhead (node pointers).
24. How does HashMap work internally? Uses an array of buckets. put(k,v): computes hash(k), maps to bucket index, stores as linked list node. On collision, chains nodes. Java 8+: bucket converts to Red-Black tree when chain length > 8 (treeification).

25. What happens when two keys have the same hashCode in a HashMap? Collision: both entries go into the same bucket and are chained (linked list or tree). equals() is used to distinguish keys within the bucket.

26. What is the difference between HashMap and Hashtable?

HashMap: Not synchronized, allows one null key, faster.
Hashtable: Synchronized (method-level), no null keys/values, legacy class. Prefer ConcurrentHashMap over Hashtable.
27. What is ConcurrentHashMap? How is it different from HashMap? Thread-safe map. Java 8+: uses CAS + synchronized on individual buckets (not the whole map). Allows concurrent reads without locking. Does not allow null keys or values.

28. What is the difference between HashSet and TreeSet?

HashSet: Backed by HashMap; O(1) ops; no ordering.
TreeSet: Backed by Red-Black tree; O(log n) ops; sorted natural/custom order.
29. What is LinkedHashMap? When would you use it? HashMap + doubly-linked list maintaining insertion order (or access order if configured). Use for LRU cache (access-order mode + override removeEldestEntry).

30. What is the difference between Iterator and ListIterator?

Iterator: Forward-only; works on any Collection.
ListIterator: Bidirectional; can add/set elements; only for List.
31. What is fail-fast vs fail-safe iterator?

Fail-fast: Throws ConcurrentModificationException if collection is modified during iteration (ArrayList, HashMap).
Fail-safe: Iterates over a snapshot copy; no exception (CopyOnWriteArrayList, ConcurrentHashMap).
32. How does PriorityQueue work? What is the default ordering? Min-heap backed by an array. Default: natural ordering (smallest element at head). Custom ordering via Comparator. peek() O(1), poll()/add() O(log n).

33. What is the difference between poll() and remove() in Queue?

poll(): Returns and removes head; returns null if empty.
remove(): Returns and removes head; throws NoSuchElementException if empty.
34. What is Collections.unmodifiableList()? Is it truly immutable? Returns a view that throws UnsupportedOperationException on mutation. Not truly immutable — the underlying list can still be modified through the original reference. Use List.of() for true immutability.

35. What is the difference between List.of() and Arrays.asList()?

List.of(): Truly immutable; no nulls allowed; no set/add/remove.
Arrays.asList(): Fixed-size but allows set(); backed by the array; allows nulls.
36. How do you sort a list of custom objects?

// Natural order (implement Comparable)
Collections.sort(list);
// Custom order
list.sort(Comparator.comparing(Person::getAge).thenComparing(Person::getName));
37. What is the time complexity of get(), put(), remove() in HashMap? O(1) average case. O(n) worst case (all keys in one bucket, pre-Java 8). O(log n) worst case with treeification (Java 8+).

38. What is WeakHashMap? When is it useful? Keys are held by weak references — GC can collect them even if the map holds them. Entry is automatically removed when key is GC'd. Useful for caches where entries should expire when no other reference exists.

39. What is EnumMap and EnumSet?

EnumMap: Map with enum keys; backed by array indexed by enum ordinal; very fast.
EnumSet: Set of enum values; backed by a bit vector; extremely compact and fast.
40. How does TreeMap maintain sorted order? Backed by a Red-Black tree (self-balancing BST). Keys are ordered by natural ordering or a provided Comparator. All operations O(log n).

Generics (41–44)
41. What is type erasure in Java Generics? Generic type parameters are removed at compile time and replaced with Object (or the bound). The bytecode has no generic type info. This ensures backward compatibility with pre-generics code.

42. What is the difference between List<?>, List<? extends T>, and List<? super T>?

List<?>: Unknown type; read-only (can only get Object).
List<? extends T>: Any subtype of T; read-only (producer) — PECS: Producer Extends.
List<? super T>: Any supertype of T; write-only (consumer) — PECS: Consumer Super.
43. Why can't you create a generic array like new T[]? Due to type erasure, T is unknown at runtime. Arrays are reified (type checked at runtime), but generics are not. Creating new T[] would bypass array type safety, so the compiler disallows it.

44. What is a bounded type parameter? Restricts the types that can be used as type arguments.

<T extends Comparable<T>>  // upper bound
<T super Integer>           // lower bound (wildcards only)
Exception Handling (45–52)
45. What is the difference between checked and unchecked exceptions?

Checked: Must be declared (throws) or caught; subclass of Exception but not RuntimeException. E.g., IOException, SQLException.
Unchecked: Subclass of RuntimeException; no forced handling. E.g., NullPointerException, IllegalArgumentException.
46. What is the difference between throw and throws?

throw: Statement to actually throw an exception instance: throw new IOException("msg").
throws: Keyword in method signature declaring it may throw checked exceptions: void read() throws IOException.
47. Can finally block be skipped? When? Yes, in two cases: System.exit() is called, or the JVM crashes / is killed. In all other cases (including exceptions in catch), finally runs.

48. What is try-with-resources? What interface must the resource implement? Automatically closes resources after the try block. Resource must implement AutoCloseable (or Closeable). close() is called even if an exception occurs.

try (BufferedReader br = new BufferedReader(new FileReader(path))) { ... }
49. What is exception chaining? Wrapping one exception inside another to preserve the original cause.

catch (SQLException e) { throw new RuntimeException("DB error", e); }
// Retrieve: e.getCause()
50. Can we have a try block without catch? Yes, with finally: try { } finally { }. Also valid with try-with-resources (no explicit catch needed).

51. What is the difference between Error and Exception?

Error: JVM-level problems not meant to be caught (OutOfMemoryError, StackOverflowError).
Exception: Application-level problems that can and should be handled.
52. What happens if an exception is thrown inside a finally block? It replaces any exception from the try/catch block — the original exception is lost (suppressed). Use Throwable.addSuppressed() to preserve it.

Multithreading & Concurrency (53–72)
53. What is the difference between Thread and Runnable?

Thread: A class; extending it means you can't extend anything else.
Runnable: A functional interface; preferred — separates task from execution mechanism. Pass to Thread or ExecutorService.
54. What are the different states of a thread? NEW → RUNNABLE → BLOCKED / WAITING / TIMED_WAITING → TERMINATED. Checked via thread.getState().

55. What is the difference between sleep() and wait()?

sleep(ms): Pauses thread for given time; does NOT release the lock; called on Thread.
wait(): Releases the intrinsic lock and waits until notify(); must be inside synchronized; called on an Object.
56. What is synchronized? What is an intrinsic lock? Every object has an intrinsic (monitor) lock. synchronized on a method/block acquires this lock, ensuring only one thread executes that section at a time. Prevents race conditions.

57. What is volatile? When should you use it? volatile ensures a variable is read/written directly from main memory, not a thread's CPU cache. Guarantees visibility but NOT atomicity. Use for simple flags (boolean running = true) not for compound operations like i++.

58. What is a race condition? Give an example. Two threads read-modify-write a shared variable concurrently, producing incorrect results. E.g., two threads both read count=5, both increment to 6, both write 6 — expected 7. Fix: synchronized or AtomicInteger.

59. What is a deadlock? How do you prevent it? Two threads each hold a lock the other needs, causing infinite wait. Prevention:

Always acquire locks in the same order.
Use tryLock() with timeout.
Avoid nested locks.
60. What is ThreadLocal? When is it useful? Provides a per-thread variable — each thread has its own isolated copy. Useful for storing user sessions, database connections, or SimpleDateFormat instances per thread without synchronization.

61. What is the difference between notify() and notifyAll()?

notify(): Wakes one arbitrary waiting thread.
notifyAll(): Wakes all waiting threads; they compete for the lock. Prefer notifyAll() unless you know exactly one thread should proceed.
62. What is ExecutorService? What are the types of thread pools? Manages a pool of threads for task execution. Types via Executors:

newFixedThreadPool(n) — fixed n threads
newCachedThreadPool() — grows/shrinks dynamically
newSingleThreadExecutor() — one thread, sequential
newScheduledThreadPool(n) — scheduled/periodic tasks
63. What is Callable vs Runnable? What does Future return?

Runnable.run(): No return value, no checked exception.
Callable.call(): Returns a value, can throw checked exceptions.
Future<T>: Handle to the async result; future.get() blocks until done.
64. What is CountDownLatch? How is it different from CyclicBarrier?

CountDownLatch: One-time; threads call countDown(); main thread waits at await() until count reaches 0.
CyclicBarrier: Reusable; all N threads wait at await() until all arrive, then proceed together.
65. What is ReentrantLock? How is it different from synchronized?

Explicit lock with lock()/unlock(). Supports tryLock(), lockInterruptibly(), fairness policy, and multiple Condition objects. Must unlock in finally. More flexible than synchronized.
66. What is AtomicInteger? How does CAS work? AtomicInteger provides lock-free thread-safe integer operations. CAS (Compare-And-Swap): atomically checks if value equals expected; if yes, updates to new value; if no, retries. Implemented via CPU-level atomic instructions.

67. What is BlockingQueue? Name implementations. A queue that blocks on put() when full and on take() when empty. Implementations: ArrayBlockingQueue (bounded), LinkedBlockingQueue (optionally bounded), PriorityBlockingQueue, SynchronousQueue (no capacity, direct handoff).

68. What is CompletableFuture? How do you chain async tasks? Async computation pipeline. Key methods:

CompletableFuture.supplyAsync(() -> fetchData())
    .thenApply(data -> process(data))
    .thenAccept(result -> save(result))
    .exceptionally(ex -> { log(ex); return null; });
69. What is the difference between parallelStream() and stream()?

stream(): Sequential processing.
parallelStream(): Splits work across ForkJoinPool threads. Faster for CPU-bound tasks on large datasets; overhead for small collections or I/O-bound tasks. Not always faster — measure first.
70. What is a Semaphore? Give a use case. Controls access to a resource by maintaining a count of permits. acquire() decrements; release() increments. Use case: limit concurrent DB connections to 10 regardless of thread count.

71. What is ForkJoinPool? What is work-stealing? ForkJoinPool is designed for divide-and-conquer tasks (RecursiveTask/RecursiveAction). Work-stealing: idle threads steal tasks from the tail of busy threads' deques, maximizing CPU utilization.

72. What is happens-before relationship in Java Memory Model? A guarantee that memory writes by one action are visible to another. Key rules: unlock happens-before subsequent lock on same monitor; volatile write happens-before subsequent read; Thread.start() happens-before any action in the started thread.

Java 8+ Features (73–84)
73. What is a lambda expression? What is a functional interface? A lambda is an anonymous function: (params) -> body. A functional interface has exactly one abstract method (@FunctionalInterface). Lambdas provide the implementation inline.

74. What are the built-in functional interfaces in java.util.function? Predicate<T> (test), Function<T,R> (apply), Consumer<T> (accept), Supplier<T> (get), BiFunction<T,U,R>, UnaryOperator<T>, BinaryOperator<T>.

75. What is a method reference? Give all four types.

String::toUpperCase        // 1. Instance method of arbitrary instance
obj::toString              // 2. Instance method of specific instance
String::new                // 3. Constructor reference
Integer::parseInt          // 4. Static method reference
76. What is the Stream API? Intermediate vs terminal operations?

Intermediate: Lazy; return a new Stream. E.g., filter, map, sorted, distinct, limit.
Terminal: Trigger execution; return a result. E.g., collect, forEach, reduce, count, findFirst.
77. What is Optional? Why was it introduced? A container that may or may not hold a value. Introduced to avoid NullPointerException and make null-handling explicit in APIs. Use ofNullable, isPresent, orElse, map, filter.

78. What is the difference between map() and flatMap() in streams?

map(): One-to-one transformation; returns Stream<Stream<T>> if mapping to a stream.
flatMap(): One-to-many; flattens nested streams into a single Stream<T>.
list.stream().flatMap(s -> Arrays.stream(s.split(" ")))  // flatten words
79. What are default and static methods in interfaces?

default: Provides a method body in the interface; allows adding methods without breaking existing implementations.
static: Utility methods on the interface itself; not inherited by implementing classes.
80. What is Collectors.groupingBy()? Give an example. Groups stream elements by a classifier function into a Map<K, List<V>>.

Map<String, List<Person>> byCity = people.stream()
    .collect(Collectors.groupingBy(Person::getCity));
81. What is the difference between findFirst() and findAny()?

findFirst(): Returns the first element in encounter order; deterministic.
findAny(): Returns any element; may be faster in parallel streams (no ordering constraint).
82. What are Records in Java 16? Immutable data classes with auto-generated constructor, getters, equals(), hashCode(), toString().

record Point(int x, int y) {}
Point p = new Point(1, 2); p.x(); // getter
83. What are Sealed Classes in Java 17? Restrict which classes can extend/implement them using permits. Enables exhaustive pattern matching.

sealed interface Shape permits Circle, Rectangle {}
84. What is var in Java 10? What are its limitations? Local variable type inference — compiler infers the type. Limitations: only for local variables (not fields, params, return types); cannot be used with null initializer; reduces readability if overused.

JVM & Memory (85–92)
85. What is the difference between JDK, JRE, and JVM?

JVM: Executes bytecode; platform-specific.
JRE: JVM + standard libraries; needed to run Java programs.
JDK: JRE + compiler (javac) + tools; needed to develop Java programs.
86. Explain the JVM memory areas.

Heap: Objects and instance variables; shared; GC managed.
Stack: Method frames, local variables; per-thread.
Metaspace: Class metadata, static variables (replaced PermGen in Java 8).
PC Register: Current instruction pointer per thread.
Native Method Stack: For JNI/native method calls.
87. What is the difference between stack and heap memory?

Stack: LIFO; stores primitives and references; auto-managed; fast; thread-local; limited size.
Heap: Stores objects; shared across threads; GC managed; larger; slower allocation.
88. What is a memory leak in Java? How do you detect one? Objects that are no longer needed but still referenced, preventing GC. Common causes: static collections, unclosed streams, listeners not removed. Detection: heap profilers (VisualVM, YourKit, Eclipse MAT), jmap, heap dumps.

89. What is Garbage Collection? Explain the GC process. Automatic memory reclamation. Process: objects allocated in Eden (Young Gen) → surviving Minor GCs promoted to Survivor spaces → eventually promoted to Old Gen → Major/Full GC cleans Old Gen. GC roots (stack refs, static fields) determine reachability.

90. What is the difference between Minor GC and Major GC?

Minor GC: Cleans Young Gen (Eden + Survivors); fast; frequent.
Major/Full GC: Cleans Old Gen (and sometimes Metaspace); slow; causes Stop-The-World pause.
91. What is the G1 Garbage Collector? Default since Java 9. Divides heap into equal-sized regions (not fixed Young/Old areas). Collects regions with most garbage first (Garbage-First). Aims for predictable pause times via -XX:MaxGCPauseMillis. Better than CMS for large heaps.

92. What is OutOfMemoryError? What are its types?

Java heap space: Heap is full.
GC overhead limit exceeded: GC spending >98% time recovering <2% heap.
Metaspace: Class metadata area full.
Unable to create new native thread: OS thread limit reached.
Direct buffer memory: Off-heap NIO buffer exhausted.
Design & Best Practices (93–100)
93. What is the Singleton pattern? How do you make it thread-safe? Ensures only one instance exists. Thread-safe via double-checked locking with volatile:

private static volatile Singleton instance;
public static Singleton getInstance() {
    if (instance == null)
        synchronized (Singleton.class) {
            if (instance == null) instance = new Singleton();
        }
    return instance;
}
Alternative: enum singleton (enum Singleton { INSTANCE; }) — simplest and safest.

94. What is the Builder pattern? When would you use it over a constructor? Constructs complex objects step-by-step. Use when: many optional parameters, need readable construction, want immutable objects. Avoids telescoping constructors.

Person p = new Person.Builder("Alice").age(30).city("NYC").build();
95. What is the Factory Method pattern? Defines an interface for creating objects but lets subclasses decide which class to instantiate. Decouples client code from concrete classes.

Shape shape = ShapeFactory.create("circle"); // returns Circle instance
96. What is the difference between composition and inheritance? Which is preferred?

Inheritance: "is-a" relationship; tight coupling; can't change at runtime.
Composition: "has-a" relationship; loose coupling; behavior can change at runtime by swapping components. Prefer composition — more flexible, avoids fragile base class problem.
97. What are the SOLID principles?

S: Single Responsibility — one class, one reason to change.
O: Open/Closed — open for extension, closed for modification.
L: Liskov Substitution — subtypes must be substitutable for base types.
I: Interface Segregation — many specific interfaces over one general.
D: Dependency Inversion — depend on abstractions, not concretions.
98. What is dependency injection? How does it relate to IoC? DI: providing dependencies to a class from outside rather than creating them internally. IoC (Inversion of Control): the framework controls object creation and wiring (e.g., Spring). DI is one way to implement IoC. Promotes testability and loose coupling.

99. What is the difference between Serializable and Externalizable?

Serializable: Marker interface; JVM handles serialization automatically.
Externalizable: Extends Serializable; requires implementing writeExternal() and readExternal() for full manual control over serialization. More performant but more work.
100. What is transient keyword? How does it affect serialization? transient marks a field to be excluded from serialization. When the object is deserialized, transient fields are set to their default values (null, 0, false). Use for sensitive data (passwords) or non-serializable fields.

Quick Reference: Complexity Cheat Sheet
Operation	ArrayList	LinkedList	HashMap	TreeMap
get(i)	O(1)	O(n)	O(1) avg	O(log n)
add(end)	O(1) amort	O(1)	—	—
add(mid)	O(n)	O(1)*	—	—
remove	O(n)	O(1)*	O(1) avg	O(log n)
contains	O(n)	O(n)	O(1) avg	O(log n)
search	O(n)	O(n)	O(1) avg	O(log n)
*O(1) for LinkedList only if you have the node reference

Tips for SDE Interviews
Always discuss time and space complexity of your solutions
Know when to use HashMap vs TreeMap vs LinkedHashMap
Be ready to implement Singleton, LRU Cache, Producer-Consumer
Understand happens-before for concurrency questions
Practice explaining HashMap internals — very frequently asked
Know the difference between checked vs unchecked exceptions cold
Be comfortable with Stream API and lambda for Java 8 questions
