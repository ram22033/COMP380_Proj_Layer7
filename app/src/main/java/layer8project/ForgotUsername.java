package layer8project;

import javax.swing.*;
import java.awt.*;

public class ForgotUsername {

    public ForgotUsername(JFrame loginFrame, UserManager userManager) { 

        JFrame frame = new JFrame("Layer 8");
        frame.setSize(800,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(30,30,30));
        frame.setLayout(new GridBagLayout());

        JPanel panel =  new JPanel();
        panel.setBackground(new Color( 30,30,30));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel formPanel = new JPanel();
        formPanel.setBackground(new Color(30,30,30));
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.setMaximumSize(new Dimension(300,250));

        ImageIcon logo = new ImageIcon(App.class.getResource("/Images/Logo.png"));
        Image scaled = logo.getImage().getScaledInstance(300,150, Image.SCALE_SMOOTH);
        
        JLabel background = new JLabel(new ImageIcon(scaled));
        background.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(background);
        panel.add(Box.createVerticalStrut(5));

        panel.add(formPanel);
        panel.add(Box.createVerticalStrut(5));

        JLabel newuser = new JLabel("Enter New Username");
        newuser.setForeground(Color.WHITE);
        newuser.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField newuserField = new JTextField();
        newuserField.setMaximumSize(new Dimension(300,40));
        newuserField.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel email = new JLabel("Enter Email");
        email.setForeground(Color.WHITE);
        email.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JTextField emailField = new JTextField();
        emailField.setMaximumSize(new Dimension (300,40));
        emailField.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton nextButton = new JButton("Next");
        nextButton.setBackground(new Color(60,60,60));
        nextButton.setForeground(Color.WHITE);
        nextButton.setFocusPainted(false);
        nextButton.setMaximumSize(new Dimension(400,45));
        nextButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        nextButton.setOpaque(true);
        nextButton.setBorderPainted(false);
        
        nextButton.addActionListener(e ->{
            frame.dispose();
            loginFrame.setVisible(true);
        });




        formPanel.add(newuser);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(newuserField);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(email);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(emailField);
        formPanel.add(nextButton);
        formPanel.add(Box.createVerticalStrut(5));



        
        

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }    



}
