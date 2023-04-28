package Swing_Control.ControlPanelPart;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class FinalCodePanel{

    private final int CODE = 459;
    private JButton validateB;
    private JButton resetB;
    private JButton zeroB;
    private JButton fourB;
    private JButton sevenB;
    private JButton nineB;
    private JButton twoB;
    private JButton fiveB;
    private JButton sixB;
    private JButton threeB;
    private JButton oneB;
    private JButton eightB;
    private JLabel CodeLabel;
    public static JFrame frame = new JFrame(null, null);
    
        public FinalCodePanel() {
            //construct components
            validateB = new JButton ("VALIDATE");
            resetB = new JButton ("RESET");
            zeroB = new JButton ("0");
            fourB = new JButton ("4");
            sevenB = new JButton ("7");
            nineB = new JButton ("9");
            twoB = new JButton ("2");
            fiveB = new JButton ("5");
            sixB = new JButton ("6");
            threeB = new JButton ("3");
            oneB = new JButton ("1");
            eightB = new JButton ("8");
            CodeLabel = new JLabel ("");
            CodeLabel.setHorizontalAlignment(JLabel.CENTER);
            CodeLabel.setFont(new Font("Arial", Font.PLAIN, 13));
    
            //adjust size and set layout
            frame.setPreferredSize (new Dimension (235, 371));
            frame.setLayout (null);
    
            //add components
            frame.add (validateB);
            frame.add (resetB);
            frame.add (zeroB);
            frame.add (fourB);
            frame.add (sevenB);
            frame.add (nineB);
            frame.add (twoB);
            frame.add (fiveB);
            frame.add (sixB);
            frame.add (threeB);
            frame.add (oneB);
            frame.add (eightB);
            frame.add (CodeLabel);

            frame.setTitle("Final Code");
            frame.setResizable(false);

            frame.setSize(253, 410);
    
            //set component bounds (only needed by Absolute Positioning)
            validateB.setBounds (120, 325, 105, 40);
            resetB.setBounds (10, 325, 105, 40);
            zeroB.setBounds (100, 260, 45, 40);
            fourB.setBounds (15, 130, 45, 40);
            sevenB.setBounds (15, 70, 45, 40);
            nineB.setBounds (175, 70, 45, 40);
            twoB.setBounds (95, 195, 45, 40);
            fiveB.setBounds (95, 130, 45, 40);
            sixB.setBounds (175, 130, 45, 40);
            threeB.setBounds (175, 195, 45, 40);
            oneB.setBounds (15, 195, 45, 40);
            eightB.setBounds (95, 70, 45, 40);
            CodeLabel.setBounds (10, 10, 215, 45);


            if(ControlPanel.isGameover()){
                System.out.println("Hello");
            }

            validateB.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    isOver();
                    if(CodeLabel.getText().equals(new String(""))){
                        CodeLabel.setForeground(Color.RED);
                        CodeLabel.setText("No code, click reset to enter again");
                    }else{
                        if(CodeLabel.getText().equals(Integer.toString(CODE))){
                            ControlPanel.getAudioClipMain().stop();
                            try {
                                ControlPanel.getAudioStreamMain().close();
                            } catch (IOException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                            try {
                                ControlPanel.audioStreamMain.close();
                                ControlPanel.audioClipMain.stop();
                                HideCardPanel.playSound("SoundEffect"+File.separator+"sucess.wav");
                                HideCardPanel.playSound("SoundEffect"+File.separator+"tatada.wav");
                                ControlPanel.timerLabel.setFont(new Font("Arial", Font.PLAIN, 40));
                                ImageIcon icon = new ImageIcon("EndImages\\youwin.png");
                                Image image = icon.getImage().getScaledInstance(375, 90, Image.SCALE_SMOOTH);
                                icon = new ImageIcon(image);
                                ControlPanel.timerLabel.setIcon(icon);
                                ControlPanel.code0--;
                                frame.setVisible(false);
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
                            ControlPanel.timer.stop();
                            ControlPanel.historyB.setEnabled(false);
                            ControlPanel.hiddenCardB.setEnabled(false);
                            ControlPanel.timerStatusB.setEnabled(false);
                            ControlPanel.getHintB.setEnabled(false);
                            ControlPanel.enterCodeB.setEnabled(false);

                        }else{
                            CodeLabel.setForeground(Color.RED);
                            CodeLabel.setText("BAD CODE: -5 min...");
                            ControlPanel.remainingTimeSeconds -= 5*60;
                            try {
                                HideCardPanel.playSound("SoundEffect"+File.separator+"wrong.wav");
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
                        }
                    }
                }
                
            });

            resetB.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    CodeLabel.setForeground(Color.BLACK);
                    CodeLabel.setText("");
                    isOver();
                }
                
            });


            zeroB.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    String a = CodeLabel.getText().toString();
                    CodeLabel.setText(a += "0");
                    isOver();
                }
                
            });

            oneB.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    String a = CodeLabel.getText().toString();
                    CodeLabel.setText(a += "1");
                    isOver();
                }
                
            });

            twoB.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    String a = CodeLabel.getText().toString();
                    CodeLabel.setText(a += "2");
                    isOver();
                }
                
            });

            threeB.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    String a = CodeLabel.getText().toString();
                    CodeLabel.setText(a += "3");
                    isOver();
                }
                
            });

            fourB.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    String a = CodeLabel.getText().toString();
                    CodeLabel.setText(a += "4");
                    isOver();
                }
                
            });

            
            fiveB.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    String a = CodeLabel.getText().toString();
                    CodeLabel.setText(a += "5");
                    isOver();
                }
                
            });

            sixB.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    String a = CodeLabel.getText().toString();
                    CodeLabel.setText(a += "6");
                    isOver();
                }
                
            });

            sevenB.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    String a = CodeLabel.getText().toString();
                    CodeLabel.setText(a += "7");
                    isOver();
                }
                
            });

            eightB.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    String a = CodeLabel.getText().toString();
                    CodeLabel.setText(a += "8");
                    isOver();
                }
                
            });

            nineB.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    String a = CodeLabel.getText().toString();
                    CodeLabel.setText(a += "9");
                    isOver();
                }
                
            });

            frame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    ControlPanel.code0--;
                }
            });

            frame.setVisible(true);
    }

    public void isOver(){
        if(ControlPanel.isGameover()){
            resetB.setEnabled(false);
            zeroB.setEnabled(false);
            oneB.setEnabled(false);
            twoB.setEnabled(false);
            threeB.setEnabled(false);
            fourB.setEnabled(false);
            fiveB.setEnabled(false);
            sixB.setEnabled(false);
            sevenB.setEnabled(false);
            eightB.setEnabled(false);
            nineB.setEnabled(false);
            validateB.setEnabled(false);
        }
    }
}