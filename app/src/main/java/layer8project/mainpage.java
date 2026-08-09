package layer8project;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class mainpage   {

    public mainpage(User loggedInUser, UserProgress userProgress, UserManager userManager, ModuleManager moduleManager, ProgressRepository progressRepository, JFrame homeFrame) {

        JFrame frame = new JFrame("Layer 7");
        frame.setSize(800,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(30,30,30));
        frame.setLayout(new BorderLayout());


        //Main Panel
        JPanel panel = new JPanel();
        panel.setBackground(new Color(30,30,30));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        //Header Panel
        JPanel headerpanel = new JPanel(new BorderLayout());
        headerpanel.setBackground(new Color(30,30,30));
        
        //Center Content
        JPanel formPanel = new JPanel();
        formPanel.setBackground(new Color(30,30,30));
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.setMaximumSize(new Dimension(300,250));

        //Search Bar
        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200,30));
        searchField.setMaximumSize(new Dimension(220,30));

        searchField.setText("Search Modules...");
        searchField.setForeground(Color.GRAY);

        //Inside the Search Bar
        searchField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (searchField.getText().equals("Search Modules...")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("Search Modules...");
                    searchField.setForeground(Color.GRAY);
                }
            }
        });
        

        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(new Color(30,30,30));

        searchPanel.add(searchField);

        //Settings
        JButton settingbutton = new JButton("⚙");
        settingbutton.setFont(settingbutton.getFont().deriveFont(22f));
        settingbutton.setPreferredSize(new Dimension(65,45));
        settingbutton.setMargin(new java.awt.Insets(0, 0, 0, 0));

        settingbutton.setFocusPainted(false);
        settingbutton.setBorderPainted(false);
        settingbutton.setContentAreaFilled(false);
        settingbutton.setOpaque(false);
        settingbutton.setForeground(Color.WHITE);

        settingbutton.addActionListener(e -> {
            frame.dispose();
            homeFrame.setVisible(true);
        });

        //Logo
        ImageIcon logo = new ImageIcon(App.class.getResource("/Images/Logo.png"));
        Image scaled = logo.getImage().getScaledInstance(140,40,Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(scaled));

        //Profile Button
        JButton profilebutton = new JButton("👤");
        profilebutton.setFont(profilebutton.getFont().deriveFont(22f));
        profilebutton.setPreferredSize(new Dimension(65,45));
        profilebutton.setMargin(new java.awt.Insets(0, 0, 0, 0));

        profilebutton.setFocusPainted(false);
        profilebutton.setBorderPainted(false);
        profilebutton.setContentAreaFilled(false);
        profilebutton.setOpaque(false);
        profilebutton.setForeground(Color.WHITE);
        

        profilebutton.addActionListener(e -> {
            frame.setVisible(false);

            new ProfilePage(loggedInUser, userProgress, userManager, moduleManager, progressRepository, frame);
        }); 

        //rightPanel Buttons
        JPanel rightPanel = new JPanel(
            new FlowLayout(FlowLayout.RIGHT,5,0)
            );

        rightPanel.setOpaque(false);
        rightPanel.add(settingbutton);
        rightPanel.add(profilebutton);

        JPanel moduleGrid = new JPanel(new GridLayout(3,3,30,30));
        moduleGrid.setBackground(new Color (30,30,30));
        moduleGrid.setBorder( 
            javax.swing.BorderFactory.createEmptyBorder(50, 100, 50, 100)
        );

        // Getting list of modules
        ArrayList<LearningModule> modules = moduleManager.getAllModules();
        // Create Button for every module
        for (LearningModule module : modules) {
            JButton moduleButton = createModuleButton(module.getTitle());
            moduleButton.addActionListener(e -> {
                frame.setVisible(false);
                new ModulesPage(loggedInUser, userProgress, moduleManager, module, frame);
            });
            moduleGrid.add(moduleButton);
        }


        headerpanel.add(logoLabel,BorderLayout.WEST);
        headerpanel.add(searchPanel, BorderLayout.CENTER);
        headerpanel.add(rightPanel, BorderLayout.EAST);

        frame.add(headerpanel, BorderLayout.NORTH);
        frame.add(moduleGrid, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    private JButton createModuleButton(String text) { 
        JButton button = new JButton(text);

        button.setBackground(new Color (30,30,30));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setContentAreaFilled(true);

        button.setBorder(
            javax.swing.BorderFactory.createLineBorder( 
                Color.WHITE,
                3,
                true
            )
        );
        return button;

    }
}