package JavaFX_Board.CardObject;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;

public class NoClickableCard extends ImageView implements Serializable{

    public static ArrayList<Integer> carteTrouvee = new ArrayList<>();

    private int numeroCarte;


    public int getNumeroCarte() {
        return numeroCarte;
    }

    private String imagePath = "CImages";
    private final String fileSeparator = File.separator;

    private double dragX, dragY;

    protected double ROTATE_ANGLE = 45.0;

    public NoClickableCard(int numeroCarte){
        this.numeroCarte = numeroCarte;
        this.imagePath += fileSeparator;
        this.imagePath += "card";
        this.imagePath += numeroCarte;
        this.imagePath += ".png";


        if(this.numeroCarte == 0 || this.numeroCarte == 58){
            try {
                File file = new File(imagePath);
                Image img = new Image(file.toURI().toString());
                this.setImage(img);
                this.setPreserveRatio(true);
                this.setFitHeight(300);
                this.setFitWidth(500);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }else{
            try {
                File file = new File(imagePath);
                Image img = new Image(file.toURI().toString());
                this.setImage(img);
                this.setPreserveRatio(true);
                this.setFitHeight(500);
                this.setFitWidth(300);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        setOnMouseDragged(mouse_event -> handleMouseDragged(mouse_event));
        setOnMousePressed(mouse_event -> handleMousePressed(mouse_event));
        setOnMouseClicked(mouse_event -> handleMouseClicked(mouse_event));

    }

    private void handleMousePressed(javafx.scene.input.MouseEvent mouse_event){
        toFront();
        this.dragX = mouse_event.getScreenX() - getTranslateX() ;
        this.dragY = mouse_event.getScreenY() - getTranslateY();
    }

    private void handleMouseDragged(javafx.scene.input.MouseEvent mouse_event){
        translateXProperty().set(mouse_event.getScreenX()-this.dragX);
        translateYProperty().set(mouse_event.getScreenY()-this.dragY);
    }

    private void handleMouseClicked(javafx.scene.input.MouseEvent mouse_event){
        if(mouse_event.getButton() == MouseButton.SECONDARY){
            this.setRotate(getRotate()+ROTATE_ANGLE);
        }
        
    }

}