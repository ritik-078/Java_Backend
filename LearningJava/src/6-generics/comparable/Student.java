package generics.comparable;

public class Student implements Comparable<Student>{
    int rollNo;
    float marks;

    public Student(int rno, float marks){
        this.rollNo = rno;
        this.marks = marks;
    }
    // Comparable interface is used to explicitly define the comparison
    // parameter to compare objects
    @Override
    public int compareTo(Student o) {
        // return Float.compare(this.marks, o.marks);
        if(this.marks == o.marks)
            return 0;
        else if(this.marks > o.marks)
            return 1;
        else
            return -1;
    }
}
