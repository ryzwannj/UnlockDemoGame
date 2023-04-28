package Swing_Control.ControlPanelPart;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.text.AbstractDocument;

import Ser_DSer.Serialize;
import Swing_Control.ControlPanelPart.EntryVerif.IntegerOnlyFilter;

public class HideCardPanel{
    private JTextField cardField1;
    private JTextField cardField2;
    private JButton showB;
    private JLabel jcomp4;
    private JLabel jcomp5;
    private JLabel card2Show;

    private int[] resultPosibility = {58, 76, 70, 79};
    public static int[] cardToShow = {0};
    public static Object endGame;
    private String fileSeparator = File.separator;

    public HideCardPanel() {
        //construct components
        cardField1 = new JTextField ();
        cardField1.setSize(20, 20);
        cardField1.setHorizontalAlignment(JTextField.CENTER);
        cardField2 = new JTextField ();
        cardField2.setSize(20, 20);
        cardField2.setHorizontalAlignment(JTextField.CENTER);
        cardField1.setFont(new Font("Arial", Font.PLAIN, 20));
        cardField2.setFont(new Font("Arial", Font.PLAIN, 20));

        ((AbstractDocument) cardField1.getDocument()).setDocumentFilter(new IntegerOnlyFilter());
        ((AbstractDocument) cardField2.getDocument()).setDocumentFilter(new IntegerOnlyFilter());

        showB = new JButton ("Show Card");
        jcomp4 = new JLabel ("Do action with card number:");
        jcomp5 = new JLabel ("and card number");
        card2Show = new JLabel ("0");
        card2Show.setFont(new Font("Arial", Font.PLAIN, 20));
        card2Show.setHorizontalAlignment(JTextField.CENTER);

        JFrame frame = new JFrame("Action", null);
        frame.setSize(380, 380);

        //adjust size and set layout
        frame.setLayout (null);

        //add components
        frame.add (cardField1);
        frame.add (cardField2);
        frame.add (showB);
        frame.add (jcomp4);
        frame.add (jcomp5);
        frame.add (card2Show);

        frame.setResizable(false);

        //set component bounds (only needed by Absolute Positioning)
        cardField1.setBounds (225, 25, 100, 75);
        cardField2.setBounds (225, 155, 100, 75);
        showB.setBounds (20, 280, 170, 50);
        jcomp4.setBounds (10, 50, 175, 30);
        jcomp5.setBounds (20, 180, 100, 25);
        card2Show.setBounds (210, 285, 105, 40);

        showB.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cardField1.getText().equals(new String(""))|| cardField2.getText().equals(new String(""))){
                    card2Show.setText("Error");
                }else{
                    try {
                        int num1 = Integer.parseInt(cardField1.getText()); 
                        int num2 = Integer.parseInt(cardField2.getText());
                        int card = num1 + num2;
                        card2Show.setText(String.valueOf(card));
                        if(coupleValide(num1, num2)){
                            for(int i =0; i < resultPosibility.length; i++){
                                if(card == resultPosibility[i]){
                                    card2Show.setText(Integer.toString(card));
                                    cardToShow[0] = card;
                                    new Serialize(cardToShow, "SerializationFiles"+ fileSeparator +"HiddenCard2Show.ser");
                                    ControlPanel.hiddenO--;
                                    frame.dispose();
                                }
                            }
                        }else{
                            try {
                                playSound("SoundEffect"+File.separator+"wrong.wav");
                            } catch (UnsupportedAudioFileException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            } catch (IOException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            } catch (LineUnavailableException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                            ControlPanel.remainingTimeSeconds -= 3*60;
                            card2Show.setText("-3 min...");
                            ControlPanel.hiddenO--;
                            frame.dispose();
                        }
                    } catch (NumberFormatException a) {
                        System.err.println("Error: Input is not an integer"); 
                        card2Show.setText("Error");
                    }
                }
            }
        });

        frame.setVisible(true);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                ControlPanel.hiddenO--;
            }
        });
    }

    public static void playSound(String file) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        File audioFile = new File(file);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
        AudioFormat format = audioStream.getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class, format);
        Clip audioClip = (Clip) AudioSystem.getLine(info);
        audioClip.open(audioStream);
        audioClip.start();
    }

    public boolean coupleValide(int x , int y){
        boolean carte1V = false;
        boolean carte2V = false;

        boolean coupleV = false;
        int[][] couple = {
            {7, 51},
            {51, 7},
            {37, 39},
            {39, 37},
            {39, 31}, 
            {31, 39}, 
            {68, 11}, 
            {11, 68},
            {21, 22},
            {22, 21},
        };

        for(int i= 0; i<ControlPanel.carteTrouveeList.size(); i++){
            if(x == ControlPanel.carteTrouveeList.get(i)){
                carte1V = true;
            }
            if(y == ControlPanel.carteTrouveeList.get(i)){
                carte2V = true;
            }
        }

        if(carte2V && carte1V){
            for (int i = 0; i < couple.length; i++) {
                if (couple[i][0] == x && couple[i][1] == y ) {
                    coupleV = true; // La paire a été trouvée dans le tableau
                    break; // Sort de la boucle for
                }
            }
        }
        return coupleV;
    }
}