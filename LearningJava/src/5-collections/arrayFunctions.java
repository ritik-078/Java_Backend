package collections;

import java.util.Arrays;
import java.util.List;

public class arrayFunctions {
    public static void main(String[] args) {
        String[] strs = {"apple", "banana", "orange"};
        List<String> list = Arrays.asList(strs);

        int[] array = {1, 3, 5, 7, 9};
        int index = Arrays.binarySearch(array, 5);

        String stringRepresentation = Arrays.toString(array);

        Arrays.sort(array);

        int[] newArray = Arrays.copyOf(array, 3);

        int[] newArray2 = Arrays.copyOf(array, 3);

        int[] array1 = {1, 2, 3};
        int[] array2 = {1, 2, 3};
        boolean equal = Arrays.equals(array1, array2);



    }
}
