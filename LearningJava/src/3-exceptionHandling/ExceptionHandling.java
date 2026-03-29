import java.io.IOException;

public class ExceptionHandling {

    // --- Exception Hierarchy ---
    // Throwable
    //   ├── Error          (JVM-level, don't catch: OutOfMemoryError, StackOverflowError)
    //   └── Exception
    //         ├── Checked  (must handle at compile time: IOException, SQLException)
    //         └── Unchecked / RuntimeException (optional: NullPointerException, ArithmeticException)


    // --- 1. Basic try-catch-finally ---
    // finally always runs, even if exception is thrown or not
    static void basicTryCatch() {
        try {
            int result = 10 / 0; // throws ArithmeticException
        } catch (ArithmeticException e) {
            System.out.println("Caught: " + e.getMessage());
        } finally {
            System.out.println("Finally block always runs");
        }
    }

    // --- 2. Multiple catch blocks ---
    // More specific exceptions must come before general ones
    static void multipleCatch(String input) {
        try {
            int[] arr = new int[3];
            arr[5] = Integer.parseInt(input); // may throw NumberFormatException or ArrayIndexOutOfBoundsException
        } catch (NumberFormatException e) {
            System.out.println("Invalid number: " + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Index out of bounds: " + e.getMessage());
        } catch (Exception e) {
            // Catches any other exception — acts as a fallback
            System.out.println("General exception: " + e.getMessage());
        }
    }

    // --- 3. Multi-catch (Java 7+) ---
    // Handles multiple unrelated exceptions in one block using |
    static void multiCatch(String[] args) {
        try {
            String s = null;
            System.out.println(s.length()); // NullPointerException
        } catch (NullPointerException | IllegalArgumentException e) {
            System.out.println("Multi-catch: " + e.getClass().getSimpleName());
        }
    }

    // --- 4. Checked Exception ---
    // Must be declared with 'throws' or handled with try-catch
    static void checkedExample() throws IOException {
        throw new IOException("Simulated IO failure");
    }

    // --- 5. throw vs throws ---
    // 'throw'  — used to explicitly throw an exception instance
    // 'throws' — declares that a method may throw an exception (caller must handle)
    static void validateAge(int age) {
        if (age < 0) throw new IllegalArgumentException("Age cannot be negative: " + age);
        System.out.println("Valid age: " + age);
    }

    // --- 6. Custom Exception ---
    // Extend Exception (checked) or RuntimeException (unchecked)
    static class InsufficientFundsException extends Exception {
        private final double amount;
        InsufficientFundsException(double amount) {
            super("Insufficient funds. Short by: " + amount);
            this.amount = amount;
        }
        double getAmount() { return amount; }
    }

    static void withdraw(double balance, double amount) throws InsufficientFundsException {
        if (amount > balance) throw new InsufficientFundsException(amount - balance);
        System.out.println("Withdrawal successful. Remaining: " + (balance - amount));
    }

    // --- 7. try-with-resources (Java 7+) ---
    // AutoCloseable resources are closed automatically — no need for finally
    static void tryWithResources() {
        // Simulating a resource that implements AutoCloseable
        try (AutoCloseable resource = () -> System.out.println("Resource closed automatically")) {
            System.out.println("Using resource...");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // --- 8. Exception Chaining ---
    // Wrapping a low-level exception inside a higher-level one preserves the root cause
    static void exceptionChaining() throws Exception {
        try {
            int[] arr = new int[2];
            arr[5] = 1; // ArrayIndexOutOfBoundsException
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new Exception("High-level operation failed", e); // e is the cause
        }
    }

    public static void main(String[] args) {
        // 1. Basic try-catch-finally
        basicTryCatch();

        // 2. Multiple catch
        multipleCatch("abc");   // NumberFormatException
        multipleCatch("99");    // ArrayIndexOutOfBoundsException

        // 3. Multi-catch
        multiCatch(args);

        // 4 & 5. Checked + throws
        try {
            checkedExample();
        } catch (IOException e) {
            System.out.println("Handled checked: " + e.getMessage());
        }

        // 5. throw
        try {
            validateAge(-5);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        // 6. Custom exception
        try {
            withdraw(100.0, 150.0);
        } catch (InsufficientFundsException e) {
            System.out.println("Custom exception: " + e.getMessage());
        }

        // 7. try-with-resources
        tryWithResources();

        // 8. Exception chaining
        try {
            exceptionChaining();
        } catch (Exception e) {
            System.out.println("Chained: " + e.getMessage());
            System.out.println("Root cause: " + e.getCause().getClass().getSimpleName());
        }
    }
}
