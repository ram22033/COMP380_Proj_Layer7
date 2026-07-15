package layer8project;
public class User {
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

    public String getUsername() {
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
