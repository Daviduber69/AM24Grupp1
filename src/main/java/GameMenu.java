import javax.swing.*;
import java.awt.*;

public class GameMenu extends JFrame {

    public GameMenu() {
        super("Game Menu");
        setSize(1000, 1000);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        ImageIcon backgroundImage = new ImageIcon("Media/devilRamTitel.png");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        add(backgroundLabel, BorderLayout.CENTER);
        
        
        JButton easyButton = new JButton("Easy");
        easyButton.setPreferredSize(new Dimension(150,100));
        easyButton.setBackground(Color.GREEN);
        easyButton.setFont(new Font("Arial", Font.BOLD, 48));
        easyButton.addActionListener(e -> startGame("easy"));
        add(easyButton, BorderLayout.WEST);
        
        JButton hardButton = new JButton("Hard");
        hardButton.setPreferredSize(new Dimension(150,100));
        hardButton.setBackground(Color.RED);
        hardButton.setFont(new Font("Arial", Font.BOLD, 48));
        hardButton.addActionListener(e -> startGame("hard"));
        add(hardButton, BorderLayout.EAST);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(easyButton);
        buttonPanel.add(hardButton);
        add(buttonPanel, BorderLayout.SOUTH);
     
        setVisible(true);
    }

    public void startGame(String difficulty) {
        dispose();                                              // closes previous JFrame-object
        JFrame window = new JFrame();                           // Creates a new window
        GamePanel gamePanel = new GamePanel(difficulty);        // Creates a new GamePanel
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Makes closing on demand available
        window.setResizable(false);                             // Sets this frame not resizable by the user.
        window.setTitle("Jumpy Birb");                          // Set title
        window.add(gamePanel);                                  // Makes everything that is modified in gamePanel class visible in the window
        window.pack();                                          // Layout estimation for the most preferable construction of the window
        window.setLocationRelativeTo(null);                     // Center the window
        window.setVisible(true);                                // Makes the window visable for the user
        window.requestFocus();                                  // Allowing the user to interact with the window
        gamePanel.startGameThread();                             // starting gameThread
    }

    
}
