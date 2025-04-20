package playable;

import jdk.jfr.StackTrace;

import java.util.ArrayList;

public class Crusader extends MyCharacter implements Hero {
    private ArrayList<MyCharacter> units = new ArrayList<>();

    public Crusader(int x, int y) {
        super(x,y,1,1,100,MyCharacterTypes.HERO);
        this.isEvil = false;
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
