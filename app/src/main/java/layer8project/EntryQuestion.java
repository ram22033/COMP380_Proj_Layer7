package layer8project;

// This is a specific type of question that requires the user to input a text answer. The answer is then checked against the correct answer stored in the question object. The comparison is case-insensitive and ignores leading/trailing whitespace.
// getCorrectAnswer, checkAnswer, changeCorrectAnswer, changePrompt

public class EntryQuestion extends Question{
    private String correctAnswer; // The correct answer for the question

    // this function will normalize inputs by trimming whitespace and converting to lowercase
    private String normalize(String input) {
        return input.trim().toLowerCase();
    }

    public EntryQuestion(String ID, String prompt, String correctAnswer) {
        super(ID, prompt);
        // Check for null or empty value for correctAnswer
        if (correctAnswer == null || correctAnswer.isEmpty()) {
            throw new IllegalArgumentException("Correct answer cannot be null or empty");
        }
        this.correctAnswer = normalize(correctAnswer); // this should be normalized to ensure consistent comparison
    }
    
    public boolean checkAnswer(String answer) {
        // Check for null or empty value for answer
        if (answer == null || answer.isEmpty()) {
            throw new IllegalArgumentException("Answer cannot be null or empty");
        }
        return normalize(answer).equals(correctAnswer);
    }

    public void changeCorrectAnswer(String newCorrectAnswer) {
        // Check for null or empty value for newCorrectAnswer
        if (newCorrectAnswer == null || newCorrectAnswer.isEmpty()) {
            throw new IllegalArgumentException("New correct answer cannot be null or empty");
        }
        this.correctAnswer = normalize(newCorrectAnswer);
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void changePrompt(String newPrompt) {
        // Check for null or empty value for newPrompt
        if (newPrompt == null || newPrompt.isEmpty()) {
            throw new IllegalArgumentException("New prompt cannot be null or empty");
        }
        super.setPrompt(newPrompt);
    }






}
