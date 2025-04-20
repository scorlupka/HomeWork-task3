package Tests;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;
import partOfGame.*;
import placable.*;
import playable.*;

import static org.junit.Assert.*;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

public class MapObjectsTest {

    @Rule
    public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

    private Crusader crusader;
    Map map;
    Player me;

    @Before
    public void setUp() {
        // Инициализация объектов перед каждым тестом
        crusader = new Crusader(0, 0);
        map = new Map(2, 5);
        me = new Player(crusader, map, new Game());
    }

    @Test
    public void CollectCoinTest(){
        systemInMock.provideLines("1","0","3","2");
        assertEquals(map.getObjects()[2][1].getType(), MyObjectTypes.COIN);
        me.makeMove(1);
        assertNotEquals(map.getObjects()[2][1].getType(), MyObjectTypes.COIN);
        assertEquals(me.getMoney(),300);
    }

    @Test
    public void CollectArtefactTest(){
        int StartDamage=me.getHeroes().get(0).getDamage();
        systemInMock.provideLines("1","0","4","1");
        assertEquals(map.getObjects()[4][1].getType(), MyObjectTypes.ARTEFACT);
        me.makeMove(1);
        assertNotEquals(map.getObjects()[4][1].getType(), MyObjectTypes.ARTEFACT);
        assertEquals(me.getHeroes().get(0).getDamage(),StartDamage+5);
    }

    @Test
    public void NotAbleToCrossTheWallTest(){
        systemInMock.provideLines("1","0","0","4","1","0","5","4");
        me.makeMove(1);
        assertEquals(map.getCharacters()[0][4], crusader);
        assertArrayEquals(new int[]{crusader.getX(),crusader.getY()},new int[]{0,4});

        me.makeMove(1);
        assertNotEquals(map.getCharacters()[5][4], crusader);
        assertArrayEquals(new int[]{crusader.getX(),crusader.getY()},new int[]{2,4});
    }

    @Test
    public void MovementPenaltyTest(){
        crusader.setSpeed(5);
        systemInMock.provideLines("1","0","19","0","1","0","9","0");
        me.makeMove(1);
        assertEquals(map.getCharacters()[5][0], crusader);
        assertArrayEquals(new int[]{crusader.getX(),crusader.getY()},new int[]{5,0});

        me.makeMove(1);
        assertEquals(map.getCharacters()[8][0], crusader);
        assertArrayEquals(new int[]{crusader.getX(),crusader.getY()},new int[]{8,0});
    }

}