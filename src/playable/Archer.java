package playable;
import java.io.Serializable;

public class Archer extends MyCharacter implements Serializable {
    public Archer(int x, int y) {
        super(x,y,70,30,3,MyCharacterTypes.ARCHER);
    }
}
