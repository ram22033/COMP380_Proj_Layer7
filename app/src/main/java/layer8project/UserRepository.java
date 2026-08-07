package layer8project;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.sqlite.SQLiteDataSource;

/**
 * Manages database operations for user accounts within the
 * Layer7 learning management system.
 *
 * UserRepository connects to the Layer7 SQLite database and
 * provides methods for creating, retrieving, updating, and
 * deleting user records. It stores account credentials, email
 * addresses, roles, and account balances.
 *
 * Responsibilities:
 * - Initialize the user database table
 * - Add new user accounts
 * - Retrieve all user accounts
 * - Find users by username
 * - Update passwords
 * - Update account balances
 * - Update email addresses
 * - Update user roles
 * - Delete user accounts
 *
 * Main Functions:
 * - addUser()
 * - getAllUsers()
 * - findUser()
 * - updatePassword()
 * - updateBalance()
 * - updateEmail()
 * - updateRole()
 * - deleteUser()
 *
 * @author Christopher Sparks
 * @since August 2026
 */
public class UserRepository {
    private Connection connection;

    /**
     * Creates a connection to the Layer7 SQLite database and
     * initializes the users table if it does not already exist.
     */
    public UserRepository() {
        try {
            SQLiteDataSource dataSource = new SQLiteDataSource();
            dataSource.setUrl("jdbc:sqlite:layer7.db");
            connection = dataSource.getConnection();

            try (Statement stmt = connection.createStatement()){
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS users (" +
                "username TEXT PRIMARY KEY, " +
                "password TEXT NOT NULL, " +
                "email TEXT, " +
                "role TEXT NOT NULL, " +
                "balance REAL DEFAULT 0.0" +
                ")"
            );
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    /**
     * Adds a new user account to the database.
     * 
     * @param user the user account to add
     * @return {@code true} if the user was successfully inserted;
     * otherwise {@code false}
     */
    public boolean addUser(User user) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO users (username, password, email, role, balance) VALUES (?, ?, ?, ?, ?)"
            );
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getRole().name());
            stmt.setDouble(5, user.getBalance());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error adding user: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves all user accounts stored in the database.
     * 
     * @return a list containing all stored users
     */
    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                User user = new User(
                    rs.getString("username"),
                    rs.getString("password"),
                    Role.valueOf(rs.getString("role"))
                );
                user.setEmail(rs.getString("email"));
                user.setBalance(rs.getDouble("balance"));
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Error getting users: " + e.getMessage());
        }
        return users;
    }

    /**
     * Retrieves a user account using its username.
     * 
     * @param username the username of the account to locate
     * @return the matching user, or {@code null} if no account is found
     */
    public User findUser(String username) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                "SELECT * FROM users WHERE username = ?"
            );
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()){
            if (rs.next()) {
                User user = new User(
                    rs.getString("username"),
                    rs.getString("password"),
                    Role.valueOf(rs.getString("role"))
                );
                user.setEmail(rs.getString("email"));
                user.setBalance(rs.getDouble("balance"));
                return user;
            
            }
        }
        } catch (SQLException e) {
            System.out.println("Error finding user: " + e.getMessage());
        }
        return null;
    }
    /**
     * Updates the stored password for a user account.
     * 
     * The supplied password should already be hashed before this method is called.
     * 
     * @param username the username of the account to update
     * @param hashedPassword the new hashed password
     * @return {@code true} if the password was successfully updated;
     * otherwise {@code false}
     */
    public boolean updatePassword(String username, String hashedPassword) {
    try {
        PreparedStatement stmt = connection.prepareStatement(
            "UPDATE users SET password = ? WHERE username = ?"
        );
        stmt.setString(1, hashedPassword);
        stmt.setString(2, username);
        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0;
    } catch (SQLException e) {
        System.out.println("Error updating password: " + e.getMessage());
        return false;
        }
    }

    /**
     * Updates the account balance for a user.
     * 
     * @param username the username of the account to update
     * @param newBalance the new account balance
     * @return {@code true} if the balance was successfully updated;
     * otherwise {@code false}
     */
    public boolean updateBalance(String username, double newBalance) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                "UPDATE users SET balance = ? WHERE username = ?"
            );
            stmt.setDouble(1, newBalance);
            stmt.setString(2, username);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error updating balance: " + e.getMessage());
            return false;
            }
    }

    /**
     * Updates the email address associated with a user account.
     * 
     * @param username the username of the account to update
     * @param newEmail the new email address
     * @return {@code true} if the email was successfully updated;
     * otherwise {@code false}
     */
    public boolean updateEmail(String username, String newEmail){
        try {
            PreparedStatement stmt = connection.prepareStatement("UPDATE users SET email = ? WHERE username = ?");
            stmt.setString(1, newEmail);
            stmt.setString(2, username);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error updating email: " + e.getMessage());
            return false;
        }

    }

    /**
     * Updates the assigned role for a user account.
     * 
     * @param username the username of the account to update
     * @param newRole the new role assigned to the user
     * @return {@code true} if the role was successfully updated;
     * otherwise {@code false}
     */
    public boolean updateRole(String username, Role newRole) {
        try {
            PreparedStatement stmt = connection.prepareStatement("UPDATE users SET role = ? WHERE username = ?");
            stmt.setString(1, newRole.name());
            stmt.setString(2, username);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error updating role: " + e.getMessage());
            return false;
        }
    }

    /**
     * Deletes a user account from the database.
     * 
     * @param username the username of the account to delete
     * @return {@code true} if the user was successfully deleted;
     * otherwise {@code false}
     */
    public boolean deleteUser(String username) {
        try {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM users WHERE username = ?");
            stmt.setString(1, username);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting user: " + e.getMessage());
            return false;
        }
    }



}