public class userManager {
    private userRepository repository;

    public userManager(userRepository repository) {
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

    ////// MUST ADD ASAP:
    /// Add public User userLogin(String UserName, String Password){
    /// use find user method in respositoy
    /// if null then return null
    /// if found then run user.getpassword and compare that to submitted password
    /// if not equal then return null "Incorrect Password
    // if equals then return user"}

    ////// NEED to add createUser, resetPassword, deleteUser, promoteToAdmin, banUser, findUser

    
}
