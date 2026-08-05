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
import java.awt.GridLayout;


public class mainpage   {

public mainpage(
            User loggedUser, 
            UserProgress userProgress, 
            UserManager userManager,
            JFrame homefFrame
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
        profilebutton.setFocusPainted(false);
        profilebutton.setBorderPainted(false);
        profilebutton.setContentAreaFilled(false);
        profilebutton.setOpaque(false);
        profilebutton.setForeground(Color.WHITE);
        

        profilebutton.addActionListener(e -> {
            frame.setVisible(false);

            new ProfilePage(
                loggedUser,
                userProgress,
                userManager,
                frame);
        }); 

        JPanel moduleGrid = new JPanel(new GridLayout(3,3,30,30));
        moduleGrid.setBackground(new Color (30,30,30));
        moduleGrid.setBorder( 
            javax.swing.BorderFactory.createEmptyBorder(50, 100, 50, 100)
        );

        JButton module1 = createModuleButton("Module 1");
        JButton module2 = createModuleButton("Module 2");
        JButton module3 = createModuleButton("Module 3");
        JButton module4 = createModuleButton("Module 4");
        JButton module5 = createModuleButton("Module 5");
        JButton module6 = createModuleButton("Module 6");
        JButton module7 = createModuleButton("Module 7");
        JButton module8 = createModuleButton("Module 8");
        JButton module9 = createModuleButton("Module 9");

        moduleGrid.add(module1);
        moduleGrid.add(module2);
        moduleGrid.add(module3);
        moduleGrid.add(module4);
        moduleGrid.add(module5);
        moduleGrid.add(module6);
        moduleGrid.add(module7);
        moduleGrid.add(module8);
        moduleGrid.add(module9);



        headerpanel.add(logoLabel,BorderLayout.WEST);
        headerpanel.add(profilebutton, BorderLayout.EAST);

        frame.add(headerpanel, BorderLayout.NORTH);
        frame.add(moduleGrid, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    private JButton createModuleButton(String text) { 
        JButton button = new JButton(text);

        button.setBackground(new Color (30,30,30));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setContentAreaFilled(true);

        button.setBorder(
            javax.swing.BorderFactory.createLineBorder( 
                Color.WHITE,
                3,
                true
            )
        );
        return button;

    }
}