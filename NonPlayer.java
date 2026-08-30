public class NonPlayer{
   private String name;
   private int health;
   private int damage;
   public NonPlayer(String name, int health, int damage) {
       this.name = name;
       this.health = health;
       this.damage = damage;
   }
   public void takeDamage(int damage) {
       this.health -= damage;
   }
   public int attack(){
    return this.damage;
   }
   public int getHealth(){
    return this.health;
   }
}
