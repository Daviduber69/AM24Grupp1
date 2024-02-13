import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel implements Runnable {
    final int originalTileSize = 16; // 16x16
    final int scale = 3;
    final int tileSize = originalTileSize * scale; // 16x3 = 48px
    final int maxScreenColumn = 12;
    final int maxScreenRow = 16;
    final int screenWidth = tileSize * maxScreenColumn; // 48*12px = 576 px
    final int screenHeight = tileSize * maxScreenRow; // 48*16 px = 768 px
    Image backGroundImage;

    ImagePanel imagePanel;
    BottlePanel bottlePanel;
    Thread gameThread;
    KeyHandler keyHandler = new KeyHandler();
    // FPS, the game is to be run in 60 Frames per second.
    int fps = 60;
    // players default starting position
    int playerX = 100;
    int playerY = 300;
    double playerSpeedY = 0.0;
    int pipeX = 510;
    private List<Pipes> pipes = new ArrayList<>();

    /**
     * Constructor to set dimensions of window,
     * background color and also sets whether this
     * component should use a buffer to paint.
     */
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.white);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.imagePanel = new ImagePanel();
        this.bottlePanel = new BottlePanel();
        backGroundImage = new ImageIcon("office.jpg").getImage();
    }

    /**
     * Allocates a new Thread object
     * and causes this thread to begin execution
     */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Starting the thread causes this method to be called.
     * While gameThread not equals null:
     * call the method update() and the method repaint()
     * <p>
     * The repaint()-method checks if this component is a lightweight component,
     * then this method causes a call to this component's paint method as soon as
     * possible. Otherwise,
     * this method causes a call to this component's update method as soon as
     * possible.
     * <p>
     * Lightweight components are those that are entirely written in Java
     * and are drawn using Java's graphics system
     * <p>
     * // * @see Component.repaint();
     */
    // Limits FPS to 60 so that the JumpyBirby doenst travel 1 million pixels in a
    // second
    @Override
    public void run() {
        double drawInterval = 1000000000.0 / fps; // 0.016666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;
        while (gameThread != null) {
            update();
            repaint();
            try {
                double remainingtime = nextDrawTime - System.nanoTime();
                remainingtime /= 1000000;
                if (remainingtime < 0) {
                    remainingtime = 0;
                }
                Thread.sleep((long) remainingtime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                System.err.println("YOU DIED!!!");
                break;
            }
        }
    }

    // checks if spacebar is pressed and released
    // then calls the resetSpacebarReleased method which is set to false as default
    // Bird doesn't move further down when at the bottom of the screen
    public void update() {
        pipeX -= 3;
        if (keyHandler.isSpacebarPress()) {
            // Jumping: Apply acceleration upwards
            playerSpeedY = -10; // You can adjust this value for smoother jumping
            playerY += (int) playerSpeedY;
            keyHandler.resetSpacebarReleased();

            // Check if the player is above the top of the window
            if (playerY < 0) {
                playerY = 0;
            }
        } else {
            // Falling or on the ground: Apply gravity
            playerSpeedY += 0.5; // Gravity effect, you can adjust this value for more or less gravity
            playerY += (int) playerSpeedY;

            // Check if the player is on the ground
            if (playerY >= screenHeight - tileSize) {
                resetGame();

            }
        }

    }



    private void resetGame() {
        gameThread.interrupt(); // Interrupt the current thread if it's still running

        // Create a new GamePanel instance
        GamePanel newGamePanel = new GamePanel();

        // Set the new instance as the content pane for the JFrame
        JFrame window = (JFrame) SwingUtilities.getWindowAncestor(this);
        window.setContentPane(newGamePanel);

        // Revalidate and repaint the window
        window.revalidate();
        window.repaint();

        // Start the new game thread
        newGamePanel.startGameThread();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g.drawImage(backGroundImage, 0, 0, getWidth(), getHeight(), this);
        g2.drawImage(ImagePanel.image, playerX, playerY, this);

        List<Graphics2D> listOfPipes = new ArrayList<>();
        listOfPipes.add(g2); // Add the original graphics context to the list

        // Draw the initial pair of bottles
        g2.drawImage(BottlePanel.bottle2, pipeX, -10, this);
        g2.drawImage(BottlePanel.bottle, pipeX, 500, this);

        // Add additional pairs of bottles if pipeX < playerX
        if (pipeX < playerX) {
            for (int i = 1; pipeX + 300 * i < playerX; i++) {
                Graphics2D newPipe = (Graphics2D) g.create(); // Create a new graphics context
                newPipe.translate(300 * i, 0); // Translate the graphics context
                listOfPipes.add(newPipe); // Add the new graphics context to the list
                newPipe.drawImage(BottlePanel.bottle2, pipeX + 300 * i, -10, this);
                newPipe.drawImage(BottlePanel.bottle, pipeX + 300 * i, 500, this);
            }
        }

        this.requestFocusInWindow();
    }
}
 