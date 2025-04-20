package placable;

public class Coin extends MyObject {
    public Coin(int x, int y, MyObject underObject) {
        super(x,y,MyObjectTypes.COIN,0);
        this.underObject=underObject;
    }
}