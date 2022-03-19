package tqs;

public class Stock {

    private String label;
    private int quantity;

    public Stock(String label, int quantity) {
        this.label = label;
        this.quantity = quantity;
    }

    public String getLabel() {
        return this.label;
    }
    public int getQuantity() {
        return this.quantity;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
}
