package layer8project;

import java.util.ArrayList;

/**
 * Represents a multiple-choice question within the Layer7
 * learning management system.
 *
 * A multiple-choice question consists of a question prompt,
 * one or more answer options, and the index of the correct
 * answer. The class supports questions with any number of
 * answer choices, including True/False questions.
 *
 * Responsibilities:
 * - Store answer choices
 * - Track the correct answer
 * - Validate user selections
 * - Manage answer options
 * - Update the correct answer
 *
 * Main Functions:
 * - validateAnswer()
 * - addOption()
 * - removeOption()
 * - changeOption()
 * - changeCorrectAnswer()
 * - getOptions()
 * - getCorrectAnswerIndex()
 * - isCorrectAnswer()
 *
 * @author Christopher Sparks
 * @since August 2026
 */
public class MultipleChoiceQuestion extends Question {
    private ArrayList<String> options;
    private int correctAnswerIndex;
    
    /**
     * Creates a new multiple-choice question.
     * @param ID the unique identifier for the question
     * @param prompt the question presented to the user
     * @param options the list of possible answer choices
     * @param correctAnswerIndex the index of the correct answer
     * @throws IllegalArgumentException if the options list is invalid or the correct answer index is outside the bounds of the list
     */
    public MultipleChoiceQuestion(String ID, String prompt, ArrayList<String> options, int correctAnswerIndex) {
        super(ID, prompt);
        //  Input Checks
        if (options == null || options.isEmpty()) {
            throw new IllegalArgumentException("Options cannot be null or empty");
        }
        if (correctAnswerIndex < 0 || correctAnswerIndex >= options.size()) {
            throw new IllegalArgumentException("Correct answer index is out of bounds");
        }
        this.options = new ArrayList<>(options);
        this.correctAnswerIndex = correctAnswerIndex;
    }
    /**
     * Returns the list of answer choices.
     * @return the available answer options
     */
    public ArrayList<String> getOptions() {
        return new ArrayList<>(options); 
    }
    /**
     *  Determines whether the selected answer is correct.
     * @param selectedChoiceIndex The selected answer
     * @return {@code true} if the selected answer is correct; otherwise {@code false}
     */
    public boolean validateAnswer(int selectedChoiceIndex) { // FOR GUI : Validate the selected choice index against the correct answer index
        return selectedChoiceIndex == correctAnswerIndex;
    }

    /**
     * Updates the index of the correct answer.
     * @param newCorrectAnswerIndex the new correct answer index
     * @throws IllegalArgumentException if the index is invalid
     */
    public void changeCorrectAnswer(int newCorrectAnswerIndex) {
        // Check for out of bounds index
        if (newCorrectAnswerIndex < 0 || newCorrectAnswerIndex >= options.size()) {
            throw new IllegalArgumentException("New correct answer index is out of bounds");
        }
        this.correctAnswerIndex = newCorrectAnswerIndex;
    }

    /**
     * Updates one of the available answer choices.
     * @param index the index of the option to update
     * @param newOption the new option
     * @throws IllegalArgumentException if the index is invalid or the option is empty
     */
    public void changeOption(int index, String newOption) {
        // Check for out of bounds index
        if (index < 0 || index >= options.size()) {
            throw new IllegalArgumentException("Option index is out of bounds");
        }
        // Check for null or empty value for newOption
        if (newOption == null || newOption.isEmpty()) {
            throw new IllegalArgumentException("New option cannot be null or empty");
        }
        options.set(index, newOption);
    }

    /**
     * Adds a new answer choice to the question.
     * @param newOption the answer choice to add
     *  @throws IllegalArgumentException if the option is null or empty
     */
    public void addOption(String newOption) {
        // Check for null or empty value for newOption
        if (newOption == null || newOption.isEmpty()) {
            throw new IllegalArgumentException("New option cannot be null or empty");
        }
        options.add(newOption);
    }

    /**
     * Removes an answer choice from the question.
     * @param index index the index of the option to remove
     * @throws IllegalArgumentException if the index is invalid
     */
    public void removeOption(int index) {
        // Check for out of bounds index
        if (index < 0 || index >= options.size()) {
            throw new IllegalArgumentException("Option index is out of bounds");
        }
        options.remove(index);
        // Adjust correctAnswerIndex if necessary
        if (correctAnswerIndex == index) {
            correctAnswerIndex = -1; // No correct answer now
        } else if (correctAnswerIndex > index) {
            correctAnswerIndex--; // Shift down the correct answer index
        }
    }

    /**
     * Returns the index of the correct answer.
     * @return The correct answer index
     */
    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    /**
     * Determines whether the provided answer matches the correct answer.
     * @param userAnswer the user's answer
     * @return {@code true} if the answer is correct; otherwise {@code false}
     * @throws IllegalArgumentException if the answer is null or empty
     */
    public boolean isCorrectAnswer(String userAnswer) {
        if (userAnswer == null || userAnswer.isEmpty()) {
            throw new IllegalArgumentException("User answer cannot be null or empty");
        }
        return options.get(correctAnswerIndex).equals(userAnswer);
    }

}
