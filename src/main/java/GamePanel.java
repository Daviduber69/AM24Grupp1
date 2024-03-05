import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// nya imports för startknapp----
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable {
    final int originalTileSize = 16; // 16x16
    final int scale = 3;
    final int tileSize = originalTileSize * scale; // 16x3 = 48px
    final int maxScreenColumn = 12;
    final int maxScreenRow = 16;
    final int screenWidth = tileSize * maxScreenColumn; // 48*12px = 576 px
    final int screenHeight = tileSize * maxScreenRow; // 48*16 px = 768 px
    Image backGroundImage;
    private JLabel highscore;
    private JLabel playerScore;
    private SoundPlayer deathSound;
    private SoundPlayer musicLoop;

    private JButton startButton; // startknapp test----
    private boolean gameRunning = false; // Deklarera och initialisera gameRunning här -- startknapp test
    private boolean gamePaused = true;
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
    private int bottleWidth = 170;
    private int bottleHeight = 282;
    private int playerWidth = 99;
    private int playerHeight = 99;
    private long lastPipeSpawnTime = System.currentTimeMillis();
    private long pipeSpawnInterval = 4000;
    private List<Pipes> pipes = new ArrayList<>();
    Highscore highscoreList = new Highscore();

    /**
     * Constructor to set dimensions of window,
     * background color and also sets whether this
     * component should use a buffer to paint.
     */
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        this.requestFocusInWindow();
        images = new ImagePanel();
        initializePipes();
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
        highscore.setBounds(10, 10, 10, 10);
        this.add(playerScore);
        this.add(highscore);
        backGroundImage = new ImageIcon("office.jpg").getImage();
        initializeStartButton(); // Call the method to initialize the start button -- test för startknapp
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

    //add pipes pipes with starting coordinate values, for easy mode they spawn at the same coordinates
    //in update() spawn new pipes every 4 seconds
    private void initializePipes() {
        Random random = new Random();
        int lowerY = 500;
        int upperY = random.nextInt((150 + 80) + 1) - 80;
        lowerY = lowerY + upperY;
        pipes.add(new Pipes(500, upperY, lowerY, false));

    }


    // checks if spacebar is pressed and released
    // then calls the resetSpacebarReleased method which is set to false as default
    // Bird doesn't move further down when at the bottom of the screen

    public void update() {
        if (System.currentTimeMillis() - lastPipeSpawnTime >= pipeSpawnInterval) {
            initializePipes();
            lastPipeSpawnTime = System.currentTimeMillis();
        }
        highscoreList.saveHighscore();
        highscore.setText(String.valueOf(highscoreList.printHighscore()));
        playerScore.setText(String.valueOf(score));
        // Move the pipes to the left
        for (Pipes pipe : pipes) {
            pipe.setX(pipe.getX() - 3);
            int pipeX = pipe.getX();
            int upperPipeY = pipe.getUpperPipeY();
            int lowerPipeY = pipe.getLowerPipeY();
            //make the player and pipes Rectangles from Swing to use the intersects method
            // to see if they collide
            Rectangle playerRect = new Rectangle(playerX + 35, playerY + 35, playerWidth - 82, playerHeight - 50);
            Rectangle upperPipeRect = new Rectangle(pipeX, upperPipeY, bottleWidth + 10, bottleHeight + 15);
            Rectangle lowerPipeRect = new Rectangle(pipeX, lowerPipeY, bottleWidth + 10, bottleHeight + 15);
            if (pipeX + bottleWidth <= playerX + 170 && !pipe.isPassed()) {
                score++;
                pipe.setPassed(true);
                playerScore.setText("" + score);
                highscoreList.addScore(score);
            }
            if (playerRect.intersects(upperPipeRect) || playerRect.intersects(lowerPipeRect)) {
                deathSound.play();
                resetGame();
            }
        }
        // Reset passedPipes flag if the player moves back before the pipe
        for (Pipes pipe : pipes) {
            if (pipe.getX() >= playerX) {
                pipe.setPassed(false);
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
            deathSound.play();
            resetGame();
        }
        //test startknapp ---
        if (gameRunning && playerY >= screenHeight - tileSize) {
            gameRunning = false; // Avbryt spelet
        }
        if (!gameRunning || gamePaused) { //---test startknapp
            return; // Om spelet inte körs eller är pausat, avbryt uppdateringen
        }
    }

    //Test för startknapp
    public void initializeStartButton() {
        startButton = new JButton("Start"); // Create the start button
        startButton.setFont(new Font("Arial", Font.BOLD, 20));
        startButton.setFocusPainted(false);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(); // Call the method to start the game when the button is clicked
            }
        });
        this.add(startButton); // Add the start button to the panel
    }

    // also tillagd för  test av startknapp
    public void startGame() {
        // Kontrollera om spelet redan körs för att undvika flera startknappar
        if (!gameRunning) {
            startButton.setEnabled(false); // Inaktivera startknappen när spelet startar
            gameRunning = true; // Markera att spelet körs
            gamePaused = false; // Markera att spelet inte är pausat längre
            startGameThread(); // Starta spelet
        }
    }


    private void resetGame() {
        musicLoop.stop();
        gameThread.interrupt();// Interrupt the current thread if it's still running

        initializeStartButton();
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


        //--test startknapp....-----

        gameRunning = false;
        gamePaused = true; //--test startknapp-----
        startButton.setEnabled(true); // Aktivera startknappen igen
        this.remove(startButton); // Ta bort den befintliga startknappen
        startButton = null; // Nollställ startknappen

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
 