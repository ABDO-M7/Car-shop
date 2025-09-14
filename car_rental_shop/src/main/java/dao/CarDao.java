package dao;

import model.Car;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

import db.MongoUtil;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

public class CarDao {
    private final MongoCollection<Document> cars;
    
    public CarDao(){
        this.cars = MongoUtil.getDatabase().getCollection("cars");
    }
    public String addCar(Car car){
        Document carDoc = new Document();
        carDoc.append("model", car.getModel());
        carDoc.append("color", car.getColor());
        carDoc.append("numberOfDoors", car.getNumberOfDoors());
        carDoc.append("year", car.getYear());
        carDoc.append("pricePerDay", car.getPricePerDay());
        carDoc.append("price", car.getPrice());
        carDoc.append("mileage", car.getMileage());
        carDoc.append("statue", car.isStatue());
        cars.insertOne(carDoc);
        return carDoc.getObjectId("_id").toHexString();
    }




    public void showCars(Bson Filter){

         MongoCursor <Document> cursor =cars.find(Filter).cursor();

        if (!cursor.hasNext()) {
            System.out.println("No cars found.");
             return;
        }
        
        while (cursor.hasNext()){
            System.out.println(cursor.next().toJson());
            }
    }

    public void deleteCar(String id){
        Bson filter=Filters.eq("_id", new ObjectId(id));
         Document doc=cars.findOneAndDelete(filter);
        if(doc==null){
            System.out.println("id does not match");
        }else{

            System.out.println("deleted succesfly");
        } 
        
        
    }

    public void updateCar(String id,Car car){
        Document update = new Document("$set", new Document()
        .append("model", car.getModel())
        .append("color",car.getColor())
        .append("numberOfDoors", car.getNumberOfDoors())
        .append("year", car.getYear())
        .append("pricePerDay", car.getPricePerDay())
        .append("price", car.getPrice())
        .append("mileage", car.getMileage())
        .append("statue", car.isStatue())
        );
        Bson filter= Filters.eq("_id",  new ObjectId(id));
        cars.findOneAndUpdate( filter, update);
    }
    
    public Car findCar(String id){
        Bson filter=Filters.eq("_id", new ObjectId(id));
        
        Document doc =cars.find(filter).first();
        return doctoCar(doc);
    }


    /////////////////////////////helper methodes///////////////////
    public Car doctoCar(Document doc){
        Car car =new Car(doc.getObjectId("_id").toHexString(),doc.getString("model"),doc.getString("color"),
        doc.getInteger("numberOfDoors"),doc.getInteger("year"),doc.getInteger("pricePerDay"),doc.getInteger("price"),
        doc.getInteger("mileage"),doc.getString("statue"));
        return car;


    }



}
