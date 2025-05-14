package partOfGame;
import java.util.concurrent.TimeUnit;

public class GameClock extends Thread{
    private volatile long gameTime;
    private volatile boolean isRunning;

    public GameClock(long gameTime){
        this.gameTime=gameTime;
        this.isRunning=true;
    }

    @Override
    public void run(){
        try{
            while(isRunning){
                TimeUnit.SECONDS.

            }
        }
    }
}
