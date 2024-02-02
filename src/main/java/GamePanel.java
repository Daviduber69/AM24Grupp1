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
    KeyHandler keyHandler = new KeyHandler();
    //FPS, spelet körs i 60 FPS
    int fps = 60;
    //players default starting position
    int playerX = 100;
    int playerY = 300;
    int playerSpeed = 100;
    double playerSpeedY = 0.0;
    int pipeX = 510;

    /**
     * Constructor to set dimensions of window,
     * background color and also sets whether this
     * component should use a buffer to paint.
     */
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.blue);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        this.requestFocusInWindow();
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
     * then this method causes a call to this component's paint method as soon as possible. Otherwise,
     * this method causes a call to this component's update method as soon as possible.
     * <p>
     * Lightweight components are those that are entirely written in Java
     * and are drawn using Java's graphics system
     * <p>
     * // * @see Component.repaint();
     */
    //Limits FPS to 60 so that the JumpyBirby doenst travel 1 million pixels in a second
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
                System.err.println("YOU DIED!!!" );
                break;
            }
        }
    }

    //checks if spacebar is pressed and released
    //then calls the resetSpacebarReleased method which is set to false as default
    //Bird doesn't move further down when at the bottom of the screen
    public void update() {
        pipeX--;

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
        Graphics2D pipe1 = (Graphics2D) g;
        Graphics2D pipe2 = (Graphics2D) g;
        g2.setColor(Color.white);
        g2.fillRect(playerX, playerY, tileSize, tileSize);
        pipe1.setColor(Color.green);
        pipe1.fillRect(pipeX, 40, 50, 300);
        pipe2.setColor(Color.green);
        pipe2.fillRect(pipeX, 500, 50, 300);
        this.requestFocusInWindow();
        g2.dispose();
    }

}
