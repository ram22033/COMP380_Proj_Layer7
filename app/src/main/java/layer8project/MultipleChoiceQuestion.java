package layer8project;

import java.util.ArrayList;

// This class represents multiple choice questions, which are a type of question that has a prompt and a list of options, with one correct answer. It extends the abstract Question class and provides methods to validate answers, change the correct answer, and manage the list of options.
// getOptions, validateAnswer, changeCorrectAnswer, changeOption, addOption, removeOption, getCorrectAnswerIndex

public class MultipleChoiceQuestion extends Question {
    private ArrayList<String> options;
    private int correctAnswerIndex;

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
    public ArrayList<String> getOptions() {
        return options; 
        // Should later add a method to return a copy of the options list to prevent external modification
    }

    public boolean validateAnswer(int selectedChoiceIndex) { // FOR GUI : Validate the selected choice index against the correct answer index
        return selectedChoiceIndex == correctAnswerIndex;
    }

    public void changeCorrectAnswer(int newCorrectAnswerIndex) {
        // Check for out of bounds index
        if (newCorrectAnswerIndex < 0 || newCorrectAnswerIndex >= options.size()) {
            throw new IllegalArgumentException("New correct answer index is out of bounds");
        }
        this.correctAnswerIndex = newCorrectAnswerIndex;
    }

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
    public void addOption(String newOption) {
        // Check for null or empty value for newOption
        if (newOption == null || newOption.isEmpty()) {
            throw new IllegalArgumentException("New option cannot be null or empty");
        }
        options.add(newOption);
    }
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
    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

}
