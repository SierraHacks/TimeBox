import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class main {
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

        // Add panel in center of frame (using JLayeredPane or a Panel with null layout if you want free positioning inside)
        // For simplicity, let's use a standard JPanel with null layout *only* for the center container if you want absolute positioning inside it.
        JPanel center = new JPanel(null);
        center.setBackground(Color.white);
        frame.add(center, BorderLayout.CENTER);

        // Inventory Panel
        JPanel inventory = new JPanel();
        inventory.setBackground(Color.WHITE);
        JLabel inventoryTitle = new JLabel("Inventory");
        inventory.setBounds(50, 70, 200, 100); // Absolute bounds work safely inside a container that uses null layout
        inventory.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        
        inventory.add(inventoryTitle);
        center.add(inventory); // Add inventory to the center panel, not the frame directly

        // Inventory visibility toggle
        inventory.setVisible(false);
        
        // Show the frame
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setVisible(true);

        // KEY BINDINGS
        inventoryKeyBinding(inventory, center);
    }

    public static void main(String[] args) { 
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    public static void inventoryKeyBinding(JPanel inventory, JPanel center) {
        Action displayInventory = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean visible = inventory.isVisible();
                inventory.setVisible(!visible);
                inventory.repaint();
                //update to accurately reflect player inventory
            }
        };
        
        center.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_C,0), "toggleInventory");
        center.getActionMap().put("toggleInventory", displayInventory);
    }


}