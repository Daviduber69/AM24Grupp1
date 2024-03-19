import javax.swing.*;
import java.awt.*;

public class  Main {
    public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {      // skapar trådsäkerhet
                GameMenu menu = new GameMenu();     // skapar menyobjekt
                menu.setVisible(true);              // sätter fönster till synligt
            });
        } 
    }

