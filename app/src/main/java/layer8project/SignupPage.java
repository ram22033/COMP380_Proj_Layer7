package layer8project;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SignupPage { 

    public SignupPage() {

        JFrame frame = new JFrame("Sign up");
        frame.setSize(800,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().setBackground(new Color(30,30,30));
        frame.setLayout(new GridBagLayout());
        // Creation of the background color along with the layout inside the frame

        JPanel panel = new JPanel();
        panel.setBackground(new Color(30,30,30));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        //The creation of the panel allows us to edit anything inside the frame
        // and the setLayout allows us to stack them vertically  

        JPanel formPanel = new JPanel();
        formPanel.setBackground(new Color(30,30,30));
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.setMaximumSize(new Dimension(400,250));  
        
        ImageIcon logo = new ImageIcon(App.class.getResource("/Images/Logo.png"));
        Image scaled = logo.getImage().getScaledInstance(300,150,Image.SCALE_SMOOTH);
        // This loads the image saved under Logo.png and resizes it so its 250x120


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
        accountfield.setMaximumSize(new Dimension(400,40));
        accountfield.setAlignmentX(Component.LEFT_ALIGNMENT);

        formPanel.add(account);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(accountfield);
        formPanel.add(Box.createVerticalStrut(10));

        JLabel password = new JLabel("Create Password");
        password.setForeground(Color.WHITE);
        password.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField passwordfield = new JTextField(25);
        passwordfield.setMaximumSize(new Dimension(400,25));
        passwordfield.setAlignmentX(Component.LEFT_ALIGNMENT);

        formPanel.add(password);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(passwordfield);
        formPanel.add(Box.createVerticalStrut(10));
        

    UserRepository repository = new UserRepository();
    UserManager userManager = new UserManager(repository);

    javax.swing.JButton signupButton = new javax.swing.JButton("Create Account");
    signupButton.setBackground(new Color(0, 120, 255));
    signupButton.setForeground(Color.WHITE);
    signupButton.setFocusPainted(false);
    signupButton.setMaximumSize(new Dimension(400, 45));
    signupButton.setAlignmentX(Component.LEFT_ALIGNMENT);

    signupButton.addActionListener(e -> {
    String newUsername = accountfield.getText().trim();
    String newPassword = passwordfield.getText().trim();

    if (newUsername.isEmpty() || newPassword.isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(frame, "Username and password cannot be empty.");
        return;
    }

    if (userManager.findUser(newUsername) != null) {
        javax.swing.JOptionPane.showMessageDialog(frame, "Username already taken.");
        return;
    }

    User newUser = new User(newUsername, newPassword, Role.USER);
    repository.addUser(newUser);
    javax.swing.JOptionPane.showMessageDialog(frame, "Account created! You can now log in.");
    frame.dispose();
});

    formPanel.add(signupButton);
    formPanel.add(Box.createVerticalStrut(10));
        frame.setLocationRelativeTo(null);
        frame.add(panel);
        frame.setVisible(true);


    }
}