import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class SoundPlayer {
    private Clip clip;

    public SoundPlayer(String filePath, boolean loop) {
        try {
            File file = new File(filePath);
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedInputStream);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            if(loop){
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (clip != null) {
            clip.start();
        }
    }

    public void stop() {
        if (clip != null) {
            clip.stop();
        }
    }
}
