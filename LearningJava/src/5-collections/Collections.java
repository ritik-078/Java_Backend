import java.time.DayOfWeek;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;


public class Collections {
    public static void main(String[] args) {
        demonstrateArrayList();
        demonstrateLinkedList();
        demonstrateVector();
        demonstrateStackLegacy();
        demonstrateCopyOnWriteArrayList();
        demonstrateMap();
        demonstrateSet();
        demonstrateStack();
        demonstrateQueue();
        demonstrateDeque();
        demonstratePriorityQueue();
    }

    /* ARRAYLIST - Dynamic array, O(1) amortized append, O(n) insert/delete
     * INTERNAL WORKING:
     * 1. Backed by Object[] array, default capacity 10
     * 2. When full, creates new array 1.5x size and copies elements
     * 3. Random access via index calculation: array[index]
     * 4. Insert/delete shifts elements: System.arraycopy()
     * USE CASES:
     * - Frequent random access by index
     * - Iteration over elements
     * - Rare insertions/deletions in middle
     * - Building lists with unknown size
     */
    public static void demonstrateArrayList() {
        System.out.println("\n=== ARRAYLIST ===");
        List<Integer> list = new ArrayList<>();

        // Add operations - O(1) amortized
        list.add(10);      // O(1) amortized
        list.add(20);
        list.add(30);
        System.out.println("After adds: " + list);

        // Get operation - O(1)
        System.out.println("Get index 1: " + list.get(1));

        // Insert at index - O(n)
        list.add(1, 15);   // O(n) - shifts elements
        System.out.println("After insert at 1: " + list);

        // Remove - O(n)
        list.remove(Integer.valueOf(15)); // O(n)
        System.out.println("After remove: " + list);

        // Contains - O(n)
        System.out.println("Contains 20: " + list.contains(20));

        // Sort - O(n log n)
        Collections.sort(list);
        System.out.println("Sorted: " + list);

        // Reverse - O(n)
        Collections.reverse(list);
        System.out.println("Reversed: " + list);

        // Size and isEmpty - O(1)
        System.out.println("Size: " + list.size() + ", Empty: " + list.isEmpty());

        // Fixed Size ArrayList
        List<Integer> asArray = Arrays.asList(1, 2, 3, 4, 5);
        // asArray.add(1); -> throws exception
        // list.set(1,21); -> can change elements

        // Immutable ArrayList
        List<Integer> listOf = List.of(1, 2, 3, 4);

        // List to Array
        Integer[] arr = list.toArray(new Integer[0]);
        System.out.println(arr);
    }

    /* LINKEDLIST - Doubly linked list, O(1) add/remove at ends, O(n) random access
     * INTERNAL WORKING:
     * 1. Each node has: data, next pointer, prev pointer
     * 2. Maintains first and last node references
     * 3. Traversal required for index access: O(n/2) optimized
     * 4. No resizing needed, allocates per node
     * USE CASES:
     * - Frequent insertions/deletions at ends (queue/deque)
     * - Implementing stacks, queues
     * - When memory fragmentation is acceptable
     * - Rare random access needed
     */
    public static void demonstrateLinkedList() {
        System.out.println("\n=== LINKEDLIST ===");
        LinkedList<String> list = new LinkedList<>();

        // Add operations - O(1) at ends
        list.add("A");           // O(1) append
        list.add("B");
        list.add("C");
        System.out.println("After adds: " + list);

        // Add at specific index - O(n)
        list.add(1, "X");        // O(n)
        System.out.println("After insert at 1: " + list);

        // Add first/last - O(1)
        list.addFirst("START");  // O(1)
        list.addLast("END");     // O(1)
        System.out.println("After addFirst/Last: " + list);

        // Get operations - O(n) random access
        System.out.println("Get first: " + list.getFirst()); // O(1)
        System.out.println("Get last: " + list.getLast());   // O(1)
        System.out.println("Get index 2: " + list.get(2));   // O(n)

        // Remove operations - O(1) at ends
        list.removeFirst();      // O(1)
        list.removeLast();       // O(1)
        System.out.println("After removeFirst/Last: " + list);

        // Remove at index - O(n)
        list.remove(1);          // O(n)
        System.out.println("After remove index 1: " + list);

        // Contains - O(n)
        System.out.println("Contains 'B': " + list.contains("B"));

        // Size - O(1)
        System.out.println("Size: " + list.size());
    }

