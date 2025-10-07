package collections;
import java.util.*;

public class demoList {
    public static void main(String[] args) {

        ArrayList<Integer> list1 = new ArrayList<>();
        list1.add(41);
        list1.add(12);
        list1.add(3);
        list1.add(14);
        list1.add(5);

//        System.out.println(list1);
//        System.out.println(list1.get(1));
//        System.out.println(list1.set(1,6)); // set the value at specified index and return prev element
//        System.out.println(list1);

//        Collections.sort(list1); //Sort the array
//        System.out.println(list1);


//        ArrayList<String> list2 = new ArrayList<>();
//        list2.add("Hi");
//        list2.add("Ritik");
//        list2.add("DTU");
//        list2.add("COE");

//        Iterator it = list2.iterator(); //getting the Iterator
//        while(it.hasNext()){    //check if iterator has the elements
//            System.out.println(it.next());//printing the element and move to next
//        }
//
//        for (String s : list2) {
//            System.out.println(s);
//        }


        LinkedList<Integer> ll = new LinkedList<Integer>();
        ll.add(123);
        ll.addAll(list1);
        System.out.println(ll);




        List<String> al=new ArrayList<String>();
        al.add("Amit");
        al.add("Vijay");
        al.add("Kumar");
        al.add(1,"Sachin");
        ListIterator<String> itr=al.listIterator();
        System.out.println("Traversing elements in forward direction");
        while(itr.hasNext()){

            System.out.println("index:"+itr.nextIndex()+" value:"+itr.next());
        }
        System.out.println("Traversing elements in backward direction");
        while(itr.hasPrevious()){

            System.out.println("index:"+itr.previousIndex()+" value:"+itr.previous());
        }



    }
}
