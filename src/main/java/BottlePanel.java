import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class BottlePanel extends JPanel {
    public static Image bottle;
    public static Image bottle2;


    public BottlePanel(){
        try{
            URL bottleURL = new URL("https://github.com/Daviduber69/AM24Grupp1/blob/main/Colaflaska.png?raw=true");
            URL bottle2URL = new URL("https://github.com/Daviduber69/AM24Grupp1/blob/main/Colaflaska_uppochner.png?raw=true");
            bottle = ImageIO.read(bottleURL);
            bottle2 = ImageIO.read(bottle2URL);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
  
}