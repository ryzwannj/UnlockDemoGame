
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import JavaFX_Board.CardObject.ClickableCard;
import JavaFX_Board.CardObject.NoClickableCard;
import Ser_DSer.Deserialize;
import Ser_DSer.Serialize;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Controller {
    @FXML
    Pane board;
    @FXML private javafx.scene.control.Button closeButton;

    private int[] carteToAdd = {0};
    private String fileSeparator = File.separator;
    public void initialize(){
        board.setOnMouseClicked(mouse_event -> {
            try {
                handleMouseClicked(mouse_event);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
    }

    public void handleMouseClicked(javafx.scene.input.MouseEvent mouseEvent) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        System.out.println(ClickableCard.carteTrouvee);
        new Serialize(NoClickableCard.carteTrouvee, "SerializationFiles"+fileSeparator+"ArrayListCarteTrouvee.ser");
        Deserialize r = new Deserialize("SerializationFiles"+fileSeparator+"HiddenCard2Show.ser");
        Deserialize end = new Deserialize("SerializationFiles"+ fileSeparator +"EndingGameBoolean.ser");
        boolean endGameFlag = (boolean) end.getObject();
        System.out.println("before endgame flag");
        if(endGameFlag == true){
            System.out.println("inside endGameFlag");
            System.out.println("EndGameFlag: " + endGameFlag);
            Alert alert = new Alert(AlertType.WARNING);
            alert.setContentText("You can't continue to play the game without the Control Panel, so after you pressed 'OK' the game will be closed!");
            alert.setResizable(false);
            alert.showAndWait();
            if(alert.getResult() == ButtonType.OK){
                System.out.println("Closing the Board Window");
                Stage stage = (Stage) board.getScene().getWindow();
                stage.close();
            }
        }
        carteToAdd = (int[]) r.getObject();
        if(! (NoClickableCard.carteTrouvee.contains(carteToAdd))){
            File audioFile = new File("SoundEffect"+File.separator+"card.wav");
            AudioInputStream audioStreamMain = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat formatMain = audioStreamMain.getFormat();
            DataLine.Info infoMain = new DataLine.Info(Clip.class, formatMain);
            Clip audioClipMain = (Clip) AudioSystem.getLine(infoMain);
            audioClipMain.open(audioStreamMain);
            audioClipMain.start();
            NoClickableCard.carteTrouvee.add(carteToAdd[0]);
            System.out.println("Carte Trouve: " + NoClickableCard.carteTrouvee);
            board.getChildren().add(new ClickableCard(carteToAdd[0], board));
            new Serialize(NoClickableCard.carteTrouvee, "SerializationFiles"+fileSeparator+"HiddenCard2Show.ser");
            new Serialize(NoClickableCard.carteTrouvee, "SerializationFiles"+fileSeparator+"ArrayListCarteTrouvee.ser");
        }
    }
}