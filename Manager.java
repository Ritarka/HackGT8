import java.util.Scanner;

/**
*@author Ritarka Samanta
*@version 1.0
*/
public class Manager {
    private static Account[] accounts;
    private static Supplier[] suppliers;
    private static Employee[] employees;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        while (true) {
            //Main Executeable loop
        }
    }

    /**
    *@param o the object to be sorted into the arrays
    */
    public static void add(Object o) {
        if (o instanceof Account) {
            Account[] arr = accounts;
            Account[] brr = new Account[arr.length + 1];
            Account a = (Account)o;
        } else if (o instanceof Supplier) {
            Supplier[] arr = suppliers;
            Supplier[] brr = new Supplier[arr.length + 1];
            Supplier a = (Supplier)o;
        } else if (o instanceof Employee) {
            Employee[] arr = employees;
            Employee[] brr = new Employee[arr.length + 1];
            Employee a = (Supplier)o;
        } else {
            throw new Exception("Adding invalid object");
        }

        for (int i = 0; i < arr.length; i++) {
            brr[i] = arr[i];
        }
        brr[arr.length] = a;
        arr = brr;
    }

    public static void checkAccounts() {
        int money = 0;
        for (Account a : accounts) {
            System.out.println(a);
            money += a.getMoney();
        }
        System.out.println("You have a total of %d dollars across %d accounts!", money, accounts.length);
    }
}