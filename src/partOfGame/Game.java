package partOfGame;

import com.google.gson.annotations.Expose;
import placable.*;
import playable.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Game implements Saveable, Serializable{

    final int pointsToWin = 1;

    private long currentTime;

     final int MoneyForUnit = 50;
    final int MoneyForHero = 150;

    final int PointsForUnit = 2;
    final int PointsForHero = 10;
    final int PointsForMoney = 1;
    private Map map;

    private Player me;
    private Computer computer;
    private String name = "";

    private int mapType=0;
    private int gamePoint;

    private transient GameClock gameClock;


    public Game(Map map, Player me, Computer computer) {
        this.computer = computer;
        this.map = map;
        this.me = me;
        gameClock = new GameClock(currentTime);

        gameClock.setNPCs(createNPCs());
        gamePoint=0;
    }

    public void setCurrentTime(long time){
        this.currentTime=time;
        gameClock.setGameTime(time);
    }
    public long getCurrentTime(){
        long time = gameClock.getGameTime();
        this.currentTime=time;
        return time;
    }
    public int getGamePoint() {
        return gamePoint;
    }

    public void setGamePoint(int gamePoint) {
        this.gamePoint = gamePoint;
    }

    public void setMe(Player me) {
        this.me = me;
    }
    public Player getMe(){
        return me;
    }

    public int getPointsToWin() {
        return pointsToWin;
    }

    public int getMoneyForUnit() {
        return MoneyForUnit;
    }

    public int getMoneyForHero() {
        return MoneyForHero;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Computer getComputer() {
        return computer;
    }

    public void setComputer(Computer computer) {
        this.computer = computer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMapType() {
        return mapType;
    }

    public void setMapType(int mapType) {
        this.mapType = mapType;
    }

    public Game(){}

    public int Play() {
        gameClock.start();

        this.map.updateMap();
        while (true) {
            if (me.getHeroes().size() <= 0 || computer.getHeroes().size() <= 0 || me.points > pointsToWin || computer.points > pointsToWin) {
                break;
            }
            playersMakeMove(me, computer);

            if (me.getHeroes().size() <= 0 || computer.getHeroes().size() <= 0 || me.points > pointsToWin || computer.points > pointsToWin) {
                break;
            }
            playersMakeMove(computer, me);

            checkForBeingInZone();

            System.out.println("\n Ammount of points:\nMe: " + me.points + "\nComputer: " + computer.points + "\n");
        }

        if (me.getHeroes().size() == 0) {
            System.out.println("Our kingdom has fallen my lord...");
            System.out.println("Win by killing all heroes");
            return 1;
        } else if (computer.getHeroes().size() == 0) {
            System.out.println("The enemy has been defeated!!!");
            System.out.println("Win by killing all heroes");
            return 2;
        } else if (computer.points > pointsToWin) {
            System.out.println("Our kingdom has fallen my lord...");
            System.out.println("Win by capturing a castle");
            return 3;
        } else {
            System.out.println("The enemy has been defeated!!!");
            System.out.println("Win by capturing a castle");
            return 4;
        }
    }


    private void playersMakeMove(Player main, Player oponent) {
        int unusedMoves = 2;
        while (unusedMoves > 0) {
            if (unusedMoves == 1) {
                System.out.println("\nlast move\n");
            }
            unusedMoves -= main.makeMove(unusedMoves);
            checkForEndOfTheGame(me, computer);
            checkForEndOfTheGame(computer, me);
        }
    }

    private void checkForEndOfTheGame(Player player, Player other) {

        for (int i = 0; i < player.getHeroes().size(); i++) {
            if (player.getHeroes().get(i).getHP() <= 0) {
                if (player.getHeroes().get(i).getUnits().size() > 0) {
                    other.addMoney(player.getHeroes().get(i).getUnits().size() * MoneyForUnit + MoneyForHero);
                    if(player == computer){
                        gamePoint+=player.getHeroes().get(i).getUnits().size() * PointsForUnit + PointsForHero;
                    }
                }
                player.getHeroes().remove(i);
            }
        }
    }


    private void checkForBeingInZone() {
        for (int i = 0; i < me.getHeroes().size(); i++) {
            if (Distance(me.getHeroes().get(i), computer.getCastlePosition()) < 4) {
                me.increasePoints();
            }
        }
        for (int i = 0; i < computer.getHeroes().size(); i++) {
            if (Distance(computer.getHeroes().get(i), me.getCastlePosition()) < 4) {
                computer.increasePoints();
            }
        }
    }

    public static double Distance(MyCharacter character1, int[] castle) {
        int x1 = character1.getX();
        int y1 = character1.getY();

        int x2 = castle[0];
        int y2 = castle[1];

        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    public void createNewMap() {
        System.out.println("Type size of the map");
        Scanner scanner = new Scanner(System.in);
        int width = scanner.nextInt();
        int height = scanner.nextInt();
        System.out.println("Type seed for map");
        int seed = scanner.nextInt();
        MyObject[][] objectsForCustomMap = new MyObject[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                objectsForCustomMap[i][j] = new ground(i, j);
            }
        }

        System.out.println("Type Coordinates for castle 1");
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        map.genZone(objectsForCustomMap, x, y, width, height);
        System.out.println("Type Coordinates for castle 1");
        int x1 = scanner.nextInt();
        int y1 = scanner.nextInt();
        if ((x1 - x) * (x1 - x) + (y1 - y) * (y1 - y) < 9) {
            System.out.println("Castles are to close");
            return;
        }
        map.genZone(objectsForCustomMap, x1, y1, width, height);
        objectsForCustomMap[x][y] = new Castle(x, y);
        objectsForCustomMap[x1][y1] = new Castle(x1, y1);

        while (true) {
            System.out.println("enter option: 1 - place road\n 2 - place coin\n 3 - place artefact\n 4 - place the grail\n 5 - place the wall\n 6 - end of making map");
            int decicison = scanner.nextInt();
            switch (decicison) {
                case 1:
                    System.out.println("Type Coordinates for road");
                    x = scanner.nextInt();
                    y = scanner.nextInt();
                    objectsForCustomMap[x][y] = new Road(x, y);
                    break;
                case 2:
                    System.out.println("Type Coordinates for coin");
                    x = scanner.nextInt();
                    y = scanner.nextInt();
                    objectsForCustomMap[x][y] = new Coin(x, y, objectsForCustomMap[x][y]);
                    break;
                case 3:
                    System.out.println("Type Coordinates for artefact");
                    x = scanner.nextInt();
                    y = scanner.nextInt();
                    objectsForCustomMap[x][y] = new Artefact(x, y, objectsForCustomMap[x][y]);
                    break;
                case 4:
                    System.out.println("Type Coordinates for the Grail");
                    x = scanner.nextInt();
                    y = scanner.nextInt();
                    objectsForCustomMap[x][y] = new HolyGrail(x, y, objectsForCustomMap[x][y]);
                    break;
                case 5:
                    System.out.println("Type Coordinates for the wall");
                    x = scanner.nextInt();
                    y = scanner.nextInt();
                    objectsForCustomMap[x][y] = new Wall(x, y);
                    break;
                default:
                    Map newMap = new Map(objectsForCustomMap, width, height, seed);
                    newMap.updateMap();
                    saveCustomMap(objectsForCustomMap, width, height, seed);
                    return;
            }
        }
    }

    public void saveCustomMap(MyObject[][] objectsForCustomMap, int width, int height, int seed) {
        try (FileWriter writer = new FileWriter("maps.txt", true)) {
            writer.write(width + ";" + height + ";" + seed + "\n");
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    switch (objectsForCustomMap[j][i].getType()) {
                        case GROUND:
                            writer.write("1;");
                            break;
                        case ZONE:
                            writer.write("2;");
                            break;
                        case CASTLE:
                            writer.write("3;");
                            break;
                        case ROAD:
                            writer.write("4;");
                            break;
                        case COIN:
                            writer.write("5;");
                            break;
                        case ARTEFACT:
                            writer.write("6;");
                            break;
                        case WALL:
                            writer.write("7;");
                            break;
                        case HOLYGRAIL:
                            writer.write("8;");
                            break;
                    }
                }
            }
            writer.write("\n");
            writer.flush();
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }
    }

    public void loadCustomMap() {
        try (RandomAccessFile raf = new RandomAccessFile("maps.txt", "r");) {
            String line;
            int lineNumber = 1;

            System.out.println("which map to load");

            while ((line = raf.readLine()) != null) {
                if (lineNumber % 2 == 1) {
                    System.out.println(line);
                }
                lineNumber++;
            }

            Scanner scanner = new Scanner(System.in);
            int mapNum = scanner.nextInt();
            mapType=mapNum; // запоминаем номер используемой карты для сохранения
            String mapObjects = "";
            String mapParams = "";
            raf.seek(0);
            for (int i = 0; i < 2 * mapNum; i++) {
                mapObjects = raf.readLine();
            }
            raf.seek(0);
            for (int i = 0; i < 2 * mapNum - 1; i++) {
                mapParams = raf.readLine();
            }
            raf.seek(0);
            for (int i = 0; i < 2 * mapNum; i++) {
                mapObjects = raf.readLine();
            }
            int[] mapParamsArr = Arrays.stream(mapParams.split(";")).mapToInt(Integer::parseInt).toArray();
            int[] mapObjectsArr = Arrays.stream(mapObjects.split(";")).mapToInt(Integer::parseInt).toArray();
            int width = mapParamsArr[0];
            int height = mapParamsArr[1];
            int seed = mapParamsArr[2];
            MyObject[][] objects = new MyObject[width][height];
            int castleCount = 0;
            int[][] CastlePositions = new int[2][2];
            for (int i = 0; i < width * height; i++) {
                switch (mapObjectsArr[i]) {
                    case 1:
                        objects[i % width][i / width] = new ground(i % width, i / width);
                        break;
                    case 2:
                        objects[i % width][i / width] = new Zone(i % width, i / width);
                        break;
                    case 3:
                        objects[i % width][i / width] = new Castle(i % width, i / width);
                        CastlePositions[castleCount][0] = i % width;
                        CastlePositions[castleCount][1] = i / width;
                        castleCount = 1;
                        break;
                    case 4:
                        objects[i % width][i / width] = new Road(i % width, i / width);
                        break;
                    case 5:
                        objects[i % width][i / width] = new Coin(i % width, i / width, new ground(i % width, i / width));
                        break;
                    case 6:
                        objects[i % width][i / width] = new Artefact(i % width, i / width, new ground(i % width, i / width));
                        break;
                    case 7:
                        objects[i % width][i / width] = new Wall(i % width, i / width);
                        break;
                    case 8:
                        objects[i % width][i / width] = new HolyGrail(i % width, i / width, new ground(i % width, i / width));
                        break;
                }
            }
            Map newMap = new Map(objects, width, height, seed);
            this.map = newMap;
            me.getHeroes().get(0).setPosition(CastlePositions[0][0], CastlePositions[0][1]);
            me.setCastlePosition(CastlePositions[0]);
            me.map = newMap;
            computer.getHeroes().get(0).setPosition(CastlePositions[1][0], CastlePositions[1][1]);
            computer.setCastlePosition(CastlePositions[1]);
            computer.map = newMap;
            map.addCharacter(me.getHeroes().get(0), me.getHeroes().get(0).getX(), me.getHeroes().get(0).getY());
            map.addCharacter(computer.getHeroes().get(0), computer.getHeroes().get(0).getX(), computer.getHeroes().get(0).getY());
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }

    @Override
    public void saveGame() {
        Path savesPath = Paths.get("Saves", name); // Saves/Pavel

        try {
            Files.createDirectories(savesPath); // Создаст все папки в пути
        } catch (IOException e) {
            System.err.println("Ошибка при создании папки: " + e.getMessage());
        }

        try (FileOutputStream outputStream = new FileOutputStream("Saves"+ File.separator + name+ File.separator + "SavesMap.ser");
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject(this.getMap());
        } catch (Exception e) {
            System.out.println("Failed to save");
        }

        try (FileOutputStream outputStream = new FileOutputStream("Saves"+ File.separator + name+ File.separator + "SavesMe.ser");
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject(this.getMe());
        } catch (Exception e) {
            System.out.println("Failed to save");
        }

        try (FileOutputStream outputStream = new FileOutputStream("Saves"+ File.separator + name+ File.separator + "SavesComoputer.ser");
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject(this.getComputer());
        } catch (Exception e) {
            System.out.println("Failed to save");
        }

        try (FileOutputStream outputStream = new FileOutputStream("Saves"+ File.separator + name+ File.separator + "SavesGame.ser");
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject(this);
        } catch (Exception e) {
            System.out.println("Failed to save");
        }

        try (FileWriter writer = new FileWriter("Saves"+ File.separator + name+ File.separator +"record.txt", false)) {
            gamePoint+=(me.money/100)*PointsForMoney;
            writer.write("Earned Point: \n" + this.getGamePoint());
        } catch (IOException e ){System.out.println("Failed to save");}

        try (FileWriter writer = new FileWriter("Saves"+ File.separator + name+ File.separator +"time.txt", false)) {
            writer.write(Long.toString(gameClock.getGameTime()));
        } catch (IOException e ){System.out.println("Failed to save");}

        System.out.println("Game is saved");
    }

    private static NPC[] createNPCs(){
        NPC npc1 = new NPC("Igor",0);
        NPC npc2 = new NPC("Ivan",1);
        NPC npc3 = new NPC("Inna",2);

        return new NPC[]{npc1,npc2,npc3};
    }

}
