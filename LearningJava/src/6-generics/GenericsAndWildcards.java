import java.util.Arrays;
import java.util.List;

public class GenericsAndWildcards {

    // --- 1. Generic Class ---
    // T is a type parameter, replaced by actual type at compile time
    static class Box<T> {
        private T value;
        Box(T value) { this.value = value; }
        T get() { return value; }
    }

    // --- 2. Generic Method ---
    // <T> before return type declares a method-level type parameter
    static <T> void printArray(T[] arr) {
        System.out.println(Arrays.toString(arr));
    }

    // --- 3. Bounded Type Parameter ---
    // <T extends Number> restricts T to Number and its subclasses (Integer, Double, etc.)
    static <T extends Number> double sum(List<T> list) {
        double total = 0;
        for (T item : list) total += item.doubleValue();
        return total;
    }

    // --- 4. Wildcard: Unknown type ---
    // <?> accepts a list of any type — read-only usage
    static void printList(List<?> list) {
        System.out.println(list);
    }

    // --- 5. Upper Bounded Wildcard ---
    // <? extends Number> accepts List<Integer>, List<Double>, etc. — safe to READ
    static double sumWildcard(List<? extends Number> list) {
        double total = 0;
        for (Number n : list) total += n.doubleValue();
        return total;
    }

    // --- 6. Lower Bounded Wildcard ---
    // <? super Integer> accepts List<Integer>, List<Number>, List<Object> — safe to WRITE
    static void addNumbers(List<? super Integer> list) {
        list.add(10);
        list.add(20);
    }

    // --- 7. Multiple Bounds ---
    // T must implement both Comparable and extend Number
    static <T extends Number & Comparable<T>> T max(T a, T b) {
        return a.compareTo(b) >= 0 ? a : b;
    }

    public static void main(String[] args) {
        // Generic class
        Box<String> strBox = new Box<>("Hello Generics");
        Box<Integer> intBox = new Box<>(42);
        System.out.println(strBox.get() + " | " + intBox.get());

        // Generic method
        printArray(new Integer[]{1, 2, 3});
        printArray(new String[]{"a", "b", "c"});

        // Bounded type parameter
        System.out.println("Sum: " + sum(Arrays.asList(1, 2, 3, 4)));

        // Unbounded wildcard
        printList(Arrays.asList("x", "y", "z"));

        // Upper bounded wildcard
        System.out.println("Wildcard Sum: " + sumWildcard(Arrays.asList(1.5, 2.5, 3.0)));

        // Lower bounded wildcard
        List<Number> numbers = new java.util.ArrayList<>();
        addNumbers(numbers);
        System.out.println("After addNumbers: " + numbers);

        // Multiple bounds
        System.out.println("Max: " + max(10, 25));
    }
}
