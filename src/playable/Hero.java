package playable;

import java.util.ArrayList;

public interface Hero {

    ArrayList<MyCharacter> getUnits();

    void addUnits(MyCharacter unit);

    void setHoldsTheGrail(boolean isHoldingTheGrail);
}

