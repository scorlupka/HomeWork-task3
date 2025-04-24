package partOfGame;

import playable.*;
import placable.*;

import java.util.Random;

public class Computer extends Player {
    private int phase = 0;

    transient final private Random random = new Random(5);

    //конструкторы
    public Computer(MyCharacter hero1, Map map) {
        super(hero1, map, new Game());
        this.money = 0;
        this.castlePosition = new int[]{map.getWidth() - 1, map.getHeight() - 1};
    }

    //сделать ход
    @Override
    public int makeMove(int move) {
        if (heroes.size() > 0) {
            if (heroes.get(0).getHoldsTheGrail()) {
                money += map.moveCharacterStraight(heroes.get(0), castlePosition[0], castlePosition[1]);
                prayToGod(this, castlePosition);
                return 1;
            }
            // идём собирать деньги
            if (phase == 0) {
                int[] nearestCoin = findNearestCoin(map.getObjects(), this.heroes.get(0).getX(), this.heroes.get(0).getY());
                money += map.moveCharacterStraight(heroes.get(0), nearestCoin[0], nearestCoin[1]);
                if (money == 600) {
                    phase = 1;
                }
            }
            //идем на базу
            if (phase == 1) {
                money += map.moveCharacterStraight(heroes.get(0), castlePosition[0], castlePosition[1]);
                if (heroes.get(0).getX() == castlePosition[0] && heroes.get(0).getY() == castlePosition[1]) {
                    phase = 2;
                }
            }
            //на базе покупаем юнитов
            if (phase == 2) {
                switch (random.nextInt(2)) {
                    case 0:
                        money -= 100;
                        Viking viking = new Viking(units.size() / 15, units.size() % 15);
                        units.add(viking);
                        heroes.get(0).addUnits(viking);
                        break;
                    case 1:
                        money -= 150;
                        Archer archer = new Archer(units.size() / 15, units.size() % 15);
                        units.add(archer);
                        heroes.get(0).addUnits(archer);
                        break;
                    default:
                        money -= 200;
                        Healer healer = new Healer(units.size() / 15, units.size() % 15);
                        units.add(healer);
                        heroes.get(0).addUnits(healer);
                        break;
                }
                if (heroes.get(0).getUnits().size() >= 3) {
                    phase = 3;
                }
            }

            if (phase == 3) {
                int[] nearestHero = findNearestHero(map.getCharacters(), this.heroes.get(0).getX(), this.heroes.get(0).getY());
                money += map.moveCharacterStraight(heroes.get(0), nearestHero[0], nearestHero[1]);
            }

            System.out.println("OPONENT MADE A MOVE MY MAJESTY!");
            map.updateMap();
        }
        prayToGod(this, castlePosition);
        return 1;
    }


    // Метод для поиска ближайшей монеты
    public static int[] findNearestCoin(MyObject[][] grid, int startX, int startY) {
        // Проверка на валидность входных данных
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            throw new IllegalArgumentException("Grid is empty or invalid.");
        }

        int rows = grid.length;
        int cols = grid[0].length;

        // Проверка, что начальная позиция находится в пределах массива
        if (startX < 0 || startX >= rows || startY < 0 || startY >= cols) {
            throw new IllegalArgumentException("Start position is out of bounds.");
        }

        int[] nearestCoin = null; // Координаты ближайшей монеты
        double minDistance = Double.MAX_VALUE; // Минимальное расстояние

        // Проходим по всем клеткам массива
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Если найдена монета
                if (grid[i][j].getType() == MyObjectTypes.COIN) {
                    // Вычисляем расстояние до монеты
                    double distance = Math.sqrt(Math.pow(i - startX, 2) + Math.pow(j - startY, 2));

                    // Если расстояние меньше минимального, обновляем ближайшую монету
                    if (distance < minDistance) {
                        minDistance = distance;
                        nearestCoin = new int[]{i, j};
                    }
                }
            }
        }

        // Возвращаем координаты ближайшей монеты (или null, если монет нет)
        return nearestCoin;
    }

    public int[] findNearestHero(MyCharacter[][] grid, int startX, int startY) {
        // Проверка на валидность входных данных
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            throw new IllegalArgumentException("Grid is empty or invalid.");
        }

        int rows = grid.length;
        int cols = grid[0].length;

        // Проверка, что начальная позиция находится в пределах массива
        if (startX < 0 || startX >= rows || startY < 0 || startY >= cols) {
            throw new IllegalArgumentException("Start position is out of bounds.");
        }

        int[] nearestHero = new int[]{0, 0};
        double minDistance = Double.MAX_VALUE; // Минимальное расстояние

        // Проходим по всем клеткам массива
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double distance = Math.sqrt(Math.pow(i - startX, 2) + Math.pow(j - startY, 2));
                // Если расстояние меньше минимального, обновляем ближайшую монету
                if (distance < minDistance && distance > 0 && grid[i][j] != null) {
                    minDistance = distance;
                    nearestHero = new int[]{i, j};
                }
            }
        }

        return nearestHero;
    }

    @Override
    public int[] getCastlePosition() {
        return castlePosition;
    }
}
