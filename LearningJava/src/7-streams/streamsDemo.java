package streams;

public class streamsDemo {
  public static void main(String[] args) {
    demonstrateFunctionalInterfaces();
    demonstrateStreams();
  }
   // FUNCTIONAL INTERFACES - Core building blocks for functional programming
    public static void demonstrateFunctionalInterfaces() {
        System.out.println("\n=== FUNCTIONAL INTERFACES ===");

        /* FUNCTIONAL INTERFACES OVERVIEW:
         * 1. DEFINITION: Interface with exactly one abstract method (SAM - Single Abstract Method)
         * 2. @FunctionalInterface: Optional annotation for compile-time checking
         * 3. LAMBDA EXPRESSIONS: Concise way to implement functional interfaces
         * 4. METHOD REFERENCES: Shorthand for lambdas (ClassName::methodName)
         * 5. CORE FUNCTIONAL INTERFACES (java.util.function):
         *    - Predicate<T>: T -> boolean (test/filter)
         *    - Consumer<T>: T -> void (accept/process)
         *    - Supplier<T>: () -> T (get/generate)
         *    - Function<T,R>: T -> R (apply/transform)
         *    - BiPredicate<T,U>: (T,U) -> boolean
         *    - BiConsumer<T,U>: (T,U) -> void
         *    - BiFunction<T,U,R>: (T,U) -> R
         *    - UnaryOperator<T>: T -> T (extends Function<T,T>)
         *    - BinaryOperator<T>: (T,T) -> T (extends BiFunction<T,T,T>)
         * 6. PRIMITIVE SPECIALIZATIONS: IntPredicate, IntConsumer, IntSupplier, IntFunction, etc.
         * 7. COMPOSABILITY: andThen, compose, and, or, negate methods
         */

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // PREDICATE<T> - Takes T, returns boolean (test condition)
        System.out.println("\n--- PREDICATE ---");
        Predicate<Integer> isEven = n -> n % 2 == 0;
        Predicate<Integer> isGreaterThan5 = n -> n > 5;

        System.out.println("Is 4 even? " + isEven.test(4));
        System.out.println("Is 7 even? " + isEven.test(7));

        // Predicate composition - and, or, negate
        Predicate<Integer> isEvenAndGreaterThan5 = isEven.and(isGreaterThan5);
        Predicate<Integer> isEvenOrGreaterThan5 = isEven.or(isGreaterThan5);
        Predicate<Integer> isOdd = isEven.negate();

        System.out.println("Is 6 even AND > 5? " + isEvenAndGreaterThan5.test(6));
        System.out.println("Is 3 even OR > 5? " + isEvenOrGreaterThan5.test(3));
        System.out.println("Is 5 odd? " + isOdd.test(5));

        // Predicate with streams
        List<Integer> evenNumbers = numbers.stream()
                .filter(isEven)
                .collect(Collectors.toList());
        System.out.println("Even numbers: " + evenNumbers);

        // CONSUMER<T> - Takes T, returns void (side effects)
        System.out.println("\n--- CONSUMER ---");
        Consumer<String> printUpperCase = s -> System.out.println(s.toUpperCase());
        Consumer<String> printLength = s -> System.out.println("Length: " + s.length());

        printUpperCase.accept("hello");

        // Consumer composition - andThen
        Consumer<String> printBoth = printUpperCase.andThen(printLength);
        printBoth.accept("world");

        // Consumer with streams
        List<String> words = Arrays.asList("apple", "banana", "cherry");
        System.out.println("Processing words:");
        words.forEach(printUpperCase);

        // BiConsumer - takes two arguments
        BiConsumer<String, Integer> printKeyValue = (k, v) ->
                System.out.println(k + " = " + v);
        Map<String, Integer> map = Map.of("a", 1, "b", 2, "c", 3);
        map.forEach(printKeyValue);

        // SUPPLIER<T> - Takes nothing, returns T (lazy generation)
        System.out.println("\n--- SUPPLIER ---");
        Supplier<Double> randomSupplier = () -> Math.random();
        Supplier<String> uuidSupplier = () -> UUID.randomUUID().toString();
        Supplier<List<Integer>> listSupplier = () -> new ArrayList<>(Arrays.asList(1, 2, 3));

        System.out.println("Random: " + randomSupplier.get());
        System.out.println("UUID: " + uuidSupplier.get());
        System.out.println("New list: " + listSupplier.get());

        // Supplier for lazy initialization
        Supplier<Integer> expensiveComputation = () -> {
            System.out.println("Computing...");
            return numbers.stream().reduce(0, Integer::sum);
        };
        System.out.println("Result: " + expensiveComputation.get());

        // Supplier with Optional
        Optional<String> empty = Optional.empty();
        String result = empty.orElseGet(() -> "default value");
        System.out.println("Optional result: " + result);

        // FUNCTION<T,R> - Takes T, returns R (transformation)
        System.out.println("\n--- FUNCTION ---");
        Function<String, Integer> stringLength = s -> s.length();
        Function<Integer, Integer> square = n -> n * n;
        Function<Integer, String> intToString = n -> "Number: " + n;

        System.out.println("Length of 'hello': " + stringLength.apply("hello"));
        System.out.println("Square of 5: " + square.apply(5));

        // Function composition - andThen, compose
        Function<String, Integer> lengthSquared = stringLength.andThen(square);
        Function<Integer, String> squareToString = square.andThen(intToString);

        System.out.println("Length squared of 'test': " + lengthSquared.apply("test"));
        System.out.println("Square to string: " + squareToString.apply(7));

        // compose - applies argument function first
        Function<Integer, Integer> addTen = n -> n + 10;
        Function<Integer, Integer> multiplyByTwo = n -> n * 2;
        Function<Integer, Integer> composedFunc = multiplyByTwo.compose(addTen);
        System.out.println("(5 + 10) * 2 = " + composedFunc.apply(5));

        // Function with streams
        List<Integer> lengths = words.stream()
                .map(stringLength)
                .collect(Collectors.toList());
        System.out.println("Word lengths: " + lengths);

        // BiFunction - takes two arguments
        BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
        BiFunction<String, String, String> concat = (s1, s2) -> s1 + s2;
        System.out.println("Add 3 + 4: " + add.apply(3, 4));
        System.out.println("Concat: " + concat.apply("Hello", "World"));

        // UNARYOPERATOR<T> - Special case of Function<T,T>
        System.out.println("\n--- UNARYOPERATOR ---");
        UnaryOperator<Integer> increment = n -> n + 1;
        UnaryOperator<String> toUpper = String::toUpperCase;

        System.out.println("Increment 5: " + increment.apply(5));
        System.out.println("To upper: " + toUpper.apply("hello"));

        // UnaryOperator with replaceAll
        List<String> mutableWords = new ArrayList<>(words);
        mutableWords.replaceAll(toUpper);
        System.out.println("Uppercase words: " + mutableWords);

        // BINARYOPERATOR<T> - Special case of BiFunction<T,T,T>
        System.out.println("\n--- BINARYOPERATOR ---");
        BinaryOperator<Integer> sum = (a, b) -> a + b;
        BinaryOperator<Integer> max = (a, b) -> a > b ? a : b;
        BinaryOperator<String> concatenate = (s1, s2) -> s1 + " " + s2;

        System.out.println("Sum: " + sum.apply(10, 20));
        System.out.println("Max: " + max.apply(10, 20));
        System.out.println("Concatenate: " + concatenate.apply("Hello", "World"));

        // BinaryOperator with reduce
        int total = numbers.stream().reduce(0, sum);
        System.out.println("Total sum: " + total);

        Optional<Integer> maxValue = numbers.stream().reduce(max);
        System.out.println("Max value: " + maxValue.orElse(-1));

        // METHOD REFERENCES
        System.out.println("\n--- METHOD REFERENCES ---");

        // Static method reference
        Function<String, Integer> parseInt = Integer::parseInt;
        System.out.println("Parse '123': " + parseInt.apply("123"));

        // Instance method reference
        String prefix = "Hello ";
        Function<String, String> addPrefix = prefix::concat;
        System.out.println("Add prefix: " + addPrefix.apply("World"));

        // Constructor reference
        Supplier<ArrayList<String>> listConstructor = ArrayList::new;
        List<String> newList = listConstructor.get();
        System.out.println("New list: " + newList);

        // Array constructor reference
        Function<Integer, int[]> arrayConstructor = int[]::new;
        int[] array = arrayConstructor.apply(5);
        System.out.println("Array length: " + array.length);

        // PRACTICAL EXAMPLES
        System.out.println("\n--- PRACTICAL EXAMPLES ---");

        // Validation with Predicate
        Predicate<String> isValidEmail = email -> email.contains("@") && email.contains(".");
        System.out.println("Valid email: " + isValidEmail.test("user@example.com"));

        // Data processing pipeline
        List<String> names = Arrays.asList("alice", "bob", "charlie", "david");
        names.stream()
                .filter(name -> name.length() > 3)
                .map(String::toUpperCase)
                .forEach(System.out::println);

        // Custom functional interface usage
        Function<List<Integer>, Integer> sumList = list -> list.stream().reduce(0, Integer::sum);
        System.out.println("Sum of list: " + sumList.apply(numbers));
    }

