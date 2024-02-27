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
    //int pipeX = 510;
    private int score = 0;
    private boolean passedPipes = false;
    private final int bottleWidth = 170;
    private final int bottleHeight = 282;
    private final int playerWidth = 99;
    private final int playerHeight = 99;
    private long lastPipeSpawnTime = System.currentTimeMillis();
    private long pipeSpawnInterval = 4000;
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
        initializePipes();
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
        if (System.currentTimeMillis() - lastPipeSpawnTime >= pipeSpawnInterval) {
            initializePipes();
            lastPipeSpawnTime = System.currentTimeMillis();
        }
        // Move the pipes to the left
        for (Pipes pipe : pipes) {
            pipe.setX(pipe.getX() - 3);

            // Check collision with pipes and passing pipes
            int pipeX = pipe.getX();
            int upperPipeY = pipe.getUpperPipeY();
            int lowerPipeY = pipe.getLowerPipeY();

            if (pipeX + bottleWidth <= playerX+170 && !pipe.isPassed()) {
                score++;
                pipe.setPassed(true);
                System.out.println(score);
            }
            if (playerX + playerWidth - 35 >= pipeX && playerX + 35 <= pipeX + bottleWidth) {
                if (playerY + 35 <= upperPipeY + bottleHeight || playerY + playerHeight - 35 >= lowerPipeY) {
                    resetGame();
                    System.out.println("You got "+score +" points! Wow..");
                }
            }


        }

        // Remove passed pipes
        pipes.removeIf(pipe -> pipe.getX() + bottleWidth < 0);

        // Reset passedPipes flag if the player moves back before the pipe
        for (Pipes pipe : pipes) {
            if (pipe.getX() >= playerX) {
                passedPipes = false;
                break; // Exit the loop early once passedPipes is set to false
            }
        }

        // Handle player movement
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
        }

        // Check if the player is on the ground
        if (playerY >= screenHeight - tileSize) {
            resetGame();
            System.out.println("You got "+score +" points! Wow..");
        }
    }

    private void initializePipes() {
        pipes.add(new Pipes(500, -10, 500, false));
    }

    private void resetGame() {
        gameThread.interrupt(); // Interrupt the current thread if it's still running

        // Create a new JFrame instance
        JFrame window = (JFrame) SwingUtilities.getWindowAncestor(this);
        window.getContentPane().removeAll(); // Remove all components from the window

        // Create a new GamePanel instance
        GamePanel newGamePanel = new GamePanel();

        // Add the newGamePanel to the window
        window.add(newGamePanel);

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

        for (Pipes pipe : pipes) {
            int pipeX = pipe.getX();
            int upperPipeY = pipe.getUpperPipeY();
            int lowerPipeY = pipe.getLowerPipeY();

            // Draw upper pipe
            g2.drawImage(BottlePanel.bottle2, pipeX, upperPipeY, this);

            // Draw lower pipe
            g2.drawImage(BottlePanel.bottle, pipeX, lowerPipeY, this);
        }
        this.requestFocusInWindow();
    }
}
 