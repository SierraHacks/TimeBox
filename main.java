import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import java.awt.event.*;
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

        // Adjust size and color of start button
        start.setPreferredSize(new Dimension(100, 30));
        start.setBackground(Color.green);

        // Adjust the size and color of the stop button
        quit.setPreferredSize(new Dimension(75, 25));
        quit.setBackground(Color.red);

        pane.add(start);
        pane.add(quit);

        // Inventory Panel
        JPanel inventory = new JPanel();
        inventory.setLayout(new BoxLayout(inventory, BoxLayout.Y_AXIS));
        inventory.setBackground(Color.WHITE);
        JLabel inventoryTitle = new JLabel("Inventory");
        inventory.setPreferredSize(new Dimension(200, 150));
        inventory.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        inventory.add(inventoryTitle);
        pane.add(inventory); // Add inventory to the center panel, not the frame directly

        // Inventory visibility toggle
        inventory.setVisible(false);

        // craft panel
        JPanel craft = new JPanel(new BorderLayout());
        JLabel status = new JLabel("");
        craft.setName("craft");
        craft.add(new JLabel("Crafting"), BorderLayout.NORTH);
        craft.setPreferredSize(new Dimension(100, 100));
        craft.add(status, BorderLayout.SOUTH);
        craft.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        craft.setVisible(false);
        pane.add(craft);
        // Show the frame
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setVisible(true);

        // KEY BINDINGS
        setupPlayerBindings();
        inventoryKeyBinding(inventory, pane);
        craftButtonKey(craft,status);
    }
    // inventory toggle upon pressing "c" key
    public static void inventoryKeyBinding(JPanel inventory, JPanel center) {
        Action displayInventory = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean visible = inventory.isVisible();
                Map<String, Integer> playerInventory = player.getInventory();
                inventory.removeAll();
                JLabel inventoryTitle = new JLabel("Inventory");
                inventory.add(inventoryTitle);
                for (String key : playerInventory.keySet()) {
                    inventory.add(new JLabel(key + " x" + playerInventory.get(key)));
                }
                inventory.setVisible(!visible);
                inventory.revalidate();
                inventory.repaint();

                center.revalidate();
                center.repaint();
            }
        };

        center.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_I, 0),
                "toggleInventory");
        center.getActionMap().put("toggleInventory", displayInventory);
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
    public static void craftButtonKey(JPanel craft, JLabel status) {
        Action openCraftMenu = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(craft.isVisible()){
                    craft.setVisible(false);
                    return;
                }
                craft.removeAll();
                craft.add(new JLabel("Crafting"), BorderLayout.NORTH);
                craft.add(status, BorderLayout.SOUTH);
                JPanel weaponGrid = new JPanel(new GridLayout());
                for (Weapon w : Weapon.weaponList) {
                    JButton weapon = new JButton(w.getName());
                    weaponCraft(weapon, craft, status);
                    weaponGrid.add(weapon);
                }
                craft.add(weaponGrid, BorderLayout.CENTER);
                craft.setVisible(true);
                craft.revalidate();
                craft.repaint();
            }
        };
        craft.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_C, 0), "openCraft");

        craft.getActionMap()
                .put("openCraft", openCraftMenu);
    };

    public static void weaponCraft(JButton button, JPanel c, JLabel stat) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player.craft(button.getText())) {
                    stat.setText("Successfully crafted a " + button.getText());
                } else {
                    stat.setText("Craft Failed. You either lack the materials to craft a " + button.getText() + " or already possess one.");
                }
                c.repaint();
                c.revalidate();
            }
        });
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                player = new Player(100, 0, 0);
                createAndShowGUI();
            }
        });
    }

}
