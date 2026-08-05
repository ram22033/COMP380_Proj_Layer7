package layer8project;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.sqlite.SQLiteDataSource;

// This class serves as a repository for managing questions in the database. It provides methods to add, retrieve, update, and delete both entry and multiple choice questions. The class interacts with the SQLite database to perform CRUD operations on the questions and their associated data, ensuring that questions are stored and retrieved efficiently.
// addEntryQuestion, addMultipleChoiceQuestion, getQuestionById, getQuestionsBySubModuleId, updateEntryQuestion, updateMultipleChoiceQuestion, deleteQuestion

public class QuestionRepository {
    private Connection connection;

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

}