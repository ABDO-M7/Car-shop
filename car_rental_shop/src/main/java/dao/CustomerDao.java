package dao;

import model.Customer;
import model.User;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import db.MongoUtil;

import java.nio.file.DirectoryStream.Filter;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

public class CustomerDao {
    private final MongoCollection<Document> customers;

    public CustomerDao() {
        this.customers = MongoUtil.getDatabase().getCollection("customers");
    }

    public String addCustomer(Customer customer) {
        Document customerDoc = new Document();
        customerDoc.append("name", customer.getName());
        customerDoc.append("email", customer.getEmail());
        customerDoc.append("licenseNumber", customer.getLicenseNumber());
        customerDoc.append("address", customer.getAddress());
        customerDoc.append("phoneNumber", customer.getPhoneNumber());
        if (customer.getBaseUser() != null) {
            customerDoc.append("username", customer.getBaseUser().getUsername());
            customerDoc.append("password", customer.getBaseUser().getPassword());
            customerDoc.append("role", customer.getBaseUser().getRole());
            customerDoc.append("userId", customer.getBaseUser().getRefId());
        }
        customers.insertOne(customerDoc);
        return customerDoc.getObjectId("_id").toHexString();
    }

    public void showMyCars(String id){
        MongoCollection<Document> cars = MongoUtil.getDatabase().getCollection("cars");
        Document customer = customers.find(Filters.eq("_id", new ObjectId(id))).first();

        if (customer != null) {
            System.out.println("Customer: " + customer.getString("name"));

            List<ObjectId> carIds = (List<ObjectId>) customer.get("cars");

            if (carIds != null && !carIds.isEmpty()) {
                for (ObjectId carId : carIds) {
                    Document car = cars.find(Filters.eq("_id", carId)).first();
                    if (car != null) {
                        String model = car.getString("model");
                        int year = car.getInteger("year");
                        System.out.println("Car Model: " + model + ", Year: " + year);
                    }
                }
            } else {
                System.out.println("No cars found for this customer.");
            }
        } else {
            System.out.println("Customer not found.");
        }
    }

    public void showMyProfile(String id) {
        Document customer = customers.find(Filters.eq("_id", new ObjectId(id))).first();
        if (customer != null) {
            System.out.println("Customer Profile:");
            System.out.println("Name: " + customer.getString("name"));
            System.out.println("Email: " + customer.getString("email"));
            System.out.println("License Number: " + customer.getString("licenseNumber"));
            System.out.println("Address: " + customer.getString("address"));
            System.out.println("Phone Number: " + customer.getString("phoneNumber"));
            System.out.println("Username: " + customer.getString("username"));
            System.out.println("Role: " + customer.getString("role"));
            System.out.println("UserId: " + customer.getString("userId"));
        } else {
            System.out.println("Customer not found.");
        }
    }

    public void showAllCustomers() {
        for (Document doc : customers.find()) {
            System.out.println(doc.toJson());
        }
    }

    public void deleteCustomer(String id) {
        Document doc = customers.findOneAndDelete(new Document("_id", new ObjectId(id)));
        if (doc == null) {
            System.out.println("Customer id does not match");
        } else {
            System.out.println("Customer deleted successfully");
        }
    }

    public void updateCustomer(String id, Customer customer) {
        Document update = new Document("$set", new Document()
            .append("name", customer.getName())
            .append("email", customer.getEmail())
            .append("licenseNumber", customer.getLicenseNumber())
            .append("address", customer.getAddress())
            .append("phoneNumber", customer.getPhoneNumber())
            .append("username", customer.getBaseUser() != null ? customer.getBaseUser().getUsername() : null)
            .append("password", customer.getBaseUser() != null ? customer.getBaseUser().getPassword() : null)
            .append("role", customer.getBaseUser() != null ? customer.getBaseUser().getRole() : null)
            .append("userId", customer.getBaseUser() != null ? customer.getBaseUser().getRefId() : null)
        );
        customers.findOneAndUpdate(new Document("_id", new org.bson.types.ObjectId(id)), update);
        System.out.println("Customer updated successfully");
    }

    public Customer findCustomer(String id) {
        Bson filter =Filters.eq("_id",new ObjectId(id));
        Document doc = customers.find(filter).first();
        if (doc == null) return null;
        User user = new User(
            doc.getString("userId"),
            doc.getString("username"),
            doc.getString("name"),
            doc.getString("role"),
            doc.getString("userId")
        );
        user.setPassword(doc.getString("password"));
        Customer customer = new Customer(
            doc.getObjectId("_id").toHexString(),
            doc.getString("name"),
            doc.getString("email"),
            doc.getString("licenseNumber"),
            doc.getString("address"),
            user,
            doc.getString("phoneNumber"),
            null,
            null
        );
        return customer;
    }
} 