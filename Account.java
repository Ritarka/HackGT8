/**
*@author Ritarka Samanta
*@version 1.0
*/
public class Account {
    private int money;
    private double rate;
    private String name;
    private String bank;

    /**
    *@param money the amount of money the account starts with
    *@param rate the APY rate
    *@param the name of the account
    *@param the name of the bank
    */
    public Account(int money, double rate, String name, String bank) {
        this.money = money;
        this.rate = rate;
        this.name = name;
        this.bank = bank;
    }

    public void withdraw(int money) {
        this.money -= money;
    }

    public void deposit(int money) {
        this.money += money;
    }

    public void transfer(Account other, int amount) {
        this.money -= amount;
        other.money += amount;
    }

    public void accumulateInterest() {
        this.money *= rate;
    }

    public String toString() {
        return String.format("Account %s has %d dollars and makes %d every year.", name, money, money * rate);
    }

    public int getMoney() {
        return money;
    }
}