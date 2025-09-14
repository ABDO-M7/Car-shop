package model;

public class Card {

    private int card_number;
    private int pin;
    private int balance;

    public Card(int card_number, int pin, int balance) {
        this.card_number = card_number;
        this.pin = pin;
        this.balance = balance;
    }

    public int getCard_number() {
        return card_number;
    }

    public void setCard_number(int card_number) {
        this.card_number = card_number;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
    
}
