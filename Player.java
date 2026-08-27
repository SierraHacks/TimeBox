import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.List;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;

public class Player {
    static int level = 0;
    private int health;
    private Weapon weapon;
    private Map<String, Integer> inventory = new HashMap<>();
    private List<Weapon> weapons = new ArrayList<>();
    private double xcoord;
    private double ycoord;
    private BufferedImage walkingLeft, walkingRight, walkingUp, walkingDown, idleUp, idleDown;
    

    // figure out weapons/crafting
    public Player(int health, int xcoord, int ycoord) {
        this.health = health;
        this.xcoord = xcoord;
        this.ycoord = ycoord;
    }

    public int attack() {
        return 5 + this.weapon.getDamage();
    }

    public void takeDamage(int damage, boolean block) {
        if (!block) {
            this.health -= damage;
        }
    }

    public void setxcoord(int new_xcoord) {
        this.xcoord = new_xcoord;
    }

    public void setycoord(int new_ycoord){
        this.ycoord = new_ycoord;
    }

    private static Weapon weaponCheck(String weaponChoice) {
        for (int i = 0; i < Weapon.weaponList.size(); i++) {
            Weapon w = Weapon.weaponList.get(i);
            if (weaponChoice.equals(w.getName())) {
                return w;
            }
        }
        return null;
    }

    public void craft(Weapon w) {
        if (Player.weaponCheck(w.getName()) != null) {
            if (this.inventory.keySet().containsAll(w.getRecipe().keySet())) {
                this.weapons.add(w);
                for (String key : w.getRecipe().keySet()) {
                    this.inventory.put(key, this.inventory.get(key) - w.getRecipe().get(key));
                }
                this.weapons.add(w);
            }
        }
    }

    public void equipWeapon(Weapon weapon) {
        if (this.weapons.contains(weapon)) {
            this.weapon = weapon;
        }
    }

    public Map<String, Integer> getInventory() {
        return Collections.unmodifiableMap(this.inventory);
    }

}