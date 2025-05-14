package placable;

import java.io.Serializable;

public class Road extends MyObject implements Serializable {
    public Road(int x, int y) {
        super(x,y,MyObjectTypes.ROAD,1);
    }
}