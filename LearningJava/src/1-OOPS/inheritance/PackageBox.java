package inheritance;

public class PackageBox extends Cube{
    boolean isPacked;
    double price;

    PackageBox(){
        super();
        this.isPacked = false;
        this.price = 0;
    }

    PackageBox(double side, boolean isPacked){
        super(side);
        this.isPacked = isPacked;
        this.price = super.area * 10;
    }

    void showPackageBox(){
        System.out.println("Box Dimension: " + this.l + ", Price:" + this.price);
    }
}
