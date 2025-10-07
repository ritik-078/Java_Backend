package singleton;

public class Main {
    public static void main(String[] args) {
        SingletonClass obj1 =  SingletonClass.getInstance();
        SingletonClass obj2 =  SingletonClass.getInstance();
        SingletonClass obj3 =  SingletonClass.getInstance();


        System.out.println(obj1 + " " + obj2 + " " + obj3);
    }
}

