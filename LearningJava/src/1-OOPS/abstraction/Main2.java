package OOPS.abstraction;
// Demonstration of Nested Classes in Java

public class NestedClasses {
    public static void main(String[] args) {
        NestedExample.StaticNested staticNested = new NestedExample.StaticNested();
        staticNested.display();

        NestedExample outer = new NestedExample();
        NestedExample.Inner inner = outer.new Inner();
        inner.show();
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
