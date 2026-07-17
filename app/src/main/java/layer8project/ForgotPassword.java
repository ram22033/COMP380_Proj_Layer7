package layer8project;

import javax.swing.*;
import java.awt.*;

public class ForgotPassword {
    public ForgotPassword(JFrame loginFrame, UserManager userManager) {

        JFrame frame = new JFrame("Layer 8");
        frame.setSize(800,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(30,30,30));
        frame.setLayout(new GridBagLayout());

        JPanel panel = new JPanel();
        panel.setBackground(new Color(30,30,30));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JPanel formPanel = new JPanel();
        formPanel.setBackground(new Color (30,30,30));
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.setMaximumSize(new Dimension(300,250));

        ImageIcon logo = new ImageIcon(App.class.getResource("/Images/Logo.png"));
        Image scaled = logo.getImage().getScaledInstance(300,150, Image.SCALE_SMOOTH);
        
        JLabel background = new JLabel(new ImageIcon(scaled));
        background.setAlignmentX(Component.CENTER_ALIGNMENT);;

        panel.add(background);
        panel.add(Box.createVerticalStrut(5));

        panel.add(formPanel);
        panel.add(Box.createVerticalStrut(5));

        JLabel newpassword = new JLabel("Enter New Password");
        newpassword.setForeground(Color.WHITE);
        newpassword.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPasswordField newpasswordField = new JPasswordField();
        newpasswordField.setMaximumSize(new Dimension(300,40));
        newpasswordField.setAlignmentX((Component.LEFT_ALIGNMENT));

        JLabel email = new JLabel("Enter Email");
        email.setForeground(Color.WHITE);
        email.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField emailField = new JTextField();
        emailField.setMaximumSize(new Dimension(300,40));
        emailField.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton nextButton = new JButton("Next");
        nextButton.setBackground(new Color(60,120,255));
        nextButton.setForeground(Color.WHITE);
        nextButton.setFocusPainted(false);;
        nextButton.setMaximumSize(new Dimension(400,45));
        nextButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        nextButton.setOpaque(true);
        nextButton.setBorderPainted(false);

        nextButton.addActionListener( e-> {
            frame.dispose();
            loginFrame.setVisible(true);
        });

        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(60,60,60));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setMaximumSize((new Dimension(400,45)));;
        backButton.setAlignmentX((Component.LEFT_ALIGNMENT));
        backButton.setOpaque(true);
        backButton.setBorderPainted(false);

        backButton.addActionListener(e -> {
            frame.dispose();
            loginFrame.setVisible(true);
            
        });

        formPanel.add(newpassword);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(newpasswordField);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(email);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(emailField);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(nextButton);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(backButton);
        formPanel.add(Box.createVerticalStrut(20));


        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);    
    }
}
