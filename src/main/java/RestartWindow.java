import javax.swing.*;
import java.awt.*;

public class RestartWindow extends JFrame {
    private String difficulty;
    public RestartWindow() {
        super("Restart");
        setLayout(new FlowLayout());
        setSize(10,10);
        setFocusable(false);

        JButton restartButton = new JButton();
    //    restartButton.addActionListener(e -> restartGame(difficulty));
    }
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}
