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
import javax.swing.JPanel;

public class ModuleManagementPage {
    private final ModuleManager moduleManager;
    private JComboBox<String> moduleSelector;
    private JComboBox<String> subModuleSelector;
    private JLabel moduleTitleLabel;
    private JLabel moduleDescriptionLabel;
    private JLabel unlockPriceLabel;
    private JLabel rewardLabel;
    private JLabel subModuleTitleLabel;
    private JLabel subModuleDescriptionLabel;
    private JLabel subModuleOrderLabel;
    private LearningModule selectedModule;
    private SubModule selectedSubModule;

    public ModuleManagementPage(ModuleManager moduleManager, JFrame adminFrame) {
        this.moduleManager = moduleManager;

        // Frame
        JFrame frame = new JFrame("Layer 7 - Learning Content");
        frame.setSize(850, 750);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(30, 30, 30));
        frame.setLayout(new GridBagLayout());

        // Main Panel
        JPanel panel = new JPanel();
        panel.setBackground(new Color(30, 30, 30));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(500, 680));

        // Header
        JLabel title = new JLabel("Modules Manager");
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle =new JLabel("Manage modules, submodules, and questions");
        subtitle.setForeground(new Color(150, 150, 150));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Module Selector

        JLabel moduleSection =createSectionTitle("MODULE");
        moduleSelector =new JComboBox<>();
        moduleSelector.setMaximumSize(new Dimension(450, 35));
        moduleSelector.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Module Information

        moduleTitleLabel = createInfoLabel("Title", "-");
        moduleDescriptionLabel = createInfoLabel("Description", "-");
        unlockPriceLabel = createInfoLabel("Unlock Price", "-");
        rewardLabel = createInfoLabel("Completion Reward", "-");

        // Module Buttons
        JButton addModuleButton = createButton("Add Module");
        JButton editModuleButton = createButton("Edit Module");
        JButton deleteModuleButton = createButton("Delete Module");

        // Submodule Section
        JLabel subModuleSection = createSectionTitle("SUBMODULES");
        subModuleSelector = new JComboBox<>();
        subModuleSelector.setMaximumSize(new Dimension(450, 35));
        subModuleSelector.setAlignmentX(Component.CENTER_ALIGNMENT);
        subModuleTitleLabel = createInfoLabel("Title","-");
        subModuleDescriptionLabel = createInfoLabel("Description", "-");
        subModuleOrderLabel = createInfoLabel("Order", "-");

        // Submodule Buttons
        JButton addSubModuleButton = createButton("Add Submodule");
        JButton editSubModuleButton = createButton("Edit Submodule");
        JButton deleteSubModuleButton = createButton("Delete Submodule");
        JButton manageQuestionsButton = createButton("Manage Questions");

        // Back Button
        JButton backButton = createButton("Back to Admin Dashboard");
        backButton.setBackground(new Color(60, 60, 60));

        // Load Modules
        loadModules();

        // Module Selector Action
        moduleSelector.addActionListener(e -> {
                String moduleID = (String)moduleSelector.getSelectedItem();
                if (moduleID == null) {
                        return;
                }
                selectedModule = moduleManager.getModuleById(moduleID);
                updateModuleDisplay();
                loadSubModules();
        });

        // Automatically select first module
        if (moduleSelector.getItemCount() > 0) {
            moduleSelector.setSelectedIndex(0);
            String moduleID = (String) moduleSelector.getSelectedItem();
            selectedModule = moduleManager.getModuleById(moduleID);
            updateModuleDisplay();
            loadSubModules();
        }

        // Submodule Selector Action
        subModuleSelector.addActionListener(e -> {
            String subModuleID = (String)subModuleSelector.getSelectedItem();
            if (subModuleID == null || selectedModule == null) {
                return;
            }
            ArrayList<SubModule> subModules = moduleManager.getSubModules(selectedModule.getModuleID());
            selectedSubModule = null;
            for (SubModule subModule : subModules) {
                if (subModule.getSubModuleID().equals(subModuleID)) {
                        selectedSubModule = subModule;
                        break;
                }
            }
            updateSubModuleDisplay();
        });

        // Buttons - placeholders for now

        addModuleButton.addActionListener(e -> {
            System.out.println(
                    "Add Module clicked"
            );
        });


        editModuleButton.addActionListener(e -> {
            if (selectedModule == null) {
                return;
            }
            System.out.println("Edit Module: " + selectedModule.getModuleID());
        });


        deleteModuleButton.addActionListener(e -> {
            if (selectedModule == null) {
                return;
            }
            System.out.println("Delete Module: " + selectedModule.getModuleID()
            );
        });

        addSubModuleButton.addActionListener(e -> {
            if (selectedModule == null) {
                return;
            }
            System.out.println("Add Submodule to " + selectedModule.getModuleID());
        });


        editSubModuleButton.addActionListener(e -> {
            if (selectedSubModule == null) {
                return;
            }
            System.out.println("Edit Submodule: " + selectedSubModule.getSubModuleID());
        });

        deleteSubModuleButton.addActionListener(e -> {
                if (selectedSubModule == null) {
                        return;
                }
            System.out.println("Delete Submodule: " + selectedSubModule.getSubModuleID());
        });


        manageQuestionsButton.addActionListener(e -> {
            if (selectedSubModule == null) {
                return;
            }
            System.out.println("Manage questions for " + selectedSubModule.getSubModuleID());
        });

        backButton.addActionListener(e -> {
            frame.dispose();
            adminFrame.setVisible(true);
        });

        // Build Page
        panel.add(title);
        panel.add(Box.createVerticalStrut(5));
        panel.add(subtitle);
        panel.add(Box.createVerticalStrut(25));
        panel.add(moduleSection);
        panel.add(Box.createVerticalStrut(8));
        panel.add(moduleSelector);
        panel.add(Box.createVerticalStrut(15));
        panel.add(moduleTitleLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(moduleDescriptionLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(unlockPriceLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(rewardLabel);
        panel.add(Box.createVerticalStrut(15));
        panel.add(addModuleButton);
        panel.add(Box.createVerticalStrut(7));
        panel.add(editModuleButton);
        panel.add(Box.createVerticalStrut(7));
        panel.add(deleteModuleButton);
        panel.add(Box.createVerticalStrut(25));
        panel.add(subModuleSection);
        panel.add(Box.createVerticalStrut(8));
        panel.add(subModuleSelector);
        panel.add(Box.createVerticalStrut(15));
        panel.add(subModuleTitleLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(subModuleDescriptionLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(subModuleOrderLabel);
        panel.add(Box.createVerticalStrut(15));
        panel.add(addSubModuleButton);
        panel.add(Box.createVerticalStrut(7));
        panel.add(editSubModuleButton);
        panel.add(Box.createVerticalStrut(7));
        panel.add(deleteSubModuleButton);
        panel.add(Box.createVerticalStrut(15));
        panel.add(manageQuestionsButton);
        panel.add(Box.createVerticalStrut(25));
        panel.add(backButton);
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Load Modules
    private void loadModules() {
        moduleSelector.removeAllItems();
        ArrayList<LearningModule> modules = moduleManager.getAllModules();
        for (LearningModule module : modules) {
            moduleSelector.addItem(module.getModuleID());
        }
    }

    // Load Submodules
    private void loadSubModules() {
        subModuleSelector.removeAllItems();
        selectedSubModule = null;
        if (selectedModule == null) {
            clearSubModuleDisplay();
            return;
        }
        ArrayList<SubModule> subModules =
                moduleManager.getSubModules(selectedModule.getModuleID());
        for (SubModule subModule : subModules) {
            subModuleSelector.addItem(subModule.getSubModuleID());
        }
        if (subModuleSelector.getItemCount() > 0) {
            subModuleSelector.setSelectedIndex(0);
            String firstID = (String)subModuleSelector.getSelectedItem();
            for (SubModule subModule : subModules) {
                if (subModule.getSubModuleID().equals(firstID)) {
                        selectedSubModule = subModule;
                        break;
                }
            }
            updateSubModuleDisplay();
        } else {
            clearSubModuleDisplay();
        }
    }

    // Module Display

    private void updateModuleDisplay() {
        if (selectedModule == null) {
            clearModuleDisplay();
            return;
        }
        moduleTitleLabel.setText(formatInfo("Title",selectedModule.getTitle()));
        moduleDescriptionLabel.setText(formatInfo("Description", selectedModule.getDescription()));
        unlockPriceLabel.setText(formatInfo("Unlock Price", String.valueOf(selectedModule.getUnlockPrice())));
        rewardLabel.setText(formatInfo("Completion Reward", String.valueOf(selectedModule.getCompletionReward())));
    }

    // Submodule Display
    private void updateSubModuleDisplay() {
        if (selectedSubModule == null) {
            clearSubModuleDisplay();
            return;
        }

        subModuleTitleLabel.setText(formatInfo("Title", selectedSubModule.getTitle()));
        subModuleDescriptionLabel.setText(formatInfo("Description",selectedSubModule.getDescription()));
        subModuleOrderLabel.setText(formatInfo("Order", String.valueOf(selectedSubModule.getSubModuleOrder())));
    }


    private void clearModuleDisplay() {
        moduleTitleLabel.setText(formatInfo("Title", "-"));
        moduleDescriptionLabel.setText(formatInfo("Description", "-"));
        unlockPriceLabel.setText(formatInfo("Unlock Price", "-"));
        rewardLabel.setText(formatInfo("Completion Reward", "-"));
    }


    private void clearSubModuleDisplay() {
        subModuleTitleLabel.setText(formatInfo("Title", "-"));
        subModuleDescriptionLabel.setText(formatInfo("Description", "-"));
        subModuleOrderLabel.setText(formatInfo("Order", "-"));
    }

    // Button Helper
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(0, 120, 255));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setMaximumSize(new Dimension(450, 40));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }

    // Section Title Helper
    private JLabel createSectionTitle(String text) {
        JLabel label =new JLabel(text);
        label.setForeground(new Color(150, 150, 150));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    // Information Label Helper

    private JLabel createInfoLabel(String title, String value) {
        JLabel label = new JLabel(formatInfo(title, value));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private String formatInfo(String title, String value) {
        return "<html>" + "<span style='color:#969696;'>" + title + ":</span> " + "<span style='color:white;'>"
                + value + "</span>" + "</html>";
    }

}
