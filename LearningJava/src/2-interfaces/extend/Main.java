package interfaces.extend;

public class Main implements A,B{
    //When one interface extend another interface and we implement interface(that
    // extends another interface), we have to define the methods in the both the
    // interfaces
    // all methods in interface are by default public and abstract
    // all variables in interface are by default public, static and final
    // final variables need to be initialized at the time of declaration only  
    // static method in interface can be called using interface name
    // static methods in interface can't be overridden in implementing class
    // default methods in interface can be overridden in implementing class
    // need for interface is to achieve multiple inheritance in java
    // interface is a blueprint of a class, it has static constants and abstract methods

    @Override
    public void greeting2() {
        System.out.println("Greeting");
    }

    @Override
    public void greeting() {
        System.out.println("Greeting 2");
    }

    public static void main(String[] args){

    }
}
