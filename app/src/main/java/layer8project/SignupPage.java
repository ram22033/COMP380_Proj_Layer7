package layer8project;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SignupPage { 

    public SignupPage() {

        JFrame frame = new JFrame("Sign up");
        frame.setSize(800,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().setBackground(new Color(30,30,30));
        frame.setLayout(new GridBagLayout());
        // Creation of the background color along with the layout inside the frame

        JPanel panel = new JPanel();
        panel.setBackground(new Color(30,30,30));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        //The creation of the panel allows us to edit anything inside the frame
        // and the setLayout allows us to stack them vertically  

        JPanel formPanel = new JPanel();
        formPanel.setBackground(new Color(30,30,30));
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.setMaximumSize(new Dimension(300,250));        

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }
}