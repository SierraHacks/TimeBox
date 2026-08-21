import java.util.Map;
import java.util.HashMap;
public class Weapon {
    private String name;
    private Map<String, Integer> recipe = new HashMap<>();
    private int damage;
    public Weapon(HashMap<String, Integer> recipe, int damage, String name){
        this.name = name;
        this.recipe = recipe;
        this.damage = damage;
    }
    public int getDamage(){
        return this.damage;
    }

    
}
