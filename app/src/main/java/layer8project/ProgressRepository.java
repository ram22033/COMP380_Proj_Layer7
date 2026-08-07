package layer8project;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.SQLiteDataSource;

/**
 * Manages database operations related to user learning progress
 * within the Layer7 learning management system.
 *
 * ProgressRepository stores and retrieves information about a
 * user's unlocked modules, completed modules, unlocked
 * submodules, completed submodules, and completed questions.
 * It also initializes the database tables used to persist
 * learning progress.
 *
 * Responsibilities:
 * - Initialize progress database tables
 * - Save unlocked learning modules
 * - Save completed learning modules
 * - Save unlocked submodules
 * - Save completed submodules
 * - Save completed questions
 * - Load user learning progress
 *
 * Main Functions:
 * - saveUnlockedModule()
 * - saveCompletedModule()
 * - saveUnlockedSubModule()
 * - saveCompletedSubModule()
 * - saveCompletedQuestion()
 * - loadUserProgress()
 *
 * @author Christopher Sparks
 * @since August 2026
 */
public class ProgressRepository {
    private Connection connection;

    /**
     * Creates a connection to the Layer7 SQLite database and
     * initializes the tables used to store user learning progress.
     * 
     * @throws RuntimeException if the database cannot be initialized
     */
    public ProgressRepository() {

        try {

            SQLiteDataSource dataSource = new SQLiteDataSource();
            dataSource.setUrl("jdbc:sqlite:layer7.db");
            connection = dataSource.getConnection();
            Statement stmt = connection.createStatement();
            stmt.execute("PRAGMA foreign_keys = ON");


            // Modules unlocked by each user
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS user_unlocked_modules ("
                    + "userID TEXT NOT NULL, "
                    + "moduleID TEXT NOT NULL, "
                    + "PRIMARY KEY(userID, moduleID), "
                    + "FOREIGN KEY(moduleID) "
                    + "REFERENCES learning_modules(moduleID) "
                    + "ON DELETE CASCADE"
                    + ")"
            );


            // Modules completed by each user
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS user_completed_modules ("
                    + "userID TEXT NOT NULL, "
                    + "moduleID TEXT NOT NULL, "
                    + "PRIMARY KEY(userID, moduleID), "
                    + "FOREIGN KEY(moduleID) "
                    + "REFERENCES learning_modules(moduleID) "
                    + "ON DELETE CASCADE"
                    + ")"
            );


            // Submodules unlocked by each user
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS user_unlocked_submodules ("
                    + "userID TEXT NOT NULL, "
                    + "subModuleID TEXT NOT NULL, "
                    + "PRIMARY KEY(userID, subModuleID), "
                    + "FOREIGN KEY(subModuleID) "
                    + "REFERENCES submodules(subModuleID) "
                    + "ON DELETE CASCADE"
                    + ")"
            );


            // Submodules completed by each user
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS user_completed_submodules ("
                    + "userID TEXT NOT NULL, "
                    + "subModuleID TEXT NOT NULL, "
                    + "PRIMARY KEY(userID, subModuleID), "
                    + "FOREIGN KEY(subModuleID) "
                    + "REFERENCES submodules(subModuleID) "
                    + "ON DELETE CASCADE"
                    + ")"
            );


            // Questions completed by each user
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS user_completed_questions ("
                    + "userID TEXT NOT NULL, "
                    + "questionID TEXT NOT NULL, "
                    + "PRIMARY KEY(userID, questionID), "
                    + "FOREIGN KEY(questionID) "
                    + "REFERENCES questions(questionID) "
                    + "ON DELETE CASCADE"
                    + ")"
            );

            stmt.close();

        } catch (SQLException e) {

            throw new RuntimeException("ProgressRepository database initialize error: " + e.getMessage());
        }
    }

