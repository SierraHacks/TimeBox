import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.Timer;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
public class Dragon extends NonPlayer {
    public enum State { IDLE, DASH, CLAW_SWIPE, FIRE_BREATH, DEFEAT }
    private State currentState = State.IDLE; //short for currentState, state determines current movement type

    
    private int x, y;
    private int currentFrame = 0;
    private BufferedImage[] currentAnim;
    
    //Arrays for the animations
    private BufferedImage[] idleFrames;
    private BufferedImage[] dashFrames;
    private BufferedImage[] fireFrames;

    public Dragon(String name, int health, int damage, int startX, int startY) {
        super(name, health, damage);
        this.x = startX;
        this.y = startY;
        loadSprites(); 
        currentAnim = idleFrames;
    }

    private void loadSprites() {
        //need to use x,y coordinates here from the png image
        //Alright, remind me to never do sprites lke this again

        try {
            BufferedImage spriteSheet = ImageIO.read(new File("Dragon.png"));

            // IDLE ANIMATION (Using your extracted coordinates)
            idleFrames = new BufferedImage[3];
            idleFrames[0] = spriteSheet.getSubimage(3, 0, 81, 79);   // sprite1
            idleFrames[1] = spriteSheet.getSubimage(102, 15, 75, 67); // sprite6
            idleFrames[2] = spriteSheet.getSubimage(185, 11, 73, 68); // sprite5

            // Set the default state so the dragon appears immediately
            
            fireFrames = new BufferedImage[3];
            fireFrames[0] = spriteSheet.getSubimage(520, 420, 160, 150); 
            fireFrames[1] = spriteSheet.getSubimage(680, 420, 160, 150); 

            // Tweak this X coordinate (840) left or right until it grabs the head
            fireFrames[2] = spriteSheet.getSubimage(840, 420, 300, 150);
            currentAnim = idleFrames;

        } catch (IOException e) {
            System.err.println("Failed to load Dragon.png.");
            e.printStackTrace();
        }
    }

               //working on AI
    public void updateAI(Player player) {
        //commenting out AI to debug animations
        /*if (currentState == State.DEFEAT || currentState == State.FIRE_BREATH) return; // Lock state

        int distX = Math.abs(player.getX() - this.x);
        
        if (distX < 100) {
            changeState(State.CLAW_SWIPE, dashFrames); // Switch to close combat
        } else if (distX < 300) {
            changeState(State.FIRE_BREATH, fireFrames); // Ranged attack
        } else {
            changeState(State.IDLE, idleFrames);
        }*/
    }

    private void changeState(State newState, BufferedImage[] newAnim) {
        if (currentState != newState) {
            currentState = newState;
            currentAnim = newAnim;
            currentFrame = 0; // Reset animation loop
        }
    }
    public void tickAnimation() {
        if (currentAnim != null) {
            currentFrame = (currentFrame + 1) % currentAnim.length;
        }
    }

    public void draw(Graphics g) {
        if (currentAnim != null && currentAnim[currentFrame] != null) {
            g.drawImage(currentAnim[currentFrame], x, y, null);
        }
    }



}