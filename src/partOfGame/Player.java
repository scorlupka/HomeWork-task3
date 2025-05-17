package partOfGame;

import placable.*;
import playable.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.*;

import static java.util.Objects.isNull;

public class Player implements Serializable {
    transient Saveable saveHandler;  // Не сохраняется

    transient static final Logger logger = LogManager.getLogger(Player.class);  // static не сохраняется



    protected int points = 0;



    ArrayList<MyCharacter> heroes = new ArrayList<>();



    protected ArrayList<MyCharacter> units = new ArrayList<>();


    protected int money = 0;

    transient Map map;  // Не сохраняется



    protected int[] castlePosition = {0, 0};

    transient Random random = new Random();  // Не сохраняется

    public Player(Saveable saveHandler) {
        Crusader crusader = new Crusader(0, 0);
        heroes.add(crusader);
        this.map = map;
        map.addCharacter(crusader, crusader.getX(), crusader.getY());
        this.saveHandler = saveHandler;
    }

    public Player(MyCharacter hero1, Map map, Saveable saveHandler) {
        heroes.add(hero1);
        this.map = map;
        map.addCharacter(hero1, hero1.getX(), hero1.getY());
        this.saveHandler = saveHandler;
    }

    public ArrayList<MyCharacter> getHeroes() {
        return heroes;
    }
    public void setHeroes(ArrayList<MyCharacter> heroes){this.heroes = heroes;}

    public Saveable getSaveHandler() {
        return saveHandler;
    }

