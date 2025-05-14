package placable;

import java.io.Serializable;

public class HolyGrail extends MyObject implements Serializable {
    public HolyGrail(int x, int y, MyObject underObject) {
        super(x,y,MyObjectTypes.HOLYGRAIL,0);
        this.underObject=underObject;
    }
}
