package layer8project;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.sqlite.SQLiteDataSource;

/**
 * Manages database operations for questions within the Layer7
 * learning management system.
 *
 * QuestionRepository stores and retrieves entry questions and
 * multiple-choice questions. It also manages question prompts,
 * correct answers, multiple-choice options, and the relationship
 * between questions and submodules.
 *
 * Responsibilities:
 * - Initialize question-related database tables
 * - Add entry questions
 * - Add multiple-choice questions
 * - Retrieve individual questions
 * - Retrieve questions associated with a submodule
 * - Update entry and multiple-choice questions
 * - Delete questions
 * - Preserve question-to-submodule relationships
 *
 * Main Functions:
 * - addEntryQuestion()
 * - addMultipleChoiceQuestion()
 * - getQuestionById()
 * - getQuestionsBySubModuleId()
 * - updateEntryQuestion()
 * - updateMultipleChoiceQuestion()
 * - deleteQuestion()
 *
 * @author Christopher Sparks
 * @since August 2026
 */
public class QuestionRepository {
    private Connection connection;

    /**
     * Creates a connection to the Layer7 SQLite database and initializes
     * the tables used to store questions, entry answers, multiple-choice
     * answers, and multiple-choice options.
     * 
     * @throws RuntimeException if the database cannot be initialized
     */
    public QuestionRepository() {
        try {
            SQLiteDataSource dataSource = new SQLiteDataSource();
            dataSource.setUrl("jdbc:sqlite:layer7.db");
            connection = dataSource.getConnection();
            Statement stmt = connection.createStatement();
            stmt.execute("PRAGMA foreign_keys = ON");
            // Create table for all questions
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS questions (" +
                "questionid TEXT PRIMARY KEY, " +
                "submoduleid TEXT NOT NULL, " +
                "prompt TEXT NOT NULL, " +
                "type TEXT NOT NULL, " +
                "FOREIGN KEY(submoduleid) REFERENCES submodules(submoduleid) ON DELETE CASCADE" +
                ")"
            );
            // Create table for entry questions
                        stmt.execute(
                "CREATE TABLE IF NOT EXISTS entry_questions (" +
                "questionid TEXT PRIMARY KEY, " +
                "correct_answer TEXT NOT NULL, " +
                "FOREIGN KEY(questionid) REFERENCES questions(questionid) ON DELETE CASCADE" +
                ")"
            );
            // Create table for multiple choice questions
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS multiple_choice_questions (" +
                "questionid TEXT PRIMARY KEY, " +
                "correct_answer_index INTEGER NOT NULL, " +
                "FOREIGN KEY(questionid) REFERENCES questions(questionid) ON DELETE CASCADE" +
                ")"
            );
            // Create table for multiple choice options
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS multiple_choice_options (" +
                "questionid TEXT NOT NULL, " +
                "optionIndex INTEGER NOT NULL, " +
                "optionText TEXT NOT NULL, " +
                "PRIMARY KEY(questionid, optionIndex), " +
                "FOREIGN KEY(questionid) REFERENCES questions(questionid) ON DELETE CASCADE" +
                ")"
            );
        stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException("QuestionRepositoryDatabase initialize error: " + e.getMessage());

        }
    }
    
    /**
     * Adds an entry question to a submodule.
     * 
     * The question's shared information is stored in the main questions
     * table, while its correct answer is stored in the entry-question table.
     * @param question the entry question to add
     * @param subModuleId the identifier of the parent submodule
     * @return {@code true} if the question was successfully added;
     * otherwise {@code false}
     * @throws IllegalArgumentException if the question or submodule ID is invalid
     */
    public boolean addEntryQuestion(EntryQuestion question, String subModuleId) {
        if (question == null || subModuleId == null || subModuleId.isEmpty()) {
            throw new IllegalArgumentException("Question and SubModule ID cannot be null or empty");
        }
        try {
            // Insert into questions table
            PreparedStatement pstmt = connection.prepareStatement(
                "INSERT INTO questions (questionid, submoduleid, prompt, type) VALUES (?, ?, ?, ?)"
            );
            pstmt.setString(1, question.getID());
            pstmt.setString(2, subModuleId);
            pstmt.setString(3, question.getPrompt());
            pstmt.setString(4, "entry");
            pstmt.executeUpdate();
            pstmt.close();

            // Insert into entry_questions table
            pstmt = connection.prepareStatement(
                "INSERT INTO entry_questions (questionid, correct_answer) VALUES (?, ?)"
            );
            pstmt.setString(1, question.getID());
            pstmt.setString(2, question.getCorrectAnswer());
            pstmt.executeUpdate();
            pstmt.close();

            return true;
        } catch (SQLException e) {
            System.err.println("Error adding entry question: " + e.getMessage());
            return false;
        }
    }

