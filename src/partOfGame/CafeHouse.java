package partOfGame;

import playable.MyCharacter;

import java.io.Serializable;
import java.util.Scanner;

public class CafeHouse {
    final int maximumUsers = 3;
    final long timeForUserN = 900;
    final long timeForUserH = 1800;
    final int priceN = 50;
    final int priceH = 100;

    int currentNumUsers = 0;
    long[] CafeShedule = new long[maximumUsers];
    private final int startWorkHour = 10;
    private final int endWorkHour = 24;

    public CafeHouse(){
        for(int i =0;i<CafeShedule.length;i++){
            CafeShedule[i]=0;
        }
    }

    public void addUser(long CurrentTime, MyCharacter hero, Player player){
        refreshCafeHouse(CurrentTime);

        long curHours = (CurrentTime%86400)/3600;
        Game game = (Game)player.saveHandler;

        //проверка на рабочее время
        if(curHours<startWorkHour || curHours>endWorkHour){
            System.out.println("Cafe dosnt work right now");
            return;
        }

        //проверка на наличие свободных мест
        if(currentNumUsers+1>maximumUsers){
            System.out.println("No ampty spaces\nReady to wait?\n1 - yes\n2 - no");
            Scanner scanner = new Scanner(System.in);
            int decision = scanner.nextInt();

            switch (decision){
                case 1:{
                    for(int i=0;i<CafeShedule.length;i++){
                        if(CafeShedule[i]>0){
                            game.setCurrentTime(CafeShedule[i]);
                            refreshCafeHouse(CafeShedule[i]);
                            break;
                        }
                    }
                    break;
                }
                default:{
                    return;
                }
            }
        }

        System.out.println("Which meal you want?\n1 - Normal\n2- tasty meal");
        Scanner scanner = new Scanner(System.in);
        int decision = scanner.nextInt();

        switch(decision){
            case 1:{
                if(player.getMoney()<priceN){System.out.println("not enough money"); return;}
                player.money= player.getMoney()-priceN;
                hero.setSpeed(hero.getSpeed()+2);
                game.setCurrentTime(CurrentTime+timeForUserN);
                break;
            }
            case 2:{
                if(player.getMoney()<priceH){System.out.println("not enough money"); return;}
                player.money= player.getMoney()-priceH;
                hero.setSpeed(hero.getSpeed()+3);
                game.setCurrentTime(CurrentTime+timeForUserH);
                break;
            }
        }
    }

    public void addNPC(long CurrentTime){
        refreshCafeHouse(CurrentTime);

        if(currentNumUsers+1>maximumUsers){
            System.out.println("oh-oh(");
            return;
        }

        currentNumUsers++;
        for(int i=0;i<CafeShedule.length;i++){
            if(CafeShedule[i]==0){
                CafeShedule[i]=CurrentTime+10800;
            }
        }
    }

    //выгоняем посетителей которые засиделись
    public void refreshCafeHouse(long currentTime){
        for(int i=0;i<CafeShedule.length;i++){
            if(currentTime>=CafeShedule[i] && CafeShedule[i]!=0){
                currentNumUsers--;
                CafeShedule[i]=0;
            }
        }
    }

    public int getMaximumUsers() {
        return maximumUsers;
    }

    public long getTimeForUserN() {
        return timeForUserN;
    }

    public int getCurrentNumUsers() {
        return currentNumUsers;
    }

    public void setCurrentNumUsers(int currentNumUsers) {
        this.currentNumUsers = currentNumUsers;
    }

    public long[] getCafeShedule() {
        return CafeShedule;
    }

    public void setCafeShedule(long[] barbersShedule) {
        CafeShedule = barbersShedule;
    }
}
