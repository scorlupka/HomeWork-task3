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


public class FightSystemTest {

    @Rule
    public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

    Crusader crusader1;
    Skeleton skeleton;
    Map map;

    @Before
    public void setUp() {
        // Инициализация объектов перед каждым тестом
        crusader1 = new Crusader(0, 0);
        skeleton = new Skeleton(1, 0);
        map = new Map(2, 5);

        map.addCharacter(crusader1,crusader1.getX(),crusader1.getY());
        map.addCharacter(skeleton,skeleton.getX(),skeleton.getY());
    }

    @Test
    public void AbleToLooseTheFightTest(){
        map.moveCharacterStraight(crusader1,3,0);

        assertTrue(crusader1.getHP()<=0);
        assertTrue(skeleton.getHP()>0);
    }

    @Test
    public void AbleToWinTheFightTest(){
        crusader1.setHP(1000);
        crusader1.increaseDamage(1000);
        map.moveCharacterStraight(crusader1,3,0);

        assertTrue(crusader1.getHP()>0);
        assertTrue(skeleton.getHP()<=0);
    }

    @Test
    public void NoFriendlyFire(){
        Crusader crusader2 = new Crusader(0,7);
        map.addCharacter(crusader2, crusader2.getX(), crusader2.getY());

        map.moveCharacterStraight(crusader1,0,8);
        assertEquals(map.getCharacters()[0][6],crusader1);
        assertTrue(crusader1.getHP()>0 && crusader2.getHP()>0);
    }

}