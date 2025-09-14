package service;
import dao.CardDao;
import model.Card;

public class cardService {
    private final CardDao cardDao;

    public cardService(){
        this.cardDao=new CardDao();
    }

    public Card  checkCard(int card_number,int pin){
             return cardDao.checkCard(card_number,pin);

    }
        
}
