package layer8project;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class mainpage   {

    public mainpage(User loggedInUser, UserProgress userProgress, UserManager userManager, ModuleManager moduleManager, ProgressRepository progressRepository, JFrame homeFrame) {

        JFrame frame = new JFrame("Layer 7");
        frame.setSize(700,600);
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
        searchField.setPreferredSize(new Dimension(220,34));
        searchField.setMaximumSize(new Dimension(220,34));
        searchField.setText("Search Modules...");
        searchField.setForeground(Color.GRAY);
        searchField.setBorder(javax.swing.BorderFactory.createCompoundBorder(new RoundedBorder(15), javax.swing.BorderFactory.createEmptyBorder(2, 10, 2, 10)));
        searchField.setBackground(new Color(245, 245, 245));
        searchField.setFont(searchField.getFont().deriveFont(18f));

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
        settingbutton.setPreferredSize(new Dimension(45,45));
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
        Image scaled = logo.getImage().getScaledInstance(180,60,Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(scaled));

        //Profile Button
        JButton profilebutton = new JButton("👤");
        profilebutton.setFont(profilebutton.getFont().deriveFont(22f));
        profilebutton.setPreferredSize(new Dimension(45,45));
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

        // Balance Icon
        JLabel balanceLabel = new JLabel("$" + String.format("%.0f", loggedInUser.getBalance()));
        balanceLabel.setForeground(Color.WHITE);
        balanceLabel.setFont(balanceLabel.getFont().deriveFont(Font.BOLD, 40f));

        // Icon Panel (This was built to keep Top Right Icons all right next to each other)
        JPanel iconPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        iconPanel.setOpaque(false);
        settingbutton.setPreferredSize(new Dimension(38, 45));
        profilebutton.setPreferredSize(new Dimension(38, 45));
        iconPanel.add(settingbutton);
        iconPanel.add(profilebutton);

        // RightPanel Buttons (Displayed at the Top Right of the Page)
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,2,0));
        rightPanel.setOpaque(false);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.X_AXIS));
        rightPanel.add(balanceLabel);
        // For space between Balance and Icons
        rightPanel.add(Box.createHorizontalStrut(100));
        rightPanel.add(iconPanel);

        // Module Grid
        JPanel moduleGrid = new JPanel(new GridLayout(3,3,30,30));
        moduleGrid.setBackground(new Color (30,30,30));
        moduleGrid.setBorder( javax.swing.BorderFactory.createEmptyBorder(35, 60, 50, 60));

        // Getting list of modules
        ArrayList<LearningModule> modules = moduleManager.getAllModules();
        // Create Button for every module
        for (LearningModule module : modules) {
            JPanel moduleCard = createModuleCard(module, loggedInUser, userProgress, userManager, moduleManager, progressRepository, frame);
            moduleGrid.add(moduleCard);
        }

        // BUILDING THE HEADER
        headerpanel.add(logoLabel,BorderLayout.WEST);
        headerpanel.add(searchPanel, BorderLayout.CENTER);
        headerpanel.add(rightPanel, BorderLayout.EAST);

        // BUILDING THE WHOLE PAGE
        frame.add(headerpanel, BorderLayout.NORTH);
        frame.add(moduleGrid, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    //////////// HELPERS //////////////
    // CREATING MODULE BUTTONS
    private JPanel createModuleCard(LearningModule module, User loggedInUser, UserProgress userProgress, UserManager userManager, ModuleManager moduleManager, ProgressRepository progressRepository, JFrame frame) {
        // Build the card
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card,BoxLayout.Y_AXIS));
        card.setBackground(new Color(30, 30, 30));
        card.setBorder(new RoundedCardBorder(new Color(0, 120, 255), 3, 20));
        JLabel titleLabel = new JLabel(module.getTitle());
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 16f));
        titleLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        // Add the Title
        card.add(Box.createVerticalStrut(15));
        card.add(titleLabel);
        card.add(Box.createVerticalGlue());

        // Determine if the Module is unlocked and if completed or not
        boolean unlocked = userProgress.isModuleUnlocked(module.getModuleID());
        boolean completed = userProgress.isModuleFullyCompleted(module);

        // If Locked show a locked symbol
        if (!unlocked) {
            JLabel lockLabel = new JLabel("\uD83D\uDD12\uFE0E");
            lockLabel.setForeground(Color.YELLOW);
            lockLabel.setFont(lockLabel.getFont().deriveFont(Font.PLAIN, 22f));
            lockLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            JLabel priceLabel = new JLabel("$" + String.format("%.0f", module.getUnlockPrice()));
            priceLabel.setForeground(Color.WHITE);
            priceLabel.setFont(priceLabel.getFont().deriveFont(22f));
            priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            card.add(priceLabel);
            card.add(lockLabel);
        }
        // If unlocked then calculate the module progress and display it
        else {
            // Getting list of questions in module and counting them
            ArrayList<Question> moduleQuestions = new ArrayList<>();
            ArrayList<SubModule> subModules = moduleManager.getSubModules(module.getModuleID());
            for (SubModule subModule : subModules) {
                moduleQuestions.addAll(moduleManager.getQuestionsBySubModuleId(subModule.getSubModuleID()));
            }
            int progress = (int) Math.round(userProgress.getModuleProgressPercentage(moduleQuestions));
            JProgressBar progressBar = new JProgressBar(0, 100);
            progressBar.setValue(progress);
            progressBar.setFont(progressBar.getFont().deriveFont(22f));
            progressBar.setString(progress + "%");
            progressBar.setStringPainted(true);
            card.add(progressBar);

            JLabel unlockLabel = new JLabel("\uD83D\uDD13\uFE0E");
            unlockLabel.setForeground(Color.WHITE);
            unlockLabel.setFont(unlockLabel.getFont().deriveFont(Font.PLAIN, 22f));
            unlockLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            card.add(unlockLabel);
        }
        // If completed show a check mark
        if (completed) {
            JLabel completedLabel = new JLabel("✓");
            completedLabel.setFont(completedLabel.getFont().deriveFont(Font.BOLD,30f));
            completedLabel.setForeground(Color.YELLOW);
            completedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            card.add(completedLabel);
        }
        // Make it clickable
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                frame.setVisible(false);
                new ModulesPage(loggedInUser, userProgress, userManager, moduleManager, module, progressRepository, frame);
            }
        });
        return card;
    }
    
    // Sleeker SearchBar Look
    private static class RoundedBorder implements javax.swing.border.Border {
        private final int radius;
        RoundedBorder(int radius) {
            this.radius = radius;
        }
        @Override
        public java.awt.Insets getBorderInsets(Component c) {
            return new java.awt.Insets(radius / 2, radius / 2, radius / 2, radius / 2);
        }
        @Override
        public boolean isBorderOpaque() {
            return false;
        }
        @Override
        public void paintBorder(Component c, java.awt.Graphics g, int x, int y, int width, int height) {
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }

    // Rounder Buttons
    private static class RoundedCardBorder implements javax.swing.border.Border {
        private final Color color;
        private final int thickness;
        private final int radius;
        public RoundedCardBorder(Color color, int thickness, int radius) {
            this.color = color;
            this.thickness = thickness;
            this.radius = radius;
        }
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(thickness, thickness, thickness, thickness);
        }
        @Override
        public boolean isBorderOpaque() {
            return false;
        }
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(color);
            g2.setStroke(new BasicStroke(thickness));
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.drawRoundRect(x + thickness / 2, y + thickness / 2, width - thickness, height - thickness, radius, radius);
            g2.dispose();
        }
    }
}