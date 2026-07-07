package layer8project;

import javax.swing.*;
import java.awt.*;

//"import javax.swing.*;" is a prebuilt GUI tool that helps you create windows, buttons, and text boxes
//^ In short it helps with quality of life 
//import java.awt.*; is short for Abstract Window Tool and by adding this it allows the user to have a more basic framework of the GUI 
//import java.awt.Color


public class App {
    
    public static void main(String[] args) {
        JFrame frame = new JFrame ("Layer 8");
        // ^ "JFrame" helps us creates a window for our app based program
        // (title:"Layer 8"); is title of the app
        
        frame.setSize(800,600);
        //^ frame.setSize(width: 1000, height: 1000); Creates the width and height of the app. WE can either make it smaller or wider.

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //^"frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);"
        // This allows the user to close the app

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
        formPanel.setMaximumSize(new Dimension(300,250));


        ImageIcon logo = new ImageIcon(App.class.getResource("/Images/Logo.png"));
        Image scaled = logo.getImage().getScaledInstance(250,120,Image.SCALE_SMOOTH);
        // This loads the image saved under Logo.png and resizes it so its 250x120


        JLabel background = new JLabel(new ImageIcon(scaled));
        background.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(background);
        panel.add(Box.createVerticalStrut(10));
        //pane.add(background) puts the logo inside the panel that we added above 
        //createVerticalStrut creates a set amount of pixels of empty spaces


        panel.add(formPanel);
        panel.add(Box.createVerticalStrut(30));

        JLabel username = new JLabel("Username");
        username.setForeground(Color.WHITE);
        username.setAlignmentX(Component.LEFT_ALIGNMENT);
        //Creation of the username
        //setForeground makes the text white 
        //setAlignment centers the word "Username"

        JTextField usernamefield = new JTextField();
        usernamefield.setMaximumSize(new Dimension(300,40));
        usernamefield.setAlignmentX(Component.LEFT_ALIGNMENT);
        // This creates the space where the user types
        //setMaximumSize creates the size of the textbox

        formPanel.add(username);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(usernamefield);
        formPanel.add(Box.createVerticalStrut(20));
        // This adds the username and password into the panel

        JLabel password = new JLabel("Password");
        password.setForeground(Color.WHITE);
        password.setAlignmentX(Component.LEFT_ALIGNMENT);
        // The password label is created 
        //setForeground makes the text white and setAlignment centers it

        JPasswordField passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(300,40));
        passwordField.setAlignmentX(Component.LEFT_ALIGNMENT);
        //Creates the password box and rathern than showing the letters it comes out as *

        formPanel.add(password);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(passwordField);
        formPanel.add(Box.createVerticalStrut(10));
        // Same thing with the usernamefield, it adds on them on the panel

        JButton forgot = new JButton("Forgot Password?");
        forgot.setBorderPainted(false);
        forgot.setContentAreaFilled(false);
        forgot.setForeground(new Color(0,120,255));
        forgot.setAlignmentX(Component.LEFT_ALIGNMENT);
        //setBorderPainted(false); makes it so that the border around the forgot button is hidden
        //setContentAreaFilled(false); removes the background of the button

        formPanel.add(forgot);
        formPanel.add(Box.createVerticalStrut(30));
        //Creation of a box where the User can put the password for their account

        JButton ForgotUser = new JButton ("Forgot Username?");
        ForgotUser.setBorderPainted(false);
        ForgotUser.setContentAreaFilled(false);
        ForgotUser.setForeground(new Color(0,120,225));
        ForgotUser.setAlignmentX(Component.LEFT_ALIGNMENT);

        formPanel.add(ForgotUser);
        formPanel.add(Box.createVerticalStrut(30));

        JButton login = new JButton("LOGIN");
        login.setBackground(new Color(0,120,255));
        login.setForeground(Color.BLACK);
        login.setFocusPainted(false);
        login.setMaximumSize(new Dimension(300,45));
        login.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Creation of the LOGIN button
        // Along with the coloring, color of the text, background and restricts the sizing of the button

        panel.add(login);
        //Creation of a Login button

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        //frame.add(panel) adds the panel into the frame such as Logo, Username, Textbox etc
        //setLocationRelativeTo(null) centers the users screen
        //Makes the window visible

    }
    }
