package interfaces;


// NiceCar -> Engine -> Power Engine
//                   -> Electric Engine
// NiceCar -> Media Player -> CD Player
//                         -> Any other Player
public class NiceCar{
    private Engine engine;
    private  MediaPlayer player = new CDPlayer();
    public NiceCar(){
        engine = new ElectricEngine();
    }
    public NiceCar(Engine engine){
        this.engine = engine;
    }

    public void start(){
        engine.start();
    }
    public void stop(){
        engine.stop();
    }
    public void acc(){
        engine.acc();
    }

    public void startMusic(){
        player.start();
    }
    public void stopMusic(){
        player.stop();
    }

    public void changeEngine(Engine engine){
        this.engine = engine;
    }
}
