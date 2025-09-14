package dao;

import model.Card;

import com.mongodb.client.MongoCollection;
import db.MongoUtil;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.model.Filters;


public class CardDao {
    
    private final MongoCollection<Document> cards ;

    public CardDao(){
        cards=MongoUtil.getDatabase().getCollection("cards");
    }


    public Card checkCard(int card_number, int pin){

        Bson filterCardcard_number=Filters.eq("card_number",card_number);
        Document doc=cards.find(filterCardcard_number).first();

        if(doc==null){
            return new Card(0,0,-1);
        }
        if(doc.getInteger("pin")!=pin){
            return new Card(0,0,-2);
        }

        return new Card(card_number,pin,doc.getInteger("balance"));

        

    }
}
