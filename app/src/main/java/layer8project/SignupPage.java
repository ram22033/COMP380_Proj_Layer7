package layer8project;

import org.mindrot.jbcrypt.BCrypt;
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
import javax.swing.JTextField;

public class SignupPage {

    public SignupPage(JFrame loginFrame) {

        JFrame frame = new JFrame("Sign up");
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
        formPanel.setMaximumSize(new Dimension(400, 350));

        ImageIcon logo = new ImageIcon(App.class.getResource("/Images/Logo.png"));
        Image scaled = logo.getImage().getScaledInstance(300, 150, Image.SCALE_SMOOTH);

        JLabel background = new JLabel(new ImageIcon(scaled));
        background.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(background);
        panel.add(Box.createVerticalStrut(5));
        panel.add(formPanel);
        panel.add(Box.createVerticalStrut(5));

        JLabel account = new JLabel("Create Account");
        account.setForeground(Color.WHITE);
        account.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField accountfield = new JTextField(25);
        accountfield.setMaximumSize(new Dimension(400, 40));
        accountfield.setAlignmentX(Component.LEFT_ALIGNMENT);
        accountfield.setForeground(Color.BLACK);

        JLabel password = new JLabel("Create Password");
        password.setForeground(Color.WHITE);
        password.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField passwordfield = new JTextField(25);
        passwordfield.setMaximumSize(new Dimension(400, 40));
        passwordfield.setAlignmentX(Component.LEFT_ALIGNMENT);
        passwordfield.setForeground(Color.BLACK);

        UserRepository repository = new UserRepository();
        UserManager userManager = new UserManager(repository);

        // Sign up button
        JButton signupButton = new JButton("Create Account");
        signupButton.setBackground(new Color(0, 120, 255));
        signupButton.setForeground(Color.WHITE);
        signupButton.setFocusPainted(false);
        signupButton.setMaximumSize(new Dimension(400, 45));
        signupButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        signupButton.setOpaque(true);
        signupButton.setBorderPainted(false);

        signupButton.addActionListener(e -> {
            String newUsername = accountfield.getText().trim();
            String newPassword = passwordfield.getText().trim();

            if (newUsername.isEmpty() || newPassword.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Username and password cannot be empty.");
                return;
            }

            if (userManager.findUser(newUsername) != null) {
                JOptionPane.showMessageDialog(frame, "Username already taken.");
                return;
            }

            String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
            User newUser = new User(newUsername, hashedPassword, Role.USER);
            repository.addUser(newUser);
            JOptionPane.showMessageDialog(frame, "Account created! You can now log in.");
            frame.dispose();
            loginFrame.setVisible(true);
        });

        // Back button
        JButton backButton = new JButton("Back to Login");
        backButton.setBackground(new Color(60, 60, 60));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setMaximumSize(new Dimension(400, 45));
        backButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        backButton.setOpaque(true);
        backButton.setBorderPainted(false); 

        backButton.addActionListener(e -> {
            frame.dispose();
            loginFrame.setVisible(true);
        });

        formPanel.add(account);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(accountfield);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(password);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(passwordfield);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(signupButton);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(backButton);

        frame.setLocationRelativeTo(null);
        frame.add(panel);
        frame.setVisible(true);
    }
}