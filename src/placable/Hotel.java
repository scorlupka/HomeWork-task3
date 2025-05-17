package placable;
import java.io.Serializable;

public class Hotel extends MyObject implements Serializable {
    private final int startWorkHour = 8;
    private final int endWorkHour = 20;

    private final int maxCustomersNum = 3;
    private final int oneHairCutDuration = 1800;
    public Hotel(int x, int y) {
        super(x,y,MyObjectTypes.HOTEL,0);
    }

    public int getStartWorkHour() {
        return startWorkHour;
    }

    public int getEndWorkHour() {
        return endWorkHour;
    }

    public int getMaxCustomersNum() {
        return maxCustomersNum;
    }

    public int getOneHairCutDuration(){
        return oneHairCutDuration;
    }
}
