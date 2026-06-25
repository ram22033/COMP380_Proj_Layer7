import java.util.ArrayList;

public class UserRepository {
    private ArrayList<User> users = new ArrayList<>();

    public void addUser(User user){ // Adding a new user
        users.add(user);
    }

    public ArrayList<User> getAllUsers() { // Show all users
        return users;
    }
    
    public User findUser(String username) { // Finding specific user
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
    
}