    /* VECTOR - Legacy Class, synchronized dynamic array, O(1) amortized append, O(n) insert/delete
     * INTERNAL WORKING:
     * 1. Same as ArrayList but all methods synchronized
     * 2. Doubles capacity when full (vs 1.5x for ArrayList)
     * 3. Thread-safe but slower due to synchronization overhead
     * USE CASES:
     * - Legacy code compatibility
     * - Simple thread-safe list (prefer CopyOnWriteArrayList or Collections.synchronizedList)
     * - Generally avoid in new code
     */
    public static void demonstrateVector() {
        System.out.println("\n=== VECTOR (Legacy) ===");
        Vector<Integer> vector = new Vector<>();

        // Add operations - O(1) amortized
        vector.add(10);
        vector.add(20);
        vector.add(30);
        System.out.println("After adds: " + vector);

        // Get operation - O(1)
        System.out.println("Get index 1: " + vector.get(1));

        // Insert at index - O(n)
        vector.add(1, 15);
        System.out.println("After insert at 1: " + vector);

        // Remove - O(n)
        vector.remove(Integer.valueOf(15));
        System.out.println("After remove: " + vector);

        // Contains - O(n)
        System.out.println("Contains 20: " + vector.contains(20));

        // Size and isEmpty - O(1)
        System.out.println("Size: " + vector.size() + ", Empty: " + vector.isEmpty());

        // elementAt - O(1)
        System.out.println("Element at 0: " + vector.elementAt(0));

        // firstElement/lastElement - O(1)
        System.out.println("First: " + vector.firstElement() + ", Last: " + vector.lastElement());
    }

    /* STACK - Legacy Class, extends Vector, LIFO, O(1) push/pop/peek
     * INTERNAL WORKING:
     * 1. Extends Vector, inherits synchronized array
     * 2. Push adds to end, pop removes from end
     * 3. All operations synchronized (thread-safe but slow)
     * USE CASES:
     * - Legacy code only
     * - Prefer Deque<Integer> stack = new ArrayDeque<>() for new code
     * - Expression evaluation, backtracking, undo operations
     */
    public static void demonstrateStackLegacy() {
        System.out.println("\n=== STACK (Legacy) ===");
        Stack<Integer> stack = new Stack<>();

        // Push - O(1)
        stack.push(10);
        stack.push(20);
        stack.push(30);
        System.out.println("After pushes: " + stack);

        // Peek - O(1)
        System.out.println("Peek: " + stack.peek());

        // Pop - O(1)
        System.out.println("Pop: " + stack.pop());
        System.out.println("After pop: " + stack);

        // Search - O(n)
        System.out.println("Search 10: " + stack.search(10));

        // Size and isEmpty - O(1)
        System.out.println("Size: " + stack.size() + ", Empty: " + stack.isEmpty());
    }

    /* COPYONWRITEARRAYLIST - Thread-safe, O(n) write, O(1) read, creates copy on modification
     * INTERNAL WORKING:
     * 1. Backed by volatile Object[] array
     * 2. Every write creates new array copy with modification
     * 3. Reads don't need synchronization (lock-free)
     * 4. Iterator uses snapshot of array at creation time
     * USE CASES:
     * - Read-heavy, write-rare scenarios
     * - Event listener lists
     * - Observer pattern implementations
     * - Concurrent iteration without ConcurrentModificationException
     */
    public static void demonstrateCopyOnWriteArrayList() {
        System.out.println("\n=== COPYONWRITEARRAYLIST ===");
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();

        // Add operations - O(n) creates new copy
        list.add(10);
        list.add(20);
        list.add(30);
        System.out.println("After adds: " + list);

        // Get operation - O(1)
        System.out.println("Get index 1: " + list.get(1));

        // Insert at index - O(n)
        list.add(1, 15);
        System.out.println("After insert at 1: " + list);

        // Remove - O(n)
        list.remove(Integer.valueOf(15));
        System.out.println("After remove: " + list);

        // Contains - O(n)
        System.out.println("Contains 20: " + list.contains(20));

        // Size and isEmpty - O(1)
        System.out.println("Size: " + list.size() + ", Empty: " + list.isEmpty());
    }

