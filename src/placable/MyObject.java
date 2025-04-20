package placable;

public abstract class MyObject {
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
