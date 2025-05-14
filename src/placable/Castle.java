package placable;

import java.io.Serializable;

public class Castle extends MyObject implements Serializable {
    public Castle(int x, int y) {
        super(x,y,MyObjectTypes.CASTLE,0);
    }
}