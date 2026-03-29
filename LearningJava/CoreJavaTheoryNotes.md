# Core Java Interview Notes

---

## 1. OOP Concepts

### Pillars of OOP
- **Encapsulation** — Binding data and methods together, hiding internal state using access modifiers (`private`, `protected`, `public`). The outside world interacts only through public methods (getters/setters), protecting the internal state from unintended modification.
- **Abstraction** — Hiding implementation details and exposing only what's necessary. You define *what* an object does, not *how* it does it. Achieved via abstract classes and interfaces. Example: you use `List.add()` without knowing how it resizes internally.
- **Inheritance** — A child class acquires fields and methods of a parent class using `extends`. Promotes code reuse. Java supports single class inheritance but multiple interface inheritance. The child can override parent behavior while still reusing common logic.
- **Polymorphism** — "Many forms". The same method name behaves differently based on context.
  - *Compile-time (static)*: Method Overloading — same name, different parameters, resolved at compile time
  - *Runtime (dynamic)*: Method Overriding — child class redefines parent method, resolved at runtime via dynamic dispatch

### Overloading vs Overriding
| | Overloading | Overriding |
|---|---|---|
| Binding | Compile-time | Runtime |
| Signature | Different | Same |
| Inheritance | Not required | Required |
| Return type | Can differ | Must be same (or covariant) |
| `static` methods | Can be overloaded | Cannot be overridden |
| `private` methods | Can be overloaded | Cannot be overridden |
| Access modifier | No restriction | Cannot be more restrictive |

> **Covariant return type** — overriding method can return a subtype of the parent's return type. E.g., parent returns `Animal`, child can return `Dog`.

---

## 2. Classes & Objects

- **Class** — Blueprint/template. Defines structure (fields) and behavior (methods). No memory is allocated until an object is created.
- **Object** — Instance of a class, created with `new`. Each object has its own copy of instance variables but shares class-level (static) variables.
- **Constructor** — Special method called when object is created. No return type, same name as class. If no constructor is defined, Java provides a default no-arg constructor automatically.
- **`this`** — Refers to the current object instance. Used to resolve naming conflicts between fields and parameters, or to call another constructor (`this()`).
- **`super`** — Refers to the immediate parent class. Used to call parent constructor (`super()`) — must be the first statement — or to call an overridden parent method.

### Constructor Chaining
- `this()` — calls another constructor in the same class
- `super()` — calls parent class constructor
- Both must be the first statement in a constructor; you cannot use both in the same constructor

### Types of Classes
- **Abstract class** — Cannot be instantiated directly. May have abstract methods (no body) that subclasses must implement, plus concrete methods with implementation.
- **Final class** — Cannot be subclassed. Used to prevent modification of behavior (e.g., `String`, `Integer`, `Math`).
- **Nested/Inner class** — Class defined inside another class. Has access to outer class members including private ones.
  - *Static nested class* — does not need outer class instance
  - *Non-static inner class* — needs outer class instance to exist
- **Anonymous class** — Class without a name, defined and instantiated in one expression. Commonly used to implement interfaces or extend classes inline.
- **Singleton class** — Only one instance allowed (private constructor + static instance). Ensures a single point of access.

---

## 3. Interfaces

- Defines a **contract** — specifies what a class must do, not how. Any class implementing the interface must provide implementations for all abstract methods.
- **Java 8+**: `default` methods (with body, can be overridden) and `static` methods allowed
- **Java 9+**: `private` methods allowed (to share code between default methods)
- A class can implement multiple interfaces — this is Java's way of achieving multiple inheritance of behavior
- All fields in an interface are `public static final` by default (constants)
- All methods are `public abstract` by default (unless default/static/private)
- Interfaces cannot have instance fields or constructors

### When to use Interface vs Abstract Class
- Use **interface** when unrelated classes need to share a capability (e.g., `Comparable`, `Serializable`, `Runnable`)
- Use **abstract class** when related classes share common state or base implementation (e.g., `AbstractList`, `HttpServlet`)

