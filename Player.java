import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.List;

public class Player {
    static int level = 0;
    private int health;
    private Weapon weapon;
    private Map<String, Integer> inventory = new HashMap<>();
    private List<Weapon> weapons = new ArrayList<>();
    private double xcoord;
    private double ycoord;

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

    public void getxcoord(double xcoord) {
        this.xcoord = xcoord;
    }

    public void getycoord(double ycoord){
        this.ycoord = ycoord;
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

}