package partOfGame;

public interface Saveable {
    void saveGame();
}

/*
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;

public class GameSaveManager {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void saveGame(String filename, GameState gameState) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(gameState, writer);
        }
    }

    public static GameState loadGame(String filename) throws IOException {
        try (FileReader reader = new FileReader(filename)) {
            return gson.fromJson(reader, GameState.class);
        }
    }
}
 */

//тоннель
//тесты