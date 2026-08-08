package layer8project;

/**
 * Represents a free-response question within the Layer7
 * learning management system.
 *
 * A free-response question requires the user to enter a text
 * response that is compared against the stored correct answer.
 * Unlike multiple-choice questions, users must supply their
 * own answer rather than selecting from predefined options.
 *
 * Responsibilities:
 * - Store the correct answer
 * - Validate user responses
 * - Update the correct answer
 * - Update the question prompt
 *
 * Main Functions:
 * - validateAnswer()
 * - changeAnswer()
 * - changePrompt()
 * - getCorrectAnswer()
 *
 * @author Christopher Sparks
 * @since August 2026
 */
public class EntryQuestion extends Question{
    private String correctAnswer; // The correct answer for the question

    // this function will normalize inputs by trimming whitespace and converting to lowercase
    private String normalize(String input) {
        return input.trim().toLowerCase();
    }

    /**
 * Creates a new entry question.
 *
 * @param ID the unique identifier for the question
 * @param prompt the question presented to the user
 * @param correctAnswer the correct response used for validation
 */
    public EntryQuestion(String ID, String prompt, String correctAnswer) {
        super(ID, prompt);
        // Check for null or empty value for correctAnswer
        if (correctAnswer == null || correctAnswer.isEmpty()) {
            throw new IllegalArgumentException("Correct answer cannot be null or empty");
        }
        this.correctAnswer = normalize(correctAnswer); // this should be normalized to ensure consistent comparison
    }
    
    /**
 * Determines whether the user's answer matches the stored
 * correct answer.
 *
 * @param answer the user's submitted response
 * @return {@code true} if the answer is correct; otherwise {@code false}
 */
    public boolean checkAnswer(String answer) {
        // Check for null or empty value for answer
        if (answer == null || answer.isEmpty()) {
            throw new IllegalArgumentException("Answer cannot be null or empty");
        }
        return normalize(answer).equals(correctAnswer);
    }

    /**
 * Updates the correct answer for this entry question.
 *
 * @param newCorrectAnswer the new correct answer
 */
    public void changeCorrectAnswer(String newCorrectAnswer) {
        // Check for null or empty value for newCorrectAnswer
        if (newCorrectAnswer == null || newCorrectAnswer.isEmpty()) {
            throw new IllegalArgumentException("New correct answer cannot be null or empty");
        }
        this.correctAnswer = normalize(newCorrectAnswer);
    }

    /**
 * Returns the correct answer for this question.
 *
 * @return the correct answer
 */
    public String getCorrectAnswer() {
        return correctAnswer;
    }

}
