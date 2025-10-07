package inheritance;

public class Main {
    public static void main(String[] args) {

//        Box cube = new Box(6);
//        Box cuboid = new Box(1,2,3);
//        cuboid.show();

//        Cube c = new Cube(6);
//        Cube c2 = new Cube(c);
//        System.out.println(c2 + ", Area:" + c2.getArea() + ", Volume:" + c2.getVolume());

        // Parent class object can be initialized with child class constructor
//        Box b1 = new Cube(2);
//        System.out.println(b1);

        //Can't initialize child object with parent class constructor, as variables in
        //child class can't be initialized as parent object can't access child class
        //components
        //Cube c1 = new Box(2);


        // Multi-Level Inheritance
//        PackageBox p1 = new PackageBox(5,true);
//        System.out.println(p1);
//        p1.showBox();
//        p1.showPackageBox();

        // Every class in java extends Object class by default
        // Object class is the parent class of all classes in java
        // Object class has some methods which can be overridden in child class
        // toString(), equals(), hashCode(), getClass(), clone(), finalize()

        // super() keyword
        // super() is used to call parent class constructor from child class constructor
        // super() should be the first line in child class constructor

        // multiple inheritance is not supported in java because of ambiguity
        // if a class inherits from two classes which have a method with same name
        // then which method will be called when we call that method from child class
        // to solve this problem, java provides interface
        // interface is a blueprint of a class, it has static constants and abstract methods
        // a class can implement multiple interfaces    
        // interface is implemented using implements keyword

        // method overriding
        // when a method in child class has same name and parameters as a method in parent class
        // then the method in child class is said to override the method in parent class
        // to override a method, the method in child class should have same name, parameters and
        // return type as the method in parent class
        // @Override annotation is used to indicate that a method is overriding a method in parent class
        // if we want to call the method in parent class from child class, we can use super keyword
        // super.methodName()

        // access modifiers in inheritance
        // private: accessible only within the class
        // default: accessible within the package
        // protected: accessible within the package and in child classes outside the package
        // public: accessible from anywhere
        
        // Upcasting and Downcasting
        // Upcasting: when a parent class reference is used to refer to a child class object
        // Downcasting: when a child class reference is used to refer to a parent class object
        // Downcasting is not allowed in java, it will give compile time error
        // Downcasting can be done using type casting, but it will give runtime error
        // if the object is not of the type of the child class 
        





    }
}
