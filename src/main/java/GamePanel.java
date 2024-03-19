import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class GamePanel extends JPanel implements Runnable {
    // ------------------ FLAG-TESTING -------------------- //

    private boolean testingRestartFeature = false;

    // ------------------ FLAG-TESTING -------------------- //
    final int originalTileSize = 16; // 16x16
    final int scale = 3;
    final int tileSize = originalTileSize * scale; // 16x3 = 48px
    final int maxScreenColumn = 24;
    final int maxScreenRow = 24;
    final int screenWidth = tileSize * maxScreenRow; // 48*24px = 1152 px
    final int screenHeight = tileSize * maxScreenColumn; // 48*24 px = 1152 px
    Image backGroundImage;
    private JLabel highscore;
    private JLabel playerScore;
    private SoundPlayer deathSound;
    private SoundPlayer musicLoop;
    ImagePanel images;
    Thread gameThread;
    KeyHandler keyHandler = new KeyHandler();
    // FPS, the game is to be run in 60 Frames per second.
    int fps = 60;
    // players default starting position
    int playerX = 130;
    int playerY = 300;
    double playerSpeedY = 0.0;
    private int score = 0;
    private int pipeWidth = 119;
    private int pipeHeight = 805;
    private int playerWidth = 99;
    private int playerHeight = 99;
    private long lastPipeSpawnTime = System.currentTimeMillis();
    private final List<Pipes> pipes = new ArrayList<>();
    Highscore highscoreList = new Highscore();

    private String difficulty = "";

    /**
     * Constructor to set dimensions of window,
     * background color and also sets whether this
     * component should use a buffer to paint.
     */
    public GamePanel(String difficulty) {
        setDifficulty(difficulty);
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        this.requestFocusInWindow();
        images = new ImagePanel();
        deathSound = new SoundPlayer("death.wav", false);
        musicLoop = new SoundPlayer("questsong-.wav", true);
        playerScore = new JLabel("");
        highscore = new JLabel("");
        playerScore.setOpaque(false);
        playerScore.setForeground(Color.green);
        playerScore.setBackground(Color.black);
        playerScore.setFont(new Font("Arial", Font.BOLD, 48));
        highscore.setForeground(Color.pink);
        highscore.setFont(new Font("Arial", Font.BOLD, 24));
        highscore.setBounds(400, 10, 100, 50);
        this.add(playerScore);
        this.add(highscore);
        backGroundImage = new ImageIcon("_88d2b0b7-b505-4a37-a995-69152b5e5b97.jpg").getImage();
    }
    /*
    In this method you regulate the appropriate variables
    to increase or decrease difficulty of the given GamePanel object.
     */

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    private void initializePipesHard() {
        Random random = new Random();
        int lowerY = 1025;
        int upperY = random.nextInt(400) - 800 + 50;
        lowerY = upperY + lowerY;
        pipes.add(new Pipes(screenWidth, upperY, lowerY, false));
    }

    private void initializePipesEasy() {
        Random random = new Random();
        int lowerY = 900;
        int upperY = random.nextInt(200) - 800 + 50;
        lowerY = upperY + lowerY + 200;
        pipes.add(new Pipes(screenWidth, upperY, lowerY, false));
    }

    public void initializePipes() {
        if (difficulty.equalsIgnoreCase("hard")) {
            initializePipesHard();
        } else if (difficulty.equalsIgnoreCase("easy")) {
            initializePipesEasy();
        }
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
     * The repaint()-method checks if this component is a lightweight component,
     * then this method causes a call to this component's paint method as soon as
     * possible. Otherwise,
     * this method causes a call to this component's update method as soon as
     * possible.
     * Lightweight components are those that are entirely written in Java
     * and are drawn using Java's graphics system
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

    //add pipes pipes with startng coordinate values, for easy mode they spawn at the same coordinates
    //in update() spawn new pipes every 4 seconds


    // checks if spacebar is pressed and released
    // then calls the resetSpacebarReleased method which is set to false as default
    // Bird doesn't move further down when at the bottom of the screen
    long startTime = System.currentTimeMillis();

    public void update() {
        long pipeSpawnInterval;
        if (difficulty.equalsIgnoreCase("hard")) {
            pipeSpawnInterval = 2300;
            highscoreList.saveHardHighscore();
            highscore.setText(String.valueOf(highscoreList.printHardHighscore()));
            playerScore.setText(String.valueOf(score));
        } else {
            highscoreList.saveEasyHighscore();
            highscore.setText(String.valueOf(highscoreList.printEasyHighscore()));
            playerScore.setText(String.valueOf(score));
            pipeSpawnInterval = 2800;
        }
        if (System.currentTimeMillis() - lastPipeSpawnTime >= pipeSpawnInterval) {
            initializePipes();
            lastPipeSpawnTime = System.currentTimeMillis();
        }

        // Move the pipes to the left
        for (Pipes pipe : pipes) {
            if (difficulty.equalsIgnoreCase("hard")) {
                pipe.setX((pipe.getX() - 6));
            } else {
                pipe.setX((pipe.getX() - 5));
            }
            int pipeX = pipe.getX();
            int upperPipeY = pipe.getUpperPipeY();
            int lowerPipeY = pipe.getLowerPipeY();
            //make the player and pipes Rectangles from Swing to use the intersects method
            // to see if they collide
            Rectangle playerRect = new Rectangle(playerX + 35, playerY + 35, playerWidth - 82, playerHeight - 50);
            Rectangle upperPipeRect = new Rectangle(pipeX, upperPipeY, pipeWidth + 10, pipeHeight + 20);
            Rectangle lowerPipeRect = new Rectangle(pipeX, lowerPipeY, pipeWidth + 10, pipeHeight + 20);
            if (pipeX <= playerX && !pipe.isPassed()) {
                score++;
                pipe.setPassed(true);
                playerScore.setText("" + score);
                highscoreList.addScore(score);
            }
            if (playerRect.intersects(upperPipeRect) || playerRect.intersects(lowerPipeRect)) {
                deathSound.play();
                resetGame();
            }
            if (pipe.getX() >= playerX) {
                pipe.setPassed(false);
            }
        }
        // Handle player movement
        if (keyHandler.isSpacebarPress()) {
            // Jumping acceleration
            playerSpeedY = -10; // You can adjust this value for smoother jumping
            playerY += (int) playerSpeedY;
            keyHandler.resetSpacebarReleased();

            // Check if the player is above the top of the window
            if (playerY < 0) {
                playerY = 0;
            }
        }
            // Gravity starts after 1 second
        if (System.currentTimeMillis() >= startTime + 1000) {
            playerSpeedY += 0.5;
            playerY += (int) playerSpeedY;
        }


        // Check if the player is on the ground
        if (playerY >= screenHeight - tileSize) {
            deathSound.play();
            resetGame();
        }
    }

    private void resetGame() {

        musicLoop.stop();

        gameThread.interrupt();// Interrupt the current thread if it's still running
        // Create a new JFrame instance
        JFrame window = (JFrame) SwingUtilities.getWindowAncestor(this);
        window.getContentPane().removeAll(); // Remove all components from the window

        // Create a new GamePanel instance
        GamePanel newGamePanel = new GamePanel(difficulty);

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
        g2.drawImage(ImagePanel.playerImage, playerX, playerY, this);

        for (Pipes pipe : pipes) {
            int pipeX = pipe.getX();
            int upperPipeY = pipe.getUpperPipeY();
            int lowerPipeY = pipe.getLowerPipeY();

            // Draw upper pipe
            g2.drawImage(ImagePanel.upperPipe, pipeX, upperPipeY, this);

            // Draw lower pipe
            g2.drawImage(ImagePanel.lowerPipe, pipeX, lowerPipeY, this);
        }
        this.requestFocusInWindow();
    }
}
 