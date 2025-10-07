package interfaces.extend;

public interface A {

    // static method in interfaces need to be defined
    // static void greeting(){ System.out.println("Greeting"); }
    void greeting();

    // default methods
    //default void greeting2(){ System.out.println("Greeting2");

    // access modifier of the over-ridden function cannot be more restrictive
    // than defined in interface
}