    public void setSaveHandler(Saveable saveHandler) {
        this.saveHandler = saveHandler;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public Map getMap() {
        return map;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public ArrayList<MyCharacter> getUnits() {
        return units;
    }
    public void setUnits(ArrayList<MyCharacter> units){this.units = units;}

    public void setMap(Map map) {
        this.map = map;
    }

    public void addMoney(int money) {
        this.money += money;
    }

    public int getMoney() {
        return money;
    }

    public int[] getCastlePosition() {
        return castlePosition;
    }

    public void setCastlePosition(int[] castlePosition) {
        this.castlePosition = castlePosition;
    }

    public void increasePoints() {
        this.points += 1;
    }

    public int getPoints() {
        return points;
    }

    public int makeMove(int moves) {
        int movesToReturn = 1;

        System.out.println("Type what do you want to do:\n 1 - go to any point \n 2 - inspect any point " +
                "\n 3 - buy units \n 4 - count Army \n 5 - buy hero \n 6 - buy horse house " +
                "\n 7 - try to dig the Holy Grail \n 8 - save the game \n 9 - dig a tunnel \n -1 - have a rest");

        try {
            Scanner scanner = new Scanner(System.in);
            int decision = scanner.nextInt();
            switch (decision) {
                case 1:
                    goToPoint();
                    //проверяем что ход был сделан на помещение
                    checkForStepOnBuilding();
                    break;
                case 2:
                    inspect();
                    break;
                case 3:
                    buyUnits();
                    break;
                case 4:
                    countArmy();
                    break;
                case 5:
                    buyHero();
                    break;
                case 6:
                    buyHorseHouse();
                    break;
                case 7:
                    movesToReturn = digTheHolyGrail(moves);
                    break;
                case 8:
                    saveHandler.saveGame();
                    break;
                case 9:
                    digTunnel();
                default:
                    System.out.println("what a wonderful day is today! You have " + money + " gold");
                    break;
            }
        } catch (InputMismatchException e) {
            System.out.println("I dont understand you my majesty");
        } finally {
            map.updateMap();
        }

        //проверяем что герой держит грааль и молимся
        prayToGod(this, castlePosition);

        //возвращаем количество оставшихся ходов
        return movesToReturn;
    }
    public void checkForStepOnBuilding(){
        int personCNT=-1;

            for (int i = 0; i < heroes.size(); i++) {
                switch (map.getObjects()[heroes.get(i).getX()][heroes.get(i).getY()].getType()) {
                    case BARBER:
                        goToBarber(heroes.get(i));
                        personCNT = i;
                        break;
                    case CAFE:
                        goToCafe(heroes.get(i));
                        personCNT = i;
                        break;
                    case HOTEL:
                        goToHotel(heroes.get(i));
                        personCNT = i;
                        break;
                    case TUNNEL:
                        goToTunnel(heroes.get(i));
                        personCNT = i;
                        return;
                }
            }

        // выход из помещения
        if(personCNT == -1){return;}


        int x = heroes.get(personCNT).getX();
        int y = heroes.get(personCNT).getY();

            // Все возможные направления (включая диагональные)
        int[][] directions = {
                {0, 1},   // Вверх
                {0, -1},  // Вниз
                {1, 0},   // Вправо
                {-1, 0},  // Влево
                {1, 1},   // Вверх-вправо
                {1, -1},  // Вниз-вправо
                {-1, 1},  // Вверх-влево
                {-1, -1}  // Вниз-влево
        };

            // Проверяем каждое направление
        for (int[] dir : directions) {
            int newX = x + dir[0];
            int newY = y + dir[1];

            try {
                if (isNull(map.getCharacters()[newX][newY])) {
                    money += map.moveCharacterStraight(heroes.get(personCNT), newX, newY);
                    return; // Выходим после первого успешного хода
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                // Игнорируем выход за границы карты
            }
        }
    }
    public void goToBarber(MyCharacter hero){
        Barber barber = (Barber)(map.getObjects()[hero.getX()][hero.getY()]);

        Game game = (Game)saveHandler;
        barber.getHouse().addUser(game.getCurrentTime(),hero,this);

    }
    public void goToTunnel(MyCharacter hero){
        Game game = (Game)saveHandler;

        if(((game.getCurrentTime()%86400)/3600)>6){
            System.out.println("You can use tunnel only at night");
            return;
        }

        int x1= hero.getX();
        int y1= hero.getY();

        if(game.getMap().getObjects()[x1][y1].getType()==MyObjectTypes.TUNNEL) {
            System.out.println("hero has stepped in the tunnel");
            tunnel tunnel1 = (tunnel) game.getMap().getObjects()[x1][y1];

            double chance = 0.1; // 10% вероятность

            // Генерация случайного числа от 0.0 (включительно) до 1.0 (исключительно)
            double randomValue = random.nextDouble();

            if (randomValue < chance) {
                System.out.println("A tunnel has collapsed. Today your hero is sleeping with worms");
                game.getMap().getObjects()[tunnel1.getConnectionPoint()[0]][tunnel1.getConnectionPoint()[1]] = new ground(tunnel1.getConnectionPoint()[0], tunnel1.getConnectionPoint()[1]);
                game.getMap().getObjects()[tunnel1.getX()][tunnel1.getY()] = new ground(tunnel1.getX(), tunnel1.getY());

                hero = null;
            }
            else{
                money += map.moveCharacterStraight(hero, tunnel1.getConnectionPoint()[0], tunnel1.getConnectionPoint()[1]);
            }
        }
    }

    public void goToCafe(MyCharacter hero){
        Cafe cafe = (Cafe)(map.getObjects()[hero.getX()][hero.getY()]);

        Game game = (Game)saveHandler;
        cafe.getHouse().addUser(game.getCurrentTime(),hero,this);
    }
    public void goToHotel(MyCharacter hero){
        Hotel hotel = (Hotel)(map.getObjects()[hero.getX()][hero.getY()]);

        Game game = (Game)saveHandler;
        hotel.getHouse().addUser(game.getCurrentTime(),hero,this);
    }

    private void digTunnel(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Which playable.character you want to dig a tunnel?");
        for (int i = 0; i < heroes.size(); i++) {
            System.out.println(i + " " + heroes.get(i).getType() + " at point " + heroes.get(i).getX() + "," + heroes.get(i).getY());
        }
        try {
            int personCnt = scanner.nextInt();

            System.out.println("Type a place you want to dig out (type by enter)");
            int x = scanner.nextInt();
            int y = scanner.nextInt();

            int x1 = heroes.get(personCnt).getX();
            int y1 = heroes.get(personCnt).getY();

            if((map.getObjects()[x1][y1].getType() == MyObjectTypes.CASTLE) || (map.getObjects()[x][y].getType() == MyObjectTypes.CASTLE)){
                System.out.println("we cant dig a tunnel in the castle, my lord");
                return;
            }
            if(x1==x && y1 == y){
                System.out.println("we are already here, my lord");
                return;
            }

            if(Game.Distance(heroes.get(personCnt),new int[]{x,y}) <=5){
                map.getObjects()[x][y]= new tunnel(x,y,x1,y1);
                map.getObjects()[x1][y1]= new tunnel(x1,y1,x,y);
            }
            else{
                System.out.println("Sorry my majesty, tunnel was too long... your hero has fallen under ground");
                heroes.get(personCnt).setHP(-1);
            }
            map.moveCharacterStraight(heroes.get(personCnt), x1, y1);
        } catch (InputMismatchException e) {
            System.out.println("I dont understand you my majesty");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("We havent this hero my majesty");
        }
    }

    private void goToPoint() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Which playable.character you want to move?");
        for (int i = 0; i < heroes.size(); i++) {
            System.out.println(i + " " + heroes.get(i).getType() + " at point " + heroes.get(i).getX() + "," + heroes.get(i).getY());
        }
        try {
            int personCnt = scanner.nextInt();

            System.out.println("Type a place you want to go (type by enter)");
            int x = scanner.nextInt();
            int y = scanner.nextInt();

            money += map.moveCharacterStraight(heroes.get(personCnt), x, y);

        } catch (InputMismatchException e) {
            System.out.println("I dont understand you my majesty");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("We havent this hero my majesty");
        }
    }

    protected void prayToGod(Player player, int[] castlePosition) {
        //проверим держит ли кто-то грааль
        MyCharacter hero = null;
        for (int i = 0; i < player.heroes.size(); i++) {
            if (player.heroes.get(i).getHoldsTheGrail()) {
                if (player.heroes.get(i).getX() == castlePosition[0] && player.heroes.get(i).getY() == castlePosition[1]) {
                    System.out.println(player.heroes.get(i).getType() + " brought the grail to the altar of his castle. " +
                            "Now all army of this castle is blessed");
                    player.heroes.get(i).setHoldsTheGrail(false);
                    player.heroes.get(i).setSpeed(player.heroes.get(i).getSpeed() * 2);
                    for (int j = 0; j < player.heroes.size(); j++) {
                        for (int k = 0; k < player.heroes.get(j).getUnits().size(); k++) {
                            player.heroes.get(j).getUnits().get(k).increaseDamage(100);
                            player.heroes.get(j).getUnits().get(k).increaseHP(100);
                        }
                    }
                } else {
                    hero = player.heroes.get(i);
                }
            }
        }
        if (hero == null) {
            return;
        }

        System.out.println();
        System.out.println(hero.getType() + " is Praying to God\n");

        double chance = 0.5; // 30% вероятность

        // Генерация случайного числа от 0.0 (включительно) до 1.0 (исключительно)
        double randomValue = random.nextDouble();

        if (randomValue < chance) {
            System.out.println("The prayer was successful. God favors the army of " + hero.getType());
            randomValue = random.nextDouble();
            if (randomValue < chance) {
                System.out.println("Units now are Healthier!\n");
                logger.info("Молитва удалась и юниты героя стали здоровее");
                for (int i = 0; i < hero.getUnits().size(); i++) {
                    hero.getUnits().get(i).increaseHP(10);
                }
            } else {
                System.out.println("Units now are Stronger!\n");
                logger.info("Молитва удалась и юниты героя стали сильнее");
                for (int i = 0; i < hero.getUnits().size(); i++) {
                    hero.getUnits().get(i).increaseDamage(10);
                }
            }
        } else {
            System.out.println("The prayer failed. God is angry with the army of " + hero.getType());
            System.out.println();
            logger.error("Молитва игрока не удалась и его герой в страхе делает шаг от замка!");

            int dx = hero.getX() > castlePosition[0] ? 1 : -1;
            int dy = hero.getY() > castlePosition[1] ? 1 : -1;

            money += map.moveCharacterStraight(hero, hero.getX() + dx, hero.getY() + dy);
            map.updateMap();
        }
    }

    private void inspect() {
        System.out.println("Type a place you want to inspect");
        Scanner scanner = new Scanner(System.in);
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        try {
            if (map.getCharacters()[x][y] != null) {
                System.out.println(map.getCharacters()[x][y].getType());
            } else {
                System.out.println(map.getObjects()[x][y].getType());
            }
        } catch (InputMismatchException e) {
            System.out.println("I dont understand you my majesty");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("We havent this hero my majesty");
        }
    }

    private void buyUnits() {
        int heroToBuyUnits = 0;
        boolean heroAtCastle = false;
        for (int i = 0; i < heroes.size(); i++) {
            if (heroes.get(i).getX() == castlePosition[0] && heroes.get(i).getY() == castlePosition[1]) {
                shopUnits(heroes.get(i));
                heroAtCastle = true;
            }
        }
        if (!heroAtCastle) {
            System.out.println("One of your heroes must be at placable.castle my majesty");
        }
    }

    private void countArmy() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Army of which hero you want to see my majesty?");
        for (int i = 0; i < heroes.size(); i++) {
            System.out.println(i + " " + heroes.get(i).getType() + " at point " + heroes.get(i).getX() + "," + heroes.get(i).getY());
        }
        try {
            int personCnt = scanner.nextInt();
            if (heroes.get(personCnt).getUnits().size() == 0) {
                System.out.println("this hero doesnt has army yet");
                return;
            }

            for (int i = 0; i < heroes.get(personCnt).getUnits().size(); i++) {
                System.out.println(i + " " + heroes.get(personCnt).getUnits().get(i).getType() +
                        " HP: " + heroes.get(personCnt).getUnits().get(i).getHP() +
                        " Damage: " + heroes.get(personCnt).getUnits().get(i).getDamage() +
                        " at point " + heroes.get(personCnt).getUnits().get(i).getX() +
                        "," + heroes.get(personCnt).getUnits().get(i).getY());
            }
        } catch (InputMismatchException e) {
            System.out.println("I dont understand you my majesty");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("We havent this hero my majesty");
        }
    }

    private void shopUnits(MyCharacter hero) {
        System.out.println("what unit you want to buy:\n 1 - archer: 150 gold \n 2 - viking: 100 gold \n 3 - healer: 200 gold");
        try {
            Scanner scanner = new Scanner(System.in);
            int decision = scanner.nextInt();

            switch (decision) {
                case 2:
                    if (money < 100) {
                        System.out.println("we have not enough gold!");
                        break;
                    }
                    money -= 100;
                    Viking viking = new Viking(units.size() / 15, units.size() % 15);
                    units.add(viking);
                    hero.addUnits(viking);
                    break;
                case 3:
                    if (money < 200) {
                        System.out.println("we have not enough gold!");
                        break;
                    }
                    money -= 200;
                    Healer healer = new Healer(units.size() / 15, units.size() % 15);
                    units.add(healer);
                    hero.addUnits(healer);
                    break;
                default:
                    if (money < 150) {
                        System.out.println("we have not enough gold!");
                        break;
                    }
                    money -= 150;
                    Archer archer = new Archer(units.size() / 15, units.size() % 15);
                    units.add(archer);
                    hero.addUnits(archer);
                    break;
            }
        } catch (InputMismatchException e) {
            System.out.println("I dont understand you my majesty");
        }
    }

    private void buyHero() {
        if (money > 500 && map.getCharacters()[castlePosition[0]][castlePosition[1]] == null) {
            Crusader hero = new Crusader(0, 0);
            heroes.add(hero);
            map.addCharacter(hero, hero.getX(), hero.getY());
        } else {
            System.out.println("you have not enough money or placable.castle already has a hero");
        }
    }

    private void buyHorseHouse() {
        if (money > 500) {
            for (int i = 0; i < heroes.size(); i++) {
                if (heroes.get(i).getX() == castlePosition[0] && heroes.get(i).getY() == castlePosition[1]) {
                    for (int j = 0; j < heroes.size(); j++) {
                        heroes.get(j).addHorse();
                        System.out.println(heroes.get(i).getType() + " has speed of " + heroes.get(i).getSpeed());
                    }
                    return;
                }
                System.out.println("One of heroes must be at placable.castle");
            }
        } else {
            System.out.println("you have not enough");
        }
    }

    private int digTheHolyGrail(int moves) {
        if (moves < 2) {
            System.out.println("We dont have enough time to dig out a grail, my majesty!");
            logger.warn("Раскопка грааля не удалась: не достаточно ходов для начала раскопок");
            return 1;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("which hero is the chosen one to atempt himself in finding the Holy Grail, my majesty?");
        for (int i = 0; i < heroes.size(); i++) {
            System.out.println(i + " " + heroes.get(i).getType() + " at point " + heroes.get(i).getX() + "," + heroes.get(i).getY());
        }
        try {
            int personCnt = scanner.nextInt();
            return map.digTheGrail(heroes.get(personCnt));

        } catch (InputMismatchException e) {
            System.out.println("I dont understand you my majesty");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("We havent this hero my majesty");
        }
        return 1;
    }


}
