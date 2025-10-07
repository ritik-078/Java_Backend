package polymorphism;

public class Main {
    public static void main(String[] args){
        Shapes s = new Shapes();
        Circle c = new Circle();
        Triangle t = new Triangle();
        Square sq = new Square();

        s.area();
        c.area();
        t.area();
        sq.area();

        System.out.println(s);
        System.out.println(c);
        System.out.println(t);
        System.out.println(sq);

        // child class object created by parent class reference, here what it can access is
        //defined by reference(i.e parent class), but which version of the  function will be
        // called will be determined by object type(i.e. child class), known as Upcasting
        // parent class need to have the function defined so that it can access it
        Shapes c2 = new Circle();
        // Dynamic method dispatch determines which function to call at runtime


        // Early Binding : When a function is declared final in parent class, it can't be
        // over-ride and call to it can be resolved at compile time only

        // Late Binding : Function overriding when call to a function is resolved at run-time

        // Static -> independent of objects whereas Overriding -> depends on objects
        // Static methods can't be over-ride, call to static is object independent, always
        // calls base class static method

        // two types of polymorphism
        // 1. Compile time polymorphism : method overloading, operator overloading
        // 2. Run time polymorphism : method overriding

        // dynamic method dispatch
        // when a parent class reference is used to refer to a child class object
        // method to be called is determined at runtime by the object type
        // method to be accessed is determined at compile time by the reference type

        
    

    }
}