    /**
     * Adds a multiple-choice question to a submodule.
     * 
     * The method stores the question prompt, correct-answer index,
     * and all available answer options in their related database tables.
     * 
     * @param question the multiple-choice question to add
     * @param subModuleId the identifier of the parent submodule
     * @return {@code true} if the question was successfully added;
     * otherwise {@code false}
     * @throws IllegalArgumentException if the question or submodule ID is invalid
     */
    public boolean addMultipleChoiceQuestion(MultipleChoiceQuestion question, String subModuleId) {
        if (question == null || subModuleId == null || subModuleId.isEmpty()) {
            throw new IllegalArgumentException("Question and SubModule ID cannot be null or empty");
        }
        try {
            // Insert into questions table
            PreparedStatement pstmt = connection.prepareStatement(
                "INSERT INTO questions (questionid, submoduleid, prompt, type) VALUES (?, ?, ?, ?)"
            );
            pstmt.setString(1, question.getID());
            pstmt.setString(2, subModuleId);
            pstmt.setString(3, question.getPrompt());
            pstmt.setString(4, "multiple_choice");
            pstmt.executeUpdate();
            pstmt.close();

            // Insert into multiple_choice_questions table
            pstmt = connection.prepareStatement(
                "INSERT INTO multiple_choice_questions (questionid, correct_answer_index) VALUES (?, ?)"
            );
            pstmt.setString(1, question.getID());
            pstmt.setInt(2, question.getCorrectAnswerIndex());
            pstmt.executeUpdate();
            pstmt.close();

            // Insert into multiple_choice_options table
            for (int i = 0; i < question.getOptions().size(); i++) {
                pstmt = connection.prepareStatement(
                    "INSERT INTO multiple_choice_options (questionid, optionIndex, optionText) VALUES (?, ?, ?)"
                );
                pstmt.setString(1, question.getID());
                pstmt.setInt(2, i);
                pstmt.setString(3, question.getOptions().get(i));
                pstmt.executeUpdate();
                pstmt.close();
            }

            return true;
        } catch (SQLException e) {
            System.err.println("Error adding multiple choice question: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves a question using its unique identifier.
     * 
     * The method determines the stored question type and reconstructs
     * either an EntryQuestion or MultipleChoiceQuestion object.
     * 
     * @param questionId the unique identifier of the question
     * @return the matching question, or {@code null} if no question is found
     * @throws IllegalArgumentException if the question ID is invalid
     */
    public Question getQuestionById(String questionId) {
        if (questionId == null || questionId.isBlank()) {
            throw new IllegalArgumentException("Question ID cannot be null or empty");
        }
        String questionSql ="SELECT type, prompt " + "FROM questions " + "WHERE questionid = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(questionSql)) {
            pstmt.setString(1, questionId);
            String type;
            String prompt;
            try (ResultSet rs = pstmt.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                type = rs.getString("type");
                prompt = rs.getString("prompt");
            }
        // The first ResultSet is closed before these queries begin
        if ("entry".equals(type)) {
            String entrySql = "SELECT correct_answer " + "FROM entry_questions " + "WHERE questionid = ?";
            try (PreparedStatement entryPstmt = connection.prepareStatement(entrySql)) {
                entryPstmt.setString(1, questionId);
                try (ResultSet entryRs = entryPstmt.executeQuery()) {
                    if (entryRs.next()) {
                        String correctAnswer = entryRs.getString("correct_answer");
                        return new EntryQuestion(questionId, prompt, correctAnswer);
                    }
                }
            }
        } else if ("multiple_choice".equals(type)) {
            String multipleChoiceSql = "SELECT correct_answer_index " + "FROM multiple_choice_questions " + "WHERE questionid = ?";
            int correctAnswerIndex;
            try (PreparedStatement mcPstmt = connection.prepareStatement(multipleChoiceSql)) {
                mcPstmt.setString(1, questionId);
                try (ResultSet mcRs = mcPstmt.executeQuery()) {
                    if (!mcRs.next()) {
                        return null;
                    }
                    correctAnswerIndex = mcRs.getInt("correct_answer_index");
                }
            }
            ArrayList<String> options = new ArrayList<>();
            String optionsSql = "SELECT optionText " + "FROM multiple_choice_options " + "WHERE questionid = ? " + "ORDER BY optionIndex";
            try (PreparedStatement optionsPstmt = connection.prepareStatement(optionsSql)) {
                optionsPstmt.setString(1, questionId);
                try (ResultSet optionsRs = optionsPstmt.executeQuery()) {
                    while (optionsRs.next()) {
                        options.add(optionsRs.getString("optionText"));
                    }
                }
            }
            return new MultipleChoiceQuestion(questionId, prompt, options, correctAnswerIndex);
        }
        return null;
    } catch (SQLException e) {
        System.err.println("Error retrieving question: " + e.getMessage());
        return null;
    }
    }

    /**
     * Retrieves all questions associated with a submodule.
     * 
     * @param subModuleId the unique identifier of the parent submodule
     * @return a list containing all questions assigned to the submodule
     * @throws IllegalArgumentException if the submodule ID is invalid
     */
    public ArrayList<Question> getQuestionsBySubModuleId(String subModuleId) {
        if (subModuleId == null || subModuleId.isBlank()) {
            throw new IllegalArgumentException("SubModule ID cannot be null or empty");
        }
        ArrayList<String> questionIds = new ArrayList<>();
        String sql = "SELECT questionid " + "FROM questions " + "WHERE submoduleid = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, subModuleId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    questionIds.add(rs.getString("questionid"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving questions for submodule: " + e.getMessage());
            return new ArrayList<>();
        }
        // The original ResultSet and statement are now closed
        ArrayList<Question> questions = new ArrayList<>();
        for (String questionId : questionIds) {
            Question question = getQuestionById(questionId);
            if (question != null) {
                questions.add(question);
            }
        }
        return questions;
    }

