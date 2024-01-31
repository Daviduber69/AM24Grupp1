import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    final int originalTileSize = 16; //16x16
    final int scale = 3;
    final int tileSize = originalTileSize * scale; //16x3 = 48px
    final int maxScreenColumn = 12;
    final int maxScreenRow = 16;
    final int screenWidth = tileSize * maxScreenColumn; //48*12px = 576 px
    final int screenHeight = tileSize * maxScreenRow; //48*16 px = 768 px

    Thread gameThread;


    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.blue);
        this.setDoubleBuffered(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (gameThread != null) {
            update();
            repaint();
        }
    }

    public void update() {


    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        Graphics2D pipe1 = (Graphics2D) g;
        Graphics2D pipe2 = (Graphics2D) g;
        g2.setColor(Color.white);
        g2.fillRect(100, 100, tileSize, tileSize);
        pipe1.setColor(Color.green);
        pipe1.fillRect(510, 40, 50, 300);
        pipe2.setColor(Color.green);
        pipe2.fillRect(510, 500, 50, 300);
        g2.dispose();
    }

}
