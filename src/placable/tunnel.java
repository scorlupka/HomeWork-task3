package placable;

import java.io.Serializable;

public class tunnel extends MyObject implements Serializable {

    public tunnel(int x, int y) {
        super(x,y,MyObjectTypes.TUNNEL,0);
    }

    public tunnel(int x, int y, int toX, int toY) {
        super(x,y,MyObjectTypes.TUNNEL,0);
        this.connectionPoint = new int[]{toX,toY};
    }

    private int connectionPoint[];

    public void setConnectionPoint(int[] connectionPoint) {
        this.connectionPoint = connectionPoint;
    }

    public int[] getConnectionPoint() {
        return connectionPoint;
    }
}
