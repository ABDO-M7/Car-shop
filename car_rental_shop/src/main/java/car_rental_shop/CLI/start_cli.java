package car_rental_shop.CLI;

import java.util.Scanner;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.model.Filters;

import model.*;
import service.*;
import dao.UserDao;
import service.AdminService;
import model.admin;
import model.User;

public class start_cli {
    private static final CarService carService = new CarService();
    private static final CustomerService customerService = new CustomerService();
    private static final RentService rentService = new RentService();
    private static final AdminService adminService = new AdminService();


    private static final Scanner scanner = new Scanner(System.in);

   
    static {
        System.out.println();System.out.println();
        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║  Welcome to the car rental shop  ║ ");
        System.out.println("╚══════════════════════════════════╝");
        System.out.println();

    }
    
    public static void main(String[] args) {
        
        String username;
        String password;
        
        while (true) {

            System.out.println("\n --Login menu--");
            System.out.print("Username:");
            username=scanner.nextLine();
            System.out.print("Password:");
            password=scanner.nextLine(); 
            
            User user =Login.login(username, password);
            if(user==null){
                System.out.println("incoorect");
                main(args);
            }
            switch (user.getRole()) {
                case "admin":
                    System.out.println("\n Welcome "+user.getUsername());
                    adminMenu();
                    break;
                case "manager":
                    System.out.println("\n Welcome "+user.getUsername());
                    mangerMenu();
                    break;
                case "customer":
                    System.out.println("\n Welcome "+user.getUsername());
                     customerMenu(user.getRefId());
                     break;
                default:
                    System.out.println("incoorect");
            }


        }

    }

