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

public class IdiotTest {
    @Rule
    public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

    Crusader crusader1;
    Player me;
    Map map;

    @Before
    public void setUp() {
        // Инициализация объектов перед каждым тестом
        crusader1 = new Crusader(0, 0);
        map = new Map(2, 5);

        map.addCharacter(crusader1,crusader1.getX(),crusader1.getY());
        me= new Player(crusader1,map, new Game());
    }

    @Test
    public void NotAbleToescapeMap(){
        systemInMock.provideLines("1","0","100","100","1","0","-1","-1");
        assertEquals(map.getCharacters()[0][0],crusader1);
        me.makeMove(1);
        assertEquals(map.getCharacters()[0][0],crusader1);
        me.makeMove(1);
        assertEquals(map.getCharacters()[0][0],crusader1);
    }
}