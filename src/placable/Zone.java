package placable;

import java.io.Serializable;

public class Zone extends MyObject implements Serializable {
    public Zone(int x, int y) {
        super(x,y,MyObjectTypes.ZONE,1);
    }
}