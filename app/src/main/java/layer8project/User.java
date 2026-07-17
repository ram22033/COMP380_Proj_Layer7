package layer8project;

// This class represents a user in the system, encapsulating their username, password, role, balance, and email. It provides methods to access and modify these attributes while ensuring that the user's role can be checked for administrative privileges.
// isAdmin, getUsername, setPassword, setBalance, setEmail, setRole, getEmail, getBalance, getPassword, getRole

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
