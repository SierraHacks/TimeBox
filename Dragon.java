import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.Timer;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
public class Dragon extends NonPlayer {
    public enum State { IDLE, DASH, CLAW_SWIPE, FIRE_BREATH, DEFEAT }
    private State currentState = State.IDLE; //short for currentState, state determines current movement type

    private static final int[][] IDLE_RECTS = {
        {  13,  44, 196, 161},
        { 257,  44, 187, 161},
        { 466,  44, 180, 161}
    };
    private static final int[][] DASH_RECTS = {
        {  10, 248, 205, 135},
        { 220, 248, 275, 135},
        { 500, 248, 260, 135}
    };
    private static final int[][] CLAW_RECTS = {
        { 795, 265, 180, 135},
        { 975, 265, 140, 135},
        {1137, 265, 178, 135},
        {1318, 265, 208, 135}
    };
    private static final int[][] FIRE_RECTS = {
        { 795, 450, 165, 135},
        { 968, 450, 190, 135},
        {1163, 440, 345, 150}
    };
    private static final int[][] HIT_RECTS = {
        {  11, 838, 120, 150},
        { 150, 838, 120, 150},
        { 288, 838, 120, 150}
    };
    private static final int[][] DEFEAT_RECTS = {
        { 420, 838, 205, 150},
        { 630, 838, 190, 150},
        { 820, 838, 140, 150}
    };

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