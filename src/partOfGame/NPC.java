package partOfGame;

public class NPC {
    private String name;
    private int shedule;

    private final int[][][] availableShedules={
            { {0,8}, {1,16} },
            { {1,11}, {2,18} },
            { {0,6},{1,12},{2,20} }
    };

    public NPC(String name, int shedule){
        this.name = name;
        this.shedule = shedule;
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
                goToBareber();
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

    private void goToBareber(){
        System.out.println(this.name + " go to the barber");
    }

    private void goToHotel(){
        System.out.println(this.name + " go to the Hotel");
    }

    private void goToCafe(){
        System.out.println(this.name + " go to the Cafe");
    }

}
