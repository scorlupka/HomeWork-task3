package placable;

import java.io.Serializable;

public class ground extends MyObject implements Serializable {
    public ground(int x, int y) {
        super(x,y,MyObjectTypes.GROUND,2);
    }
}