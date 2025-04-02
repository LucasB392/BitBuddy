import java.io.File;
import javax.sound.sampled.*;

public class SoundPlayer {
    private Clip clip;

    public void playLoop(String filename) {
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(filename)));
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if (clip != null) clip.stop();
    }
}
