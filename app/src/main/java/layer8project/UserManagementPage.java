package layer8project;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class UserManagementPage {
    private final User loggedInAdmin;
    private final UserManager userManager;
    private JComboBox<String> userSelector;
    private JLabel usernameLabel;
    private JLabel roleLabel;
    private JLabel emailLabel;
    private JLabel balanceLabel;
    private User selectedUser;


    public UserManagementPage(User loggedInAdmin, UserManager userManager, JFrame adminFrame) {
        // Make sure only admins can access this page
        if (!loggedInAdmin.isAdmin()) {
            throw new SecurityException("Only administrators can access User Management.");
        }
        this.loggedInAdmin = loggedInAdmin;
        this.userManager = userManager;

        // Frame
        JFrame frame = new JFrame("Layer 7 - User Management");
        frame.setSize(800, 700);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(30, 30, 30));
        frame.setLayout(new GridBagLayout());

        // Main Panel
        JPanel panel = new JPanel();
        panel.setBackground(new Color(30, 30, 30));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(450, 620));

        // Header
        JLabel title = new JLabel("User Management");
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel subtitle = new JLabel("Select and manage user accounts");
        subtitle.setForeground(new Color(150, 150, 150));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // User Selector
        userSelector = new JComboBox<>();
        userSelector.setMaximumSize(new Dimension(400, 35));
        userSelector.setAlignmentX(Component.CENTER_ALIGNMENT);

        // User Information
        usernameLabel = createInfoLabel("Username", "-");
        roleLabel = createInfoLabel("Role", "-");
        emailLabel = createInfoLabel("Email", "-");
        balanceLabel = createInfoLabel("Balance", "-");

        // Buttons
        JButton addFundsButton = createButton("Add Funds");
        JButton changeEmailButton = createButton("Change Email");
        JButton resetPasswordButton = createButton("Reset Password");
        JButton promoteButton = createButton("Promote to Admin");
        JButton banButton = createButton("Ban User");
        JButton deleteButton = createButton("Delete User");
        JButton createUserButton = createButton("Create New User");
        JButton backButton = createButton("Back to Admin Dashboard");
        backButton.setBackground(new Color(60, 60, 60));

        // Load Users
        loadUsers();

        // User Selector Action
        userSelector.addActionListener(e -> {
            String username = (String)userSelector.getSelectedItem();
            if (username != null) {
                selectedUser = userManager.findUser(username);
                updateUserDisplay();
            }
        });


        // Automatically select first user
        if (userSelector.getItemCount() > 0) {
            userSelector.setSelectedIndex(0);
            String firstUsername = (String)userSelector.getSelectedItem();
            selectedUser = userManager.findUser(firstUsername);
            updateUserDisplay();
        }

        // Add Funds
        addFundsButton.addActionListener(e -> {
            if (selectedUser == null) {
                return;
            }
            String input = JOptionPane.showInputDialog(frame, "Amount to add:");
            if (input == null) {
                return;
            }

            try {
                double amount = Double.parseDouble(input);
                double newBalance = selectedUser.getBalance() + amount;
                boolean success = userManager.changeBalance(loggedInAdmin, selectedUser, newBalance);
                if (success) {
                    updateUserDisplay();
                    JOptionPane.showMessageDialog(frame, "Funds added successfully.");
                } else {
                    JOptionPane.showMessageDialog(frame, "Unable to update balance.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid number.");
            }
        });

        // Change Email
        changeEmailButton.addActionListener(e -> {
            if (selectedUser == null) {
                return;
            }
            String newEmail = JOptionPane.showInputDialog(frame, "Enter new email:", selectedUser.getEmail());
            if (newEmail == null) {
                return;
            }
            boolean success = userManager.changeEmail(loggedInAdmin, selectedUser, newEmail);
            if (success) {
                updateUserDisplay();
                JOptionPane.showMessageDialog(frame, "Email updated.");
            } else {
                JOptionPane.showMessageDialog(frame, "Unable to update email.");
            }
        });

        // Reset Password
        resetPasswordButton.addActionListener(e -> {
            if (selectedUser == null) {
                return;
            }
            String newPassword = JOptionPane.showInputDialog(frame, "Enter new password:");
            if (newPassword == null) {
                return;
            }
            boolean success = userManager.resetPassword(loggedInAdmin, selectedUser, newPassword);
            if (success) {
                JOptionPane.showMessageDialog(frame, "Password reset successfully.");
            } else {
                JOptionPane.showMessageDialog(frame, "Unable to reset password.");
            }
        });

        // Promote User
        promoteButton.addActionListener(e -> {
            if (selectedUser == null) {
                return;
            }
            boolean success = userManager.promoteToAdmin(loggedInAdmin, selectedUser);
            if (success) {
                updateUserDisplay();
                JOptionPane.showMessageDialog(frame, selectedUser.getUsername()+ " is now an administrator.");
            } else {
                JOptionPane.showMessageDialog(frame, "Unable to promote user.");
            }
        });

        // Ban User

        banButton.addActionListener(e -> {
            if (selectedUser == null) {
                return;
            }
            boolean success = userManager.banUser(loggedInAdmin, selectedUser);
            if (success) {
                updateUserDisplay();
                JOptionPane.showMessageDialog(frame, "User banned.");
            } else {
                JOptionPane.showMessageDialog(frame, "Unable to ban user.");
            }
        });

        // Delete User
        deleteButton.addActionListener(e -> {
            if (selectedUser == null) {
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(frame,"Delete user "+ selectedUser.getUsername()+ "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }
            boolean success = userManager.deleteUser(loggedInAdmin,selectedUser);
            if (success) {
                JOptionPane.showMessageDialog(frame,"User deleted.");
                selectedUser = null;
                loadUsers();
                if (userSelector.getItemCount() > 0) {
                    userSelector.setSelectedIndex(0);
                    selectedUser = userManager.findUser((String)userSelector.getSelectedItem());
                    updateUserDisplay();
                } else {
                    clearUserDisplay();
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Unable to delete user.");
            }
        });

        // Create New User
        createUserButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Create User page coming soon.");
        });

        // Back
        backButton.addActionListener(e -> {
            frame.dispose();
            adminFrame.setVisible(true);
        });

        // Build Page
        panel.add(title);
        panel.add(Box.createVerticalStrut(5));
        panel.add(subtitle);
        panel.add(Box.createVerticalStrut(20));
        panel.add(userSelector);
        panel.add(Box.createVerticalStrut(20));
        panel.add(usernameLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(roleLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(emailLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(balanceLabel);
        panel.add(Box.createVerticalStrut(25));
        panel.add(addFundsButton);
        panel.add(Box.createVerticalStrut(8));
        panel.add(changeEmailButton);
        panel.add(Box.createVerticalStrut(8));
        panel.add(resetPasswordButton);
        panel.add(Box.createVerticalStrut(8));
        panel.add(promoteButton);
        panel.add(Box.createVerticalStrut(8));
        panel.add(banButton);
        panel.add(Box.createVerticalStrut(8));
        panel.add(deleteButton);
        panel.add(Box.createVerticalStrut(20));
        panel.add(createUserButton);
        panel.add(Box.createVerticalStrut(20));
        panel.add(backButton);
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Button Helper
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(0, 120, 255));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setMaximumSize(new Dimension(400, 40));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }

    // Label Helper

    private JLabel createInfoLabel(String title,String value) {
        JLabel label = new JLabel(formatInfo(title, value));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    // Load User List
    private void loadUsers() {
        userSelector.removeAllItems();
        ArrayList<User> users = userManager.getAllUsers();
        for (User user : users) {
            userSelector.addItem(user.getUsername());
        }
    }

    // Update User Information

    private void updateUserDisplay() {
        if (selectedUser == null) {
            clearUserDisplay();
            return;
        }
        usernameLabel.setText(formatInfo("Username",selectedUser.getUsername()));
        roleLabel.setText(formatInfo("Role", selectedUser.getRole().name()));
        String email = selectedUser.getEmail();
        if (email == null || email.isEmpty()) {
            email = "None";
        }
        emailLabel.setText(formatInfo("Email", email));
        balanceLabel.setText(formatInfo("Balance", String.valueOf(selectedUser.getBalance())));
    }

    // Clear User Information

    private void clearUserDisplay() {
        usernameLabel.setText(formatInfo("Username","-"));
        roleLabel.setText(formatInfo("Role","-"));
        emailLabel.setText(formatInfo("Email","-"));
        balanceLabel.setText(formatInfo("Balance", "-"));
    }

    // HTML Formatting

    private String formatInfo(String title,String value) {
        return "<html>" + "<span style='color:#969696;'>" + title + ":</span> " + "<span style='color:white;'>"
                + value + "</span>" + "</html>";
    }

}
