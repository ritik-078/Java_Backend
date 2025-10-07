package interfaces.extend;

public interface B extends A{
    default void greeting2(){
        System.out.println("Greeting2 B");
    }
}
