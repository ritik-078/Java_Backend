package streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        List<Integer> nums = Arrays.asList(12,52,3,34,5);

        Stream<Integer> data = nums.stream(); // returns a stream
//        System.out.println(data.count()); // count() will use the stream amd makes it empty
//        data.forEach(n -> System.out.println(n));
        // changes to stream will not be reflected in original array
        // Stream can be operated only once, once used it becomes empty
        //

//        Stream<Integer> sortedData = data.sorted();
//        sortedData.forEach(n -> System.out.println(n));

//        Stream<Integer> mappedData = data.map(n -> n*3);
//        mappedData.forEach(n -> System.out.println(n));

        Stream<Integer> filterData = data.filter(n -> n%2==0);
        filterData.forEach(n -> System.out.println(n));

    }
}
