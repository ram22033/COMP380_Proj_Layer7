package layer8project;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.SQLiteDataSource;

// This class serves as a repository for managing user progress data in the database. It provides methods to save and load user progress, including unlocked and completed modules, submodules, and questions. The class interacts with the SQLite database to perform CRUD operations on user progress data, ensuring that user progress is stored and retrieved efficiently.
// saveUnlockedModule, saveCompletedModule, saveUnlockedSubModule, saveCompletedSubModule, saveCompletedQuestion, loadUserProgress

public class ProgressRepository {
    private Connection connection;

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
