package partOfGame;

import playable.MyCharacter;

import java.io.Serializable;
import java.util.Scanner;

public class BarberHouse {
    final int maximumUsers = 3;
    final long timeForUserN = 600;
    final long timeForUserH = 1800;
    final int priceN = 50;
    final int priceH = 100;

    int currentNumUsers = 0;
    long[] BarbersShedule = new long[maximumUsers];
    private final int startWorkHour = 8;
    private final int endWorkHour = 20;

    public BarberHouse(){
        for(int i =0;i<BarbersShedule.length;i++){
                BarbersShedule[i]=0;
        }
    }                                              
                                                   
    public void addUser(long CurrentTime, MyCharacter hero, Player player){
        refreshBarbersHouse(CurrentTime);

        long curHours = (CurrentTime%86400)/3600;
        Game game = (Game)player.saveHandler;

        //проверка на рабочее время
        if(curHours<startWorkHour || curHours>endWorkHour){
            System.out.println("Barber dosnt work right now");
            return;
        }

        //проверка на наличие свободных мест
        if(currentNumUsers+1>maximumUsers){
            System.out.println("No ampty spaces\nReady to wait?\n1 - yes\n2 - no");
            Scanner scanner = new Scanner(System.in);
            int decision = scanner.nextInt();

            switch (decision){
                case 1:{
                    for(int i=0;i<BarbersShedule.length;i++){
                        if(BarbersShedule[i]>0){
                            game.setCurrentTime(BarbersShedule[i]);
                            refreshBarbersHouse(BarbersShedule[i]);
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

        System.out.println("Which haircut you want?\n1 - Normal\n2- Cool haircut");
        Scanner scanner = new Scanner(System.in);
        int decision = scanner.nextInt();

        switch(decision){
           case 1:{
             if(player.getMoney()<priceN){System.out.println("not enough money"); return;}
                 player.money= player.getMoney()-priceN;
                 game.setCurrentTime(CurrentTime+timeForUserN);
                 break;
           }
           case 2:{
             if(player.getMoney()<priceH){System.out.println("not enough money"); return;}
                 player.money= player.getMoney()-priceH;
                 player.points=1;
                 game.setCurrentTime(CurrentTime+timeForUserH);
                 break;
           }
        }
    }

    public void addNPC(long CurrentTime){
        refreshBarbersHouse(CurrentTime);

        if(currentNumUsers+1>maximumUsers){
            System.out.println("oh-oh(");
            return;
        }

        currentNumUsers++;
        for(int i=0;i<BarbersShedule.length;i++){
            if(BarbersShedule[i]==0){
                BarbersShedule[i]=CurrentTime+10800;
            }
        }
    }

    //выгоняем посетителей которые засиделись
    public void refreshBarbersHouse(long currentTime){
        for(int i=0;i<BarbersShedule.length;i++){
            if(currentTime>=BarbersShedule[i] && BarbersShedule[i]!=0){
                currentNumUsers--;
                BarbersShedule[i]=0;
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

    public long[] getBarbersShedule() {
        return BarbersShedule;
    }

    public void setBarbersShedule(long[] barbersShedule) {
        BarbersShedule = barbersShedule;
    }
}
