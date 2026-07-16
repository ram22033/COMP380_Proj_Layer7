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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class ChangePasswordPage {

    public ChangePasswordPage(User loggedInUser, UserManager userManager) {

        JFrame frame = new JFrame("Change Password");
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

        // Current password field
        JLabel currentLabel = new JLabel("Current Password");
        currentLabel.setForeground(Color.WHITE);
        currentLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPasswordField currentField = new JPasswordField();
        currentField.setMaximumSize(new Dimension(400, 40));
        currentField.setAlignmentX(Component.LEFT_ALIGNMENT);

        // New password field
        JLabel newLabel = new JLabel("New Password");
        newLabel.setForeground(Color.WHITE);
        newLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPasswordField newField = new JPasswordField();
        newField.setMaximumSize(new Dimension(400, 40));
        newField.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Confirm new password field
        JLabel confirmLabel = new JLabel("Confirm New Password");
        confirmLabel.setForeground(Color.WHITE);
        confirmLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPasswordField confirmField = new JPasswordField();
        confirmField.setMaximumSize(new Dimension(400, 40));
        confirmField.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Submit button
        JButton submitButton = new JButton("Change Password");
        submitButton.setBackground(new Color(0, 120, 255));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.setMaximumSize(new Dimension(400, 45));
        submitButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        submitButton.addActionListener(e -> {
            String currentPassword = new String(currentField.getPassword());
            String newPassword = new String(newField.getPassword());
            String confirmPassword = new String(confirmField.getPassword());

            if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields are required.");
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(frame, "New passwords do not match.");
                return;
            }

            if (newPassword.length() < 6) {
                JOptionPane.showMessageDialog(frame, "New password must be at least 6 characters.");
                return;
            }

            boolean success = userManager.changeOwnPassword(loggedInUser, currentPassword, newPassword);
            if (success) {
                JOptionPane.showMessageDialog(frame, "Password changed successfully!");
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(frame, "Current password is incorrect.");
            }
        });

        // Add everything to form panel
        formPanel.add(currentLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(currentField);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(newLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(newField);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(confirmLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(confirmField);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(submitButton);

        panel.add(Box.createVerticalStrut(40));
        panel.add(formPanel);
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}