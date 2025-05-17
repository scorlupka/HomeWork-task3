package partOfGame;

import placable.Barber;

import static placable.MyObjectTypes.BARBER;

public class NPC {
    private String name;
    private int shedule;
    private BarberHouse barber;
    private Game game;

    private final int[][][] availableShedules={
            { {0,8}, {1,16} },
            { {0,8}, {2,18} },
            { {0,8},{1,12},{2,20} }
    };

    public NPC(String name, int shedule,Game game){
        this.name = name;
        this.game=game;
        this.shedule = shedule;
        for (int i = 0; i < game.getMap().getObjects().length; i++) {
            for (int j = 0; j < game.getMap().getObjects()[i].length; j++) {
                if (game.getMap().getObjects()[i][j] != null && game.getMap().getObjects()[i][j].getType() == BARBER) {
                    Barber barber1 = (Barber)(game.getMap().getObjects()[i][j]);
                    this.barber = barber1.getHouse();
                }
            }
        }
    }

    public void doAction(int time){//время в часах!!!
        for(int i=0;i<availableShedules[shedule].length;i++){
            if(time==availableShedules[shedule][i][1]){
                executeAction(availableShedules[shedule][i][0]);
            }
        }
    }

    private void executeAction(int action){
        switch (action){
            case 0:
                goToBarber();
                break;
            case 1:
                goToHotel();
                break;
            case 2:
                goToCafe();
                break;
            default:
                break;
        }
    }

    private void goToBarber(){
        System.out.println(name + " went to barber");
        barber.addNPC(game.getCurrentTime());
    }

    private void goToHotel(){

    }

    private void goToCafe(){
    }

}
