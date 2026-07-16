package layer8project;
import org.mindrot.jbcrypt.BCrypt;
public class UserManager {
    private UserRepository repository;


    public UserManager(UserRepository repository) {
        this.repository = repository;
    }
    
    public void changePassword(User currentUser, User userToChange, String newPassword){
        if(!currentUser.isAdmin()){
            System.out.println("Access Denied");
            return;
        }
        userToChange.setPassword(newPassword);
    }

    public void changeBalance(User currentUser, User userToChange, double newBalance){
        if(!currentUser.isAdmin()){
            System.out.println("Access Denied");
            return;
        }
        userToChange.setBalance(newBalance);
    }

    public void changeEmail(User currentUser, User userToChange, String newEmail){
        if(!currentUser.isAdmin()){
            System.out.println("Access Denied");
            return;
        }
        userToChange.setEmail(newEmail);
    }

    public void promoteToAdmin(User currentUser, User userToPromote){
        if(!currentUser.isAdmin()){
            System.out.println("Access Denied");
            return;
        }
        userToPromote.setRole(Role.ADMIN);
    }

    public void banUser(User currentUser, User userToBan){
        if(!currentUser.isAdmin()){
            System.out.println("Access Denied");
            return;
        }
        userToBan.setRole(Role.BANNED);
    }

    public void createUser(User currentUser, User userToCreate){
    if(!currentUser.isAdmin()){
        System.out.println("Access Denied");
        return;
    }
    String hashedPassword = BCrypt.hashpw(userToCreate.getPassword(), BCrypt.gensalt());
    userToCreate.setPassword(hashedPassword);
    repository.addUser(userToCreate);
}

    public void deleteUser(User currentUser, User userToDelete){
        if(!currentUser.isAdmin()){
            System.out.println("Access Denied");
            return;
        }
        repository.getAllUsers().remove(userToDelete);
    }

    public void resetPassword(User currentUser, User userToReset, String newPassword){
        if(!currentUser.isAdmin()){
            System.out.println("Access Denied");
            return;
        }
        userToReset.setPassword(newPassword);
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
