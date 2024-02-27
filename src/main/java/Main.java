import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();                           // Creates a new window
        GamePanel gamePanel = new GamePanel();                  // Creates a new GamePanel
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Makes closing on demand available
        window.setResizable(false);                             // Sets this frame not resizable by the user.
        window.setTitle("Jumpy Birb");                          // Set title
        window.add(gamePanel);                                  // Makes everything that is modified in gamePanel class visible in the window
        window.pack();                                          // Layout estimation for the most preferable construction of the window
        window.setLocationRelativeTo(null);                     // Center the window
        window.setVisible(true);                                // Makes the window visable for the user
        window.requestFocus();                                  // Allowing the user to interact with the window
        GameMenu.menu();                                        // calling method gameMenu
        gamePanel.startGameThread();                         // starting gameThread
    }
}
