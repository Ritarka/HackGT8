import java.io.File;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

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

        try {
            File f = new File("save.txt");
            Scanner reader = new Scanner(f);
            if(!f.createNewFile()) {
                System.out.println("Reload previous session? [Y/n]");
                String ans = input.nextLine().toLowerCase().substring(0, 1);
                if (ans.equals("y")) {
                    while (reader.hasNextLine()) {
                        String[] arr = reader.nextLine().split(" ");
                        if (arr.length == 4) {
                            Account a = new Account(Double.parseDouble(arr[0]), Double.parseDouble(arr[1]), arr[2], arr[3]);
                            add(a);
                        } else if (arr.length == 3) {
                            Supplier s = new Supplier(arr[0], arr[1], arr[2]);
                            add(s);
                        } else if (arr.length == 2) {
                            Employee e = new Employee(arr[0], Double.parseDouble(arr[1]));
                            add(e);
                        }
                    }
                    reader.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

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
                    System.out.print("Please input the following information:\nDeposit (USD): ");
                    String deposit = input.nextLine();
                    System.out.print("Rate: ");
                    String rate = input.nextLine();
                    System.out.print("Name: ");
                    String name = input.nextLine();
                    System.out.print("Bank: ");
                    String bank = input.nextLine();
                    String[] arr = {deposit, rate, name, bank};
                    if (arr.length == 4) {
                        Account a = new Account(Double.parseDouble(arr[0]), Double.parseDouble(arr[1]), arr[2], arr[3]);
                        if (add(a)) {
                            System.out.printf("Added %s account with $%.2f.\n", arr[2], Double.parseDouble(arr[0]));
                        }
                    } else if (arr.length == 3) {
                        Supplier s = new Supplier(arr[0], arr[1], arr[2]);
                        if (add(s)) {
                            System.out.printf("Added supplier %s from %s.\n", arr[0], arr[2]);
                        }
                    } else if (arr.length == 2) {
                        Employee e = new Employee(arr[0], Double.parseDouble(arr[1]));
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

                    try {
                        File f = new File("save.txt");
                        f.createNewFile();

                        FileWriter w = new FileWriter("save.txt");

                        for (Account a : accounts) {
                            w.write(a.summary());
                        }

                        w.close();

                        System.out.print("Goodbye!");
                        System.exit(0);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    
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

    /**
     * Check finances and make deposits/withdrawls
     */
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
        System.out.print("\nWould you like to make a deposit, withdrawal, or transfer? [d/w/t/x] ");
        String ans = input.nextLine().toLowerCase().substring(0, 1);

        if (ans.equals("d")) {
            System.out.println("Please enter the account number and deposit amount: ");
            String[] arr = input.nextLine().split(" ");
            int num = Integer.parseInt(arr[0]) - 1;
            double value = Double.parseDouble(arr[1]);

            accounts[num].deposit(value);
            System.out.printf("Deposited $%.2f to account %d.\n", value, num + 1);
            }
        else if (ans.equals("w")) {
            System.out.println("Please enter the account number and withdrawal amount: ");
            String[] arr = input.nextLine().split(" ");
            int num = Integer.parseInt(arr[0]) - 1;
            double value = Double.parseDouble(arr[1]);

            accounts[num].withdraw(value);
            System.out.printf("Withdrew $%.2f from account %d.\n", value, num + 1);        
        
        } else if (ans.equals("t")) {
            System.out.print("Please enter the account to transfer from: ");
            String accountFrom = input.nextLine();
            System.out.print("Please enter the account to transfer to: ");
            String accountTo = input.nextLine();

            System.out.print("Please enter the amount to transfer: ");
            String arr = input.nextLine();
            double value = Double.parseDouble(arr);

            accounts[Integer.parseInt(accountFrom)-1].withdraw(value);
            accounts[Integer.parseInt(accountTo)-1].deposit(value);
            System.out.printf("Transfered $%.2f from account %s to %s,\n", value, accountFrom, accountTo);   
            for (int i = 0; i < accounts.length; i++) {
                System.out.println("" + (i + 1) + ": " + accounts[i]);
            }
        } else {
            return;
        }

    }
}