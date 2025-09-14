package model;

public class Car {
    private String id;
    private String model;
    private String color;
    private int numberOfDoors;
    private int year;
    private int pricePerDay;
    private int price;
    private int mileage;
    private String statue;

    public Car() {
    }

    public Car(String id, String model, String color, int numberOfDoors, int year, int pricePerDay, int price, int mileage, String statue) {
        this.id = id;
        this.model = model;
        this.color = color;
        this.numberOfDoors = numberOfDoors;
        this.year = year;
        this.pricePerDay = pricePerDay;
        this.price = price;
        this.mileage = mileage;
        this.statue = statue;
    }

    public String getId() {return id;}
    public void setId(String id) {this.id = id; }
    public String getModel() {return model;}
    public void setModel(String model) {this.model = model;}
    public String getColor() {return color;}
    public void setColor(String color) {this.color = color;}
    public int getNumberOfDoors() {return numberOfDoors;}
    public void setNumberOfDoors(int numberOfDoors) {this.numberOfDoors = numberOfDoors;}
    public int getYear() { return year;}
    public void setYear(int year) { this.year = year;}
    public int getPricePerDay() {return pricePerDay;}
    public void setPricePerDay(int pricePerDay) {  this.pricePerDay = pricePerDay; }
    public int getPrice() {return price;}
    public void setPrice(int price) { this.price = price; }
    public int getMileage() {  return mileage;}
    public void setMileage(int mileage) {this.mileage = mileage;}
    public String isStatue() {return statue;}
    public void setStatue(String statue) {this.statue = statue;}
}