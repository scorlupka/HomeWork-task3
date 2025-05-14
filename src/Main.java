import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import partOfGame.*;
import playable.Crusader;
import playable.MyCharacter;
import playable.Skeleton;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Heroes of IU3\n1 - New game\n2 - Continue game");
        Scanner scanner = new Scanner(System.in);
        int decision1 = scanner.nextInt();

        scanner.nextLine();
        System.out.println("Enter your name");
        String name = scanner.nextLine();

        switch (decision1) {
            case 1:
                StartNewGame(name);
                break;
            case 2:
                try{
                Game game = loadGameFromComponents(name);
                game.setName(name);
                game.Play();
                } catch(Exception e){System.out.println("Failed to load game");}
                break;
            default:
                break;
        }

    }
    private static void StartNewGame(String name){
        Game game = new Game();

        Crusader crusader = new Crusader(0, 0);
        Skeleton skeleton = new Skeleton(19,9);
        Map map = new Map(2, 5);

        Player me = new Player(crusader, map, null);
        Computer computer = new Computer(skeleton, map);

        game = new Game(map, me, computer);
        game.setName(name);
        me.setSaveHandler(game);

        Scanner scanner = new Scanner(System.in);
        System.out.println("1 - Regular map\n2 - load custom map\n3 - Create new map");
        int decision = scanner.nextInt();
        switch (decision) {
            case 1:
                break;
            case 2:
                game.loadCustomMap();
                break;
        }

        game.Play();
    }
    private static Game loadGameFromComponents(String name) {
        try {
            // Загрузка карты
            Map map;
            try (FileInputStream mapInput = new FileInputStream("Saves"+ File.separator + name+ File.separator + "SavesMap.ser");
                 ObjectInputStream mapOis = new ObjectInputStream(mapInput)) {
                map = (Map) mapOis.readObject();
            }

            // Загрузка игрока
            Player me;
            try (FileInputStream playerInput = new FileInputStream("Saves"+ File.separator + name+ File.separator + "SavesMe.ser");
                 ObjectInputStream playerOis = new ObjectInputStream(playerInput)) {
                me = (Player) playerOis.readObject();
                me.setMap(map); // Восстанавливаем связь
            }

            // Загрузка компьютера
            Computer computer;
            try (FileInputStream compInput = new FileInputStream("Saves"+ File.separator + name+ File.separator + "SavesComoputer.ser");
                 ObjectInputStream compOis = new ObjectInputStream(compInput)) {
                computer = (Computer) compOis.readObject();
                computer.setMap(map); // Восстанавливаем связь
            }
            Game game = new Game(map, me, computer);
            me.setSaveHandler(game);

            String line;
            try (RandomAccessFile raf = new RandomAccessFile("Saves"+ File.separator + name+ File.separator +"record.txt", "r");) {
                line = raf.readLine();
                line = raf.readLine();

                game.setGamePoint(Integer.parseInt(line));
            }catch (IOException e){System.out.println("Failed to load game");}
            return game;
        } catch (Exception e) {
            System.out.println("Ошибка при загрузке компонентов: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

}