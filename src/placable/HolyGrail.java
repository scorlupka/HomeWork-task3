package placable;

public class HolyGrail extends MyObject {
    public HolyGrail(int x, int y, MyObject underObject) {
        super(x,y,MyObjectTypes.HOLYGRAIL,0);
        this.underObject=underObject;
    }
}
