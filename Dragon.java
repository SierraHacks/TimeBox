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
            // Ensure "Dragon.png" is in your project's root or resource folder
            BufferedImage spriteSheet = ImageIO.read(new File("Dragon.png"));

            // IDLE / FLOAT: Row 1, Left Side (3 frames)
            // Estimated at roughly 130x130 pixels per frame
            idleFrames = new BufferedImage[3];
            idleFrames[0] = spriteSheet.getSubimage(10, 30, 130, 130);   // Frame 1
            idleFrames[1] = spriteSheet.getSubimage(150, 30, 130, 130);  // Frame 2
            idleFrames[2] = spriteSheet.getSubimage(290, 30, 130, 130);  // Frame 3

            // DASH / CHARGE: Row 2, Left Side (3 frames)
            // These frames are wider due to the speed lines and stretched body
            dashFrames = new BufferedImage[3];
            dashFrames[0] = spriteSheet.getSubimage(10, 190, 180, 120);
            dashFrames[1] = spriteSheet.getSubimage(200, 190, 180, 120);
            dashFrames[2] = spriteSheet.getSubimage(390, 190, 180, 120);

            // FIRE BREATH: Row 3, Right Side (3 frames)
            // The final frame is massive due to the flame effect
            fireFrames = new BufferedImage[3];
            fireFrames[0] = spriteSheet.getSubimage(520, 350, 150, 140); // Charge 1
            fireFrames[1] = spriteSheet.getSubimage(680, 350, 150, 140); // Charge 2
            fireFrames[2] = spriteSheet.getSubimage(840, 350, 240, 140); // Release Flame

            // Set default animation
            currentAnim = idleFrames;

        } catch (IOException e) {
            System.err.println("Failed to load Dragon.png. Check file path.");
            e.printStackTrace();
        }
    }
    //working on AI
    public void updateAI(Player player) {
        if (currentState == State.DEFEAT || currentState == State.FIRE_BREATH) return; // Lock state

        int distX = Math.abs(player.getX() - this.x);
        
        if (distX < 100) {
            changeState(State.CLAW_SWIPE, dashFrames); // Switch to close combat
        } else if (distX < 300) {
            changeState(State.FIRE_BREATH, fireFrames); // Ranged attack
        } else {
            changeState(State.IDLE, idleFrames);
        }
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