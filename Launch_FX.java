import java.io.File;
import java.util.Optional;

import JavaFX_Board.CardObject.NoClickableCard;
import Ser_DSer.Serialize;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class Launch_FX extends Application {
    public static boolean endGameFX = false;
    private static String fileSeparator = File.separator;
    public static void main(String[] args) {
        new Serialize(endGameFX, "SerializationFiles"+fileSeparator+"EndingGameFlagFx.ser");
        new Serialize(NoClickableCard.carteTrouvee, "SerializationFiles"+fileSeparator+"ArrayListCarteTrouvee.ser");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("board.fxml"));
        primaryStage.setTitle("Unlock Board");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setContentText("Are you sure you want to close this window, if you click 'OK', it'll end the game");
            alert.setTitle("Close the Unlock Board");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                endGameFX = true;
                new Serialize(endGameFX, "SerializationFiles"+fileSeparator+"EndingGameFlagFx.ser");
                primaryStage.close();
            } else {
            }
        });
    }
}