    // MAP IMPLEMENTATION AND UTILITIES
    public static void demonstrateMap() {
        System.out.println("\n=== MAP OPERATIONS ===");

        /* INTERNAL WORKING OF HASHMAP:
         * 1. STRUCTURE: Array of buckets (Node<K,V>[] table), default size 16
         * 2. HASH FUNCTION: hashCode() -> hash(hashCode) -> index = hash & (n-1)
         * 3. COLLISION HANDLING:
         *    - Java 8+: Linked List (<=8 nodes) -> Red-Black Tree (>8 nodes)
         *    - Improves worst case from O(n) to O(log n)
         * 4. LOAD FACTOR: Default 0.75 (75% full triggers resize)
         * 5. RESIZE: Doubles capacity (16->32->64...), rehashes all entries - O(n)
         * 6. PUT OPERATION:
         *    - Calculate hash and bucket index
         *    - If empty, insert new node
         *    - If collision, traverse list/tree, update if key exists, else add
         * 7. GET OPERATION:
         *    - Calculate hash and bucket index
         *    - Traverse list/tree comparing keys using equals()
         * 8. TIME COMPLEXITY:
         *    - Average: O(1) for get/put/remove
         *    - Worst: O(log n) with good hash, O(n) with poor hash
         * 9. REQUIREMENTS:
         *    - Keys must override hashCode() and equals()
         *    - Immutable keys recommended (String, Integer, etc.)
         */

        // HashMap - O(1) average get/put/remove, unordered
        Map<String, Integer> map = new HashMap<>();
        map.put("apple", 5);     // O(1) average
        map.put("banana", 3);
        map.put("cherry", 7);
        System.out.println("HashMap: " + map);

        System.out.println("Get: " + map.get("apple"));        // O(1) average
        System.out.println("Contains key: " + map.containsKey("apple")); // O(1) average
        System.out.println("Size: " + map.size());             // O(1)

        // getOrDefault - O(1) average
        System.out.println("Get or default: " + map.getOrDefault("grape", 0));

        // putIfAbsent - O(1) average
        map.putIfAbsent("grape", 2);
        System.out.println("After putIfAbsent: " + map);

        // Frequency counter - O(n)
        String text = "hello";
        Map<Character, Integer> freq = new HashMap<>();
        for (char c : text.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }
        System.out.println("Frequency map: " + freq);

        // Iteration - O(n)
        System.out.println("Keys: " + map.keySet());
        System.out.println("Values: " + map.values());
        System.out.println("Entries: " + map.entrySet());

        // Loop through map - O(n)
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }

        // Remove - O(1) average
        map.remove("banana");
        System.out.println("After remove: " + map);

        // Clear - O(n)
        map.clear();
        System.out.println("Empty: " + map.isEmpty());


        // LinkedHashMap - O(1) operations, maintains insertion order
        Map<String, Integer> linkedMap = new LinkedHashMap<>();
        linkedMap.put("first", 1);  // O(1)
        linkedMap.put("second", 2);
        linkedMap.put("third", 3);
        System.out.println("LinkedHashMap (ordered): " + linkedMap);


        // WeakHashMap - O(1) average, allows null keys/values, internal structure similar to HashMap
        Map<String, Integer> weakMap = new WeakHashMap<>();
        weakMap.put("key1", 1);
        System.out.println("WeakHashMap: " + weakMap);
        weakMap.put(null, 1);
        System.out.println("WeakHashMap with null: " + weakMap);
        weakMap.put("key2", null);
        System.out.println("WeakHashMap with null: " + weakMap);
        weakMap.put(null, null);
        System.out.println("WeakHashMap with all nulls: " + weakMap);


        // Identity HashMap - O(1) average, uses == for key comparison, not equals()
        // Uses Object hash Code only - memory
        Map<String, Integer> identityMap = new IdentityHashMap<>();
        String key1 = new String("key1");
        String key2 = new String("key1");
        // So two equal string will have different object hash, hence treated as unique keys
        identityMap.put(key1, 1);
        identityMap.put(key2, 2);
        System.out.println("IdentityHashMap: " + identityMap);


