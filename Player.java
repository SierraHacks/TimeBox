import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.net.URL;
import javax.swing.ImageIcon;

public class Player {
    static int level = 0;
    private int health;
    private Weapon weapon;
    private Map<String, Integer> inventory = new HashMap<>();
    private ArrayList<Weapon> weapons = new ArrayList<>();
    
    // Kept as doubles for precise movement accumulation if needed
    private double xcoord;
    private double ycoord;
    
    private boolean movingLeft = false;
    private boolean movingRight = false;
    private boolean movingUp = false;
    private boolean movingDown = false;
    private double speed = 10;
    private String direction = "right";
    
    private ImageIcon moving_leftSprite;
    private ImageIcon moving_rightSprite;
    private ImageIcon idle_upSprite;
    private ImageIcon idle_downSprite;
    private ImageIcon idle_leftSprite;
    private ImageIcon idle_rightSprite;

    public Player(int health, int xcoord, int ycoord) {
        this.health = health;
        this.xcoord = xcoord;
        this.ycoord = ycoord;
        
        // FIXED: You must call loadSprites inside the constructor, 
        // otherwise your sprites stay null!
        loadSprites(); 
    }

    public void update() {
        if (movingLeft) xcoord -= speed;
        if (movingRight) xcoord += speed;
    }

    private void loadSprites(){
        // FIXED: Added a leading "/" to all paths so Java looks in the root resource directory
        idle_rightSprite   = loadImage("/movement sprites/char_idle_right_anim.gif");
        moving_rightSprite = loadImage("/movement sprites/char_run_right_anim.gif");
        idle_leftSprite    = loadImage("/movement sprites/char_idle_right_anim.gif");
        moving_leftSprite  = loadImage("/movement sprites/char_run_left_anim.gif");
    }

    private ImageIcon loadImage(String path) {
        URL imgUrl = getClass().getResource(path);
        if (imgUrl != null) {
            return new ImageIcon(imgUrl);
        } else {
            System.err.println("Error: Could not find GIF asset at " + path);
            return null;
        }
    }

public ImageIcon getActiveSprite() {
    switch (direction) {
        case "left":
            return moving_leftSprite;
        default:
            return moving_rightSprite;
    }
}

    public int attack() {
        return 5 + this.weapon.getDamage();
    }

    public void takeDamage(int damage, boolean block) {
        if (!block) {
            this.health -= damage;
        }
    }

    // FIXED: Cast the double coordinates to integers here.
    // This allows g.drawImage(..., player.getX(), player.getY(), ...) to compile flawlessly!
    public int getX() { 
        return (int) xcoord; 
    }

    public int getY() { 
        return (int) ycoord; 
    }

    public void setDirection(String direction) { 
        this.direction = direction; 
    }

    public void setxcoord(double new_xcoord) { 
        this.xcoord = new_xcoord; 
    }

    public void setycoord(double new_ycoord){ 
        this.ycoord = new_ycoord; 
    }

    public void setMovingLeft(boolean moving)   { this.movingLeft = moving; }
    public void setMovingRight(boolean moving)  { this.movingRight = moving; }

    private boolean craftHelper(Weapon w) {
        if (this.inventory.keySet().containsAll(w.getRecipe().keySet())) {
            for (String key : w.getRecipe().keySet()) {
                boolean matreq = this.inventory.get(key) - w.getRecipe().get(key) >= 0;
                if(!matreq){
                    return false;
                }
            }
            for (String key : w.getRecipe().keySet()) {
                this.inventory.put(key, this.inventory.get(key) - w.getRecipe().get(key));
            }
            if (!this.weapons.contains(w)){
                this.weapons.add(w);
                return true;
            }
        }
        return false;
    }

    private static Weapon weaponRetrieve(String weaponChoice) {
        for (int i = 0; i < Weapon.weaponList.size(); i++) {
            Weapon w = Weapon.weaponList.get(i);
            if (weaponChoice.equals(w.getName())) {
                return w;
            }
        }
        return null;
    }

    public boolean craft(String wName){
        if(weaponRetrieve(wName) != null){
            return this.craftHelper(weaponRetrieve(wName));
        }
        return false;
    }

    public void equipWeapon(Weapon weapon) {
        if (this.weapons.contains(weapon)) {
            this.weapon = weapon;
        }
    }

    public Map<String, Integer> getInventory() {
        return Collections.unmodifiableMap(this.inventory);
    }

    public ArrayList<Weapon> getWeapons(){
        return this.weapons;
    }

    public void updateInventory(String key, int add){
        this.inventory.put(key, this.inventory.get(key)+add);
    }

}