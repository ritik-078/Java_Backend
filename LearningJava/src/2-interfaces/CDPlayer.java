package interfaces;

public class CDPlayer implements MediaPlayer{
    @Override
    public void start() {
        System.out.println("CD Player starts");
    }

    @Override
    public void stop() {
        System.out.println("CD Player stops");
    }
}
