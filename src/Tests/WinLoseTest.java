package Tests;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;
import partOfGame.*;
import playable.*;

import static org.junit.Assert.*;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;


public class WinLoseTest {
    @Rule
    public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

    private Game game;

    @Before
    public void setUp() {
        // Инициализация объектов перед каждым тестом
        Crusader crusader = new Crusader(0, 0);
        Skeleton skeleton = new Skeleton(19, 9);
        Map map = new Map(2, 5);

        Player me = new Player(crusader, map, new Game());
        Computer computer = new Computer(skeleton, map);

        game = new Game(map, me, computer);
    }

    @Test
    public void LoseByKillingHeroesTest() {
        // Симулируем ввод ходов
        systemInMock.provideLines("1","lol","1","1", "0", "19", "9", "-1");

        assertEquals(game.Play(),1);
    }

    @Test
    public void WinByKillingHeroesTest() {
        // Симулируем ввод ходов
        systemInMock.provideLines("1","lol","1","1","0","2","1","1","0","0","0","3", "2","1","0", "19", "8");

        assertEquals(game.Play(),2);
    }
    @Test
    public void WinByCapturingTheCastle(){
        // Симулируем ввод ходов
        systemInMock.provideLines("1","lol","1","1","0","18","9","-1","-1","-1");

        assertEquals(game.Play(),4);
    }
    @Test
    public void LoseByCapturingTheCastle(){
        // Симулируем ввод ходов
        systemInMock.provideLines("1","lol","1","-1","-1","-1","-1","-1","-1","-1","-1","-1","-1","-1","-1","-1","-1","1","0","0","9","-1","1","0","0","0","-1");

        assertEquals(game.Play(),3);
    }

}