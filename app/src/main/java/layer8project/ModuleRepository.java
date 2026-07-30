package layer8project;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.sqlite.SQLiteDataSource;

// This class serves as a repository for managing LearningModule and SubModule objects in a SQLite database. It provides methods to add, retrieve, update, and delete modules and submodules, as well as to retrieve all modules or submodules associated with a specific module. The class ensures that the database schema is created if it does not already exist and handles SQL exceptions appropriately.
// addModule, addSubModule, getModuleById, getSubModuleById, updateModule, updateSubModule, deleteModule, deleteSubModule, getAllModules, getSubModules

public class ModuleRepository {
    private Connection connection;

    public ModuleRepository() {

        try {
            SQLiteDataSource dataSource = new SQLiteDataSource();
            dataSource.setUrl("jdbc:sqlite:layer7.db"); // Same database file as UserRepository
            connection = dataSource.getConnection();
            Statement stmt = connection.createStatement();
            stmt.execute("PRAGMA foreign_keys = ON");
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

        } catch (SQLException e) {
            throw new RuntimeException("Unable to initialize ModuleRepository", e);
        }
    }

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
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error adding module: " + e.getMessage());
            return false;
        }
    }

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
    public LearningModule getModuleById(String moduleID) {
        if (moduleID == null || moduleID.isEmpty()) {
            throw new IllegalArgumentException("Module ID cannot be null or empty");
        }
        String sql = "SELECT * FROM learning_modules WHERE moduleID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, moduleID);
            ResultSet results = pstmt.executeQuery();
            if (results.next()) {
                String title = results.getString("title");
                String description = results.getString("description");
                double unlockPrice = results.getDouble("unlockPrice");
                double completionReward = results.getDouble("completionReward");
                return new LearningModule(moduleID, title, description, unlockPrice, completionReward);
            } else {
                return null; // Module not found
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving module: " + e.getMessage());
            return null;
        }
    }

    // getSubModuleById
    public SubModule getSubModuleById(String subModuleID) {
        if (subModuleID == null || subModuleID.isEmpty()) {
            throw new IllegalArgumentException("SubModule ID cannot be null or empty");
        }
        String sql = "SELECT * FROM submodules WHERE subModuleID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, subModuleID);
            ResultSet results = pstmt.executeQuery();
            if (results.next()) {
                String moduleID = results.getString("moduleID");
                String title = results.getString("title");
                String description = results.getString("description");
                int subModuleOrder = results.getInt("subModuleOrder");
                return new SubModule(subModuleID, title, description, subModuleOrder);
            } else {
                return null; // SubModule not found
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving submodule: " + e.getMessage());
            return null;
        }
    }

    // updateModule
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
    public ArrayList<LearningModule> getAllModules() {
        String sql = "SELECT * FROM learning_modules";
        ArrayList<LearningModule> modules = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet results = stmt.executeQuery(sql);
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
    public ArrayList<SubModule> getSubModules(String moduleID) {
        if (moduleID == null || moduleID.isEmpty()) {
            throw new IllegalArgumentException("Module ID cannot be null or empty");
        }
        String sql = "SELECT * FROM submodules WHERE moduleID = ? ORDER BY subModuleOrder ASC";
        ArrayList<SubModule> subModules = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, moduleID);
            ResultSet results = pstmt.executeQuery();
            while (results.next()) {
                String subModuleID = results.getString("subModuleID");
                String title = results.getString("title");
                String description = results.getString("description");
                int subModuleOrder = results.getInt("subModuleOrder");
                SubModule subModule = new SubModule(subModuleID, title, description, subModuleOrder);
                subModules.add(subModule);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving submodules: " + e.getMessage());
        }
        return subModules;
    }

}
