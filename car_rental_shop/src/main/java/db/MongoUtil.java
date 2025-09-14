package db;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class MongoUtil {

    private static  MongoClient client;
    private static  MongoDatabase db;

    static {
        try (InputStream input = MongoUtil.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties prop = new Properties();
            if (input == null) {
                throw new RuntimeException("Unable to find config.properties");
            }

            prop.load(input);

            String uri=prop.getProperty("mongodb.uri");
            String dbName=prop.getProperty("mongodb.database");
            
            client=MongoClients.create(uri);
            db=client.getDatabase(dbName);

            
        }catch (IOException ex) {
            throw new RuntimeException("Failed to load MongoDB config", ex);
        }
    }

    public static MongoDatabase getDatabase() {
        return db;
    }



    public static void close() {
        if (client != null) {

            client.close();
        }
    }

    public static MongoClient getMongoClient() {
        return client;
    }

}
