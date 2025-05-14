package placable;

import java.io.Serializable;

public class Coin extends MyObject implements Serializable {
    public Coin(int x, int y, MyObject underObject) {
        super(x,y,MyObjectTypes.COIN,0);
        this.underObject=underObject;
    }
}