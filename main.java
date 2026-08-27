import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class main {
    public static void createAndShowGUI(){
        JFrame frame = new JFrame("Time Box");
        Player player = new Player(100,0,0);

        // Add title
        JLabel title = new JLabel("Time Box", SwingConstants.CENTER);
        Font heading = new Font("Times Roman", Font.BOLD, 20);
        title.setFont(heading);
        title.setPreferredSize(new Dimension(300,50));
        title.setOpaque(true);
        title.setBackground(Color.decode("#f0e9e9"));
        frame.getContentPane().add(title, BorderLayout.PAGE_START);

        // Add panel in center of frame
        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(Color.cyan);

    }
  
  }  
