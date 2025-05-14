package placable;
import java.io.Serializable;

public class Barber extends MyObject implements Serializable {
    public Barber(int x, int y) {
        super(x,y,MyObjectTypes.BARBER,0);
    }
}
