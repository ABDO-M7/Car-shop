package service;

import dao.RentDao;
import model.Car;
import model.Card;
import model.Customer;

public class RentService {
    private  final RentDao rentDao;
    private final cardService cardService;
    

    public RentService(){
        this.rentDao=new RentDao();

        this.cardService=new cardService();
    }


    public  void makeRent(Customer customer, Car car,int card_number, int pin) {

        Card card=cardService.checkCard(card_number, pin);
        if(card.getBalance()==-1){
            System.out.println("we do not found your CARD NUMBER");
        }else if(card.getBalance()==-2){
            System.out.println("incorrect pin");
        }else{rentDao.makeRent(customer, car,card);}


        
        
        

        
    }

}