import java.util.Map;
public class Weapon {
    private String name;
    private Map<String, Integer> recipe = new Hashmap<>();
    private int damage;
    public Weapon(HashMap<String, Integer> recipe, int damage, String name){
        this.name = name;
        this.recipe = recipe;
        this.damage = damage;
    }
    
}
