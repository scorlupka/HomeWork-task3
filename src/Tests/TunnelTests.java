package Tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;
import partOfGame.Game;
import partOfGame.Map;
import partOfGame.Player;
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

    Crusader crusader1;
    Map map;
    Player me;

    @Before
    public void setUp() {
        // Инициализация объектов перед каждым тестом
        crusader1 = new Crusader(0, 0);
        map = new Map(2, 5);

        map.addCharacter(crusader1,crusader1.getX(),crusader1.getY());

        tunnel tunnelPoint1 = new tunnel(1,0);
        tunnel tunnelPoint2 = new tunnel(5,0);

        tunnelPoint1.setConnectionPoint(new int[]{5,0});
        tunnelPoint2.setConnectionPoint(new int[]{1,0});

        map.getObjects()[1][0]=tunnelPoint1;
        map.getObjects()[5][0]=tunnelPoint2;

        me = new Player(crusader1, map, new Game());
    }

    @Test
    public void TunnelWorkTest(){
        systemInMock.provideLines("1", "0", "1", "0");

        me.makeMove(1);

        assertNotEquals(map.getCharacters()[1][0], crusader1);
        assertEquals(map.getCharacters()[5][0], crusader1);
    }

    @Test
    public void AbleToDigTunnel(){
        crusader1.setX(1);
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
        crusader1.setX(2);
        systemInMock.provideLines("9", "0", "19", "9");

        me.makeMove(1);

        assertNotEquals(map.getCharacters()[2][0], crusader1);
        assertNotEquals(map.getCharacters()[19][9], crusader1);

        assertNotEquals(map.getObjects()[2][0].getType(), MyObjectTypes.TUNNEL);
        assertNotEquals(map.getObjects()[19][9].getType(), MyObjectTypes.TUNNEL);
    }
}
