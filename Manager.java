import java.util.Scanner;

/**
*@author Ritarka Samanta
*@version 1.0
*/
public class Manager {
    private static Account[] accounts;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        while (true) {
            //Main Executeable loop
        }
    }

    public static void addAccount(Account a) {
        Account[] newAccounts = new Account[accounts.length + 1];
        for (int i = 0; i < accounts.length; i++) {
            newAccounts[i] = accounts[i];
        }
        newAccounts[accounts.length] = a;
        accounts = newAccounts;
    }
}