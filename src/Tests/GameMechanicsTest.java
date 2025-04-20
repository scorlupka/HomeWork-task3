package Tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;
import partOfGame.*;
import playable.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

public class GameMechanicsTest {
    @Rule
    public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

    private ByteArrayOutputStream output = new ByteArrayOutputStream();
    Crusader crusader;
    Map map;
    Player me;


    @Before
    public void setUp(){
        System.setOut(new PrintStream(output));
        crusader = new Crusader(0,0);
        map = new Map(2, 5);
        me = new Player(crusader, map, new Game());
    }

    @Test
    public void InspectionTest(){
        systemInMock.provideLines("2","7","2");
        me.makeMove(1);
        assertTrue(output.toString().contains(map.getObjects()[7][2].getType().toString())||output.toString().contains(map.getCharacters()[7][2].getType().toString()));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(System.out);
    }

}