package Swing_Control.ControlPanelPart;

import java.awt.Font;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class HintPanelHistory
{
    private JLabel hintsHistoryL;
    private JLabel jcomp2;

    public static HashMap<Integer, String> hint = new HashMap<>();
    public String hintsHString = "<html>";
    
    public HintPanelHistory(){
        //construct components
        hintsHistoryL = new JLabel ("No hint(s) yet");
        jcomp2 = new JLabel ("Hints History");
        jcomp2.setFont(new Font("Arial", Font.PLAIN, 20));
        hintsHistoryL.setFont(new Font("Arial", Font.PLAIN, 15));
        hintsHistoryL.setHorizontalAlignment(JLabel.CENTER);

        JFrame frame = new JFrame(null, null);
        frame.setTitle("Hint History Panel");
        frame.setSize(623, 655);
        frame.setLayout (null);

        //add components
        frame.add (hintsHistoryL);
        frame.add (jcomp2);

        //set component bounds (only needed by Absolute Positioning)
        hintsHistoryL.setBounds (5, 70, 610, 575);
        jcomp2.setBounds (15, 10, 455, 50);

        if(hint.size() <1){
            hintsHistoryL.setText("No hint(s) yet");
        }else{
            for (Integer numero: hint.keySet()) {
                hintsHString += "Card";
                String key = numero.toString();
                hintsHString += " " + key + " : ";
                String value = hint.get(numero).toString();
                hintsHString += value + "<br/>";
                System.out.println(key + " " + value);
            }
            hintsHString += "</html>";
            System.out.println(hintsHString);
            hintsHistoryL.setText(hintsHString);
        }
        frame.setVisible(true);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                ControlPanel.history0--;
            }
        });
    }
}