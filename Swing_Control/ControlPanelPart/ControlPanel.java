package Swing_Control.ControlPanelPart;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.WindowConstants;

import Ser_DSer.Deserialize;
import Ser_DSer.Serialize;

@SuppressWarnings("unchecked")
public class ControlPanel{
    public static JButton enterCodeB;
    public static JButton historyB;
    public static JButton hiddenCardB;
    public static JButton timerStatusB;
    public static JButton getHintB;
    public static JLabel timerLabel;
    public static ArrayList<Integer> carteTrouveeList;
    public static int remainingTimeSeconds = 40 * 60;
    private boolean isRunning;
    public static Timer timer;
    public static String fileSeparator = File.separator;

    public static boolean gameOver = false;
    public static Clip audioClipMain;
    public static AudioInputStream audioStreamMain;

    public static int hiddenO = 0;
    public static int hint0 = 0;
    public static int history0 = 0;
    public static int code0 = 0;
    public static boolean endGame = false;
    private JFrame frame;


    public ControlPanel() throws LineUnavailableException{
        File audioFile = new File("SoundEffect"+File.separator+"game.wav");
        try {
            audioStreamMain = AudioSystem.getAudioInputStream(audioFile);
            final AudioFormat formatMain = audioStreamMain.getFormat();
            DataLine.Info infoMain = new DataLine.Info(Clip.class, formatMain);
            audioClipMain = (Clip) AudioSystem.getLine(infoMain);
                
            audioClipMain.open(audioStreamMain);
            audioClipMain.start();
            audioClipMain.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //construct components
        enterCodeB = new JButton ("Enter Code");
        historyB = new JButton ("Hint History");
        hiddenCardB = new JButton ("Do Action");
        timerStatusB = new JButton ("Stop");
        getHintB = new JButton ("Get Hint");
        timerLabel = new JLabel ("40:00");
        timerLabel.setHorizontalAlignment(JLabel.CENTER);
        timerLabel.setFont(new Font("Arial", Font.PLAIN, 70));
        timerLabel.setForeground(Color.BLUE);


        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remainingTimeSeconds--;

                if (remainingTimeSeconds <= 10*60){
                    timerLabel.setForeground(Color.ORANGE);
                
                }

                // Convert the remaining time from seconds to minutes and seconds
                int remainingMinutes = remainingTimeSeconds / 60;
                int remainingSeconds = remainingTimeSeconds % 60;

                // Update the timer label with the remaining time
                timerLabel.setText(String.format("%02d:%02d", remainingMinutes, remainingSeconds));
                if (remainingTimeSeconds <= 0) {
                    FinalCodePanel.frame.setVisible(false);
                    gameOver = true;
                    System.out.println(gameOver);
                    enterCodeB.setEnabled(false);
                    historyB.setEnabled(false);
                    hiddenCardB.setEnabled(false);
                    getHintB.setEnabled(false);
                    timerStatusB.setEnabled(false);
                    timerLabel.setFont(new Font("Arial", Font.PLAIN, 30));
                    timerLabel.setText("GAME OVER");
                    timerLabel.setForeground(Color.RED);
                    ((Timer) e.getSource()).stop();
                    audioClipMain.stop();
                    try {
                        audioStreamMain.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    File audioFile = new File("SoundEffect"+File.separator+"gameOver.wav");
                    AudioInputStream audioStream;
                    try {
                        audioStream = AudioSystem.getAudioInputStream(audioFile);
                        AudioFormat format = audioStream.getFormat();
                        DataLine.Info info = new DataLine.Info(Clip.class, format);
                        Clip audioClip = (Clip) AudioSystem.getLine(info);
                        audioClip.open(audioStream);
                        audioClip.start();
                    } catch (UnsupportedAudioFileException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (LineUnavailableException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        //adjust size and set layout
        frame = new JFrame(null, null);
        frame.setSize(412, 364);
        frame.setTitle("Control Panel");
        frame.setResizable(false);
        frame.setLayout (null);

        //add components
        frame.add (enterCodeB);
        frame.add (historyB);
        frame.add (hiddenCardB);
        frame.add (timerStatusB);
        frame.add (getHintB);
        frame.add (timerLabel);

        enterCodeB.setBounds (155, 270, 105, 45);
        historyB.setBounds (30, 270, 105, 45);
        hiddenCardB.setBounds (280, 270, 105, 45);
        timerStatusB.setBounds (90, 195, 105, 45);
        getHintB.setBounds (215, 195, 105, 45);
        timerLabel.setBounds (20, 50, 375, 90);

        timer.start();
 

        enterCodeB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deserFlag();
                Deserialize r = new Deserialize("SerializationFiles" + fileSeparator + "ArrayListCarteTrouvee.ser");
                carteTrouveeList = (ArrayList<Integer>) r.getObject();
                System.out.println(carteTrouveeList);
                if(code0 == 1){
                    JLabel label = new JLabel("\"Final Code\" Panel is already opened, to open a new one please close the panel which is already opened.");
                    label.setFont(new Font("Arial", Font.BOLD, 20));
                    label.setForeground(Color.RED);
                    JOptionPane.showMessageDialog(null,label,"Panel already opened",JOptionPane.WARNING_MESSAGE);
                }else{
                    code0++;
                    new FinalCodePanel();
                }
            }
        });

        historyB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deserFlag();
                Deserialize r = new Deserialize("SerializationFiles"+ fileSeparator + "ArrayListCarteTrouvee.ser");
                carteTrouveeList = (ArrayList<Integer>) r.getObject();
                System.out.println(carteTrouveeList);
                if(history0 == 1){
                    JLabel label = new JLabel("\"Hint History\" Panel is already opened, to open a new one please close the panel which is already opened.");
                    label.setFont(new Font("Arial", Font.BOLD, 20));
                    label.setForeground(Color.RED);
                    JOptionPane.showMessageDialog(null,label,"Panel already opened",JOptionPane.WARNING_MESSAGE);
                }else{
                    history0++;
                    new HintPanelHistory();
                }
            }
        });

