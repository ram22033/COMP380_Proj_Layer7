package layer8project;
import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Coordinates user account operations within the Layer7
 * learning management system.
 *
 * UserManager serves as the intermediary between the graphical
 * user interface and UserRepository. It manages authentication,
 * password security, user account updates, administrator actions,
 * and role-based access control.
 *
 * Responsibilities:
 * - Authenticate users
 * - Hash and verify passwords
 * - Manage user account information
 * - Enforce administrator permissions
 * - Create and delete user accounts
 * - Promote, ban, and manage user roles
 * - Keep in-memory user objects synchronized with the database
 *
 * Main Functions:
 * - userLogin()
 * - createUser()
 * - deleteUser()
 * - changePassword()
 * - changeOwnPassword()
 * - resetPassword()
 * - changeBalance()
 * - changeEmail()
 * - promoteToAdmin()
 * - banUser()
 * - findUser()
 * - getAllUsers()
 *
 * @author Christopher Sparks
 * @since August 2026
 */
public class UserManager {
    private UserRepository repository;

    /**
     * Creates a user manager that performs account operations through
     * the provided repository.
     * 
     * @param repository
     */
    public UserManager(UserRepository repository) {
        this.repository = repository;
    }
    
    /**
     * Allows an administrator to change another user's password.
     * 
     * The new password is hashed before it is saved to the database.
     * 
     * @param currentUser the administrator performing the change
     * @param userToChange the user whose password will be changed
     * @param newPassword the new plain-text password
     * @return {@code true} if the password was successfully updated;
     * otherwise {@code false}
     * @throws IllegalArgumentException if the new password is invalid
     */
    public boolean changePassword(User currentUser, User userToChange, String newPassword){
        if(!currentUser.isAdmin()){
            System.out.println("Access Denied");
            return false;
        }
        if (newPassword == null || newPassword.isEmpty()) {
            throw new IllegalArgumentException(
                "Password cannot be null or empty"
            );
        }
        String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        boolean success = repository.updatePassword(userToChange.getUsername(),hashedPassword);
        if (success) {
            userToChange.setPassword(hashedPassword);
        }
        return success;
    }

