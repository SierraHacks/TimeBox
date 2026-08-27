import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Map;

public class main {
    static Player player;
    
    public static class MoveAction extends AbstractAction {
        private int dx;
        private int dy;
        private int playerX;
        private int playerY;

        public MoveAction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            playerX += dx;
            playerY += dy;
            player.setxcoord(playerX);
            player.setycoord(playerY);
            
        }

    }
    public static void createAndShowGUI() {
        JFrame frame = new JFrame("Time Box");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Use BorderLayout for the main frame
        frame.setLayout(new BorderLayout());

        // Add title
        JLabel title = new JLabel("Time Box", SwingConstants.CENTER);
        Font heading = new Font("Serif", Font.BOLD, 20); // "Times Roman" is better written as "Serif" in Java
        title.setFont(heading);
        title.setPreferredSize(new Dimension(300, 50));
        title.setOpaque(true);
        title.setBackground(Color.decode("#f0e9e9"));
        frame.add(title, BorderLayout.PAGE_START);

        // Adds a flow panel at center of screen
        JPanel pane = new JPanel(new FlowLayout());
        pane.setPreferredSize(new Dimension(300, 400));
        pane.setBackground(Color.WHITE);
        pane.setOpaque(true);
        frame.getContentPane().add(pane, BorderLayout.CENTER);

        // Add Start and Quit Button
        JButton start = new JButton("Start");
        JButton quit = new JButton("Quit");

        //Adjust size and color of start button
        start.setPreferredSize(new Dimension(100, 30));
        start.setBackground(Color.green);

        // Adjust the size and color of the stop button
        quit.setPreferredSize(new Dimension(75, 25));
        quit.setBackground(Color.red);

        pane.add(start);
        pane.add(quit);

        // Inventory Panel
        JPanel inventory = new JPanel();
        inventory.setBackground(Color.WHITE);
        JLabel inventoryTitle = new JLabel("Inventory");
        inventory.setBounds(50, 70, 200, 100); // Absolute bounds work safely inside a container that uses null layout
        inventory.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        
        inventory.add(inventoryTitle);
        pane.add(inventory); // Add inventory to the center panel, not the frame directly

        // Inventory visibility toggle
        inventory.setVisible(false);
        
        // Show the frame
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setVisible(true);

        // KEY BINDINGS
        setupPlayerBindings();
        inventoryKeyBinding(inventory, pane);
    }

    public static void setupPlayerBindings(){
        InputMap playerInputMap = new JPanel().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap playerActionMap = new JPanel().getActionMap();
        playerInputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP,0), "moveUp");
        playerInputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,0), "moveDown");
        playerInputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,0), "moveLeft");
        playerInputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,0), "moveRight");

        playerActionMap.put("moveUp", new MoveAction(0, -1));
        playerActionMap.put("moveDown", new MoveAction(0, 1));
        playerActionMap.put("moveLeft", new MoveAction(-1, 0));
        playerActionMap.put("moveRight", new MoveAction(1, 0));
    }

    public static void inventoryKeyBinding(JPanel inventory, JPanel center) {
        Action displayInventory = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean visible = inventory.isVisible();
                Map<String, Integer> playerInventory = player.getInventory();
                inventory.removeAll();
                for(String key: playerInventory.keySet()){
                    inventory.add(new JLabel(key + "x " + playerInventory.get(key)));
                }
                inventory.setVisible(!visible);
                inventory.repaint();
            }
        };
        
        center.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_C,0), "toggleInventory");
        center.getActionMap().put("toggleInventory", displayInventory);
    }
    public static void main(String[] args) { 
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                player = new Player(100,0,0);
                createAndShowGUI();
            }
        });
    }

    


}
