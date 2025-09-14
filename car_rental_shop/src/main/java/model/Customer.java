package model;

import java.util.ArrayList;

public class Customer{

    private String id;
    private String name;
    private String email;
    private String licenseNumber;
    private String address;
    private String phoneNumber;
    private ArrayList<Car> boughtCars;
    private ArrayList<Car> rentedCars;

    private User baseUser;


    public Customer(){}
    
    public Customer(String id, String name, String email, String licenseNumber, String address, User baseUser, String phoneNumber, ArrayList<Car> boughtCars, ArrayList<Car> rentedCars) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.licenseNumber = licenseNumber;
        this.address = address;
        this.baseUser = baseUser;
        this.phoneNumber = phoneNumber;
        this.boughtCars = new ArrayList<>();
        this.rentedCars = new ArrayList<>();

    }


    public String getLicenseNumber() {return licenseNumber;}
    public void setLicenseNumber(String licenseNumber) {this.licenseNumber = licenseNumber;}
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public User getBaseUser() { return baseUser; }
    public void setBaseUser(User baseUser) { this.baseUser = baseUser; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public ArrayList<Car> getBoughtCars() { return boughtCars; }
    public void setBoughtCars(ArrayList<Car> boughtCars) { this.boughtCars = boughtCars; }
    public ArrayList<Car> getRentedCars() { return rentedCars; }
    public void setRentedCars(ArrayList<Car> rentedCars) { this.rentedCars = rentedCars; }
}
