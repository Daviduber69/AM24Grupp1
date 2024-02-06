import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ImagePanel extends JPanel {
    public static Image image;
    public ImagePanel() {
        try {
            URL imageURL = new URL("https://github.com/Daviduber69/AM24Grupp1/blob/main/hamen.png?raw=true");
            image = ImageIO.read(imageURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
