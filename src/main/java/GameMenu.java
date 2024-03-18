    /*
     * Jag vill skapa en klass som instansieras genom main-metoden.
     * Denna klass kommer bli spelets meny.
     * Här är det tänkt att man ska kunna välja svårighetsgrad, starta spelet samt se highscore.
     * 
     * Steg 1:
     * Skapa ett objekt av gameMenu som ska kallas på genom main-klassen (eventuellt göra gameMenu till static,
     * vad är skillnaden (?)).
     * 
     * Steg 2:
     * Skapa funktion för att starta spelet.
     * Denna funktion ska reagerar på input från spaceknappen (ev. vänster musklick)
     * 
     * 
     */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

    public class GameMenu extends JFrame {
    private GamePanel gamePanel;

    public GameMenu() {
        super("Game Menu");
        setSize(60, 200);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new FlowLayout()); //Using flowlayout to automaticly set buttons relativ to window and each other

        JLabel label = new JLabel("Choose Difficulty");

        JButton easyButton = new JButton("Easy");
        easyButton.setBounds(10, 10, 10, 10);
        easyButton.addActionListener(e -> startGame("easy"));
        add(easyButton);
        easyButton.setVisible(true);

        JButton normalButton = new JButton("Hard");
        normalButton.setBounds(10, 10, 10, 10);
        normalButton.addActionListener(e -> startGame("hard"));
        add(normalButton);
        normalButton.setVisible(true);

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
      //  gamePanel.setDifficulty(difficulty);
        gamePanel.startGameThread();                             // starting gameThread
    }
}
