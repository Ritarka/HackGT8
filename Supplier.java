/**
*@author Ritarka Samanta
*@version 1.0
*/
public class Supplier {
    private String name;
    private String product;
    private String company;
    private double cost;

    public Supplier(String name, String product, String company, double cost) {
        this.name = name;
        this.product = product;
        this.company = company;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return String.format("%s from %s sells %s at $%.2f each", name, company, product, cost);
    }

    public String summary() {
        return String.format("%s %s %s %f", name , product, company, name);
    }

    public String getProduct() {
        return product;
    }

    public double getCost() {
        return cost;
    }
}