    /**
     * Records that a user has unlocked a learning module.
     * @param userID userID the unique identifier of the user
     * @param moduleID the unique identifier of the unlocked module
     * @return {@code true} if the record was successfully saved;
     * otherwise {@code false}
     * @throws IllegalArgumentException if either identifier is invalid
     */
    public boolean saveUnlockedModule(String userID, String moduleID) {
        if (userID == null || userID.isEmpty() || moduleID == null || moduleID.isEmpty()) {
            throw new IllegalArgumentException("User ID and Module ID cannot be null or empty");
        }
        String sql = "INSERT OR IGNORE INTO user_unlocked_modules(userID, moduleID) VALUES(?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, userID);
            pstmt.setString(2, moduleID);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error saving unlocked module: " + e.getMessage());
        }
    }

    /**
     * Records that a user has completed a learning module.
     * @param userID the unique identifier of the user
     * @param moduleID the unique identifier of the completed module
     * @return {@code true} if the record was successfully saved;
     * otherwise {@code false}
     * @throws IllegalArgumentException if either identifier is invalid
     */
    public boolean saveCompletedModule(String userID, String moduleID) {
        if (userID == null || userID.isEmpty() || moduleID == null || moduleID.isEmpty()) {
            throw new IllegalArgumentException("User ID and Module ID cannot be null or empty");
        }
        String sql = "INSERT OR IGNORE INTO user_completed_modules(userID, moduleID) VALUES(?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, userID);
            pstmt.setString(2, moduleID);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error saving completed module: " + e.getMessage());
        }
    }

    /**
     * Records that a user has unlocked a submodule.
     * @param userID the unique identifier of the user
     * @param subModuleID the unique identifier of the unlocked submodule
     * @return {@code true} if the record was successfully saved;
     * otherwise {@code false}
     * @throws IllegalArgumentException if either identifier is invalid
     */
    public boolean saveUnlockedSubModule(String userID, String subModuleID) {
        if (userID == null || userID.isEmpty() || subModuleID == null || subModuleID.isEmpty()) {
            throw new IllegalArgumentException("User ID and SubModule ID cannot be null or empty");
        }
        String sql = "INSERT OR IGNORE INTO user_unlocked_submodules(userID, subModuleID) VALUES(?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, userID);
            pstmt.setString(2, subModuleID);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error saving unlocked submodule: " + e.getMessage());
        }
    }

    /**
     * Records that a user has completed a submodule.
     * @param userID the unique identifier of the user
     * @param subModuleID the unique identifier of the completed submodule
     * @return {@code true} if the record was successfully saved;
     * otherwise {@code false}
     * @throws IllegalArgumentException if either identifier is invalid
     */
    public boolean saveCompletedSubModule(String userID, String subModuleID) {
        if (userID == null || userID.isEmpty() || subModuleID == null || subModuleID.isEmpty()) {
            throw new IllegalArgumentException("User ID and SubModule ID cannot be null or empty");
        }
        String sql = "INSERT OR IGNORE INTO user_completed_submodules(userID, subModuleID) VALUES(?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, userID);
            pstmt.setString(2, subModuleID);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error saving completed submodule: " + e.getMessage());
        }
    }

    /**
     * Records that a user has correctly completed a question.
     * @param userID the unique identifier of the user
     * @param questionID the unique identifier of the completed question
     * @return {@code true} if the record was successfully saved;
     * otherwise {@code false}
     * @throws IllegalArgumentException if either identifier is invalid
     */
    public boolean saveCompletedQuestion(String userID, String questionID) {
        if (userID == null || userID.isEmpty() || questionID == null || questionID.isEmpty()) {
            throw new IllegalArgumentException("User ID and Question ID cannot be null or empty");
        }
        String sql = "INSERT OR IGNORE INTO user_completed_questions(userID, questionID) VALUES(?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, userID);
            pstmt.setString(2, questionID);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error saving completed question: " + e.getMessage());
        }
    }

    /**
     * Loads all saved learning progress for a user.
     * 
     * The returned UserProgress object includes unlocked modules,
     * completed modules, unlocked submodules, completed submodules,
     * and completed questions.
     * @param userID userID the unique identifier of the user
     * @return the user's learning progress
     * @throws IllegalArgumentException if the user ID is invalid
     */
    public UserProgress loadUserProgress(String userID) {
        if (userID == null || userID.isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        UserProgress userProgress = new UserProgress(userID);
        loadUnlockedModules(userProgress);
        loadCompletedModules(userProgress);
        loadUnlockedSubModules(userProgress);
        loadCompletedSubModules(userProgress);
        loadCompletedQuestions(userProgress);
        return userProgress;
    }

    private void loadUnlockedModules(UserProgress userProgress) {
        String sql = "SELECT moduleID FROM user_unlocked_modules WHERE userID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, userProgress.getUserID());
            try (ResultSet rs = pstmt.executeQuery()){
            while (rs.next()) {
                userProgress.unlockModule(rs.getString("moduleID"));
            }
        }
        } catch (SQLException e) {
            throw new RuntimeException("Error loading unlocked modules: " + e.getMessage());
        }
    }

    private void loadCompletedModules(UserProgress userProgress) {
        String sql = "SELECT moduleID FROM user_completed_modules WHERE userID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, userProgress.getUserID());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    userProgress.markModuleCompleted(rs.getString("moduleID"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error loading completed modules: " + e.getMessage());
        }
    }

    private void loadUnlockedSubModules(UserProgress userProgress) {
        String sql = "SELECT subModuleID FROM user_unlocked_submodules WHERE userID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, userProgress.getUserID());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    userProgress.unlockSubModule(rs.getString("subModuleID"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error loading unlocked submodules: " + e.getMessage());
        }
    }

    private void loadCompletedSubModules(UserProgress userProgress) {
        String sql = "SELECT subModuleID FROM user_completed_submodules WHERE userID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, userProgress.getUserID());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    userProgress.markSubModuleCompleted(rs.getString("subModuleID"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error loading completed submodules: " + e.getMessage());
        }
    }

    private void loadCompletedQuestions(UserProgress userProgress) {
        String sql = "SELECT questionID FROM user_completed_questions WHERE userID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, userProgress.getUserID());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    userProgress.markQuestionCorrect(rs.getString("questionID"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error loading completed questions: " + e.getMessage());
        }
    }



}
