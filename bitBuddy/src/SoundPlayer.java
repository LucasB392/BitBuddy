import java.io.File;
import javax.sound.sampled.*;

/**
 * Class that handles playing the sound
 * <br><br>
 * The class accepts a filename and plays that sound file.<br><br>
 * 
 * @version 1.0
 * @author Lucas
 */

//Class the handles the playing of audio (music, sound effects)
public class SoundPlayer {
	/** private variable that represents the clip of the file */
    private Clip clip;

    /**
     * Play audio file.
     * @param filename stores the .wav file
     * @throw catches an exception if the file cannot be played
     */
    public void playLoop(String filename) {
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(filename)));
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Stop playing audio file.
     */
    public void stop() {
        if (clip != null) clip.stop();
    }
}