        // TreeMap <- SortedMap(I) <- Map(I)
        // TreeMap <- NavigableMap(I) <- SortedMap(I) <- Map(I)
        // TreeMap - O(log n) get/put/remove, sorted by keys
        /* INTERNAL IMPLEMENTATION OF TREEMAP:
         * 1. STRUCTURE: Red-Black Tree (self-balancing BST)
         * 2. NODE: Entry<K,V> with key, value, left, right, parent, color (RED/BLACK)
         * 3. ORDERING: Natural ordering (Comparable) or custom Comparator
         * 4. BALANCING: Maintains O(log n) height through rotations and recoloring
         * 5. PUT: BST insert + rebalance (rotations/recoloring) - O(log n)
         * 6. GET: BST search using compareTo() - O(log n)
         * 7. REMOVE: BST delete + rebalance - O(log n)
         * 8. NO NULL KEYS: Throws NPE (needs comparison)
         * 9. NULL VALUES: Allowed
         * 10. SORTED OPERATIONS: firstKey(), lastKey(), subMap() - O(log n)
         * 11. NAVIGABLE OPERATIONS: lowerKey(), floorKey(), ceilingKey(), higherKey(),
         *     descendingMap(), pollFirstEntry(), pollLastEntry() - O(log n)
         *     - lowerKey(k): greatest key < k
         *     - floorKey(k): greatest key <= k
         *     - ceilingKey(k): smallest key >= k
         *     - higherKey(k): smallest key > k
         */
        SortedMap<String, Integer> treeMap = new TreeMap<>();
        treeMap.put("zebra", 1);   // O(log n)
        treeMap.put("apple", 2);
        treeMap.put("mango", 3);
        treeMap.put("orange", 4);
        treeMap.put("kiwi", 5);
        System.out.println("TreeMap (sorted): " + treeMap); // Sorted on the basis of key alphabetically by default

        System.out.println("First key: " + treeMap.firstKey());  // O(log n)
        System.out.println("Last key: " + treeMap.lastKey());    // O(log n)
        System.out.println("Head map (up to zebra): " + treeMap.headMap("zebra"));
        System.out.println("Tail map (from apple): " + treeMap.tailMap("apple"));
        System.out.println("SubMap : " +  treeMap.subMap("orange", "zebra"));

        // NavigableMap operations
        NavigableMap<String, Integer> navMap = new TreeMap<>();
        navMap.put("b", 2);
        navMap.put("d", 4);
        navMap.put("f", 6);
        navMap.put("h", 8);
        System.out.println("NavigableMap: " + navMap);

        System.out.println("Lower key (<f): " + navMap.lowerKey("f"));      // O(log n) - d
        System.out.println("Floor key (<=f): " + navMap.floorKey("f"));      // O(log n) - f
        System.out.println("Ceiling key (>=e): " + navMap.ceilingKey("e")); // O(log n) - f
        System.out.println("Higher key (>f): " + navMap.higherKey("f"));     // O(log n) - h
        System.out.println("Descending map: " + navMap.descendingMap());      // O(1) - reversed view


        // ConcurrentHashMap - Thread-safe, O(1) average operations
        /* INTERNAL IMPLEMENTATION OF CONCURRENTHASHMAP:
         * 1. STRUCTURE: Array of Node<K,V>[] bins, default size 16
         * 2. CONCURRENCY: Lock-free reads, fine-grained locking on writes (per-bin)
         * 3. CAS (Compare-And-Swap): Atomic operation for lock-free updates
         *    - CAS(memory, expected, new): Updates memory to new if current == expected
         *    - Returns true if successful, false if value changed (retry needed)
         *    - Used for: empty bin insertion, size updates, initialization
         *    - Hardware-level atomic instruction (CPU support)
         * 4. NO GLOBAL LOCK: Uses CAS operations + synchronized blocks per bin
         * 5. SEGMENTS: Java 8+: per-bin locking instead of segment-level
         * 6. NULL HANDLING: NO null keys or values (throws NPE)
         * 7. THREAD-SAFE: Multiple threads can read/write concurrently
         * 8. WEAKLY CONSISTENT: Iterators don't throw ConcurrentModificationException
         * 9. TIME COMPLEXITY: O(1) average for get/put/remove
         * 10. ATOMIC OPERATIONS: putIfAbsent, remove, replace are atomic
         * 11. USE CASE: High concurrency scenarios, replaces Hashtable
         */
        Map<String, Integer> concurrentMap = new ConcurrentHashMap<>();
        concurrentMap.put("apple", 5);   // O(1) average, thread-safe
        concurrentMap.put("banana", 3);
        concurrentMap.put("cherry", 7);
        System.out.println("ConcurrentHashMap: " + concurrentMap);

        // Thread-safe atomic operations
        concurrentMap.putIfAbsent("grape", 2);  // Atomic O(1)
        System.out.println("After putIfAbsent: " + concurrentMap);


        // Immuatable Map
        Map<String,Integer> unmodifiableMap = Collections.unmodifiableMap(map);
        Map<String,Integer> unmodifiableMap2 = Map.of("A",1,"B",2);
        Map<String,Integer> unmodifiableMap3 = Map.ofEntries(Map.entry("A",1),Map.entry("B",2));


