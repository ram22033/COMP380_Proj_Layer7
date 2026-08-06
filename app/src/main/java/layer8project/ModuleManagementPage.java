package layer8project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * ModuleManagementPage.java
 *
 * Provides the graphical user interface for administrators to manage
 * learning modules, submodules, and questions within the Layer7
 * learning management system.
 *
 * This page allows administrators to:
 * - Create, edit, and delete learning modules
 * - Create, edit, and delete submodules
 * - Create, edit, and delete entry and multiple-choice questions
 * - View and modify learning content stored in the database
 * 
 * The page communicates with ModuleManager to perform all
 * database operations and does not directly access repository classes.
 *
 * @author Christopher Sparks
 * @since August 2026
 */

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
    // For Questions Category
    private JComboBox<String> questionSelector;
    private JLabel questionTypeLabel;
    private JLabel questionPromptLabel;
    private JLabel questionAnswerLabel;
    private Question selectedQuestion;

    public ModuleManagementPage(ModuleManager moduleManager, JFrame adminFrame) {
        this.moduleManager = moduleManager;

        // Frame
        JFrame frame = new JFrame("Layer 7 - Learning Content");
        frame.setSize(850, 750);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(30, 30, 30));
        frame.setLayout(new BorderLayout());

        // Main Panel
        JPanel panel = new JPanel();
        panel.setBackground(new Color(30, 30, 30));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(600, 1050));

        // Header
        JLabel pagetitle = new JLabel("Modules Manager");
        pagetitle.setForeground(Color.WHITE);
        pagetitle.setAlignmentX(Component.CENTER_ALIGNMENT);

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

        // Question Section
        JLabel questionSection = createSectionTitle("QUESTIONS");
        questionSelector = new JComboBox<>();
        questionSelector.setMaximumSize(new Dimension(450, 35));
        questionSelector.setAlignmentX(Component.CENTER_ALIGNMENT);
        questionTypeLabel = createInfoLabel("Type", "-");
        questionPromptLabel = createInfoLabel("Prompt", "-");
        questionAnswerLabel = createInfoLabel("Correct Answer", "-");
        JButton addQuestionButton = createButton("Add Question");
        JButton editQuestionButton = createButton("Edit Question");
        JButton deleteQuestionButton = createButton("Delete Question");

        // Back Button
        JButton backButton = createButton("Back to Admin Dashboard");
        backButton.setBackground(new Color(60, 60, 60));

        // Load Modules
        loadModules();

        // Module Selector Action
        moduleSelector.addActionListener(e -> {
                String moduleID = (String)moduleSelector.getSelectedItem();
                if (moduleID == null) {
                    selectedModule = null;
                    clearModuleDisplay();
                    clearSubModuleDisplay();
                    clearQuestions();
                        return;
                }
                selectedModule = moduleManager.getModuleById(moduleID);
                updateModuleDisplay();
                loadSubModules();
        });

        // Automatically select first module
        if (moduleSelector.getItemCount() > 0) {
            moduleSelector.setSelectedIndex(0);
        }
        else {
            selectedModule = null;
            clearModuleDisplay();
            clearSubModuleDisplay();
            clearQuestions();
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
            loadQuestions();
        });

        // Buttons - placeholders for now
                // Module Buttons
        addModuleButton.addActionListener(e -> {
            JTextField moduleIDField = new JTextField();
            JTextField titleField = new JTextField();
            JTextArea descriptionArea = new JTextArea(5, 30);
            descriptionArea.setLineWrap(true);
            descriptionArea.setWrapStyleWord(true);
            JTextField unlockPriceField = new JTextField("0");
            JTextField completionRewardField = new JTextField("0");
            Object[] fields = {
                "Module ID:", moduleIDField, "Title:", titleField, "Description:", new JScrollPane(descriptionArea),
                "Unlock Price:", unlockPriceField, "Completion Reward:", completionRewardField
            };
            int result = JOptionPane.showConfirmDialog(frame, fields, "Add Module", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result != JOptionPane.OK_OPTION) {
                return;
            }
            String moduleID = moduleIDField.getText().trim();
            String title = titleField.getText().trim();
            String description = descriptionArea.getText().trim();
            if (moduleID.isEmpty() || title.isEmpty() || description.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Module ID, title, and description are required.");
                return;
            }
            if (moduleManager.getModuleById(moduleID) != null) {
                JOptionPane.showMessageDialog(frame, "That module ID already exists.");
                return;
            }
            try {
                double unlockPrice = Double.parseDouble(unlockPriceField.getText().trim());
                double completionReward = Double.parseDouble(completionRewardField.getText().trim());
                if (unlockPrice < 0 || completionReward < 0) {
                    JOptionPane.showMessageDialog(frame, "Price and reward cannot be negative.");
                    return;
                }
                LearningModule newModule = new LearningModule(moduleID, title, description, unlockPrice, completionReward);
                System.out.println("About to insert...");
                boolean success = moduleManager.addModule(newModule);
                System.out.println("Insert finished: " + success);
                if (success) {
                    JOptionPane.showMessageDialog(frame,"Module added successfully.");
                    refreshModulesAndSelect(moduleID);
                } else {
                    JOptionPane.showMessageDialog(frame, "Unable to add module.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame,"Unlock price and completion reward must be numbers.");
            }
        });


        editModuleButton.addActionListener(e -> {
            if (selectedModule == null) {
                JOptionPane.showMessageDialog(frame, "Select a module first.");
                return;
            }
            JTextField titleField =new JTextField(selectedModule.getTitle());
            JTextArea descriptionArea = new JTextArea(selectedModule.getDescription(), 5, 30);
            descriptionArea.setLineWrap(true);
            descriptionArea.setWrapStyleWord(true);
            JTextField unlockPriceField = new JTextField(String.valueOf(selectedModule.getUnlockPrice()));
            JTextField completionRewardField = new JTextField(String.valueOf(selectedModule.getCompletionReward()));
            Object[] fields = {
                "Module ID:", selectedModule.getModuleID(), "Title:", titleField, "Description:", new JScrollPane(descriptionArea), "Unlock Price:", unlockPriceField, "Completion Reward:", completionRewardField
            };
            int result = JOptionPane.showConfirmDialog(frame, fields, "Edit Module", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result != JOptionPane.OK_OPTION) {
                return;
            }
            String updatedTitle = titleField.getText().trim();
            String updatedDescription = descriptionArea.getText().trim();
            if (updatedTitle.isEmpty() || updatedDescription.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Title and description are required.");
                return;
            }
            try {
                double updatedUnlockPrice = Double.parseDouble(unlockPriceField.getText().trim());
                double updatedCompletionReward = Double.parseDouble(completionRewardField.getText().trim());
                if (updatedUnlockPrice < 0 || updatedCompletionReward < 0) {
                    JOptionPane.showMessageDialog(frame,"Price and reward cannot be negative.");
                    return;
                }
                LearningModule updatedModule = new LearningModule(selectedModule.getModuleID(), updatedTitle, updatedDescription, updatedUnlockPrice, updatedCompletionReward);
                boolean success = moduleManager.updateModule( updatedModule);
                if (success) {
                    JOptionPane.showMessageDialog(frame, "Module updated successfully.");
                    refreshModulesAndSelect(updatedModule.getModuleID());
                } else {
                    JOptionPane.showMessageDialog(frame, "Unable to update module.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Unlock price and completion reward must be valid numbers.");
            }
        });

        deleteModuleButton.addActionListener(e -> {
            if (selectedModule == null) {
                JOptionPane.showMessageDialog(frame, "Select a module first.");
                return;
            }
            String moduleID = selectedModule.getModuleID();
            String moduleTitle = selectedModule.getTitle();
            int confirmation = JOptionPane.showConfirmDialog(frame,"Are you sure you want to delete \""
                            + moduleTitle + "\"?\n\n" + "Its submodules and questions " + "will also be deleted.",
                    "Delete Module", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (confirmation != JOptionPane.YES_OPTION) {
                        return;
                    }
                    boolean success = moduleManager.deleteModule(moduleID);
                    if (success) {
                        JOptionPane.showMessageDialog(frame, "Module deleted successfully.");
                        selectedModule = null;
                        selectedSubModule = null;
                        selectedQuestion = null;
                        refreshAfterModuleDeletion();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Unable to delete module.");
                    }
                });

                // Submodule Buttons
        addSubModuleButton.addActionListener(e -> {
            if (selectedModule == null) {
                JOptionPane.showMessageDialog(frame,"Select a module first.");
                return;
            }
            JTextField subModuleIDField = new JTextField();
            JTextField titleField = new JTextField();
            JTextArea descriptionArea = new JTextArea(6, 30);
            descriptionArea.setLineWrap(true);
            descriptionArea.setWrapStyleWord(true);
            JTextField orderField = new JTextField(String.valueOf(subModuleSelector.getItemCount()));
            Object[] fields = {
                "Parent Module:", selectedModule.getTitle(), "Submodule ID:", subModuleIDField, "Title:", titleField, "Description:", new JScrollPane(descriptionArea), "Order:", orderField
            };
            int result = JOptionPane.showConfirmDialog(frame,fields,"Add Submodule", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result != JOptionPane.OK_OPTION) {
                return;
            }
            String subModuleID = subModuleIDField.getText().trim();
            String subModuleTitle = titleField.getText().trim();
            String description = descriptionArea.getText().trim();
            if (subModuleID.isEmpty() || subModuleTitle.isEmpty() || description.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Submodule ID, title, and description are required.");
                return;
            }
            if (moduleManager.getSubModuleById(subModuleID) != null) {
                JOptionPane.showMessageDialog(
                    frame,
                    "That submodule ID already exists."
                );
                return;
            }
            try {
                int subModuleOrder = Integer.parseInt(orderField.getText().trim());
                if (subModuleOrder < 0) {
                    JOptionPane.showMessageDialog(frame, "Submodule order cannot be negative.");
                    return;
                }
                SubModule newSubModule = new SubModule(subModuleID, subModuleTitle, description, subModuleOrder);
                boolean success = moduleManager.addSubModule(selectedModule.getModuleID(), newSubModule);
                if (success) {
                    JOptionPane.showMessageDialog(frame,"Submodule added successfully.");
                    refreshSubModulesAndSelect(subModuleID);
                } else {
                    JOptionPane.showMessageDialog(frame, "Unable to add submodule.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame,"Order must be a whole number.");
            }
        });

        editSubModuleButton.addActionListener(e -> {
            if (selectedSubModule == null) {
                JOptionPane.showMessageDialog(frame, "Select a submodule first.");
                return;
            }
            JTextField titleField = new JTextField(selectedSubModule.getTitle());
            JTextArea descriptionArea = new JTextArea(selectedSubModule.getDescription(),6,30);
            descriptionArea.setLineWrap(true);
            descriptionArea.setWrapStyleWord(true);
            JTextField orderField = new JTextField(String.valueOf(selectedSubModule.getSubModuleOrder()));
            Object[] fields = {
                "Submodule ID:", selectedSubModule.getSubModuleID(), "Parent Module:", selectedModule.getTitle(),
                "Title:", titleField, "Description:", new JScrollPane(descriptionArea), "Order:", orderField
            };
            int result = JOptionPane.showConfirmDialog(frame,fields,"Edit Submodule",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result != JOptionPane.OK_OPTION) {
                return;
            }
            String updatedTitle = titleField.getText().trim();
            String updatedDescription = descriptionArea.getText().trim();
            if (updatedTitle.isEmpty() || updatedDescription.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Title and description are required.");
                return;
            }
            try {
                int updatedOrder = Integer.parseInt(orderField.getText().trim());
                if (updatedOrder < 0) {
                    JOptionPane.showMessageDialog(frame, "Submodule order cannot be negative.");
                    return;
                }
                SubModule updatedSubModule = new SubModule(selectedSubModule.getSubModuleID(), updatedTitle, updatedDescription, updatedOrder);
                boolean success = moduleManager.updateSubModule(updatedSubModule);
                if (success) {
                    JOptionPane.showMessageDialog(frame,"Submodule updated successfully.");
                    refreshSubModulesAndSelect(updatedSubModule.getSubModuleID());
                } else {
                    JOptionPane.showMessageDialog(frame,"Unable to update submodule.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame,"Order must be a whole number.");
            }
        });

        deleteSubModuleButton.addActionListener(e -> {
            if (selectedSubModule == null) {
                JOptionPane.showMessageDialog(frame,"Select a submodule first.");
                return;
            }
            String subModuleID = selectedSubModule.getSubModuleID();
            String subModuleTitle = selectedSubModule.getTitle();
            int confirmation = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete \""+ subModuleTitle
                + "\"?\n\n" + "All questions inside this submodule " + "will also be deleted.", "Delete Submodule",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (confirmation != JOptionPane.YES_OPTION) {
                    return;
                }
                boolean success = moduleManager.deleteSubModule(subModuleID);
                if (success) {
                    JOptionPane.showMessageDialog(frame, "Submodule deleted successfully.");
                    selectedSubModule = null;
                    selectedQuestion = null;
                    refreshAfterSubModuleDeletion();
                } else {
                    JOptionPane.showMessageDialog(frame,"Unable to delete submodule.");
                }
            });

        // QUESTION Listener Actions
        // Maintain selectedQuestion based on selection in questionSelector
        questionSelector.addActionListener(e -> {
            String questionID = (String)questionSelector.getSelectedItem();
            if (questionID == null || selectedSubModule == null) {
                selectedQuestion = null;
                clearQuestionDisplay();
                return;
            }
            ArrayList<Question> questions =
            moduleManager.getQuestionsBySubModuleId(selectedSubModule.getSubModuleID());
            selectedQuestion = null;
            for (Question question : questions) {
                if (question.getID().equals(questionID)) {
                    selectedQuestion = question;
                    break;
                }
            }
            updateQuestionDisplay();
        });
        addQuestionButton.addActionListener(e -> {
            if (selectedSubModule == null) {
                JOptionPane.showMessageDialog(frame,"Select a submodule first.");
                return;
            }
            String[] questionTypes = {
                "Entry Question","Multiple Choice"
            };
            String selectedType =(String) JOptionPane.showInputDialog(frame, "Select the type of question:",
                "Add Question", JOptionPane.PLAIN_MESSAGE, null, questionTypes, questionTypes[0]);
                // User clicked Cancel
                if (selectedType == null) {
                    return;
                }
                if (selectedType.equals("Entry Question")) { // User clicks "Entry Question"
                    showAddEntryQuestionDialog(frame); //Scroll down to the method definition for showAddEntryQuestionDialog
                } else {
                    showAddMultipleChoiceQuestionDialog(frame); // User clicks "Multiple Choice"
                    // Scroll down to the method definition for showAddMultipleChoiceQuestionDialog
                }
            });
        editQuestionButton.addActionListener(e -> {
            if (selectedQuestion == null) {
                JOptionPane.showMessageDialog(frame,"Select a question first.");
                return;
            }
            // Determine which type of question is selected and show the appropriate edit dialog
            if (selectedQuestion instanceof EntryQuestion) {
                showEditEntryQuestionDialog(frame,(EntryQuestion) selectedQuestion);
            } else if (selectedQuestion instanceof MultipleChoiceQuestion) {
                showEditMultipleChoiceQuestionDialog(frame, (MultipleChoiceQuestion) selectedQuestion);
            } else {
                JOptionPane.showMessageDialog(frame,"Unknown question type.");}
            });
        deleteQuestionButton.addActionListener(e -> {
            if (selectedQuestion == null) {
                JOptionPane.showMessageDialog(frame, "Select a question first.");
                return;
            }
            String questionID = selectedQuestion.getID();
            String prompt = selectedQuestion.getPrompt();
            int confirmation = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this question?\n\n"
                + prompt, "Delete Question", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (confirmation != JOptionPane.YES_OPTION) {
                return;
            }
            boolean success = moduleManager.deleteQuestion(questionID);
            if (success) {
                JOptionPane.showMessageDialog(frame, "Question deleted successfully.");
                selectedQuestion = null;
                refreshAfterQuestionDeletion();
            } else {
                JOptionPane.showMessageDialog(frame, "Unable to delete question.");
            }
        });

        // Back Button Action
        backButton.addActionListener(e -> {
            frame.dispose();
            adminFrame.setVisible(true);
        });

        // Build Page
        panel.add(pagetitle);
        panel.add(Box.createVerticalStrut(5));
        panel.add(subtitle);
        panel.add(Box.createVerticalStrut(25));
        // Module Section
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
        // Submodule Section
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
        //////////////////// QUESTIONS SECTION ///////////////////////
        panel.add(Box.createVerticalStrut(25));
        panel.add(questionSection);
        panel.add(Box.createVerticalStrut(8));
        panel.add(questionSelector);
        panel.add(Box.createVerticalStrut(15));
        panel.add(questionTypeLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(questionPromptLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(questionAnswerLabel);
        panel.add(Box.createVerticalStrut(15));
        panel.add(addQuestionButton);
        panel.add(Box.createVerticalStrut(7));
        panel.add(editQuestionButton);
        panel.add(Box.createVerticalStrut(7));
        panel.add(deleteQuestionButton);
        panel.add(Box.createVerticalStrut(25));
        // Back Button
        panel.add(backButton);

        // Finalize Frame
        JScrollPane scrollPane = new JScrollPane(panel); // Wrap the main panel in a scroll pane for space management
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        frame.add(scrollPane, BorderLayout.CENTER);
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
            loadQuestions();
        } else {
            clearSubModuleDisplay();
            clearQuestions();
        }
    }
    /////////////////////// HELPER METHODS ///////////////////////
    
    // Module Display Helpers
    private void refreshModulesAndSelect(String moduleID) {
        loadModules();
        moduleSelector.setSelectedItem(moduleID);
        selectedModule = moduleManager.getModuleById(moduleID);
        updateModuleDisplay();
        loadSubModules();
    }
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
    private void clearModuleDisplay() {
        moduleTitleLabel.setText(formatInfo("Title", "-"));
        moduleDescriptionLabel.setText(formatInfo("Description", "-"));
        unlockPriceLabel.setText(formatInfo("Unlock Price", "-"));
        rewardLabel.setText(formatInfo("Completion Reward", "-"));
    }
    private void refreshAfterModuleDeletion() {
        // Reload all module IDs
        loadModules();
        if (moduleSelector.getItemCount() > 0) {
            // This triggers the moduleSelector listener
            moduleSelector.setSelectedIndex(0);
        } 
        else {
            selectedModule = null;
            selectedSubModule = null;
            selectedQuestion = null;
            clearModuleDisplay();
            subModuleSelector.removeAllItems();
            clearSubModuleDisplay();
            questionSelector.removeAllItems();
            clearQuestionDisplay();
        }
    }

    // Submodule Display Helpers
    private void updateSubModuleDisplay() {
        if (selectedSubModule == null) {
            clearSubModuleDisplay();
            return;
        }
        subModuleTitleLabel.setText(formatInfo("Title", selectedSubModule.getTitle()));
        subModuleDescriptionLabel.setText(formatInfo("Description",selectedSubModule.getDescription()));
        subModuleOrderLabel.setText(formatInfo("Order", String.valueOf(selectedSubModule.getSubModuleOrder())));
    }
    private void refreshSubModulesAndSelect(String subModuleID) {
        loadSubModules();
        subModuleSelector.setSelectedItem(subModuleID);
        selectedSubModule = moduleManager.getSubModuleById(subModuleID);
        updateSubModuleDisplay();
        loadQuestions();
    }
    private void clearSubModuleDisplay() {
        subModuleTitleLabel.setText(formatInfo("Title", "-"));
        subModuleDescriptionLabel.setText(formatInfo("Description", "-"));
        subModuleOrderLabel.setText(formatInfo("Order", "-"));
    }
    private void refreshAfterSubModuleDeletion() {
        loadSubModules();
        if (subModuleSelector.getItemCount() > 0) {
            // Triggers the subModuleSelector listener
            subModuleSelector.setSelectedIndex(0);
        } else {
            selectedSubModule = null;
            selectedQuestion = null;
            clearSubModuleDisplay();
            questionSelector.removeAllItems();
            clearQuestionDisplay();
        }
    }
    
    // Question Display Helpers
    private void loadQuestions() {
        questionSelector.removeAllItems();
        selectedQuestion = null;
        if (selectedSubModule == null) {
            clearQuestionDisplay();
            return;
        }
        ArrayList<Question> questions = moduleManager.getQuestionsBySubModuleId(selectedSubModule.getSubModuleID());
        if (questions == null || questions.isEmpty()) {
            clearQuestionDisplay();
            return;
        }
        for (Question question : questions) {
            questionSelector.addItem(
            question.getID());
        }
        questionSelector.setSelectedIndex(0);
        String firstQuestionID = (String)questionSelector.getSelectedItem();
        for (Question question : questions) {
            if (question.getID().equals(firstQuestionID)) {
                selectedQuestion = question;
                break;
            }
        }
        updateQuestionDisplay();
    }
    
    private void clearQuestions() {
        questionSelector.removeAllItems();
        selectedQuestion = null;
        clearQuestionDisplay();
    }  

    private void updateQuestionDisplay() {
        if (selectedQuestion == null) {
            clearQuestionDisplay();
            return;
        }
        String questionType;
        String correctAnswer;
        if (selectedQuestion instanceof EntryQuestion) {
            EntryQuestion entryQuestion = (EntryQuestion) selectedQuestion;
            questionType = "Entry Question";
            correctAnswer = entryQuestion.getCorrectAnswer();
        } else if (selectedQuestion instanceof MultipleChoiceQuestion) {
            MultipleChoiceQuestion multipleChoice = (MultipleChoiceQuestion) selectedQuestion;
            questionType = "Multiple Choice";
            int correctIndex = multipleChoice.getCorrectAnswerIndex();
            ArrayList<String> options = multipleChoice.getOptions();
            if (correctIndex >= 0 && correctIndex < options.size()) {
                correctAnswer = options.get(correctIndex);
            } else {
                correctAnswer = "No correct answer selected";
            }
        } else {
            questionType = "Unknown";
            correctAnswer = "-";
        }
        questionTypeLabel.setText(formatInfo("Type", questionType));
        questionPromptLabel.setText(formatInfo("Prompt", selectedQuestion.getPrompt()));
        questionAnswerLabel.setText(formatInfo("Correct Answer",correctAnswer));
    }

    private void clearQuestionDisplay() {
        questionTypeLabel.setText(formatInfo("Type", "-"));
        questionPromptLabel.setText(formatInfo("Prompt", "-"));
        questionAnswerLabel.setText(formatInfo("Correct Answer", "-"));
    }

    private void showAddEntryQuestionDialog(JFrame frame) {
        JTextField questionIDField = new JTextField();
        JTextArea promptArea = new JTextArea(4, 30);
        promptArea.setLineWrap(true);
        promptArea.setWrapStyleWord(true);
        JTextField correctAnswerField = new JTextField();
        Object[] fields = {
            "Question ID:", questionIDField, "Prompt:", new JScrollPane(promptArea), "Correct Answer:", correctAnswerField
        };
        int result = JOptionPane.showConfirmDialog(frame, fields, "Add Entry Question", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) {
            return;
        }
        String questionID = questionIDField.getText().trim();
        String prompt = promptArea.getText().trim();
        String correctAnswer = correctAnswerField.getText().trim();
        if (questionID.isEmpty() || prompt.isEmpty() || correctAnswer.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Question ID, prompt, and correct answer are required.");
            return;
        }
        if (moduleManager.getQuestionById(questionID) != null) {
            JOptionPane.showMessageDialog(frame,"That question ID already exists.");
            return;
        }
        EntryQuestion newQuestion = new EntryQuestion(questionID,prompt, correctAnswer);
        boolean success = moduleManager.addEntryQuestion(newQuestion, selectedSubModule.getSubModuleID());
        if (success) {
            JOptionPane.showMessageDialog(frame,"Entry question added successfully.");
            refreshQuestionsAndSelect(questionID);
        } else {
            JOptionPane.showMessageDialog(frame,"Unable to add entry question.");
        }
    }

    private void showAddMultipleChoiceQuestionDialog(JFrame frame) {
        JTextField questionIDField = new JTextField();
        JTextArea promptArea = new JTextArea(4, 30);
        promptArea.setLineWrap(true);
        promptArea.setWrapStyleWord(true);
        // Holds the option text fields
        ArrayList<JTextField> optionFields = new ArrayList<>();
        // Panel containing all option rows
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        JComboBox<String> correctAnswerSelector = new JComboBox<>();
        // Start with two options
        addOptionRow(optionsPanel, optionFields, correctAnswerSelector);
        addOptionRow(optionsPanel,optionFields,correctAnswerSelector);
        JButton addOptionButton = new JButton("+ Add Option");
        addOptionButton.addActionListener(e -> {
            addOptionRow(optionsPanel,optionFields,correctAnswerSelector);
            optionsPanel.revalidate();
            optionsPanel.repaint();
        });
        JScrollPane optionsScrollPane = new JScrollPane(optionsPanel);
        optionsScrollPane.setPreferredSize(new Dimension(400, 180));
        optionsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        optionsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JPanel optionContainer = new JPanel();
        optionContainer.setLayout(new BoxLayout(optionContainer,BoxLayout.Y_AXIS));
        optionContainer.add(optionsScrollPane);
        optionContainer.add(Box.createVerticalStrut(5));
        optionContainer.add(addOptionButton);
        Object[] fields = {
            "Question ID:", questionIDField, "Prompt:", new JScrollPane(promptArea), "Options:", optionContainer,
                "Correct Answer:", correctAnswerSelector
            };
        int result = JOptionPane.showConfirmDialog(frame, fields, "Add Multiple Choice Question", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) {
            return;
        }
        String questionID = questionIDField.getText().trim();
        String prompt = promptArea.getText().trim();
        if (questionID.isBlank() || prompt.isBlank()) {
            JOptionPane.showMessageDialog(frame, "Question ID and prompt are required.");
            return;
        }
        if (moduleManager.getQuestionById(questionID) != null) {
            JOptionPane.showMessageDialog(frame, "That question ID already exists.");
            return;
        }
        ArrayList<String> options = new ArrayList<>();
        // Every visible option must contain text
        for (JTextField optionField : optionFields) {
            String option = optionField.getText().trim();
            if (option.isBlank()) {
                JOptionPane.showMessageDialog(frame, "All option fields must contain text.");
                return;
            }
            options.add(option);
        }
        if (options.size() < 1) {
            JOptionPane.showMessageDialog(frame, "A multiple-choice question must have at least one option.");
            return;
        }
        int correctAnswerIndex = correctAnswerSelector.getSelectedIndex();
        if (correctAnswerIndex < 0 || correctAnswerIndex >= options.size()) {
            JOptionPane.showMessageDialog(frame, "Select a valid correct answer.");
            return;
        }
        MultipleChoiceQuestion newQuestion = new MultipleChoiceQuestion(questionID, prompt, options, correctAnswerIndex);
        boolean success = moduleManager.addMultipleChoiceQuestion(newQuestion, selectedSubModule.getSubModuleID());
        if (success) {
            JOptionPane.showMessageDialog(frame,"Multiple-choice question added successfully.");
            refreshQuestionsAndSelect(questionID);
        } else {
            JOptionPane.showMessageDialog(frame,"Unable to add multiple-choice question.");
        }
    }

    private void addOptionRow(JPanel optionsPanel, ArrayList<JTextField> optionFields, JComboBox<String> correctAnswerSelector) {
        JPanel optionRow = new JPanel();
        optionRow.setLayout(new BoxLayout(optionRow,BoxLayout.X_AXIS));
        JTextField optionField = new JTextField();
        optionField.setMaximumSize(new Dimension(300, 35));
        JButton removeButton = new JButton("Remove");
        optionFields.add(optionField);
        removeButton.addActionListener(e -> {
            // Always require at least one choices
            if (optionFields.size() <= 1) {
                JOptionPane.showMessageDialog(optionsPanel, "A question must have at least two options.");
                return;
            }
            optionFields.remove(optionField);
            optionsPanel.remove(optionRow);
            refreshCorrectAnswerSelector(optionFields,correctAnswerSelector);
            optionsPanel.revalidate();
            optionsPanel.repaint();
        });
        optionRow.add(optionField);
        optionRow.add(Box.createHorizontalStrut(8));
        optionRow.add(removeButton);
        optionRow.setMaximumSize(new Dimension(390, 40));
        optionsPanel.add(optionRow);
        optionsPanel.add(Box.createVerticalStrut(5));
        refreshCorrectAnswerSelector(optionFields,correctAnswerSelector);
    }

    private void refreshCorrectAnswerSelector(ArrayList<JTextField> optionFields, JComboBox<String> correctAnswerSelector) {
        int previousSelection = correctAnswerSelector.getSelectedIndex();
        correctAnswerSelector.removeAllItems();
        for (int i = 0; i < optionFields.size(); i++) {
            correctAnswerSelector.addItem("Option " + (i + 1));
        }
        if (previousSelection >= 0 && previousSelection < optionFields.size()) {
            correctAnswerSelector.setSelectedIndex(previousSelection);
        } else if (!optionFields.isEmpty()) {
            correctAnswerSelector.setSelectedIndex(0);
        }
    }

    private void refreshQuestionsAndSelect(String questionID) {
        loadQuestions();
        questionSelector.setSelectedItem(questionID);
        selectedQuestion = moduleManager.getQuestionById(questionID);
        updateQuestionDisplay();
    }

    private void showEditEntryQuestionDialog(JFrame frame, EntryQuestion question) {
        JTextArea promptArea = new JTextArea(question.getPrompt(),4,30);
        promptArea.setLineWrap(true);
        promptArea.setWrapStyleWord(true);
        JTextField correctAnswerField = new JTextField(question.getCorrectAnswer());
        Object[] fields = {
            "Question ID:", question.getID(), "Prompt:", new JScrollPane(promptArea), "Correct Answer:", correctAnswerField
        };
        int result = JOptionPane.showConfirmDialog(frame, fields, "Edit Entry Question", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) {
            return;
        }
        String updatedPrompt = promptArea.getText().trim();
        String updatedAnswer = correctAnswerField.getText().trim();
        if (updatedPrompt.isBlank() || updatedAnswer.isBlank()) {
            JOptionPane.showMessageDialog(frame, "Prompt and correct answer are required.");
            return;
        }
        EntryQuestion updatedQuestion = new EntryQuestion(question.getID(), updatedPrompt, updatedAnswer);
        boolean success = moduleManager.updateEntryQuestion(updatedQuestion);
        if (success) {
            JOptionPane.showMessageDialog(frame, "Entry question updated successfully.");
            refreshQuestionsAndSelect(updatedQuestion.getID());
        } else {
            JOptionPane.showMessageDialog(frame, "Unable to update entry question.");
        }
    }

    private void showEditMultipleChoiceQuestionDialog(JFrame frame, MultipleChoiceQuestion question) {
        JTextArea promptArea = new JTextArea(question.getPrompt(), 4, 30);
        promptArea.setLineWrap(true);
        promptArea.setWrapStyleWord(true);
        ArrayList<JTextField> optionFields = new ArrayList<>();
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel,BoxLayout.Y_AXIS));
        JComboBox<String> correctAnswerSelector = new JComboBox<>();
        // Load the existing options
        for (String option : question.getOptions()) {
            addOptionRow(optionsPanel,optionFields,correctAnswerSelector);
            JTextField newestField =optionFields.get(optionFields.size() - 1);
            newestField.setText(option);
        }
        // Defensive fallback
        if (optionFields.isEmpty()) {
            addOptionRow(optionsPanel,optionFields,correctAnswerSelector);
        }
        refreshCorrectAnswerSelector(optionFields, correctAnswerSelector);
        int oldCorrectIndex = question.getCorrectAnswerIndex();
        if (oldCorrectIndex >= 0 && oldCorrectIndex < optionFields.size()) {
            correctAnswerSelector.setSelectedIndex(oldCorrectIndex);
        }
        JButton addOptionButton = new JButton("+ Add Option");
        addOptionButton.addActionListener(e -> {
            addOptionRow(optionsPanel,optionFields,correctAnswerSelector);
            optionsPanel.revalidate();
            optionsPanel.repaint();
        });
        JScrollPane optionsScrollPane = new JScrollPane(optionsPanel);
        optionsScrollPane.setPreferredSize(new Dimension(400, 180));
        optionsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JPanel optionContainer = new JPanel();
        optionContainer.setLayout(new BoxLayout(optionContainer,BoxLayout.Y_AXIS));
        optionContainer.add(optionsScrollPane);
        optionContainer.add(Box.createVerticalStrut(5));
        optionContainer.add(addOptionButton);
        Object[] fields = {
                "Question ID:", question.getID(), "Prompt:", new JScrollPane(promptArea), "Options:", optionContainer, "Correct Answer:", correctAnswerSelector
        };
        int result = JOptionPane.showConfirmDialog(frame, fields, "Edit Multiple-Choice Question", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result != JOptionPane.OK_OPTION) {
                return;
            }
        String updatedPrompt = promptArea.getText().trim();
        if (updatedPrompt.isBlank()) {
            JOptionPane.showMessageDialog(frame,"Prompt is required.");
            return;
        }
        ArrayList<String> updatedOptions = new ArrayList<>();
        for (JTextField optionField : optionFields) {
            String option =optionField.getText().trim();
            if (option.isBlank()) {
                JOptionPane.showMessageDialog(frame,"All visible option fields must contain text.");
                return;
            }
            updatedOptions.add(option);
        }
        if (updatedOptions.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "The question must have at least one option.");
            return;
        }
        int updatedCorrectIndex = correctAnswerSelector.getSelectedIndex();
        if (updatedCorrectIndex < 0 || updatedCorrectIndex >= updatedOptions.size()) {
            JOptionPane.showMessageDialog(frame, "Select a valid correct answer.");
            return;
        }
        MultipleChoiceQuestion updatedQuestion = new MultipleChoiceQuestion(question.getID(), updatedPrompt, updatedOptions, updatedCorrectIndex);
        boolean success = moduleManager.updateMultipleChoiceQuestion(updatedQuestion);
        if (success) {
            JOptionPane.showMessageDialog(frame, "Multiple-choice question updated successfully.");
            refreshQuestionsAndSelect(updatedQuestion.getID());
        } else {
            JOptionPane.showMessageDialog(frame, "Unable to update multiple-choice question.");
        }
    }

    private void refreshAfterQuestionDeletion() {
        loadQuestions();
        if (questionSelector.getItemCount() > 0) {
            // Triggers the questionSelector listener
            questionSelector.setSelectedIndex(0);
        } else {
            selectedQuestion = null;
            clearQuestionDisplay();
        }
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
