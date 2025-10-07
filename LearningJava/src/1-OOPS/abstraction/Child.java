package abstraction;

public class Child extends Parent{
    int age;
    String name;

    Child(int age, String name){
        this.age = age;
        this.name  = name;
    }

    @Override
    void career(String careerName){
        System.out.println(name + "'s career choice: " + careerName);
    }

    @Override
    void partner(String name, int age){
        System.out.println(this.name + "'s partner: " + name + ", " + age + " years old");
    }
}
