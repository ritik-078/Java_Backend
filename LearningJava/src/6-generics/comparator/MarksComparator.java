package generics.comparator;

import java.util.Comparator;

public class MarksComparator implements Comparator<Student> {
    @Override
    public int compare(Student s1, Student s2) {
        if(s1.marks==s2.marks)
            return 0;
        else if(s1.marks>s2.marks)
            return 1;
        else
            return -1;
    }
}
