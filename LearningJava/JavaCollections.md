# Java Collections Framework

---

## Collection Hierarchy

```
Iterable
└── Collection
    ├── List
    │   ├── ArrayList
    │   ├── LinkedList
    │   ├── Vector
    │   └── Stack
    ├── Set
    │   ├── HashSet
    │   ├── LinkedHashSet
    │   └── TreeSet
    └── Queue
        ├── PriorityQueue
        ├── ArrayDeque
        └── LinkedList (also implements Deque)

Map (separate hierarchy)
    ├── HashMap
    ├── LinkedHashMap
    ├── TreeMap
    ├── Hashtable
    └── ConcurrentHashMap

Concurrent Collections
    ├── CopyOnWriteArrayList
    ├── ConcurrentHashMap
    ├── BlockingQueue (ArrayBlockingQueue, LinkedBlockingQueue)
    └── ConcurrentLinkedQueue
```

---

## 1. ArrayList

- Backed by a resizable array
- O(1) random access, O(n) insert/delete in middle
- Grows by 50% when capacity exceeded
- Not thread-safe

```java
import java.util.*;

List<String> list = new ArrayList<>();
list.add("Apple");
list.add("Banana");
list.add(1, "Cherry");          // insert at index
list.remove("Banana");          // remove by value
list.remove(0);                 // remove by index
String val = list.get(0);       // O(1)
list.set(0, "Mango");           // update
Collections.sort(list);         // sort in-place
list.sort(Comparator.reverseOrder());

// Iterate
for (String s : list) System.out.println(s);
list.forEach(System.out::println);

// Convert array to list (fixed-size)
List<String> fixed = Arrays.asList("A", "B", "C");

// Mutable list from array
List<String> mutable = new ArrayList<>(Arrays.asList("A", "B"));

// Sublist view (backed by original)
List<String> sub = list.subList(0, 2);
```

Use when: frequent random access, infrequent insertions/deletions in the middle.

---

## 2. LinkedList

- Doubly linked list; implements both `List` and `Deque`
- O(1) insert/delete at head/tail, O(n) random access
- Higher memory overhead (node pointers)

```java
LinkedList<Integer> ll = new LinkedList<>();
ll.add(10);
ll.addFirst(5);         // O(1)
ll.addLast(20);         // O(1)
ll.removeFirst();       // O(1)
ll.removeLast();        // O(1)
ll.get(1);              // O(n) — traversal needed
ll.peek();              // view head without removing
ll.poll();              // remove head (returns null if empty)

// Use as Stack
ll.push(100);           // addFirst
ll.pop();               // removeFirst

// Use as Queue
ll.offer(200);          // addLast
ll.poll();              // removeFirst
```

Use when: frequent insertions/deletions at both ends, implementing stacks/queues.

---

## 3. Vector & Stack

- `Vector` – synchronized ArrayList; legacy class
- `Stack` – extends Vector; LIFO structure
- Prefer `ArrayDeque` over `Stack` in modern code

```java
Stack<Integer> stack = new Stack<>();
stack.push(1);
stack.push(2);
stack.push(3);
int top = stack.peek();     // view top: 3
int popped = stack.pop();   // remove top: 3
boolean empty = stack.isEmpty();
int pos = stack.search(1);  // 1-based position from top

// Modern alternative
Deque<Integer> modernStack = new ArrayDeque<>();
modernStack.push(1);
modernStack.pop();
```

---

## 4. ArrayDeque

- Resizable array-based double-ended queue
- Faster than `LinkedList` for stack/queue operations
- Does not allow `null` elements

```java
Deque<String> deque = new ArrayDeque<>();

// As Queue (FIFO)
deque.offer("A");       // enqueue
deque.offer("B");
deque.poll();           // dequeue: "A"
deque.peek();           // view front: "B"

// As Stack (LIFO)
deque.push("X");        // addFirst
deque.push("Y");
deque.pop();            // removeFirst: "Y"

// Deque operations
deque.offerFirst("Z");
deque.offerLast("W");
deque.pollFirst();
deque.pollLast();
```

