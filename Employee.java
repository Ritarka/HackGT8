/**
 * @author Ritarka samanta
 * @version 1.0
 */
public class Employee {
    private String name;
    private double salary;

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return String.format("Employee %s makes $%f.\n", name, salary);
    }

    /**
     * getter method for the salary of the employee
     * @return the salary of the employee
     */
    public double getSalary() {
        return salary;
    }
    public String getName() {
        return name;
    }
}