package javaObj;

public class Human {
    int age;
    String name;
    public static int population=0;
    //static variable is shared among all instances of the class
    //it is not tied to any specific instance, can be called by class reference

    // static method can access only static data
    // static block is used to initialize static variables
    // called when class is loaded by classloader
    // static
    // {
    //     population=100;
    // }

    // instance variable is tied to specific instance of the class, can't be called 
    // by class reference or static method
    // to use instance variable in a static method, we need to create an instance of the 
    // class and pass it to the static method


    public Human(int age, String name){
        this.name = name;
        this.age = age;
        population++;
    }
}
