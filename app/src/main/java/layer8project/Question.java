package layer8project;

// This is meant to be an abstract class that will be extended by the different types of questions
// (MultipleChoiceQuestion, TrueFalseQuestion, ShortAnswerQuestion, etc.)

public abstract class Question {
    private String ID; // Unique identifier for the question (Database reference)
    private String prompt; // The question text

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

    public String getID() {
        return ID;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        // Check for null or empty value for prompt
        if (prompt == null || prompt.isEmpty()) {
            throw new IllegalArgumentException("Prompt cannot be null or empty");
        }
        this.prompt = prompt;
    }


}
