package model;

public class admin{
    private String id;
    private String name;
    private String email;
    private String phone;

    private User baseUser;

    public admin(){}
    
    public admin(String id, String name, String email, String phone, User baseUser) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.baseUser = baseUser;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public User getBaseUser() { return baseUser; }
    public void setBaseUser(User baseUser) { this.baseUser = baseUser; }
}
