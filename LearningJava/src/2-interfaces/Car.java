package interfaces;

public class Car implements Brake, Engine{

    @Override
    public void brake() {
        System.out.println("Braking");
    }

    @Override
    public void start() {
        System.out.println("Starting");
    }

    @Override
    public void stop() {
        System.out.println("Stopping");
    }

    @Override
    public void acc() {
        System.out.println("Accelerating");
    }
}
