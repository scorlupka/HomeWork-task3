package placable;
import partOfGame.BarberHouse;

import java.io.Serializable;

public class Barber extends MyObject implements Serializable {

    BarberHouse house;
    public Barber(int x, int y, BarberHouse house) {
        super(x,y,MyObjectTypes.BARBER,0);
        this.house = house;
    }

    public BarberHouse getHouse() {
        return house;
    }

    public void setHouse(BarberHouse house) {
        this.house = house;
    }
}
