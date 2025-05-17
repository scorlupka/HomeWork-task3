package placable;
import partOfGame.HotelHouse;

import java.io.Serializable;

public class Hotel extends MyObject implements Serializable {

    HotelHouse house;
    public Hotel(int x, int y, HotelHouse house) {
        super(x,y,MyObjectTypes.HOTEL,0);
        this.house = house;
    }

    public HotelHouse getHouse() {
        return house;
    }

    public void setHouse(HotelHouse house) {
        this.house = house;
    }
}
