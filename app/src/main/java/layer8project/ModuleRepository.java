package layer8project;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.sqlite.SQLiteDataSource;

/**
 * Manages database operations for learning modules and submodules
 * within the Layer7 learning management system.
 *
 * ModuleRepository connects to the Layer7 SQLite database and provides
 * methods for creating, retrieving, updating, and deleting learning
 * modules and submodules. It also initializes the required database
 * tables and maintains the relationship between modules and their
 * associated submodules.
 *
 * Responsibilities:
 * - Initialize module and submodule database tables
 * - Add learning modules and submodules
 * - Retrieve modules and submodules from the database
 * - Update stored module and submodule information
 * - Delete modules and submodules
 * - Preserve module-to-submodule relationships
 *
 * Main Functions:
 * - addModule()
 * - addSubModule()
 * - getModuleById()
 * - getSubModuleById()
 * - updateModule()
 * - updateSubModule()
 * - deleteModule()
 * - deleteSubModule()
 * - getAllModules()
 * - getSubModules()
 *
 * @author Christopher Sparks
 * @since August 2026
 */
public class ModuleRepository {
    private Connection connection;
    
    
    /**
     * Creates a repository connection to the Layer7 SQLite database
     * and initializes the learning module and submodule tables if
     * they do not already exist.
     * 
     * @throws RuntimeException if the database connection or table initialization fails
     */
    public ModuleRepository() {

        try {
            SQLiteDataSource dataSource = new SQLiteDataSource();
            dataSource.setUrl("jdbc:sqlite:layer7.db"); // Same database file as UserRepository
            connection = dataSource.getConnection();
            try (Statement stmt = connection.createStatement()){;
            stmt.execute("PRAGMA foreign_keys = ON");
            stmt.execute("PRAGMA busy_timeout = 5000");
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS learning_modules ("
                + "moduleID TEXT PRIMARY KEY,"
                + "title TEXT NOT NULL,"
                + "description TEXT,"
                + "unlockPrice INTEGER NOT NULL DEFAULT 0,"
                + "completionReward INTEGER NOT NULL DEFAULT 0"
                + ")"
            );

            stmt.execute(
                "CREATE TABLE IF NOT EXISTS submodules ("
                + "subModuleID TEXT PRIMARY KEY,"
                + "moduleID TEXT NOT NULL,"
                + "title TEXT NOT NULL,"
                + "description TEXT,"
                + "subModuleOrder INTEGER NOT NULL,"
                + "FOREIGN KEY(moduleID) REFERENCES learning_modules(moduleID)"
                + " ON DELETE CASCADE"
                + ")"
            );
            stmt.close();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Unable to initialize ModuleRepository", e);
        }
    }

    /**
     * Adds a new learning module to the database.
     * @param module the learning module to add
     * @return {@code true} if the module was successfully inserted;
     * otherwise {@code false}
     * @throws IllegalArgumentException if the module is {@code null}
     */
    public boolean addModule(LearningModule module) {
        if (module == null) {
            throw new IllegalArgumentException("Module cannot be null");
        }
        String sql = "INSERT INTO learning_modules (moduleID, title, description, unlockPrice, completionReward) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, module.getModuleID());
            pstmt.setString(2, module.getTitle());
            pstmt.setString(3, module.getDescription());
            pstmt.setDouble(4, module.getUnlockPrice());
            pstmt.setDouble(5, module.getCompletionReward());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error adding module: " + e.getMessage());
            return false;
        }
    }

