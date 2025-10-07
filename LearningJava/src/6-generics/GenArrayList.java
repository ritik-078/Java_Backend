package generics;
import java.util.Arrays;

// https://docs.oracle.com/javase/tutorial/java/generics/restrictions.html#createObjects

// Wildcards are used to define the types allowed by declaring a upper bound
// Int, Float , Double are type of Numbers
//public class GenArrayList<T extends Number> {

public class GenArrayList<T> {

    private Object[] data;
    private static final int DEFAULT_SIZE = 10;
    private int size = 0; // also working as index value

    public GenArrayList() {
        data = new Object[DEFAULT_SIZE];
    }

    public void add(T num) {
        if (isFull()) {
            resize();
        }
        data[size++] = num;
    }

    private void resize() {
        Object[] temp = new Object[data.length * 2];

        // copy the current items in the new array
        System.arraycopy(data, 0, temp, 0, data.length);
        data = temp;
    }

    private boolean isFull() {
        return size == data.length;
    }

    public T remove() {
        return (T)(data[--size]);
    }

    public T get(int index) {
        return (T)data[index];
    }

    public int size() {
        return size;
    }

    public void set(int index, T value) {
        data[index] = value;
    }

    @Override
    public String toString() {
        return "CustomGenArrayList{" +
                "data=" + Arrays.toString(data) +
                ", size=" + size +
                '}';
    }

    public static void main(String[] args) {
        GenArrayList list = new GenArrayList();


        GenArrayList<Integer> list3 = new GenArrayList<>();
        for (int i = 0; i < 14; i++) {
            list3.add(2 * i);
        }

        System.out.println(list3);

    }
}