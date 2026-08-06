package layer8project;

/**
 * Represents the abstract base class for all question types
 * within the Layer7 learning management system.
 *
 * Every question contains a unique identifier and a prompt
 * presented to the user. Specific question types extend this
 * class to implement their own answer validation and behavior.
 *
 * Responsibilities:
 * - Store a unique question identifier
 * - Store the question prompt
 * - Provide common functionality shared by all question types
 * - Serve as the parent class for all question implementations
 *
 * Main Functions:
 * - getID()
 * - getPrompt()
 * - changePrompt()
 *
 * @author Christopher Sparks
 * @since August 2026
 */
public abstract class Question {
    private String ID; // Unique identifier for the question (Database reference)
    protected String prompt; // The question text

    /**
     * Creates a new question.
     * @param ID the unique identifier for the question
     * @param prompt the question presented to the user
     * @throws IllegalArgumentException if the ID or prompt is null or empty
     */
    public Question(String ID, String prompt) {
        // Check for null or empty values for ID and prompt
        if (ID == null || ID.isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        if (prompt == null || prompt.isEmpty()) {
            throw new IllegalArgumentException("Prompt cannot be null or empty");
        }
        
        this.ID = ID;
        this.prompt = prompt;
    }

    /**
     * Returns the unique identifier for this question.
     * @return the question identifier
     */
    public String getID() {
        return ID;
    }

    /**
     * Returns the question prompt.
     * @return the question prompt
     */
    public String getPrompt() {
        return prompt;
    }

    /**
     * Updates the question prompt.
     * @param prompt the new question prompt
     * @throws IllegalArgumentException if the prompt is null or empty
     */
    public void changePrompt(String prompt) {
        // Check for null or empty value for prompt
        if (prompt == null || prompt.isEmpty()) {
            throw new IllegalArgumentException("Prompt cannot be null or empty");
        }
        this.prompt = prompt;
    }


}
