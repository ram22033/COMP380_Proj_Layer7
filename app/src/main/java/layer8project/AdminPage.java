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

public class AdminPage {

    public AdminPage(User loggedInUser,UserManager userManager,ModuleManager moduleManager,JFrame homeFrame) {
        // Extra protection in case a non-admin somehow opens this page
        if (!loggedInUser.isAdmin()) {
            throw new SecurityException("Only administrators can access the Admin Page.");
        }
        JFrame frame = new JFrame("Layer 8 - Admin Dashboard");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(30, 30, 30));
        frame.setLayout(new GridBagLayout());
        JPanel panel = new JPanel();
        panel.setBackground(new Color(30, 30, 30));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(450, 500));

        // Header

        JLabel title = new JLabel("Admin Dashboard");
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Administrator Controls");
        subtitle.setForeground(new Color(150, 150, 150));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // User Management
        JLabel userSection = createSectionTitle("USER MANAGEMENT");
        JButton manageUsersButton = createButton("Manage Users");

        // Content Management
        JLabel contentSection = createSectionTitle("CONTENT MANAGEMENT");
        JButton manageModulesButton = createButton("Manage Modules");
        JButton manageSubModulesButton = createButton("Manage Submodules");
        JButton manageQuestionsButton = createButton("Manage Questions");

        // System
        JLabel systemSection = createSectionTitle("SYSTEM");
        JButton statisticsButton = createButton("View Statistics");

        // Back
        JButton backButton = createButton("Back to Home");
        backButton.setBackground(new Color(60, 60, 60));

        //////////////////     TO DO!!!!!!!!!!    //////////
        // Button Actions
        manageUsersButton.addActionListener(e -> {
            System.out.println("Open User Management Page");
        });


        manageModulesButton.addActionListener(e -> {
            System.out.println("Open Module Management Page");
        });

        manageSubModulesButton.addActionListener(e -> {
            System.out.println("Open Submodule Management Page");
        });

        manageQuestionsButton.addActionListener(e -> {
            System.out.println("Open Question Management Page");
        });


        statisticsButton.addActionListener(e -> {
            System.out.println("Open Statistics Page");
        });


        backButton.addActionListener(e -> {
            frame.dispose();
            homeFrame.setVisible(true);
        });

        // Build Page
        panel.add(title);
        panel.add(Box.createVerticalStrut(5));
        panel.add(subtitle);
        panel.add(Box.createVerticalStrut(25));
        panel.add(userSection);
        panel.add(Box.createVerticalStrut(8));
        panel.add(manageUsersButton);
        panel.add(Box.createVerticalStrut(25));
        panel.add(contentSection);
        panel.add(Box.createVerticalStrut(8));
        panel.add(manageModulesButton);
        panel.add(Box.createVerticalStrut(8));
        panel.add(manageSubModulesButton);
        panel.add(Box.createVerticalStrut(8));
        panel.add(manageQuestionsButton);
        panel.add(Box.createVerticalStrut(25));
        panel.add(systemSection);
        panel.add(Box.createVerticalStrut(8));
        panel.add(statisticsButton);
        panel.add(Box.createVerticalStrut(30));
        panel.add(backButton);
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    // Creates matching dashboard buttons
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(0, 120, 255));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setMaximumSize(new Dimension(400, 45));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }


    // Creates gray section headers
    private JLabel createSectionTitle(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(new Color(150, 150, 150));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }
}
