import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class BottlePanel extends JPanel {
    public static Image image;


    public BottlePanel(){
        try{
            image = ImageIO.read(new File("coca-cola-original-20oz.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public Dimension preferedSize(){
        if(image!=null){
            return new Dimension(image.getWidth(this),image.getHeight(this));
        }
        else {
            return super.getPreferredSize();
        }
    }
}