Use when: need a stack or queue — preferred over `Stack` and `LinkedList`.

---

## 5. PriorityQueue

- Min-heap by default (smallest element at head)
- O(log n) insert and remove, O(1) peek
- Does not allow `null`; not thread-safe

```java
PriorityQueue<Integer> minHeap = new PriorityQueue<>();
minHeap.offer(5);
minHeap.offer(1);
minHeap.offer(3);
minHeap.peek();     // 1 (min)
minHeap.poll();     // removes 1

// Max-heap
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
maxHeap.offer(5);
maxHeap.offer(1);
maxHeap.poll();     // 5 (max)

// Custom object ordering
PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]); // sort by second element
pq.offer(new int[]{1, 3});
pq.offer(new int[]{2, 1});
pq.poll();  // [2, 1]

// Drain in order
while (!minHeap.isEmpty()) System.out.println(minHeap.poll());
```

Use when: scheduling tasks by priority, Dijkstra's algorithm, top-K problems.

---

## 6. HashSet

- Backed by `HashMap`; stores unique elements
- O(1) average add/remove/contains
- No guaranteed order; allows one `null`

```java
Set<String> set = new HashSet<>();
set.add("Apple");
set.add("Banana");
set.add("Apple");       // duplicate — ignored
set.contains("Apple");  // true
set.remove("Banana");
set.size();             // 1

// Set operations
Set<Integer> a = new HashSet<>(Arrays.asList(1, 2, 3));
Set<Integer> b = new HashSet<>(Arrays.asList(2, 3, 4));

Set<Integer> union = new HashSet<>(a);
union.addAll(b);        // {1, 2, 3, 4}

Set<Integer> intersection = new HashSet<>(a);
intersection.retainAll(b);  // {2, 3}

Set<Integer> difference = new HashSet<>(a);
difference.removeAll(b);    // {1}
```

Use when: fast lookup, deduplication, membership testing.

---

## 7. LinkedHashSet

- Extends `HashSet`; maintains insertion order
- Slightly slower than `HashSet` due to linked list overhead

```java
Set<String> lhs = new LinkedHashSet<>();
lhs.add("Banana");
lhs.add("Apple");
lhs.add("Cherry");
lhs.add("Apple");   // duplicate ignored

// Iteration preserves insertion order: Banana, Apple, Cherry
lhs.forEach(System.out::println);
```

Use when: need uniqueness + predictable iteration order.

---

## 8. TreeSet

- Backed by `TreeMap` (Red-Black tree)
- Elements sorted in natural order or by `Comparator`
- O(log n) add/remove/contains; does not allow `null`

```java
TreeSet<Integer> ts = new TreeSet<>();
ts.add(5); ts.add(1); ts.add(3); ts.add(2);

ts.first();         // 1
ts.last();          // 5
ts.floor(3);        // 3 (largest ≤ 3)
ts.ceiling(3);      // 3 (smallest ≥ 3)
ts.lower(3);        // 2 (largest < 3)
ts.higher(3);       // 5 (smallest > 3)
ts.headSet(3);      // {1, 2} (exclusive)
ts.tailSet(3);      // {3, 5} (inclusive)
ts.subSet(2, 5);    // {2, 3} (from inclusive, to exclusive)

// Custom comparator (reverse order)
TreeSet<String> rev = new TreeSet<>(Comparator.reverseOrder());
rev.add("Banana"); rev.add("Apple"); rev.add("Cherry");
// Iteration: Cherry, Banana, Apple
```

Use when: sorted unique elements, range queries, navigable set operations.

---

## 9. HashMap

- Array of buckets + linked list / Red-Black tree (Java 8+)
- O(1) average get/put; O(n) worst case (hash collision)
- Default capacity: 16, load factor: 0.75
- Treeifies bucket when chain length > 8
- Allows one `null` key, multiple `null` values; not thread-safe

