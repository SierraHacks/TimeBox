import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
public class Weapon {
    static ArrayList<Weapon> weaponList = new ArrayList<>();
    private String name;
    private Map<String, Integer> recipe = new HashMap<>();
    private int damage;
    public Weapon(HashMap<String, Integer> recipe, int damage, String name){
        this.name = name;
        this.recipe = recipe;
        this.damage = damage;
        weaponList.add(this);
    }
    public int getDamage(){
        return this.damage;
    }
    public String getName(){
        return this.name;
    }
    public Map<String, Integer> getRecipe() {
        return Collections.unmodifiableMap(this.recipe);
    }

    
}
