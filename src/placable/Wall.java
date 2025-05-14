package placable;

import java.io.Serializable;

public class Wall extends MyObject implements Serializable {
    public Wall(int x, int y) {
        super(x,y,MyObjectTypes.WALL,0);
    }
}