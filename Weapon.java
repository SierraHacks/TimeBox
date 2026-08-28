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
    Weapon sword = new Weapon(
        new HashMap<>(Map.of("Wood", 2, "Steel", 5)),
        10,
        "Sword",
        5
    );

    Weapon warHammer = new Weapon(
        new HashMap<>(Map.of("Wood", 2, "Steel", 10)),
        5,
        "War Hammer",
        3
    );

    Weapon poleaxe = new Weapon(
        new HashMap<>(Map.of("Wood", 5, "Steel", 15)),
        25,
        "Poleaxe",
        10
    );
    Weapon.weaponList.add(sword);
    Weapon.weaponList.add(warHammer);
    Weapon.weaponList.add(poleaxe);

}

    public Map<String, Integer> getRecipe() {
        return Collections.unmodifiableMap(this.recipe);
    }

    public static void initWeapons() {
    Weapon sword = new Weapon(
        new HashMap<>(Map.of("Wood", 2, "Steel", 5)),
        10,
        "Sword",
        5
    );

    Weapon warHammer = new Weapon(
        new HashMap<>(Map.of("Wood", 2, "Steel", 10)),
        5,
        "War Hammer",
        3
    );

    Weapon poleaxe = new Weapon(
        new HashMap<>(Map.of("Wood", 5, "Steel", 15)),
        25,
        "Poleaxe",
        10
    );
    Weapon.weaponList.add(sword);
    Weapon.weaponList.add(warHammer);
    Weapon.weaponList.add(poleaxe);
}


    
}
