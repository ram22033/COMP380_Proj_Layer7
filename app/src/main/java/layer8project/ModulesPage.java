package layer8project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;



/**
 * ModulesPage.java
 *
 * Provides the graphical interface that allows users to browse and
 * complete learning modules within Layer7.
 *
 * Users can:
 * - View unlocked and locked modules
 * - Navigate through submodules
 * - Read learning content
 * - Track learning progress
 *
 * Module information is retrieved through ModuleManager and displayed
 * using a tree-based navigation interface.
 *
 * @author Christopher Sparks
 * @since August 2026
 */

public class ModulesPage {


    private final User loggedInUser;
    private final UserProgress userProgress;
    private final ModuleManager moduleManager;
    private final UserManager userManager;
    private final ProgressRepository progressRepository;
    private JFrame frame;
    private JTree moduleTree;
    private JPanel contentPanel;
    private JLabel contentTitle;
    private JTextArea contentText;
    public ModulesPage(User loggedInUser, UserProgress userProgress, UserManager userManager, ModuleManager moduleManager, LearningModule selectedModule, ProgressRepository progressRepository, JFrame homeFrame) {
        this.loggedInUser = loggedInUser;
        this.userProgress = userProgress;
        this.moduleManager = moduleManager;
        this.userManager = userManager;
        this.progressRepository = progressRepository;

        // Frame
        frame = new JFrame("Layer 7 - Learning Modules");
        frame.setSize(1100, 700);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(30, 30, 30));

        ///////////////// Header ///////////////////////
        //Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(30,30,30));
        
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
        JButton profilebutton = new JButton("\uD83D\uDC64\uFE0E");
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
        
        // Build Header
        headerPanel.add(logoLabel, BorderLayout.WEST);
        headerPanel.add(searchPanel, BorderLayout.CENTER);
        headerPanel.add(rightPanel, BorderLayout.EAST);


        //////////////// END OF HEADER SECTION /////////////
        
