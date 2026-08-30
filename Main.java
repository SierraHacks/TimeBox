import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.event.*;
import java.util.Map;

public class Main {
    static Player player;
    static Dragon dragon;

    public static class JPanelWithBackground extends JPanel {

        private Image backgroundImage;

        // Some code to initialize the background image.
        // Here, we use the constructor to load the image. This
        // can vary depending on the use case of the panel.
        public JPanelWithBackground(String fileName) throws IOException {
            backgroundImage = ImageIO.read(new File(fileName));
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Draw the background image.
            g.drawImage(backgroundImage, 0, 0, this);
        }
    }

    public static class MoveAction extends AbstractAction {
        private final int dx;
        private final int dy;
        private final String direction;
        private final Player player; // Reference to the actual player in your game
        private final JPanel pane;

        public MoveAction(JPanel pane, Player player, int dx, int dy, String direction) {
            this.pane = pane;
            this.player = player;
            this.dx = dx;
            this.dy = dy;
            this.direction = direction;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // 1. Get the current position directly from the player object

            // 2. Calculate new positions and update the player object
            player.setxcoord(Math.max(0, Math.min(player.getX()+dx, pane.getWidth() - 40)));
            player.setycoord(player.getY() + dy);

            player.setDirection(direction);

            pane.repaint();
        }

    }

    public static void createAndShowGUI() {
        JFrame frame = new JFrame("Time Box");
        frame.setSize(1280, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Use BorderLayout for the main frame
        frame.setLayout(new BorderLayout());

        // Add title
        JLabel title = new JLabel("Time Box", SwingConstants.CENTER);
        Font heading = new Font("Serif", Font.BOLD, 20);
        title.setFont(heading);
        title.setPreferredSize(new Dimension(300, 50));
        title.setOpaque(true);
        title.setBackground(Color.decode("#f0e9e9"));
        frame.add(title, BorderLayout.PAGE_START);

        // Adds a flow panel at center of screen
        final JPanel pane = new JPanel(new FlowLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Ensure the player object exists and has an active GIF loaded
                if (player != null && player.getActiveSprite() != null) {
                    ImageIcon sprite = player.getActiveSprite();

                    // FIXED: Changed 'this' to 'pane' to fix the drawImage type mismatch error
                    // NOTE: If player uses getxcoord() instead of getX(), change player.getX()
                    // below to player.getxcoord()
                    g.drawImage(sprite.getImage(), player.getX(), player.getY(), 40, 40, this);
                }
                if (dragon != null) {
                    dragon.draw(g);
                }

            }
        };
        pane.setPreferredSize(new Dimension(1280, 720));
        pane.setOpaque(false);
        try {
            JPanelWithBackground background = new JPanelWithBackground("DragonArenaScaled.png");
            background.setLayout(new BorderLayout());
            background.add(pane, BorderLayout.CENTER);
            frame.setContentPane(background);
        } catch (IOException e) {
            e.printStackTrace();
        }

        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.getContentPane().add(pane, BorderLayout.CENTER);

        // Inventory Panel
        JPanel inventory = new JPanel();
        inventory.setLayout(new BoxLayout(inventory, BoxLayout.Y_AXIS));
        inventory.setBackground(Color.WHITE);
        JLabel inventoryTitle = new JLabel("Inventory", SwingConstants.CENTER);
        inventoryTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        inventory.setPreferredSize(new Dimension(200, 150));
        inventory.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        inventory.add(inventoryTitle);
        pane.add(inventory);

        // Inventory visibility toggle
        inventory.setVisible(false);

        // craft panel
        JPanel craft = new JPanel(new BorderLayout());
        JLabel status = new JLabel("");
        craft.setName("craft");
        JLabel craftTitle = new JLabel("Crafting", SwingConstants.CENTER);
        craft.add(craftTitle, BorderLayout.NORTH);
        craft.setPreferredSize(new Dimension(250, 200));
        craft.add(status, BorderLayout.SOUTH);
        craft.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        craft.setVisible(false);
        pane.add(craft);
        // Show the frame
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setVisible(true);

        // KEY BINDINGS
        setupPlayerBindings(pane, player);
        inventoryKeyBinding(inventory, pane);
        craftButtonKey(craft, status, pane);
        attack(pane, player);

        Timer gameTimer = new Timer(100, e -> {
            dragon.updateAI(player);
            dragon.tickAnimation();
            pane.repaint();
        });

        gameTimer.start();

    }

    // inventory toggle upon pressing "c" key
    public static void inventoryKeyBinding(JPanel inventory, JPanel center) {
        Action displayInventory = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean visible = inventory.isVisible();
                Map<String, Integer> playerInventory = player.getInventory();
                inventory.removeAll();
                JLabel inventoryTitle = new JLabel("Inventory", SwingConstants.CENTER);
                inventoryTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
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

    public static void setupPlayerBindings(JPanel pane, Player player) {

        InputMap input = pane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actions = pane.getActionMap();

        input.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "leftPressed");
        input.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "leftReleased");

        input.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "rightPressed");
        input.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "rightReleased");

        actions.put("leftPressed", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                player.setMovingLeft(true);
                player.setDirection("left");
            }
        });

        actions.put("leftReleased", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                player.setMovingLeft(false);
            }
        });

        actions.put("rightPressed", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                player.setMovingRight(true);
                player.setDirection("right");
            }
        });

        actions.put("rightReleased", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                player.setMovingRight(false);
            }
        });

        Timer timer = new Timer(16, e -> {
            player.update(pane.getWidth());
            pane.repaint();
        });

        timer.start();
    }

    public static void attack(JPanel pane, Player player) {
        Action attack = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dragon.startDamage(player);
            }
        };
        pane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"), "attack");
        pane.getActionMap().put("attack", attack);
    }

    public static void craftButtonKey(JPanel craft, JLabel status, JPanel pane) {
        Action openCraftMenu = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (craft.isVisible()) {
                    craft.setVisible(false);
                    return;
                }
                craft.removeAll();
                JLabel craftTitle = new JLabel("Crafting", SwingConstants.CENTER);
                craft.add(craftTitle, BorderLayout.NORTH);

                craft.add(status, BorderLayout.SOUTH);
                JPanel weaponGrid = new JPanel(new GridLayout(0, 1));
                for (Weapon w : Weapon.weaponList) {
                    JButton weapon = new JButton(w.getName());
                    weaponCraft(weapon, craft, status);
                    weapon.setMaximumSize(new Dimension(50, 30));
                    weaponGrid.add(weapon);
                    weaponGrid.add(Box.createRigidArea(new Dimension(0, 5)));
                }
                craft.add(weaponGrid, BorderLayout.CENTER);
                craft.setVisible(true);
                craft.revalidate();
                craft.repaint();
            }
        };
        pane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_C, 0), "openCraft");

        pane.getActionMap()
                .put("openCraft", openCraftMenu);
    };

    public static void weaponCraft(JButton button, JPanel c, JLabel stat) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player.craft(button.getText())) {
                    stat.setText("Successfully crafted a " + button.getText());
                } else {
                    stat.setText("Craft Failed. You either lack the materials to craft a " + button.getText()
                            + " or already possess one.");
                }
                c.repaint();
                c.revalidate();
            }
        });
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Weapon.initWeapons();
                player = new Player(100, 500, 500);
                dragon = new Dragon("Boss", 100, 10, 1100, 600);
                createAndShowGUI();
            }
        }); 
    }
}
