package layer8project;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class HomePage {

    public HomePage(User loggedInUser, UserProgress userProgress, UserManager userManager, ModuleManager moduleManager, ProgressRepository progressRepository) {

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
        frame.setVisible(false);
        new ProfilePage(loggedInUser, userProgress, userManager, frame);
        });

        // Placeholder for future module grid
        JButton modulesButton = new JButton("Modules");
        modulesButton.setBackground(new Color(30, 200, 30));
        modulesButton.setForeground(Color.WHITE);
        modulesButton.setFocusPainted(false);
        modulesButton.setMaximumSize(new Dimension(300, 100));
        modulesButton.setPreferredSize(new Dimension(300,80));
        modulesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        modulesButton.setOpaque(true);
        modulesButton.setBorderPainted(false);
                modulesButton.addActionListener(e -> {
            frame.dispose();
            new ModulesPage(loggedInUser, userProgress, moduleManager, frame);
        });
        

        // Contact support button
        JButton contactButton = new JButton("Contact Support");
        contactButton.setBackground(new Color(60, 60, 60));
        contactButton.setForeground(Color.WHITE);
        contactButton.setFocusPainted(false);
        contactButton.setMaximumSize(new Dimension(300, 45));
        contactButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        contactButton.setOpaque(true);
        contactButton.setBorderPainted(false);
        // Contact Support Listener
        contactButton.addActionListener(e -> {
                JOptionPane.showMessageDialog(frame,
                    "Need help?\n\n" + 
                    "Email: support@layer7.com\n" +
                    "Phone: (877) CAS-HNOW\n\n" +
                    "Support Hours: Monday - Friday, 9 AM - 5 PM",
                    "Contact Support",
                    JOptionPane.INFORMATION_MESSAGE);
                });

        // Add everything to panel
        panel.add(Box.createVerticalStrut(20));
        panel.add(logoLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(welcomeLabel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(modulesButton);
        panel.add(Box.createVerticalStrut(20));
        panel.add(profileButton);
        panel.add(Box.createVerticalStrut(20));
        panel.add(contactButton);

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // ADMIN PAGE Button
        if (loggedInUser.isAdmin()) {
            JButton adminButton = new JButton("Admin Dashboard");
            adminButton.setBackground(new Color(180, 80, 0));
            adminButton.setForeground(Color.WHITE);
            adminButton.setFocusPainted(false);
            adminButton.setMaximumSize(new Dimension(300, 45));
            adminButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            adminButton.addActionListener(e -> {
                frame.setVisible(false);
                new AdminPage(loggedInUser,userManager,moduleManager,frame);
            });
            panel.add(Box.createVerticalStrut(10));
            panel.add(adminButton);
        }

        // Logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(new Color(120, 40, 40));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setOpaque(true);
        logoutButton.setBorderPainted(false);
        logoutButton.setMaximumSize(new Dimension(300, 45));
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButton.addActionListener(e -> {
            frame.dispose();
            new LoginPage(userManager, moduleManager, progressRepository);
        });
        panel.add(Box.createVerticalStrut(10));
        panel.add(logoutButton);
    }
}