### Abstract Class vs Interface
| | Abstract Class | Interface |
|---|---|---|
| Instantiation | No | No |
| Multiple inheritance | No | Yes |
| Constructor | Yes | No |
| Fields | Any type | `public static final` only |
| Method types | Abstract + concrete | Abstract + default + static |
| Access modifiers | Any | `public` only (mostly) |
| Use when | Shared base behavior | Defining a contract/capability |

---

## 4. Access Modifiers

| Modifier | Same Class | Same Package | Subclass | Everywhere |
|---|---|---|---|---|
| `private` | ✅ | ❌ | ❌ | ❌ |
| (default) | ✅ | ✅ | ❌ | ❌ |
| `protected` | ✅ | ✅ | ✅ | ❌ |
| `public` | ✅ | ✅ | ✅ | ✅ |

- `private` — strictest, used for encapsulation
- default (package-private) — no keyword needed, accessible within the same package only
- `protected` — useful for inheritance; subclass in a different package can still access it
- `public` — accessible from anywhere

---

## 5. Keywords

- **`static`** — Belongs to the class, not to any instance. Static fields are shared across all objects. Static methods can be called without creating an object. Static blocks run once when the class is loaded.
- **`final`**
  - Variable: value cannot be reassigned (must be initialized at declaration or in constructor)
  - Method: cannot be overridden by subclasses
  - Class: cannot be extended/subclassed
- **`abstract`** — Method has no body (subclass must implement). A class with even one abstract method must be declared abstract.
- **`synchronized`** — Only one thread can execute the block/method at a time. Uses the object's intrinsic (monitor) lock. Prevents race conditions but can cause performance bottlenecks.
- **`volatile`** — Tells JVM to always read/write the variable directly from/to main memory, bypassing CPU cache. Ensures visibility across threads but does NOT guarantee atomicity.
- **`transient`** — Field is excluded from serialization. Useful for sensitive data (passwords) or non-serializable fields.
- **`instanceof`** — Checks if an object is an instance of a class or interface. Returns `false` for `null`. Java 16+ supports pattern matching: `if (obj instanceof String s)`.
- **`native`** — Method implemented in native code (C/C++) via JNI (Java Native Interface)

---

## 6. String

- `String` is **immutable** — once created, its value cannot be changed. Any operation like `concat`, `replace`, `toUpperCase` returns a new `String` object.
- Why immutable? — Thread safety, security (used in class loading, network connections), hashcode caching, String pool optimization
- Stored in the **String Pool** (a special area in heap) — when you create `"hello"`, JVM checks the pool first; if it exists, returns the same reference (interning)
- `new String("hello")` — always creates a new object on heap, bypassing the pool
- `==` compares references (memory addresses); `.equals()` compares actual character content
- `String.intern()` — forces a string into the pool and returns the pooled reference

### String methods commonly asked
- `charAt(i)`, `substring(start, end)`, `indexOf()`, `contains()`, `replace()`, `split()`, `trim()`, `toCharArray()`
- `String.valueOf()` — converts any type to String
- `compareTo()` — lexicographic comparison

### String vs StringBuilder vs StringBuffer
| | String | StringBuilder | StringBuffer |
|---|---|---|---|
| Mutable | No | Yes | Yes |
| Thread-safe | Yes (immutable) | No | Yes (synchronized) |
| Performance | Slow (for concat) | Fastest | Slower than StringBuilder |
| Use when | Fixed values | Single-threaded concat | Multi-threaded concat |

---

## 7. Collections Framework

### Hierarchy
```
Iterable
  └── Collection
        ├── List       → ArrayList, LinkedList, Vector, Stack
        ├── Set        → HashSet, LinkedHashSet, TreeSet
        └── Queue      → PriorityQueue, ArrayDeque

Map (separate hierarchy — does not extend Collection)
  └── HashMap, LinkedHashMap, TreeMap, Hashtable, ConcurrentHashMap
```

