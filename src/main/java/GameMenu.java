    /*
     * Jag vill skapa en klass som instansieras genom main-metoden.
     * Denna klass kommer bli spelets meny.
     * Här är det tänkt att man ska kunna välja svårighetsgrad, starta spelet samt se highscore.
     * 
     * Steg 1:
     * Skapa ett objekt av gameMenu som ska kallas på genom main-klassen (eventuellt göra gameMenu till static,
     * vad är skillnaden (?)).
     * 
     * Steg 2:
     * Skapa funktion för att starta spelet.
     * Denna funktion ska reagerar på input från spaceknappen (ev. vänster musklick)
     * 
     * 
     */

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameMenu extends GamePanel implements KeyListener {
    
    /*
     * Skapar en metod startGame som ska ärva av GamePanel.
     * I denna klass har vi en metod startMenuThread() som ska lyssna
     * efter ifall använ
     */
    public static void menu() {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
