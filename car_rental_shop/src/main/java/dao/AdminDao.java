package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import db.MongoUtil;
import org.bson.Document;
import org.bson.types.ObjectId;
import model.admin;
import model.User;

public class AdminDao {
    private final MongoCollection<Document> admins;

    public AdminDao() {
        this.admins = MongoUtil.getDatabase().getCollection("admins");
    }

    public String addAdmin(admin adminObj) {
        Document adminDoc = new Document();
        adminDoc.append("name", adminObj.getName());
        adminDoc.append("email", adminObj.getEmail());
        adminDoc.append("phone", adminObj.getPhone());
        if (adminObj.getBaseUser() != null) {
            adminDoc.append("username", adminObj.getBaseUser().getUsername());
            adminDoc.append("password", adminObj.getBaseUser().getPassword());
            adminDoc.append("role", adminObj.getBaseUser().getRole());
            adminDoc.append("userId", adminObj.getBaseUser().getRefId());
        }
        admins.insertOne(adminDoc);
        return adminDoc.getObjectId("_id").toHexString();
    }

    public void showAllAdmins() {
        for (Document doc : admins.find()) {
            System.out.println(doc.toJson());
        }
    }

    public void deleteAdmin(String id) {
        Document doc = admins.findOneAndDelete(new Document("_id", new ObjectId(id)));
        if (doc == null) {
            System.out.println("Admin id does not match");
        } else {
            System.out.println("Admin deleted successfully");
        }
    }

    public void updateAdmin(String id, admin adminObj) {
        Document update = new Document("$set", new Document()
            .append("name", adminObj.getName())
            .append("email", adminObj.getEmail())
            .append("phone", adminObj.getPhone())
            .append("username", adminObj.getBaseUser() != null ? adminObj.getBaseUser().getUsername() : null)
            .append("password", adminObj.getBaseUser() != null ? adminObj.getBaseUser().getPassword() : null)
            .append("role", adminObj.getBaseUser() != null ? adminObj.getBaseUser().getRole() : null)
            .append("userId", adminObj.getBaseUser() != null ? adminObj.getBaseUser().getRefId() : null)
        );
        admins.findOneAndUpdate(new Document("_id", new ObjectId(id)), update);
        System.out.println("Admin updated successfully");
    }
}
