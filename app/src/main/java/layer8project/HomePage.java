package layer8project;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Image;

public class HomePage {

    public HomePage(User loggedInUser, UserManager userManager) {

        JFrame frame = new JFrame("Layer 7 - Home");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(30, 30, 30));
        frame.setLayout(new GridBagLayout());

        JPanel panel = new JPanel();
        panel.setBackground(new Color(30, 30, 30));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Logo
        ImageIcon logo = new ImageIcon(App.class.getResource("/Images/Logo.png"));
        Image scaled = logo.getImage().getScaledInstance(300, 150, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(scaled));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Welcome message
        JLabel welcomeLabel = new JLabel("Welcome, " + loggedInUser.getUsername() + "!");
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Profile button
        JButton profileButton = new JButton("Profile / Settings");
        profileButton.setBackground(new Color(0, 120, 255));
        profileButton.setForeground(Color.WHITE);
        profileButton.setFocusPainted(false);
        profileButton.setMaximumSize(new Dimension(300, 45));
        profileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        profileButton.setOpaque(true);
        profileButton.setBorderPainted(false);

        profileButton.addActionListener(e -> {
            new ProfilePage(loggedInUser, userManager);
        });

        // Placeholder for future module grid
        JLabel modulesLabel = new JLabel("Modules coming soon...");
        modulesLabel.setForeground(new Color(150, 150, 150));
        modulesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Contact support button
        JButton contactButton = new JButton("Contact Support");
        contactButton.setBackground(new Color(60, 60, 60));
        contactButton.setForeground(Color.WHITE);
        contactButton.setFocusPainted(false);
        contactButton.setMaximumSize(new Dimension(300, 45));
        contactButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        contactButton.setOpaque(true);
        contactButton.setBorderPainted(false);

        // Add everything to panel
        panel.add(Box.createVerticalStrut(20));
        panel.add(logoLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(welcomeLabel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(profileButton);
        panel.add(Box.createVerticalStrut(20));
        panel.add(modulesLabel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(contactButton);

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}