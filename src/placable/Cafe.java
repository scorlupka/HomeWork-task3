package placable;
import partOfGame.CafeHouse;

import java.io.Serializable;

public class Cafe extends MyObject implements Serializable {

    CafeHouse house;
    public Cafe(int x, int y, CafeHouse house) {
        super(x,y,MyObjectTypes.CAFE,0);
        this.house = house;
    }

    public CafeHouse getHouse() {
        return house;
    }

    public void setHouse(CafeHouse house) {
        this.house = house;
    }
}
