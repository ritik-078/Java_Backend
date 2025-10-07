package generics.comparator;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        Student s1 = new Student(23, "Ritik", 99);
        Student s2 = new Student(8, "Parth", 82);
        Student s3 = new Student(22,"Rohit", 69);
        Student s4 = new Student(24, "Satyam",46);

        ArrayList<Student> students = new ArrayList<Student>();
        students.add(s1);
        students.add(s2);
        students.add(s3);
        students.add(s4);

        System.out.println("Before Sorting:");
        for(Student s : students)
        {
            System.out.println(s.name + " " + s.age + " " + s.marks );
        }

        students.sort(new AgeComparator());
        System.out.println("Sorting on basis of age:");
        for(Student s : students){
            System.out.println(s.name + " " + s.age + " " + s.marks );
        }

        students.sort(new MarksComparator());
        System.out.println("Sorting on basis of marks:");
        for(Student s : students){
            System.out.println(s.name + " " + s.age + " " + s.marks );
        }

    }
}
