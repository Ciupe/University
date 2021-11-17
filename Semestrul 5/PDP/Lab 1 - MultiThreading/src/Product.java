public class Product {

    private final String name;
    private final float price;
    private int quantity;
    private final int initialQuantity;

    public Product(String name, Float price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.initialQuantity = quantity;
    }

    public String getName(){
        return this.name;
    }

    public float getPrice(){
        return this.price;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public void setQuantity(int newQuantity){
        this.quantity = newQuantity;
    }

    public int getInitialQuantity(){
        return this.initialQuantity;
    }

    public String toString() {
        return "Name: " + this.name + "\nPrice: " + this.price + "\nQuantity: " + this.quantity;
    }
}
