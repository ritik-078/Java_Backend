package generics;

public class Main {
    public static void main(String[] args) {
        GenericDemo<Integer, String> gen = new GenericDemo(23, "Ritik");
        System.out.println(gen.get1());
        System.out.println(gen.get2());
    }
}
