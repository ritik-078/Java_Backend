package inheritance;

public class Box {
    double l, w, h;

    Box() {
        this.l = -1;
        this.w = -1;
        this.h = -1;
    }

    Box(double side){
        this.l = side;
        this.w = side;
        this.h = side;
    }

    Box(double l, double w, double h){
        this.l = l;
        this.w = w;
        this.h = h;
    }

    Box(Box old){
        this.l = old.l;
        this.w = old.w;
        this.h = old.h;
    }

    void showBox(){
        System.out.println(this.l + " " + this.w + " " + this.h);
    }

    void print(){
        System.out.println("In Parent class");
    }
}