        // EnumMap - Memory Efficient al keys known to map in advance
        Map<DayOfWeek, String> enumMap = new EnumMap<>(DayOfWeek.class);
        enumMap.put(DayOfWeek.MONDAY, "CHEST");
        enumMap.put(DayOfWeek.TUESDAY, "BACK");


        // ConcurrentSkipListMap - Thread-safe sorted map, O(log n) operations
        System.out.println("\n=== CONCURRENTSKIPLISTMAP ===");

        /* THEORY:
         * 1. STRUCTURE: Skip list (probabilistic balanced tree)
         * 2. THREAD-SAFE: Lock-free, uses CAS
         * 3. SORTED: Natural ordering or Comparator
         * 4. TIME COMPLEXITY: O(log n) for get/put/remove
         * 5. NULL: Does NOT allow null keys or values
         * 6. WEAKLY CONSISTENT: Iterators don't throw ConcurrentModificationException
         */

        ConcurrentSkipListMap<String, Integer> skipMap = new ConcurrentSkipListMap<>();

        // Put - O(log n), thread-safe
        skipMap.put("zebra", 1);
        skipMap.put("apple", 2);
        skipMap.put("mango", 3);
        System.out.println("ConcurrentSkipListMap: " + skipMap);

        // Get - O(log n)
        System.out.println("Get apple: " + skipMap.get("apple"));

        // First/Last - O(log n)
        System.out.println("First key: " + skipMap.firstKey());
        System.out.println("Last key: " + skipMap.lastKey());

        // Lower/Higher - O(log n)
        System.out.println("Lower key (<mango): " + skipMap.lowerKey("mango"));
        System.out.println("Higher key (>mango): " + skipMap.higherKey("mango"));

