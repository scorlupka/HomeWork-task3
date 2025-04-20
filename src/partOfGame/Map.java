package partOfGame;

import placable.*;
import playable.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Map {
    private static final Logger logger = LogManager.getLogger(Map.class);

    final private int width;
    final private int height;
    private MyCharacter[][] characters;
    private MyObject[][] objects;
    final private Random random;

    //конструктор
    public Map(MyObject[][] objects, int width, int height, int seed){
        this.random = new Random(seed);
        this.objects = objects;
        this.width = width;
        this.height = height;
        this.characters = new MyCharacter[width][height];
    }

    public Map(int size, long seed) {
        //size = 2 - big
        //size = else - small
        switch (size) {
            case 2:
                this.width = 20;
                this.height = 10;
                break;
            default:
                this.width = 10;
                this.height = 5;
                break;
        }

        this.random = new Random(seed);

        characters = new MyCharacter[width][height];
        objects = new MyObject[width][height];
        //////////////////////////////
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x == 3) {
                    objects[x][y] = new Wall(x, y);
                } else
                    objects[x][y] = new ground(x, y);
            }
        }
        //дорога между замками
        getLinePoints(0, 0, width - 1, height - 1).stream().forEach(elem -> objects[elem[0]][elem[1]] = new Road(elem[0], elem[1]));
        //зоны игрока и соперника
        genZone(objects, 0, 0, width, height);
        genZone(objects, width - 1, height - 1, width, height);
        //замки игрока и соперника
        objects[0][0] = new Castle(0, 0);
        objects[width - 1][height - 1] = new Castle(width - 1, height - 1);
        //артефакты и деньги
        while (true) {
            int[] placeForGrail = generateRandomCoordinates();
            if (objects[placeForGrail[0]][placeForGrail[1]].getType() == MyObjectTypes.GROUND) {
                objects[placeForGrail[0]][placeForGrail[1]] = new HolyGrail(placeForGrail[0], placeForGrail[1], objects[placeForGrail[0]][placeForGrail[1]]);
                break;
            }
        }

        int coinCnt;
        int artsCnt;
        switch (size) {
            case 2:
                coinCnt = 10;
                artsCnt = 7;
                break;
            default:
                coinCnt = 7;
                artsCnt = 3;
        }
        int[] place;
        int i = 0;
        while (i < coinCnt) {
            place = generateRandomCoordinates();
            if ((place[0] != 0 && place[1] != 0) && (place[0] != width - 1 && place[1] != height - 1)) {
                objects[place[0]][place[1]] = new Coin(place[0], place[1], objects[place[0]][place[1]]);
                i++;
            }
        }
        i = 0;
        while (i < artsCnt) {
            place = generateRandomCoordinates();
            if ((place[0] != 0 && place[1] != 0) && (place[0] != width - 1 && place[1] != height - 1)) {
                objects[place[0]][place[1]] = new Artefact(place[0], place[1], objects[place[0]][place[1]]);
                i++;
            }
        }
    }

    public void genZone(MyObject[][] objectcts1, int x, int y, int width, int height) {
        int r = 5;
        for (int i = -r; i <= r; i++) {
            for (int j = -r; j <= r; j++) {
                if (j * j + 3 * i * i <= r * r) {
                    if (x + j >= 0 && x + j < width && y + i >= 0 && y + i < height) {
                        objectcts1[x + j][y + i] = new Zone(x + j, y + i);
                    }
                }
            }
        }
    }

    public MyObject[][] getObjects() {
        return objects;
    }

    public MyCharacter[][] getCharacters() {
        return characters;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void addCharacter(MyCharacter character1, int x, int y) {
        this.characters[x][y] = character1;
    }

    public void updateMap() {
        System.out.print("\n  ");
        for (int i = 0; i < width; i++) {
            System.out.print(i % 10+" ");
        }

        System.out.println();
        for (int y = 0; y < height; y++) {
            System.out.print(y % 10 + " ");
            for (int x = 0; x < width; x++) {
                if (characters[x][y] != null) {
                    System.out.print(characters[x][y].getType().getSymbol()+" ");
                } else {
                    System.out.print(objects[x][y].getType().getSymbol()+" ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public int moveCharacterStraight(MyCharacter character1, int x, int y) {
        int[] pos = getDistance(character1, x, y);
        if ((pos[0] < 0 || pos[0] > width - 1 || pos[1] < 0 || pos[1] > height - 1)) {
            return 0;
        }
        characters[character1.getX()][character1.getY()] = null;
        if (character1.getHP() > 0) {
            characters[pos[0]][pos[1]] = character1;
            character1.setPosition(pos[0], pos[1]);
        }
        return pos[2];
    }


    private List<int[]> getLinePoints(int x1, int y1, int x2, int y2) {
        List<int[]> points = new ArrayList<>();

        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;
        int err = dx - dy;

        while (true) {
            points.add(new int[]{x1, y1});

            if (x1 == x2 && y1 == y2) {
                break;
            }

            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }
        }

        return points;
    }

    private int[] getDistance(MyCharacter character1, int x2, int y2) {
        if (x2 < 0 || x2 > width - 1 || y2 < 0 || y2 > height - 1) {
            System.out.println("We cant go here my majesty!");
            return new int[]{character1.getX(), character1.getY(), 0};
        }
        int x1 = character1.getX();
        int y1 = character1.getY();
        int speed = character1.getSpeed();

        int coins = 0;

        int distanceGone = 0;

        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;
        int err = dx - dy;

        //значение предыдущей клекти
        int lastX=x1;
        int lastY=y1;

        while (speed - distanceGone > 0) {

            if (x1 == x2 && y1 == y2) {
                break;
            }

            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }

            distanceGone += objects[x1][y1].getMovementPenalty();

            if (characters[x1][y1] != null) {
                if (characters[x1][y1].getEvil() != character1.getEvil()) {
                    Fight(character1, characters[x1][y1]);
                }
                return new int[]{lastX, lastY, coins};
            }
            if (objects[x1][y1].getType() == MyObjectTypes.WALL) {
                System.out.println("there is a wall here!");
                return new int[]{lastX, lastY, coins};
            }

            if (objects[x1][y1] != null) {
                if (objects[x1][y1].getType() == MyObjectTypes.ARTEFACT || objects[x1][y1].getType() == MyObjectTypes.COIN) {
                    System.out.println(character1.getType() + " has collected " + objects[x1][y1].getType());
                    if (objects[x1][y1].getType() == MyObjectTypes.COIN) {
                        coins += 300;
                    }
                    if (objects[x1][y1].getType() == MyObjectTypes.ARTEFACT) {
                        character1.increaseDamage(5);
                    }
                    objects[x1][y1] = objects[x1][y1].getUnderObject();
                }
            }
            //обновляем предыдущие значения
            lastX=x1;
            lastY=y1;

        }
        return new int[]{x1, y1, coins};
    }

    public void Fight(MyCharacter first, MyCharacter second) {
        System.out.println(first.getType() + " atacks " + second.getType());
        System.out.println();

        if (!(second instanceof Hero)) {
            second.setHP(0);
        }

        int[] PreparedArmyFirst= PrepareArmy(first);
        int HPFirst = PreparedArmyFirst[0];
        int DamageFirst = PreparedArmyFirst[1];
        System.out.println();
        int[] PreparedArmySecond = PrepareArmy(second);
        int HPSecond = PreparedArmySecond[0];
        int DamageSecond = PreparedArmySecond[1];

        System.out.println();

        while (true) {
            HPSecond= Atack(first, second, HPFirst, DamageFirst, HPSecond, DamageSecond);
            if (HPSecond <= 0) {
                break;
            }

            HPFirst = Atack(second, first, HPSecond, DamageSecond, HPFirst, DamageSecond);
            if (HPFirst <= 0) {
                break;
            }
        }

        System.out.println();

        if (HPFirst <= 0) {
            DefeatingEnemy(first,second);
        } else {
            DefeatingEnemy(second,first);
        }
    }

    private int[] PrepareArmy(MyCharacter character){
        int HP=0;
        int Damage=0;
        if (character.getUnits() != null) {
            if (character.getUnits().size() != 0) {
                for (int i = 0; i < character.getUnits().size(); i++) {
                    HP += character.getUnits().get(i).getHP();
                    Damage += character.getUnits().get(i).getDamage();
                }
                System.out.println(character.getType() + " has " + character.getUnits().size() + " units");
                System.out.println(character.getType() + " army stats:\n HP: " + HP + "\nDamage: " + Damage);
            } else {
                HP = character.getHP();
                Damage = character.getDamage();
                System.out.println(character.getType() + " is going to fight alone");
                System.out.println(character.getType() + " hero stats:\n HP: " + HP + "\nDamage: " + Damage);
            }
        }
        return new int[] {HP,Damage};
    }

    private int Atack(MyCharacter first, MyCharacter second , int HPFirst, int DamageFirst, int HPSecond, int DamageSecond){
        HPSecond -= DamageFirst;
        System.out.println(first.getType() + " deals " + second.getType() + " " + DamageFirst + " damage");
        System.out.println(second.getType() + " HP: " + HPSecond);
        System.out.println();
        return HPSecond;
    }

    private void DefeatingEnemy(MyCharacter first, MyCharacter second){
        System.out.println(second.getType() + " has won");
        if (first.getHoldsTheGrail()) {
            first.setHoldsTheGrail(false);
            second.setHoldsTheGrail(true);
            second.setSpeed(second.getSpeed() / 2);
            System.out.println(second.getType() + " now is holding the Grail!");
        }

        characters[first.getX()][first.getY()] = null;
        first.setHP(0);
        first = null;
    }

    protected int digTheGrail(MyCharacter hero) {
        if (objects[hero.getX()][hero.getY()].getType() == MyObjectTypes.HOLYGRAIL) {
            System.out.println(hero.getType() + " now is holding the Holy Grail!");
            objects[hero.getX()][hero.getY()] = objects[hero.getX()][hero.getY()].getUnderObject();
            hero.setHoldsTheGrail(true);
            hero.setSpeed(hero.getSpeed() / 2);
            return 2;
        }
        if (objects[hero.getX()][hero.getY()].getType() == MyObjectTypes.GROUND) {
            System.out.println("There is no Holy Grail My Lord...");
            logger.warn("Раскопка грааля не удалась: нет граля в месте раскопок");
            return 1;
        }
        System.out.println("We cant dig in this place My Lord");
        logger.warn("Раскопка грааля не удалась: здесь нельзя копать");
        return 1;
    }

    private int[] generateRandomCoordinates() {
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        return new int[]{x, y};
    }

}
