package dao;

import model.User;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import db.MongoUtil;

import java.rmi.server.ObjID;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

public class UserDao {
    private final MongoCollection<Document> users;

    public UserDao() {
        this.users = MongoUtil.getDatabase().getCollection("users");
    }

    public String addUser(User user) {
        Document userDoc = new Document();
        userDoc.append("username", user.getUsername());
        userDoc.append("password", user.getPassword());
        userDoc.append("role", user.getRole());
        userDoc.append("refId", user.getRefId());
        users.insertOne(userDoc);
        return userDoc.getObjectId("_id").toHexString();
    }

    public String AddRefID(String refID,String ID){

        Bson update=Updates.set("refId", refID);
        Bson filter=Filters.eq("_id",new ObjectId(ID));
        Document document=users.findOneAndUpdate(filter, update);
        return document.getString("refId");
    }

    public boolean usernameExists(String username) {
        return users.find(new Document("username", username)).first() != null;
    }
} 