```java
Map<String, Integer> map = new HashMap<>();
map.put("Alice", 90);
map.put("Bob", 85);
map.put("Alice", 95);       // overwrites
map.get("Alice");           // 95
map.getOrDefault("Eve", 0); // 0
map.containsKey("Bob");     // true
map.containsValue(85);      // true
map.remove("Bob");

// Conditional operations
map.putIfAbsent("Charlie", 88);
map.computeIfAbsent("Dave", k -> k.length() * 10);
map.computeIfPresent("Alice", (k, v) -> v + 5);
map.merge("Alice", 10, Integer::sum); // Alice = 95 + 10 = 105

// Iterate
for (Map.Entry<String, Integer> e : map.entrySet())
    System.out.println(e.getKey() + " -> " + e.getValue());

map.forEach((k, v) -> System.out.println(k + ": " + v));

// Frequency count pattern
String[] words = {"a", "b", "a", "c", "b", "a"};
Map<String, Integer> freq = new HashMap<>();
for (String w : words) freq.merge(w, 1, Integer::sum);
```

Use when: fast key-value lookup, caching, frequency counting.

---

## 10. LinkedHashMap

- Extends `HashMap`; maintains insertion order (default) or access order
- Slightly more memory than `HashMap`

```java
// Insertion-order
Map<String, Integer> lhm = new LinkedHashMap<>();
lhm.put("C", 3); lhm.put("A", 1); lhm.put("B", 2);
// Iteration: C, A, B

// Access-order (LRU Cache pattern)
Map<Integer, String> lruCache = new LinkedHashMap<>(16, 0.75f, true) {
    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, String> eldest) {
        return size() > 3; // max capacity 3
    }
};
lruCache.put(1, "One");
lruCache.put(2, "Two");
lruCache.put(3, "Three");
lruCache.get(1);        // access 1 → moves to end
lruCache.put(4, "Four");// evicts 2 (least recently used)
```

Use when: need insertion-order iteration, implementing LRU cache.

---

## 11. TreeMap

- Red-Black tree; keys sorted in natural order or by `Comparator`
- O(log n) get/put/remove; does not allow `null` keys

```java
TreeMap<String, Integer> tm = new TreeMap<>();
tm.put("Banana", 2); tm.put("Apple", 1); tm.put("Cherry", 3);

tm.firstKey();              // Apple
tm.lastKey();               // Cherry
tm.floorKey("Blueberry");   // Banana
tm.ceilingKey("Blueberry"); // Cherry
tm.headMap("Cherry");       // {Apple=1, Banana=2}
tm.tailMap("Banana");       // {Banana=2, Cherry=3}
tm.subMap("Apple", "Cherry"); // {Apple=1, Banana=2}
tm.descendingMap();         // reverse order view

// Sorted frequency map
TreeMap<Character, Integer> charFreq = new TreeMap<>();
for (char c : "hello".toCharArray())
    charFreq.merge(c, 1, Integer::sum);
// {e=1, h=1, l=2, o=1}
```

Use when: sorted key-value pairs, range queries on keys, ordered maps.

---

## 12. Hashtable

- Legacy synchronized map; does not allow `null` keys or values
- Prefer `ConcurrentHashMap` for thread-safe operations

```java
Hashtable<String, Integer> ht = new Hashtable<>();
ht.put("A", 1);
ht.get("A");        // 1
ht.remove("A");
// ht.put(null, 1); // NullPointerException
```

---

## 13. ConcurrentHashMap

- Thread-safe; uses CAS + segment locking (Java 8+)
- Does not allow `null` keys or values
- Fail-safe iterator (works on snapshot)

