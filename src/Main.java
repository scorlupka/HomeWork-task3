import partOfGame.Computer;
import partOfGame.Game;
import partOfGame.Map;
import partOfGame.Player;
import playable.Crusader;
import playable.Skeleton;

public class Main {
    public static void main(String[] args) {

        Crusader crusader = new Crusader(0, 0);
        Skeleton skeleton = new Skeleton(19, 9);
        Map map = new Map(2, 5);

        Game game = new Game();

        Player me = new Player(crusader, map, game);
        Computer computer = new Computer(skeleton, map);

        game = new Game(map, me, computer);
        game.Play();
    }
}