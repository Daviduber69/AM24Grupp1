import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ImagePanel extends JPanel {
    public static Image image;


    public ImagePanel(){
        try{
            image = ImageIO.read(new File("C:\\Users\\risif\\Desktop\\hamen.png"));
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
