package layer8project;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ProfilePage {

    public ProfilePage(User loggedInUser, UserManager userManager, JFrame homeFrame)  {

        JFrame frame = new JFrame("Profile");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(30, 30, 30));
        frame.setLayout(new GridBagLayout());

        JPanel panel = new JPanel();
        panel.setBackground(new Color(30, 30, 30));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel formPanel = new JPanel();
        formPanel.setBackground(new Color(30, 30, 30));
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.setMaximumSize(new Dimension(400, 300));

        // Username display
        JLabel usernameTitle = new JLabel("Username");
        usernameTitle.setForeground(new Color(150, 150, 150));
        usernameTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel usernameValue = new JLabel(loggedInUser.getUsername());
        usernameValue.setForeground(Color.WHITE);
        usernameValue.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Change password button
        JButton changePasswordButton = new JButton("Change Password");
        changePasswordButton.setBackground(new Color(0, 120, 255));
        changePasswordButton.setForeground(Color.WHITE);
        changePasswordButton.setFocusPainted(false);
        changePasswordButton.setMaximumSize(new Dimension(400, 45));
        changePasswordButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        changePasswordButton.setOpaque(true);
        changePasswordButton.setBorderPainted(false);


        changePasswordButton.addActionListener(e -> {
            new ChangePasswordPage(loggedInUser, userManager);
        });

        formPanel.add(usernameTitle);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(usernameValue);
        formPanel.add(Box.createVerticalStrut(30));
        formPanel.add(changePasswordButton);

        JButton backButton = new JButton("Back to Home");
        backButton.setBackground(new Color(60, 60, 60));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setOpaque(true);
        backButton.setBorderPainted(false);
        backButton.setMaximumSize(new Dimension(400, 45));
        backButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        backButton.addActionListener(e -> {
         frame.dispose();
         homeFrame.setVisible(true);
        });

        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(backButton);      

        panel.add(Box.createVerticalStrut(60));
        panel.add(formPanel);
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}