    /**
     * Allows an administrator to update a user's account balance.
     * 
     * @param currentUser the administrator performing the change
     * @param userToChange the user whose balance will be updated
     * @param newBalance the new account balance
     * @return {@code true} if the balance was successfully updated;
     * otherwise {@code false}
     * @throws IllegalArgumentException if the balance is negative
     */
    public boolean changeBalance(User currentUser, User userToChange, double newBalance){
        if(!currentUser.isAdmin()){
            System.out.println("Access Denied");
            return false;
        }
        if (newBalance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        boolean success = repository.updateBalance(userToChange.getUsername(),newBalance);
        if (success) {
            userToChange.setBalance(newBalance);
        }
        return success;
    }
    
    /**
     * Allows an administrator to update a user's email address.
     * 
     * @param currentUser the administrator performing the change
     * @param userToChange the user whose email address will be updated
     * @param newEmail the new email address
     * @return {@code true} if the email was successfully updated;
     * otherwise {@code false}
     * @throws IllegalArgumentException if the email is invalid
     */
    public boolean changeEmail(User currentUser, User userToChange, String newEmail){
        if(!currentUser.isAdmin()){
            System.out.println("Access Denied");
            return false;
        }
        if (newEmail == null || newEmail.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        boolean success = repository.updateEmail(userToChange.getUsername(),newEmail);
        if (success) {
            userToChange.setEmail(newEmail);
        }
        return success;
    }

    /**
     * Promotes a user to the administrator role.
     *  
     * @param currentUser the administrator performing the promotion
     * @param userToPromote the user receiving administrator privileges
     * @return {@code true} if the user's role was successfully updated;
     * otherwise {@code false}
     */
    public boolean promoteToAdmin(User currentUser, User userToPromote){
        if(!currentUser.isAdmin()){
            System.out.println("Access Denied");
            return false;
        }
        boolean success = repository.updateRole(userToPromote.getUsername(),Role.ADMIN);
        if (success) {
            userToPromote.setRole(Role.ADMIN);
        }
        return success;
    }
    
    /**
     * Assigns the banned role to a user.
     * 
     * @param currentUser the administrator performing the action
     * @param userToBan the user whose account will be banned
     * @return {@code true} if the user's role was successfully updated;
     * otherwise {@code false}
     */
    public boolean banUser(User currentUser, User userToBan){
        if(!currentUser.isAdmin()){
            System.out.println("Access Denied");
            return false;
        }
        boolean success = repository.updateRole(userToBan.getUsername(),Role.BANNED);
        if (success) {
            userToBan.setRole(Role.BANNED);
        }
        return success;
    }

    /**
     * Allows an administrator to create a new user account.
     * 
     * The user's password is hashed before the account is saved.
     * 
     * @param currentUser the administrator creating the account
     * @param userToCreate the new user account
     * @return {@code true} if the account was successfully created;
     * otherwise {@code false}
     */
    public boolean createUser(User currentUser, User userToCreate){
    if(!currentUser.isAdmin()){
        System.out.println("Access Denied");
        return false;
    }
    String hashedPassword =BCrypt.hashpw(userToCreate.getPassword(),BCrypt.gensalt());
    userToCreate.setPassword(hashedPassword);
    return repository.addUser(userToCreate);
    }

    /**
     * Allows an administrator to delete a user account.
     * 
     * @param currentUser the administrator deleting the account
     * @param userToDelete the account being deleted
     * @return {@code true} if the account was successfully deleted;
     * otherwise {@code false}
     */
    public boolean deleteUser(User currentUser, User userToDelete){
        if(!currentUser.isAdmin()){
            System.out.println("Access Denied");
            return false;
        }
        return repository.deleteUser(userToDelete.getUsername());
    }

    /**
     * Allows an administrator to reset another user's password.
     * 
     * The new password is hashed before being stored.
     * 
     * @param currentUser the administrator performing the reset
     * @param userToReset the user whose password will be reset
     * @param newPassword the new plain-text password
     * @return {@code true} if the password was successfully reset;
     * otherwise {@code false}
     * @throws IllegalArgumentException if the password is invalid
     */
    public boolean resetPassword(User currentUser, User userToReset, String newPassword){
        if(!currentUser.isAdmin()){
            System.out.println("Access Denied");
            return false;
        }
        if (newPassword == null || newPassword.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        boolean success = repository.updatePassword(userToReset.getUsername(), hashedPassword);
        if (success) {
            userToReset.setPassword(hashedPassword);
        }
        return success;
    }

    /**
     * Authenticates a user using a username and password.
     * 
     * The supplied password is compared with the stored BCrypt hash.
     * 
     * @param userName the username entered by the user
     * @param password the plain-text password entered by the user
     * @return the authenticated user, or {@code null} if authentication fails
     */
    public User userLogin(String userName, String password){
        User user = repository.findUser(userName);
        if(user == null){
            return null;
        }
        if(!BCrypt.checkpw(password, user.getPassword())){
            return null;
        }
        return user;
    }

    public User findUser(String userName){
        return repository.findUser(userName);
    }

    public ArrayList<User> getAllUsers() {
        return repository.getAllUsers();
    }
    
    /**
     * Allows a user to change their own password after verifying their current password.
     * 
     * @param user the user changing their password
     * @param currentPassword the user's current plain-text password
     * @param newPassword the new plain-text password
     * @return {@code true} if the password was successfully changed;
     * otherwise {@code false}
     */
    public boolean changeOwnPassword(User user, String currentPassword, String newPassword) {
        // Verify current password is correct
        if (!BCrypt.checkpw(currentPassword, user.getPassword())) {
            return false;
        }
        // Hash the new password
        String hashedNewPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        // Update in database
        boolean success = repository.updatePassword(user.getUsername(), hashedNewPassword);
        if (success) {
            // Update the in-memory user object too so it stays in sync
            user.setPassword(hashedNewPassword);
        }
        return success;
    }
    
}
