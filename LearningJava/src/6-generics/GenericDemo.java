package generics;

public class GenericDemo<T1, T2> {
    private T1 t1;
    private T2 t2;
    GenericDemo(T1 t1, T2 t2){
        this.t1 = t1;
        this.t2 = t2;
    }

    T2 get2(){
        return t2;
    }
    T1 get1(){
        return t1;
    }
}
