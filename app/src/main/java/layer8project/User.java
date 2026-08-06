package layer8project;

/**
 * Represents a user within the Layer7 learning
 * management system.
 *
 * A user account stores authentication information,
 * account details, assigned permissions, and the
 * user's available account balance. Each user is
 * assigned a role that determines the features and
 * administrative privileges available within the
 * application.
 *
 * Responsibilities:
 * - Store user account information
 * - Store authentication credentials
 * - Maintain account balance
 * - Maintain user role
 * - Provide administrative role verification
 *
 * Main Functions:
 * - isAdmin()
 * - getUsername()
 * - getRole()
 * - getBalance()
 * - getEmail()
 * - setPassword()
 * - setBalance()
 * - setEmail()
 * - setRole()
 *
 * @author Christopher Sparks
 * @since August 2026
 */
public class User {
    private String userName;
    private String password;
    private Role role;
    private double balance;
    private String email;
    
    /**
     * Creates a new user account.
     * 
     * @param username the user's username
     * @param password the user's password
     * @param role the user's assigned role
     */
    public User(String username, String password, Role role){
        // Check for null or empty values
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }
        this.userName = username;
        this.password = password;
        this.role = role;
        this.balance = 0.0;
        this.email = "";
    }

    /**
     * Determines whether this user has administrator privileges.
     * 
     * @return {@code true} if the user is an administrator;
     * otherwise {@code false}
     */
    public boolean isAdmin() {
        return role == Role.ADMIN;
    }

    public String getUsername() {
        return userName;
    }

    /**
     * Updates the user's password.
     * 
     * @param newPassword the new password
     */
    public void setPassword(String newPassword){
        this.password = newPassword;
    }

    /**
     * Updates the user's balance
     * 
     * @param newBalance the new balance
     */
    public void setBalance(double newBalance){
        this.balance = newBalance;
    }

    /**
     * Updates the user's email
     * 
     * @param newEmail the new email
     */
    public void setEmail(String newEmail){
        this.email = newEmail;
    }

    /**
     * Updates the user's role
     * 
     * @param newRole the new role
     */
    public void setRole(Role newRole){
        this.role = newRole;
    }

    public String getEmail() {
        return email;
    }

    public double getBalance() {
        return balance;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }


}
