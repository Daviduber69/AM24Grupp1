import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

    public class GameMenu extends JFrame {
        private GamePanel gamePanel;
    public GameMenu() {
        super("GameMenu");
        setDefaultCloseOperation(GameMenu.EXIT_ON_CLOSE);
        setSize(500, 250);
        setResizable(false);
        setLocationRelativeTo(null);


        JLabel instructionsLabel = new JLabel("Press Space To Start");
        instructionsLabel.setBounds(50, 20, 200, 30);
        add(instructionsLabel);

        JButton startButton = new JButton("Start Game");
        startButton.setBounds(50, 60, 200, 30);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
        add(startButton);
        setVisible(true);
    }
        private void startGame() {
            dispose();                                              // Close the menu window
            JFrame gameFrame = new JFrame("Jumpy Birb");
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.setResizable(false);
            gameFrame.setSize(800, 600);
            gamePanel = new GamePanel();
            gameFrame.add(gamePanel);
            gameFrame.setLocationRelativeTo(null);
            gameFrame.setVisible(true);                              // Allowing the user to interact with the window
            gamePanel.startGameThread();                            // starting gameThread
        }
}