        hiddenCardB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deserFlag();
                Deserialize r = new Deserialize("SerializationFiles"+ fileSeparator +"ArrayListCarteTrouvee.ser");
                carteTrouveeList = (ArrayList<Integer>) r.getObject();
                System.out.println(carteTrouveeList);
                if(hiddenO == 1){
                    JLabel label = new JLabel("\"Hidden Card\" Panel is already opened, to open a new one please close the panel which is already opened.");
                    label.setFont(new Font("Arial", Font.BOLD, 20));
                    label.setForeground(Color.RED);
                    JOptionPane.showMessageDialog(null,label,"Panel already opened",JOptionPane.WARNING_MESSAGE);
                }else{
                    hiddenO++;
                    new HideCardPanel();
                }
            }
        });

        timerStatusB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (isRunning) {
  
  deserFlag();                  timer.stop();
                    isRunning = false;
                    timerStatusB.setText("Start");
                    enterCodeB.setEnabled(false);
                    historyB.setEnabled(false);
                    hiddenCardB.setEnabled(false);
                    getHintB.setEnabled(false);

                } else {
                    timer.start();
                    isRunning = true;
                    timerStatusB.setText("Stop");
                    enterCodeB.setEnabled(true);
                    historyB.setEnabled(true);
                    hiddenCardB.setEnabled(true);
                    getHintB.setEnabled(true);
                }
            }
        });

        getHintB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deserFlag();
                Deserialize r = new Deserialize("SerializationFiles"+ fileSeparator +"ArrayListCarteTrouvee.ser");
                carteTrouveeList = (ArrayList<Integer>) r.getObject();
                System.out.println(carteTrouveeList);
                if(hint0 == 1){
                    JLabel label = new JLabel("\"Get Hint\" Panel is already opened, to open a new one please close the panel which is already opened.");
                    label.setFont(new Font("Arial", Font.BOLD, 20));
                    label.setForeground(Color.RED);
                    JOptionPane.showMessageDialog(null,label,"Panel already opened",JOptionPane.WARNING_MESSAGE);
                }else{
                    hint0++;
                    new HintPanel();
                }
                
            }
        });

        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(frame, 
                    "Are you sure you want to close the Control Panel Window, if you click yes it will end the game?", "Close Window?", 
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                        endGame = true;
                        new Serialize(endGame, "SerializationFiles"+ fileSeparator +"EndingGameBoolean.ser");
                        System.exit(0);
                }
            }
        });
        frame.setVisible(true);
    }

    public void deserFlag(){
        Deserialize flag = new Deserialize("SerializationFiles"+fileSeparator+"EndingGameFlagFx.ser");
        boolean flagCast = (boolean) flag.getObject();
        if(flagCast == true){
            System.out.println("Error not working without Fx part");
            String message = "Without fx board, the game can't continue well, when you'll press 'OK' the Control Panel will be closed";
            String title = "Fx Board Closed";
            int messageType = JOptionPane.WARNING_MESSAGE;
            Object[] options = {"OK"};

            int result = JOptionPane.showOptionDialog(null, message, title,
                    JOptionPane.DEFAULT_OPTION, messageType, null, options, options[0]);

            if (result == JOptionPane.OK_OPTION) {
                System.out.println("OK button clicked.");
                System.exit(0);
            }
        }
    }

    public static AudioInputStream getAudioStreamMain() {
        return audioStreamMain;
    }

    public static Clip getAudioClipMain() {
        return audioClipMain;
    }

    public static boolean isGameover() {
        return gameOver;
    }

    public static void removeAllSerialFiles(){
        String fileNameSER1 = "SerializationFiles"+ fileSeparator +"ArrayListCarteTrouvee.ser";
        String fileNameSER2 = "SerializationFiles"+ fileSeparator +"HiddenCard2Show.ser";
        String endGameSerial = "SerializationFiles"+ fileSeparator +"EndingGameBoolean.ser";
        try {
            Files.delete(Paths.get(fileNameSER1));
        } catch (IOException x) {
            System.err.println(x);
        }
        try {
            Files.delete(Paths.get(fileNameSER2));
        } catch (IOException x) {
            System.err.println(x);
        }
        try {
            Files.delete(Paths.get(endGameSerial));
        } catch (IOException x) {
            System.err.println(x);
        }
    }

    public static void recreateAllSerialFiles(){
        String fileNameSER1 = "SerializationFiles"+ fileSeparator +"ArrayListCarteTrouvee.ser";
        String fileNameSER2 = "SerializationFiles"+ fileSeparator +"HiddenCard2Show.ser";
        String endGameSerial = "SerializationFiles"+ fileSeparator +"EndingGameBoolean.ser";
        boolean endGame = false;

        try {

            File New_File = new File(fileNameSER1);
    
            if (New_File.createNewFile()){
                System.out.println("The file is created successfully!");
            }
            else{
                System.out.println("The file already exists.");
            }
    
            }catch (IOException e) {
                e.printStackTrace();
            }

        try {

            File New_File = new File(fileNameSER2);
            new Serialize(HideCardPanel.cardToShow, fileNameSER2);
            if (New_File.createNewFile()){
                System.out.println("The file is created successfully!");
            }
            else{
                System.out.println("The file already exists.");
            }
    
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            try{

                File New_File = new File(endGameSerial);
                new Serialize(endGame, endGameSerial);
                if (New_File.createNewFile()){
                    System.out.println("The file is created successfully!");
                }
                else{
                    System.out.println("The file already exists.");
                }
        
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
    }
    
    

}