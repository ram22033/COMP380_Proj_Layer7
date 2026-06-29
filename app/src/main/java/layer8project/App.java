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

        JLabel username = new JLabel("Username");
        JTextField usernamefield = new JTextField(15);
        //Creation of a box where the User can input their Username

        JLabel password = new JLabel("Password");
        JPasswordField passwordfield = new JPasswordField(15);
        //Creation of a box where the User can put the password for their account

        JButton button1 = new JButton("Forget Password");
        //Creation of a Forget Password button

        JButton button = new JButton("Login");
        //Creation of a Login button

        ImageIcon bg = new ImageIcon(App.class.getResource("/Images/Logo.png"));
        JLabel background = new JLabel(bg);


        background.setBounds(1,1,20, 20);
        frame.add(background);

        frame.setLayout(new GridBagLayout());
        JPanel panel = new JPanel();
        //A panel helps us add add objects without them overlapping through the use of JFrame
        panel.setLayout(new GridLayout( 3, 3, 1, 1));
        // The GridLayout helps create a grid inside out app which allows us to center the objects we created.

        

        //background Color
        panel.setBackground(Color.WHITE);
        panel.setBackground(new Color(30, 30 , 30));

        // This allows us to set the background of the entire window to dark
        frame.getContentPane().setBackground(new Color (30, 30 ,30));

        //Button colors and button background colors
        button.setBackground(new Color (0,120,255));
        button.setForeground(Color.BLACK);

        button1.setBackground(new Color (0,120,255));
        button1.setForeground(Color.BLACK);

        username.setForeground(Color.WHITE);
        password.setForeground(Color.WHITE);

        panel.add(username);
        panel.add(usernamefield);
        
        panel.add(password);
        panel.add(passwordfield);

        panel.add(button);
        panel.add(button1);

        frame.add(panel);
        frame.setVisible(true);
        //This adds the objects we made into a panel and by setting it visible we are able to see them.
        // "frame.setVisible(b:true)" allows the window itself to open.
        //If it was false then the window would be hidden and not reveal itself
    }
    }