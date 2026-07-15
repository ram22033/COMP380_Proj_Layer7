package layer8project;
import org.sqlite.SQLiteDataSource;
import java.sql.*;
import java.util.ArrayList;

public class UserRepository {
    private Connection connection;

    public UserRepository() {
        try {
            SQLiteDataSource dataSource = new SQLiteDataSource();
            dataSource.setUrl("jdbc:sqlite:layer7.db");
            connection = dataSource.getConnection();

            Statement stmt = connection.createStatement();
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS users (" +
                "username TEXT PRIMARY KEY, " +
                "password TEXT NOT NULL, " +
                "email TEXT, " +
                "role TEXT NOT NULL, " +
                "balance REAL DEFAULT 0.0" +
                ")"
            );
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    public void addUser(User user) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO users (username, password, email, role, balance) VALUES (?, ?, ?, ?, ?)"
            );
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getRole().name());
            stmt.setDouble(5, user.getBalance());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding user: " + e.getMessage());
        }
    }

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

    public User findUser(String username) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                "SELECT * FROM users WHERE username = ?"
            );
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
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
        } catch (SQLException e) {
            System.out.println("Error finding user: " + e.getMessage());
        }
        return null;
    }
}