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

public class BuildingTests {

    @Rule
    public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

    Crusader crusader;
    Map map;
    Player me;

    @Before
    public void setUp() {
        // Инициализация объектов перед каждым тестом
        crusader = new Crusader(0, 0);
        map = new Map(2, 5);
        map.addCharacter(crusader,crusader.getX(),crusader.getY());
        me = new Player(crusader, map, new Game());
    }

    @Test
    public void BuyUnitTest(){
        me.addMoney(1000);
        systemInMock.provideLines("3","1","3","2","3","3");
        assertEquals(crusader.getUnits().size(),0);
        me.makeMove(1);
        me.makeMove(1);
        me.makeMove(1);
        assertEquals(crusader.getUnits().size(),3);
        assertEquals(crusader.getUnits().get(0).getType(),MyCharacterTypes.ARCHER);
        assertEquals(crusader.getUnits().get(1).getType(),MyCharacterTypes.VIKING);
        assertEquals(crusader.getUnits().get(2).getType(),MyCharacterTypes.HEALER);
    }

    @Test
    public void BuyHeroTest(){
        systemInMock.provideLines("1","0","2","2","5");
        me.addMoney(600);
        me.makeMove(1);
        me.makeMove(1);

        assertEquals(me.getHeroes().size(),2);
        assertArrayEquals(new int[]{me.getHeroes().get(1).getX(),me.getHeroes().get(1).getY()},new int[]{0,0});
    }

    @Test
    public void ByHorseHouseTest(){
        systemInMock.provideLines("6");
        int speed = crusader.getSpeed();

        me.addMoney(1000);
        me.makeMove(1);

        assertEquals(speed+2,crusader.getSpeed());
    }
}