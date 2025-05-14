package placable;

import java.io.Serializable;

public class Artefact extends MyObject implements Serializable {
    public Artefact(int x, int y, MyObject underObject) {
        super(x,y,MyObjectTypes.ARTEFACT,0);
        this.underObject=underObject;
    }

}