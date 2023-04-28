import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import Swing_Control.ControlPanelPart.ControlPanel;

public class Launch_SWING {
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        Swing_Control.ControlPanelPart.ControlPanel.removeAllSerialFiles();
        Swing_Control.ControlPanelPart.ControlPanel.recreateAllSerialFiles();
        new ControlPanel();
    }
}
