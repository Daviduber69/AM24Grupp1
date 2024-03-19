import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

    public class GameMenu extends JFrame {
    private GamePanel gamePanel;

    public GameMenu() {
        super("Game Menu");
        setSize(500, 1000);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        ImageIcon backgroundImage = new ImageIcon("office2.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        add(backgroundLabel, BorderLayout.CENTER);


        JButton easyButton = new JButton("Easy");
        add(easyButton, BorderLayout.WEST);
        easyButton.setBackground(Color.GREEN);
        easyButton.setFont(new Font("Arial", Font.BOLD, 48));
        easyButton.addActionListener(e -> startGame("easy"));

        JButton hardButton = new JButton("Hard");
        add(hardButton, BorderLayout.EAST);
        hardButton.setBackground(Color.RED);
        hardButton.setFont(new Font("Arial", Font.BOLD, 48));
        hardButton.addActionListener(e -> startGame("hard"));
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
