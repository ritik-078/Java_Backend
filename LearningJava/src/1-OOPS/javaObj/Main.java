package javaObj;
// import staticExample.Human;

public class Main {
    public static void main(String[] args) {

        //Student[] s1 = new Student[20];
        Student s1 = new Student(19,"Ritik",89f);
        Student s2 = new Student(s1);
        System.out.println(s1);
        s2.showDetails();
 


        // static keyword
        // Human h = new Human(23, "Ritik");
        //  System.out.println(h.population);

        // 
    }
}
