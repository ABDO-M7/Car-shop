package model;

public class User {
    private String id;
    private String username;
    private String password;
    private String role;
    private String refId;

    public User(){}

    public User(String id, String username, String name, String role, String refId) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.refId = refId;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; } 
    public void setUsername(String username) {this.username = username;}
    public String getUsername(){return username;}
    public String getPassword() {return password;}
    public void setPassword(String pass) {this.password = pass;}
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getRefId() { return refId; }
    public void setRefId(String refId) { this.refId = refId; }
    
   
    
}
