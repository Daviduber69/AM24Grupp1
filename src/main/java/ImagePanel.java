import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ImagePanel extends JPanel {
    public static Image playerImage;
    public static Image upperPipe;
    public static Image lowerPipe;
    public ImagePanel() {
        try {
            playerImage = Toolkit.getDefaultToolkit().createImage("hamram_border.gif");
            upperPipe = ImageIO.read(new File("trippelburkvandkant.png"));
            lowerPipe = ImageIO.read(new File("trippelburk.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
 