    private EntryQuestion getEntryQuestion(String questionId, String prompt) {
        try {
            PreparedStatement pstmt = connection.prepareStatement(
                "SELECT correct_answer FROM entry_questions WHERE questionid = ?"
            );
            pstmt.setString(1, questionId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String correctAnswer = rs.getString("correct_answer");
                return new EntryQuestion(questionId, prompt, correctAnswer);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving entry question: " + e.getMessage());
        }
        return null;
    }

    private MultipleChoiceQuestion getMultipleChoiceQuestion(String questionId, String prompt) {
        try {
            PreparedStatement pstmt = connection.prepareStatement(
                "SELECT correct_answer_index FROM multiple_choice_questions WHERE questionid = ?"
            );
            pstmt.setString(1, questionId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int correctAnswerIndex = rs.getInt("correct_answer_index");
                ArrayList<String> options = new ArrayList<>();
                PreparedStatement optionsPstmt = connection.prepareStatement(
                    "SELECT optionText FROM multiple_choice_options WHERE questionid = ? ORDER BY optionIndex"
                );
                optionsPstmt.setString(1, questionId);
                ResultSet optionsRs = optionsPstmt.executeQuery();
                while (optionsRs.next()) {
                    options.add(optionsRs.getString("optionText"));
                }
                return new MultipleChoiceQuestion(questionId, prompt, options, correctAnswerIndex);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving multiple choice question: " + e.getMessage());
        }
        return null;
    }

    private ArrayList<String> getMultipleChoiceOptions(String questionId) {
        ArrayList<String> options = new ArrayList<>();
        try {
            PreparedStatement pstmt = connection.prepareStatement(
                "SELECT optionText FROM multiple_choice_options WHERE questionid = ? ORDER BY optionIndex"
            );
            pstmt.setString(1, questionId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                options.add(rs.getString("optionText"));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving multiple choice options: " + e.getMessage());
        }
        return options;
    }

    /**
     * Updates the prompt and correct answer of an entry question.
     * 
     * @param question the entry question containing the updated values
     * @return {@code true} if the question was successfully updated;
     * otherwise {@code false}
     * @throws IllegalArgumentException if the question is {@code null}
     */
    public boolean updateEntryQuestion(EntryQuestion question) {
        if (question == null) {
            throw new IllegalArgumentException("Question cannot be null");
        }
        try {
            // Update questions table
            PreparedStatement pstmt = connection.prepareStatement(
                "UPDATE questions SET prompt = ? WHERE questionid = ?"
            );
            pstmt.setString(1, question.getPrompt());
            pstmt.setString(2, question.getID());
            pstmt.executeUpdate();
            pstmt.close();

            // Update entry_questions table
            pstmt = connection.prepareStatement(
                "UPDATE entry_questions SET correct_answer = ? WHERE questionid = ?"
            );
            pstmt.setString(1, question.getCorrectAnswer());
            pstmt.setString(2, question.getID());
            pstmt.executeUpdate();
            pstmt.close();

            return true;
        } catch (SQLException e) {
            System.err.println("Error updating entry question: " + e.getMessage());
            return false;
        }
    }

    /**
     * Updates a multiple-choice question.
     * 
     * The method updates the prompt and correct-answer index, removes
     * the previously stored options, and inserts the updated option list.
     * 
     * @param question the multiple-choice question containing updated values
     * @return {@code true} if the question was successfully updated;
     * otherwise {@code false}
     * @throws IllegalArgumentException if the question is {@code null}
     */
    public boolean updateMultipleChoiceQuestion(MultipleChoiceQuestion question) {
        if (question == null) {
            throw new IllegalArgumentException("Question cannot be null");
        }
        try {
            // Update questions table
            PreparedStatement pstmt = connection.prepareStatement(
                "UPDATE questions SET prompt = ? WHERE questionid = ?"
            );
            pstmt.setString(1, question.getPrompt());
            pstmt.setString(2, question.getID());
            pstmt.executeUpdate();
            pstmt.close();

            // Update multiple_choice_questions table
            pstmt = connection.prepareStatement(
                "UPDATE multiple_choice_questions SET correct_answer_index = ? WHERE questionid = ?"
            );
            pstmt.setInt(1, question.getCorrectAnswerIndex());
            pstmt.setString(2, question.getID());
            pstmt.executeUpdate();
            pstmt.close();

            // Delete existing options
            pstmt = connection.prepareStatement(
                "DELETE FROM multiple_choice_options WHERE questionid = ?"
            );
            pstmt.setString(1, question.getID());
            pstmt.executeUpdate();
            pstmt.close();

            // Insert new options
            for (int i = 0; i < question.getOptions().size(); i++) {
                pstmt = connection.prepareStatement(
                    "INSERT INTO multiple_choice_options (questionid, optionIndex, optionText) VALUES (?, ?, ?)"
                );
                pstmt.setString(1, question.getID());
                pstmt.setInt(2, i);
                pstmt.setString(3, question.getOptions().get(i));
                pstmt.executeUpdate();
                pstmt.close();
            }

            return true;
        } catch (SQLException e) {
            System.err.println("Error updating multiple choice question: " + e.getMessage());
            return false;
        }
    }

    /**
     * Deletes a question from the database.
     * 
     * Related entry-question data, multiple-choice data, and options
     * are removed through foreign-key cascade relationships.
     * 
     * @param questionId the unique identifier of the question to delete
     * @return {@code true} if the question was successfully deleted;
     * otherwise {@code false}
     * @throws IllegalArgumentException if the question ID is invalid
     */
    public boolean deleteQuestion(String questionId) {
        if (questionId == null || questionId.isEmpty()) {
            throw new IllegalArgumentException("Question ID cannot be null or empty");
        }
        try {
            PreparedStatement pstmt = connection.prepareStatement(
                "DELETE FROM questions WHERE questionid = ?"
            );
            pstmt.setString(1, questionId);
            int affectedRows = pstmt.executeUpdate();
            pstmt.close();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting question: " + e.getMessage());
            return false;
        }
    }

    /**
     * Returns the total number of questions stored in the database.
     * @return the total number of questions
     */
    public int getTotalQuestionCount() {
        String sql = "SELECT COUNT(*) AS total FROM questions";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving total question count: " + e.getMessage());
        }
        return 0;
    }

}