```java
import java.util.concurrent.*;

ConcurrentHashMap<String, Integer> chm = new ConcurrentHashMap<>();
chm.put("A", 1);
chm.putIfAbsent("B", 2);
chm.computeIfAbsent("C", k -> k.length());

// Atomic increment
chm.merge("A", 1, Integer::sum);

// Bulk operations (parallel)
chm.forEach(1, (k, v) -> System.out.println(k + "=" + v));
long count = chm.reduceValues(1, v -> (long) v, Long::sum);
```

Use when: concurrent read/write access to a shared map.

---

## 14. CopyOnWriteArrayList

- Thread-safe list; creates a new copy of array on every write
- Fail-safe iterator; expensive writes, cheap reads

```java
import java.util.concurrent.*;

CopyOnWriteArrayList<String> cowList = new CopyOnWriteArrayList<>();
cowList.add("A");
cowList.add("B");

// Safe to iterate while another thread modifies
for (String s : cowList) {
    cowList.add("C"); // no ConcurrentModificationException
}
```

Use when: read-heavy, write-rare concurrent access (e.g., listener lists).

---

## 15. BlockingQueue

- Thread-safe queue for producer-consumer pattern
- Blocks on `put()` when full, blocks on `take()` when empty

```java
import java.util.concurrent.*;

BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);

// Producer thread
new Thread(() -> {
    try {
        for (int i = 0; i < 5; i++) queue.put(i);
    } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
}).start();

// Consumer thread
new Thread(() -> {
    try {
        while (true) System.out.println(queue.take());
    } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
}).start();

// LinkedBlockingQueue — optionally bounded
BlockingQueue<String> lbq = new LinkedBlockingQueue<>(100);

// PriorityBlockingQueue — unbounded, priority-ordered
BlockingQueue<Integer> pbq = new PriorityBlockingQueue<>();
```

Use when: producer-consumer, task queues, thread pool work queues.

---

## 16. Collections Utility Class

```java
List<Integer> nums = new ArrayList<>(Arrays.asList(3, 1, 4, 1, 5, 9));

Collections.sort(nums);                         // [1, 1, 3, 4, 5, 9]
Collections.sort(nums, Comparator.reverseOrder());
Collections.reverse(nums);
Collections.shuffle(nums);
Collections.min(nums);
Collections.max(nums);
Collections.frequency(nums, 1);                 // 2
Collections.binarySearch(nums, 3);              // index (list must be sorted)
Collections.fill(nums, 0);                      // fill all with 0
Collections.nCopies(5, "X");                    // [X, X, X, X, X]
Collections.swap(nums, 0, 1);
Collections.disjoint(nums, List.of(10, 20));    // true if no common elements

// Unmodifiable wrappers
List<String> immutable = Collections.unmodifiableList(new ArrayList<>(List.of("A")));

// Synchronized wrappers (prefer concurrent classes instead)
List<String> syncList = Collections.synchronizedList(new ArrayList<>());
```

---

## 17. Iterators & Fail-fast vs Fail-safe

```java
List<String> list = new ArrayList<>(Arrays.asList("A", "B", "C"));

// Iterator — safe removal during iteration
Iterator<String> it = list.iterator();
while (it.hasNext()) {
    String s = it.next();
    if (s.equals("B")) it.remove(); // safe
}

// ListIterator — bidirectional
ListIterator<String> lit = list.listIterator();
while (lit.hasNext()) {
    String s = lit.next();
    lit.set(s.toLowerCase()); // modify in-place
}

// Fail-fast: throws ConcurrentModificationException
try {
    for (String s : list) list.add("X"); // modcount mismatch
} catch (ConcurrentModificationException e) {
    System.out.println("Fail-fast triggered");
}

// Fail-safe: CopyOnWriteArrayList iterates on snapshot
CopyOnWriteArrayList<String> safe = new CopyOnWriteArrayList<>(List.of("A", "B"));
for (String s : safe) safe.add("C"); // no exception
```

---

## 18. Sorting & Comparators

