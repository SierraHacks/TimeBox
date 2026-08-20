public class NonPlayer{
   private String name;
   private int health;
   public NonPlayer(String name, int health) {
       this.name = name;
       this.health = health;
   }
   public void takeDamage(int damage) {
       health -= damage;
   }
}
