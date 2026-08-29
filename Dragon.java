import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.Timer;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
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
    private static final int DRAW_HEIGHT = 110;

    private int x, y;
    private int currentFrame = 0;
    private BufferedImage[] currentAnim;

    private BufferedImage[] idleFrames;
    private BufferedImage[] dashFrames;
    private BufferedImage[] clawFrames;
    private BufferedImage[] fireFrames;
    private BufferedImage[] hitFrames;
    private BufferedImage[] defeatFrames;

    
    // Attacks play through once, then the dragon goes back to idle for a moment.
    private boolean attackLocked = false;
    private int cooldownTicks = 0;
    public Dragon(String name, int health, int damage, int startX, int startY) {
        super(name, health, damage);
        this.x = startX;
        this.y = startY;
        loadSprites(); 
        currentAnim = idleFrames;
    }

    private void loadSprites() {
        try {
            BufferedImage sheet = ImageIO.read(new File("Dragon.png"));
            idleFrames   = slice(sheet, IDLE_RECTS);
            dashFrames   = slice(sheet, DASH_RECTS);
            clawFrames   = slice(sheet, CLAW_RECTS);
            fireFrames   = slice(sheet, FIRE_RECTS);
            hitFrames    = slice(sheet, HIT_RECTS);
            defeatFrames = slice(sheet, DEFEAT_RECTS);
        } catch (IOException e) {
            System.err.println("Failed to load Dragon.png -- is it in the working directory?");
            e.printStackTrace();
        }
    }
    private BufferedImage[] slice(BufferedImage sheet, int[][] rects) {
        BufferedImage[] frames = new BufferedImage[rects.length];
        for (int i = 0; i < rects.length; i++) {
            int rx = rects[i][0], ry = rects[i][1], rw = rects[i][2], rh = rects[i][3];
            rw = Math.min(rw, sheet.getWidth()  - rx);
            rh = Math.min(rh, sheet.getHeight() - ry);
            frames[i] = sheet.getSubimage(rx, ry, rw, rh);
        }
        return frames;
    }
    //Updated AI, for some reason, the dragon did not move at all earlier, so I jsut reworked it
    public void updateAI(Player player) {
        if (currentState == State.DEFEAT) return;

        // Let an attack animation finish before choosing a new move.
        if (attackLocked) {
            if (currentFrame == currentAnim.length - 1) {
                attackLocked = false;
                cooldownTicks = 8; // ~0.8s at a 100ms timer
                changeState(State.IDLE, idleFrames);
            }
            return;
        }
        if (cooldownTicks > 0) {
            cooldownTicks--;
            return;
        }
        int dx = player.getX() - this.x;
        int dy = player.getY() - this.y;
        double dist = Math.sqrt(dx * dx + dy * dy);

        if (dist < 100) {
            startAttack(State.CLAW_SWIPE, clawFrames);
        } else if (dist < 300) {
            startAttack(State.FIRE_BREATH, fireFrames);
        } else {
            // Too far away: charge toward the player instead of standing still.
            changeState(State.DASH, dashFrames);
            this.x += Integer.signum(dx) * 4;
            this.y += Integer.signum(dy) * 4;
        }

    }    
    
    
    private void startAttack(State s, BufferedImage[] anim) {
        changeState(s, anim);
        attackLocked = true;
    }
    private void changeState(State newState, BufferedImage[] newAnim) {
        if (currentState != newState) {
            currentState = newState;
            currentAnim = newAnim;
            currentFrame = 0; // Reset animation loop
        }
        
    }
    public void tickAnimation() {
        if (currentAnim == null || currentAnim.length == 0) return;
        if (attackLocked && currentFrame == currentAnim.length - 1) return; // hold last attack frame
        currentFrame = (currentFrame + 1) % currentAnim.length;
    }

    public void draw(Graphics g) {
        if (currentAnim == null) return;
        BufferedImage frame = currentAnim[currentFrame];
        if (frame == null) return;

        double scale = (double) DRAW_HEIGHT / frame.getHeight();
        int w = (int) (frame.getWidth() * scale);
        int h = DRAW_HEIGHT;

        Graphics2D g2 = (Graphics2D) g.create();
        // Pixel art: keep the hard edges instead of blurring them.
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                            RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2.drawImage(frame, x, y, w, h, null);
        g2.dispose();

        // g.drawRect(x, y, w, h);  // uncomment to see the hitbox while debugging
    }

    public State getState()  { return currentState; }
    public int getX()        { return x; }
    public int getY()        { return y; }
    public void setPosition(int x, int y) { this.x = x; this.y = y; }

    //Alright, I tested this code on my VS Code editor, hopefully it works, because I cant run it here

}