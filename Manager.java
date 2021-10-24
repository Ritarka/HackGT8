import java.io.File;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
*@author Ritarka Samanta
*@version 1.0
*/
public class Manager {
    private static ArrayList<Account> accounts = new ArrayList<>();
    private static ArrayList<Supplier> suppliers = new ArrayList<>();
    private static ArrayList<Employee> employees = new ArrayList<>();

    private static Scanner input = new Scanner(System.in);

    private static int logs = 1;

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
                        if (arr[0].equals("Accounts:")) {
                            Account a = new Account(Double.parseDouble(arr[0]), Double.parseDouble(arr[1]), arr[2], arr[3]);
                            add(a);
                        } else if (arr[0].equals("Suppliers:")) {
                            Supplier s = new Supplier(arr[1], arr[2], arr[3], Double.parseDouble(arr[4]));
                            add(s);
                        } else if (arr[0].equals("Employees:")) {
                            Employee e = new Employee(arr[1], Double.parseDouble(arr[2]));
                            add(e);
                        }
                    }
                    reader.close();
                }
            }
        } catch (IOException e) {
            //e.printStackTrace();
        }

        while (true) {
            System.out.println("\nWhat would you like to do?");
            System.out.println("1. Add an account/supplier/employee");
            System.out.println("2. Check accounts");
            System.out.println("3. Pay employees");
            System.out.println("4. Exit the program");
            int x = input.nextInt();
            input.nextLine();

            switch (x) {
                case 1:
                    System.out.print("Employee, Supplier, or Account?: ");
                    String typeAccount = input.nextLine();
                    System.out.println(typeAccount);
                    
                    if (typeAccount.equalsIgnoreCase("account")) {
                        System.out.print("Please input the following information:\nDeposit (USD): ");
                        String deposit = input.nextLine();
                        System.out.print("Rate: ");
                        String rate = input.nextLine();
                        System.out.print("Name: ");
                        String name = input.nextLine();
                        System.out.print("Bank: ");
                        String bank = input.nextLine();
                        if (add(new Account(Double.parseDouble(deposit), Double.parseDouble(rate), name, bank))) {
                            System.out.printf("Added %s's account with $%.2f.\n", name, Double.parseDouble(deposit));
                        }
                    } else if (typeAccount.equalsIgnoreCase("supplier")) {
                        System.out.print("Please input the following information:\nName: ");
                        String name = input.nextLine();
                        System.out.print("Product: ");
                        String product = input.nextLine();
                        System.out.print("Company: ");
                        String company = input.nextLine();
                        System.out.print("Cost: ");
                        String cost = input.nextLine();

                        if (add(new Supplier(name, product, company, Double.parseDouble(cost)))) {
                            System.out.printf("Added supplier %s from %s.\n", name, company);
                        }
                    } else if (typeAccount.equalsIgnoreCase("employee")) {
                        System.out.print("Please input the following information:\nName: ");
                        String name = input.nextLine();
                        System.out.print("Salary: ");
                        String salary = input.nextLine();
                        if (add(new Employee(name, Double.parseDouble(salary)))) {
                            System.out.printf("Employee %s added.\n", name);
                        }
                    } else {
                        System.out.println("Invalid input.");
                    }
                    break;

                case 2:
                    checkAccounts();
                    break;
                case 3:
                    payEmployees();
                    break;
                default:

                    try {
                        File f = new File("save.txt");
                        f.createNewFile();

                        FileWriter w = new FileWriter("save.txt");

                        for (Account a : accounts) {
                            w.write("Accounts: " + a.summary());
                        }

                        for (Supplier s : suppliers) {
                            w.write("Suppliers: " + s.summary());
                        }

                        for (Employee e : employees) {
                            w.write("Employees: " + e.summary());
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
            accounts.add((Account) o);
        } else if (o instanceof Supplier) {
            suppliers.add((Supplier) o);
        } else if (o instanceof Employee) {
            employees.add((Employee) o);
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

        if (accounts.size() == 0) {
            System.out.println("You currently have no open accounts.");
            return;
        }

        int money = 0;
        String note = "";
        for (int i = 0; i < accounts.size(); i++) {
            System.out.println("" + (i + 1) + ": " + accounts.get(i));
            money += accounts.get(i).getMoney();
        }
        System.out.printf("You have a total of $%d across %d account(s)!\n", money, accounts.size());
        System.out.print("\nWould you like to make a deposit, withdrawal, or transfer? [d/w/t/x] ");
        String ans = input.nextLine().toLowerCase().substring(0, 1);

        if (ans.equals("d")) {
            System.out.println("Please enter the account number and deposit amount: ");
            String[] arr = input.nextLine().split(" ");
            int num = Integer.parseInt(arr[0]) - 1;
            double value = Double.parseDouble(arr[1]);

            accounts.get(num).deposit(value);
            note = String.format("Deposited $%.2f to account %d.\n", value, num);
        }
        else if (ans.equals("w")) {
            System.out.println("Please enter the account number and withdrawal amount: ");
            String[] arr = input.nextLine().split(" ");
            int num = Integer.parseInt(arr[0]) - 1;
            double value = Double.parseDouble(arr[1]);

            accounts.get(num).withdraw(value);
            note = String.format("Withdrew $%.2f from account %d.\n", value, num);         
        
        } else if (ans.equals("t")) {
            System.out.print("Please enter the account to transfer from: ");
            String accountFrom = input.nextLine();
            System.out.print("Please enter the account to transfer to: ");
            String accountTo = input.nextLine();

            System.out.print("Please enter the amount to transfer: ");
            String arr = input.nextLine();
            double value = Double.parseDouble(arr);
            accounts.get(Integer.parseInt(accountFrom)-1).withdraw(value);
            accounts.get(Integer.parseInt(accountTo)-1).deposit(value);
            note = String.format("Transfered $%.2f from account %s to %s,\n", value, accountFrom, accountTo); 
            for (int i = 0; i < accounts.size(); i++) {
                System.out.println("" + (i + 1) + ": " + accounts.get(i));
            }
        } else {
            return;
        }
        log(note);
    }

    public static void checkEmployees() {
        double wages = 0;
        for (Employee e : employees) {
            System.out.println(e);
            wages += e.getSalary();
        }
        System.out.printf("Altogether, you pay them $%.2f./n", wages);
    }

    private static void log(String logger) {
        try {
            File f = new File("logs.txt");
            f.createNewFile();
            FileWriter w = new FileWriter("logs.txt");
            w.append("" + logs + ": " + logger);
            w.close();
            logs++;
            System.out.printf(logger);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void manageSupplies() {
        if (accounts.size() == 0) {
            System.out.println("You don't have any accounts open");
            return;
        }
        if (suppliers.size() == 0) {
            System.out.println("Noone is selling anything");
            return;
        }
        for (Supplier s : suppliers) {
            System.out.println(s);
        }
        System.out.println("What would you like to buy?");
        String ans = input.nextLine();
        for (Supplier s : suppliers) {
            if (ans.equals(s.getProduct())) {
                System.out.printf("How many %s would you like to buy.\n", ans);
                int amount = input.nextInt();
                input.nextLine();

                double cost = amount * s.getCost();
                for (int i = 0; i < accounts.size(); i++) {
                    System.out.println("" + (i + 1) + ": " + accounts.get(i));
                }
                System.out.printf("Your total will be %.2f, which account would you like to use?.\n", cost);

                int accountNum = input.nextInt();
                if (accounts.get(accountNum - 1).withdraw(cost)) {
                    log(String.format("%d of %s was bought for %.2f using account %d", amount, s.getProduct(), cost, accountNum));
                }
                return;
            }
        }
        System.out.printf("There are no suppliers selling %s.\n", ans);
    }
    private static void payEmployees() {
        System.out.print("Please enter the name of the employee to pay?: ");
        String employeeTemp = input.nextLine();
        System.out.print("How much would you like to pay the employee?: ");
        String pay = input.nextLine();
        for(Employee person : employees){
            if(person.getName().equalsIgnoreCase(employeeTemp)){
                person.payEmployee(Double.parseDouble(pay));
            }
        }
        System.out.print(String.format("%s was paid $%s.", employeeTemp, pay));
    }
}