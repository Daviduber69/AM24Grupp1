import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    private boolean spacebarPress;
    private boolean spacebarRelease;
    //registers a spacebar press as only one button press
    public boolean isSpacebarPress() {
        return spacebarPress && spacebarRelease;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_SPACE) {
            if (!spacebarPress) {
                spacebarPress = true;
                //startGame(); // Anropa startGame() när spacebar trycks ned
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_SPACE){
            spacebarPress = false;
            spacebarRelease = true;
        }
    }

    public void resetSpacebarReleased(){
        spacebarRelease = false;
    }
}
