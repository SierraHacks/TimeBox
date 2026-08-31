import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
public class Weapon {
    static ArrayList<Weapon> weaponList = new ArrayList<>(List.of());
    private String name;
    private Map<String, Integer> recipe = new HashMap<>();
    private int damage;
    private int range;
    public Weapon(HashMap<String, Integer> recipe, int damage, String name, int range){
        this.name = name;
        this.recipe = recipe;
        this.damage = damage;
        this.range = range;
        weaponList.add(this);
    }
    public int getDamage(){
        return this.damage;
    }
    public String getName(){
        return this.name;
    }
public static void initWeapons() {
    Weapon warHammer = new Weapon(
        new HashMap<>(Map.of("Wood", 2, "Steel", 10)),
        5,
        "War Hammer",
        10
    );

    Weapon sword = new Weapon(
        new HashMap<>(Map.of("Wood", 2, "Steel", 5)),
        10,
        "Sword",
        18
    );

    Weapon poleaxe = new Weapon(
        new HashMap<>(Map.of("Wood", 5, "Steel", 15)),
        25,
        "Poleaxe",
        28
    );
}

    public Map<String, Integer> getRecipe() {
        return Collections.unmodifiableMap(this.recipe);
    }
    public int getRange(){
        return this.range;
    }

    
}
