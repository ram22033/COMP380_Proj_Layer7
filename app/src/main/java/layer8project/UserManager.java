package layer8project;
import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;

// This class manages user-related operations, including changing passwords, balances, emails, promoting users to admin, banning users, creating and deleting users, resetting passwords, and handling user login. It interacts with the UserRepository to perform these operations and ensures that only users with admin privileges can perform certain actions.
// changePassword, changeBalance, changeEmail, promoteToAdmin, banUser, createUser, deleteUser, resetPassword, userLogin, findUser, changeOwnPassword

public class UserManager {
    private UserRepository repository;


    public UserManager(UserRepository repository) {
        this.repository = repository;
    }
    
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


    public boolean createUser(User currentUser, User userToCreate){
    if(!currentUser.isAdmin()){
        System.out.println("Access Denied");
        return false;
    }
    String hashedPassword =BCrypt.hashpw(userToCreate.getPassword(),BCrypt.gensalt());
    userToCreate.setPassword(hashedPassword);
    return repository.addUser(userToCreate);
    }



    public boolean deleteUser(User currentUser, User userToDelete){
        if(!currentUser.isAdmin()){
            System.out.println("Access Denied");
            return false;
        }
        return repository.deleteUser(userToDelete.getUsername());
    }

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
