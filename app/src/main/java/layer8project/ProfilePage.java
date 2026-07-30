package layer8project;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;


public class ProfilePage {

    public ProfilePage(User loggedInUser, UserProgress userProgress, UserManager userManager, JFrame homeFrame)  {

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
        
        // Edits
        JPanel statsPanel = new JPanel(new GridLayout(1, 2, 80, 0));
        statsPanel.setBackground(new Color(30, 30, 30));
        statsPanel.setMaximumSize(new Dimension(500, 140));
        
        // Left Column
        JPanel leftStats = new JPanel();
        leftStats.setLayout(new BoxLayout(leftStats, BoxLayout.Y_AXIS));
        leftStats.setBackground(new Color(30, 30, 30));
        

        JLabel usernameLabel = new JLabel(
        "<html><span style='color:#969696;'>Username:</span> " +
        "<span style='color:white;'>" + loggedInUser.getUsername() + "</span></html>"
        );
        
        JLabel roleLabel = new JLabel(
        "<html><span style='color:#969696;'>Role:</span> " +
        "<span style='color:white;'>" + loggedInUser.getRole() + "</span></html>"
        );

        JLabel balanceLabel = new JLabel(
        "<html><span style='color:#969696;'>Balance:</span> " +
        "<span style='color:white;'>" + loggedInUser.getBalance() + "</span></html>"
        );
        
        usernameLabel.setForeground(Color.WHITE);
        roleLabel.setForeground(Color.WHITE);
        balanceLabel.setForeground(Color.WHITE);

        usernameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        roleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        balanceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        leftStats.add(usernameLabel);
        leftStats.add(Box.createVerticalStrut(10));
        leftStats.add(roleLabel);
        leftStats.add(Box.createVerticalStrut(10));
        leftStats.add(balanceLabel);
        
        //Right Column
        JPanel rightStats = new JPanel();
        rightStats.setLayout(new BoxLayout(rightStats, BoxLayout.Y_AXIS));
        rightStats.setBackground(new Color(30, 30, 30));

        JLabel modulesCompleted = new JLabel(
        "<html><span style='color:#969696;'>Modules Completed:</span> " +
        "<span style='color:white;'>" + userProgress.getCompletedModuleCount() + "</span></html>"
        );
        
        JLabel subModulesCompleted = new JLabel(
        "<html><span style='color:#969696;'>Submodules Completed:</span> " +
        "<span style='color:white;'>" + userProgress.getCompletedSubModuleCount() + "</span></html>"
        );
        
        JLabel questionsCompleted = new JLabel(
        "<html><span style='color:#969696;'>Questions Answered:</span> " +
        "<span style='color:white;'>" + userProgress.getCompletedQuestionCount() + "</span></html>"
        );

        modulesCompleted.setForeground(Color.WHITE);
        subModulesCompleted.setForeground(Color.WHITE);
        questionsCompleted.setForeground(Color.WHITE);

        modulesCompleted.setAlignmentX(Component.LEFT_ALIGNMENT);
        subModulesCompleted.setAlignmentX(Component.LEFT_ALIGNMENT);
        questionsCompleted.setAlignmentX(Component.LEFT_ALIGNMENT);

        rightStats.add(modulesCompleted);
        rightStats.add(Box.createVerticalStrut(10));
        rightStats.add(subModulesCompleted);
        rightStats.add(Box.createVerticalStrut(10));
        rightStats.add(questionsCompleted);
        
        // Insert Left And Right Column
        statsPanel.add(leftStats);
        statsPanel.add(rightStats);
        
        //Progress Pannel
        JPanel progressPanel = new JPanel();
        progressPanel.setLayout(
        new BoxLayout(progressPanel, BoxLayout.Y_AXIS)
        );
        progressPanel.setBackground(new Color(30, 30, 30));
        progressPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel progressTitle =
        new JLabel("Overall Progress");

        progressTitle.setForeground(Color.WHITE);
        progressTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        int progressPercent = userProgress.getCompletedQuestionCount();
        
        JProgressBar progressBar =
        new JProgressBar(0, 100);

        progressBar.setValue(progressPercent);
        progressBar.setStringPainted(true);

        progressBar.setMaximumSize(
        new Dimension(400, 25)
        );

        progressBar.setAlignmentX(
        Component.CENTER_ALIGNMENT
        );
        
        // Add Prgoress Bar
        progressPanel.add(progressTitle);
        progressPanel.add(Box.createVerticalStrut(10));
        progressPanel.add(progressBar);
        
        // Forum
        formPanel.add(statsPanel);

        formPanel.add(
        Box.createVerticalStrut(35)
        );

        formPanel.add(progressPanel);

        formPanel.add(
        Box.createVerticalStrut(30)
        );

        formPanel.add(
        Box.createVerticalStrut(10)
        );



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
        
        formPanel.add(changePasswordButton);
    }
}