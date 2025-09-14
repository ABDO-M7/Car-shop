package dao;
import model.Customer;
import model.User;
import model.Car;
import model.Card;

import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.TransactionBody;

import db.MongoUtil;

import java.time.Instant;
import java.util.Date;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;

import java.util.Scanner;

public class RentDao {

    private final MongoCollection<Document> customers;
    private final MongoCollection<Document> cars;
    private final MongoCollection<Document> rentals;
    private final MongoCollection<Document> cards;

    
    public RentDao(){
        this.customers = MongoUtil.getDatabase().getCollection("customers");
        this.cars = MongoUtil.getDatabase().getCollection("cars");
        this.rentals = MongoUtil.getDatabase().getCollection("rentals");
        this.cards=MongoUtil.getDatabase().getCollection("cards");
    }

    public void makeRent(Customer customer, Car car,Card card){


        ObjectId CustomerId=new ObjectId(customer.getId());
        ObjectId CarID=new ObjectId(car.getId());
        
        TransactionOptions transactionOptions = TransactionOptions.builder()
            .writeConcern(WriteConcern.MAJORITY)
            .build();

            try (ClientSession session = MongoUtil.getMongoClient().startSession()){

                TransactionBody<String> rent = () -> {

                    Bson cardFilter = Filters.eq("card_number", card.getCard_number());
                    Document cardDoc = cards.find(session, cardFilter).first();
    
                    if (cardDoc == null) {
                        throw new RuntimeException("Card not found");
                    }
    
                    int currentBalance = cardDoc.getInteger("balance");
                    if (currentBalance < car.getPricePerDay()) {
                        throw new RuntimeException("Not enough balance on card");
                    }
    
           
                    Bson carFilter = Filters.and(
                            Filters.eq("_id", CarID),
                            Filters.eq("statue", "available")
                    );
    
                    Document updatedCar = cars.findOneAndUpdate(
                            session,
                            carFilter,
                            Updates.set("statue", "rented"),
                            new FindOneAndUpdateOptions().returnDocument(ReturnDocument.BEFORE)
                    );
    
                    if (updatedCar == null) {
                        throw new RuntimeException("Car is already rented by another user.");
                    }
    
                    cards.findOneAndUpdate(
                            session,
                            cardFilter,
                            Updates.inc("balance", -car.getPricePerDay())
                    );
    
                    Bson customerFilter = Filters.eq("_id", CustomerId);
                    customers.updateOne(
                            session,
                            customerFilter,
                            Updates.push("cars", CarID),
                            new UpdateOptions().upsert(true)
                    );

                    Bson rentalCheck = Filters.eq("car_id", CarID);
                    long existingRentals = rentals.countDocuments(session, rentalCheck);
                    if (existingRentals > 0) {
                        throw new RuntimeException("Rental record already exists for this car");
                    }
    
                    Document rentalDoc = new Document()
                            .append("customer_id", CustomerId)
                            .append("car_id", CarID)
                            .append("pricePerDay", car.getPricePerDay())
                            .append("rentDate", Date.from(Instant.now()));
    
                    rentals.insertOne(session, rentalDoc);
    
                    return "Car rented successfully";

                };

                try {
                    String result = session.withTransaction(rent);
                    System.out.println(result);
                } catch (Exception e) {
                    System.err.println("Transaction failed: " + e.getMessage());
                }
 
            }
        

    }

    public void buyCar() {
        // Prompt for customer ID and car ID
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter customer ID: ");
        String customerId = scanner.nextLine();
        System.out.print("Enter car ID: ");
        String carId = scanner.nextLine();
        ObjectId customerObjId = new ObjectId(customerId);
        ObjectId carObjId = new ObjectId(carId);

        try (ClientSession session = MongoUtil.getMongoClient().startSession()) {
            TransactionBody<String> buy = () -> {
                // Check if car is available
                Document carDoc = cars.find(session, Filters.and(
                    Filters.eq("_id", carObjId),
                    Filters.eq("statue", "available")
                )).first();
                if (carDoc == null) {
                    throw new RuntimeException("Car is not available for sale.");
                }
                // Mark car as sold
                cars.findOneAndUpdate(session, Filters.eq("_id", carObjId), Updates.set("statue", "sold"));
                // Add car to customer's cars list
                customers.updateOne(session, Filters.eq("_id", customerObjId), Updates.push("cars", carObjId));
                // Optionally, create a sale record (not implemented)
                return "Car bought successfully.";
            };
            try {
                String result = session.withTransaction(buy);
                System.out.println(result);
            } catch (Exception e) {
                System.err.println("Transaction failed: " + e.getMessage());
            }
        }
    }

    public void deleteRental() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter rental ID to delete: ");
        String rentalId = scanner.nextLine();
        ObjectId rentalObjId = new ObjectId(rentalId);
        Document doc = rentals.findOneAndDelete(Filters.eq("_id", rentalObjId));
        if (doc == null) {
            System.out.println("Rental ID does not match");
        } else {
            System.out.println("Rental deleted successfully");
        }
    }

    public void updateRental() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter rental ID to update: ");
        String rentalId = scanner.nextLine();
        ObjectId rentalObjId = new ObjectId(rentalId);
        Document rentalDoc = rentals.find(Filters.eq("_id", rentalObjId)).first();
        if (rentalDoc == null) {
            System.out.println("Rental not found.");
            return;
        }
        // For simplicity, allow updating pricePerDay only
        System.out.print("Enter new price per day (current: " + rentalDoc.getInteger("pricePerDay") + "): ");
        String input = scanner.nextLine();
        int newPrice = rentalDoc.getInteger("pricePerDay");
        try {
            if (!input.isEmpty()) {
                newPrice = Integer.parseInt(input);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number. Keeping old value.");
        }
        rentals.findOneAndUpdate(Filters.eq("_id", rentalObjId), Updates.set("pricePerDay", newPrice));
        System.out.println("Rental updated successfully");
    }
}
