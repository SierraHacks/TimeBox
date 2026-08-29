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

    private void loadSprites() {}
        

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