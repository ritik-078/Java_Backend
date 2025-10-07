package cloning;

public class Human implements Cloneable{
    int age;
    String name;
    int[] arr;

    public Human(int age, String name){
        this.age = age;
        this.name = name;
        arr = new int[]{1,2,3,4,5};
    }

    public Human(Human h){
        this.age = h.age;
        this.name = h.name;
        arr = new int[]{1,2,3,4,5};
    }

    public void show(){
        System.out.println(this.name + ":" + this.age);
    }

    @Override
    public Human clone() throws CloneNotSupportedException {
        Human twin = (Human) super.clone();
        twin.arr = new int[twin.arr.length];
        System.arraycopy(this.arr, 0, twin.arr, 0, twin.arr.length);
        return twin;
    }
}
