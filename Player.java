import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
public class Player{
    static int level = 0;
    private int health;
    private Weapon weapon;
    private Map<String, Integer> inventory = new HashMap<>();
    private List<Weapon> weapons = new ArrayList<>();
    private double xcoord;
    private double ycoord;

//figure out weapons/crafting
    public Player(int health, int xcoord, int ycoord){
        this.health = health;
        this.xcoord = xcoord;
        this.ycoord = ycoord;
    }
    public int attack(){
        return 5+this.weapon.getDamage();
    }
    public void takeDamage(int damage, boolean block){
        if(!block){
            this.health -= damage;
        }
    }
    public void craft(){
        //figure out crafting logic based on inventory
    }
    public void equipWeapon(Weapon weapon){
        if(this.weapons.contains(weapon)){
            this.weapon = weapon;
        }
    }

}