### List
- Ordered, allows duplicates, index-based access
- `ArrayList` — backed by a dynamic array; fast random access; slow insert/delete in middle
- `LinkedList` — doubly linked list; fast insert/delete; slow random access; also implements `Deque`
- `Vector` — like ArrayList but synchronized (legacy, prefer ArrayList + explicit sync)

### Set
- No duplicates. Uses `equals()` and `hashCode()` to detect duplicates
- `HashSet` — backed by HashMap; no order guarantee; O(1) operations
- `LinkedHashSet` — maintains insertion order; slightly slower than HashSet
- `TreeSet` — sorted (natural or custom `Comparator`); O(log n); no null

### Map
- Key-value pairs; keys are unique
- `HashMap` — most used; no order; allows one null key and multiple null values; O(1) average
- `LinkedHashMap` — maintains insertion order; useful for LRU cache
- `TreeMap` — sorted by key; O(log n); no null key
- `Hashtable` — legacy, synchronized, no null key/value
- `ConcurrentHashMap` — thread-safe, better performance than Hashtable (segment locking)

### Key Differences
| | ArrayList | LinkedList |
|---|---|---|
| Internal structure | Dynamic array | Doubly linked list |
| Access (get) | O(1) | O(n) |
| Insert/Delete (middle) | O(n) | O(1) |
| Memory | Less | More (node pointers) |

| | HashSet | LinkedHashSet | TreeSet |
|---|---|---|---|
| Order | No order | Insertion order | Sorted order |
| Null | One null | One null | No null |
| Performance | O(1) | O(1) | O(log n) |

| | HashMap | LinkedHashMap | TreeMap | Hashtable |
|---|---|---|---|---|
| Order | No order | Insertion order | Sorted by key | No order |
| Null key | One | One | No | No |
| Thread-safe | No | No | No | Yes |

### How HashMap works internally
1. Key's `hashCode()` is computed and mapped to a bucket index
2. If bucket is empty, entry is placed directly
3. If collision occurs, entries are stored as a linked list in the bucket
4. Java 8+: if a bucket's list exceeds 8 entries, it converts to a **Red-Black Tree** (O(log n) instead of O(n))
5. When load factor (default 0.75) is exceeded, the map **rehashes** — doubles capacity and redistributes entries

### fail-fast vs fail-safe Iterators
- **fail-fast** — Throws `ConcurrentModificationException` if collection is structurally modified during iteration. Uses a `modCount` counter internally. (e.g., `ArrayList`, `HashMap`)
- **fail-safe** — Works on a cloned copy of the collection, so modifications don't affect iteration. No exception thrown but may not reflect latest data. (e.g., `CopyOnWriteArrayList`, `ConcurrentHashMap`)

---

## 8. Generics

- Enables **type-safe** code without casting. Errors caught at compile time, not runtime.
- Type parameters are erased at runtime (**type erasure**) — `List<String>` and `List<Integer>` are both just `List` at runtime. This is for backward compatibility with pre-generics code.
- `<T>` — generic type | `<T extends Number>` — upper bound | `<? super Integer>` — lower bound

### Why Generics?
Without generics: `List list = new ArrayList(); list.add("hello"); String s = (String) list.get(0);` — requires casting and risks `ClassCastException`
With generics: `List<String> list = new ArrayList<>(); String s = list.get(0);` — type-safe, no cast needed

