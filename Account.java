import java.text.DecimalFormat;

/**
*@author Ritarka Samanta
*@version 1.0
*/
public class Account {
    private double money;
    private double rate;
    private String name;
    private String bank;

    /**
    *@param money the amount of money the account starts with
    *@param rate the APY rate
    *@param the name of the account
    *@param the name of the bank
    */
    public Account(double money, double rate, String name, String bank) {
        this.money = money;
        this.rate = rate;
        this.name = name;
        this.bank = bank;
    }

    /**
     * Withdraw some money
     * @param money to be withdrawn
     */
    public boolean withdraw(double money) {
        if (money > this.money) {
            System.out.println("You don't have enough money for that");
            return false;
        }
        this.money -= money;
        return true;
    }

    /**
     * Deposit some money
     * @param money to be deposited
     */
    public void deposit(double money) {
        this.money += money;
    }

    /**
     * Transfer money from one account to another
     * @param other the account to be transferred to
     * @param amount the amount of money to be transferred
     */
    public void transfer(Account other, double amount) {
        this.money -= amount;
        other.money += amount;
    }

    /**
     * Calling this function makes an account gain interest
     */
    public void accumulateInterest() {
        this.money *= rate;
    }

    @Override
    public String toString() {
        DecimalFormat dollar = new DecimalFormat(".00");
        return String.format("Account %s has $%.2f and makes $%s every year.", name, money, dollar.format(Math.round(money * rate)));
    }

    /**
     * For internal summarizing purposes
     * @return a string with all instance variables
     */
    public String summary() {
        return String.format("%f %f %s %s\n", money, rate, name, bank);
    }

    /**
     * Getter method
     * @return the amount of money in the account
     */
    public double getMoney() {
        return money;
    }
}