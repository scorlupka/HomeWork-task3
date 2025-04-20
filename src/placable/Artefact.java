package placable;

public class Artefact extends MyObject {
    public Artefact(int x, int y, MyObject underObject) {
        super(x,y,MyObjectTypes.ARTEFACT,0);
        this.underObject=underObject;
    }
}