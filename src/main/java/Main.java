import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
            JFrame window = new JFrame();
            GamePanel gamePanel = new GamePanel();
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setResizable(false);
            window.setTitle("Jumpy Birb");
            window.add(gamePanel);
            window.pack();
            window.setLocationRelativeTo(null);
            window.setVisible(true);
            window.requestFocus();
            gamePanel.startGameThread();
        }
    }
