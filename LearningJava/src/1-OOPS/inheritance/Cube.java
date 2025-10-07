package inheritance;

public class Cube extends Box{
    double area, volume;
    Cube(){
        super();
        this.area = -1;
        this.volume = -1;
    }
    Cube(double side){
        super(side);
        this.area = 6*side*side;
        this.volume = side*side*side;
    }

    Cube(Cube c){
        super(c); // calling parent class copy constructor passing child class reference
        this.area = 6*(c.l)*(c.l);
        this.volume = (c.l)*(c.l)*(c.l);
    }

    double getArea(){
        return this.area;
    }
    double getVolume(){
        return this.volume;
    }
}
