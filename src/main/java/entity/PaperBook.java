package entity;

import javax.persistence.Entity;

/**
 *
 * @author Bo
 */
@Entity
public class PaperBook extends Book {

    private double shippingWeight;
    private int inStock;
    
    public PaperBook(double shippingWeight, int inStock, String title, String author, double price) {
        super(title, author, price);
        this.shippingWeight = shippingWeight;
        this.inStock = inStock;
    }
    
    public PaperBook() {
        
    }

    public double getShippingWeight() {
        return shippingWeight;
    }

    public void setShippingWeight(double shippingWeight) {
        this.shippingWeight = shippingWeight;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }
    
}
