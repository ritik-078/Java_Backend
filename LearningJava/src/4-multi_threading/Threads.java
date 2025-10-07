package multi_threading;

public class Threads extends Thread{
    long num;
    Threads(long num) {
        this.num = num;
    }

    @Override
    public synchronized void start() {
        System.out.println("Thread started");
    }

    public void run() {
        System.out.println("Thread running");
    }
}
