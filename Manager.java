import java.util.Scanner;

/**
*@author Ritarka Samanta
*@version 1.0
*/
public class Manager {
    private static Account[] accounts = new Account[0];
    private static Supplier[] suppliers = new Supplier[0];
    private static Employee[] employees = new Employee[0];

    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            System.out.println("\nWhat would you like to do?");
            System.out.println("1. Add an account/supplier/employee");
            System.out.println("2. Check accounts");
            System.out.println("3. Find parts");
            System.out.println("4. Exit the program");
            int x = input.nextInt();
            input.nextLine();

            switch (x) {
                case 1:
                    System.out.println("Please input their information");
                    String[] arr = input.nextLine().split(" ");
                    if (arr.length == 4) {
                        Account a = new Account(Integer.parseInt(arr[0]), Double.parseDouble(arr[1]), arr[2], arr[3]);
                        if (add(a)) {
                            System.out.printf("Added %s account with $%d.\n", arr[2], Integer.parseInt(arr[0]));
                        }
                    } else if (arr.length == 3) {
                        Supplier s = new Supplier(arr[0], arr[1], arr[2]);
                        if (add(s)) {
                            System.out.printf("Added supplier %s from %s.\n", arr[0], arr[2]);
                        }
                    } else if (arr.length == 2) {
                        Employee e = new Employee(arr[0], Integer.parseInt(arr[1]));
                        if (add(e)) {
                            System.out.printf("Employee %s added.\n", arr[0]);
                        }
                    } else {
                        System.out.println("Invalid input.");
                    }
                    break;
                case 2:
                    checkAccounts();
                    break;
                case 3:
                    //
                default:
                    System.out.print("Goodbye!");
                    System.exit(0);
            }
            
        }
    }

    /**
    *@param o the object to be sorted into the arrays
    */
    private static boolean add(Object o) {
        if (o instanceof Account) {
            Account[] arr = accounts;
            Account[] brr = new Account[arr.length + 1];
            Account a = (Account)o;
            for (int i = 0; i < arr.length; i++) {
                brr[i] = arr[i];
            }
            brr[arr.length] = a;
            accounts = brr;
        } else if (o instanceof Supplier) {
            Supplier[] arr = suppliers;
            Supplier[] brr = new Supplier[arr.length + 1];
            Supplier a = (Supplier)o;
            for (int i = 0; i < arr.length; i++) {
                brr[i] = arr[i];
            }
            brr[arr.length] = a;
            suppliers = brr;
        } else if (o instanceof Employee) {
            Employee[] arr = employees;
            Employee[] brr = new Employee[arr.length + 1];
            Employee a = (Employee)o;

            for (int i = 0; i < arr.length; i++) {
                brr[i] = arr[i];
            }
            brr[arr.length] = a;
            employees = brr;
        } else {
            System.out.println("Invalid Object!");
            return false;
        }
        return true;
    }

    private static void checkAccounts() {
        if (accounts.length == 0) {
            System.out.println("You currently have no open accounts.");
            return;
        }

        int money = 0;
        for (int i = 0; i < accounts.length; i++) {
            System.out.println("" + (i + 1) + ": " + accounts[i]);
            money += accounts[i].getMoney();
        }
        System.out.printf("You have a total of $%d across %d account(s)!\n", money, accounts.length);
        System.out.print("\nWould you like to make a deposit or withdrawal? [d/w/x] ");
        String ans = input.nextLine().toLowerCase().substring(0, 1);

        if (ans.equals("d")) {
            System.out.println("Please enter the account number and deposit amount: ");
        } else if (ans.equals("w")) {
            System.out.println("Please enter the account number and withdrawal amount: ");
        } else {
            return;
        }

        String[] arr = input.nextLine().split(" ");
        int num = Integer.parseInt(arr[0]) - 1;
        double value = Double.parseDouble(arr[1]);

        if (ans.equals("w")) {
            accounts[num].withdraw(value);
            System.out.printf("Withdrew $%.2f from account %d.\n", value, num + 1);
        } else {
            accounts[num].deposit(value);
            System.out.printf("Deposited $%.2f to account %d.\n", value, num + 1);
        }
    }
}