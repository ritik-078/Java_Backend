package cloning;
import java.util.Arrays;
public class Main {
    public static void main(String[] args) throws CloneNotSupportedException{
        Human Ritik = new Human(23,"Ritik");
//        Human twin = new Human(Ritik);
//        Ritik.show();
//        twin.show();
//        System.out.println(Ritik + " " + twin);

        // we can make a clone of an object like this, but this takes time as new keywords
        // is used , memory is assigned, there is a faster way with clone() method

        Human twin2 = Ritik.clone(); // faster way, shallow copy
        // for deep copy, we have to explicitly clone object references
        twin2.show();
        System.out.println(twin2);

        // Shallow copy: In the process of object cloning, if there are nested objects
        // in shallow copy sub objects are not cloned whereas referenced to the original
        // changes will be reflected in both original and cloned object

        // Depp copy: In the process of object cloning, if there are nested objects
        // in deep copy sub objects are also cloned.


        Human Parth = new Human(8, "Parth");
//        Human twin = new Human(Parth);

        Human twin = Parth.clone();
        twin.show();
        System.out.println(Arrays.toString(twin.arr));

        twin.arr[0] = 100;

        System.out.println(Arrays.toString(twin.arr));
        System.out.println(Arrays.toString(Parth.arr));
    }
}
