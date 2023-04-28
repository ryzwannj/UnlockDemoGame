package Swing_Control.ControlPanelPart;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;


import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

import Swing_Control.ControlPanelPart.EntryVerif.IntegerOnlyFilter;

public class HintPanel extends JPanel{

    private JLabel jcomp1;
    private JTextField jcomp2;
    private JButton getHint;
    private HashMap<Integer, String> dictionary = new HashMap<>();

    public HintPanel(){
        System.setProperty("java.awt.headless", "false");

        dictionary.put(0, "Nothing to say...");
        dictionary.put(2, "This text have no sense, maybe something is missing to that.");
        dictionary.put(5, "This text have no sense, maybe something is missing to that.");
        dictionary.put(7, "This object can open all the thing, it's not subtle, but it's working");
        dictionary.put(11, "The place is well lit. But maybe you'll find something which is more dark to reveal.");
        dictionary.put(12, "Nothing to say, don't forget to take the card 21");
        dictionary.put(21, "The ghost hate the fact when a person touch his finger");
        dictionary.put(22, "Couldn't find the random code. maybe someone can help you");
        dictionary.put(29, "From a certain angle, the letters look a lot like numbers...");
        dictionary.put(31, "A key, a door... you will find what to do.");
        dictionary.put(37, "You don't have a tie. But maybe this object can slip into very small places.");
        dictionary.put(39, "There's a way to push that key out of the lock");
        dictionary.put(50, "Nothing to say...");
        dictionary.put(51, "You don't have a key, you'll have to resort to a more brutal method.");
        dictionary.put(58, "Take cards 11, 39, 22. Remember what the ghost tells you!");
        dictionary.put(66, "Three numbers upside down... where could you use them?");
        dictionary.put(68, "This painting is really very dark. If only you had something to light up");
        dictionary.put(70, "Take cards 68 and 73");
        dictionary.put(71, "Nothing to say...");
        dictionary.put(73, "Click on what it's pointing, maybe it will show up a new card");
        dictionary.put(76, "You could probably pull the rug under the door to get the key.");
        dictionary.put(79, "Do not forget to take the card whose number is visible. And keep this painting in sight, there is surely a use");
        dictionary.put(88, "Nothing to say...");


        jcomp1 = new JLabel ("Enter a card number, to get a hint :");
        jcomp2 = new JTextField (5);
        ((AbstractDocument) jcomp2.getDocument()).setDocumentFilter(new IntegerOnlyFilter());
        getHint = new JButton ("GET");

        jcomp2.setFont(new Font("Arial", Font.PLAIN, 20));
        jcomp2.setHorizontalAlignment(JTextField.CENTER);

        JFrame frame = new JFrame(null, null);
        frame.setPreferredSize (new Dimension (340, 135));
        frame.setLayout (null);

        frame.setSize(350, 150);

        //add components
        frame.add (jcomp1);
        frame.add (jcomp2);
        frame.add (getHint);

        //set component bounds (only needed by Absolute Positioning)
        jcomp1.setBounds (10, 20, 210, 45);
        jcomp2.setBounds (225, 20, 100, 85);
        getHint.setBounds (60, 65, 100, 25);

        getHint.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed (ActionEvent e) {
               if(!jcomp2.getText().equals(new String(""))){
                int num = Integer.parseInt(jcomp2.getText());
                if(isValide(num)){
                    System.out.println(showHint(num));
                    JLabel label = new JLabel(showHint(num).toString());
                    label.setFont(new Font("Arial", Font.BOLD, 20));
                    JOptionPane.showMessageDialog(null,label,"Hint: " + num,JOptionPane.WARNING_MESSAGE);
                    HintPanelHistory.hint.put(num, showHint(num));
                    ControlPanel.hint0--;
                    frame.dispose();
                }else{
                    JLabel label = new JLabel("No hint because card not on board");
                    label.setFont(new Font("Arial", Font.BOLD, 20));
                    JOptionPane.showMessageDialog(null,label,"Hint: " + num,JOptionPane.WARNING_MESSAGE);
                    ControlPanel.hint0--;
                    frame.dispose();
                }
                }else{
                    JLabel label = new JLabel("No hint because card null");
                    label.setFont(new Font("Arial", Font.BOLD, 20));
                    JOptionPane.showMessageDialog(null,label,"Hint: ",JOptionPane.WARNING_MESSAGE);
                    ControlPanel.hint0--;
                    frame.dispose();
                }
            }

            private boolean isValide(int x) {
                boolean valid = false;
                for(int i=0; i<ControlPanel.carteTrouveeList.size(); i++){
                    if(x == ControlPanel.carteTrouveeList.get(i)){
                        valid = true;
                    }
                }
                return valid;
            }
            
        });
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                ControlPanel.hint0--;
            }
        });
        frame.setVisible(true);
    }

    public String getHintByCard(int a){
        String hint;
        try{
            hint = dictionary.get(a);
        }catch(Exception e){
            hint = "Card doesn't exist...";
        }
        return hint;
    }

    private String showHint(Integer a){
        String hint = this.getHintByCard(a);
        return hint;
    }
}