```java
record Person(String name, int age) {}

List<Person> people = new ArrayList<>(List.of(
    new Person("Alice", 30),
    new Person("Bob", 25),
    new Person("Charlie", 30)
));

// Sort by age, then name
people.sort(Comparator.comparingInt(Person::age)
    .thenComparing(Person::name));

// Reverse sort
people.sort(Comparator.comparingInt(Person::age).reversed());

// Null-safe comparator
List<String> withNulls = new ArrayList<>(Arrays.asList("B", null, "A"));
withNulls.sort(Comparator.nullsFirst(Comparator.naturalOrder()));
```

---

## 19. Stream API with Collections

```java
List<Integer> nums = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

// Filter + map + collect
List<Integer> evens = nums.stream()
    .filter(n -> n % 2 == 0)
    .map(n -> n * n)
    .collect(Collectors.toList());

// Group by
Map<Boolean, List<Integer>> grouped = nums.stream()
    .collect(Collectors.partitioningBy(n -> n % 2 == 0));

// Frequency map
List<String> words = List.of("apple", "banana", "apple", "cherry");
Map<String, Long> freq = words.stream()
    .collect(Collectors.groupingBy(w -> w, Collectors.counting()));

// Flat map
List<List<Integer>> nested = List.of(List.of(1, 2), List.of(3, 4));
List<Integer> flat = nested.stream()
    .flatMap(Collection::stream)
    .collect(Collectors.toList());

// Reduce
int sum = nums.stream().reduce(0, Integer::sum);

// Collectors.toMap
Map<String, Integer> nameLen = words.stream()
    .distinct()
    .collect(Collectors.toMap(w -> w, String::length));
```

---

## 20. Common Interview Patterns

### Top K Elements
```java
// Top K frequent elements
int[] nums = {1, 1, 1, 2, 2, 3};
Map<Integer, Integer> freq = new HashMap<>();
for (int n : nums) freq.merge(n, 1, Integer::sum);

PriorityQueue<Integer> minHeap = new PriorityQueue<>(Comparator.comparingInt(freq::get));
for (int key : freq.keySet()) {
    minHeap.offer(key);
    if (minHeap.size() > 2) minHeap.poll(); // keep top 2
}
// minHeap contains top 2 frequent elements
```

### Sliding Window with Deque
```java
// Max in every window of size k
int[] arr = {1, 3, -1, -3, 5, 3, 6, 7};
int k = 3;
Deque<Integer> dq = new ArrayDeque<>(); // stores indices
List<Integer> result = new ArrayList<>();

for (int i = 0; i < arr.length; i++) {
    if (!dq.isEmpty() && dq.peekFirst() < i - k + 1) dq.pollFirst();
    while (!dq.isEmpty() && arr[dq.peekLast()] < arr[i]) dq.pollLast();
    dq.offerLast(i);
    if (i >= k - 1) result.add(arr[dq.peekFirst()]);
}
// [3, 3, 5, 5, 6, 7]
```

### Two-Sum with HashMap
```java
int[] twoSum(int[] nums, int target) {
    Map<Integer, Integer> seen = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
        int complement = target - nums[i];
        if (seen.containsKey(complement)) return new int[]{seen.get(complement), i};
        seen.put(nums[i], i);
    }
    return new int[]{};
}
```

### Graph with Adjacency List
```java
Map<Integer, List<Integer>> graph = new HashMap<>();
graph.computeIfAbsent(1, k -> new ArrayList<>()).add(2);
graph.computeIfAbsent(1, k -> new ArrayList<>()).add(3);
graph.computeIfAbsent(2, k -> new ArrayList<>()).add(4);

// BFS
Queue<Integer> queue = new ArrayDeque<>();
Set<Integer> visited = new HashSet<>();
queue.offer(1);
visited.add(1);
while (!queue.isEmpty()) {
    int node = queue.poll();
    for (int neighbor : graph.getOrDefault(node, List.of())) {
        if (visited.add(neighbor)) queue.offer(neighbor);
    }
}
```

---

## 21. Choosing the Right Collection

