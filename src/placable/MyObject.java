package placable;

import java.io.Serializable;

public abstract class MyObject implements Serializable {
    protected MyObject underObject;
    protected int x;
    protected int y;
    protected MyObjectTypes type;

    MyObject(int x, int y,MyObjectTypes Type, int movementPenalty){
        this.x = x;
        this.y = y;
        this.type = Type;
        this.movementPenalty = movementPenalty;
        this.underObject = this;
    }

    protected int movementPenalty;

    public void setUnderObject(MyObject underObject) {
        this.underObject = underObject;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setType(MyObjectTypes type) {
        this.type = type;
    }

    public void setMovementPenalty(int movementPenalty) {
        this.movementPenalty = movementPenalty;
    }

    public MyObjectTypes getType() {
        return type;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getMovementPenalty() {
        return movementPenalty;
    }

    public MyObject getUnderObject(){
        return underObject;
    };
}
