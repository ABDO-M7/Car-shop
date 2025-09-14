package service;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.*;

import db.*;
import model.User;



public class Login {
    
    public static User  login(String username, String Pass){

        MongoCollection<Document> users = MongoUtil.getDatabase().getCollection("users");
        Bson filter = Filters.eq("username", username);
        Document doc = users.find(filter).first();
        if(doc!=null&& doc.getString("password").equals(Pass)){
            User user = new User();
            user.setId(doc.getObjectId("_id").toString());
            user.setUsername(doc.getString("username"));
            user.setPassword(doc.getString("password"));
            user.setRole(doc.getString("role"));
            user.setRefId(doc.getObjectId("refId").toString());
            return user;
        }

        return null;
    }
   
}
