package OOPS.abstraction;
// Demonstration of Nested Classes in Java

public class NestedClasses {
    public static void main(String[] args) {
        NestedExample.StaticNested staticNested = new NestedExample.StaticNested();
        staticNested.display();

        NestedExample outer = new NestedExample();
        NestedExample.Inner inner = outer.new Inner();
        inner.show();

        // Nested classes can be used to logically group classes that are only used in one place
        // It increases encapsulation
        // It can lead to more readable and maintainable code
        // Types of nested classes:
        // 1. Static nested class
        // 2. Inner class (non-static)
        // 3. Local class (defined within a method)
        // 4. Anonymous class (defined and instantiated in a single expression)
        
    }
}

class NestedExample {
    // Static nested class
    static class StaticNested {
        void display() {
            System.out.println("Inside Static Nested Class");
        }
    }

    // Inner class (non-static)
    class Inner {
        void show() {
            System.out.println("Inside Inner Class");
        }
    }
}
