package partOfGame;

public class GameClock extends Thread{
    private volatile long gameTime;
    private volatile boolean isRunning;
    private NPC[] NPCs;

    public GameClock(long gameTime){
        this.gameTime=gameTime;
        this.isRunning=true;
    }


    @Override
    public void run(){
        try{
            while(isRunning){
                Thread.sleep(100);
                gameTime+=60;

                if(gameTime%86400==0){
                    System.out.println(Long.toString(gameTime/86400+1)+" day");
                }

                if(gameTime%3600==0){
                    System.out.println(((gameTime%86400)/3600));
                    activateNPCs();
                }
            }
        }catch(InterruptedException e){
            System.out.println("Something gone wrong with time");
        }
    }

    public void pause(){
        isRunning = false;
    }

    public void resumeClock(){
        isRunning = true;
    }

    public long getGameTime(){
        return gameTime;
    }

    public void setNPCs(NPC[] NPCs){
        this.NPCs = NPCs;
    }
    public NPC[] getNPCs(NPC[] NPCs){
        return NPCs;
    }

    private void activateNPCs(){
        int time = (int)((gameTime%86400)/3600);
        for(int i=0;i<NPCs.length;i++){
            NPCs[i].doAction(time);
        }
    }

    public void setGameTime(long gameTime) {
        this.gameTime = gameTime;
    }
}
