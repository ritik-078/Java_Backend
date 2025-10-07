package generics.comparable;

public class Main {
    public static void main(String[] args){
        Student Ritik = new Student(21,9.9f);
        Student Parth = new Student(12,89.9f);

        if(Ritik.compareTo(Parth)>0){
            System.out.println("Ritik has more marks");
        }
        else{
            System.out.println("Parth has more marks");
        }
    }
}
