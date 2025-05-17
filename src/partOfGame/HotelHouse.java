package partOfGame;

import playable.MyCharacter;

import java.io.Serializable;
import java.util.Scanner;

public class HotelHouse {
    final int maximumUsers = 3;
    final long timeForUserN = 86400;
    final long timeForUserH = 259200;
    final int priceN = 100;
    final int priceH = 150;

    int currentNumUsers = 0;
    long[] HotelShedule = new long[maximumUsers];
    private final int startWorkHour = 8;
    private final int endWorkHour = 22;

    public HotelHouse(){
        for(int i =0;i<HotelShedule.length;i++){
            HotelShedule[i]=0;
        }
    }

    public void addUser(long CurrentTime, MyCharacter hero, Player player){
        refreshHotelHouse(CurrentTime);

        long curHours = (CurrentTime%86400)/3600;
        Game game = (Game)player.saveHandler;

        //проверка на рабочее время
        if(curHours<startWorkHour || curHours>endWorkHour){
            System.out.println("Hotel dosnt work right now");
            return;
        }

        //проверка на наличие свободных мест
        if(currentNumUsers+1>maximumUsers){
            System.out.println("No ampty spaces\nReady to wait?\n1 - yes\n2 - no");
            Scanner scanner = new Scanner(System.in);
            int decision = scanner.nextInt();

            switch (decision){
                case 1:{
                    for(int i=0;i<HotelShedule.length;i++){
                        if(HotelShedule[i]>0){
                            game.setCurrentTime(HotelShedule[i]);
                            refreshHotelHouse(HotelShedule[i]);
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

        System.out.println("Which room you want?\n1 - Normal\n2- large room");
        Scanner scanner = new Scanner(System.in);
        int decision = scanner.nextInt();

        switch(decision){
            case 1:{
                if(player.getMoney()<priceN){System.out.println("not enough money"); return;}
                player.money= player.getMoney()-priceN;

                for(int i=0;i<hero.getUnits().size();i++){
                    hero.getUnits().get(i).setHP(hero.getUnits().get(i).getHP()+20);
                }

                game.setCurrentTime(CurrentTime+timeForUserN);
                break;
            }
            case 2:{
                if(player.getMoney()<priceH){System.out.println("not enough money"); return;}
                player.money= player.getMoney()-priceH;

                for(int i=0;i<hero.getUnits().size();i++){
                    hero.getUnits().get(i).setHP(hero.getUnits().get(i).getHP()+50);
                }

                game.setCurrentTime(CurrentTime+timeForUserH);
                break;
            }
        }
    }

    public void addNPC(long CurrentTime){
        refreshHotelHouse(CurrentTime);

        if(currentNumUsers+1>maximumUsers){
            System.out.println("oh-oh(");
            return;
        }

        currentNumUsers++;
        for(int i=0;i<HotelShedule.length;i++){
            if(HotelShedule[i]==0){
                HotelShedule[i]=CurrentTime+10800;
            }
        }
    }

    //выгоняем посетителей которые засиделись
    public void refreshHotelHouse(long currentTime){
        for(int i=0;i<HotelShedule.length;i++){
            if(currentTime>=HotelShedule[i] && HotelShedule[i]!=0){
                currentNumUsers--;
                HotelShedule[i]=0;
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

    public long[] getHotelShedule() {
        return HotelShedule;
    }

    public void setHotelShedule(long[] barbersShedule) {
        HotelShedule = barbersShedule;
    }
}