### Wildcards
- `<?>` — unknown type; you can read as `Object` but cannot add (except null)
- `<? extends T>` — T or any subtype; safe to READ (you know it's at least T), cannot write
- `<? super T>` — T or any supertype; safe to WRITE (you can add T), reading gives Object
- **PECS**: Producer Extends, Consumer Super — a useful mnemonic for choosing the right wildcard

---

## 9. Exception Handling

### Hierarchy
```
Throwable
  ├── Error          (JVM-level, don't catch: OutOfMemoryError, StackOverflowError)
  └── Exception
        ├── Checked  (IOException, SQLException — compiler forces you to handle)
        └── Unchecked / RuntimeException (NullPointerException, ArithmeticException — optional to handle)
```

### Checked vs Unchecked
- **Checked** — Represent recoverable conditions (file not found, network failure). Compiler enforces handling via `try-catch` or `throws`. Caller is aware something might go wrong.
- **Unchecked** — Represent programming bugs (null access, bad index, illegal argument). Not enforced by compiler. Should be fixed in code, not caught.

### Key Keywords
- **`throw`** — explicitly throw an exception instance: `throw new IllegalArgumentException("msg")`
- **`throws`** — declares that a method may propagate an exception to its caller: `void read() throws IOException`
- **`finally`** — always executes after try/catch, even if exception is thrown. Used for cleanup (closing resources). Does NOT run only if `System.exit()` is called or JVM crashes.
- **try-with-resources** — auto-closes any `AutoCloseable` resource after the block ends (Java 7+). Cleaner than manually closing in `finally`.
- **Exception chaining** — wrap root cause inside a higher-level exception: `new Exception("High level", cause)`. Use `getCause()` to retrieve the original exception.

### Exception Best Practices
- Never swallow exceptions silently: `catch (Exception e) {}` — always log or rethrow
- Catch the most specific exception first, general (`Exception`) last
- Use custom exceptions to add domain-specific meaning
- Prefer unchecked exceptions for unrecoverable programming errors
- Always close resources — use try-with-resources

---

## 10. Multithreading & Concurrency

### Why Multithreading?
- Improves CPU utilization by running tasks in parallel
- Keeps UI responsive while background tasks run
- Faster execution for I/O-bound and CPU-bound tasks

### Thread Creation
1. Extend `Thread` class and override `run()` — simple but wastes inheritance
2. Implement `Runnable` interface (preferred) — separates task from thread management
3. Implement `Callable<T>` — like Runnable but returns a result and can throw checked exceptions

### Thread Lifecycle
```
NEW → RUNNABLE → RUNNING → BLOCKED/WAITING/TIMED_WAITING → TERMINATED
```
- **NEW** — Thread created but `start()` not called yet
- **RUNNABLE** — Ready to run, waiting for CPU
- **RUNNING** — Currently executing
- **BLOCKED** — Waiting to acquire a lock
- **WAITING** — Waiting indefinitely for another thread (`wait()`, `join()`)
- **TIMED_WAITING** — Waiting for a specified time (`sleep()`, `wait(timeout)`)
- **TERMINATED** — Execution completed

### Key Concepts
- **`synchronized`** — mutual exclusion on a method or block using object's intrinsic lock. Only one thread can hold the lock at a time.
- **`volatile`** — ensures visibility of variable changes across threads. Does NOT make compound operations (like `i++`) atomic.
- **`wait()` / `notify()` / `notifyAll()`** — inter-thread communication. Must be called inside a `synchronized` block. `wait()` releases the lock; `notify()` wakes one waiting thread.
- **Deadlock** — two or more threads waiting on each other's locks indefinitely. Prevention: always acquire locks in the same order.
- **Race condition** — outcome depends on unpredictable thread execution order. Fix with synchronization.
- **Starvation** — a thread never gets CPU time because others always take priority
- **Livelock** — threads keep responding to each other but make no progress

### Executor Framework
- `ExecutorService` — manages a pool of threads; avoids creating threads manually
- `Executors.newFixedThreadPool(n)` — fixed number of threads
- `Executors.newCachedThreadPool()` — creates threads as needed, reuses idle ones
- `Executors.newSingleThreadExecutor()` — single background thread
- `Future<T>` — holds result of async `Callable` task; `get()` blocks until result is ready
- `CompletableFuture` — non-blocking async chaining with callbacks (Java 8+)

### java.util.concurrent
- `ReentrantLock` — explicit lock with more control than `synchronized` (tryLock, fairness)
- `CountDownLatch` — one or more threads wait until N other threads finish their tasks
- `CyclicBarrier` — N threads wait for each other to reach a common barrier point, then proceed together
- `Semaphore` — controls access to a shared resource with a fixed number of permits
- `ConcurrentHashMap` — thread-safe map; uses bucket-level locking (not full map lock like Hashtable)

---

## 11. Java Memory Model (JMM)

### Memory Areas (JVM)
- **Heap** — All objects and arrays live here. Shared across all threads. Managed by Garbage Collector. Divided into Young and Old generations.
- **Stack** — Each thread has its own stack. Stores method call frames, local variables, and references. Automatically managed (LIFO). `StackOverflowError` if too deep (e.g., infinite recursion).
- **Method Area / Metaspace** — Stores class metadata, static variables, constant pool. In Java 8+, PermGen was replaced by Metaspace (grows dynamically in native memory).
- **PC Register** — Each thread has its own Program Counter that tracks the current instruction being executed.
- **Native Method Stack** — Used for native (C/C++) method calls via JNI.

### Object Creation in Memory
1. Class is loaded into Method Area
2. `new` keyword allocates memory on Heap
3. Constructor initializes the object
4. Reference variable on Stack points to the Heap object

### Garbage Collection
- GC automatically reclaims heap memory of objects with no live references
- **Young Generation** — New objects are created here (Eden space). Minor GC runs frequently.
- **Old (Tenured) Generation** — Long-lived objects promoted from Young. Major GC runs less often.
- **GC Algorithms**:
  - *Serial GC* — single thread, for small apps
  - *Parallel GC* — multiple threads, throughput-focused
  - *G1 GC* — default since Java 9; balances throughput and latency; divides heap into regions
  - *ZGC / Shenandoah* — ultra-low pause time GCs (Java 11+)
- `System.gc()` — hints JVM to run GC, not guaranteed
- `finalize()` — called before GC (deprecated Java 9, removed Java 18) — don't rely on it

---

## 12. Java 8+ Features

### Lambda Expressions
- Anonymous function: `(params) -> expression` or `(params) -> { statements; }`
- Enables functional programming style — pass behavior as data
- Can only be used where a **functional interface** is expected
- Captures effectively final local variables from enclosing scope (closure)

### Functional Interfaces
- An interface with exactly **one abstract method** (SAM — Single Abstract Method)
- `@FunctionalInterface` annotation enforces this at compile time
- Built-in functional interfaces in `java.util.function`:
  - `Predicate<T>` — `test(T t)` → boolean — used for filtering
  - `Function<T, R>` — `apply(T t)` → R — used for transformation/mapping
  - `Consumer<T>` — `accept(T t)` → void — used for side effects (printing, saving)
  - `Supplier<T>` — `get()` → T — used for lazy value generation
  - `BiFunction<T, U, R>` — takes two inputs, returns one output
  - `UnaryOperator<T>` — `Function<T, T>` — input and output same type

### Stream API
- A pipeline of operations on a data source (collection, array, I/O)
- Streams are **lazy** — intermediate operations are not executed until a terminal operation is called
- Streams are **not reusable** — once consumed, a new stream must be created
- **Intermediate** (lazy, return Stream): `filter()`, `map()`, `flatMap()`, `sorted()`, `distinct()`, `limit()`, `peek()`
- **Terminal** (eager, trigger execution): `collect()`, `forEach()`, `reduce()`, `count()`, `findFirst()`, `anyMatch()`, `allMatch()`
- `flatMap()` — flattens nested collections: `Stream<List<T>>` → `Stream<T>`
- `reduce()` — combines elements into a single result: `stream.reduce(0, Integer::sum)`
- Parallel streams: `list.parallelStream()` — splits work across multiple threads (use carefully)

### Optional
- A container object that may or may not hold a non-null value. Designed to replace null checks and avoid `NullPointerException`.
- `Optional.of(value)` — throws NPE if value is null
- `Optional.ofNullable(value)` — safe, wraps null as empty Optional
- `Optional.empty()` — explicitly empty
- `orElse(default)` — returns value or default if empty
- `orElseGet(supplier)` — lazily computes default only if empty (preferred over `orElse` for expensive defaults)
- `orElseThrow()` — throws exception if empty
- `map()`, `filter()`, `ifPresent()` — chain operations safely

### Method References
- Shorthand for lambdas that just call an existing method
- `ClassName::staticMethod` — e.g., `Math::abs`
- `instance::instanceMethod` — e.g., `System.out::println`
- `ClassName::instanceMethod` — e.g., `String::toUpperCase`
- `ClassName::new` — constructor reference — e.g., `ArrayList::new`

### Other Java 8+ Additions
- **Default & Static methods** in interfaces — add new methods without breaking existing implementations
- **Date/Time API** (`java.time`): `LocalDate`, `LocalTime`, `LocalDateTime`, `ZonedDateTime`, `Duration`, `Period` — immutable, thread-safe, replaces legacy `Date`/`Calendar`
- **`var` keyword** (Java 10): local variable type inference — `var list = new ArrayList<String>()` — type inferred by compiler, still statically typed
- **Records** (Java 16): immutable data classes — `record Point(int x, int y) {}` — auto-generates constructor, getters, `equals`, `hashCode`, `toString`
- **Sealed classes** (Java 17): restrict which classes can extend/implement — `sealed class Shape permits Circle, Rectangle {}`
- **Text Blocks** (Java 15): multi-line string literals using `"""`

---

## 13. Serialization

- Converting an object's state to a byte stream for storage (file/DB) or transmission (network)
- **Deserialization** — reconstructing the object from the byte stream
- Class must implement `Serializable` (marker interface — no methods)
- `serialVersionUID` — a version ID used during deserialization to verify sender and receiver have compatible class definitions. If mismatched, `InvalidClassException` is thrown. Always declare it explicitly.
- `transient` fields are skipped during serialization (e.g., passwords, cached data)
- `static` fields are NOT serialized (they belong to the class, not the object)
- `ObjectOutputStream.writeObject()` — serializes | `ObjectInputStream.readObject()` — deserializes
- **Externalizable** — more control over serialization by implementing `writeExternal()` and `readExternal()`

---

## 14. Immutability

### What is Immutability?
An object is immutable if its state cannot be changed after construction. Every "modification" returns a new object instead.

### How to create an Immutable class
1. Declare class as `final` — prevents subclassing that could add mutable behavior
2. Make all fields `private final` — no reassignment after construction
3. No setters — no way to change state from outside
4. Initialize all fields via constructor
5. Return **deep copies** of mutable fields (e.g., `List`, `Date`, arrays) in getters — prevents external mutation of internal state

### Why Immutability matters
- **Thread-safe by default** — no synchronization needed since state never changes
- **Safe to share** — can be freely passed around without defensive copying
- **Hashcode stability** — safe to use as HashMap keys
- **Easier to reason about** — no unexpected state changes

- `String`, `Integer`, `Long`, `LocalDate`, `BigDecimal` are examples of immutable classes

---

## 15. Design Patterns (Commonly Asked)

### Creational — Object creation mechanisms
- **Singleton** — Ensures only one instance exists. Private constructor + static `getInstance()`. Thread-safe version uses double-checked locking or `enum`.
- **Factory Method** — Defines an interface for creating objects, but lets subclasses decide which class to instantiate. Decouples object creation from usage.
- **Abstract Factory** — Factory of factories. Creates families of related objects without specifying concrete classes.
- **Builder** — Constructs complex objects step by step. Separates construction from representation. (e.g., `StringBuilder`, Lombok `@Builder`, `AlertDialog.Builder`)
- **Prototype** — Creates new objects by cloning an existing object (`clone()`)

### Structural — Class/object composition
- **Decorator** — Wraps an object to add behavior dynamically without subclassing. (e.g., Java I/O: `BufferedReader` wraps `FileReader`)
- **Proxy** — A surrogate that controls access to another object. (e.g., Spring AOP, lazy loading, security checks)
- **Adapter** — Converts one interface to another that the client expects. Bridges incompatible interfaces.
- **Facade** — Provides a simplified interface to a complex subsystem.
- **Composite** — Treats individual objects and compositions uniformly (tree structures).

### Behavioral — Communication between objects
- **Observer** — Defines a one-to-many dependency. When one object changes state, all dependents are notified. (e.g., event listeners, pub-sub)
- **Strategy** — Defines a family of algorithms, encapsulates each, and makes them interchangeable at runtime. (e.g., sorting strategies, payment methods)
- **Template Method** — Defines the skeleton of an algorithm in a base class, deferring some steps to subclasses.
- **Command** — Encapsulates a request as an object, allowing undo/redo, queuing, and logging.
- **Iterator** — Provides a way to sequentially access elements without exposing the underlying structure.

---

## 16. SOLID Principles

| Principle | Meaning | Violation Example |
|---|---|---|
| **S** — Single Responsibility | A class should have only one reason to change | A `User` class that handles both user data AND email sending |
| **O** — Open/Closed | Open for extension, closed for modification | Adding new payment type by editing existing `PaymentProcessor` class |
| **L** — Liskov Substitution | Subclass should be substitutable for its parent without breaking behavior | `Square extends Rectangle` where setting width also changes height |
| **I** — Interface Segregation | Prefer small, specific interfaces over large fat ones | Forcing a `ReadOnlyFile` to implement a `write()` method |
| **D** — Dependency Inversion | Depend on abstractions, not concrete implementations | High-level module directly instantiating a low-level `MySQLDatabase` class |

---

## 17. Java Type System

### Primitive vs Wrapper Types
| Primitive | Wrapper | Default Value |
|---|---|---|
| `int` | `Integer` | 0 |
| `double` | `Double` | 0.0 |
| `boolean` | `Boolean` | false |
| `char` | `Character` | '\u0000' |
| `long` | `Long` | 0L |

- **Autoboxing** — automatic conversion from primitive to wrapper: `Integer i = 5;`
- **Unboxing** — automatic conversion from wrapper to primitive: `int x = new Integer(5);`
- Beware: unboxing a `null` wrapper throws `NullPointerException`
- Wrapper classes are immutable and cached for small values (`Integer` caches -128 to 127)

### Type Casting
- **Widening (implicit)** — smaller type to larger: `int → long → float → double` — no data loss
- **Narrowing (explicit)** — larger to smaller: `(int) 9.99` → `9` — possible data loss, requires cast
- **Upcasting** — subclass reference to superclass: always safe, implicit
- **Downcasting** — superclass reference to subclass: requires explicit cast, may throw `ClassCastException`; use `instanceof` to check first

---

## 18. Quick Interview Tips

- `==` vs `.equals()` — `==` is reference equality (same memory address); `.equals()` is content equality (overridden in String, Integer, etc.)
- `hashCode()` contract — if `a.equals(b)` is true, then `a.hashCode() == b.hashCode()` must also be true. Always override both together.
- `Comparable` vs `Comparator` — `Comparable` defines natural ordering inside the class (`compareTo`); `Comparator` is an external strategy for custom ordering (`compare`)
- `String` is immutable; `StringBuilder` is mutable and faster for concatenation in loops
- `ArrayList` is not thread-safe; use `CopyOnWriteArrayList` or `Collections.synchronizedList()`
- `HashMap` allows one `null` key and multiple `null` values; `Hashtable` allows neither
- `interface` fields are implicitly `public static final`; methods are implicitly `public abstract`
- `abstract` class can have constructors (called via `super()`); `interface` cannot
- Checked exceptions must be handled at compile time; unchecked exceptions are optional
- `finally` does not run only if `System.exit()` is called or JVM crashes
- `static` methods cannot be overridden (they are hidden, not overridden) — no runtime polymorphism
- `String s = "a" + "b"` — compiler optimizes to `"ab"` at compile time (constant folding)
- `int` is 32-bit signed; range is -2,147,483,648 to 2,147,483,647. Use `long` for larger values.
- `Collections.unmodifiableList()` — returns a read-only view; `List.of()` (Java 9+) — truly immutable list
