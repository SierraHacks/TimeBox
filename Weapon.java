import java.util.ArrayList;
public class Weapon {
    private String name;
    private ArrayList<String> recipe = new ArrayList<>();
    private int damage;
    public Weapon(ArrayList<String> recipe, int damage, String name){
        this.name = name;
        this.recipe = recipe;
        this.damage = damage;
    }
    
}
