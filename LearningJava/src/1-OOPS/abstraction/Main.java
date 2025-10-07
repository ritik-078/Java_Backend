package abstraction;

public class Main {
    public static void main(String[] args){
        Child c = new Child(23,"Ritik");
        c.career("Engineer");
        c.partner("Ritika",21);

        //Anonymous class
        // an anonymous class is a class that is defined and instantiated in a single 
        // expression, it is used to override a method of a class or an interface
        // it is useful when you have a class that you want to use only once and don't want to
        // create a separate class for it

        Parent p = new Parent() {
            @Override
            void career(String careerName) {
                System.out.println("Career Name: " + careerName);
            }

            @Override
            void partner(String name, int age) {
                System.out.println("Partner Name: " + name + ", Age: " + age);
            }
        };

        // Abstraction is the process of hiding the implementation details and showing only the functionality to the user
        // Abstraction can be achieved using abstract classes and interfaces
        // Abstract class is a class which is declared with abstract keyword
        // Abstract class can have abstract and non-abstract methods
        // Abstract method is a method which is declared without any implementation
        // Abstract class can't be instantiated, but can be inherited
        // If a class has at least one abstract method, then the class must be declared as abstract
        // If a class is inheriting an abstract class, then it must implement all the abstract methods of the parent class
        // Interface is a blueprint of a class, it has static constants and abstract methods
        // A class can implement multiple interfaces
        // Interface is implemented using implements keyword 
        // By default, all the methods in an interface are abstract and public
        // From Java 8, interface can have default and static methods with implementation
        // A class can implement multiple interfaces, but can inherit only one class

        // Abstract class vs Interface
        // 1. Abstract class can have abstract and non-abstract methods, whereas interface can
        // have only abstract methods (default and static methods from Java 8)
        // 2. A class can inherit only one abstract class, but can implement multiple interfaces
        // 3. Abstract class can have constructors, whereas interface can't have constructors
        // 4. Abstract class can have instance variables, whereas interface can have only static and
        // final variables
        // 5. Abstract class can have access modifiers for methods and variables, whereas all the
        // methods and variables in an interface are public by default



    }
}
