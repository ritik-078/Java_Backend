package interfaces;

import access.A;

public class Main {
    public static void main(String[] args){
//        Car Maruti = new Car();
//        Maruti.start();
//        Maruti.acc();
//        Maruti.brake();
//        Maruti.stop();

        // Here Loose Coupling is demonstrated
        // Coupling between the NiceCar and type of engine is loose as engines types 
        // are dynamically assigned through Engine interface

        Engine eng = new PowerEngine();
        Engine eng2 = new ElectricEngine();
        NiceCar Audi = new NiceCar(eng);
        Audi.start();
        Audi.stop();
        Audi.acc();
        Audi.startMusic();
        Audi.stopMusic();
        Audi.changeEngine(eng2);
        Audi.start();
        Audi.stop();
        Audi.acc();

        /*

 * 1. Loose Coupling:
 *    - Loose coupling means classes are independent of each other's concrete implementations.
 *    - In this example, NiceCar depends on the Engine interface, not a specific engine type.
 *    - This allows changing the engine at runtime, making the design flexible and extensible.
 *
 * 2. Interface Usage:
 *    - The Engine interface defines the contract for engine behavior.
 *    - PowerEngine and ElectricEngine implement Engine, providing different behaviors.
 *    - NiceCar can work with any Engine implementation, promoting abstraction.
 *
 * 3. Benefits:
 *    - Easier to maintain and extend code.
 *    - New engine types can be added without modifying NiceCar.
 *    - Demonstrates the power of interfaces in Java OOP.
 */

 // Functional Interface  
 // A functional interface is an interface that contains only one abstract method
 // It can have multiple default and static methods
 // purpose of functional interface is to provide a target type for lambda expressions and method references
 // can be annotated with @FunctionalInterface annotation

// Types of interfaces
// 1. Marker Interface: An interface with no methods, used to mark a class for a specific purpose
// 2. Functional Interface: An interface with a single abstract method, used for lambda expressions
// 3. Normal Interface: An interface with multiple abstract methods
 
    }
}