        // Left Navigation
        JPanel navigationPanel = new JPanel(new BorderLayout());
        navigationPanel.setBackground(new Color(35, 35, 35));
        navigationPanel.setPreferredSize(new Dimension(250, 700));
        JLabel navigationTitle = new JLabel(" Learning Modules");
        navigationTitle.setForeground(Color.WHITE);
        navigationTitle.setFont(navigationTitle.getFont().deriveFont(Font.BOLD, 18f));
        navigationTitle.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 15, 12, 10));
        navigationPanel.add(navigationTitle, BorderLayout.NORTH);
        moduleTree = new JTree();
        moduleTree.setBackground(new Color(40, 40, 40));
        moduleTree.setForeground(Color.WHITE);
        moduleTree.setRowHeight(26);
        moduleTree.setFont(
        moduleTree.getFont().deriveFont(15f));
        moduleTree.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 8, 5, 8));
        JScrollPane treeScrollPane = new JScrollPane(moduleTree);
        // Change JTree selection colors
        JScrollBar verticalBar = treeScrollPane.getVerticalScrollBar();
        verticalBar.setBackground(new Color(30, 30, 30));
        verticalBar.setForeground(new Color(0, 120, 255));
        JScrollBar horizontalBar = treeScrollPane.getHorizontalScrollBar();
        horizontalBar.setBackground(new Color(30, 30, 30));
        horizontalBar.setForeground(new Color(0, 120, 255));
        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) moduleTree.getCellRenderer();
        renderer.setLeafIcon(null);
        renderer.setClosedIcon(null);
        renderer.setOpenIcon(null);
        renderer.setBackgroundSelectionColor(new Color(0, 120, 255));
        renderer.setTextSelectionColor(Color.YELLOW);
        renderer.setBackgroundNonSelectionColor(new Color(40, 40, 40));
        renderer.setTextNonSelectionColor(Color.WHITE);
        // Add to Panel
        navigationPanel.add(treeScrollPane, BorderLayout.CENTER);

        // Content Area
        contentPanel = new ScrollablePanel();
        contentPanel.setBackground(new Color(30, 30, 30));
        contentPanel.setLayout(new BoxLayout(contentPanel,BoxLayout.Y_AXIS));
        contentTitle = new JLabel("Select a submodule");
        contentTitle.setForeground(Color.WHITE);
        contentText =new JTextArea();
        contentText.setEditable(false);
        contentText.setLineWrap(true);
        contentText.setWrapStyleWord(true);
        contentText.setBackground(new Color(30, 30, 30));
        contentText.setForeground(Color.WHITE);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(contentTitle);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(contentText);
        JScrollPane contentScrollPane = new JScrollPane(contentPanel);
        contentScrollPane.setBorder(null);
        contentScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        contentScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        contentScrollPane.setBackground(new Color(30, 30, 30));
        contentScrollPane.getViewport().setBackground(new Color(30, 30, 30));

        // Split Pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, navigationPanel, contentScrollPane);
        splitPane.setDividerLocation(250);
        splitPane.setResizeWeight(0.25);

        // Bottom Navigation
        JButton backButton =new JButton("Back to Home");
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(0, 120, 255));
        backButton.addActionListener(e -> {
            frame.dispose();
            homeFrame.setVisible(true);
        });
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(30, 30, 30));
        bottomPanel.add(backButton);

        // Build Page
        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(splitPane, BorderLayout.CENTER);
        frame.add(bottomPanel,BorderLayout.SOUTH);
        loadModuleTree();
        setupTreeSelection();
        displayModule(selectedModule);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

}
        // Scroll Panel helper
        private static class ScrollablePanel extends JPanel implements javax.swing.Scrollable {
                @Override
                public Dimension getPreferredScrollableViewportSize() {
                        return getPreferredSize();
                }
                @Override
                public int getScrollableUnitIncrement(java.awt.Rectangle visibleRect, int orientation, int direction) {
                        return 16;
                }
                @Override
                public int getScrollableBlockIncrement(java.awt.Rectangle visibleRect, int orientation, int direction) {
                        return 100;
                }
                @Override
                public boolean getScrollableTracksViewportWidth() {
                        return true;
                }
                @Override
                public boolean getScrollableTracksViewportHeight() {
                        return false;
                }
        }

        ///////////////// HELPERS ///////////////////
        
        ///////// Loading Module Tree ///////
        private void loadModuleTree(){
                DefaultMutableTreeNode root = new DefaultMutableTreeNode("Modules");
                ArrayList<LearningModule> modules = moduleManager.getAllModules();
                for (LearningModule module : modules) {
                        String moduleStatus;
                        if (userProgress.isModuleCompleted(module.getModuleID())) {
                                moduleStatus = " ✓";
                        } else if (userProgress.isModuleUnlocked(module.getModuleID())) {
                                moduleStatus = "";
                        } else {
                                moduleStatus = " [LOCKED]";
                        }
                        ModuleTreeItem moduleItem = new ModuleTreeItem(module,moduleStatus);
                        DefaultMutableTreeNode moduleNode = new DefaultMutableTreeNode(moduleItem);
                        ArrayList<SubModule> subModules = moduleManager.getSubModules(module.getModuleID());
                        for (SubModule subModule : subModules) {
                                String subStatus;
                                if (userProgress.isSubModuleCompleted(subModule.getSubModuleID())) {
                                        subStatus = " ✓";
                                } else if (userProgress.isSubModuleUnlocked(subModule.getSubModuleID())) {
                                        subStatus = "";
                                } else {
                                        subStatus = " [LOCKED]";
                                }
                                SubModuleTreeItem item = new SubModuleTreeItem(module, subModule, subStatus);
                                DefaultMutableTreeNode subNode = new DefaultMutableTreeNode(item);
                                subNode.setUserObject(item);
                                moduleNode.add(subNode);
                        }
                        root.add(moduleNode);
                }
                DefaultTreeModel model = new DefaultTreeModel(root);
                moduleTree.setModel(model);
                moduleTree.setRootVisible(false);
        }
        
        /////// Create Module Items ////////
        private static class ModuleTreeItem {
                private final LearningModule module;
                private final String status;
                public ModuleTreeItem(LearningModule module, String status) {
                        this.module = module;
                        this.status = status;
                }
                public LearningModule getModule() {
                        return module;
                }
                @Override
                public String toString() {
                        return module.getTitle() + status;
                }
        }
        private static class SubModuleTreeItem {
                private final LearningModule module;
                private final SubModule subModule;
                private final String status;
                public SubModuleTreeItem(LearningModule module, SubModule subModule, String status) {
                        this.module = module;
                        this.subModule = subModule;
                        this.status = status;
                }
                public LearningModule getModule() {
                        return module;
                }
                public SubModule getSubModule() {
                        return subModule;
                }
                @Override
                public String toString() {
                        return subModule.getTitle() + status;
                }
        }

        //////// Setup Tree Selection ////////
        private void setupTreeSelection() {
                moduleTree.addTreeSelectionListener(e -> {
                        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)moduleTree.getLastSelectedPathComponent();
                        if (selectedNode == null) {
                                return;
                        }
                        Object selectedObject = selectedNode.getUserObject();
                        if (selectedObject instanceof ModuleTreeItem) {
                                ModuleTreeItem item = (ModuleTreeItem) selectedObject;
                                LearningModule module = item.getModule();
                                displayModule(module);
                                return;
                        }
                        if (!(selectedObject instanceof SubModuleTreeItem)) {
                                return;
                        }
                        SubModuleTreeItem item = (SubModuleTreeItem)selectedObject;
                        LearningModule module = item.getModule();
                        SubModule subModule = item.getSubModule();
                        
                        // Don't allow locked content
                        if (!userProgress.isSubModuleUnlocked(subModule.getSubModuleID())) {
                                displayLockedSubModule(module, subModule);
                                return;
                        }
                        displaySubModule(module, subModule);
                });
        }

        // Function to display submodule contents for both locked and unlocked submodules
        private void displaySubModule(LearningModule module, SubModule subModule) {
                contentPanel.removeAll();
                JLabel title = new JLabel(module.getTitle() + " - " + subModule.getTitle());
                title.setForeground(Color.WHITE);
                title.setFont(title.getFont().deriveFont(Font.BOLD,24f));
                JTextArea descriptionArea = new JTextArea(subModule.getDescription());
                descriptionArea.setEditable(false);
                descriptionArea.setLineWrap(true);
                descriptionArea.setWrapStyleWord(true);
                descriptionArea.setBackground(new Color(30, 30, 30));
                descriptionArea.setForeground(Color.WHITE);
                descriptionArea.setFont(descriptionArea.getFont().deriveFont(18f));
                descriptionArea.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20));
                descriptionArea.setColumns(50);
                descriptionArea.setRows(10);
                descriptionArea.setLineWrap(true);
                descriptionArea.setWrapStyleWord(true);
                descriptionArea.setAlignmentX(Component.LEFT_ALIGNMENT);
                contentPanel.add(Box.createVerticalStrut(20));
                contentPanel.add(title);
                contentPanel.add(Box.createVerticalStrut(20));
                contentPanel.add(descriptionArea);contentPanel.revalidate();
                contentPanel.repaint();

                // Adding questions
                ArrayList<Question> questions = moduleManager.getQuestionsBySubModuleId(subModule.getSubModuleID());
                JLabel questionsLabel = new JLabel("Questions");
                questionsLabel.setForeground(Color.WHITE);
                questionsLabel.setFont(questionsLabel.getFont().deriveFont(Font.BOLD,22f));
                
                contentPanel.add(questionsLabel);
                contentPanel.add(Box.createVerticalStrut(15));
                int questionNumber = 1;
                for (Question question : questions) {
                        JLabel questionLabel = new JLabel("<html>" + questionNumber + ". " + question.getPrompt() + "</html>");
                        questionLabel.setForeground(Color.WHITE);
                        questionLabel.setFont(questionLabel.getFont().deriveFont(16f));
                        contentPanel.add(questionLabel);
                        contentPanel.add(Box.createVerticalStrut(10));
                        questionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
       
                // ENTRY QUESTION
                
                if (question instanceof EntryQuestion) {
                        EntryQuestion entryQuestion = (EntryQuestion) question;
                        JTextField answerField = new JTextField();
                        answerField.setMaximumSize(new Dimension(400,35));
                        JButton submitButton = new JButton("Submit Answer");
                        submitButton.setAlignmentX(Component.LEFT_ALIGNMENT);
                        submitButton.addActionListener(e -> {
                                String answer = answerField.getText().trim();
                                if (answer.isEmpty()) {
                                        JOptionPane.showMessageDialog(frame,"Please enter an answer.");
                                        return;
                                }
                                boolean correct =moduleManager.submitAnswer(loggedInUser, userProgress, module, subModule, entryQuestion, answer);
                                if (correct) {
                                        JOptionPane.showMessageDialog(frame,"Correct!");
                                        // Refresh progress status
                                        loadModuleTree();
                                } 
                                else {
                                        JOptionPane.showMessageDialog(frame,"Incorrect. Try again.");
                                }
                        });
                        contentPanel.add(answerField);
                        contentPanel.add(Box.createVerticalStrut(8));
                        contentPanel.add(submitButton);
                }
                // MULTIPLE CHOICE QUESTION
                else if (question instanceof MultipleChoiceQuestion) {
                        MultipleChoiceQuestion mcQuestion = (MultipleChoiceQuestion) question;
                        ButtonGroup buttonGroup = new ButtonGroup();
                        ArrayList<JRadioButton> optionButtons = new ArrayList<>();
                        ArrayList<String> options = mcQuestion.getOptions();
                        for (int i = 0; i < options.size(); i++) {
                                JRadioButton optionButton = new JRadioButton(options.get(i));
                                optionButton.setBackground(new Color(30, 30, 30));
                                optionButton.setForeground(Color.WHITE);
                                optionButton.setAlignmentX(Component.LEFT_ALIGNMENT);
                                buttonGroup.add(optionButton);
                                optionButtons.add(optionButton);
                                contentPanel.add(optionButton);
                        }
                        JButton submitButton = new JButton("Submit Answer");
                        submitButton.addActionListener(e -> {
                                int selectedIndex = -1;
                                for (int i = 0; i < optionButtons.size(); i++) {
                                        if (optionButtons.get(i).isSelected()) {
                                                selectedIndex = i;
                                                break;
                                        }
                                }
                                if (selectedIndex == -1) {
                                        JOptionPane.showMessageDialog(frame,"Please select an answer.");
                                        return;
                                }
                                boolean correct =moduleManager.submitAnswer(loggedInUser, userProgress, module, subModule, mcQuestion, selectedIndex);
                                if (correct) {
                                        JOptionPane.showMessageDialog(frame, "Correct!");
                                        loadModuleTree();
                                } else {
                                        JOptionPane.showMessageDialog(frame, "Incorrect. Try again.");
                                }
                        });
                        contentPanel.add(Box.createVerticalStrut(8));
                        contentPanel.add(submitButton);
                 }
                 contentPanel.add(Box.createVerticalStrut(25));
                 questionNumber++;
                }
        }
        private void displayLockedSubModule(LearningModule module, SubModule subModule) {
                contentPanel.removeAll();
                JLabel title = new JLabel(subModule.getTitle());
                title.setForeground(Color.WHITE);
                title.setFont(title.getFont().deriveFont(Font.BOLD,24f));
                JLabel lockedLabel = new JLabel("This submodule is currently locked.");
                lockedLabel.setForeground(new Color(150, 150, 150));
                JLabel instructionLabel = new JLabel("Complete the previous content to unlock it.");
                instructionLabel.setForeground(new Color(150, 150, 150));
                contentPanel.add(Box.createVerticalStrut(20));
                contentPanel.add(title);
                contentPanel.add(Box.createVerticalStrut(20));
                contentPanel.add(lockedLabel);
                contentPanel.add(Box.createVerticalStrut(10));
                contentPanel.add(instructionLabel);
                contentPanel.revalidate();
                contentPanel.repaint();}

        // Rebuild Content Panel
        private void displayModule(LearningModule module) {
                // Add Bordering
                contentPanel.removeAll();
                contentPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 35, 20, 35));
                // Create Title
                JLabel title = new JLabel(module.getTitle());
                title.setMaximumSize(new Dimension(Integer.MAX_VALUE, title.getPreferredSize().height));
                title.setForeground(Color.WHITE);
                title.setFont(title.getFont().deriveFont(Font.BOLD, 24f));
                title.setHorizontalAlignment(SwingConstants.LEFT);
                title.setAlignmentX(Component.LEFT_ALIGNMENT);
                contentPanel.add(Box.createVerticalStrut(20));
                contentPanel.add(title);
                contentPanel.add(Box.createVerticalStrut(20));
                boolean unlocked = userProgress.isModuleUnlocked(module.getModuleID());
                if (!unlocked) {
                        JLabel lockedLabel = new JLabel("This module is currently locked. Purchase for $" + module.getUnlockPrice());
                        lockedLabel.setForeground(new Color(150, 150, 150));
                        contentPanel.add(lockedLabel);
                        contentPanel.add(Box.createVerticalStrut(20));
                        JButton unlockButton = new JButton("Purchase Module");
                        unlockButton.addActionListener(e -> {
                                boolean success = moduleManager.purchaseModule(loggedInUser, userProgress, module);
                                if (success) {
                                        JOptionPane.showMessageDialog(frame,"Module unlocked!");
                                        // Rebuild tree so [LOCKED] disappears
                                        loadModuleTree();
                                        // Refresh module display
                                        displayModule(module);
                                } else {
                                        JOptionPane.showMessageDialog(frame, "Unable to unlock module.");
                                }
                        });
                        contentPanel.add(unlockButton);
                } else {
                        JLabel unlockedLabel = new JLabel("This module is unlocked.");
                        unlockedLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, unlockedLabel.getPreferredSize().height));
                        unlockedLabel.setForeground(Color.WHITE);
                        unlockedLabel.setHorizontalAlignment(SwingConstants.LEFT);
                        unlockedLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                        JTextArea descriptionArea = new JTextArea("Description: " + module.getDescription());
                        descriptionArea.setEditable(false);
                        descriptionArea.setLineWrap(true);
                        descriptionArea.setWrapStyleWord(true);
                        descriptionArea.setBackground(new Color(30, 30, 30));
                        descriptionArea.setForeground(Color.WHITE);
                        descriptionArea.setFont(descriptionArea.getFont().deriveFont(Font.BOLD, 18f));
                        descriptionArea.setAlignmentX(Component.LEFT_ALIGNMENT);
                        JLabel instructionLabel = new JLabel("Select an unlocked submodule from the left.");
                        instructionLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, instructionLabel.getPreferredSize().height));
                        instructionLabel.setForeground(new Color(150, 150, 150));
                        instructionLabel.setHorizontalAlignment(SwingConstants.LEFT);
                        instructionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                        contentPanel.add(unlockedLabel);
                        contentPanel.add(Box.createVerticalStrut(15));
                        contentPanel.add(descriptionArea);
                        contentPanel.add(Box.createVerticalStrut(20));
                        contentPanel.add(instructionLabel);
                }
                contentPanel.revalidate();
                contentPanel.repaint();

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
         
}