    public static void adminMenu(){
        
        while(true){
            
            System.out.println("\n--- Car Rental Admin System ---");
            System.out.println("1. Car Management");
            System.out.println("2. Customer Management");
            System.out.println("3. Rental Transactions");
            System.out.println("0. Exit");
            System.out.println("-1. Previous");
            System.out.print("Select option: ");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    carManagement();
                    break;
                case 2:
                    customerManagement();
                    break;
                case 3:
                    rentalTransactions();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                case -1:
                    return;
                default:
                    System.out.println("Invalid option");
            }


        }
    }

   public static void mangerMenu(){
        System.out.println("\n--- Car Rental Manger System ---");
        System.out.println("1. Car Management");
        System.out.println("2. Customer Management");
        System.out.println("3. Admins Management");
        System.out.println("4. Rental Transactions");
        System.out.println("0. Exit");
        System.out.println("-1. Previous");
        System.out.print("Select option: ");
        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1:
                carManagement();
                break;
            case 2:
                 customerManagement();
                break;
            case 3:
                adminManagement();
                break;
            case 4:
                rentalTransactions();
                break;
            case 0:
                System.out.println("Exiting...");
                break;
            case -1:
                return;
            default:
                System.out.println("Invalid option");
        }

   } 
   public static void customerMenu(String id){

    while (true) {
        System.out.println("\n--- Car Rental Customer System ---");
        System.out.println("1.show my cars");
        System.out.println("2.make a rental");
        System.out.println("3.show my profile");
        System.out.println("0. Exit");
        System.out.println("-1. Previous");
        System.out.print("Select option: ");
        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1:
            showMycars(id);
                break;
            case 2:
                makeRental();
                break;
            case 3:
                // showMyProfile();
                break;
            case 0:
                System.out.println("Exiting...");
                break;
            case -1:
                return;
            default:
                System.out.println("Invalid option");
        }
    }

   }
   
   public static void showMycars(String id){

        customerService.showMyCars(id);

   }


   public static void carManagement(){

    while(true){
        System.out.println("\n--- Car Rental Car Management ---");
        System.out.println("1.add a car");
        System.out.println("2.show all cars");
        System.out.println("3.show available cars");
        System.out.println("4.show rented cars");
        System.out.println("5.show sold cars");
        System.out.println("6.Delete a car");
        System.out.println("7.Update a car");
        System.out.println("0. Exit");
        System.out.println("-1. Previous");
        System.out.print("Select option: ");
        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1:
                addCar();
                break;
            case 2:
                showAllCars();
                break;
            case 3:
                showAvailbleCars();
                break;
            case 4:
                showRentedCars();
                break;
            case 5:
                showSoldCars();
                break;
            case 6:
                deleteCar();
                break;  
            case 7:
                updateCar();
                break;      
        }
    }
}

    public static void addCar(){
        System.out.println("\n--- Car Rental Add Car ---");

        System.out.print("Enter car model: ");String model = scanner.nextLine();
        System.out.print("Enter car color: ");String color = scanner.nextLine();
        System.out.print("Enter car number of doors: ");int numberOfDoors = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter car year: ");int year = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter car price per day: ");int pricePerDay = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter car price: ");int price = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter car mileage: ");int mileage = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter car statue: ");String statue = scanner.nextLine();
        
        Car car = new Car(null,model, color, numberOfDoors, year, pricePerDay, price, mileage, statue);
        String id = carService.addCar(car);
        System.out.println("Car added with ID: " + id);
    

    }

    public static void showAllCars(){
        Bson filter = new Document(); 
        carService.showCars(filter);

    }

    public static void showAvailbleCars(){
        Bson filter=Filters.eq("statue", "availble");
        carService.showCars(filter);
    }

    public static void showRentedCars(){
        Bson filter=Filters.eq("statue", "rented");
        carService.showCars(filter);
    }

    public static void showSoldCars(){
        Bson filter=Filters.eq("statue", "sold");
        carService.showCars(filter);
    }

    public static void deleteCar(){
        showAllCars();

        System.out.print("Enter the id of the  car you want to delete:");String id=scanner.nextLine();
        carService.deleteCar(id);
    }

    public static void updateCar(){
        showAllCars();
        System.out.print("Enter the id of the  car you want to update:");String id=scanner.nextLine();
        Car car=findCar(id);
        if(car==null){
            System.out.println("INVALID ID");
            return;
        }else{
            String input;

            System.out.print("Enter car model: " + car.getModel() + ": ");
            input = scanner.nextLine().trim();
            car.setModel(getUpdatedValue(input, car.getModel()));

            // color
            System.out.print("Enter car color: " + car.getColor() + ": ");
            input = scanner.nextLine().trim();
            car.setColor(getUpdatedValue(input, car.getColor()));

            // number of doors
            System.out.print("Enter number of doors: " + car.getNumberOfDoors() + ": ");
            input = scanner.nextLine().trim();
            car.setNumberOfDoors(getUpdatedInt(input, car.getNumberOfDoors()));

            // year
            System.out.print("Enter car year: " + car.getYear() + ": ");
            input = scanner.nextLine().trim();
            car.setYear(getUpdatedInt(input, car.getYear()));

            // pricePerDay
            System.out.print("Enter car price per day: " + car.getPricePerDay() + ": ");
            input = scanner.nextLine().trim();
            car.setPricePerDay(getUpdatedInt(input, car.getPricePerDay()));

            // price
            System.out.print("Enter car price: " + car.getPrice() + ": ");
            input = scanner.nextLine().trim();
            car.setPrice(getUpdatedInt(input, car.getPrice()));

            // mileage
            System.out.print("Enter car mileage: " + car.getMileage() + ": ");
            input = scanner.nextLine().trim();
            car.setMileage(getUpdatedInt(input, car.getMileage()));

            // statue
            System.out.print("Enter car statue: " + car.isStatue() + ": ");
            input = scanner.nextLine().trim();
            car.setStatue(getUpdatedValue(input, car.isStatue()));
            
            carService.updateCar(id,car);
            System.out.println("update succsefly");

        }



    }
    
    public static void customerManagement(){
        while(true){
            System.out.println("1. add a Cutomer");
            System.out.println("2. show all customers");
            System.out.println("3. update customer");
            System.out.println("4. delete customer");
            System.out.print("Select option: ");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    addCustomer(customerService);
                    break;
                case 2:
                    showAllCustomers(customerService);
                    break;
                case 3:
                    updateCustomer(customerService);
                    break;
                case 4:
                    deleteCustomer(customerService);
                    break;
                case 0:
                    System.out.println("Exiting...");
                    System.exit(0);
                    return;
                case -1:
                    return;
                default:
                    System.out.println("Invalid option");
            }
        }
    }

    public static void addCustomer(CustomerService customerService) {
        System.out.println("\n--- Car Rental Add Customer ---");
        System.out.print("Enter customer name: "); String name = scanner.nextLine();
        System.out.print("Enter customer email: "); String email = scanner.nextLine();
        System.out.print("Enter customer license number: "); String licenseNumber = scanner.nextLine();
        System.out.print("Enter customer address: "); String address = scanner.nextLine();
        System.out.print("Enter customer phone number: "); String phoneNumber = scanner.nextLine();
        System.out.print("Enter username: "); String username = scanner.nextLine();
        System.out.print("Enter password: "); String password = scanner.nextLine();
        UserDao userDao = new UserDao();
        if (userDao.usernameExists(username)) {
            System.out.println("Username already exists. Please choose another.");
            return;
        }
        User user = new User(null, username, name, "customer", null);
        user.setPassword(password);
        String userId = userDao.addUser(user);
        user.setId(userId);
        user.setRefId(userId); // Link user and customer
        Customer customer = new Customer(null, name, email, licenseNumber, address, user, phoneNumber, null, null);
        String id = customerService.addCustomer(customer);
        String  r=userDao.AddRefID(id, userId);
        System.out.println("Customer added with ID: " + id);
    }

    public static void showAllCustomers(CustomerService customerService) {
        customerService.showAllCustomers();
    }

    public static void deleteCustomer(CustomerService customerService) {
        showAllCustomers(customerService);
        System.out.print("Enter the id of the customer you want to delete: ");
       String id = scanner.nextLine();
        customerService.deleteCustomer(id);
    }

    public static void updateCustomer(CustomerService customerService) {
        showAllCustomers(customerService);
        System.out.print("Enter the id of the customer you want to update: ");
        String id = scanner.nextLine();
        Customer oldCustomer = customerService.findCustomer(id);
        if (oldCustomer == null) {
            System.out.println("INVALID ID");
            return;
        }
        String input;
        // name
        System.out.print("Enter customer name: " + oldCustomer.getName() + ": ");
        input = scanner.nextLine().trim();
        String name = getUpdatedValue(input, oldCustomer.getName());
        // email
        System.out.print("Enter customer email: " + oldCustomer.getEmail() + ": ");
        input = scanner.nextLine().trim();
        String email = getUpdatedValue(input, oldCustomer.getEmail());
        // license number
        System.out.print("Enter customer license number: " + oldCustomer.getLicenseNumber() + ": ");
        input = scanner.nextLine().trim();
        String licenseNumber = getUpdatedValue(input, oldCustomer.getLicenseNumber());
        // address
        System.out.print("Enter customer address: " + oldCustomer.getAddress() + ": ");
        input = scanner.nextLine().trim();
        String address = getUpdatedValue(input, oldCustomer.getAddress());
        // phone number
        System.out.print("Enter customer phone number: " + oldCustomer.getPhoneNumber() + ": ");
        input = scanner.nextLine().trim();
        String phoneNumber = getUpdatedValue(input, oldCustomer.getPhoneNumber());
        // username
        System.out.print("Enter username: " + oldCustomer.getBaseUser().getUsername() + ": ");
        input = scanner.nextLine().trim();
        String username = getUpdatedValue(input, oldCustomer.getBaseUser().getUsername());
        // password
        System.out.print("Enter password: (leave blank to keep current): ");
        input = scanner.nextLine().trim();
        String password = getUpdatedValue(input, oldCustomer.getBaseUser().getPassword());
        // role
        System.out.print("Enter role: " + oldCustomer.getBaseUser().getRole() + ": ");
        input = scanner.nextLine().trim();
        String role = getUpdatedValue(input, oldCustomer.getBaseUser().getRole());
        // userId
        String userId = oldCustomer.getBaseUser().getRefId();
        User user = new User(userId, username, name, role, userId);
        user.setPassword(password);
        Customer customer = new Customer(id, name, email, licenseNumber, address, user, phoneNumber, null, null);
        customerService.updateCustomer(id, customer);
    }



    public  static void rentalTransactions(){
        while(true){
            System.out.println("1.make a rental");
            System.out.println("2.buy car");
            System.out.println("3.delete a rental");
            System.out.println("4.update rental");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    makeRental();
                    break;
                case 2:
                    // buyCar()
                    break;
                case 3:
                    // deleteRental()
                    break;
                case 4:
                    // updateRental();
                break;
                case 0:
                    System.out.println("Exiting...");
                    System.exit(0);
                    return;
                case -1:
                    return;
                default:
                    System.out.println("Invalid option");
            }        }
        

    }

    public static void makeRental(){

        String input;

        String customerID=null;
        String carID=null;

        System.out.print("enter the customer id");
        input=scanner.nextLine();
        customerID=getUpdatedValue(input, customerID);
        Customer customer =customerService.findCustomer(customerID);

         if(customer==null){
            System.out.println("Invalid ID");
         }

        System.out.print("enter the car id");
        input=scanner.nextLine();
        carID=getUpdatedValue(input, carID);
        Car car =findCar(carID);

        if(car==null || !car.isStatue().equals("available")){
            System.out.println("no availble car");
            return;
        }

        int card_number=-1;
        int pin=-1;

        System.out.print("enter the card number:");
        input=scanner.nextLine();
        card_number=getUpdatedInt(input, card_number);

        System.out.print("enter the pin:");
        input=scanner.nextLine();
        pin=getUpdatedInt(input, pin);

        rentService.makeRent(customer,car,card_number ,pin);

    }



  //////////////////////////////////////////helper methodes////////////////////////////////////////////////////////////////
    public static Car findCar(String id) {
        return carService.findCar(id);   
    }

    public static int getUpdatedInt(String input, int oldValue) {
        if (input.isEmpty()) return oldValue;
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number. Keeping old value.");
            return oldValue;
        }
    }

    public static String getUpdatedValue(String input, String oldValue) {
        return input.isEmpty() ? oldValue : input;
    }

    public static void adminManagement() {
        while (true) {
            System.out.println("\n--- Admin Management ---");
            System.out.println("1. List Admins");
            System.out.println("2. Add Admin");
            System.out.println("3. Update Admin");
            System.out.println("4. Delete Admin");
            System.out.println("0. Exit");
            System.out.println("-1. Previous");
            System.out.print("Select option: ");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    adminService.showAllAdmins();
                    break;
                case 2:
                    System.out.print("Enter admin name: "); String name = scanner.nextLine();
                    System.out.print("Enter admin email: "); String email = scanner.nextLine();
                    System.out.print("Enter admin phone: "); String phone = scanner.nextLine();
                    System.out.print("Enter username: "); String username = scanner.nextLine();
                    System.out.print("Enter password: "); String password = scanner.nextLine();
                    User user = new User(null, username, name, "admin", null);
                    user.setPassword(password);
                    admin adminObj = new admin(null, name, email, phone, user);
                    String id = adminService.addAdmin(adminObj);
                    System.out.println("Admin added with ID: " + id);
                    break;
                case 3:
                    System.out.print("Enter the id of the admin you want to update: ");
                    String updateId = scanner.nextLine();
                    // For simplicity, ask for all fields again
                    System.out.print("Enter admin name: "); name = scanner.nextLine();
                    System.out.print("Enter admin email: "); email = scanner.nextLine();
                    System.out.print("Enter admin phone: "); phone = scanner.nextLine();
                    System.out.print("Enter username: "); username = scanner.nextLine();
                    System.out.print("Enter password: "); password = scanner.nextLine();
                    user = new User(null, username, name, "admin", null);
                    user.setPassword(password);
                    adminObj = new admin(updateId, name, email, phone, user);
                    adminService.updateAdmin(updateId, adminObj);
                    break;
                case 4:
                    System.out.print("Enter the id of the admin you want to delete: ");
                    String deleteId = scanner.nextLine();
                    adminService.deleteAdmin(deleteId);
                    break;
                case 0:
                    System.out.println("Exiting...");
                    System.exit(0);
                    return;
                case -1:
                    return;
                default:
                    System.out.println("Invalid option");
            }
        }
    }
     
}
