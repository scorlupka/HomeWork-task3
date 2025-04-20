package playable;

import placable.MyObjectTypes;

import java.util.ArrayList;

public abstract class MyCharacter {

    protected boolean holdsTheGrail = false;
    protected boolean isEvil;
    protected int x;
    protected int y;
    protected MyCharacterTypes type;
    protected int HP;
    protected int damage;
    protected int speed;

    public MyCharacter(int x, int y, int hp, int damage, int speed, MyCharacterTypes Type){
        this.x=x;
        this.y=y;
        this.HP=hp;
        this.damage=damage;
        this.speed=speed;
        this.type = Type;
    }
    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getHP() {

        return HP;
    }

    public void increaseHP(int hp) {
        this.HP += hp;
    }

    public void setHoldsTheGrail(boolean isHoldingTheGrail) {
        System.out.println("this character cant use the Grail");
    }

    public boolean getHoldsTheGrail() {
        return holdsTheGrail;
    }


    public void addHorse() {
        this.speed += 2;
    }

    public boolean getEvil() {
        return this.isEvil;
    }

    public void increaseDamage(int damage) {
        this.damage += damage;
    }

    public int getDamage() {

        return damage;
    }

    public MyCharacterTypes getType() {
        return type;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setSpeed(int speed) {

        this.speed = speed;
    }

    public int getSpeed() {

        return speed;
    }

    public void addUnits(MyCharacter unit) {
        if (this instanceof Hero) {
            ((Hero) this).addUnits(unit);
            return;
        }
        System.out.println("This is not a hero my majesty");
    }

    public ArrayList<MyCharacter> getUnits() {
        if (this instanceof Hero) {
            return ((Hero) this).getUnits();
        }
        return null;
    }

}
