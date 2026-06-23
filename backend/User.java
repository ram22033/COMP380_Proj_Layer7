
public enum Role {
    USER, ADMIN
}


public class User {
    private String userName;
    private String password;
    private Role role;
    
    public User(String username, String password, Role role){
        this.username = username;
        this.password = password;
        this.Role = role;
    }

    public boolean isAdmin() {
        return role == Role.ADMIN;
    }

    public String getusername() {
        return username;
    }

    public void setPassword(string newPassword){
        this.password = newPassword;
    }


}