    // JAVA STREAMS - Functional programming for collections
    public static void demonstrateStreams() {
        System.out.println("\n=== JAVA STREAMS ===");

        /* INTERNAL WORKING OF STREAMS:
         * 1. PIPELINE: Source -> Intermediate Operations -> Terminal Operation
         * 2. LAZY EVALUATION: Intermediate operations not executed until terminal operation
         * 3. TYPES:
         *    - Stream<T>: Object streams
         *    - IntStream, LongStream, DoubleStream: Primitive streams (avoid boxing)
         * 4. INTERMEDIATE OPERATIONS (return Stream): filter, map, flatMap, distinct, sorted, limit, skip
         * 5. TERMINAL OPERATIONS (return result): forEach, collect, reduce, count, anyMatch, allMatch, findFirst
         * 6. STATELESS vs STATEFUL:
         *    - Stateless: filter, map (process each element independently)
         *    - Stateful: sorted, distinct (need to see all elements)
         * 7. PARALLEL STREAMS: Uses ForkJoinPool, splits data for concurrent processing
         * 8. SHORT-CIRCUITING: anyMatch, allMatch, findFirst stop early when possible
         * 9. IMMUTABLE: Original collection unchanged, creates new stream
         * 10. ONE-TIME USE: Stream can only be consumed once
         */


        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // Filter - O(n)
        List<Integer> evens = numbers.stream()
                .filter(n -> n % 2 == 0)
                .toList();
        System.out.println("Evens: " + evens);

        // Map - O(n)
        List<Integer> squares = numbers.stream()
                .map(n -> n * n)
                .collect(Collectors.toList());
        System.out.println("Squares: " + squares);

        // Filter + Map - O(n)
        List<Integer> evenSquares = numbers.stream()
                .filter(n -> n % 2 == 0)
                .map(n -> n * n)
                .collect(Collectors.toList());
        System.out.println("Even squares: " + evenSquares);

        // Reduce - O(n)
        int sum = numbers.stream()
                .reduce(0, (a, b) -> a + b);
        System.out.println("Sum: " + sum);

        // Count - O(n)
        long count = numbers.stream()
                .filter(n -> n > 5)
                .count();
        System.out.println("Count > 5: " + count);

        // Sorted - O(n log n)
        List<Integer> sorted = numbers.stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        System.out.println("Sorted desc: " + sorted);

        // Distinct - O(n)
        List<Integer> duplicates = Arrays.asList(1, 2, 2, 3, 3, 4);
        List<Integer> unique = duplicates.stream()
                .distinct()
                .collect(Collectors.toList());
        System.out.println("Unique: " + unique);

        // Limit and Skip - O(n)
        List<Integer> limited = numbers.stream()
                .skip(3)
                .limit(4)
                .collect(Collectors.toList());
        System.out.println("Skip 3, limit 4: " + limited);

        // anyMatch, allMatch, noneMatch - O(n) short-circuit
        boolean hasEven = numbers.stream().anyMatch(n -> n % 2 == 0);
        boolean allPositive = numbers.stream().allMatch(n -> n > 0);
        System.out.println("Has even: " + hasEven + ", All positive: " + allPositive);

        // findFirst, findAny - O(n) short-circuit
        Optional<Integer> first = numbers.stream()
                .filter(n -> n > 5)
                .findFirst();
        System.out.println("First > 5: " + first.orElse(-1));

        // FlatMap - O(n*m) flattens nested collections
        List<List<Integer>> nested = Arrays.asList(
                Arrays.asList(1, 2),
                Arrays.asList(3, 4),
                Arrays.asList(5, 6)
        );
        List<Integer> flattened = nested.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
        System.out.println("Flattened: " + flattened);

        // Collectors.groupingBy - O(n)
        List<String> words = Arrays.asList("apple", "banana", "apricot", "berry", "cherry");
        Map<Character, List<String>> grouped = words.stream()
                .collect(Collectors.groupingBy(w -> w.charAt(0)));
        System.out.println("Grouped by first char: " + grouped);

        // Collectors.partitioningBy - O(n)
        Map<Boolean, List<Integer>> partitioned = numbers.stream()
                .collect(Collectors.partitioningBy(n -> n % 2 == 0));
        System.out.println("Partitioned: " + partitioned);

        // Collectors.joining - O(n)
        String joined = words.stream()
                .collect(Collectors.joining(", "));
        System.out.println("Joined: " + joined);


        // IntStream range - O(n)
        int rangeSum = IntStream.range(1, 11).sum();
        System.out.println("Range sum 1-10: " + rangeSum);

        // Parallel Stream - O(n) with parallelism
        long parallelSum = numbers.parallelStream()
                .mapToLong(Integer::longValue)
                .sum();
        System.out.println("Parallel sum: " + parallelSum);

        // Stream from array - O(n)
        int[] arr = {1, 2, 3, 4, 5};
        int arraySum = Arrays.stream(arr).sum();
        System.out.println("Array sum: " + arraySum);

        // Max and Min - O(n)
        Optional<Integer> max = numbers.stream().max(Integer::compareTo);
        Optional<Integer> min = numbers.stream().min(Integer::compareTo);
        System.out.println("Max: " + max.orElse(-1) + ", Min: " + min.orElse(-1));
    }
}
