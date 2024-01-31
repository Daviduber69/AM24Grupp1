import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean spacebarPress;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        System.out.println("Key pressed: " + KeyEvent.getKeyText(code));
        if (code == KeyEvent.VK_SPACE) {
            spacebarPress = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        System.out.println("Key released: " + KeyEvent.getKeyText(code));
        if(code == KeyEvent.VK_SPACE){
            spacebarPress = false;
        }
    }
}
