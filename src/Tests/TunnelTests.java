package Tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;
import partOfGame.*;
import placable.MyObjectTypes;
import placable.tunnel;
import playable.Crusader;
import playable.Healer;
import playable.Skeleton;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

public class TunnelTests {

    @Rule
    public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

    private Game game;
    private Map map;
    private Player me;
    Crusader crusader;
    Skeleton skeleton;
    Computer computer;
    @Before
    public void setUp() {
        // Инициализация объектов перед каждым тестом
        crusader = new Crusader(0, 0);
        skeleton = new Skeleton(19, 9);
        map = new Map(2, 5);

        me = new Player(crusader, map, new Game());
        computer = new Computer(skeleton, map);

        game = new Game(map, me, computer);
        me.setPoints(1);
        computer.setPoints(1);
    }

    @Test
    public void TunnelWorkTest(){
        systemInMock.provideLines( "1","0","3","0");
        Game game = new Game();

        Crusader crusader = new Crusader(0, 0);
        Skeleton skeleton = new Skeleton(19,9);
        Map map = new Map(2, 5);
        Player me = new Player(crusader, map, null);
        Computer computer = new Computer(skeleton, map);
        game = new Game(map, me, computer);
        me.setSaveHandler(game);

        me.makeMove(1);
        map = new Map(2, 5);
        assertNotEquals(game.getMap().getCharacters()[3][0], game.getMe().getHeroes().get(0));
        assertEquals(game.getMap().getCharacters()[7][0], game.getMe().getHeroes().get(0));
    }

    @Test
    public void AbleToDigTunnel(){
        crusader.setX(1);
        systemInMock.provideLines("9", "0", "5", "0");

        me.makeMove(1);

        assertEquals(map.getObjects()[1][0].getType(), MyObjectTypes.TUNNEL);
        assertEquals(map.getObjects()[5][0].getType(), MyObjectTypes.TUNNEL);
    }

    @Test
    public void NotAbleToDigTunnelInCastle(){
        systemInMock.provideLines("9", "0", "0", "5");

        me.makeMove(1);

        assertNotEquals(map.getObjects()[0][0].getType(), MyObjectTypes.TUNNEL);
        assertNotEquals(map.getObjects()[0][5].getType(), MyObjectTypes.TUNNEL);
    }

    @Test
    public void LongTunnelWilCollapse(){
        crusader.setX(2);
        systemInMock.provideLines("9", "0", "19", "9");

        me.makeMove(1);

        assertNotEquals(map.getCharacters()[2][0], crusader);
        assertNotEquals(map.getCharacters()[19][9], crusader);

        assertNotEquals(map.getObjects()[2][0].getType(), MyObjectTypes.TUNNEL);
        assertNotEquals(map.getObjects()[19][9].getType(), MyObjectTypes.TUNNEL);
    }
}
