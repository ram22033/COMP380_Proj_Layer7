package layer8project;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class mainpage   {

public mainpage(
            User loggedUser, 
            UserProgress userProgress, 
            UserManager userManager
            ) {

        JFrame frame = new JFrame("Layer 7");
        frame.setSize(800,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(30,30,30));
        frame.setLayout(new BorderLayout());


        //Main Panel
        JPanel panel = new JPanel();
        panel.setBackground(new Color(30,30,30));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        //Header Panel
        JPanel headerpanel = new JPanel(new BorderLayout());
        headerpanel.setBackground(new Color(30,30,30));
        
        //Center Content
        JPanel formPanel = new JPanel();
        formPanel.setBackground(new Color(30,30,30));
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.setMaximumSize(new Dimension(300,250));

        //Logo
        ImageIcon logo = new ImageIcon(App.class.getResource("/Images/Logo.png"));
        Image scaled = logo.getImage().getScaledInstance(140,40,Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(scaled));

        //Profile Button
        JButton profilebutton = new JButton("👤");
        profilebutton.setPreferredSize(new Dimension(60,60));
        profilebutton.setMaximumSize(new Dimension(60,60));
        profilebutton.setAlignmentX(Component.CENTER_ALIGNMENT);

        profilebutton.addActionListener(e -> {
            frame.setVisible(false);

            new ProfilePage(
                loggedUser,
                userProgress,
                userManager,
                frame);
        }); 


        headerpanel.add(logoLabel,BorderLayout.WEST);
        headerpanel.add(profilebutton, BorderLayout.EAST);

        panel.add(headerpanel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(formPanel);
        panel.add(Box.createVerticalStrut(5));
;
    

        frame.add(panel, BorderLayout.NORTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}