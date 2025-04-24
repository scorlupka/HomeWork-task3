package playable;
import java.io.Serializable;
public class Healer extends MyCharacter implements Serializable {
    public Healer(int x, int y) {
        super(x,y,120,0,3,MyCharacterTypes.HEALER);
    }
}
