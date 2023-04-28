package JavaFX_Board.CardObject;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

public class ClickableCard extends NoClickableCard{

    ArrayList<ClickableZone> cercles = new ArrayList<>();
    private Pane p;

    public ArrayList<ClickableZone> getCercles() {
        return this.cercles;
    }

    public ClickableCard(int numeroCarte, Pane p){
        super(numeroCarte);
        this.p = p;
        setOnMouseClicked(mouse_event -> {
            try {
                handleMouseClicked(mouse_event);
                setOnKeyPressed(e -> {
                    if(e.getCode().equals(KeyCode.ALT)){
                        p.getChildren().remove(this);
                    }
                });
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });



        if(numeroCarte == 0){
            cercles.add(new ClickableZone(7,184, 165));
            cercles.add(new ClickableZone(51,258, 119));
            cercles.add(new ClickableZone(29,263, 229));
        }
        if(numeroCarte == 12){
            cercles.add(new ClickableZone(21,169, 337));
        }
        if(numeroCarte == 76){
            cercles.add(new ClickableZone(31, 135.0,355.0));
        }
        if(numeroCarte == 58){
            cercles.add(new ClickableZone(39,91, 142));
            cercles.add(new ClickableZone(11,175, 134));
            cercles.add(new ClickableZone(22,257, 106));
        }
        if(numeroCarte == 70){
            cercles.add(new ClickableZone(73,130, 296));
            cercles.add(new ClickableZone(68,109, 119));
        }
        if(numeroCarte == 79){
            cercles.add(new ClickableZone(2,178.0, 292.0));
        }
        if(numeroCarte == 29){
            cercles.add(new ClickableZone(37,78.0, 176.0));
        }

        if(numeroCarte == 73){
            cercles.add(new ClickableZone(12,157.0,164.0));
        }

        if(numeroCarte == 21){
            cercles.add(new ClickableZone(66, 162.0,154.0));
        }

    }


    private void handleMouseClicked(javafx.scene.input.MouseEvent mouse_event) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        switch(mouse_event.getButton()){
            case PRIMARY -> {
                double x = mouse_event.getX();
                double y = mouse_event.getY();
                for(ClickableZone cercle: cercles){
                    if(cercle.isInside(x, y)){
                        if(!NoClickableCard.carteTrouvee.contains(cercle.getNumero())){
                            NoClickableCard.carteTrouvee.add(cercle.getNumero());
                            p.getChildren().add(new ClickableCard(cercle.getNumero(), p));
                            File audioFile = new File("SoundEffect"+File.separator+"card.wav");
                            AudioInputStream audioStreamMain = AudioSystem.getAudioInputStream(audioFile);
                            AudioFormat formatMain = audioStreamMain.getFormat();
                            DataLine.Info infoMain = new DataLine.Info(Clip.class, formatMain);
                            Clip audioClipMain = (Clip) AudioSystem.getLine(infoMain);
                            audioClipMain.open(audioStreamMain);
                            audioClipMain.start();
                        }
                    }
                }
            }
            case SECONDARY -> {
                setRotate(getRotate()+this.ROTATE_ANGLE);
            }
            default -> throw new IllegalArgumentException("Unexpected value: " + mouse_event.getButton());
        }
    }

}