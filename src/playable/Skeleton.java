package playable;

import java.util.ArrayList;

public class Skeleton extends MyCharacter implements Hero {
    private ArrayList<MyCharacter> units = new ArrayList<>();

    public Skeleton(int x, int y) {
        super(x,y,25,25,2,MyCharacterTypes.SKELETON);
        this.isEvil = true;
    }

    @Override
    public ArrayList<MyCharacter> getUnits() {
        return units;
    }

    @Override
    public void addUnits(MyCharacter unit) {
        units.add(unit);
    }

    @Override
    public void setHoldsTheGrail(boolean isHoldingTheGrail) {
        this.holdsTheGrail = isHoldingTheGrail;
    }
}