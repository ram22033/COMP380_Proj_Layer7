package layer8project;

import java.util.ArrayList;

// This class represents a SubModule, which is a component of a LearningModule. Each SubModule has a unique identifier, title, description, and a list of questions. It provides methods to manage the questions and modify its properties.
// getSubModuleID, getTitle, getDescription, getQuestions, addQuestion, removeQuestion, changeTitle, changeDescription, getQuestionCount

public class SubModule {
    private String subModuleID;
    private String title;
    private String description;
    private ArrayList<Question> questions;

    public SubModule(String subModuleID, String title, String description) {
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

    public ArrayList<Question> getQuestions() {
        return new ArrayList<>(questions); // Return a copy to prevent external modification
    }

    public void addQuestion(Question question) {
        // Check for null value for question
        if (question == null) {
            throw new IllegalArgumentException("Question cannot be null");
        }
        questions.add(question);
    }

    public void removeQuestion(Question question) {
        // Check for null value for question
        if (question == null) {
            throw new IllegalArgumentException("Question cannot be null");
        }
        questions.remove(question);
    }

    public void changeTitle(String newTitle) {
        // Check for null or empty value for newTitle
        if (newTitle == null || newTitle.isEmpty()) {
            throw new IllegalArgumentException("New title cannot be null or empty");
        }
        this.title = newTitle;
    }

    public void changeDescription(String newDescription) {
        // Check for null or empty value for newDescription
        if (newDescription == null || newDescription.isEmpty()) {
            throw new IllegalArgumentException("New description cannot be null or empty");
        }
        this.description = newDescription;
    }

    public int getQuestionCount() {
        return questions.size();
    }


}
