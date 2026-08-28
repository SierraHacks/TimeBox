import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.Timer;

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
        
    }

}