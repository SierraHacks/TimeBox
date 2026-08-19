import java.util.ArrayList;
public class Player{
    static int level = 0;
    private int health;
    private ArrayList<String> inventory = new ArrayList<>();
    private double xcoord;
    private double ycoord;

//figure out weapons/crafting
    public Player(int health, int xcoord, int ycoord){
        this.health = health;
        this.xcoord = xcoord;
        this.ycoord = ycoord;
    }
    public void attack(){
        // returns attack damage based on equipped weapons
    }
    public void takeDamage(){
        //take damage based on enemy strength, no damage if block
    }
    public void craft(){
        //figure out crafting logic based on inventory
    }

}