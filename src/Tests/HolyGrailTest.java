package Tests;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;
import partOfGame.*;
import playable.*;

import static org.junit.Assert.*;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

public class HolyGrailTest {
    @Rule
    public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

    Crusader crusader;
    Map map;
    Player me;

    @Before
    public void setUp() {
        // Инициализация объектов перед каждым тестом
        crusader = new Crusader(7, 2);
        map = new Map(2, 5);
        map.addCharacter(crusader, crusader.getX(), crusader.getY());
        me = new Player(crusader, map, new Game());
    }

    @Test
    public void CanDigTheGrailTest() {
        systemInMock.provideLines("7", "7", "0");
        assertFalse(crusader.getHoldsTheGrail());
        me.makeMove(1);
        assertFalse(crusader.getHoldsTheGrail());
        me.makeMove(2);
        assertTrue(crusader.getHoldsTheGrail());
    }

    @Test
    public void CanStealTheGrailTest() {
        systemInMock.provideLines("1", "0", "9", "2");
        crusader.setHoldsTheGrail(true);
        Skeleton skeleton = new Skeleton(8, 2);
        map.addCharacter(skeleton, skeleton.getX(), skeleton.getY());
        assertFalse(skeleton.getHoldsTheGrail());
        me.makeMove(1);
        assertTrue(skeleton.getHoldsTheGrail());
    }

    @Test
    public void GrailSpellIsWorkingTest() {
        systemInMock.provideLines("1", "0", "0", "0");
        crusader.setHoldsTheGrail(true);
        crusader.addUnits(new Healer(0,0));

        me.makeMove(1);
        assertFalse(crusader.getHoldsTheGrail());
        assertEquals(crusader.getUnits().get(0).getHP(),220);
        assertEquals(crusader.getUnits().get(0).getDamage(),100);
    }

    @Test
    public void IsPrayingTest(){
        systemInMock.provideLines("-1");
        crusader.setHoldsTheGrail(true);
        crusader.addUnits(new Healer(0,0));

        me.makeMove(1);
        assertTrue((map.getCharacters()[8][3]==crusader)||(crusader.getUnits().get(0).getHP()==130)||(crusader.getUnits().get(0).getDamage()==10));
    }
}

// подключить в случайном месте логирование, логировать в отдельный новый файл
//дописать 2 теста грааля
//логирование попыток молитв, попыток выкапывания; подключить стандартную библиотеку логирования;

//warning о неудачном выкапывании
//инфо об удачных
//ерор о нудачной молитве