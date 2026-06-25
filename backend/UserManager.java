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
        if(user == null){ // if user does not exist
            return null;
        }
        if(!user.getPassword().equals(password)){ // if password is incorrect
            return null;
        }
        return user; // return user if login is successful
    }

    public User findUser(String userName){
        return repository.findUser(userName);
    }


    
}
