package layer8project;

import java.util.ArrayList;

/**
 * Represents a submodule within the Layer7 learning
 * management system.
 *
 * A submodule belongs to a learning module and contains
 * instructional content and a collection of associated
 * questions. Each submodule has a unique identifier,
 * title, description, and display order.
 *
 * Responsibilities:
 * - Store submodule information
 * - Maintain the submodule display order
 * - Manage the collection of questions
 * - Organize learning content within a module
 *
 * Main Functions:
 * - addQuestion()
 * - removeQuestion()
 * - getQuestions()
 * - changeTitle()
 * - changeDescription()
 * - getQuestionCount()
 *
 * @author Christopher Sparks
 * @since August 2026
 */

public class SubModule {
    private String subModuleID;
    private String title;
    private String description;
    private ArrayList<Question> questions;
    private int subModuleOrder; // Order of the submodule within its parent module

    /**
     * Creates a new submodule.
     * 
     * @param subModuleID the unique identifier for the submodule
     * @param title the title of the submodule
     * @param description the description of the submodule
     * @param subModuleOrder the display order within the parent module
     * @throws IllegalArgumentException if any parameter is invalid
     */
    public SubModule(String subModuleID, String title, String description, int subModuleOrder) {
        // Check for null or empty values for subModuleID, title, and description
        if (subModuleID == null || subModuleID.isEmpty()) {
            throw new IllegalArgumentException("SubModule ID cannot be null or empty");
        }
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }
        if (subModuleOrder < 0) {
            throw new IllegalArgumentException("SubModule order cannot be negative");
        }
        this.subModuleOrder = subModuleOrder;
        this.subModuleID = subModuleID;
        this.title = title;
        this.description = description;
        this.questions = new ArrayList<>();
    }

    public String getSubModuleID() {
        return subModuleID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Returns a copy of the questions contained in this submodule.
     * 
     * A copy is returned to prevent external modification of the internal question collection.
     * 
     * @return A copy is returned to prevent external modification of the internal question collection.
     */
    public ArrayList<Question> getQuestions() {
        return new ArrayList<>(questions); // Return a copy to prevent external modification
    }

    /**
     * Adds a question to this submodule.
     * 
     * @param question the question to add
     * @throws IllegalArgumentException if the question is null
     */
    public void addQuestion(Question question) {
        // Check for null value for question
        if (question == null) {
            throw new IllegalArgumentException("Question cannot be null");
        }
        questions.add(question);
    }

    /**
     * Removes a question from this submodule.
     * 
     * @param question the question to remove
     * @throws IllegalArgumentException if the question is null
     */
    public void removeQuestion(Question question) {
        // Check for null value for question
        if (question == null) {
            throw new IllegalArgumentException("Question cannot be null");
        }
        questions.remove(question);
    }

    /**
     * Updates the title of this submodule.
     * 
     * @param newTitle the new submodule title
     * @throws IllegalArgumentException if the title is null or empty
     */
    public void changeTitle(String newTitle) {
        // Check for null or empty value for newTitle
        if (newTitle == null || newTitle.isEmpty()) {
            throw new IllegalArgumentException("New title cannot be null or empty");
        }
        this.title = newTitle;
    }

    /**
     * Updates the description of this submodule.
     * 
     * @param newDescription the new submodule description
     * @throws IllegalArgumentException if the description is null or empty
     */
    public void changeDescription(String newDescription) {
        // Check for null or empty value for newDescription
        if (newDescription == null || newDescription.isEmpty()) {
            throw new IllegalArgumentException("New description cannot be null or empty");
        }
        this.description = newDescription;
    }

    /**
     * Returns the number of questions contained in this submodule.
     * 
     * @return the number of questions
     */
    public int getQuestionCount() {
        return questions.size();
    }

    public int getSubModuleOrder() {
        return subModuleOrder;
    }


}