        // Remove - O(log n), thread-safe
        skipMap.remove("apple");
        System.out.println("After remove: " + skipMap);


    }

    // HASHTABLE - Legacy synchronized map, O(1) average operations
    public static void demonstrateHashtable() {
        System.out.println("\n=== HASHTABLE (Legacy) ===");

        /* INTERNAL IMPLEMENTATION OF HASHTABLE:
         * 1. STRUCTURE: Array of buckets (Entry<K,V>[] table), default size 11
         * 2. HASH FUNCTION: key.hashCode() -> index = (hash & 0x7FFFFFFF) % table.length
         * 3. COLLISION HANDLING: Linked List only (no tree conversion)
         * 4. LOAD FACTOR: Default 0.75 (75% full triggers resize)
         * 5. RESIZE: Doubles capacity + 1 (11->23->47...), rehashes all entries - O(n)
         * 6. SYNCHRONIZATION: All methods synchronized (thread-safe but slower)
         * 7. NULL HANDLING: NO null keys or values (throws NPE)
         * 8. TIME COMPLEXITY:
         *    - Average: O(1) for get/put/remove
         *    - Worst: O(n) with poor hash (no tree optimization)
         * 9. LEGACY: Use ConcurrentHashMap for thread-safety instead
         * 10. DIFFERENCES FROM HASHMAP:
         *     - Synchronized vs non-synchronized
         *     - No null keys/values vs allows one null key
         *     - Initial capacity 11 vs 16
         *     - No tree conversion vs tree after 8 collisions
         */

        Hashtable<String, Integer> table = new Hashtable<>();

        // Put operations - O(1) average, synchronized
        table.put("apple", 5);
        table.put("banana", 3);
        table.put("cherry", 7);
        System.out.println("Hashtable: " + table);

        // Get - O(1) average, synchronized
        System.out.println("Get apple: " + table.get("apple"));

        // Contains - O(1) average, synchronized
        System.out.println("Contains key: " + table.containsKey("apple"));
        System.out.println("Contains value: " + table.containsValue(5));

        // getOrDefault - O(1) average
        System.out.println("Get or default: " + table.getOrDefault("grape", 0));

        // putIfAbsent - O(1) average
        table.putIfAbsent("grape", 2);
        System.out.println("After putIfAbsent: " + table);

        // Iteration - O(n)
        System.out.println("Keys: " + table.keySet());
        System.out.println("Values: " + table.values());
        System.out.println("Entries: " + table.entrySet());

        // Remove - O(1) average, synchronized
        table.remove("banana");
        System.out.println("After remove: " + table);

        // Size and isEmpty - O(1)
        System.out.println("Size: " + table.size() + ", Empty: " + table.isEmpty());

        // NULL handling - throws NullPointerException
        try {
            table.put(null, 1);  // NPE
        } catch (NullPointerException e) {
            System.out.println("Null key not allowed: " + e.getClass().getSimpleName());
        }

        try {
            table.put("key", null);  // NPE
        } catch (NullPointerException e) {
            System.out.println("Null value not allowed: " + e.getClass().getSimpleName());
        }
    }

    // SET IMPLEMENTATION AND UTILITIES
    public static void demonstrateSet() {
        System.out.println("\n=== SET OPERATIONS ===");

        // HashSet - O(1) average add/remove/contains, unordered
        Set<Integer> set = new HashSet<>();
        set.add(5);      // O(1) average
        set.add(2);
        set.add(8);
        set.add(2);      // Duplicate ignored
        System.out.println("HashSet: " + set);

        System.out.println("Contains 5: " + set.contains(5));  // O(1) average
        System.out.println("Size: " + set.size());             // O(1)
        set.remove(2);                                         // O(1) average
        System.out.println("After remove: " + set);

        // TreeSet - O(log n) add/remove/contains, sorted
        TreeSet<String> treeSet = new TreeSet<>();
        treeSet.add("dog");    // O(log n)
        treeSet.add("cat");
        treeSet.add("zebra");
        System.out.println("TreeSet (sorted): " + treeSet);

        System.out.println("First: " + treeSet.first());       // O(log n)
        System.out.println("Last: " + treeSet.last());         // O(log n)

        // LinkedHashSet - O(1) operations, maintains insertion order
        Set<Integer> linkedSet = new LinkedHashSet<>();
        linkedSet.add(10);     // O(1)
        linkedSet.add(5);
        linkedSet.add(20);
        System.out.println("LinkedHashSet (ordered): " + linkedSet);

        // Set operations - O(n)
        Set<Integer> a = new HashSet<>(Arrays.asList(1, 2, 3, 4));
        Set<Integer> b = new HashSet<>(Arrays.asList(3, 4, 5, 6));

        Set<Integer> union = new HashSet<>(a);
        union.addAll(b);                                       // O(n)
        System.out.println("Union: " + union);

        Set<Integer> intersection = new HashSet<>(a);
        intersection.retainAll(b);                             // O(n)
        System.out.println("Intersection: " + intersection);

        Set<Integer> difference = new HashSet<>(a);
        difference.removeAll(b);                               // O(n)
        System.out.println("Difference: " + difference);

        System.out.println("\n=== CONCURRENTSKIPLISTSET ===");


        // CONCURRENTSKIPLISTSET - Thread-safe sorted set, O(log n) operations

        /* THEORY:
         * 1. STRUCTURE: Skip list (probabilistic balanced tree alternative)
         * 2. THREAD-SAFE: Lock-free, uses CAS (Compare-And-Swap)
         * 3. SORTED: Natural ordering or custom Comparator
         * 4. TIME COMPLEXITY: O(log n) for add/remove/contains
         * 5. NULL: Does NOT allow null elements
         * 6. WEAKLY CONSISTENT: Iterators don't throw ConcurrentModificationException
         */

        ConcurrentSkipListSet<Integer> skipSet = new ConcurrentSkipListSet<>();

        // Add - O(log n), thread-safe
        skipSet.add(50);
        skipSet.add(20);
        skipSet.add(80);
        skipSet.add(10);
        System.out.println("ConcurrentSkipListSet: " + skipSet);

        // Contains - O(log n)
        System.out.println("Contains 20: " + skipSet.contains(20));

        // First/Last - O(log n)
        System.out.println("First: " + skipSet.first());
        System.out.println("Last: " + skipSet.last());

        // Lower/Higher - O(log n)
        System.out.println("Lower than 50: " + skipSet.lower(50));
        System.out.println("Higher than 50: " + skipSet.higher(50));

        // Floor/Ceiling - O(log n)
        System.out.println("Floor of 50: " + skipSet.floor(50));
        System.out.println("Ceiling of 50: " + skipSet.ceiling(50));

        // SubSet - O(log n)
        System.out.println("SubSet [20, 80): " + skipSet.subSet(20, 80));

        // Remove - O(log n), thread-safe
        skipSet.remove(20);
        System.out.println("After remove: " + skipSet);

        // PollFirst/PollLast - O(log n), atomic
        System.out.println("Poll first: " + skipSet.pollFirst());
        System.out.println("Poll last: " + skipSet.pollLast());
        System.out.println("After polls: " + skipSet);


        // COPYONWRITEARRAYSET - Thread-safe set, O(n) write, O(n) read
        System.out.println("\n=== COPYONWRITEARRAYSET ===");

        /* THEORY:
         * 1. STRUCTURE: Backed by CopyOnWriteArrayList
         * 2. THREAD-SAFE: Creates copy on modification
         * 3. NO DUPLICATES: Uses equals() for uniqueness
         * 4. TIME COMPLEXITY: O(n) for add/remove/contains
         * 5. NULL: Allows null elements
         * 6. USE CASE: Read-heavy, few writes, small sets
         */

        CopyOnWriteArraySet<Integer> cowSet = new CopyOnWriteArraySet<>();

        // Add - O(n), creates copy
        cowSet.add(10);
        cowSet.add(20);
        cowSet.add(30);
        cowSet.add(20);  // Duplicate ignored
        System.out.println("CopyOnWriteArraySet: " + cowSet);

        // Contains - O(n)
        System.out.println("Contains 20: " + cowSet.contains(20));

        // Remove - O(n), creates copy
        cowSet.remove(20);
        System.out.println("After remove: " + cowSet);

        // Size - O(1)
        System.out.println("Size: " + cowSet.size());
    }

    // STACK - LIFO, O(1) push/pop/peek
    public static void demonstrateStack() {
        System.out.println("\n=== STACK (LIFO) ===");
        Deque<Integer> stack = new ArrayDeque<>();

        // Push - O(1)
        stack.push(10);
        stack.push(20);
        stack.push(30);
        System.out.println("After pushes: " + stack);

        // Peek - O(1)
        System.out.println("Peek: " + stack.peek());

        // Pop - O(1)
        System.out.println("Pop: " + stack.pop());
        System.out.println("After pop: " + stack);

        // Size and isEmpty - O(1)
        System.out.println("Size: " + stack.size() + ", Empty: " + stack.isEmpty());

    }

    // QUEUE - FIFO, O(1) offer/poll/peek
    public static void demonstrateQueue() {
        System.out.println("\n=== QUEUE (FIFO) ===");
        Queue<String> queue = new LinkedList<>();

        // Offer (enqueue) - O(1)
        queue.offer("First");
        queue.offer("Second");
        queue.offer("Third");
        System.out.println("After offers: " + queue);

        // Peek - O(1)
        System.out.println("Peek: " + queue.peek());

        // Poll (dequeue) - O(1)
        System.out.println("Poll: " + queue.poll());
        System.out.println("After poll: " + queue);

        // Size and isEmpty - O(1)
        System.out.println("Size: " + queue.size() + ", Empty: " + queue.isEmpty());


        // CONCURRENT LINKED QUEUE - Lock-free thread-safe queue
        System.out.println("\n--- CONCURRENT LINKED QUEUE ---");
        
        /* NOTES:
         * 1. LOCK-FREE: Uses CAS (Compare-And-Swap) operations, no blocking
         * 2. UNBOUNDED: No capacity limit
         * 3. NON-BLOCKING: offer/poll never block, always return immediately
         * 4. WEAKLY CONSISTENT: Iterators may not reflect concurrent modifications
         * 5. SIZE: O(n) - not constant time (traverses entire queue)
         * 6. USE CASE: High-throughput concurrent scenarios, no blocking needed
         */
        
        ConcurrentLinkedQueue<String> concurrentQueue = new ConcurrentLinkedQueue<>();
        
        concurrentQueue.offer("Item1");  // O(1) non-blocking
        concurrentQueue.offer("Item2");
        concurrentQueue.offer("Item3");
        
        System.out.println("Poll: " + concurrentQueue.poll());  // O(1) non-blocking
        System.out.println("Peek: " + concurrentQueue.peek());  // O(1)
        System.out.println("Size: " + concurrentQueue.size());  // O(n) - expensive!


        // BLOCKING QUEUE - Thread-safe queue with blocking operations
        /* NOTES:
         * 1. THREAD-SAFE: Blocks when queue is full (put) or empty (take)
         * 2. IMPLEMENTATIONS: ArrayBlockingQueue (bounded), LinkedBlockingQueue (optionally bounded) - separate locks for enqueue and dequeue works better with more threads,
         *    PriorityBlockingQueue (unbounded)
         * 3. KEY METHODS: put() blocks on full, take() blocks on empty, offer(timeout), poll(timeout)
         * 4. USE CASE: Producer-consumer pattern, thread pools
         */
        BlockingQueue<String> queue2 = new ArrayBlockingQueue<String>(10);
        try {
            queue2.put("Item1");     // Blocks if full
            queue2.put("Item2");
            String item = queue2.take();  // Blocks if empty
            System.out.println("Taken: " + item);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }


        // DELAY QUEUE - Elements available only after delay expires
        System.out.println("\n--- DELAY QUEUE ---");
        
        /* NOTES:
         * 1. UNBOUNDED: No capacity limit
         * 2. DELAYED: Elements retrieved only after delay expires
         * 3. REQUIRES: Elements must implement Delayed interface
         * 4. SORTED: By delay time (earliest expiration first)
         * 5. USE CASE: Scheduled tasks, cache expiration, rate limiting
         */
        
        class DelayedTask implements Delayed {
            private String name;
            private long startTime;
            
            public DelayedTask(String name, long delayInMillis) {
                this.name = name;
                this.startTime = System.currentTimeMillis() + delayInMillis;
            }
            
            @Override
            public long getDelay(TimeUnit unit) {
                long diff = startTime - System.currentTimeMillis();
                return unit.convert(diff, TimeUnit.MILLISECONDS);
            }
            
            @Override
            public int compareTo(Delayed o) {
                return Long.compare(this.startTime, ((DelayedTask) o).startTime);
            }
            
            @Override
            public String toString() {
                return name;
            }
        }
        
        DelayQueue<DelayedTask> delayQueue = new DelayQueue<>();
        
        delayQueue.offer(new DelayedTask("Task-3sec", 3000));
        delayQueue.offer(new DelayedTask("Task-1sec", 1000));
        delayQueue.offer(new DelayedTask("Task-2sec", 2000));
        
        System.out.println("Added 3 tasks with delays: 3s, 1s, 2s");
        System.out.println("Queue size: " + delayQueue.size());
        
        System.out.println("Immediate poll: " + delayQueue.poll()); // null - no element ready
        
        try {
            System.out.println("Waiting for tasks...");
            System.out.println("Retrieved: " + delayQueue.take()); // Task-1sec after 1s
            System.out.println("Retrieved: " + delayQueue.take()); // Task-2sec after 2s
            System.out.println("Retrieved: " + delayQueue.take()); // Task-3sec after 3s
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // DEQUE - Double-ended queue, O(1) operations at both ends
    public static void demonstrateDeque() {
        System.out.println("\n=== DEQUE (Double-ended Queue) ===");
        Deque<Integer> deque = new ArrayDeque<>();

        // Add at both ends - O(1)
        deque.addFirst(10);      // O(1)
        deque.addLast(30);       // O(1)
        deque.addFirst(5);
        System.out.println("After adds: " + deque);

        // Get from both ends - O(1)
        System.out.println("First: " + deque.getFirst());  // O(1)
        System.out.println("Last: " + deque.getLast());    // O(1)

        // Remove from both ends - O(1)
        System.out.println("Remove first: " + deque.removeFirst()); // O(1)
        System.out.println("Remove last: " + deque.removeLast());   // O(1)
        System.out.println("After removes: " + deque);

        // Size - O(1)
        System.out.println("Size: " + deque.size());
    }

    // PRIORITY QUEUE - Min/Max heap, O(log n) offer/poll, O(1) peek
    public static void demonstratePriorityQueue() {
        System.out.println("\n=== PRIORITY QUEUE (Heap) ===");

        // Min Heap - O(log n) offer/poll, O(1) peek
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        minHeap.offer(30);       // O(log n)
        minHeap.offer(10);
        minHeap.offer(20);
        System.out.println("Min Heap after offers: " + minHeap);

        System.out.println("Peek min: " + minHeap.peek());  // O(1)
        System.out.println("Poll min: " + minHeap.poll()); // O(log n)
        System.out.println("After poll: " + minHeap);

        // Max Heap - O(log n) offer/poll, O(1) peek
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
        maxHeap.offer(30);       // O(log n)
        maxHeap.offer(10);
        maxHeap.offer(20);
        System.out.println("Max Heap after offers: " + maxHeap);

        System.out.println("Peek max: " + maxHeap.peek());  // O(1)
        System.out.println("Poll max: " + maxHeap.poll()); // O(log n)
        System.out.println("After poll: " + maxHeap);

        // Size and isEmpty - O(1)
        System.out.println("Size: " + minHeap.size() + ", Empty: " + minHeap.isEmpty());
    }
}