| Need | Use |
|------|-----|
| Fast random access | `ArrayList` |
| Fast insert/delete at ends | `ArrayDeque` or `LinkedList` |
| LIFO stack | `ArrayDeque` |
| FIFO queue | `ArrayDeque` |
| Priority-based processing | `PriorityQueue` |
| Unique elements, fast lookup | `HashSet` |
| Unique elements, insertion order | `LinkedHashSet` |
| Unique elements, sorted | `TreeSet` |
| Key-value, fast lookup | `HashMap` |
| Key-value, insertion order | `LinkedHashMap` |
| Key-value, sorted keys | `TreeMap` |
| LRU Cache | `LinkedHashMap` (access-order) |
| Thread-safe map | `ConcurrentHashMap` |
| Thread-safe list (read-heavy) | `CopyOnWriteArrayList` |
| Producer-consumer | `BlockingQueue` |

---

## 22. Time Complexity Summary

| Collection | Add | Remove | Get/Contains | Notes |
|------------|-----|--------|--------------|-------|
| `ArrayList` | O(1) amortized | O(n) | O(1) | O(n) for insert at index |
| `LinkedList` | O(1) at ends | O(1) at ends | O(n) | O(n) for index access |
| `ArrayDeque` | O(1) amortized | O(1) | O(1) peek | — |
| `PriorityQueue` | O(log n) | O(log n) | O(1) peek | O(n) contains |
| `HashSet` | O(1) avg | O(1) avg | O(1) avg | O(n) worst |
| `LinkedHashSet` | O(1) avg | O(1) avg | O(1) avg | Maintains order |
| `TreeSet` | O(log n) | O(log n) | O(log n) | Sorted |
| `HashMap` | O(1) avg | O(1) avg | O(1) avg | O(n) worst |
| `LinkedHashMap` | O(1) avg | O(1) avg | O(1) avg | Maintains order |
| `TreeMap` | O(log n) | O(log n) | O(log n) | Sorted keys |
| `ConcurrentHashMap` | O(1) avg | O(1) avg | O(1) avg | Thread-safe |

---

## 23. Key Internal Details

### HashMap Internals
- Array of `Node<K,V>[]` (buckets), default size 16
- Hash: `(key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16)`
- Bucket index: `hash & (capacity - 1)`
- Collision: chaining (linked list → tree when chain > 8, untreeify when < 6)
- Resize: doubles capacity when `size > capacity * loadFactor`

### equals() and hashCode() Contract
```java
// Must override both for correct HashMap/HashSet behavior
class Point {
    int x, y;
    Point(int x, int y) { this.x = x; this.y = y; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point p)) return false;
        return x == p.x && y == p.y;
    }

    @Override
    public int hashCode() { return Objects.hash(x, y); }
}
```

### Comparable vs Comparator
```java
// Comparable — natural ordering (implement in the class)
class Student implements Comparable<Student> {
    String name; int grade;
    Student(String name, int grade) { this.name = name; this.grade = grade; }

    @Override
    public int compareTo(Student other) { return this.grade - other.grade; }
}

// Comparator — external ordering
Comparator<Student> byName = Comparator.comparing(s -> s.name);
List<Student> students = new ArrayList<>(List.of(new Student("Bob", 85), new Student("Alice", 90)));
students.sort(byName); // Alice, Bob
```

---

## 24. Immutable Collections (Java 9+)

```java
// Unmodifiable — throws UnsupportedOperationException on mutation
List<String> immList = List.of("A", "B", "C");
Set<Integer> immSet = Set.of(1, 2, 3);
Map<String, Integer> immMap = Map.of("A", 1, "B", 2);
Map<String, Integer> immMapEntries = Map.ofEntries(
    Map.entry("Alice", 90),
    Map.entry("Bob", 85)
);

// Copy (independent immutable copy)
List<String> copy = List.copyOf(new ArrayList<>(List.of("X", "Y")));
```

---
