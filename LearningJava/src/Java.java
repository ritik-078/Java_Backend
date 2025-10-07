public class Java {
    public static void main(String[] args) {
        double d = 5.6667;
        float f = 5.6667f;
        char ch = 'a';
        int a = (int)d;
        int b = (int)f;
        int c = (int)ch;
        System.out.println(a +" "+ b +" "+ c);


        // Autoboxing: Java automatically converts primitive types to their corresponding wrapper classes.
        // Unboxing: Java automatically converts wrapper class objects to their corresponding primitive types.
        
        // For Integer objects, Java caches values between -128 and 127.
        // So, when you create Integer objects with values in this range, '==' compares references that point to the same cached object.
        // For values outside this range, new Integer objects are created, so '==' compares references that point to different objects.
        
        // '==' checks if two references point to the same object.
        // '.equals()' checks if two objects have the same value.

        Integer num = 10; // Autoboxing: primitive int to Integer object
        int val = num;    // Unboxing: Integer object to primitive int
        System.out.println("Autoboxed Integer: " + num);
        System.out.println("Unboxed int: " + val);

        int x = 100;        
        int y = 100;
        System.out.println("Primitive comparison (x == y): " + (x == y));

        Integer m = 100;
        Integer n = 100;
        System.out.println("Integer object comparison (m == n): " + (m == n));
        System.out.println("Integer object comparison (m.equals(n)): " + m.equals(n));

        Integer p = 200;
        Integer q = 200;
        System.out.println("Integer object comparison (p == q): " + (p == q));
        System.out.println("Integer object comparison (p.equals(q)): " + p.equals(q));




        // Ternary operator example
        int num1 = 10, num2 = 20;
        int max = (num1 > num2) ? num1 : num2;
        System.out.println("Max using ternary operator: " + max);

        // Switch statement example
        int day = 3;
        String dayName;
        switch (day) {
            case 1:
                dayName = "Monday";
                break;
            case 2:
                dayName = "Tuesday";
                break;
            case 3:
                dayName = "Wednesday";
                break;
            case 4:
                dayName = "Thursday";
                break;
            case 5:
                dayName = "Friday";
                break;
            case 6:
                dayName = "Saturday";
                break;
            case 7:
                dayName = "Sunday";
                break;
            default:
                dayName = "Invalid day";
        }
        System.out.println("Day name using switch statement: " + dayName);
    }

    public static void input(String[] args) {

        try {
            // Using InputStreamReader and BufferedReader
            java.io.InputStreamReader isr = new java.io.InputStreamReader(System.in);
            java.io.BufferedReader br = new java.io.BufferedReader(isr);
            System.out.print("Enter a line using BufferedReader: ");
            String line = br.readLine();
            System.out.println("You entered: " + line);

            // Using Scanner
            java.util.Scanner scanner = new java.util.Scanner(System.in);
            System.out.print("Enter a number using Scanner: ");
            int num = scanner.nextInt();
            System.out.println("You entered: " + num);

            scanner.close();
        } catch (java.io.IOException e) {
            System.out.println("Error reading input: " + e.getMessage());
        }
        
    }
}
