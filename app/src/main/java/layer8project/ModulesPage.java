package layer8project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
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
    private JFrame frame;
    private JTree moduleTree;
    private JPanel contentPanel;
    private JLabel contentTitle;
    private JTextArea contentText;
    public ModulesPage(User loggedInUser, UserProgress userProgress, ModuleManager moduleManager, JFrame homeFrame) {
        this.loggedInUser = loggedInUser;
        this.userProgress = userProgress;
        this.moduleManager = moduleManager;

        // Frame
        frame = new JFrame("Layer 7 - Learning Modules");
        frame.setSize(1100, 700);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(30, 30, 30));

        // Left Navigation
        JPanel navigationPanel = new JPanel(new BorderLayout());
        navigationPanel.setBackground(new Color(35, 35, 35));
        navigationPanel.setPreferredSize(new Dimension(300, 700));
        JLabel navigationTitle = new JLabel(" Learning Modules");
        navigationTitle.setForeground(Color.WHITE);
        navigationPanel.add(navigationTitle, BorderLayout.NORTH);
        moduleTree = new JTree();
        moduleTree.setBackground(new Color(40, 40, 40));
        moduleTree.setForeground(Color.WHITE);
        JScrollPane treeScrollPane = new JScrollPane(moduleTree);
        navigationPanel.add(treeScrollPane, BorderLayout.CENTER);

        // Content Area
        contentPanel = new JPanel();
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

        // Split Pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, navigationPanel, contentScrollPane);
        splitPane.setDividerLocation(300);
        splitPane.setResizeWeight(0.25);

        // Bottom Navigation
        JButton backButton =new JButton("Back to Home");
        backButton.addActionListener(e -> {
            frame.dispose();
            homeFrame.setVisible(true);
        });
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(30, 30, 30));
        bottomPanel.add(backButton);

        // Build Page
        frame.add(splitPane, BorderLayout.CENTER);
        frame.add(bottomPanel,BorderLayout.SOUTH);
        loadModuleTree();
        setupTreeSelection();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
}

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
                                SubModuleTreeItem item = new SubModuleTreeItem(module, subModule);
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
                public SubModuleTreeItem(LearningModule module, SubModule subModule) {
                        this.module = module;
                        this.subModule = subModule;
                }
                public LearningModule getModule() {
                        return module;
                }
                public SubModule getSubModule() {
                        return subModule;
                }
                @Override
                public String toString() {
                        return subModule.getTitle();
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
        private void displaySubModule(LearningModule module,SubModule subModule) {
                contentTitle.setText(module.getTitle()+ " - " + subModule.getTitle());
                contentText.setText(subModule.getDescription());
                contentText.setCaretPosition(0);
        }
        private void displayLockedSubModule(LearningModule module, SubModule subModule) {
                contentTitle.setText(subModule.getTitle());
                contentText.setText("This submodule is currently locked.\n\n" + "Complete the previous content " + "to unlock it.");
        }

        // Rebuild Content Panel
        private void displayModule(LearningModule module) {
                contentPanel.removeAll();
                JLabel title = new JLabel(module.getTitle());
                title.setForeground(Color.WHITE);
                contentPanel.add(Box.createVerticalStrut(20));
                contentPanel.add(title);
                contentPanel.add(Box.createVerticalStrut(20));
                boolean unlocked = userProgress.isModuleUnlocked(module.getModuleID());
                if (!unlocked) {
                        JLabel lockedLabel = new JLabel("This module is currently locked.");
                        lockedLabel.setForeground(new Color(150, 150, 150));
                        contentPanel.add(lockedLabel);
                        contentPanel.add(Box.createVerticalStrut(20));
                        JButton unlockButton = new JButton("Unlock Module");
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
                        unlockedLabel.setForeground(Color.WHITE);
                        contentPanel.add(unlockedLabel);
                }
                contentPanel.revalidate();
                contentPanel.repaint();
        }
        
}