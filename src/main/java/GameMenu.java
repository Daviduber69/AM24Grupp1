import javax.swing.*;
import java.awt.*;

public class GameMenu extends JFrame {
    public GameMenu() {
        super("Game Menu");
        setSize(1000, 1000);
        setLocationRelativeTo(null);
        setResizable(false);

        // Set the background image as the content pane
        setContentPane(new JLabel(new ImageIcon("Media/devilRamTitel.png")));

        // Set layout to null to freely position components
        setLayout(null);
        JButton easyButton = new JButton("Easy");
        easyButton.setBounds(300, 800, 200, 100);
        easyButton.setBackground(Color.white);
        easyButton.setForeground(Color.RED);
        easyButton.setFont(new Font("Trattatello", Font.BOLD, 24));
        easyButton.addActionListener(e -> startGame("easy"));
        add(easyButton);

        JButton hardButton = new JButton("Hard");
        hardButton.setBounds(550, 800, 200, 100);
        hardButton.setBackground(Color.white);
        hardButton.setForeground(Color.RED);
        hardButton.setFont(new Font("Trattatello", Font.BOLD, 24));
        hardButton.addActionListener(e -> startGame("hard"));
        add(hardButton);
        revalidate();
        repaint();

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