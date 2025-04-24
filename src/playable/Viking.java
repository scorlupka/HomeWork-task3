package playable;

import java.io.Serializable;

public class Viking extends MyCharacter implements Serializable {
    public Viking(int x, int y) {
        super(x,y,120,1000,3,MyCharacterTypes.VIKING);
    }

}