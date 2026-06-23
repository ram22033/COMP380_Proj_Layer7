public class userManager {
    
    public void changePassword(User currentUser, User userToChange, String newPassword){
        if(!currentUser.isAdmin()){
            System.out.println("Access Denied");
            return;
        }
        userToChange.setPassword(newPassword);
    }

    public void changeBalance(User currentUser, User userToChange, String newBalance){
        if(!currentUser.isAdmin()){
            System.out.println("Access Denied");
            return;
        }
        userToChange.setBalance(newBalance);
    }

    public void createUser(

    )

    ////// NEED to add createUser, resetPassword, deleteUser, promoteToAdmin, banUser, findUser

    
}
