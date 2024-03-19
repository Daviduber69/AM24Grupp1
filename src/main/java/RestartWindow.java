import javax.swing.*;
import java.awt.*;

public class RestartWindow extends JFrame {
    private String difficulty;
    public RestartWindow(String difficulty) {
        super("Restart");
        setDifficulty(difficulty);
        setLocationRelativeTo(null);
        setResizable(false);
        setSize(100,100);
        setLayout(new FlowLayout());
        setFocusable(false);

        JButton restartButton = new JButton();
        setBounds(10,10,10,10);
        setVisible(true);
        restartButton.addActionListener(e -> restartGame(difficulty));
        add(restartButton);
    }
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
    public void restartGame(String difficulty) {
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
        gamePanel.startGameThread();
    }
}
