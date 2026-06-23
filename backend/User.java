
public class User {

public enum Role {
    USER, ADMIN
}
    private String userName;
    private String password;
    private Role role;
    private double balance;
    private String email;
    
    public User(String username, String password, Role role){
        this.userName = username;
        this.password = password;
        this.role = role;
    }

    public boolean isAdmin() {
        return role == Role.ADMIN;
    }

    public String getusername() {
        return userName;
    }

    public void setPassword(String newPassword){
        this.password = newPassword;
    }

    public void setBalance(double newBalance){
        this.balance = newBalance;
    }

    public void setEmail(String newEmail){
        this.email = newEmail;
    }



}