    /**
     * Adds a new submodule to an existing learning module.
     * @param moduleID Module adding submodule to
     * @param subModule the submodule to add
     * @return {@code true} if the submodule was successfully inserted;
     * otherwise {@code false}
     * @throws IllegalArgumentException if the module ID is invalid or the submodule is {@code null}
     */
    public boolean addSubModule(String moduleID, SubModule subModule) {
        if (subModule == null || moduleID == null || moduleID.isEmpty()) {
            throw new IllegalArgumentException("SubModule and moduleID cannot be null or empty");
        }
        String sql = "INSERT INTO submodules (subModuleID, moduleID, title, description, subModuleOrder) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, subModule.getSubModuleID());
            pstmt.setString(2, moduleID);
            pstmt.setString(3, subModule.getTitle());
            pstmt.setString(4, subModule.getDescription());
            pstmt.setInt(5, subModule.getSubModuleOrder());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error adding submodule: " + e.getMessage());
            return false;
        }
    }

    // getModuleById
    /**
     * Retrieves a learning module using its unique identifier.
     * @param moduleID the unique identifier of the module
     * @return the matching learning module, or {@code null} if no matching module is found
     * @throws IllegalArgumentException if the module ID is null or empty
     */
    public LearningModule getModuleById(String moduleID) {
        if (moduleID == null || moduleID.isEmpty()) {
            throw new IllegalArgumentException("Module ID cannot be null or empty");
        }
        String sql = "SELECT * FROM learning_modules WHERE moduleID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, moduleID);
            try (ResultSet results = pstmt.executeQuery()) {
                if (results.next()) {
                    String title = results.getString("title");
                    String description = results.getString("description");
                    double unlockPrice = results.getDouble("unlockPrice");
                    double completionReward = results.getDouble("completionReward");
                    return new LearningModule(moduleID, title, description, unlockPrice, completionReward);
            } else {
                return null; // Module not found
            }
        }
        } catch (SQLException e) {
            System.out.println("Error retrieving module: " + e.getMessage());
            return null;
        }
    }

    // getSubModuleById
    /**
     * Retrieves a submodule using its unique identifier.
     * @param subModuleID the unique identifier of the submodule
     * @return the matching submodule, or {@code null} if no matching submodule is found
     * @throws IllegalArgumentException if the submodule ID is null or empty
     */
    public SubModule getSubModuleById(String subModuleID) {
        if (subModuleID == null || subModuleID.isEmpty()) {
            throw new IllegalArgumentException("SubModule ID cannot be null or empty");
        }
        String sql = "SELECT * FROM submodules WHERE subModuleID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, subModuleID);
            try (ResultSet results = pstmt.executeQuery()){
            if (results.next()) {
                String title = results.getString("title");
                String description = results.getString("description");
                int subModuleOrder = results.getInt("subModuleOrder");
                return new SubModule(subModuleID, title, description, subModuleOrder);
            } else {
                return null; // SubModule not found
            }
        }
        } catch (SQLException e) {
            System.out.println("Error retrieving submodule: " + e.getMessage());
            return null;
        }
    }

    // updateModule
    /**
     * Updates the title, description, unlock price, and completion reward of an existing learning module.
     * @param module the learning module containing the updated values
     * @return {@code true} if the module was successfully updated; otherwise {@code false}
     * @throws IllegalArgumentException if the module is {@code null}
     */
    public boolean updateModule(LearningModule module) {
        if (module == null) {
            throw new IllegalArgumentException("Module cannot be null");
        }
        String sql = "UPDATE learning_modules SET title = ?, description = ?, unlockPrice = ?, completionReward = ? WHERE moduleID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, module.getTitle());
            pstmt.setString(2, module.getDescription());
            pstmt.setDouble(3, module.getUnlockPrice());
            pstmt.setDouble(4, module.getCompletionReward());
            pstmt.setString(5, module.getModuleID());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error updating module: " + e.getMessage());
            return false;
        }
    }

    /**
     * Updates the title, description, and display order of an existing submodule.
     * @param subModule the submodule containing the updated values
     * @return {@code true} if the submodule was successfully updated; otherwise {@code false}
     * @throws IllegalArgumentException if the submodule is {@code null}
     */
    // updateSubModule
    public boolean updateSubModule(SubModule subModule) {
        if (subModule == null) {
            throw new IllegalArgumentException("SubModule cannot be null");
        }
        String sql = "UPDATE submodules SET title = ?, description = ?, subModuleOrder = ? WHERE subModuleID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, subModule.getTitle());
            pstmt.setString(2, subModule.getDescription());
            pstmt.setInt(3, subModule.getSubModuleOrder());
            pstmt.setString(4, subModule.getSubModuleID());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error updating submodule: " + e.getMessage());
            return false;
        }
    }

    // deleteModule
    /**
     * Deletes a learning module from the database.
     * Associated submodules are also deleted through the database
     * foreign-key cascade relationship.
     * @param moduleID the unique identifier of the module to delete
     * @return {@code true} if the module was successfully deleted; otherwise {@code false}
     * @throws IllegalArgumentException if the module ID is null or empty
     */
    public boolean deleteModule(String moduleID) {
        if (moduleID == null || moduleID.isEmpty()) {
            throw new IllegalArgumentException("Module ID cannot be null or empty");
        }
        String sql = "DELETE FROM learning_modules WHERE moduleID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, moduleID);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting module: " + e.getMessage());
            return false;
        }
    }

    // deleteSubModule
    /**
     * Deletes a submodule from the database.
     * @param subModuleID the unique identifier of the submodule to delete
     * @return {@code true} if the submodule was successfully deleted; otherwise {@code false}
     * @throws IllegalArgumentException if the submodule ID is null or empty
     */
    public boolean deleteSubModule(String subModuleID) {
        if (subModuleID == null || subModuleID.isEmpty()) {
            throw new IllegalArgumentException("SubModule ID cannot be null or empty");
        }
        String sql = "DELETE FROM submodules WHERE subModuleID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, subModuleID);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting submodule: " + e.getMessage());
            return false;
        }
    }

    // getAllModules
    /**
     * Retrieves all learning modules stored in the database.
     * @return a list containing all stored learning modules
     */
    public ArrayList<LearningModule> getAllModules() {
        String sql = "SELECT * FROM learning_modules";
        ArrayList<LearningModule> modules = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
        ResultSet results = stmt.executeQuery(sql)) {
            while (results.next()) {
                String moduleID = results.getString("moduleID");
                String title = results.getString("title");
                String description = results.getString("description");
                double unlockPrice = results.getDouble("unlockPrice");
                double completionReward = results.getDouble("completionReward");
                LearningModule module = new LearningModule(moduleID, title, description, unlockPrice, completionReward);
                modules.add(module);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving modules: " + e.getMessage());
        }
        return modules;
    }

    // getSubModules
    /**
     * Retrieves all submodules associated with a learning module.
     * The returned submodules are ordered by their configured submodule order.
     * @param moduleID the unique identifier of the parent module
     * @return a list containing the module's submodules
     * @throws IllegalArgumentException if the module ID is null or empty
     */
    public ArrayList<SubModule> getSubModules(String moduleID) {
        if (moduleID == null || moduleID.isEmpty()) {
            throw new IllegalArgumentException("Module ID cannot be null or empty");
        }
        String sql = "SELECT * FROM submodules WHERE moduleID = ? ORDER BY subModuleOrder ASC";
        ArrayList<SubModule> subModules = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, moduleID);
            try (ResultSet results = pstmt.executeQuery()){
            while (results.next()) {
                String subModuleID = results.getString("subModuleID");
                String title = results.getString("title");
                String description = results.getString("description");
                int subModuleOrder = results.getInt("subModuleOrder");
                SubModule subModule = new SubModule(subModuleID, title, description, subModuleOrder);
                subModules.add(subModule);
            }
        }
        } catch (SQLException e) {
            System.out.println("Error retrieving submodules: " + e.getMessage());
        }
        return subModules;
    }

}
